package net.oldschoolminecraft.hydra.mixin.render;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.EntityModelBase;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.player.PlayerBase;
import net.oldschoolminecraft.hydra.CosmeticUtils;
import net.oldschoolminecraft.hydra.MixinUtils;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * BADGES!!! THEY'RE BACK BABY! LETS GOOOOOOOO
 */
@Mixin(PlayerRenderer.class)
public class MixinPlayerRenderer extends LivingEntityRenderer
{
    public MixinPlayerRenderer(EntityModelBase modelBase, float v)
    {
        super(modelBase, v);
    }

    @Inject(method = "method_821", at = @At("HEAD"), cancellable = true)
    public void renderName(PlayerBase entityPlayer, double x, double y, double z, CallbackInfo ci)
    {
        ci.cancel();

        float scale = 0.016666668F * 1.6F; // constant pulled from vanilla code

        TextureManager renderEngine = MixinUtils.getMinecraft().textureManager;
        Tessellator tessellator = Tessellator.INSTANCE;
        if (CosmeticUtils.badges.containsKey(entityPlayer) && !entityPlayer.method_1373())
        {
            try
            {
                String badge_url = CosmeticUtils.badges.get(entityPlayer);

                GL11.glPushMatrix();
                GL11.glTranslatef((float) x + 0.0F, (float) y + 2.3F, (float) z);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-this.dispatcher.field_2497, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(this.dispatcher.field_2498, 1.0F, 0.0F, 0.0F);
                GL11.glScalef(-scale, -scale, scale);
                GL11.glDisable(GL_LIGHTING);
                GL11.glTranslatef(0.0F, 0.25F / scale, 0.0F);
                GL11.glDepthMask(false);
                GL11.glEnable(GL_BLEND);
                GL11.glBlendFunc(770, 771);
                glEnable(GL_TEXTURE_2D);

                float badgeScale = 5f;
                int offsetX = 0; // -(strWidth + 6);
                float offsetY = -16f;
                renderEngine.bindTexture(renderEngine.getTextureId(badge_url));
                glEnable(GL11.GL_DEPTH_TEST);
                glDepthMask(true);
                tessellator.start();
                tessellator.colour(1f, 1f, 1f, 1.0F);
                tessellator.vertex(-badgeScale + offsetX, badgeScale + offsetY, 0.0D, -0f, 1f);
                tessellator.vertex(badgeScale + offsetX, badgeScale + offsetY, 0.0D, 1f, 1f);
                tessellator.vertex(badgeScale + offsetX, -badgeScale + offsetY, 0.0D, 1f, -0f);
                tessellator.vertex(-badgeScale + offsetX, -badgeScale + offsetY, 0.0D, -0f, -0f);
                tessellator.draw();

                glPopMatrix();
            } catch (Exception ex) {
                System.out.println("Error rendering badge: " + ex.getMessage());
            }
        }

        String username = entityPlayer.name;
        int colorVal = CosmeticUtils.nametag_colors.getOrDefault(entityPlayer, 0x20ffffff);
        int rainbow = Color.HSBtoRGB(System.currentTimeMillis() % 1000L / 1000f, 0.8f, 0.8f);

        float dst = entityPlayer.distanceTo(this.dispatcher.entity);
        if (dst <= (float)64 && !entityPlayer.method_1373())
        {
            TextRenderer textRenderer = this.method_2023();
            float var12 = 1.6F;
            float var13 = 0.016666668F * var12;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x + 0.0F, (float)y + 2.3F, (float)z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-this.dispatcher.field_2497, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.dispatcher.field_2498, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(-var13, -var13, var13);
            GL11.glDisable(2896 /*GL_LIGHTING*/);
            GL11.glDepthMask(false);
            GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
            GL11.glEnable(3042 /*GL_BLEND*/);
            GL11.glBlendFunc(770, 771);
            byte renderOffset = 0;
            if (username.equals("deadmau5"))
                renderOffset = -10;

            tessellator.start();
            int var16 = textRenderer.getTextWidth(username) / 2;
            tessellator.colour(0.0F, 0.0F, 0.0F, 0.25F);
            tessellator.addVertex((double)(-var16 - 1), (double)(-1 + renderOffset), 0.0D);
            tessellator.addVertex((double)(-var16 - 1), (double)(8 + renderOffset), 0.0D);
            tessellator.addVertex((double)(var16 + 1), (double)(8 + renderOffset), 0.0D);
            tessellator.addVertex((double)(var16 + 1), (double)(-1 + renderOffset), 0.0D);
            tessellator.draw();
            GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
            textRenderer.drawText(username, -textRenderer.getTextWidth(username) / 2, renderOffset, (colorVal == -1) ? rainbow : colorVal); // 553648127
            GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
            GL11.glDepthMask(true);
            boolean coloredName = colorVal != 0x20ffffff;
            textRenderer.drawText(username, -textRenderer.getTextWidth(username) / 2, renderOffset, coloredName ? ((colorVal == -1) ? rainbow : colorVal) : -1); // -1
            GL11.glEnable(2896 /*GL_LIGHTING*/);
            GL11.glDisable(3042 /*GL_BLEND*/);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        }
    }
}
