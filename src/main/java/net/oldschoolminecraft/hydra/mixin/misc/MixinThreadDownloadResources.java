package net.oldschoolminecraft.hydra.mixin.misc;

import net.minecraft.client.util.ThreadDownloadResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Replace old sound URLs with working ones
 */
@Mixin(ThreadDownloadResources.class)
public class MixinThreadDownloadResources
{
    @ModifyConstant(method = "run", constant = @Constant(stringValue = "http://s3.amazonaws.com/MinecraftResources/"), remap = false)
    private String getFixedResourcesURL(String s)
    {
        return "http://resourceproxy.pymcl.net/MinecraftResources/";
    }
}
