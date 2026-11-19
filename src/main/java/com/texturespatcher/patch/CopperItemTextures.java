package com.texturespatcher.patch;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CopperItemTextures {

    private static final String INGOT = "copper_ingot";

    private static final String NUGGET = "copper_nugget";

    private static final Logger LOG = LogManager.getLogger("TexturesPatcher");

    private static final Map<Class<?>, Map<Integer, IIcon>> CACHE = new HashMap<Class<?>, Map<Integer, IIcon>>();

    private static IIcon ingotIcon;

    private static IIcon nuggetIcon;

    private static IIconRegister lastRegister;

    private static Method getMaterial;

    private static Method getOrePrefix;

    private static Object materialCopper;

    private static Object prefixIngot;

    private static Object prefixNugget;

    private static boolean bound;

    private static boolean reported;

    private CopperItemTextures() {}

    public static void onRegisterIcons(IIconRegister register) {
        if (register == null || register == lastRegister) {
            return;
        }
        lastRegister = register;
        CACHE.clear();
        ingotIcon = null;
        nuggetIcon = null;
        if (!PatchConditions.shouldPatchGTCopperItems()) {
            return;
        }
        ingotIcon = register(register, INGOT);
        nuggetIcon = register(register, NUGGET);
        if (ingotIcon == null && nuggetIcon == null) {
            return;
        }
        LOG.info("GregTech copper item fix turned on: ingot={}, nugget={}", ingotIcon != null, nuggetIcon != null);
    }

    public static IIcon iconFor(Object item, int meta) {
        if (item == null || (ingotIcon == null && nuggetIcon == null)) {
            return null;
        }
        Class<?> clazz = item.getClass();
        Map<Integer, IIcon> cache = CACHE.get(clazz);
        if (cache == null) {
            cache = new HashMap<Integer, IIcon>();
            CACHE.put(clazz, cache);
        }
        Integer key = Integer.valueOf(meta);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        IIcon icon = resolve(item, meta);
        cache.put(key, icon);
        return icon;
    }

    private static IIcon resolve(Object item, int meta) {
        if (!bind()) {
            return null;
        }
        try {
            if (getMaterial.invoke(item, Integer.valueOf(meta)) != materialCopper) {
                return null;
            }
            Object prefix = getOrePrefix.invoke(item, Integer.valueOf(meta));
            if (prefix == prefixIngot) {
                return ingotIcon;
            }
            if (prefix == prefixNugget) {
                return nuggetIcon;
            }
        } catch (Throwable t) {
            if (!reported) {
                reported = true;
                LOG.warn("GregTech copper item lookup failed", t);
            }
        }
        return null;
    }

    private static boolean bind() {
        if (bound) {
            return materialCopper != null;
        }
        bound = true;
        try {
            Class<?> materials = Class.forName("gregtech.api.enums.Materials");
            Class<?> prefixes = Class.forName("gregtech.api.enums.OrePrefixes");
            materialCopper = materials.getField("Copper").get(null);
            prefixIngot = prefixes.getField("ingot").get(null);
            prefixNugget = prefixes.getField("nugget").get(null);
            getMaterial = Class.forName("gregtech.api.items.MetaBaseItem").getMethod("getMaterial", int.class);
            getOrePrefix = Class.forName("gregtech.api.items.MetaGeneratedItemX32").getMethod("getOrePrefix", int.class);
        } catch (Throwable t) {
            LOG.warn("GregTech is not available, copper item fix stays off", t);
            materialCopper = null;
        }
        return materialCopper != null;
    }

    private static IIcon register(IIconRegister register, String name) {
        try {
            if (!PatchConditions.isTextureAvailable(
                new ResourceLocation("minecraft", "textures/items/" + name + ".png"))) {
                return null;
            }
            return register.registerIcon("minecraft:" + name);
        } catch (RuntimeException e) {
            return null;
        }
    }
}
