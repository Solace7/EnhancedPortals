package enhancedportals.item;

import com.mojang.realmsclient.gui.ChatFormatting;
import enhancedportals.Reference;
import enhancedportals.client.PortalParticleFX;
import enhancedportals.network.ClientProxy;
import enhancedportals.network.CommonProxy;
import enhancedportals.registration.RegisterPotions;
import enhancedportals.tile.TilePortalManipulator;
import enhancedportals.utility.IPortalModule;
import enhancedportals.utility.Localization;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class ItemPortalModule extends Item implements IPortalModule
{
    public static enum PortalModule
    {
        REMOVE_PARTICLES(0, "remove_particles"),
        RAINBOW_PARTICLES(1, "rainbow_particles"),
        REMOVE_SOUNDS(2, "remove_sounds"),
        KEEP_MOMENTUM(3, "keep_momentum"),
        INVISIBLE_PORTAL(4, "invisible_portal"),
        TINTSHADE_PARTICLES(5, "tintshade_particles"),
        FACING(6, "facing"),
        FEATHERFALL(7, "featherfall");


        private final String name;
        private final int id;

        private PortalModule (int id, String name) {
            this.name = name;
            this.id = id;
        }
        public String getUniqueID()
        {
            ItemStack s = new ItemStack(instance, 1, ordinal());
            return ((IPortalModule) s.getItem()).getID(s);
        }

        public int getMetadata(){
            return this.id;
        }

        public String getName()
        {
            return name;
        }
    }

    public static ItemPortalModule instance;

 //todo fix potion

    public ItemPortalModule(String n)
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setRegistryName(Reference.EPMod.mod_id, n);
        setUnlocalizedName(getRegistryName().toString());
        setMaxDamage(0);
        setMaxStackSize(64);
        setHasSubtypes(true);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("Portal Module");

        if (stack.getItemDamage() == PortalModule.FACING.ordinal())
        {
            NBTTagCompound t = stack.getTagCompound();
            int i = 0;

            if (t != null)
            {
                i = t.getInteger("facing");
            }

            list.add(ChatFormatting.GRAY + Localization.get("gui.facing." + i));
        }

        list.add(ChatFormatting.DARK_GRAY + I18n.format(getUnlocalizedNameInefficiently(stack) + ".desc"));
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
    public boolean disableParticles(TilePortalManipulator moduleManipulator, ItemStack upgrade)
    {
        return upgrade.getItemDamage() == PortalModule.REMOVE_PARTICLES.ordinal();
    }

    @Override
    public boolean disablePortalRendering(TilePortalManipulator modulemanipulator, ItemStack upgrade)
    {
        return upgrade.getItemDamage() == PortalModule.INVISIBLE_PORTAL.ordinal();
    }

    @Override
    public String getID(ItemStack upgrade)
    {
        return "ep3." + upgrade.getItemDamage();
    }

    @Override
    public EnumRarity getRarity(ItemStack itemStack)
    {
        if (itemStack.getItemDamage() == PortalModule.INVISIBLE_PORTAL.ordinal())
        {
            return EnumRarity.EPIC;
        }
        else if (itemStack.getItemDamage() == PortalModule.KEEP_MOMENTUM.ordinal())
        {
            return EnumRarity.RARE;
        }

        return EnumRarity.COMMON;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        for (int i = 0; i < PortalModule.values().length; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + PortalModule.values()[stack.getItemDamage()].getName();
    }

    @Override
    public boolean keepMomentumOnTeleport(TilePortalManipulator tileModuleManipulator, ItemStack i)
    {
        return i.getItemDamage() == PortalModule.KEEP_MOMENTUM.ordinal();
    }

    @Override
    public void onEntityTeleportEnd(Entity entity, TilePortalManipulator moduleManipulator, ItemStack upgrade)
    {
        if (upgrade.getItemDamage() == PortalModule.FEATHERFALL.ordinal())
        {
            if (entity instanceof EntityPlayer)
            {
                ((EntityPlayer) entity).addPotionEffect(new PotionEffect(RegisterPotions.featherfallPotion, 200, 0));
            }
            else if (entity instanceof EntityLiving)
            {
                ((EntityLiving) entity).addPotionEffect(new PotionEffect(RegisterPotions.featherfallPotion, 200, 0));
            }
        }
    }

    @Override
    public boolean onEntityTeleportStart(Entity entity, TilePortalManipulator moduleManipulator, ItemStack upgrade)
    {
        if (upgrade.getItemDamage() == PortalModule.FEATHERFALL.ordinal())
        {
            if (entity instanceof EntityPlayer)
            {
                ((EntityPlayer) entity).addPotionEffect(new PotionEffect(RegisterPotions.featherfallPotion, 200, 0));
            }
            else if (entity instanceof EntityLiving)
            {
                ((EntityLiving) entity).addPotionEffect(new PotionEffect(RegisterPotions.featherfallPotion, 200, 0));
            }
        }

        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        if (player.isSneaking() && stack.getItemDamage() == PortalModule.FACING.ordinal())
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

        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }

    @Override
    public void onParticleCreated(TilePortalManipulator moduleManipulator, ItemStack upgrade, PortalParticleFX particle)
    {
        if (upgrade.getItemDamage() == PortalModule.RAINBOW_PARTICLES.ordinal())
        {
            particle.setRBGColorF((float) Math.random(), (float) Math.random(), (float) Math.random());
        }
        else if (upgrade.getItemDamage() == PortalModule.TINTSHADE_PARTICLES.ordinal())
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
}
