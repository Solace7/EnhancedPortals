package enhancedportals.network;

import enhancedportals.EnhancedPortals;
import enhancedportals.client.gui.*;
import enhancedportals.inventory.*;
import enhancedportals.item.ItemAddressBook;
import enhancedportals.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import static enhancedportals.Reference.GuiEnums.GUI_ADDRESS_BOOK.*;
import static enhancedportals.Reference.GuiEnums.GUI_CONTROLLER.*;
import static enhancedportals.Reference.GuiEnums.GUI_DIAL.*;
import static enhancedportals.Reference.GuiEnums.GUI_MISC.*;
import static enhancedportals.Reference.GuiEnums.GUI_TEXTURE.*;
import static enhancedportals.Reference.GuiEnums.GUI_TRANFER.*;

public class GuiHandler implements IGuiHandler
{

    public static void openGui(EntityPlayer player, TileEntity tile, int gui)
    {
        player.openGui(EnhancedPortals.instance, gui, tile.getWorld(), tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
    }

    public static void openGui(EntityPlayer player, int gui)
    {
        player.openGui(EnhancedPortals.instance, gui, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        ItemStack stack = player.getHeldItemMainhand();

        if (stack != null && stack.getItem() instanceof ItemAddressBook)
        {
            Item i = stack.getItem();

            ItemAddressBook addressBook = (ItemAddressBook) i;

            if (ID == AB_A.ordinal())
            {
                return new GuiAddressBook(addressBook, player);
            }
            else if (ID == AB_B.ordinal())
            {
                return new GuiAddressManual(addressBook, player);
            }
            else if (ID == AB_C.ordinal())
            {
                return new GuiAddressBookAdd(addressBook, player);
            }
            else if (ID == AB_D.ordinal())
            {
                return new GuiAddressEdit(addressBook, player);
            }
            else if (ID == AB_E.ordinal())
            {
                return new GuiAddressEditIdentifier(addressBook, player);
            }
        }

        TileEntity t = world.getTileEntity(new BlockPos(x, y, z));

        if (!(t instanceof TileEP))
        {
            return null;
        }

        TileEP tile = (TileEP) t;

        if (ID == CONTROLLER_A.ordinal())
        {
            return new GuiPortalController((TileController) tile, player);
        }
        else if (ID == CONTROLLER_B.ordinal())
        {
            return new GuiPortalControllerGlyphs((TileController) tile, player);
        }
        else if (ID == REDSTONE_INTERFACE.ordinal())
        {
            return new GuiRedstoneInterface((TileRedstoneInterface) tile, player);
        }
        else if (ID == NETWORK_INTERFACE_A.ordinal())
        {
            return new GuiNetworkInterface((TileController) tile, player);
        }
        else if (ID == NETWORK_INTERFACE_B.ordinal())
        {
            return new GuiNetworkInterfaceGlyphs((TileController) tile, player);
        }
        else if (ID == MODULE_MANIPULATOR.ordinal())
        {
            return new GuiModuleManipulator((TilePortalManipulator) tile, player);
        }
        else if (ID == DIM_BRIDGE_STABILIZER.ordinal())
        {
            return new GuiDimensionalBridgeStabilizer((TileStabilizerMain) tile, player);
        }
        else if (ID == DIAL_A.ordinal())
        {
            return new GuiDialingDevice((TileDialingDevice) tile, player);
        }
        else if (ID == DIAL_B.ordinal())
        {
            return new GuiDialingManual((TileDialingDevice) tile, player);
        }
        else if (ID == DIAL_C.ordinal())
        {
            return new GuiDialingAdd((TileDialingDevice) tile, player);
        }
        else if (ID == DIAL_D.ordinal())
        {
            return new GuiDialingEdit((TileDialingDevice) tile, player);
        }
        else if (ID == DIAL_E.ordinal())
        {
            return new GuiDialingEditIdentifier((TileDialingDevice) tile, player);
        }
        else if (ID == TEXTURE_A.ordinal())
        {
            return new GuiTextureFrame((TileController) tile, player);
        }
        else if (ID == TEXTURE_B.ordinal())
        {
            return new GuiTexturePortal((TileController) tile, player);
        }
        else if (ID == TEXTURE_C.ordinal())
        {
            return new GuiTextureParticle((TileController) tile, player);
        }
        else if (ID == TEXTURE_DIAL_SAVE_A.ordinal())
        {
            return new GuiDialingEditFrame((TileDialingDevice) tile, player, false);
        }
        else if (ID == TEXTURE_DIAL_SAVE_B.ordinal())
        {
            return new GuiDialingEditPortal((TileDialingDevice) tile, player, false);
        }
        else if (ID == TEXTURE_DIAL_SAVE_C.ordinal())
        {
            return new GuiDialingEditParticle((TileDialingDevice) tile, player, false);
        }
        else if (ID == TEXTURE_DIAL_EDIT_A.ordinal())
        {
            return new GuiDialingEditFrame((TileDialingDevice) tile, player, true);
        }
        else if (ID == TEXTURE_DIAL_EDIT_B.ordinal())
        {
            return new GuiDialingEditPortal((TileDialingDevice) tile, player, true);
        }
        else if (ID == TEXTURE_DIAL_EDIT_C.ordinal())
        {
            return new GuiDialingEditParticle((TileDialingDevice) tile, player, true);
        }
        else if (ID == TRANSFER_FLUID.ordinal())
        {
            return new GuiTransferFluid((TileTransferFluid) tile, player);
        }
        else if (ID == TRANSFER_ENERGY.ordinal())
        {
            return new GuiTransferEnergy((TileTransferEnergy) tile, player);
        }
        else if (ID == TRANSFER_ITEM.ordinal())
        {
            return new GuiTransferItem((TileTransferItem) tile, player);
        }
        else if (ID == ADDRESS_DIAL.ordinal())
        {
            if (stack != null && stack.getItem() instanceof ItemAddressBook)
            {
                Item i = stack.getItem();
                ItemAddressBook addressBook = (ItemAddressBook) i;

                return new GuiAddressDial((TileDialingDevice) tile, addressBook, player);
            }
        }
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        ItemStack stack = player.getHeldItemMainhand();

        if (stack != null && stack.getItem() instanceof ItemAddressBook)
        {
            Item i = stack.getItem();
            ItemAddressBook addressBook = (ItemAddressBook) i;

            if (ID == AB_A.ordinal())
            {
                addressBook.readFromNBT(stack);
                return new ContainerAddressBook(addressBook, player.inventory);
            }
            else if (ID == AB_B.ordinal())
            {
                return new ContainerAddressManual(addressBook, player.inventory);
            }
            else if (ID == AB_C.ordinal())
            {
                return new ContainerAddressBookAdd(addressBook, player.inventory);
            }
            else if (ID == AB_D.ordinal())
            {
                return new ContainerAddressEdit(addressBook, player.inventory);
            }
            else if (ID == AB_E.ordinal())
            {
                return new ContainerAddressEditIdentifier(addressBook, player.inventory);
            }
        }

        TileEntity t = world.getTileEntity(new BlockPos(x, y, z));

        if (!(t instanceof TileEP))
        {
            return null;
        }

        TileEP tile = (TileEP) t;

        if (ID == CONTROLLER_A.ordinal())
        {
            return new ContainerPortalController((TileController) tile, player.inventory);
        }
        else if (ID == CONTROLLER_B.ordinal())
        {
            return new ContainerPortalControllerGlyphs((TileController) tile, player.inventory);
        }
        else if (ID == REDSTONE_INTERFACE.ordinal())
        {
            return new ContainerRedstoneInterface((TileRedstoneInterface) tile, player.inventory);
        }
        else if (ID == NETWORK_INTERFACE_A.ordinal())
        {
            return new ContainerNetworkInterface((TileController) tile, player.inventory);
        }
        else if (ID == NETWORK_INTERFACE_B.ordinal())
        {
            return new ContainerNetworkInterfaceGlyphs((TileController) tile, player.inventory);
        }
        else if (ID == MODULE_MANIPULATOR.ordinal())
        {
            return new ContainerModuleManipulator((TilePortalManipulator) tile, player.inventory);
        }
        else if (ID == DIM_BRIDGE_STABILIZER.ordinal())
        {
            return new ContainerDimensionalBridgeStabilizer((TileStabilizerMain) tile, player.inventory);
        }
        else if (ID == DIAL_A.ordinal())
        {
            return new ContainerDialingDevice((TileDialingDevice) tile, player.inventory);
        }
        else if (ID == DIAL_B.ordinal())
        {
            return new ContainerDialingManual((TileDialingDevice) tile, player.inventory);
        }
        else if (ID == DIAL_C.ordinal())
        {
            return new ContainerDialingAdd((TileDialingDevice) tile, player.inventory);
        }
        else if (ID == DIAL_D.ordinal())
        {
            return new ContainerDialingEdit((TileDialingDevice) tile, player.inventory);
        }
        else if (ID == DIAL_E.ordinal())
        {
            return new ContainerDialingEditIdentifier((TileDialingDevice) tile, player.inventory);
        }
        else if (ID == TEXTURE_A.ordinal())
        {
            return new ContainerTextureFrame((TileController) tile, player.inventory);
        }
        else if (ID == TEXTURE_B.ordinal())
        {
            return new ContainerTexturePortal((TileController) tile, player.inventory);
        }
        else if (ID == TEXTURE_C.ordinal())
        {
            return new ContainerTextureParticle((TileController) tile, player.inventory);
        }
        else if (ID == TEXTURE_DIAL_EDIT_A.ordinal() || ID == TEXTURE_DIAL_SAVE_A.ordinal())
        {
            return new ContainerDialingEditTexture((TileDialingDevice) tile, player.inventory);
        }
        else if (ID == TEXTURE_DIAL_EDIT_B.ordinal() || ID == TEXTURE_DIAL_SAVE_B.ordinal())
        {
            return new ContainerDialingEditPortal((TileDialingDevice) tile, player.inventory);
        }
        else if (ID == TEXTURE_DIAL_EDIT_C.ordinal() || ID == TEXTURE_DIAL_SAVE_C.ordinal())
        {
            return new ContainerDialingEditParticle((TileDialingDevice) tile, player.inventory);
        }
        else if (ID == TRANSFER_FLUID.ordinal())
        {
            return new ContainerTransferFluid((TileTransferFluid) tile, player.inventory);
        }
        else if (ID == TRANSFER_ENERGY.ordinal())
        {
            return new ContainerTransferEnergy((TileTransferEnergy) tile, player.inventory);
        }
        else if (ID == TRANSFER_ITEM.ordinal())
        {
            return new ContainerTransferItem((TileTransferItem) tile, player.inventory);
        }
        else if (ID == ADDRESS_DIAL.ordinal())
        {
            if (stack != null && stack.getItem() instanceof ItemAddressBook)
            {
                Item i = stack.getItem();
                ItemAddressBook addressBook = (ItemAddressBook) i;

                return new ContainerAddressBookDiallingDevice((TileDialingDevice) tile, addressBook, player.inventory);
            }
        }
        return null;
    }
}
