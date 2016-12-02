package enhancedportals.item;

import buildcraft.api.tools.IToolWrench;
import enhancedportals.network.CommonProxy;
import enhancedportals.utility.IDismantleable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemWrench extends ItemEP implements IToolWrench
{
    public static ItemWrench instance;

    public ItemWrench()
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setUnlocalizedName("enhancedportals:wrench");
        setRegistryName("wrench");
        setMaxStackSize(1);
    }

    @Override
    public boolean canWrench(EntityPlayer player, int x, int y, int z)
    {
        return true;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return true;
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
        if (!world.isRemote && player.isSneaking())
        {
            Block block = world.getBlockState(pos).getBlock();

            if (block instanceof IDismantleable)
            {
                ((IDismantleable) block).dismantleBlock(player, world, pos);
                return EnumActionResult.SUCCESS;
            }
        }

        return EnumActionResult.FAIL;
    }


    @Override
    public void wrenchUsed(EntityPlayer player, int x, int y, int z)
    {

    }
}
