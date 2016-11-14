package enhancedportals.inventory;

import enhancedportals.tile.TileDialingDevice;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerDialingEditTexture extends ContainerTextureFrame
{
    TileDialingDevice dial;

    public ContainerDialingEditTexture(TileDialingDevice d, InventoryPlayer p)
    {
        super(d.getPortalController(), p);
        dial = d;
    }
}
