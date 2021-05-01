package net.oldschoolminecraft.hydra.mixin.entity;

import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fix cloaks
 */
@Mixin(PlayerBase.class)
public class MixinEntityPlayer extends Living
{
    @Shadow public String playerCloakUrl;
    @Shadow public String name;

    public MixinEntityPlayer(Level world)
    {
        super(world);
    }

    @Inject(method = "initCloak", at = @At("RETURN"))
    public void updateCloak(CallbackInfo ci)
    {
        this.playerCloakUrl = "https://api.gethydra.org/cosmetics/cloak?username=" + this.name;
        this.cloakUrl = playerCloakUrl;
    }
}
