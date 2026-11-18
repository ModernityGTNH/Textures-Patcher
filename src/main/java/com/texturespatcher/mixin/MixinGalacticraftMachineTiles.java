package com.texturespatcher.mixin;

import net.minecraft.tileentity.TileEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import micdoodle8.mods.galacticraft.core.tile.TileEntityCoalGenerator;
import micdoodle8.mods.galacticraft.core.tile.TileEntityElectricIngotCompressor;
import micdoodle8.mods.galacticraft.core.tile.TileEntityFuelLoader;
import micdoodle8.mods.galacticraft.core.tile.TileEntityIngotCompressor;
import micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenCollector;
import micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenDistributor;
import micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenSealer;

import com.texturespatcher.patch.GalacticraftMachineTextures;

@Mixin({ TileEntityCoalGenerator.class, TileEntityIngotCompressor.class, TileEntityElectricIngotCompressor.class,
    TileEntityOxygenCollector.class, TileEntityOxygenDistributor.class, TileEntityOxygenSealer.class,
    TileEntityFuelLoader.class })
public class MixinGalacticraftMachineTiles {

    @Inject(method = "updateEntity", at = @At("TAIL"))
    private void texturespatcher$refreshTexture(CallbackInfo ci) {
        GalacticraftMachineTextures.refreshIfChanged((TileEntity) (Object) this);
    }
}
