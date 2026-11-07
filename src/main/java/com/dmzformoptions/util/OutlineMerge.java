package com.dmzformoptions.util;

import com.dragonminez.common.config.FormConfig;
import com.dragonminez.common.stats.character.Character;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

/**
 * Outline while a stack form (Kaio-ken) is active. DMZ already lets the stack form's outline replace the base
 * form's outline. The BASE form can tweak the stack form's outline with {@code stackOutlinePrimaryColor},
 * {@code stackOutlineThicknessMultiplicator} and {@code stackOutlineColorMode}. When at least one is set the outline
 * is the stack form's own, except:
 * <ul>
 *   <li>primary color = {@code stackOutlinePrimaryColor} (empty or "#" = the stack form's own);</li>
 *   <li>thickness = the stack form's own thickness x {@code stackOutlineThicknessMultiplicator};</li>
 *   <li>color mode = {@code stackOutlineColorMode} (empty = the stack form's own).</li>
 * </ul>
 * Secondary color, fire intensity and body glow always come from the stack form.
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

            String customPrimary = options.dmzfo$stackOutlinePrimaryColor();
            double multiplier = options.dmzfo$stackOutlineThicknessMultiplicator();
            String customMode = options.dmzfo$stackOutlineColorMode();
            if (customPrimary.isEmpty() && multiplier == 1.0 && customMode.isEmpty()) return own; // nothing asked: DMZ's own behavior

            FormConfig.FormData.OutlineShaderConfig out = new FormConfig.FormData.OutlineShaderConfig();
            ((OutlineAccess) (Object) out).dmzfo$fill(
                    customPrimary.isEmpty() ? own.getPrimaryColor() : customPrimary,
                    own.getSecondaryColor(),
                    own.getOutlineThickness() * multiplier,
                    own.getFireIntensity(),
                    customMode.isEmpty() ? own.getColorMode() : customMode,
                    own.getBodyGlow());
            return out;
        } catch (RuntimeException | LinkageError e) {
            return own;
        }
    }
}
