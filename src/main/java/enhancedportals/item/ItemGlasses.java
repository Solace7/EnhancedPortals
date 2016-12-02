package enhancedportals.item;

import enhancedportals.EnhancedPortals;
import enhancedportals.network.CommonProxy;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemGlasses extends ItemArmor
{
    public static ItemGlasses instance;


    public ItemGlasses()
    {
        super(ArmorMaterial.LEATHER, EnhancedPortals.proxy.gogglesRenderIndex, EntityEquipmentSlot.HEAD);
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setUnlocalizedName("enhancedportals:glasses");
        setRegistryName("glasses");
    }

    @Override
    public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2)
    {
        return false;
    }

}
