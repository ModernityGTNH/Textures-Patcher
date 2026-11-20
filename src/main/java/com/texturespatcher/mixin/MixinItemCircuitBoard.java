package com.texturespatcher.mixin;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import com.texturespatcher.patch.ForestryBoardTextures;

@Mixin(targets = "forestry.core.circuits.ItemCircuitBoard")
public class MixinItemCircuitBoard {

    @Inject(method = "getColorFromItemStack", at = @At("HEAD"), cancellable = true)
    private void texturespatcher$skipVanillaTint(ItemStack stack, int pass, CallbackInfoReturnable<Integer> cir) {
        if (stack == null) {
            return;
        }
        if (ForestryBoardTextures.isPatched(stack.getItemDamage())) {
            cir.setReturnValue(Integer.valueOf(0xFFFFFF));
        }
    }
}
