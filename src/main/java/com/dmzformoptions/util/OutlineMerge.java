package com.dmzformoptions.util;

import com.dragonminez.common.config.FormConfig;
import com.dragonminez.common.stats.character.Character;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

/**
 * Outline while a stack form (Kaio-ken) is active. By default DMZ lets the stack form's outline replace the base
 * form's outline. The BASE form can change that with {@code stackOutlinePrimaryColor} and
 * {@code stackOutlineThicknessMultiplicator}; when at least one is set (and both forms have an enabled outline), the
 * outline keeps the base form's look (thickness, fire, color mode, glow) and:
 * <ul>
 *   <li>primary color = {@code stackOutlinePrimaryColor}, or the stack form's own primary color when empty / "#";</li>
 *   <li>secondary color = the stack form's own secondary color;</li>
 *   <li>thickness = the base form's thickness x {@code stackOutlineThicknessMultiplicator}.</li>
 * </ul>
 */
public final class OutlineMerge {

    /** Player whose shader is being resolved right now (render thread). */
    private static Entity current;

    private OutlineMerge() {
    }

    public static void setCurrent(@Nullable Entity entity) {
        current = entity;
    }

    public static FormConfig.FormData.OutlineShaderConfig resolveStack(FormConfig.FormData stack) {
        FormConfig.FormData.OutlineShaderConfig own = stack.getOutlineShader();
        try {
            if (!own.isEnabled()) return own;

            Character character = FormOptions.characterOf(current);
            FormConfig.FormData baseForm = character != null && character.hasActiveForm() ? character.getActiveFormData() : null;
            FormOptionsAccess options = FormOptions.of(baseForm);
            if (options == null) return own;

            FormConfig.FormData.OutlineShaderConfig base = baseForm.getOutlineShader();
            if (!base.isEnabled()) return own;

            String customPrimary = options.dmzfo$stackOutlinePrimaryColor();
            double multiplier = options.dmzfo$stackOutlineThicknessMultiplicator();
            if (customPrimary.isEmpty() && multiplier == 1.0) return own; // nothing asked: DMZ's own behavior

            String primary = customPrimary.isEmpty() ? own.getPrimaryColor() : customPrimary;
            FormConfig.FormData.OutlineShaderConfig out = new FormConfig.FormData.OutlineShaderConfig();
            ((OutlineAccess) (Object) out).dmzfo$fill(primary, own.getSecondaryColor(),
                    base.getOutlineThickness() * multiplier, base.getFireIntensity(), base.getColorMode(), base.getBodyGlow());
            return out;
        } catch (RuntimeException | LinkageError e) {
            return own;
        }
    }
}
