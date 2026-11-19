package com.texturespatcher.patch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityList;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SpawnEggTextures {

    public static final String ICON_SUFFIX = "_spawn_egg";

    private static final Logger LOG = LogManager.getLogger("TexturesPatcher");

    private static final Map<String, String> ENTITY_NAMES = new HashMap<String, String>();

    private static final Map<String, String> MOD_NAMES = new HashMap<String, String>();

    private static final Map<Class<?>, IIcon> CLASS_ICONS = new HashMap<Class<?>, IIcon>();

    private static final Map<String, IIcon> NAME_ICONS = new HashMap<String, IIcon>();

    private static final Map<Integer, IIcon> ICONS = new HashMap<Integer, IIcon>();

    private static final Set<Integer> MISSES = new HashSet<Integer>();

    private static boolean ready;

    static {
        ENTITY_NAMES.put("EntityBat", "bat");
        ENTITY_NAMES.put("EntityBlaze", "blaze");
        ENTITY_NAMES.put("EntityCaveSpider", "cave_spider");
        ENTITY_NAMES.put("EntityChicken", "chicken");
        ENTITY_NAMES.put("EntityCow", "cow");
        ENTITY_NAMES.put("EntityCreeper", "creeper");
        ENTITY_NAMES.put("EntityEnderman", "enderman");
        ENTITY_NAMES.put("EntityGhast", "ghast");
        ENTITY_NAMES.put("EntityHorse", "horse");
        ENTITY_NAMES.put("EntityMagmaCube", "magma_cube");
        ENTITY_NAMES.put("EntityMooshroom", "mooshroom");
        ENTITY_NAMES.put("EntityOcelot", "ocelot");
        ENTITY_NAMES.put("EntityPig", "pig");
        ENTITY_NAMES.put("EntitySheep", "sheep");
        ENTITY_NAMES.put("EntitySilverfish", "silverfish");
        ENTITY_NAMES.put("EntitySkeleton", "skeleton");
        ENTITY_NAMES.put("EntitySlime", "slime");
        ENTITY_NAMES.put("EntitySpider", "spider");
        ENTITY_NAMES.put("EntitySquid", "squid");
        ENTITY_NAMES.put("EntityVillager", "villager");
        ENTITY_NAMES.put("EntityWitch", "witch");
        ENTITY_NAMES.put("EntityWolf", "wolf");
        ENTITY_NAMES.put("EntityPigZombie", "zombified_piglin");
        ENTITY_NAMES.put("EntityZombie", "zombie");

        ENTITY_NAMES.put("EntityHusk", "husk");
        ENTITY_NAMES.put("EntityEndermite", "endermite");
        ENTITY_NAMES.put("EntityFox", "fox");
        ENTITY_NAMES.put("EntityShulker", "shulker");
        ENTITY_NAMES.put("EntityRabbit", "rabbit");
        ENTITY_NAMES.put("EntityZombieVillager", "zombie_villager");
        ENTITY_NAMES.put("EntityStray", "stray");
        ENTITY_NAMES.put("EntityIronGolem", "iron_golem");
        ENTITY_NAMES.put("EntitySnowman", "snow_golem");
        ENTITY_NAMES.put("EntityNewSnowGolem", "snow_golem");

        MOD_NAMES.put("EntityMobEnderman", "hee_enderman");
        MOD_NAMES.put("EntityVillagerWere", "witchery_villager");
    }

    private SpawnEggTextures() {}

    public static void onRegisterIcons(IIconRegister register) {
        ready = false;
        ICONS.clear();
        MISSES.clear();
        CLASS_ICONS.clear();
        NAME_ICONS.clear();
        if (register == null) {
            return;
        }
        boolean shouldPatch;
        try {
            shouldPatch = PatchConditions.shouldPatchSpawnEggs();
        } catch (Throwable t) {
            LOG.warn("ResourcePack status error, not gonna fix spawn eggs!", t);
            return;
        }
        if (!shouldPatch) {
            return;
        }
        try {
            preload(register);
        } catch (Throwable t) {
            LOG.warn("Spawn egg icon registration failed", t);
        }
        ready = true;
        report();
    }

    public static IIcon icon(int damage) {
        if (!ready) {
            return null;
        }
        Integer key = Integer.valueOf(damage);
        IIcon cached = ICONS.get(key);
        if (cached != null) {
            return cached;
        }
        if (MISSES.contains(key)) {
            return null;
        }
        IIcon resolved = null;
        try {
            resolved = resolve(key.intValue());
        } catch (Throwable t) {
            LOG.warn("Spawn egg icon lookup failed for damage {}", key, t);
        }
        if (resolved == null) {
            MISSES.add(key);
            return null;
        }
        ICONS.put(key, resolved);
        return resolved;
    }

    public static boolean isActive() {
        return ready && !ICONS.isEmpty();
    }

    private static void preload(IIconRegister register) {
        LinkedHashSet<String> names = new LinkedHashSet<String>(ENTITY_NAMES.values());
        names.addAll(MOD_NAMES.values());
        for (String name : names) {
            registerName(name, register);
        }
        for (Object value : EntityList.IDtoClassMapping.values()) {
            if (!(value instanceof Class)) {
                continue;
            }
            Class<?> clazz = (Class<?>) value;
            if (CLASS_ICONS.containsKey(clazz)) {
                continue;
            }
            CLASS_ICONS.put(clazz, iconFor(clazz));
        }
    }

    private static IIcon registerName(String name, IIconRegister register) {
        IIcon known = NAME_ICONS.get(name);
        if (known != null) {
            return known;
        }
        if (!hasTexture(name)) {
            return null;
        }
        try {
            IIcon icon = register.registerIcon(name + ICON_SUFFIX);
            if (icon != null) {
                NAME_ICONS.put(name, icon);
                return icon;
            }
        } catch (RuntimeException ignored) {}
        return null;
    }

    private static IIcon iconFor(Class<?> clazz) {
        String name = nameOf(clazz);
        return name == null ? null : NAME_ICONS.get(name);
    }

    private static String nameOf(Class<?> clazz) {
        String simple = clazz.getSimpleName();
        String name = MOD_NAMES.get(simple);
        if (name != null) {
            return name;
        }
        return ENTITY_NAMES.get(simple);
    }

    private static IIcon resolve(int damage) {
        Object info = EntityList.entityEggs.get(Integer.valueOf(damage));
        if (!(info instanceof EntityList.EntityEggInfo)) {
            return null;
        }
        Object value = EntityList.IDtoClassMapping.get(Integer.valueOf(((EntityList.EntityEggInfo) info).spawnedID));
        if (!(value instanceof Class)) {
            return null;
        }
        Class<?> clazz = (Class<?>) value;
        IIcon icon = iconFor(clazz);
        if (icon != null) {
            CLASS_ICONS.put(clazz, icon);
        }
        return icon;
    }

    private static void report() {
        int patched = 0;
        for (Object key : EntityList.entityEggs.keySet()) {
            if (!(key instanceof Integer)) {
                continue;
            }
            Object info = EntityList.entityEggs.get(key);
            if (!(info instanceof EntityList.EntityEggInfo)) {
                continue;
            }
            Object value = EntityList.IDtoClassMapping
                .get(Integer.valueOf(((EntityList.EntityEggInfo) info).spawnedID));
            if (!(value instanceof Class)) {
                continue;
            }
            if (iconFor((Class<?>) value) != null) {
                patched++;
            }
        }
        if (patched == 0) {
            LOG.warn("enableSeparateSpawnEggTextures is claimed, but no spawn egg textures found");
        } else {
            LOG.info("Spawn egg texture fix turned on: {} eggs", patched);
        }
        List<String> missing = new ArrayList<String>();
        for (String name : new LinkedHashSet<String>(MOD_NAMES.values())) {
            if (!NAME_ICONS.containsKey(name)) {
                missing.add(name);
            }
        }
        for (String name : missing) {
            LOG.info("Spawn egg texture missing: {}", name);
        }
    }

    private static boolean hasTexture(String name) {
        try {
            return PatchConditions.isTextureAvailable(
                new ResourceLocation("minecraft", "textures/items/" + name + ICON_SUFFIX + ".png"));
        } catch (RuntimeException e) {
            return false;
        }
    }
}
