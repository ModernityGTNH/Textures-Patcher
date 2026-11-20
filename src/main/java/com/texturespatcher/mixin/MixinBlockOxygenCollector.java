package com.texturespatcher.mixin;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import com.texturespatcher.patch.GalacticraftMachineTextures;

@Mixin(targets = "micdoodle8.mods.galacticraft.core.blocks.BlockOxygenCollector")
public class MixinBlockOxygenCollector {

    @Inject(method = "registerBlockIcons", at = @At("TAIL"))
    private void texturespatcher$registerIcons(IIconRegister register, CallbackInfo ci) {
        GalacticraftMachineTextures.onRegisterOxygenCollector((Block) (Object) this, register);
    }

    @Inject(
        method = "getIcon(II)Lnet/minecraft/util/IIcon;",
        at = @At("RETURN"),
        cancellable = true)
    private void texturespatcher$patchItem(int side, int meta, CallbackInfoReturnable<IIcon> cir) {
        IIcon replaced = GalacticraftMachineTextures.itemIcon((Block) (Object) this, side, cir.getReturnValue());
        if (replaced != null) {
            cir.setReturnValue(replaced);
        }
    }
}
