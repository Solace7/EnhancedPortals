package enhancedportals.client.gui;

import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import enhancedportals.Reference;
import enhancedportals.utility.ConfigurationHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuiEPConfig extends GuiConfig
{

    public GuiEPConfig(GuiScreen guiScreen)
    {
        super(guiScreen,
                getConfigElements(),
                Reference.EPMod.mod_id,
                false,
                false,
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
