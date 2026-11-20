package com.texturespatcher.mixin;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.common.util.ForgeDirection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import appeng.block.AEBaseBlock;
import appeng.client.render.BlockRenderInfo;
import appeng.tile.misc.TileVibrationChamber;

import com.texturespatcher.patch.Ae2VibrationChamberTexture;

@Mixin(targets = "appeng.block.misc.BlockVibrationChamber")
public abstract class MixinBlockVibrationChamber {

    @Inject(method = "getIcon", at = @At("RETURN"), cancellable = true)
    private void texturespatcher$useVibrationChamberIcons(IBlockAccess world, int x, int y, int z, int side,
        CallbackInfoReturnable<IIcon> cir) {
        IIcon original = cir.getReturnValue();
        if (original == null) {
            return;
        }
        boolean running = texturespatcher$isRunning(world, x, y, z);

        // side 1 == top
        if (side == 1) {
            IIcon icon = Ae2VibrationChamberTexture.getTopIcon(running);
            if (icon != null) {
                cir.setReturnValue(icon);
            }
            return;
        }

        if (texturespatcher$isBackFace(original)) {
            IIcon icon = Ae2VibrationChamberTexture.getBackIcon(running);
            if (icon != null) {
                cir.setReturnValue(icon);
            }
        }
    }

    private boolean texturespatcher$isRunning(IBlockAccess world, int x, int y, int z) {
        if (world == null) {
            return false;
        }
        TileEntity tile = world.getTileEntity(x, y, z);
        return tile instanceof TileVibrationChamber && ((TileVibrationChamber) tile).isOn;
    }

    private boolean texturespatcher$isBackFace(IIcon icon) {
        BlockRenderInfo renderInfo = ((AEBaseBlock) (Object) this).getRendererInstance();
        if (renderInfo == null) {
            return false;
        }
        IIcon back = renderInfo.getTexture(ForgeDirection.NORTH);
        if (back == null || back == renderInfo.getTexture(ForgeDirection.SOUTH)) {
            return false;
        }
        return icon == back;
    }
}
