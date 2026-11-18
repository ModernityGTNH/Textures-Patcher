package com.texturespatcher.patch;

import java.util.Arrays;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import forestry.core.utils.StringUtil;

public final class ForestryHoneyDropTextures {

    // icon name -> assets/forestry/textures/items/<name>.png, index = item meta (EnumHoneyDrop ordinal)
    private static final String[] ICON_NAMES = new String[] { "honeyDrop", null, null };

    private static final IIcon[] ICONS = new IIcon[PatchConditions.FORESTRY_HONEY_DROPS.length];

    private static final Logger LOG = LogManager.getLogger("TexturesPatcher");

    private static final String ITEM_NAME = "honeyDrop";

    private static int lastPatchedCount = -1;

    private ForestryHoneyDropTextures() {}

    public static boolean matches(Item item) {
        if (item == null) {
            return false;
        }
        try {
            return ITEM_NAME.equals(StringUtil.cleanItemName(item));
        } catch (RuntimeException e) {
            return false;
        }
    }

    public static void onRegisterIcons(IIconRegister register) {
        Arrays.fill(ICONS, null);

        if (register == null || !PatchConditions.shouldPatchForestryHoneyDrops()) {
            logTransition(0, false);
            return;
        }

        int patched = 0;
        for (int meta = 0; meta < ICON_NAMES.length && meta < ICONS.length; meta++) {
            if (ICON_NAMES[meta] == null) {
                continue;
            }
            if (!PatchConditions.isTextureAvailable(PatchConditions.FORESTRY_HONEY_DROPS[meta])) {
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
            LOG.info("Forestry honey drop fix is turned off");
        } else if (patched == 0) {
            LOG.warn("enableSeparateForestryHoneyDropTextures is claimed, but honeyDrop.png not found");
        } else {
            LOG.info("Forestry honey drop fix turned on: {}/{} drops use pack textures", patched, ICON_NAMES.length);
        }
    }
}
