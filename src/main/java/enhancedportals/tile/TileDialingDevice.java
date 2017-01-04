package enhancedportals.tile;

import enhancedportals.Reference;
import enhancedportals.item.ItemAddressBook;
import enhancedportals.network.GuiHandler;
import enhancedportals.portal.GlyphElement;
import enhancedportals.portal.GlyphIdentifier;
import enhancedportals.portal.PortalTextureManager;
import enhancedportals.utility.ComputerUtils;
import enhancedportals.utility.Localization;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.ArrayList;

@InterfaceList(value = {@Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = Reference.Dependencies.MODID_COMPUTERCRAFT), @Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = Reference.Dependencies.MODID_OPENCOMPUTERS)})

public class TileDialingDevice extends TileFrame implements SimpleComponent
{

    public ArrayList<GlyphElement> glyphList = new ArrayList<GlyphElement>();

    //todo activate on Right click?

//    @Override
    public boolean activate(EntityPlayer player, ItemStack stack)
    {
        TileController controller = getPortalController();

        if (worldObj.isRemote)
        {
            return controller != null;
        }

        if (controller != null && controller.isFinalized())
        {
            if (controller.getIdentifierUnique() == null)
            {
                player.addChatComponentMessage(new TextComponentString(Localization.getChatError("noUidSet")));
            }
            else if (!player.isSneaking())
            {
                GuiHandler.openGui(player, this, Reference.GuiEnums.GUI_DIAL.DIAL_A.ordinal());
            }
            else if (controller.isPortalActive())
            {
                controller.connectionTerminate();
            }
            else if (!player.isSneaking() && stack.getItem() instanceof ItemAddressBook)
            {
                player.addChatComponentMessage(new TextComponentString(Localization.getChatNotify("unimplemented")));
            }
        }
        else
        {
            GuiHandler.openGui(player, this, Reference.GuiEnums.GUI_DIAL.DIAL_B.ordinal());
        }
        return true;
    }

    @Override
    public void addDataToPacket(NBTTagCompound tag)
    {

    }

    @Override
    public void onDataPacket(NBTTagCompound tag)
    {

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
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        NBTTagList list = tag.getTagList("glyphList", Constants.NBT.TAG_COMPOUND);

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

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
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

        tag.setTag("glyphList", list);

        return tag;
    }

    /////////////////
    //OpenComputers//
    /////////////////

    @Override
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public String getComponentName()
    {
        return "ep_dialling_device";
    }


    @Callback(doc = "function(uid:string):boolean -- Attempts to create a connection to the specified portal. UID must be given as a single string in the format of numbers separated by spaces.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] dial(Context context, Arguments args) throws Exception
    {
        if (args.count() < 1)
        {
            return null;
        }

        return comp_Dial(ComputerUtils.argsToArray(args));
    }

    @Callback(doc = "function(entry:number):boolean -- Dials the specified entry in the Dialing Device's list.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] dialStored(Context context, Arguments args) throws Exception
    {
        return comp_DialStored(ComputerUtils.argsToArray(args));
    }

  @Callback(direct = true, doc = "function():number -- Returns the amount of entries in the Dialing Device's list.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] getStoredCount(Context context, Arguments args)
    {
        return new Object[]{glyphList.size()};
    }

    @Callback(direct = true, doc = "function(entry:number):string -- Returns the UID as a string of the specified entry in the Dialing Device's list.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] getStoredGlyph(Context context, Arguments args) throws Exception
    {
        return comp_GetStoredGlyph(ComputerUtils.argsToArray(args));
    }

    @Callback(direct = true, doc = "function(entry:number):string -- Returns the name of the specified entry in the Dialing Device's list.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] getStoredName(Context context, Arguments args) throws Exception
    {
        return comp_GetStoredName(ComputerUtils.argsToArray(args));
    }

    @Callback(doc = "function():boolean -- Terminates any active connection.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] terminate(Context context, Arguments args)
    {
        getPortalController().connectionTerminate();
        return new Object[]{true};
    }




    Object[] comp_Dial(Object[] arguments) throws Exception
    {
        if (arguments.length == 1)
        {
            String s = arguments[0].toString();
            s = s.replace(" ", GlyphIdentifier.GLYPH_SEPERATOR);

            String error = ComputerUtils.verifyGlyphArguments(s);
            if (error != null)
            {
                throw new Exception(error);
            }

            getPortalController().connectionDial(new GlyphIdentifier(s), null, null);
        }
        else
        {
            throw new Exception("Invalid arguments");
        }

        return new Object[]{true};
    }

    Object[] comp_DialStored(Object[] arguments) throws Exception
    {
        int num = getSelectedEntry(arguments);

        if (num >= 0 && num < glyphList.size())
        {
            getPortalController().connectionDial(glyphList.get(num).identifier, null, null);
        }

        return new Object[]{true};
    }

    Object[] comp_GetStoredGlyph(Object[] arguments) throws Exception
    {
        int num = getSelectedEntry(arguments);
        GlyphElement entry = glyphList.get(num);

        if (entry != null)
        {
            return new Object[]{entry.identifier.getGlyphString()};
        }
        else
        {
            throw new Exception("Entry not found");
        }
    }

    Object[] comp_GetStoredName(Object[] arguments) throws Exception
    {
        int num = getSelectedEntry(arguments);
        GlyphElement entry = glyphList.get(num);

        if (entry != null)
        {
            return new Object[]{entry.name};
        }
        else
        {
            throw new Exception("Entry not found");
        }
    }


    int getSelectedEntry(Object[] arguments) throws Exception
    {
        try
        {
            if (arguments.length == 1)
            {
                if (arguments[0].toString().contains("."))
                {
                    arguments[0] = arguments[0].toString().substring(0, arguments[0].toString().indexOf("."));
                }

                int i = Integer.parseInt(arguments[0].toString());

                if (i < 0 || i >= glyphList.size())
                {
                    throw new Exception("There is no entry in location " + i);
                }

                return i;
            }
        }
        catch (NumberFormatException e)
        {
            throw new Exception(arguments[0].toString() + " is not an integer.");
        }

        throw new Exception("Invalid number of arguments.");
    }

}