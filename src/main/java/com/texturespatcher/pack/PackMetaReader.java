package com.texturespatcher.pack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.minecraft.client.resources.IResourcePack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class PackMetaReader {

    public static final String SECTION = "textures_patcher";
    public static final String FLAG_BLOCKS = "enableTexturesPatcherBlocks";
    public static final String FLAG_FORESTRY_CIRCUIT_BOARD = "enableSeparateForestryCircuitBoardTextures";
    public static final String FLAG_FORESTRY_ELECTRON_TUBE = "enableSeparateForestryElectronTubeTextures";
    public static final String FLAG_FORESTRY_HONEY_DROP = "enableSeparateForestryHoneyDropTextures";
    public static final String FLAG_SPAWN_EGG = "enableSeparateSpawnEggTextures";
    public static final String FLAG_GT_COPPER_ITEM = "enableSeparateGTCopperItemTextures";

    private static final String META_NAME = "pack.mcmeta";

    private static final Logger LOG = LogManager.getLogger("TexturesPatcher");

    private PackMetaReader() {}

    public static PackMeta read(IResourcePack pack) {
        if (pack == null) {
            return null;
        }
        JsonObject root = readRootObject(pack);
        if (root == null) {
            return null;
        }
        return parse(root);
    }

    public static PackMeta parse(JsonObject root) {
        if (root == null || !root.has(SECTION)) {
            return null;
        }
        JsonElement section = root.get(SECTION);
        if (section == null || !section.isJsonObject()) {
            return null;
        }
        JsonObject flags = section.getAsJsonObject();
        PackMeta meta = new PackMeta(
            readFlag(flags, FLAG_BLOCKS),
            readFlag(flags, FLAG_FORESTRY_CIRCUIT_BOARD),
            readFlag(flags, FLAG_FORESTRY_ELECTRON_TUBE),
            readFlag(flags, FLAG_FORESTRY_HONEY_DROP),
            readFlag(flags, FLAG_SPAWN_EGG),
            readFlag(flags, FLAG_GT_COPPER_ITEM));
        return meta.isEmpty() ? null : meta;
    }

    private static boolean readFlag(JsonObject flags, String name) {
        JsonElement flag = flags.get(name);
        if (flag == null || !flag.isJsonPrimitive()) {
            return false;
        }
        try {
            return flag.getAsBoolean();
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static JsonObject readRootObject(IResourcePack pack) {
        InputStream stream = null;
        try {
            stream = openByName(pack);
            if (stream != null) {
                return parseStream(pack, stream);
            }
        } catch (RuntimeException e) {
            LOG.debug("通过资源包接口读取 {} 的 pack.mcmeta 失败: {}", pack.getClass().getName(), e.toString());
        } finally {
            closeQuietly(stream);
        }
        return readFromFile(pack);
    }

    private static InputStream openByName(IResourcePack pack) {
        Method candidate = findNameStreamMethod(pack.getClass());
        if (candidate == null) {
            return null;
        }
        try {
            candidate.setAccessible(true);
            Object result = candidate.invoke(pack, META_NAME);
            if (result instanceof InputStream) {
                return (InputStream) result;
            }
        } catch (Throwable ignored) {
        }
        return null;
    }

    private static Method findNameStreamMethod(Class<?> packClass) {
        Method byName = null;
        Method bySignature = null;
        for (Class<?> clazz = packClass; clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            Method[] methods;
            try {
                methods = clazz.getDeclaredMethods();
            } catch (Throwable ignored) {
                continue;
            }
            for (Method method : methods) {
                if (Modifier.isStatic(method.getModifiers())) {
                    continue;
                }
                if (method.getReturnType() != InputStream.class) {
                    continue;
                }
                Class<?>[] params = method.getParameterTypes();
                if (params.length != 1 || params[0] != String.class) {
                    continue;
                }
                if ("getInputStreamByName".equals(method.getName())) {
                    byName = method;
                } else if (bySignature == null) {
                    bySignature = method;
                }
            }
            if (byName != null) {
                return byName;
            }
        }
        return bySignature;
    }

    private static JsonObject readFromFile(IResourcePack pack) {
        File file = locatePackFile(pack);
        if (file == null) {
            LOG.debug("Unable to locate the physical file of resource package {}, skip pack.mcmeta check", pack.getClass().getName());
            return null;
        }
        InputStream stream = null;
        ZipFile zip = null;
        try {
            if (file.isDirectory()) {
                File meta = new File(file, META_NAME);
                if (!meta.isFile()) {
                    return null;
                }
                stream = new FileInputStream(meta);
            } else {
                zip = new ZipFile(file);
                ZipEntry entry = zip.getEntry(META_NAME);
                if (entry == null) {
                    return null;
                }
                stream = zip.getInputStream(entry);
            }
            return parseStream(pack, stream);
        } catch (IOException e) {
            LOG.debug("Failed to read pack.mcmeta of {}: {}", file, e.toString());
            return null;
        } catch (RuntimeException e) {
            LOG.debug("Failed to parse pack.mcmeta of {}: {}", file, e.toString());
            return null;
        } finally {
            closeQuietly(stream);
            closeQuietly(zip);
        }
    }

    private static JsonObject parseStream(IResourcePack pack, InputStream stream) {
        if (stream == null) {
            return null;
        }
        try {
            Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            JsonElement element = new JsonParser().parse(reader);
            if (element == null || !element.isJsonObject()) {
                return null;
            }
            return element.getAsJsonObject();
        } catch (RuntimeException e) {
            LOG.debug("Failed to parse pack.mcmeta of {}: {}", pack.getClass().getName(), e.toString());
            return null;
        }
    }

    private static File locatePackFile(Object pack) {
        for (Class<?> clazz = pack.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields;
            try {
                fields = clazz.getDeclaredFields();
            } catch (Throwable ignored) {
                continue;
            }
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers()) || field.getType() != File.class) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    Object value = field.get(pack);
                    if (value instanceof File) {
                        return (File) value;
                    }
                } catch (Throwable ignored) {
                }
            }
        }
        return null;
    }

    private static void closeQuietly(java.io.Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException ignored) {
        }
    }
}
