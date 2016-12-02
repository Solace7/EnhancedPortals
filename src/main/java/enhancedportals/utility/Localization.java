package enhancedportals.utility;

import com.mojang.realmsclient.gui.ChatFormatting;
import enhancedportals.EnhancedPortals;
import net.minecraft.client.resources.I18n;

public class Localization
{
    public static String get(String s)
    {
        return I18n.format(EnhancedPortals.MOD_ID + "." + s);
    }

    public static String getChatError(String s)
    {
        return I18n.format((EnhancedPortals.MOD_ID + ".error." + s), ChatFormatting.RED + (EnhancedPortals.MOD_ID + ".error") + ChatFormatting.WHITE);
    }

    public static String getChatSuccess(String s)
    {
        return I18n.format((EnhancedPortals.MOD_ID + ".success." + s), ChatFormatting.GREEN + (EnhancedPortals.MOD_ID + ".success") + ChatFormatting.WHITE);
    }

    public static String getChatNotify(String s) {
        return I18n.format((EnhancedPortals.MOD_ID + ".notify." + s), ChatFormatting.YELLOW + (EnhancedPortals.MOD_ID + ".notify") + ChatFormatting.WHITE);
    }
}
