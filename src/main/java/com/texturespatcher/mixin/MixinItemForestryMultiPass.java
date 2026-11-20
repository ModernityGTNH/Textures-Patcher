package com.texturespatcher.mixin;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import forestry.core.circuits.ItemCircuitBoard;

import com.texturespatcher.patch.ForestryBoardTextures;

@Mixin(targets = "forestry.core.items.ItemForestryMultiPass")
public abstract class MixinItemForestryMultiPass {

    @Inject(method = "registerIcons", at = @At("TAIL"))
    private void texturespatcher$registerCircuitBoardIcons(IIconRegister register, CallbackInfo ci) {
        if (!((Object) this instanceof ItemCircuitBoard)) {
            return;
        }
        ForestryBoardTextures.onRegisterIcons(register);
    }

    @Inject(method = "getIconFromDamageForRenderPass", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$useCircuitBoardIcon(int damage, int pass, CallbackInfoReturnable<IIcon> cir) {
        if (!((Object) this instanceof ItemCircuitBoard)) {
            return;
        }
        IIcon icon = ForestryBoardTextures.getIcon(damage);
        if (icon != null) {
            cir.setReturnValue(icon);
        }
    }
}
