package com.texturespatcher.mixin;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import forestry.core.items.ItemElectronTube;
import forestry.core.items.ItemOverlay;

import com.texturespatcher.patch.ForestryHoneyDropTextures;
import com.texturespatcher.patch.ForestryTubeTextures;

@Mixin(ItemOverlay.class)
public abstract class MixinItemOverlay {

    @Inject(method = "registerIcons", at = @At("TAIL"))
    private void texturespatcher$registerOverlayIcons(IIconRegister register, CallbackInfo ci) {
        if ((Object) this instanceof ItemElectronTube) {
            ForestryTubeTextures.onRegisterIcons(register);
        } else if (ForestryHoneyDropTextures.matches((ItemOverlay) (Object) this)) {
            ForestryHoneyDropTextures.onRegisterIcons(register);
        }
    }

    @Inject(method = "getIconFromDamageForRenderPass", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$useOverlayIcon(int damage, int pass, CallbackInfoReturnable<IIcon> cir) {
        if (!((Object) this instanceof ItemElectronTube)) {
            if (ForestryHoneyDropTextures.matches((ItemOverlay) (Object) this)) {
                IIcon icon = ForestryHoneyDropTextures.getIcon(damage);
                if (icon != null) {
                    cir.setReturnValue(icon);
                }
            }
            return;
        }
        IIcon icon = ForestryTubeTextures.getIcon(damage);
        if (icon != null) {
            cir.setReturnValue(icon);
        }
    }

    @Inject(method = "getColorFromItemStack", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$skipOverlayTint(ItemStack stack, int pass, CallbackInfoReturnable<Integer> cir) {
        if (!((Object) this instanceof ItemElectronTube)) {
            if (ForestryHoneyDropTextures.matches((ItemOverlay) (Object) this)) {
                if (stack != null && ForestryHoneyDropTextures.isPatched(stack.getItemDamage())) {
                    cir.setReturnValue(0xFFFFFF);
                }
            }
            return;
        }
        if (stack != null && ForestryTubeTextures.isPatched(stack.getItemDamage())) {
            cir.setReturnValue(0xFFFFFF);
        }
    }
}
