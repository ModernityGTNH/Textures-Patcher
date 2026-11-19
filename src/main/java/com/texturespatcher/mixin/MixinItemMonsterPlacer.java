package com.texturespatcher.mixin;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.texturespatcher.patch.SpawnEggTextures;

@Mixin(ItemMonsterPlacer.class)
public class MixinItemMonsterPlacer {

    @Inject(method = "registerIcons", at = @At("TAIL"))
    private void texturespatcher$registerIcons(IIconRegister register, CallbackInfo ci) {
        SpawnEggTextures.onRegisterIcons(register);
    }

    @Inject(method = "getIconFromDamageForRenderPass", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$renderPassIcon(int damage, int pass, CallbackInfoReturnable<IIcon> cir) {
        IIcon icon = SpawnEggTextures.icon(damage);
        if (icon != null) {
            cir.setReturnValue(icon);
        }
    }

    @Inject(method = "getColorFromItemStack", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$clearTint(ItemStack stack, int pass, CallbackInfoReturnable<Integer> cir) {
        if (stack != null && SpawnEggTextures.icon(stack.getItemDamage()) != null) {
            cir.setReturnValue(Integer.valueOf(0xFFFFFF));
        }
    }
}
