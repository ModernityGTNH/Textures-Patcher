package com.texturespatcher.patch;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.ResourceLocation;

import com.texturespatcher.pack.PackMeta;
import com.texturespatcher.pack.PackMetaReader;

public final class PatchConditions {

    public static final ResourceLocation JUKEBOX_BOTTOM = new ResourceLocation(
        "minecraft",
        "textures/blocks/jukebox_bottom.png");

    public static final ResourceLocation[] FORESTRY_CIRCUIT_BOARDS = new ResourceLocation[] {
        new ResourceLocation("forestry", "textures/items/circuitBoard_basic.png"),
        new ResourceLocation("forestry", "textures/items/circuitBoard_enhanced.png"),
        new ResourceLocation("forestry", "textures/items/circuitBoard_refined.png"),
        new ResourceLocation("forestry", "textures/items/circuitBoard_intricate.png") };

    public static final ResourceLocation[] FORESTRY_ELECTRON_TUBES = new ResourceLocation[] {
        new ResourceLocation("forestry", "textures/items/electronTube_copper.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_tin.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_bronze.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_iron.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_gold.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_diamond.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_obsidian.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_blaze.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_rubber.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_emerald.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_apatite.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_lapis.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_ender.png"),
        new ResourceLocation("forestry", "textures/items/electronTube_uranium.png") };

    public static final ResourceLocation[] FORESTRY_HONEY_DROPS = new ResourceLocation[] {
        new ResourceLocation("forestry", "textures/items/honeyDrop.png"), null, null };

    public static final ResourceLocation AE2_VIBRATION_CHAMBER_TOP = new ResourceLocation(
        "appliedenergistics2",
        "textures/blocks/BlockVibrationChamberTop.png");

    public static final ResourceLocation AE2_VIBRATION_CHAMBER_TOP_ON = new ResourceLocation(
        "appliedenergistics2",
        "textures/blocks/BlockVibrationChamberTopOn.png");

    public static final ResourceLocation AE2_VIBRATION_CHAMBER_BACK = new ResourceLocation(
        "appliedenergistics2",
        "textures/blocks/BlockVibrationChamberBack.png");

    public static final ResourceLocation AE2_VIBRATION_CHAMBER_BACK_ON = new ResourceLocation(
        "appliedenergistics2",
        "textures/blocks/BlockVibrationChamberBackOn.png");

    public static final ResourceLocation GC_COAL_GENERATOR_INACTIVE = gc("coalGenerator_inactive.png");

    public static final ResourceLocation GC_COAL_GENERATOR_ACTIVE = gc("coalGenerator_active.png");

    public static final ResourceLocation GC_COMPRESSOR_INACTIVE = gc("compressor_inactive.png");

    public static final ResourceLocation GC_COMPRESSOR_ACTIVE = gc("compressor_active.png");

    public static final ResourceLocation GC_ELECTRIC_COMPRESSOR_INACTIVE = gc("electric_compressor_inactive.png");

    public static final ResourceLocation GC_ELECTRIC_COMPRESSOR_ACTIVE = gc("electric_compressor_active.png");

    public static final ResourceLocation GC_DETECTOR_SIDE_INACTIVE = gc("detector_side_inactive.png");

    public static final ResourceLocation GC_DETECTOR_SIDE_ACTIVE = gc("detector_side_active.png");

    public static final ResourceLocation GC_DETECTOR_TOP_INACTIVE = gc("detector_top_inactive.png");

    public static final ResourceLocation GC_DETECTOR_TOP_ACTIVE = gc("detector_top_active.png");

    public static final ResourceLocation GC_COLLECTOR_FAN_INACTIVE = gc("machine_collector_fan_inactive.png");

    public static final ResourceLocation GC_COLLECTOR_FAN_ACTIVE = gc("machine_collector_fan_active.png");

    public static final ResourceLocation GC_DISTRIBUTOR_FAN_INACTIVE = gc("machine_distributor_fan_inactive.png");

    public static final ResourceLocation GC_DISTRIBUTOR_FAN_ACTIVE = gc("machine_distributor_fan_active.png");

    public static final ResourceLocation GC_SEALER_INACTIVE = gc("machine_sealer_inactive.png");

    public static final ResourceLocation GC_SEALER_ACTIVE = gc("machine_sealer_active.png");

    public static final ResourceLocation GC_WORKBENCH_BOTTOM = gc("workbench_nasa_bottom.png");

