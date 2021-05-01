package net.oldschoolminecraft.hydra.mixin.net;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.oldschoolminecraft.hydra.MixinUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlayNetworkHandler
{
    @Inject(method = "disconnectClient", at = @At("RETURN"))
    public void disconnectClient(String string, Object[] objects, CallbackInfo ci)
    {
        MixinUtils.updateRPC("Idle", "In the menu");
    }
}
