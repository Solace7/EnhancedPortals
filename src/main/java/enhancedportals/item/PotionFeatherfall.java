package enhancedportals.item;

import net.minecraft.potion.Potion;

public class PotionFeatherfall extends Potion
{

    public PotionFeatherfall(int id, boolean isBad, int liquidColor)
    {
        super(id, isBad, liquidColor);
        setIconIndex(0, 0);
        setPotionName("potion.featherfall");
    }
}
