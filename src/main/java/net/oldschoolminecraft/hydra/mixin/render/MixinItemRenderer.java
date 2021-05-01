package net.oldschoolminecraft.hydra.mixin.render;

import net.minecraft.class_556;
import net.oldschoolminecraft.hydra.MixinUtils;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Do not render item in hand when out of first person perspective.
 */
@Mixin(class_556.class)
public class MixinItemRenderer
{
    @Inject(method = "method_1860", at = @At("HEAD"), cancellable = true, remap = false)
    public void renderItemInFirstPerson(float v, CallbackInfo ci)
    {
        Minecraft mc = MixinUtils.getMinecraft();
        if (MixinUtils.getCameraPerspective() != 0 && mc.viewEntity.isSleeping() && mc.options.hideHud)
            ci.cancel();
    }
}