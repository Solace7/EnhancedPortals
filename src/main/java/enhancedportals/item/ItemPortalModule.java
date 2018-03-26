package enhancedportals.item;

import enhancedportals.client.PortalParticleFX;
import enhancedportals.network.ClientProxy;
import enhancedportals.network.CommonProxy;
import enhancedportals.registration.RegisterPotions;
import enhancedportals.tile.TilePortalManipulator;
import enhancedportals.utility.IPortalModule;
import enhancedportals.utility.Localization;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemPortalModule extends Item implements IPortalModule
{
    public enum PortalModules
    {
        REMOVE_PARTICLES, RAINBOW_PARTICLES, REMOVE_SOUNDS, KEEP_MOMENTUM, INVISIBLE_PORTAL, TINTSHADE_PARTICLES,FACING, FEATHERFALL, LOOKING_GLASS;

        public String getUniqueID()
        {
            ItemStack s = new ItemStack(instance, 1, ordinal());
            return ((IPortalModule) s.getItem()).getID(s);
        }
    }

    public static ItemPortalModule instance;

    static IIcon baseIcon;
    static IIcon[] overlayIcons = new IIcon[PortalModules.values().length];

    public ItemPortalModule(String n)
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setUnlocalizedName(n);
        setMaxDamage(0);
        setMaxStackSize(64);
        setHasSubtypes(true);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("Portal Module");

        if (stack.getItemDamage() == PortalModules.FACING.ordinal())
        {
            NBTTagCompound t = stack.getTagCompound();
            int i = 0;

            if (t != null)
            {
                i = t.getInteger("facing");
            }

            list.add(EnumChatFormatting.GRAY + Localization.get("gui.facing." + i));
        }

        list.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".desc"));
    }

    @Override
    public boolean canInstallUpgrade(TilePortalManipulator moduleManipulator, IPortalModule[] installedUpgrades, ItemStack upgrade)
    {
        return true;
    }

    @Override
    public boolean canRemoveUpgrade(TilePortalManipulator moduleManipulator, IPortalModule[] installedUpgrades, ItemStack upgrade)
    {
        return true;
    }

    @Override
    public boolean enableLookingGlass(TilePortalManipulator moduleManipulator, ItemStack upgrade)
    {
        return upgrade.getItemDamage() == PortalModules.LOOKING_GLASS.ordinal();
    }

    @Override
    public boolean disableParticles(TilePortalManipulator moduleManipulator, ItemStack upgrade)
    {
        return upgrade.getItemDamage() == PortalModules.REMOVE_PARTICLES.ordinal();
    }

    @Override
    public boolean disablePortalRendering(TilePortalManipulator modulemanipulator, ItemStack upgrade)
    {
        return upgrade.getItemDamage() == PortalModules.INVISIBLE_PORTAL.ordinal();
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
        if (pass == 1)
        {
            return overlayIcons[damage];
        }

        return baseIcon;
    }

    @Override
    public String getID(ItemStack upgrade)
    {
        return "ep3." + upgrade.getItemDamage();
    }

    @Override
    public EnumRarity getRarity(ItemStack itemStack)
    {
        if (itemStack.getItemDamage() == PortalModules.INVISIBLE_PORTAL.ordinal())
        {
            return EnumRarity.epic;
        }
        else if (itemStack.getItemDamage() == PortalModules.KEEP_MOMENTUM.ordinal())
        {
            return EnumRarity.rare;
        }

        return EnumRarity.common;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        for (int i = 0; i < PortalModules.values().length; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }

    @Override
    public boolean keepMomentumOnTeleport(TilePortalManipulator tileModuleManipulator, ItemStack i)
    {
        return i.getItemDamage() == PortalModules.KEEP_MOMENTUM.ordinal();
    }

    @Override
    public void onEntityTeleportEnd(Entity entity, TilePortalManipulator moduleManipulator, ItemStack upgrade)
    {
        if (upgrade.getItemDamage() == PortalModules.FEATHERFALL.ordinal())
        {
            if (entity instanceof EntityPlayer)
            {
                ((EntityPlayer) entity).addPotionEffect(new PotionEffect(RegisterPotions.featherfallPotion.getId(), 200, 0));
            }
            else if (entity instanceof EntityLiving)
            {
                ((EntityLiving) entity).addPotionEffect(new PotionEffect(RegisterPotions.featherfallPotion.getId(), 200, 0));
            }
        }
    }

    @Override
    public boolean onEntityTeleportStart(Entity entity, TilePortalManipulator moduleManipulator, ItemStack upgrade)
    {
        if (upgrade.getItemDamage() == PortalModules.FEATHERFALL.ordinal())
        {
            if (entity instanceof EntityPlayer)
            {
                ((EntityPlayer) entity).addPotionEffect(new PotionEffect(RegisterPotions.featherfallPotion.getId(), 200, 0));
            }
            else if (entity instanceof EntityLiving)
            {
                ((EntityLiving) entity).addPotionEffect(new PotionEffect(RegisterPotions.featherfallPotion.getId(), 200, 0));
            }
        }

        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (player.isSneaking() && stack.getItemDamage() == PortalModules.FACING.ordinal())
        {
            NBTTagCompound tag = stack.getTagCompound();

            if (tag == null)
            {
                tag = new NBTTagCompound();
                tag.setInteger("facing", 1);
            }
            else
            {
                int face = tag.getInteger("facing") + 1;

                if (face >= 4)
                {
                    face = 0;
                }

                tag.setInteger("facing", face);
            }

            stack.setTagCompound(tag);
        }

        return stack;
    }

    @Override
    public void onParticleCreated(TilePortalManipulator moduleManipulator, ItemStack upgrade, PortalParticleFX particle)
    {
        if (upgrade.getItemDamage() == PortalModules.RAINBOW_PARTICLES.ordinal())
        {
            particle.setRBGColorF((float) Math.random(), (float) Math.random(), (float) Math.random());
        }
        else if (upgrade.getItemDamage() == PortalModules.TINTSHADE_PARTICLES.ordinal())
        {
            float particleRed = particle.getRedColorF(), particleGreen = particle.getGreenColorF(), particleBlue = particle.getBlueColorF();
            int i = ClientProxy.random.nextInt(3);

            if (i == 0)
            {
                particleRed *= particleRed / 4 * 3;
                particleGreen *= particleGreen / 4 * 3;
                particleBlue *= particleBlue / 4 * 3;
            }
            else if (i == 1)
            {
                particleRed = (255 - particleRed) * (particleRed / 4 * 3);
                particleGreen = (255 - particleGreen) * (particleGreen / 4 * 3);
                particleBlue = (255 - particleBlue) * (particleBlue / 4 * 3);
            }

            particle.setRBGColorF(particleRed, particleGreen, particleBlue);
        }
    }

    @Override
    public void onPortalCreated(TilePortalManipulator moduleManipulator, ItemStack upgrade)
    {

    }

    @Override
    public void onPortalRemoved(TilePortalManipulator moduleManipulator, ItemStack upgrade)
    {

    }

    @Override
    public void onUpgradeInstalled(TilePortalManipulator moduleManipulator, ItemStack upgrade)
    {

    }

    @Override
    public void onUpgradeRemoved(TilePortalManipulator moduleManipulator, ItemStack upgrade)
    {

    }

    @Override
    public void registerIcons(IIconRegister register)
    {
        baseIcon = register.registerIcon("enhancedportals:blank_portal_module");

        for (int i = 0; i < overlayIcons.length; i++)
        {
            overlayIcons[i] = register.registerIcon("enhancedportals:portal_module_" + i);
        }
    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
}
