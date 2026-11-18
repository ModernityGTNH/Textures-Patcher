package com.texturespatcher.patch;

import java.util.Arrays;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ForestryTubeTextures {

    // icon name -> assets/forestry/textures/items/<name>.png, index = item meta (EnumElectronTube ordinal)
    private static final String[] ICON_NAMES = new String[] { "electronTube_copper", "electronTube_tin",
        "electronTube_bronze", "electronTube_iron", "electronTube_gold", "electronTube_diamond",
        "electronTube_obsidian", "electronTube_blaze", "electronTube_rubber", "electronTube_emerald",
        "electronTube_apatite", "electronTube_lapis", "electronTube_ender", "electronTube_uranium" };

    private static final IIcon[] ICONS = new IIcon[PatchConditions.FORESTRY_ELECTRON_TUBES.length];

    private static final Logger LOG = LogManager.getLogger("TexturesPatcher");

    private static int lastPatchedCount = -1;

    private ForestryTubeTextures() {}


    public static void onRegisterIcons(IIconRegister register) {
        Arrays.fill(ICONS, null);

        if (register == null || !PatchConditions.shouldPatchForestryElectronTubes()) {
            logTransition(0, false);
            return;
        }

        int patched = 0;
        for (int meta = 0; meta < ICON_NAMES.length && meta < ICONS.length; meta++) {
            if (!PatchConditions.isTextureAvailable(PatchConditions.FORESTRY_ELECTRON_TUBES[meta])) {
                continue;
            }
            IIcon icon = register.registerIcon("forestry:" + ICON_NAMES[meta]);
            if (icon != null) {
                ICONS[meta] = icon;
                patched++;
            }
        }
        logTransition(patched, true);
    }

    public static IIcon getIcon(int meta) {
        if (meta < 0 || meta >= ICONS.length) {
            return null;
        }
        return ICONS[meta];
    }

    public static boolean isPatched(int meta) {
        return getIcon(meta) != null;
    }

    private static void logTransition(int patched, boolean enabled) {
        if (patched == lastPatchedCount) {
            return;
        }
        lastPatchedCount = patched;
        if (!enabled) {
            LOG.info("Forestry electron tube fix is turned off");
        } else if (patched == 0) {
            LOG.warn("enableSeparateForestryElectronTubeTextures is claimed, but no electronTube_*.png found");
        } else {
            LOG.info(
                "Forestry electron tube fix turned on: {}/{} tubes use pack textures",
                patched,
                ICON_NAMES.length);
        }
    }
}
