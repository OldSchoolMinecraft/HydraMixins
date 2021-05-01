package net.oldschoolminecraft.hydra.mixin.entity;

import net.minecraft.client.util.Session;
import net.minecraft.entity.player.AbstractClientPlayer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.oldschoolminecraft.hydra.CosmeticUtils;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Skin/cloak patch for player SP
 */
@Mixin(AbstractClientPlayer.class)
public class MixinEntityPlayerSP extends PlayerBase
{
    public MixinEntityPlayerSP(Level world)
    {
        super(world);
    }

    @Override
    public void method_494() {}

    @Inject(method = "<init>", at = @At("RETURN"))
    public void EntityPlayerSP(Minecraft mc, Level world, Session session, int i, CallbackInfo ci)
    {
        if (session != null && session.username != null && session.username.length() > 0)
        {
            this.skinUrl = "https://api.gethydra.org/cosmetics/skin?username=" + session.username;
            this.cloakUrl = "https://api.gethydra.org/cosmetics/cloak?username=" + session.username;
        }

        CosmeticUtils.obtainBadge(this);
        CosmeticUtils.obtainNameColor(this);
    }
}
