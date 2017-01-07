package enhancedportals.item;

import enhancedportals.network.CommonProxy;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class ItemBlankPortalModule extends Item
{
    public static ItemBlankPortalModule instance;
    IIcon texture;

    public ItemBlankPortalModule(String n)
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setUnlocalizedName(n);
    }

    @Override
    public IIcon getIconFromDamage(int meta)
    {
        return texture;
    }

    @Override
    public void registerIcons(IIconRegister ir)
    {
        texture = ir.registerIcon("enhancedportals:blank_portal_module");
    }
}
