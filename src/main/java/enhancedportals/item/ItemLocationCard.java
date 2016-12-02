package enhancedportals.item;

import enhancedportals.network.CommonProxy;
import enhancedportals.utility.DimensionCoordinates;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class ItemLocationCard extends ItemEP
{
    public static ItemLocationCard instance;

    public ItemLocationCard()
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setUnlocalizedName("location_card");
        setMaxDamage(0);
        setHasSubtypes(true);
        setRegistryName("location_card");
    }

    public static void clearDBSLocation(ItemStack s)
    {
        s.setTagCompound(null);
    }

    public static DimensionCoordinates getDBSLocation(ItemStack s)
    {
        if (hasDBSLocation(s))
        {
            NBTTagCompound t = s.getTagCompound();
            return new DimensionCoordinates(t.getInteger("X"), t.getInteger("Z"), t.getInteger("D"));
        }

        return null;
    }

    public static boolean hasDBSLocation(ItemStack s)
    {
        return s.hasTagCompound();
    }


    //todo setDBS
    public static void setDBSLocation(ItemStack s, DimensionCoordinates w)
    {
        NBTTagCompound t = new NBTTagCompound();
        t.setInteger("X", w.chunkXPos);
        t.setInteger("Z", w.chunkZPos);
        t.setInteger("D", w.dimension);

        s.setTagCompound(t);
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        DimensionCoordinates w = getDBSLocation(stack);

        if (w != null)
        {
            list.add("Location set");
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        if (player.isSneaking() && hasDBSLocation(stack))
        {
            clearDBSLocation(stack);
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        }

        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }

}
