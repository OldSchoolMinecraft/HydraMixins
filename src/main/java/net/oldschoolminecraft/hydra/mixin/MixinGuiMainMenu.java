package net.oldschoolminecraft.hydra.mixin;

import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.screen.menu.MainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainMenu.class)
public class MixinGuiMainMenu extends ScreenBase
{
    @Shadow private String splashMessage;

    @Inject(method = "init", at = @At("RETURN"))
    private void afterInitGui(CallbackInfo ci)
    {
        this.splashMessage = "oldschoolminecraft.net";
    }

    /*@Inject(method = "render", at = @At("RETURN"))
    private void afterDrawScreen(CallbackInfo ci)
    {
        String text = "Hail Hydra!";
        this.textManager.drawTextWithShadow(text, 2, this.height - 10, 16724530);
    }*/
}
