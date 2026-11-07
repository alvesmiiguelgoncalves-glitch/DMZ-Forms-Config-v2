package com.dmzformoptions.mixin;

import com.dmzformoptions.util.OutlineAccess;
import com.dragonminez.common.config.FormConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/** Exposes a way to fill an {@code outlineShader} block, used to build the merged outline (see OutlineMerge). */
@Mixin(value = FormConfig.FormData.OutlineShaderConfig.class, remap = false)
public abstract class OutlineShaderConfigMixin implements OutlineAccess {

    @Shadow private Boolean enabled;
    @Shadow private String primaryColor;
    @Shadow private String secondaryColor;
    @Shadow private Double outlineThickness;
    @Shadow private Double fireIntensity;
    @Shadow private String colorMode;
    @Shadow private Double bodyGlow;

    @Override
    public void dmzfo$fill(String primary, String secondary, double thickness, double fire, String mode, double glow) {
        this.enabled = true;
        this.primaryColor = primary;
        this.secondaryColor = secondary;
        this.outlineThickness = thickness;
        this.fireIntensity = fire;
        this.colorMode = mode;
        this.bodyGlow = glow;
    }
}
