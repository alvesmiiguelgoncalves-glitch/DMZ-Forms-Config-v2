package com.dmzformoptions.mixin;

import com.dmzformoptions.util.FormOptions;
import com.dragonminez.common.hair.StrandPose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Custom hair styles can give strands their own colors; DMZ then ignores the global (tinted) hair color.
 * Strands that inherit the global color are already tinted, so only strands with their own color are tinted here.
 */
@Mixin(value = StrandPose.class, remap = false)
public abstract class StrandColorMixin {

    @Inject(method = "resolveSide", at = @At("RETURN"), cancellable = true, remap = false)
    private static void dmzfo$tintOwnColors(int segmentColor, int strandColor, int tipColor, int global, float param,
                                            CallbackInfoReturnable<Integer> cir) {
        if (segmentColor == -1 && strandColor == -1 && tipColor == -1) return;
        cir.setReturnValue(FormOptions.tintStrandColor(cir.getReturnValue()));
    }
}
