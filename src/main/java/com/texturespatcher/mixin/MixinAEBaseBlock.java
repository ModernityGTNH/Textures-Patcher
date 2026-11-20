package com.texturespatcher.mixin;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import appeng.block.misc.BlockVibrationChamber;

import com.texturespatcher.patch.Ae2VibrationChamberTexture;

@Mixin(targets = "appeng.block.AEBaseBlock")
public abstract class MixinAEBaseBlock {

    @Inject(method = "registerBlockIcons", at = @At("TAIL"))
    private void texturespatcher$registerVibrationChamberTopIcons(IIconRegister register, CallbackInfo ci) {
        if ((Object) this instanceof BlockVibrationChamber) {
            Ae2VibrationChamberTexture.onRegisterIcons(register);
        }
    }

    @Inject(method = "getIcon(II)Lnet/minecraft/util/IIcon;", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$useVibrationChamberIdleTop(int side, int meta, CallbackInfoReturnable<IIcon> cir) {
        // side 1 == top
        if (side != 1 || !((Object) this instanceof BlockVibrationChamber)) {
            return;
        }
        IIcon icon = Ae2VibrationChamberTexture.getTopIcon(false);
        if (icon != null) {
            cir.setReturnValue(icon);
        }
    }
}
