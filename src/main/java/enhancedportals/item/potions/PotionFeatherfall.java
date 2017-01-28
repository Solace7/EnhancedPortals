package enhancedportals.item.potions;

import net.minecraft.potion.Potion;

public class PotionFeatherfall extends Potion
{

    public PotionFeatherfall(boolean isBad, int liquidColor)
    {
        super(isBad, liquidColor);
        setIconIndex(0, 0);
        setPotionName("potion.featherfall");
    }

    
}
