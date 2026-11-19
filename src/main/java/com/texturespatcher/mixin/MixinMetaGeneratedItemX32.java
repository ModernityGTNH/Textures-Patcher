package com.texturespatcher.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.texturespatcher.patch.CopperItemTextures;

@Mixin(targets = "gregtech.api.items.MetaGeneratedItemX32", remap = false)
public class MixinMetaGeneratedItemX32 {

    @Inject(method = "func_77617_a", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$copperIcon(int meta, CallbackInfoReturnable<IIcon> cir) {
        IIcon icon = CopperItemTextures.iconFor(this, meta);
        if (icon != null) {
            cir.setReturnValue(icon);
        }
    }

    @Inject(method = "getRGBa", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$whiteTint(ItemStack stack, CallbackInfoReturnable<short[]> cir) {
        if (stack != null && CopperItemTextures.iconFor(this, stack.getItemDamage()) != null) {
            cir.setReturnValue(new short[] { 255, 255, 255, 0 });
        }
    }
}
