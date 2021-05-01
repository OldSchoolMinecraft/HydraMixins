package net.oldschoolminecraft.hydra.mixin.entity;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.entity.player.RemoteClientPlayer;
import net.minecraft.level.Level;
import net.oldschoolminecraft.hydra.CosmeticUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Skin/cloak fix for player MP
 */
@Mixin(RemoteClientPlayer.class)
public class MixinEntityOtherPlayerMP extends PlayerBase
{
    public MixinEntityOtherPlayerMP(Level world)
    {
        super(world);
    }

    @Override
    public void method_494() {}

    @Inject(method = "<init>", at = @At("RETURN"))
    public void EntityOtherPlayerMP(Level world, String username, CallbackInfo ci)
    {
        if (username != null && username.length() > 0)
        {
            this.skinUrl = "https://api.gethydra.org/cosmetics/skin?username=" + username;
            this.cloakUrl = "https://api.gethydra.org/cosmetics/cloak?username=" + username;
        }

        CosmeticUtils.obtainBadge(this);
        CosmeticUtils.obtainNameColor(this);
    }
}
