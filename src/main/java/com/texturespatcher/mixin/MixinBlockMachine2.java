package com.texturespatcher.mixin;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import micdoodle8.mods.galacticraft.core.blocks.BlockMachine2;

import com.texturespatcher.patch.GalacticraftMachineTextures;

@Mixin(BlockMachine2.class)
public class MixinBlockMachine2 {

    @Inject(method = "registerBlockIcons", at = @At("TAIL"))
    private void texturespatcher$registerIcons(IIconRegister register, CallbackInfo ci) {
        GalacticraftMachineTextures.onRegisterBlockMachine2(register);
    }

    @Inject(
        method = "getIcon(Lnet/minecraft/world/IBlockAccess;IIII)Lnet/minecraft/util/IIcon;",
        at = @At("RETURN"),
        cancellable = true)
    private void texturespatcher$patchFront(IBlockAccess world, int x, int y, int z, int side,
        CallbackInfoReturnable<IIcon> cir) {
        if (world == null) {
            return;
        }
        TileEntity tile = world.getTileEntity(x, y, z);
        IIcon replaced = GalacticraftMachineTextures
            .electricCompressor(cir.getReturnValue(), GalacticraftMachineTextures.electricCompressorRunning(tile));
        if (replaced != null) {
            cir.setReturnValue(replaced);
        }
    }

    @Inject(
        method = "getIcon(II)Lnet/minecraft/util/IIcon;",
        at = @At("RETURN"),
        cancellable = true)
    private void texturespatcher$patchItem(int side, int meta, CallbackInfoReturnable<IIcon> cir) {
        IIcon replaced = GalacticraftMachineTextures.electricCompressor(cir.getReturnValue(), false);
        if (replaced != null) {
            cir.setReturnValue(replaced);
        }
    }
}
