package com.texturespatcher.mixin;

import net.minecraft.util.IIcon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.texturespatcher.patch.CopperItemTextures;

@Mixin(targets = "gregtech.api.items.MetaGeneratedItem", remap = false)
public class MixinMetaGeneratedItem {

    @Inject(method = "getIcon", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$copperIcon(int meta, int pass, CallbackInfoReturnable<IIcon> cir) {
        IIcon icon = CopperItemTextures.iconFor(this, meta);
        if (icon != null) {
            cir.setReturnValue(icon);
        }
    }

    @Inject(method = "getOverlayIcon", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$copperOverlay(int meta, int pass, CallbackInfoReturnable<IIcon> cir) {
        if (CopperItemTextures.iconFor(this, meta) != null) {
            cir.setReturnValue(null);
        }
    }
}
