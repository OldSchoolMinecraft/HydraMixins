package net.oldschoolminecraft.hydra.mixin.net;

import net.minecraft.client.Minecraft;
import net.minecraft.network.ClientPlayNetworkHandler;
import net.oldschoolminecraft.hydra.MixinUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler
{
    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    public void construct(Minecraft minecraft, String address, int port, CallbackInfo ci)
    {
        MixinUtils.updateRPC(address + ((port == 25565) ? "" : port), "Playing Multiplayer");
    }

    @Inject(method = "method_1647", at = @At("RETURN"))
    public void disconnect(CallbackInfo ci)
    {
        MixinUtils.updateRPC("Idle", "In the menu");
    }
}
