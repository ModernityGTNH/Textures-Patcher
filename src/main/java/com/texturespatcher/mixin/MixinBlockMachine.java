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


import com.texturespatcher.patch.GalacticraftMachineTextures;

@Mixin(targets = "micdoodle8.mods.galacticraft.core.blocks.BlockMachine")
public class MixinBlockMachine {

    @Inject(method = "registerBlockIcons", at = @At("TAIL"))
    private void texturespatcher$registerIcons(IIconRegister register, CallbackInfo ci) {
        GalacticraftMachineTextures.onRegisterBlockMachine(register);
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
        IIcon original = cir.getReturnValue();
        TileEntity tile = world.getTileEntity(x, y, z);
        IIcon replaced = GalacticraftMachineTextures
            .coalGenerator(original, GalacticraftMachineTextures.coalGeneratorRunning(tile));
        if (replaced == null) {
            replaced = GalacticraftMachineTextures
                .compressor(original, GalacticraftMachineTextures.compressorRunning(tile));
        }
        if (replaced != null) {
            cir.setReturnValue(replaced);
        }
    }

    @Inject(
        method = "getIcon(II)Lnet/minecraft/util/IIcon;",
        at = @At("RETURN"),
        cancellable = true)
    private void texturespatcher$patchItem(int side, int meta, CallbackInfoReturnable<IIcon> cir) {
        IIcon replaced = GalacticraftMachineTextures.coalGenerator(cir.getReturnValue(), false);
        if (replaced == null) {
            replaced = GalacticraftMachineTextures.compressor(cir.getReturnValue(), false);
        }
        if (replaced != null) {
            cir.setReturnValue(replaced);
        }
    }
}
