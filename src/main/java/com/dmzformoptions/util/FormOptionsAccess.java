package com.dmzformoptions.util;

import com.dragonminez.common.config.FormConfig;

import javax.annotation.Nullable;

/** Implemented on {@code FormConfig.FormData} by {@code FormDataMixin}. All getters are null-safe. */
public interface FormOptionsAccess {

    boolean dmzfo$forceTiedTail();

    boolean dmzfo$forceLooseTail();

    /** Default true (vanilla behavior). */
    boolean dmzfo$stackTintAffectsHairAndTail();

    /** Negative = use the stack form's own intensity. */
    double dmzfo$stackTintHairAndTailIntensity();

    /** Skin tint color while a stack form is active (set on the BASE form). Empty = the stack form's own tintColor. */
    String dmzfo$stackTintColor();

    /** Skin tint intensity while a stack form is active (set on the BASE form). Negative = the stack form's own. */
    double dmzfo$stackTintIntensity();

    /** Outline primary color while a stack form is active (set on the BASE form). Empty or "#" = the stack form's own. */
    String dmzfo$stackOutlinePrimaryColor();

    /** Multiplies the base form's outline thickness while a stack form is active (set on the BASE form). 1.0 = unchanged. */
    double dmzfo$stackOutlineThicknessMultiplicator();

    /** RGB 0..1, or null when empty/invalid (= game default). */
    @Nullable
    float[] dmzfo$scleraRgb();

    boolean dmzfo$noEyebrows();

    boolean dmzfo$eyeliner();

    @Nullable
    float[] dmzfo$eyelinerRgb();

    /** Default true. */
    boolean dmzfo$chargeKiAnimation();

    /**
     * A cached copy of this form with another tint: {@code color} null/empty keeps the form's own tintColor,
     * {@code intensity} negative keeps the form's own tintIntensity. Null if the copy failed.
     */
    @Nullable
    FormConfig.FormData dmzfo$tintVariant(@Nullable String color, double intensity);
}
