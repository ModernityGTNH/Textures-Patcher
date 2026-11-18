package com.texturespatcher.mixin;

import net.minecraft.util.IIcon;

import net.minecraftforge.common.util.ForgeDirection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import appeng.block.AEBaseBlock;
import appeng.block.misc.BlockVibrationChamber;
import appeng.client.render.BaseBlockRender;

import com.texturespatcher.patch.Ae2VibrationChamberTexture;

@Mixin(BaseBlockRender.class)
public abstract class MixinBaseBlockRender {
    @Inject(method = "getInventoryIcon", at = @At("RETURN"), cancellable = true, remap = false)
    private void texturespatcher$useVibrationChamberIdleTop(IIcon overrideIcon, IIcon faceIcon, AEBaseBlock block,
        ForgeDirection face, int meta, CallbackInfoReturnable<IIcon> cir) {
        if (face != ForgeDirection.UP || !(block instanceof BlockVibrationChamber)) {
            return;
        }
        IIcon icon = Ae2VibrationChamberTexture.getTopIcon(false);
        if (icon != null) {
            cir.setReturnValue(icon);
        }
    }
}
