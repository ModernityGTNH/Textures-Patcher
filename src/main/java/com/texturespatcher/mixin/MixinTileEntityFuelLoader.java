package com.texturespatcher.mixin;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import micdoodle8.mods.galacticraft.core.tile.TileEntityFuelLoader;

import com.texturespatcher.patch.GalacticraftMachineTextures;

@Mixin(TileEntityFuelLoader.class)
public class MixinTileEntityFuelLoader {

    @Inject(method = "updateEntity", at = @At("TAIL"))
    private void texturespatcher$refreshFront(CallbackInfo ci) {
        TileEntity tile = (TileEntity) (Object) this;
        World world = tile.getWorldObj();
        if (world == null || !world.isRemote) {
            return;
        }
        if (GalacticraftMachineTextures.fuelLoaderLevelChanged(tile)) {
            world.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
        }
    }
}
