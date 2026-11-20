package com.texturespatcher.mixin;

import net.minecraft.tileentity.TileEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.texturespatcher.patch.GalacticraftMachineTextures;

@Mixin(targets = { "micdoodle8.mods.galacticraft.core.tile.TileEntityCoalGenerator",
    "micdoodle8.mods.galacticraft.core.tile.TileEntityIngotCompressor",
    "micdoodle8.mods.galacticraft.core.tile.TileEntityElectricIngotCompressor",
    "micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenCollector",
    "micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenDistributor",
    "micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenSealer",
    "micdoodle8.mods.galacticraft.core.tile.TileEntityFuelLoader" })
public class MixinGalacticraftMachineTiles {

    @Inject(method = "updateEntity", at = @At("TAIL"))
    private void texturespatcher$refreshTexture(CallbackInfo ci) {
        GalacticraftMachineTextures.refreshIfChanged((TileEntity) (Object) this);
    }
}
