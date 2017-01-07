package enhancedportals.item;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import enhancedportals.network.CommonProxy;
import enhancedportals.network.GuiHandler;
import enhancedportals.portal.GlyphElement;
import enhancedportals.portal.GlyphIdentifier;
import enhancedportals.portal.PortalTextureManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ItemAddressBook extends ItemEP
{
    public ArrayList<GlyphElement> glyphList = new ArrayList<GlyphElement>();
    public static ItemAddressBook instance;
    IIcon texture;

    public ItemAddressBook(String n)
    {
        super();
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setUnlocalizedName(n);
        setMaxDamage(0);
        setMaxStackSize(1);
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();

        tag.setInteger("Entries", glyphList.size());
        tag.setTag("glyphList", list);

        itemStack.setTagCompound(tag);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("Entries"))
        {
            NBTTagCompound nbt = itemStack.getTagCompound();
            nbt.setInteger("Entries", glyphList.size());
            itemStack.setTagCompound(nbt);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4)
    {
        list.add("Entries: " + glyphList.size());
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
        return texture;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        texture = iconRegister.registerIcon("enhancedportals:address_book");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        readFromNBT(itemStack);
        GuiHandler.openGui(player, GuiHandler.ADDRESS_BOOK_A);

        return itemStack;
    }

    @Override
    public void packetGuiFill(ByteBuf buffer)
    {
        buffer.writeInt(glyphList.size());

        for (int i = 0; i < glyphList.size(); i++)
        {
            ByteBufUtils.writeUTF8String(buffer, glyphList.get(i).name);
            ByteBufUtils.writeUTF8String(buffer, glyphList.get(i).identifier.getGlyphString());
        }
    }

    @Override
    public void packetGuiUse(ByteBuf buffer)
    {
        int max = buffer.readInt();
        glyphList.clear();

        for (int i = 0; i < max; i++)
        {
            glyphList.add(new GlyphElement(ByteBufUtils.readUTF8String(buffer), new GlyphIdentifier(ByteBufUtils.readUTF8String(buffer))));
        }
    }

    @Override
    public void readFromNBT(ItemStack itemStack)
    {
        NBTTagCompound tag = itemStack.getTagCompound();

                NBTTagList list = tag.getTagList("glyphList", Constants.NBT.TAG_LIST);

                for (int i = 0; i < list.tagCount(); i++)
                {
                    NBTTagCompound t = list.getCompoundTagAt(i);
                    String name = t.getString("Name"), glyph = t.getString("Identifier");

                    if (t.hasKey("texture"))
                    {
                        PortalTextureManager tex = new PortalTextureManager();
                        tex.readFromNBT(t, "texture");

                        glyphList.add(new GlyphElement(name, new GlyphIdentifier(glyph), tex));
                    }
                    else
                    {
                        glyphList.add(new GlyphElement(name, new GlyphIdentifier(glyph)));
                    }
                }
    }


    public void writeToNBT(ItemStack itemStack)
    {
             NBTTagList list = new NBTTagList();

            for (int i = 0; i < glyphList.size(); i++)
            {
                NBTTagCompound t = new NBTTagCompound();
                GlyphElement e = glyphList.get(i);
                t.setString("Name", e.name);
                t.setString("Identifier", e.identifier.getGlyphString());

                if (e.hasSpecificTexture())
                {
                    e.texture.writeToNBT(t, "texture");
                }
                list.appendTag(t);
            }
            itemStack.getTagCompound().setTag("glyphList", list);
    }
}