package net.oldschoolminecraft.hydra.mixin.render;

import net.minecraft.class_214;
import net.minecraft.client.TexturePackManager;
import net.minecraft.client.resource.TexturePack;
import net.minecraft.client.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.HashMap;

/**
 * Attempts to grab missing resources from the internet.
 */
@Mixin(TextureManager.class)
public class MixinRenderEngine
{
    @Shadow private TexturePackManager field_1256;
    @Shadow private BufferedImage field_1257;
    @Shadow private IntBuffer field_1249;
    @Shadow private HashMap field_1246;
    @Shadow private boolean field_1254;
    @Shadow private boolean field_1255;

    /**
     * @author moderator_man
     */
    @Overwrite public int getTextureId(String s)
    {
        TexturePack texturepackbase = this.field_1256.texturePack;
        Integer integer = (Integer)this.field_1246.get(s);
        if (integer != null) {
            return integer;
        } else {
            try {
                this.field_1249.clear();
                class_214.method_742(this.field_1249);
                int i = this.field_1249.get(0);
                if (s.startsWith("##")) {
                    this.method_1089(this.method_1101(this.method_1091(texturepackbase.getResourceAsStream(s.substring(2)))), i);
                } else if (s.startsWith("%clamp%")) {
                    this.field_1254 = true;
                    this.method_1089(this.method_1091(texturepackbase.getResourceAsStream(s.substring(7))), i);
                    this.field_1254 = false;
                } else if (s.startsWith("%blur%")) {
                    this.field_1255 = true;
                    this.method_1089(this.method_1091(texturepackbase.getResourceAsStream(s.substring(6))), i);
                    this.field_1255 = false;
                } else {
                    InputStream inputstream = texturepackbase.getResourceAsStream(s);
                    if (inputstream == null)
                    {
                        inputstream = getFixedResource(s);
                        if (inputstream == null)
                            this.method_1089(this.field_1257, i);
                        else
                            this.method_1089(this.method_1091(inputstream), i);
                    } else {
                        this.method_1089(this.method_1091(inputstream), i);
                    }
                }

                this.field_1246.put(s, i);
                return i;
            } catch (IOException ex) {
                ex.printStackTrace();
                class_214.method_742(this.field_1249);
                int var4 = this.field_1249.get(0);
                this.method_1089(this.field_1257, var4);
                this.field_1246.put(s, var4);
                return var4;
            }
        }
    }

    @Shadow public void method_1089(BufferedImage bufferedimage, int i) {}
    @Shadow public BufferedImage method_1091(InputStream var1) throws IOException { return null; }
    @Shadow public BufferedImage method_1101(BufferedImage var1) { return null; }

    public InputStream getFixedResource(String resource)
    {
        try
        {
            final HttpURLConnection connection = (HttpURLConnection) new URL(resource).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
            return connection.getInputStream();
        } catch (Exception ex) {
            return null;
        }
    }
}
