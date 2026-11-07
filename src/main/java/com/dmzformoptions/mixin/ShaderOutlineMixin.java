package com.dmzformoptions.mixin;

import com.dmzformoptions.util.OutlineMerge;
import com.dragonminez.client.render.shader.TransformationPostShaderManager;
import com.dragonminez.common.config.FormConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Applies {@link OutlineMerge} to the stack form's outline (the stack wins over the base form in DMZ). */
@Mixin(value = TransformationPostShaderManager.class, remap = false)
public abstract class ShaderOutlineMixin {

    @Inject(method = "resolveActiveShaderConfig", at = @At("HEAD"), remap = false)
    private static void dmzfo$rememberPlayer(Player player, CallbackInfoReturnable<Object> cir) {
        OutlineMerge.setCurrent(player);
    }

    // ordinal 0 = the base form's getOutlineShader(), ordinal 1 = the stack form's
    @Redirect(method = "resolveActiveShaderConfig", remap = false,
            at = @At(value = "INVOKE", remap = false, ordinal = 1,
                    target = "Lcom/dragonminez/common/config/FormConfig$FormData;getOutlineShader()Lcom/dragonminez/common/config/FormConfig$FormData$OutlineShaderConfig;"))
    private static FormConfig.FormData.OutlineShaderConfig dmzfo$stackOutline(FormConfig.FormData stack) {
        return OutlineMerge.resolveStack(stack);
    }
}