    public static final ResourceLocation[] GC_FUEL_LOADER_LEVELS = new ResourceLocation[] {
        gc("machine_fuelloader_0.png"), gc("machine_fuelloader_1.png"), gc("machine_fuelloader_2.png"),
        gc("machine_fuelloader_3.png"), gc("machine_fuelloader_4.png"), gc("machine_fuelloader_5.png"),
        gc("machine_fuelloader_6.png"), gc("machine_fuelloader_7.png"), gc("machine_fuelloader_8.png"),
        gc("machine_fuelloader_9.png") };

    private PatchConditions() {}

    private static ResourceLocation gc(String fileName) {
        return new ResourceLocation("galacticraftcore", "textures/blocks/" + fileName);
    }

    public static boolean shouldPatchJukebox() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null) {
            return false;
        }
        if (!isFlagEnabled(mc, PackMetaReader.FLAG_BLOCKS)) {
            return false;
        }
        return isTextureAvailable(mc, JUKEBOX_BOTTOM);
    }

    public static boolean shouldPatchForestryCircuitBoards() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc != null && isFlagEnabled(mc, PackMetaReader.FLAG_FORESTRY_CIRCUIT_BOARD);
    }

    public static boolean shouldPatchForestryElectronTubes() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc != null && isFlagEnabled(mc, PackMetaReader.FLAG_FORESTRY_ELECTRON_TUBE);
    }

    public static boolean shouldPatchForestryHoneyDrops() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc != null && isFlagEnabled(mc, PackMetaReader.FLAG_FORESTRY_HONEY_DROP);
    }

    public static boolean shouldPatchAe2VibrationChamber() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc != null && isFlagEnabled(mc, PackMetaReader.FLAG_BLOCKS);
    }

    public static boolean shouldPatchGalacticraftMachines() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc != null && isFlagEnabled(mc, PackMetaReader.FLAG_BLOCKS);
    }

    public static boolean isTextureAvailable(ResourceLocation texture) {
        Minecraft mc = Minecraft.getMinecraft();
        return mc != null && texture != null && isTextureAvailable(mc, texture);
    }


    private static boolean isTextureAvailable(Minecraft mc, ResourceLocation texture) {
        IResourceManager resourceManager = mc.getResourceManager();
        if (resourceManager != null) {
            try {
                IResource resource = resourceManager.getResource(texture);
                if (resource != null) {
                    closeQuietly(resource.getInputStream());
                    return true;
                }
            } catch (IOException ignored) {
            } catch (RuntimeException ignored) {
            }
        }
        for (IResourcePack pack : selectedResourcePacks(mc)) {
            try {
                if (pack.resourceExists(texture)) {
                    return true;
                }
            } catch (RuntimeException ignored) {
            }
        }
        return false;
    }


    private static boolean isFlagEnabled(Minecraft mc, String flagName) {
        for (IResourcePack pack : selectedResourcePacks(mc)) {
            PackMeta meta = PackMetaReader.read(pack);
            if (meta == null) {
                continue;
            }
            if (PackMetaReader.FLAG_BLOCKS.equals(flagName)) {
                if (meta.enableTexturesPatcherBlocks) {
                    return true;
                }
            } else if (PackMetaReader.FLAG_FORESTRY_CIRCUIT_BOARD.equals(flagName)) {
                if (meta.enableSeparateForestryCircuitBoardTextures) {
                    return true;
                }
            } else if (PackMetaReader.FLAG_FORESTRY_ELECTRON_TUBE.equals(flagName)) {
                if (meta.enableSeparateForestryElectronTubeTextures) {
                    return true;
                }
            } else if (PackMetaReader.FLAG_FORESTRY_HONEY_DROP.equals(flagName)) {
                if (meta.enableSeparateForestryHoneyDropTextures) {
                    return true;
                }
            }
        }
        return false;
    }


    private static List<IResourcePack> selectedResourcePacks(Minecraft mc) {
        List<IResourcePack> packs = new ArrayList<IResourcePack>();
        ResourcePackRepository repository = mc.getResourcePackRepository();
        if (repository == null) {
            return packs;
        }
        List<?> entries;
        try {
            entries = repository.getRepositoryEntries();
        } catch (RuntimeException e) {
            return packs;
        }
        if (entries == null) {
            return packs;
        }
        for (Object entry : entries) {
            if (!(entry instanceof ResourcePackRepository.Entry)) {
                continue;
            }
            try {
                IResourcePack pack = ((ResourcePackRepository.Entry) entry).getResourcePack();
                if (pack != null) {
                    packs.add(pack);
                }
            } catch (RuntimeException ignored) {
            }
        }
        return packs;
    }

    private static void closeQuietly(InputStream stream) {
        if (stream == null) {
            return;
        }
        try {
            stream.close();
        } catch (IOException ignored) {
        }
    }
}
