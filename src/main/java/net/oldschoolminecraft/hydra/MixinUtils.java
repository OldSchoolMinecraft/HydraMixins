package net.oldschoolminecraft.hydra;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class MixinUtils
{
    private static Minecraft mc;
    private static float cameraYaw;
    private static float cameraPitch;
    private static int cameraPerspective = 0;
    private static boolean perspectiveToggled = false;
    private static boolean frontFacing = false;
    private static Frame frame;
    private boolean[] keyStates = new boolean[256];

    public static void setMinecraft(Minecraft mc)
    {
        MixinUtils.mc = mc;
    }

    public static Minecraft getMinecraft()
    {
        return mc;
    }

    public static float getCameraYaw()
    {
        return cameraYaw;
    }

    public static float getCameraPitch()
    {
        return cameraPitch;
    }

    public static int getCameraPerspective()
    {
        return cameraPerspective;
    }

    public static void setCameraPerspective(int perspective)
    {
        cameraPerspective = perspective;
    }

    public static boolean isFrontFacing()
    {
        return mc.options.thirdPerson && frontFacing;
    }

    public static void toggleFrontFacing()
    {
        frontFacing = !frontFacing;
    }

    public static void changeCameraPerspective()
    {
        cameraPerspective++;
        if (cameraPerspective > 2)
            cameraPerspective = 0;
    }

    public static boolean isPerspectiveToggled()
    {
        return perspectiveToggled;
    }

    public static void togglePerspective()
    {
        perspectiveToggled = !perspectiveToggled;
    }

    /**
     * Discord RPC
     */

    public static void initRPC()
    {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> System.out.println(String.format("RPC ready (%s#%s)", user.username, user.discriminator))).build();
        DiscordRPC.discordInitialize("834511436516556841", handlers, true);
    }

    public static void updateRPC(String state, String details)
    {
        DiscordRichPresence rich = new DiscordRichPresence.Builder(state).setDetails(details).setBigImage("minecraft", "HydraMixins").build();
        DiscordRPC.discordUpdatePresence(rich);
    }

    public static void setFrame(Frame frame)
    {
        MixinUtils.frame = frame;
    }

    public void toggleKeyState(int key, boolean value)
    {
        keyStates[key] = value;
    }
}
