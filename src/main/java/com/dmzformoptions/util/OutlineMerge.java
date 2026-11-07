package com.dmzformoptions.util;

import com.dragonminez.common.config.FormConfig;
import com.dragonminez.common.stats.character.Character;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

/**
 * Stack-form outline rules:
 * <ul>
 *   <li>empty {@code primaryColor} = the stack's {@code auraColor}; empty {@code secondaryColor} = the primary color;</li>
 *   <li>{@code addToBaseOutline: true} = start from the base form's outline (if it has one) and multiply its
 *       thickness by the stack's {@code outlineThickness} (2.0 = twice as thick, 1.0 = unchanged).</li>
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
            OutlineAccess raw = (OutlineAccess) (Object) own;

            String primary = raw.dmzfo$rawPrimary();
            String secondary = raw.dmzfo$rawSecondary();
            String aura = stack instanceof FormOptionsAccess access ? access.dmzfo$auraColor() : "";
            boolean blankPrimary = primary.isEmpty() || primary.equals("#");
            boolean blankSecondary = secondary.isEmpty() || secondary.equals("#");
            if (blankPrimary && !aura.isEmpty()) primary = aura;
            if (blankSecondary) secondary = (blankPrimary && aura.isEmpty()) ? "" : primary;

            FormConfig.FormData.OutlineShaderConfig base = null;
            if (raw.dmzfo$addToBase()) {
                Character character = FormOptions.characterOf(current);
                FormConfig.FormData form = character != null && character.hasActiveForm() ? character.getActiveFormData() : null;
                if (form != null && form.getOutlineShader().isEnabled()) base = form.getOutlineShader();
            }
            if (base == null && !(blankPrimary && !aura.isEmpty()) && !blankSecondary) return own;

            double thickness = own.getOutlineThickness();
            double fire = own.getFireIntensity();
            double glow = own.getBodyGlow();
            String mode = own.getColorMode();
            if (base != null) {
                thickness = base.getOutlineThickness() * own.getOutlineThickness();
                fire = Math.max(base.getFireIntensity(), fire);
                glow = Math.max(base.getBodyGlow(), glow);
                mode = base.getColorMode();
            }
            FormConfig.FormData.OutlineShaderConfig out = new FormConfig.FormData.OutlineShaderConfig();
            ((OutlineAccess) (Object) out).dmzfo$fill(primary, secondary, thickness, fire, mode, glow);
            return out;
        } catch (RuntimeException | LinkageError e) {
            return own;
        }
    }
}
