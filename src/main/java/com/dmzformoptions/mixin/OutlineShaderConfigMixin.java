package com.dmzformoptions.mixin;

import com.dmzformoptions.util.OutlineAccess;
import com.dragonminez.common.config.FormConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/** Adds {@code addToBaseOutline} to every {@code outlineShader} block and exposes the raw fields. */
@Mixin(value = FormConfig.FormData.OutlineShaderConfig.class, remap = false)
public abstract class OutlineShaderConfigMixin implements OutlineAccess {

    @Shadow private Boolean enabled;
    @Shadow private String primaryColor;
    @Shadow private String secondaryColor;
    @Shadow private Double outlineThickness;
    @Shadow private Double fireIntensity;
    @Shadow private String colorMode;
    @Shadow private Double bodyGlow;

    @Unique private Boolean addToBaseOutline = false;

    @Override
    public boolean dmzfo$addToBase() {
        return Boolean.TRUE.equals(addToBaseOutline);
    }

    @Override
    public String dmzfo$rawPrimary() {
        return primaryColor == null ? "" : primaryColor.trim();
    }

    @Override
    public String dmzfo$rawSecondary() {
        return secondaryColor == null ? "" : secondaryColor.trim();
    }

    @Override
    public String dmzfo$rawMode() {
        return colorMode == null ? "pulse" : colorMode;
    }

    @Override
    public double dmzfo$rawFire() {
        return fireIntensity == null ? 0.0 : fireIntensity;
    }

    @Override
    public double dmzfo$rawGlow() {
        return bodyGlow == null ? 0.0 : bodyGlow;
    }

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
