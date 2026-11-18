package com.texturespatcher.mixin;

import net.minecraft.block.BlockJukebox;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.texturespatcher.patch.JukeboxBottomTexture;

@Mixin(BlockJukebox.class)
public class MixinBlockJukebox {

    @Inject(method = "registerBlockIcons", at = @At("TAIL"))
    private void texturespatcher$registerBottomIcon(IIconRegister register, CallbackInfo ci) {
        JukeboxBottomTexture.onRegisterBlockIcons(register);
    }

    @Inject(method = "getIcon", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$useBottomIcon(int side, int meta, CallbackInfoReturnable<IIcon> cir) {
        if (side != 0) {
            return;
        }
        IIcon icon = JukeboxBottomTexture.getBottomIcon();
        if (icon != null) {
            cir.setReturnValue(icon);
        }
    }
}
