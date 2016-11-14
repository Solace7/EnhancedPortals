package enhancedportals.inventory;

import enhancedportals.tile.TileDialingDevice;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerDialingEditParticle extends ContainerTextureParticle
{
    TileDialingDevice dial;

    public ContainerDialingEditParticle(TileDialingDevice d, InventoryPlayer p)
    {
        super(d.getPortalController(), p);
        dial = d;
    }
}
