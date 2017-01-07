package enhancedportals.item;

import enhancedportals.utility.ConfigurationHandler;
import net.minecraft.potion.Potion;

public class PotionFeatherfall extends Potion
{

    public PotionFeatherfall(boolean isBad, int liquidColor)
    {
        super(ConfigurationHandler.CONFIG_POTION_FEATHERFALL_ID, isBad, liquidColor);
        setIconIndex(0, 0);
        setPotionName("potion.featherfall");
    }
}
