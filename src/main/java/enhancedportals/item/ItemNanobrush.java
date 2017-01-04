package enhancedportals.item;

import enhancedportals.network.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class ItemNanobrush extends ItemEP
{
    public static ItemNanobrush instance;


    public ItemNanobrush()
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setUnlocalizedName("nanobrush");
        setMaxStackSize(1);
        setRegistryName("nanobrush");
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return true;
    }

}
