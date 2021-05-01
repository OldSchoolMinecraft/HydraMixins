package net.oldschoolminecraft.hydra.mixin.misc;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fix annoying error when checking user premium status
 */
@Mixin(Minecraft.LoginThread.class)
public class MixinThreadCheckHasPaid
{
    @Inject(method = "run", at = @At("HEAD"), cancellable = true, remap = false)
    public void run(CallbackInfo ci) { ci.cancel(); }
}
