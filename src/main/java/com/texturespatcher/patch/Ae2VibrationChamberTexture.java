package com.texturespatcher.patch;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Ae2VibrationChamberTexture {

    private static final String ICON_TOP = "appliedenergistics2:BlockVibrationChamberTop";

    private static final String ICON_TOP_ON = "appliedenergistics2:BlockVibrationChamberTopOn";

    private static final String ICON_BACK_ON = "appliedenergistics2:BlockVibrationChamberBackOn";

    private static final Logger LOG = LogManager.getLogger("TexturesPatcher");

    private static IIcon topIcon;

    private static IIcon topOnIcon;

    private static IIcon backOnIcon;

    private static boolean backAvailable;

    private static boolean enabled;

    private static int lastState = -1;

    private Ae2VibrationChamberTexture() {}

    // invoked by mixin at the end of {@code AEBaseBlock#registerBlockIcons}, vibration chamber only
    public static void onRegisterIcons(IIconRegister register) {
        topIcon = null;
        topOnIcon = null;
        backOnIcon = null;
        backAvailable = false;
        enabled = false;

        if (register == null || !PatchConditions.shouldPatchAe2VibrationChamber()) {
            logTransition();
            return;
        }

        enabled = true;
        if (PatchConditions.isTextureAvailable(PatchConditions.AE2_VIBRATION_CHAMBER_TOP)) {
            topIcon = register.registerIcon(ICON_TOP);
        }
        if (PatchConditions.isTextureAvailable(PatchConditions.AE2_VIBRATION_CHAMBER_TOP_ON)) {
            topOnIcon = register.registerIcon(ICON_TOP_ON);
        }
        backAvailable = PatchConditions.isTextureAvailable(PatchConditions.AE2_VIBRATION_CHAMBER_BACK);
        if (backAvailable && PatchConditions.isTextureAvailable(PatchConditions.AE2_VIBRATION_CHAMBER_BACK_ON)) {
            backOnIcon = register.registerIcon(ICON_BACK_ON);
        }
        logTransition();
    }

    public static IIcon getTopIcon(boolean running) {
        if (!enabled) {
            return null;
        }
        if (running && topOnIcon != null) {
            return topOnIcon;
        }
        return topIcon;
    }


    public static IIcon getBackIcon(boolean running) {
        if (!enabled || !backAvailable) {
            return null;
        }
        return running ? backOnIcon : null;
    }

    private static void logTransition() {
        int state = enabled ? (topIcon != null ? 1 : 0) + (topOnIcon != null ? 2 : 0)
            + (backAvailable ? 4 : 0) + (backOnIcon != null ? 8 : 0) : -1;
        if (state == lastState) {
            return;
        }
        lastState = state;
        if (!enabled) {
            LOG.info("AE2 vibration chamber texture fix is turned off");
        } else if (topIcon == null && topOnIcon == null && !backAvailable) {
            LOG.warn("enableTexturesPatcherBlocks is claimed, but no BlockVibrationChamber{Top,Back}*.png found");
        } else {
            LOG.info(
                "AE2 vibration chamber texture fix turned on: top idle={}, top running={}, back idle={}, back running={}",
                topIcon != null,
                topOnIcon != null,
                backAvailable,
                backOnIcon != null);
        }
    }
}
