package net.oldschoolminecraft.hydra.mixin.render;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.oldschoolminecraft.hydra.CosmeticUtils;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.awt.*;

@Mixin(LivingEntityRenderer.class)
public class MixinRenderLiving extends EntityRenderer
{
    /**
     * @author
     */
    @Overwrite
    public void method_818(Living entity, String renderString, double x, double y, double z, int dstLimit)
    {
        TextRenderer var11 = this.method_2023();
        Tessellator tessellator = Tessellator.INSTANCE;

        byte renderOffset = 0;
        if (renderString.equals("deadmau5"))
            renderOffset = -10;

        if (!(entity instanceof PlayerBase))
        {
            GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
            tessellator.start();
            int var16 = var11.getTextWidth(renderString) / 2;
            tessellator.colour(0.0F, 0.0F, 0.0F, 0.25F);
            tessellator.addVertex((double)(-var16 - 1), (double)(-1 + renderOffset), 0.0D);
            tessellator.addVertex((double)(-var16 - 1), (double)(8 + renderOffset), 0.0D);
            tessellator.addVertex((double)(var16 + 1), (double)(8 + renderOffset), 0.0D);
            tessellator.addVertex((double)(var16 + 1), (double)(-1 + renderOffset), 0.0D);
            tessellator.draw();
            GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
            var11.drawText(renderString, -var11.getTextWidth(renderString) / 2, renderOffset, 553648127); // 553648127
            GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
            GL11.glDepthMask(true);
            var11.drawText(renderString, -var11.getTextWidth(renderString) / 2, renderOffset, -1); // -1
            GL11.glEnable(2896 /*GL_LIGHTING*/);
            GL11.glDisable(3042 /*GL_BLEND*/);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        } else {
            PlayerBase entityPlayer = (PlayerBase) entity;

            float dst = entityPlayer.distanceTo(this.dispatcher.entity);
            if (dst <= (float) 64 && !entityPlayer.method_1373())
            {
                int colorVal = CosmeticUtils.nametag_colors.getOrDefault(entityPlayer, 0x20ffffff);
                int rainbow = Color.HSBtoRGB(System.currentTimeMillis() % 1000L / 1000f, 0.8f, 0.8f);

                GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
                tessellator.start();
                int var16 = var11.getTextWidth(renderString) / 2;
                tessellator.colour(0.0F, 0.0F, 0.0F, 0.25F);
                tessellator.addVertex((double)(-var16 - 1), (double)(-1 + renderOffset), 0.0D);
                tessellator.addVertex((double)(-var16 - 1), (double)(8 + renderOffset), 0.0D);
                tessellator.addVertex((double)(var16 + 1), (double)(8 + renderOffset), 0.0D);
                tessellator.addVertex((double)(var16 + 1), (double)(-1 + renderOffset), 0.0D);
                tessellator.draw();
                GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
                var11.drawText(renderString, -var11.getTextWidth(renderString) / 2, renderOffset, (colorVal == -1) ? rainbow : colorVal); // 553648127
                GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
                GL11.glDepthMask(true);
                boolean coloredName = colorVal != 0x20ffffff;
                var11.drawText(renderString, -var11.getTextWidth(renderString) / 2, renderOffset, coloredName ? ((colorVal == -1) ? rainbow : colorVal) : -1); // -1
                GL11.glEnable(2896 /*GL_LIGHTING*/);
                GL11.glDisable(3042 /*GL_BLEND*/);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
            }
        }
    }

    @Override
    public void render(EntityBase entity, double x, double y, double z, float f, float f1) {}
}
