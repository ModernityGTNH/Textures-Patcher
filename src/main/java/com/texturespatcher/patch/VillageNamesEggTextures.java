package com.texturespatcher.patch;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class VillageNamesEggTextures {

    public static final String GUARDIAN = "villiage_names_guardian";

    public static final String ELDER_GUARDIAN = "villiage_names_elder_guardian";

    private static final String ICON_SUFFIX = "_spawn_egg";

    private static final int GUARDIAN_DAMAGE = 1;

    private static final int ELDER_GUARDIAN_DAMAGE = 2;

    private static final Logger LOG = LogManager.getLogger("TexturesPatcher");

    private static IIcon guardianIcon;

    private static IIcon elderGuardianIcon;

    private static boolean ready;

    private VillageNamesEggTextures() {}

    public static void onRegisterIcons(IIconRegister register) {
        guardianIcon = null;
        elderGuardianIcon = null;
        ready = false;
        if (register == null) {
            return;
        }
        boolean shouldPatch;
        try {
            shouldPatch = PatchConditions.shouldPatchSpawnEggs();
        } catch (Throwable t) {
            LOG.warn("ResourcePack status error, not gonna fix Village Names spawn eggs!", t);
            return;
        }
        if (!shouldPatch) {
            return;
        }
        guardianIcon = register(register, GUARDIAN);
        elderGuardianIcon = register(register, ELDER_GUARDIAN);
        ready = guardianIcon != null || elderGuardianIcon != null;
        if (ready) {
            LOG.info(
                "Village Names spawn egg fix turned on: guardian={}, elder_guardian={}",
                guardianIcon != null,
                elderGuardianIcon != null);
        }
        if (guardianIcon == null) {
            LOG.info("Spawn egg texture missing: {}", GUARDIAN);
        }
        if (elderGuardianIcon == null) {
            LOG.info("Spawn egg texture missing: {}", ELDER_GUARDIAN);
        }
    }

    public static IIcon icon(int damage) {
        if (!ready) {
            return null;
        }
        if (damage == ELDER_GUARDIAN_DAMAGE) {
            return elderGuardianIcon;
        }
        if (damage == GUARDIAN_DAMAGE) {
            return guardianIcon;
        }
        return null;
    }

    private static IIcon register(IIconRegister register, String name) {
        try {
            if (!PatchConditions.isTextureAvailable(
                new ResourceLocation("minecraft", "textures/items/" + name + ICON_SUFFIX + ".png"))) {
                return null;
            }
            return register.registerIcon("minecraft:" + name + ICON_SUFFIX);
        } catch (RuntimeException e) {
            return null;
        }
    }
}
