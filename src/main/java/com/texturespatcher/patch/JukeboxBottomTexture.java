package com.texturespatcher.patch;

import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.texture.IIconRegister;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class JukeboxBottomTexture {

    public static final String BOTTOM_ICON_NAME = "jukebox_bottom";

    private static final Logger LOG = LogManager.getLogger("TexturesPatcher");

    private static IIcon bottomIcon;
    private static boolean patched;

    private JukeboxBottomTexture() {}

    // invoked by mixin at the end of {@code BlockJukebox#registerBlockIcons}
    public static void onRegisterBlockIcons(IIconRegister register) {
        IIcon previous = bottomIcon;
        bottomIcon = null;
        patched = false;

        boolean shouldPatch;
        try {
            shouldPatch = PatchConditions.shouldPatchJukebox();
        } catch (Throwable t) {
            LOG.warn("ResourcePack status error, not gonna fix!", t);
            return;
        }

        if (!shouldPatch) {
            if (previous != null) {
                LOG.info("jukebox bottom fix is turned off");
            }
            return;
        }
        if (register == null) {
            return;
        }

        IIcon icon = register.registerIcon(BOTTOM_ICON_NAME);
        if (icon == null) {
            LOG.warn("textures_patcher is claimed, but jukebox_bottom.png is not existed. fix turned off");
            return;
        }

        bottomIcon = icon;
        patched = true;
        if (previous == null) {
            LOG.info("Jukebox Bottom Fix turned on.");
        }
    }

    public static IIcon getBottomIcon() {
        return bottomIcon;
    }

    public static boolean isPatched() {
        return patched;
    }
}
