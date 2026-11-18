package com.texturespatcher.patch;

import java.util.Arrays;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ForestryBoardTextures {

    // icon name -> assets/forestry/textures/items/<name>.png, index = item meta
    private static final String[] ICON_NAMES = new String[] { "circuitBoard_basic", "circuitBoard_enhanced",
        "circuitBoard_refined", "circuitBoard_intricate" };

    private static final IIcon[] ICONS = new IIcon[ICON_NAMES.length];

    private static final Logger LOG = LogManager.getLogger("TexturesPatcher");

    private static int lastPatchedCount = -1;

    private ForestryBoardTextures() {}

    public static void onRegisterIcons(IIconRegister register) {
        Arrays.fill(ICONS, null);

        if (register == null || !PatchConditions.shouldPatchForestryCircuitBoards()) {
            logTransition(0, false);
            return;
        }

        int patched = 0;
        for (int meta = 0; meta < ICON_NAMES.length; meta++) {
            if (!PatchConditions.isTextureAvailable(PatchConditions.FORESTRY_CIRCUIT_BOARDS[meta])) {
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
            LOG.info("Forestry circuit board fix is turned off");
        } else if (patched == 0) {
            LOG.warn("enableSeparateForestryCircuitBoardTexture is claimed, but no circuitBoard_*.png found");
        } else {
            LOG.info(
                "Forestry circuit board fix turned on: {}/{} boards use pack textures",
                patched,
                ICON_NAMES.length);
        }
    }
}
