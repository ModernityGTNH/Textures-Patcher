package com.texturespatcher.mixin;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.IIcon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.texturespatcher.patch.CopperItemTextures;
import com.texturespatcher.patch.SpawnEggTextures;

@Mixin(Item.class)
public class MixinItem {

    @Inject(method = "registerIcons", at = @At("TAIL"))
    private void texturespatcher$registerCopperIcons(IIconRegister register, CallbackInfo ci) {
        CopperItemTextures.onRegisterIcons(register);
    }

    @Inject(method = "getIconFromDamage", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$spawnEggIcon(int damage, CallbackInfoReturnable<IIcon> cir) {
        if (!SpawnEggTextures.isActive() || !((Object) this instanceof ItemMonsterPlacer)) {
            return;
        }
        IIcon icon = SpawnEggTextures.icon(damage);
        if (icon != null) {
            cir.setReturnValue(icon);
        }
    }
}
