package enhancedportals.client.gui;

import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import enhancedportals.Reference;
import enhancedportals.utility.ConfigurationHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EPGuiConfig extends GuiConfig
{

    public EPGuiConfig(GuiScreen guiScreen)
    {
        super(guiScreen,
                getConfigElements(),
                Reference.EPMod.mod_id,
                false,
                true,
                GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }

    public static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList();

        Set<String> categories = ConfigurationHandler.config.getCategoryNames();
        for (String s : categories)
        {
            if (!s.contains("."))
            {
                list.add(new DummyConfigElement.DummyCategoryElement(s, s, new ConfigElement(ConfigurationHandler.config.getCategory(s)).getChildElements()));
            }
        }

        return list;
    }


}
