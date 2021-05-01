package net.oldschoolminecraft.hydra;

import com.google.gson.Gson;
import net.minecraft.entity.player.PlayerBase;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class CosmeticUtils
{
    public static HashMap<PlayerBase, String> badges = new HashMap<>();
    public static HashMap<PlayerBase, Integer> nametag_colors = new HashMap<>();

    public static void obtainBadge(PlayerBase player)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Gson gson = new Gson();

                CosmeticServerResponse res = gson.fromJson(get(String.format("https://www.oldschoolminecraft.com/api/cosmetics/get_cosmetic?username=%s&type=badge", player.name)), CosmeticServerResponse.class);
                if (res.success)
                {
                    badges.put(player, res.value);
                    System.out.println("Successfully got badge for: " + player.name);
                } else
                    System.out.println("Failed to get badge for: " + player.name);
            }
        }).start();
    }

    public static void obtainNameColor(PlayerBase player)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Gson gson = new Gson();

                CosmeticServerResponse res = gson.fromJson(get(String.format("https://www.oldschoolminecraft.com/api/cosmetics/get_cosmetic?username=%s&type=nametag_color", player.name)), CosmeticServerResponse.class);
                if (res.success)
                {
                    if (res.value.equalsIgnoreCase("rainbow"))
                    {
                        nametag_colors.put(player, -1);
                        System.out.println(String.format("%s has a rainbow nametag", player.name));
                        return;
                    }

                    Color c = Color.decode(res.value);
                    int val = getIntFromColor(c.getRed(), c.getGreen(), c.getBlue());
                    nametag_colors.put(player, val);

                    System.out.println(String.format("Found nametag color for %s: %s", player.name, val));
                } else
                    System.out.println("Failed to get nametag color for: " + player.name);
            }
        }).start();
    }

    private static String get(String url)
    {
        try
        {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();
            return response.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static int getIntFromColor(int red, int green, int blue)
    {
        int rgb = red;
        rgb = (rgb << 8) + green;
        rgb = (rgb << 8) + blue;
        return rgb;
    }

    public class CosmeticServerResponse
    {
        public boolean success;
        public String value;
    }
}
