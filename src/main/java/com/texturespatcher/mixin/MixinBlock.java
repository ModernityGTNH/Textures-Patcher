package com.texturespatcher.mixin;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.texturespatcher.patch.GalacticraftMachineTextures;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(
        method = "getIcon(Lnet/minecraft/world/IBlockAccess;IIII)Lnet/minecraft/util/IIcon;",
        at = @At("HEAD"),
        cancellable = true)
    private void texturespatcher$patchGalacticraftMachine(IBlockAccess world, int x, int y, int z, int side,
        CallbackInfoReturnable<IIcon> cir) {
        IIcon icon = GalacticraftMachineTextures.worldIcon((Block) (Object) this, world, x, y, z, side);
        if (icon != null) {
            cir.setReturnValue(icon);
        }
    }
}
