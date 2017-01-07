package enhancedportals.utility;

import enhancedportals.EnhancedPortals;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class Localization
{
    public static String get(String s)
    {
        return StatCollector.translateToLocal(EnhancedPortals.MOD_ID + "." + s);
    }

    public static String getChatError(String s)
    {
        return String.format(StatCollector.translateToLocal(EnhancedPortals.MOD_ID + ".error." + s), EnumChatFormatting.RED + StatCollector.translateToLocal(EnhancedPortals.MOD_ID + ".error") + EnumChatFormatting.WHITE);
    }

    public static String getChatSuccess(String s)
    {
        return String.format(StatCollector.translateToLocal(EnhancedPortals.MOD_ID + ".success." + s), EnumChatFormatting.GREEN + StatCollector.translateToLocal(EnhancedPortals.MOD_ID + ".success") + EnumChatFormatting.WHITE);
    }

    public static String getChatNotify(String s) {
        return String.format(StatCollector.translateToLocal(EnhancedPortals.MOD_ID + ".notify." + s), EnumChatFormatting.YELLOW + StatCollector.translateToLocal(EnhancedPortals.MOD_ID + ".notify") + EnumChatFormatting.WHITE);
    }
}
