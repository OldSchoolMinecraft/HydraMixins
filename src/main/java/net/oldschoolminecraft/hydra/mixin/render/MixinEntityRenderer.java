package net.oldschoolminecraft.hydra.mixin.render;

import net.minecraft.sortme.GameRenderer;
import net.oldschoolminecraft.hydra.MixinUtils;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Front-facing third-person camera perspective
 */
@Mixin(GameRenderer.class)
public class MixinEntityRenderer
{
    private float prevCamRoll;
    private float camRoll;
    private float thirdPersonDistance;
    private float thirdPersonDistanceTemp;

    private float debugCamYaw;
    private float debugCamPitch;

    private float prevDebugCamYaw;
    private float prevDebugCamPitch;

    @Shadow private double field_2332; // cameraYaw
    @Shadow private double field_2333; // cameraPitch
    @Shadow private boolean field_2330; // cloudFog

    @Inject(method = "method_1851", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    public void frontFacingA(float f, CallbackInfo ci)
    {
        if (MixinUtils.getCameraPerspective() == 2 && MixinUtils.getMinecraft().options.thirdPerson)
            GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
    }

    @ModifyVariable(method = "method_1851", at = @At(value = "FIELD", id = "Living.pitch:float", ordinal = 2))
    public float frontFacingB(float f)
    {
        return (MixinUtils.getCameraPerspective() == 2 && MixinUtils.getMinecraft().options.thirdPerson) ? f += 180f : f;
    }
}
