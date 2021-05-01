package net.oldschoolminecraft.hydra.mixin;

import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.MinecraftApplet;
import net.oldschoolminecraft.hydra.MixinUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft
{
    @Shadow private static Minecraft instance;

    @Shadow private MinecraftApplet mcApplet;
    @Shadow private boolean isApplet;
    @Shadow private String fpsDebugString;

    @Inject(method = "run", at = @At("HEAD"), remap = false)
    private void onRun(CallbackInfo ci)
    {
        // always show the quit button
        this.isApplet = false;

        // set static minecraft variable for outside access
        MixinUtils.setMinecraft(instance);

        MixinUtils.initRPC();
        MixinUtils.updateRPC("In the menu", "Idle");

        System.out.println("=======================");
        System.out.println("Hello from HydraMixins!");
        System.out.println("=======================");
    }

    //@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", shift = At.Shift.BEFORE))
    private void keyboardHook(CallbackInfo ci)
    {
        //
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void tick(CallbackInfo ci)
    {
        if (Keyboard.getEventKeyState() && !Keyboard.isRepeatEvent())
        {
            if (Keyboard.getEventKey() == Keyboard.KEY_F5)
            {
                MixinUtils.changeCameraPerspective();
                //System.out.println("Camera perspective changed: " + MixinUtils.getCameraPerspective());
            }
        }

        DiscordRPC.discordRunCallbacks();
    }
}