package com.texturespatcher.patch;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.fluids.FluidTank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class GalacticraftMachineTextures {

    private static final int KIND_DETECTOR = 1;

    private static final int KIND_COLLECTOR = 2;

    private static final int KIND_DISTRIBUTOR = 3;

    private static final int KIND_SEALER = 4;

    private static final int KIND_FUEL_LOADER = 5;

    private static final int KIND_WORKBENCH = 6;

    private static final String TILE_COAL_GENERATOR = "micdoodle8.mods.galacticraft.core.tile.TileEntityCoalGenerator";

    private static final String TILE_INGOT_COMPRESSOR = "micdoodle8.mods.galacticraft.core.tile.TileEntityIngotCompressor";

    private static final String TILE_ELECTRIC_INGOT_COMPRESSOR = "micdoodle8.mods.galacticraft.core.tile.TileEntityElectricIngotCompressor";

    private static final String TILE_OXYGEN_COLLECTOR = "micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenCollector";

    private static final String TILE_OXYGEN_DISTRIBUTOR = "micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenDistributor";

    private static final String TILE_OXYGEN_SEALER = "micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenSealer";

    private static final String TILE_FUEL_LOADER = "micdoodle8.mods.galacticraft.core.tile.TileEntityFuelLoader";

    private static final String DOMAIN = "galacticraftcore:";


    private static final String VANILLA_COAL_GENERATOR = DOMAIN + "coalGenerator";

    private static final String VANILLA_COMPRESSOR = DOMAIN + "compressor";

    private static final String VANILLA_ELECTRIC_COMPRESSOR = DOMAIN + "electric_compressor";

    private static final String VANILLA_DETECTOR_SIDE = DOMAIN + "detector_side";

    private static final String VANILLA_MACHINE_BLANK = DOMAIN + "machine_blank";

    private static final String VANILLA_COLLECTOR_FAN = DOMAIN + "machine_collector_fan";

    private static final String VANILLA_DISTRIBUTOR_FAN = DOMAIN + "machine_distributor_fan";

    private static final String VANILLA_SEALER = DOMAIN + "machine_sealer";

    private static final String VANILLA_FUEL_LOADER = DOMAIN + "machine_fuelloader";

    private static final Logger LOG = LogManager.getLogger("TexturesPatcher");

    private static final Map<String, Integer> LAST_STATE = new HashMap<String, Integer>();

    private static final Map<Class<?>, Field> FIELD_CACHE = new HashMap<Class<?>, Field>();


    private static final Map<TileEntity, Integer> LAST_FUEL_LEVEL = new WeakHashMap<TileEntity, Integer>();


    private static final Map<Block, Integer> MACHINES = new IdentityHashMap<Block, Integer>();

    private static Pair coalGeneratorIcons;

    private static Pair compressorIcons;

    private static Pair electricCompressorIcons;

    private static Pair detectorSideIcons;

    private static Pair detectorTopIcons;

    private static Pair collectorFanIcons;

    private static Pair distributorFanIcons;

    private static Pair sealerIcons;

    private static final IIcon[] FUEL_LOADER_ICONS = new IIcon[PatchConditions.GC_FUEL_LOADER_LEVELS.length];

    private static IIcon workbenchBottomIcon;

    private GalacticraftMachineTextures() {}


    private static final class Pair {

        private IIcon idle;

        private IIcon active;
    }


    // The following onRegisterXxx will be invoked at the end of corresponding blocks' registerBlockIcons mixin
    public static void onRegisterBlockMachine(IIconRegister register) {
        coalGeneratorIcons = registerPair(
            register,
            "coalGenerator",
            PatchConditions.GC_COAL_GENERATOR_INACTIVE,
            PatchConditions.GC_COAL_GENERATOR_ACTIVE);
        compressorIcons = registerPair(
            register,
            "compressor",
            PatchConditions.GC_COMPRESSOR_INACTIVE,
            PatchConditions.GC_COMPRESSOR_ACTIVE);
        logState("coal generator", coalGeneratorIcons);
        logState("compressor", compressorIcons);
    }

    public static void onRegisterBlockMachine2(IIconRegister register) {
        electricCompressorIcons = registerPair(
            register,
            "electric_compressor",
            PatchConditions.GC_ELECTRIC_COMPRESSOR_INACTIVE,
            PatchConditions.GC_ELECTRIC_COMPRESSOR_ACTIVE);
        logState("electric compressor", electricCompressorIcons);
    }

    public static void onRegisterOxygenDetector(Block block, IIconRegister register) {
        detectorSideIcons = registerPair(
            register,
            "detector_side",
            PatchConditions.GC_DETECTOR_SIDE_INACTIVE,
            PatchConditions.GC_DETECTOR_SIDE_ACTIVE);
        detectorTopIcons = registerPair(
            register,
            "detector_top",
            PatchConditions.GC_DETECTOR_TOP_INACTIVE,
            PatchConditions.GC_DETECTOR_TOP_ACTIVE);
        int patched = countIcons(detectorSideIcons) + countIcons(detectorTopIcons);
        logState("oxygen detector", PatchConditions.shouldPatchGalacticraftMachines(), patched, 4);

        bind(block, KIND_DETECTOR, patched > 0);
    }

    public static void onRegisterOxygenCollector(Block block, IIconRegister register) {
        collectorFanIcons = registerPair(
            register,
            "machine_collector_fan",
            PatchConditions.GC_COLLECTOR_FAN_INACTIVE,
            PatchConditions.GC_COLLECTOR_FAN_ACTIVE);
        logState("oxygen collector", collectorFanIcons);
        bind(block, KIND_COLLECTOR, collectorFanIcons != null);
    }

    public static void onRegisterOxygenDistributor(Block block, IIconRegister register) {
        distributorFanIcons = registerPair(
            register,
            "machine_distributor_fan",
            PatchConditions.GC_DISTRIBUTOR_FAN_INACTIVE,
            PatchConditions.GC_DISTRIBUTOR_FAN_ACTIVE);
        logState("oxygen bubble distributor", distributorFanIcons);
        bind(block, KIND_DISTRIBUTOR, distributorFanIcons != null);
    }

    public static void onRegisterOxygenSealer(Block block, IIconRegister register) {
        sealerIcons = registerPair(
            register,
            "machine_sealer",
            PatchConditions.GC_SEALER_INACTIVE,
            PatchConditions.GC_SEALER_ACTIVE);
        logState("oxygen sealer", sealerIcons);
        bind(block, KIND_SEALER, sealerIcons != null);
    }

    public static void onRegisterFuelLoader(Block block, IIconRegister register) {
        boolean enabled = register != null && PatchConditions.shouldPatchGalacticraftMachines();
        int patched = 0;
        for (int level = 0; level < FUEL_LOADER_ICONS.length; level++) {
            FUEL_LOADER_ICONS[level] = null;
            if (!enabled || !PatchConditions.isTextureAvailable(PatchConditions.GC_FUEL_LOADER_LEVELS[level])) {
                continue;
            }
            FUEL_LOADER_ICONS[level] = register.registerIcon(DOMAIN + "machine_fuelloader_" + level);
            if (FUEL_LOADER_ICONS[level] != null) {
                patched++;
            }
        }
        logState("fuel loader", enabled, patched, FUEL_LOADER_ICONS.length);
        bind(block, KIND_FUEL_LOADER, patched > 0);
    }

    public static void onRegisterNasaWorkbench(Block block, IIconRegister register) {
        workbenchBottomIcon = null;
        boolean enabled = register != null && PatchConditions.shouldPatchGalacticraftMachines();
        if (enabled && PatchConditions.isTextureAvailable(PatchConditions.GC_WORKBENCH_BOTTOM)) {
            workbenchBottomIcon = register.registerIcon(DOMAIN + "workbench_nasa_bottom");
        }
        logState("NASA workbench", enabled, workbenchBottomIcon != null ? 1 : 0, 1);
        bind(block, KIND_WORKBENCH, workbenchBottomIcon != null);
    }


    public static IIcon worldIcon(Block block, IBlockAccess world, int x, int y, int z, int side) {
        if (MACHINES.isEmpty() || block == null || world == null) {
            return null;
        }
        Integer kind = MACHINES.get(block);
        if (kind == null) {
            return null;
        }
        try {
            return resolve(kind.intValue(), block, world, x, y, z, side);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public static IIcon itemIcon(Block block, int side, IIcon original) {
        if (MACHINES.isEmpty() || block == null) {
            return null;
        }
        Integer kind = MACHINES.get(block);
        if (kind == null) {
            return null;
        }
        switch (kind.intValue()) {
            case KIND_DETECTOR:
                return side == 1 ? detectorTop(original, false) : detectorSide(original, false);
            case KIND_COLLECTOR:
                return collectorFan(original, false);
            case KIND_DISTRIBUTOR:
                return distributorFan(original, false);
            case KIND_SEALER:
                return sealer(original, false);
            case KIND_FUEL_LOADER:
                return fuelLoader(original, 0);
            case KIND_WORKBENCH:
                return side == 0 ? workbenchBottomIcon : null;
            default:
                return null;
        }
    }

    private static IIcon resolve(int kind, Block block, IBlockAccess world, int x, int y, int z, int side) {
        int meta = world.getBlockMetadata(x, y, z);
        IIcon original = block.getIcon(side, meta);
        TileEntity tile = world.getTileEntity(x, y, z);
        switch (kind) {
            case KIND_DETECTOR:
                return side == 1 ? detectorTop(original, (meta & 1) != 0) : detectorSide(original, (meta & 1) != 0);
            case KIND_COLLECTOR:
                return collectorFan(original, collectorRunning(tile));
            case KIND_DISTRIBUTOR:
                return distributorFan(original, distributorRunning(tile));
            case KIND_SEALER:
                return sealer(original, sealerRunning(tile));
            case KIND_FUEL_LOADER: {
                int level = fuelLoaderLevel(tile);
                return level < 0 ? null : fuelLoader(original, level);
            }
            case KIND_WORKBENCH:
                return side == 0 ? workbenchBottomIcon : null;
            default:
                return null;
        }
    }

    private static void bind(Block block, int kind, boolean active) {
        if (block == null) {
            return;
        }
        if (active) {
            MACHINES.put(block, Integer.valueOf(kind));
        } else {
            MACHINES.remove(block);
        }
    }


    public static IIcon coalGenerator(IIcon original, boolean running) {
        return pick(coalGeneratorIcons, running, original, VANILLA_COAL_GENERATOR);
    }

    public static IIcon compressor(IIcon original, boolean running) {
        return pick(compressorIcons, running, original, VANILLA_COMPRESSOR);
    }

    public static IIcon electricCompressor(IIcon original, boolean running) {
        return pick(electricCompressorIcons, running, original, VANILLA_ELECTRIC_COMPRESSOR);
    }

    public static IIcon detectorSide(IIcon original, boolean active) {
        return pick(detectorSideIcons, active, original, VANILLA_DETECTOR_SIDE);
    }

    public static IIcon detectorTop(IIcon original, boolean active) {
        return pick(detectorTopIcons, active, original, VANILLA_MACHINE_BLANK);
    }

    public static IIcon collectorFan(IIcon original, boolean running) {
        return pick(collectorFanIcons, running, original, VANILLA_COLLECTOR_FAN);
    }

    public static IIcon distributorFan(IIcon original, boolean running) {
        return pick(distributorFanIcons, running, original, VANILLA_DISTRIBUTOR_FAN);
    }

    public static IIcon sealer(IIcon original, boolean running) {
        return pick(sealerIcons, running, original, VANILLA_SEALER);
    }

    

    public static IIcon fuelLoader(IIcon original, int level) {
        if (!isFuelLoaderIcon(original) || level < 0 || level >= FUEL_LOADER_ICONS.length) {
            return null;
        }
        return FUEL_LOADER_ICONS[level];
    }

    private static boolean isFuelLoaderIcon(IIcon icon) {
        if (isIcon(icon, VANILLA_FUEL_LOADER)) {
            return true;
        }
        for (int level = 0; level < FUEL_LOADER_ICONS.length; level++) {
            if (icon != null && icon == FUEL_LOADER_ICONS[level]) {
                return true;
            }
        }
        return false;
    }


    public static boolean coalGeneratorRunning(TileEntity tile) {
        return intField(tile, TILE_COAL_GENERATOR, "itemCookTime") > 0;
    }

    public static boolean compressorRunning(TileEntity tile) {
        return intField(tile, TILE_INGOT_COMPRESSOR, "processTicks") > 0;
    }

    public static boolean electricCompressorRunning(TileEntity tile) {
        return intField(tile, TILE_ELECTRIC_INGOT_COMPRESSOR, "processTicks") > 0;
    }

    public static boolean collectorRunning(TileEntity tile) {
        return floatField(tile, TILE_OXYGEN_COLLECTOR, "lastOxygenCollected") > 0.0F;
    }

    public static boolean distributorRunning(TileEntity tile) {
        return floatField(tile, TILE_OXYGEN_DISTRIBUTOR, "bubbleSize") >= 1.0F;
    }

    public static boolean sealerRunning(TileEntity tile) {
        return booleanField(tile, TILE_OXYGEN_SEALER, "active");
    }

    public static int fuelLoaderLevel(TileEntity tile) {
        if (!isTile(tile, TILE_FUEL_LOADER)) {
            return -1;
        }
        Field field = fieldOf(tile.getClass(), "fuelTank");
        if (field == null) {
            return -1;
        }
        try {
            Object tank = field.get(tile);
            if (!(tank instanceof FluidTank) || ((FluidTank) tank).getCapacity() <= 0) {
                return -1;
            }
            FluidTank fluidTank = (FluidTank) tank;

            return Math.min(FUEL_LOADER_ICONS.length - 1, fluidTank.getFluidAmount() * FUEL_LOADER_ICONS.length
                / fluidTank.getCapacity());
        } catch (IllegalAccessException ignored) {
            return -1;
        }
    }

    public static boolean fuelLoaderLevelChanged(TileEntity tile) {
        int level = fuelLoaderLevel(tile);
        if (level < 0) {
            return false;
        }
        Integer last = LAST_FUEL_LEVEL.get(tile);
        if (last != null && last.intValue() == level) {
            return false;
        }
        LAST_FUEL_LEVEL.put(tile, Integer.valueOf(level));
        return true;
    }

    private static boolean isTile(TileEntity tile, String className) {
        return tile != null && tile.getClass()
            .getName()
            .equals(className);
    }

    private static Field fieldOf(Class<?> clazz, String name) {
        if (FIELD_CACHE.containsKey(clazz)) {
            return FIELD_CACHE.get(clazz);
        }
        Field field = null;
        try {
            field = clazz.getField(name);
        } catch (NoSuchFieldException ignored) {
        }
        FIELD_CACHE.put(clazz, field);
        return field;
    }

    private static int intField(TileEntity tile, String simpleName, String fieldName) {
        if (!isTile(tile, simpleName)) {
            return 0;
        }
        Field field = fieldOf(tile.getClass(), fieldName);
        if (field == null) {
            return 0;
        }
        try {
            return field.getInt(tile);
        } catch (IllegalAccessException ignored) {
            return 0;
        }
    }

    private static float floatField(TileEntity tile, String simpleName, String fieldName) {
        if (!isTile(tile, simpleName)) {
            return 0.0F;
        }
        Field field = fieldOf(tile.getClass(), fieldName);
        if (field == null) {
            return 0.0F;
        }
        try {
            return field.getFloat(tile);
        } catch (IllegalAccessException ignored) {
            return 0.0F;
        }
    }

    private static boolean booleanField(TileEntity tile, String simpleName, String fieldName) {
        if (!isTile(tile, simpleName)) {
            return false;
        }
        Field field = fieldOf(tile.getClass(), fieldName);
        if (field == null) {
            return false;
        }
        try {
            return field.getBoolean(tile);
        } catch (IllegalAccessException ignored) {
            return false;
        }
    }

    private static Pair registerPair(IIconRegister register, String iconBaseName, ResourceLocation idle,
        ResourceLocation active) {
        if (register == null || !PatchConditions.shouldPatchGalacticraftMachines()) {
            return null;
        }
        Pair pair = new Pair();
        if (PatchConditions.isTextureAvailable(idle)) {
            pair.idle = register.registerIcon(DOMAIN + iconBaseName + "_inactive");
        }
        if (PatchConditions.isTextureAvailable(active)) {
            pair.active = register.registerIcon(DOMAIN + iconBaseName + "_active");
        }
        return pair.idle == null && pair.active == null ? null : pair;
    }

    private static IIcon pick(Pair pair, boolean active, IIcon original, String vanillaName) {
        if (pair == null || !matches(pair, original, vanillaName)) {
            return null;
        }
        if (active) {
            return pair.active != null ? pair.active : pair.idle;
        }
        return pair.idle;
    }

    private static boolean matches(Pair pair, IIcon icon, String vanillaName) {
        return isIcon(icon, vanillaName) || (icon != null && (icon == pair.idle || icon == pair.active));
    }

    private static boolean isIcon(IIcon icon, String name) {
        return icon != null && name.equals(icon.getIconName());
    }

    private static void logState(String machine, Pair pair) {
        logState(machine, PatchConditions.shouldPatchGalacticraftMachines(), countIcons(pair), 2);
    }

    private static int countIcons(Pair pair) {
        if (pair == null) {
            return 0;
        }
        return (pair.idle != null ? 1 : 0) + (pair.active != null ? 1 : 0);
    }

    private static void logState(String machine, boolean enabled, int patched, int total) {
        int state = !enabled ? -1 : (patched > 0 ? 1 : 0);
        Integer last = LAST_STATE.put(machine, Integer.valueOf(state));
        if (last != null && last.intValue() == state) {
            return;
        }
        if (!enabled) {
            LOG.info("Galacticraft {} texture fix is turned off", machine);
        } else if (patched == 0) {
            LOG.warn("enableTexturesPatcherBlocks is claimed, but no {} textures found", machine);
        } else if (patched < total) {
            LOG.info("Galacticraft {} texture fix turned on: {}/{} textures found", machine, patched, total);
        } else {
            LOG.info("Galacticraft {} texture fix turned on", machine);
        }
    }
}
