package com.dmzformoptions.mixin;

import com.dmzformoptions.util.FormOptions;
import com.dragonminez.client.render.layer.DMZSkinLayer;
import com.dragonminez.common.stats.StatsData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Eyebrows (and other face parts drawn in the hair color) are tinted in {@code dispatchFaceRender} with
 * {@code hair = applyColorTint(hair, stats)}. That is the second {@code applyColorTint} call there
 * (skin, hair, body color 2); we give it the hair/tail version of the tint.
 */
@Mixin(value = DMZSkinLayer.class, remap = false)
public abstract class SkinLayerBrowTintMixin {

    @Redirect(method = "dispatchFaceRender", remap = false,
            at = @At(value = "INVOKE", ordinal = 1, remap = false,
                    target = "Lcom/dragonminez/client/render/layer/DMZSkinLayer;applyColorTint([FLcom/dragonminez/common/stats/StatsData;)[F"))
    private float[] dmzfo$hairColorTint(DMZSkinLayer<?> layer, float[] rgb, StatsData stats) {
        float[] custom = FormOptions.tintWithHairTail(rgb);
        return custom != null ? custom : ((DMZSkinLayerInvoker) layer).dmzfo$applyColorTint(rgb, stats);
    }
}
