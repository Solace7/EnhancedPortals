package enhancedportals.tile;

import enhancedportals.EnhancedPortals;
import enhancedportals.Reference;
import enhancedportals.item.ItemLocationCard;
import enhancedportals.item.ItemNanobrush;
import enhancedportals.network.GuiHandler;
import enhancedportals.network.packet.PacketRerender;
import enhancedportals.portal.*;
import enhancedportals.utility.*;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Random;

@InterfaceList(value = {@Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = Reference.Dependencies.MODID_OPENCOMPUTERS)})
public class TileController extends TileFrame implements SimpleComponent
{
    enum ControlState
    {
        REQUIRES_LOCATION, REQUIRES_WRENCH, FINALIZED
    }

    ArrayList<ChunkPos> portalFrames = new ArrayList<ChunkPos>();
    ArrayList<ChunkPos> portalBlocks = new ArrayList<ChunkPos>();
    ArrayList<ChunkPos> redstoneInterfaces = new ArrayList<ChunkPos>();
    ArrayList<ChunkPos> networkInterfaces = new ArrayList<ChunkPos>();
    ArrayList<ChunkPos> diallingDevices = new ArrayList<ChunkPos>();
    ArrayList<ChunkPos> transferFluids = new ArrayList<ChunkPos>();
    ArrayList<ChunkPos> transferItems = new ArrayList<ChunkPos>();
    ArrayList<ChunkPos> transferEnergy = new ArrayList<ChunkPos>();

    ChunkPos moduleManipulator;

    DimensionCoordinates dimensionalBridgeStabilizer, temporaryDBS;

    public PortalTextureManager activeTextureData = new PortalTextureManager(), inactiveTextureData;

    ControlState portalState = ControlState.REQUIRES_LOCATION;

    public int connectedPortals = -1, instability = 0, portalType = 0;

    boolean processing;
    public boolean isPublic;

    GlyphIdentifier cachedDestinationUID;
    DimensionCoordinates cachedDestinationLoc;

    @SideOnly(Side.CLIENT)
    GlyphIdentifier uID, nID;

//todo Activate

//   @Override
    public boolean activate(EntityPlayer player, ItemStack stack)
    {
        if (player.isSneaking())
        {
            return false;
        }

        try
        {
            if (stack != null)
            {
                if (portalState == ControlState.REQUIRES_LOCATION)
                {
                    if (stack.getItem() == ItemLocationCard.instance && !worldObj.isRemote)
                    {
                        boolean reconfiguring = dimensionalBridgeStabilizer != null;
                        setDBS(player, stack);
                        configurePortal();
                        player.addChatComponentMessage(new TextComponentString(Localization.getChatSuccess(!reconfiguring ? "create" : "reconfigure")));
                    }

                    return true;
                }
                else if (portalState == ControlState.REQUIRES_WRENCH)
                {
                    if (GeneralUtils.isWrench(stack) && !worldObj.isRemote)
                    {
                        configurePortal();
                        player.addChatComponentMessage(new TextComponentString(Localization.getChatSuccess("reconfigure")));
                    }

                    return true;
                }
                else if (portalState == ControlState.FINALIZED)
                {
                    if (GeneralUtils.isWrench(stack))
                    {
                        GuiHandler.openGui(player, this, Reference.GuiEnums.GUI_CONTROLLER.CONTROLLER_A.ordinal());
                        return true;
                    }
                    else if (stack.getItem() == ItemNanobrush.instance)
                    {
                        GuiHandler.openGui(player, this, Reference.GuiEnums.GUI_TEXTURE.TEXTURE_A.ordinal());
                        return true;
                    }
                }
            }
        }
        catch (PortalException e)
        {
            player.addChatComponentMessage(new TextComponentString(e.getMessage()));
        }

        return false;
    }

    @Override
    public void addDataToPacket(NBTTagCompound tag)
    {
        tag.setByte("PortalState", (byte) portalState.ordinal());
        tag.setBoolean("PortalActive", isPortalActive());
        tag.setInteger("Instability", instability);

        if (isPortalActive())
        {
            tag.setString("DestUID", cachedDestinationUID.getGlyphString());
            tag.setInteger("destX", cachedDestinationLoc.chunkXPos);
            tag.setInteger("destZ", cachedDestinationLoc.chunkZPos);
            tag.setInteger("destD", cachedDestinationLoc.dimension);
        }

        activeTextureData.writeToNBT(tag, "Texture");

        if (moduleManipulator != null)
        {
            tag.setInteger("ModX", moduleManipulator.chunkXPos);
            tag.setInteger("ModZ", moduleManipulator.chunkZPos);
        }
    }

    public void addDialDevice(ChunkPos ChunkPos)
    {
        diallingDevices.add(ChunkPos);
    }

    public void addNetworkInterface(ChunkPos ChunkPos)
    {
        networkInterfaces.add(ChunkPos);
    }

    public void addRedstoneInterface(ChunkPos ChunkPos)
    {
        redstoneInterfaces.add(ChunkPos);
    }

    public void addTransferEnergy(ChunkPos ChunkPos)
    {
        transferEnergy.add(ChunkPos);
    }

    public void addTransferFluid(ChunkPos ChunkPos)
    {
        transferFluids.add(ChunkPos);
    }

    public void addTransferItem(ChunkPos ChunkPos)
    {
        transferItems.add(ChunkPos);
    }

     /* @Override
    public void breakBlock(Block b, int oldMetadata)
    {
        try
        {
            deconstruct();
            setIdentifierNetwork(new GlyphIdentifier());
            setIdentifierUnique(new GlyphIdentifier());
        }
        catch (PortalException e)
        {
            e.printStackTrace();
        }
    }*/

     //todo canUpdate? (Bool)
  /*  @Override
    public boolean canUpdate()
    {
        return true;
    }*/


    void configurePortal() throws PortalException
    {
        ArrayList<ChunkPos> portalStructure = PortalUtils.getAllPortalComponents(this);

        for (ChunkPos c : portalStructure)
        {
            TileEntity tile = worldObj.getTileEntity(getPos());

            if (tile instanceof TileController)
            {

            }
            else if (tile instanceof TileFrameBasic)
            {
                portalFrames.add(c);
            }
            else if (tile instanceof TileRedstoneInterface)
            {
                redstoneInterfaces.add(c);
            }
            else if (tile instanceof TileNetworkInterface)
            {
                networkInterfaces.add(c);
            }
            else if (tile instanceof TileDialingDevice)
            {
                diallingDevices.add(c);
            }
            else if (tile instanceof TilePortalManipulator)
            {
                moduleManipulator = c;
            }
            else if (tile instanceof TileTransferFluid)
            {
                transferFluids.add(c);
            }
            else if (tile instanceof TileTransferItem)
            {
                transferItems.add(c);
            }
            else if (tile instanceof TileTransferEnergy)
            {
                transferEnergy.add(c);
            }
            else
            {
                portalBlocks.add(c);
                continue;
            }

            ((TilePortalPart) tile).setPortalController(getChunkPos());
        }

        portalState = ControlState.FINALIZED;
//        markDirty();
//   todo     worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        final IBlockState state = getWorld().getBlockState(getPos());
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
    }

    public void connectionDial()
    {
        if (worldObj.isRemote || getIdentifierNetwork() == null)
        {
            return;
        }

        try
        {
            TileStabilizerMain dbs = getDimensionalBridgeStabilizer();

            if (dbs == null)
            {
                portalState = ControlState.REQUIRES_LOCATION;
                throw new PortalException("stabilizerNotFound");
            }

            dbs.setupNewConnection(getIdentifierUnique(), EnhancedPortals.proxy.networkManager.getDestination(getIdentifierUnique(), getIdentifierNetwork()), null);
        }
        catch (PortalException e)
        {
            EntityPlayer player = worldObj.getClosestPlayer(getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, 128, false);

            if (player != null)
            {
                player.addChatComponentMessage(new TextComponentString(e.getMessage()));
            }
        }

        markDirty();
    }

    public void connectionDial(GlyphIdentifier id, PortalTextureManager m, EntityPlayer player)
    {
        if (worldObj.isRemote)
        {
            return;
        }

        try
        {
            TileStabilizerMain dbs = getDimensionalBridgeStabilizer();

            if (dbs == null)
            {
                portalState = ControlState.REQUIRES_LOCATION;
                throw new PortalException("stabilizerNotFound");
            }

            dbs.setupNewConnection(getIdentifierUnique(), id, m);
        }
        catch (PortalException e)
        {
            if (player == null)
            {
                player = worldObj.getClosestPlayer(getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, 128, false);
            }

            if (player != null)
            {
                player.addChatComponentMessage(new TextComponentString(e.getMessage()));
            }
        }

//        markDirty();
        final IBlockState state = getWorld().getBlockState(getPos());
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
    }

    public void connectionTerminate()
    {
        if (worldObj.isRemote || processing)
        {
            return;
        }

        try
        {
            TileStabilizerMain dbs = getDimensionalBridgeStabilizer();

            if (dbs == null)
            {
                portalState = ControlState.REQUIRES_LOCATION;
                throw new PortalException("stabilizerNotFound");
            }

            dbs.terminateExistingConnection(getIdentifierUnique());
        }
        catch (PortalException e)
        {
            LogHelper.catching(e);
        }

        temporaryDBS = null;
//        markDirty();
        final IBlockState state = getWorld().getBlockState(getPos());
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
    }

    /**
     * Deconstructs the portal structure.
     */
    public void deconstruct()
    {
        if (processing)
        {
            return;
        }

        if (isPortalActive())
        {
            connectionTerminate();
        }

        for (ChunkPos c : portalFrames)
        {
            TileEntity t = worldObj.getTileEntity(getPos());

            if (t != null && t instanceof TilePortalPart)
            {
                ((TilePortalPart) t).setPortalController(null);
            }
        }

        for (ChunkPos c : redstoneInterfaces)
        {
            TileEntity t = worldObj.getTileEntity(getPos());

            if (t != null && t instanceof TilePortalPart)
            {
                ((TilePortalPart) t).setPortalController(null);
            }
        }

        for (ChunkPos c : networkInterfaces)
        {
            TileEntity t = worldObj.getTileEntity(getPos());

            if (t != null && t instanceof TilePortalPart)
            {
                ((TilePortalPart) t).setPortalController(null);
            }
        }

        for (ChunkPos c : diallingDevices)
        {
            TileEntity t = worldObj.getTileEntity(getPos());

            if (t != null && t instanceof TilePortalPart)
            {
                ((TilePortalPart) t).setPortalController(null);
            }
        }

        for (ChunkPos c : transferFluids)
        {
            TileEntity t = worldObj.getTileEntity(getPos());

            if (t != null && t instanceof TilePortalPart)
            {
                ((TilePortalPart) t).setPortalController(null);
            }
        }

        for (ChunkPos c : transferItems)
        {
            TileEntity t = worldObj.getTileEntity(getPos());

            if (t != null && t instanceof TilePortalPart)
            {
                ((TilePortalPart) t).setPortalController(null);
            }
        }

        for (ChunkPos c : transferEnergy)
        {
            TileEntity t = worldObj.getTileEntity(getPos());

            if (t != null && t instanceof TilePortalPart)
            {
                ((TilePortalPart) t).setPortalController(null);
            }
        }

        if (moduleManipulator != null)
        {
            TileEntity t = worldObj.getTileEntity(getPos());

            if (t != null && t instanceof TilePortalPart)
            {
                ((TilePortalPart) t).setPortalController(null);
            }
        }

        portalBlocks.clear();
        portalFrames.clear();
        redstoneInterfaces.clear();
        networkInterfaces.clear();
        diallingDevices.clear();
        transferFluids.clear();
        transferItems.clear();
        transferEnergy.clear();
        moduleManipulator = null;
        portalState = ControlState.REQUIRES_WRENCH;
//        markDirty();
        final IBlockState state = getWorld().getBlockState(getPos());
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
        //todo MarkBlockForupdate
    }


    public void setupTemporaryDBS(TileStabilizerMain sA)
    {
        temporaryDBS = sA.getDimensionCoordinates();
        final IBlockState state = getWorld().getBlockState(getPos());
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
//        markDirty();
    }

    public void swapTextureData(PortalTextureManager textureManager)
    {
        inactiveTextureData = new PortalTextureManager(activeTextureData);
        activeTextureData = textureManager;
        final IBlockState state = getWorld().getBlockState(getPos());
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
//        markDirty();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("PortalState", portalState.ordinal());
        tagCompound.setInteger("Instability", instability);
        tagCompound.setInteger("PortalType", portalType);
        tagCompound.setBoolean("isPublic", isPublic);

        GeneralUtils.saveChunkCoordList(tagCompound, getFrames(), "Frames");
        GeneralUtils.saveChunkCoordList(tagCompound, getPortals(), "Portals");
        GeneralUtils.saveChunkCoordList(tagCompound, getRedstoneInterfaces(), "RedstoneInterfaces");
        GeneralUtils.saveChunkCoordList(tagCompound, getNetworkInterfaces(), "NetworkInterface");
        GeneralUtils.saveChunkCoordList(tagCompound, getDiallingDevices(), "DialDevice");
        GeneralUtils.saveChunkCoordList(tagCompound, getTransferEnergy(), "TransferEnergy");
        GeneralUtils.saveChunkCoordList(tagCompound, getTransferFluids(), "TransferFluid");
        GeneralUtils.saveChunkCoordList(tagCompound, getTransferItems(), "TransferItems");
        GeneralUtils.saveChunkCoord(tagCompound, moduleManipulator, "ModuleManipulator");
        GeneralUtils.saveWorldCoord(tagCompound, dimensionalBridgeStabilizer, "DimensionalBridgeStabilizer");
        GeneralUtils.saveWorldCoord(tagCompound, temporaryDBS, "TemporaryDBS");

        activeTextureData.writeToNBT(tagCompound, "ActiveTextureData");

        if (inactiveTextureData != null)
        {
            inactiveTextureData.writeToNBT(tagCompound, "InactiveTextureData");
        }

        if (cachedDestinationLoc != null)
        {
            GeneralUtils.saveWorldCoord(tagCompound, cachedDestinationLoc, "CachedDestinationLoc");
            tagCompound.setString("CachedDestinationUID", cachedDestinationUID.getGlyphString());
        }

        return tagCompound;
    }

    /**
     * Updates the data of the destination portal.
     *
     * @param id
     * @param wc
     */
    public void cacheDestination(GlyphIdentifier id, DimensionCoordinates wc)
    {
        cachedDestinationUID = id;
        cachedDestinationLoc = wc;
//        markDirty();
        //todo worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        final IBlockState state = getWorld().getBlockState(getPos());
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
    }

    public void setPortalColour(int colour)
    {
        activeTextureData.setPortalColour(colour);
//        markDirty();
        final IBlockState state = getWorld().getBlockState(getPos());
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
        sendUpdatePacket(true);
    }

    public void setPortalItem(ItemStack s)
    {
        activeTextureData.setPortalItem(s);
//        markDirty();
        final IBlockState state = getWorld().getBlockState(getPos());
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
        sendUpdatePacket(true);
    }

    public void setUID(GlyphIdentifier i)
    {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
        {
            if (i != null && i.size() == 0)
            {
                i = null;
            }

            uID = i;
        }
    }

    //////////////////
    //OpenComputers//
    /////////////////




    Object[] comp_GetUniqueIdentifier()
    {
        GlyphIdentifier identifier = getIdentifierUnique();

        if (identifier == null || identifier.isEmpty())
        {
            return new Object[]{""};
        }
        else
        {
            return new Object[]{identifier.getGlyphString()};
        }
    }

    Object[] comp_SetFrameColour(Object[] arguments) throws Exception
    {
        if (arguments.length > 1 || arguments.length == 1 && arguments[0].toString().length() == 0)
        {
            throw new Exception("Invalid arguments");
        }

        try
        {
            int hex = Integer.parseInt(arguments.length == 1 ? arguments[0].toString() : "FFFFFF", 16);
            setFrameColour(hex);
        }
        catch (NumberFormatException ex)
        {
            throw new Exception("Couldn't parse input as hexidecimal");
        }

        return new Object[]{true};
    }

    Object[] comp_SetParticleColour(Object[] arguments) throws Exception
    {
        if (arguments.length > 1 || arguments.length == 1 && arguments[0].toString().length() == 0)
        {
            throw new Exception("Invalid arguments");
        }

        try
        {
            setParticleColour(new PortalTextureManager().getParticleColour());
        }
        catch (NumberFormatException ex)
        {
            throw new Exception("Couldn't parse input as hexidecimal");
        }

        return new Object[]{true};
    }

    Object[] comp_SetPortalColour(Object[] arguments) throws Exception
    {
        if (arguments.length > 1 || arguments.length == 1 && arguments[0].toString().length() == 0)
        {
            throw new Exception("Invalid arguments");
        }

        try
        {
            int hex = Integer.parseInt(arguments.length == 1 ? arguments[0].toString() : "FFFFFF", 16);
            setPortalColour(hex);
        }
        catch (NumberFormatException ex)
        {
            throw new Exception("Couldn't parse input as hexidecimal");
        }

        return new Object[]{true};
    }

    Object[] comp_SetUniqueIdentifier(Object[] arguments) throws Exception
    {
        if (arguments.length == 0)
        {
            setIdentifierUnique(new GlyphIdentifier());
            return comp_GetUniqueIdentifier();
        }
        else if (arguments.length == 1)
        {
            String s = arguments[0].toString();
            s = s.replace(" ", GlyphIdentifier.GLYPH_SEPERATOR);

            String error = ComputerUtils.verifyGlyphArguments(s);
            if (error != null)
            {
                throw new Exception(error);
            }

            setIdentifierUnique(new GlyphIdentifier(s));
        }
        else
        {
            throw new Exception("Invalid arguments");
        }

        return comp_GetUniqueIdentifier();
    }

    @Override
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public String getComponentName()
    {
        return "ep_controller";
    }

    /**
     * @return Returns the destination portal UID.
     */
    public GlyphIdentifier getDestination()
    {
        return cachedDestinationUID;
    }

    /**
     * @return Returns the location of the TilePortalController of the destination portal.
     */
    public DimensionCoordinates getDestinationLocation()
    {
        return cachedDestinationLoc;
    }

    public TileDialingDevice getDialDeviceRandom()
    {
        ChunkPos dial = null;

        if (diallingDevices.isEmpty())
        {
            return null;
        }
        else if (diallingDevices.size() == 1)
        {
            dial = diallingDevices.get(0);
        }
        else
        {
            dial = diallingDevices.get(new Random().nextInt(diallingDevices.size()));
        }

        TileEntity tile = worldObj.getTileEntity(pos);

        if (tile != null && tile instanceof TileDialingDevice)
        {
            return (TileDialingDevice) tile;
        }

        return null;
    }

    public ArrayList<ChunkPos> getDiallingDevices()
    {
        return diallingDevices;
    }

    public TileStabilizerMain getDimensionalBridgeStabilizer()
    {
        if (temporaryDBS != null)
        {
            World w = temporaryDBS.getWorld();
//            TileEntity tile = w.getTileEntity(temporaryDBS.posX, temporaryDBS.posY, temporaryDBS.posZ);
            TileEntity tile = w.getTileEntity(getPos());

            if (tile instanceof TileStabilizerMain)
            {
                return (TileStabilizerMain) tile;
            }
            else if (tile instanceof TileStabilizer)
            {
                TileStabilizer t = (TileStabilizer) tile;
                TileStabilizerMain m = t.getMainBlock();

                if (m != null)
                {
                    temporaryDBS = m.getDimensionCoordinates();
                    return m;
                }
            }

            temporaryDBS = null;
        }

        if (dimensionalBridgeStabilizer != null)
        {
            World w = dimensionalBridgeStabilizer.getWorld();
//            todo TileEntity tile = w.getTileEntity(dimensionalBridgeStabilizer.posX, dimensionalBridgeStabilizer.posY, dimensionalBridgeStabilizer.posZ);
            TileEntity tile = w.getTileEntity(getPos());

            if (tile instanceof TileStabilizerMain)
            {
                return (TileStabilizerMain) tile;
            }
            else if (tile instanceof TileStabilizer)
            {
                TileStabilizer t = (TileStabilizer) tile;
                TileStabilizerMain m = t.getMainBlock();

                if (m != null)
                {
                    dimensionalBridgeStabilizer = m.getDimensionCoordinates();
                    return m;
                }
            }

            dimensionalBridgeStabilizer = null;
        }

        return null;
    }

    @Callback(direct = true, doc = "function():number -- Returns the hexadecimal colour of the portal frame.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] getFrameColour(Context context, Arguments args) throws Exception
    {
        return new Object[]{activeTextureData.getFrameColour()};
    }

    public ArrayList<ChunkPos> getFrames()
    {
        return portalFrames;
    }

    /***
     * @return Returns if this portal has a NID.
     */
    public boolean getHasIdentifierNetwork()
    {
        return getIdentifierNetwork() != null;
    }

    /***
     * @return Returns if this portal has a UID.
     */
    public boolean getHasIdentifierUnique()
    {
        return getIdentifierUnique() != null;
    }

    /**
     * @return Returns the NID of the network this portal is connected to.
     */
    public GlyphIdentifier getIdentifierNetwork()
    {
        if (worldObj.isRemote)
        {
            return nID;
        }

        return EnhancedPortals.proxy.networkManager.getPortalNetwork(getIdentifierUnique());
    }

    /**
     * @return Returns the UID of this portal.
     */
    public GlyphIdentifier getIdentifierUnique()
    {
        if (worldObj.isRemote)
        {
            return uID;
        }

        return EnhancedPortals.proxy.networkManager.getPortalIdentifier(getDimensionCoordinates());
    }

    public TilePortalManipulator getModuleManipulator()
    {
        if (moduleManipulator != null)
        {
//            TileEntity tile = worldObj.getTileEntity(moduleManipulator.posX, moduleManipulator.posY, moduleManipulator.posZ);
            TileEntity tile = worldObj.getTileEntity(getPos());

            if (tile instanceof TilePortalManipulator)
            {
                return (TilePortalManipulator) tile;
            }
        }

        return null;
    }

    public ArrayList<ChunkPos> getNetworkInterfaces()
    {
        return networkInterfaces;
    }

    @Override
    public TileController getPortalController()
    {
        return isFinalized() ? this : null;
    }

    public ArrayList<ChunkPos> getPortals()
    {
        return portalBlocks;
    }

    public ArrayList<ChunkPos> getRedstoneInterfaces()
    {
        return redstoneInterfaces;
    }

    public ArrayList<ChunkPos> getTransferEnergy()
    {
        return transferEnergy;
    }

    public ArrayList<ChunkPos> getTransferFluids()
    {
        return transferFluids;
    }

    public ArrayList<ChunkPos> getTransferItems()
    {
        return transferItems;
    }


    public boolean isFinalized()
    {
        return portalState == ControlState.FINALIZED;
    }

    /**
     * @return Portal active state.
     */
    public boolean isPortalActive()
    {
        return cachedDestinationUID != null;
    }

    @Override
    public void onDataPacket(NBTTagCompound tag)
    {
        portalState = ControlState.values()[tag.getByte("PortalState")];

        if (tag.hasKey("DestUID"))
        {
            cachedDestinationUID = new GlyphIdentifier(tag.getString("DestUID"));
            cachedDestinationLoc = new DimensionCoordinates(tag.getInteger("destX"), tag.getInteger("destZ"), tag.getInteger("destD"));
        }
        else
        {
            cachedDestinationUID = null;
            cachedDestinationLoc = null;
        }

        activeTextureData.readFromNBT(tag, "Texture");
        instability = tag.getInteger("Instability");

        if (tag.hasKey("ModX"))
        {
            moduleManipulator = new ChunkPos(tag.getInteger("ModX"), tag.getInteger("ModZ"));
        }

        ArrayList<ChunkPos> f = EnhancedPortals.proxy.getControllerList(getChunkPos());

        if (f != null)
        {
            for (ChunkPos frames : f)
            {

                //todo markBlockForUpdate
//                worldObj.markBlockForUpdate(frames.posX, frames.posY, frames.posZ);
                final IBlockState state = getWorld().getBlockState(getPos());
                worldObj.notifyBlockUpdate(getPos(), state, state, 3);
            }

            EnhancedPortals.proxy.clearControllerList(getChunkPos());
        }
    }

    public void onEntityEnterPortal(Entity entity, TilePortal tilePortal)
    {
        if (cachedDestinationLoc == null)
        {
            return;
        }

        // Set tile to the joined portal's controller.
        TileEntity tile = cachedDestinationLoc.getTileEntity(getPos());
        // Trigger redstone interfaces.
        onEntityTouchPortal(entity);

        if (tile != null && tile instanceof TileController)
        {
            TileController control = (TileController) tile;

            TilePortalManipulator manip = getModuleManipulator();

            if (manip != null)
            {
                if (manip.onEntityTeleport(entity))
                {
                    EntityManager.setEntityPortalCooldown(entity);
                }
            }

            try
            {
                EntityManager.transferEntity(entity, getPos(), this, control);
                control.onEntityTeleported(entity);
                control.onEntityTouchPortal(entity);
            }
            catch (PortalException e)
            {
                if (entity instanceof EntityPlayer)
                {
                    ((EntityPlayer) entity).addChatComponentMessage(new TextComponentString(e.getMessage()));
                }
            }
        }

    }

    public void onEntityTeleported(Entity entity)
    {
        TilePortalManipulator module = getModuleManipulator();

        if (module != null)
        {
            module.onEntityTeleported(entity);
        }

        //todo Will charge energy depending on size of the portal
    }

    public void onEntityTouchPortal(Entity entity)
    {
        for (ChunkPos c : getRedstoneInterfaces())
        {
            ((TileRedstoneInterface) worldObj.getTileEntity(getPos())).onEntityTeleport(entity);
        }
    }

    public void onPartFrameBroken()
    {
        deconstruct();
    }

    /**
     * Creates the portal block. Throws an {@link PortalException} if an error occurs.
     */
    public void portalCreate() throws PortalException
    {
        for (ChunkPos c : portalBlocks)
        {
            if (!worldObj.isAirBlock(getPos()))
            {
                if (ConfigurationHandler.CONFIG_PORTAL_DESTROYS_BLOCKS)
                {
                    worldObj.setBlockToAir(getPos());
                }
                else
                {
                    throw new PortalException("failedToCreatePortal");
                }
            }
        }

        for (ChunkPos c : portalBlocks)
        {
            //todo setBlock
            final IBlockState state = getWorld().getBlockState(getPos());
//            worldObj.setBlockState(getPos(), state, portalType, 2);
            worldObj.setBlockState(getPos(), state);

            TilePortal portal = (TilePortal) worldObj.getTileEntity(getPos());
            portal.portalController = getChunkPos();
        }

        for (ChunkPos c : getRedstoneInterfaces())
        {
            TileRedstoneInterface ri = (TileRedstoneInterface) worldObj.getTileEntity(getPos());
            ri.onPortalCreated();
        }
    }

    public void portalRemove()
    {
        if (processing)
        {
            return;
        }

        processing = true;

        for (ChunkPos c : portalBlocks)
        {
            worldObj.setBlockToAir(getPos());
        }

        for (ChunkPos c : getRedstoneInterfaces())
        {
            TileRedstoneInterface ri = (TileRedstoneInterface) worldObj.getTileEntity(getPos());
            ri.onPortalRemoved();
        }

        processing = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);

        portalState = ControlState.values()[tagCompound.getInteger("PortalState")];
        instability = tagCompound.getInteger("Instability");
        portalType = tagCompound.getInteger("PortalType");
        isPublic = tagCompound.getBoolean("isPublic");

        portalFrames = GeneralUtils.loadChunkCoordList(tagCompound, "Frames");
        portalBlocks = GeneralUtils.loadChunkCoordList(tagCompound, "Portals");
        redstoneInterfaces = GeneralUtils.loadChunkCoordList(tagCompound, "RedstoneInterfaces");
        networkInterfaces = GeneralUtils.loadChunkCoordList(tagCompound, "NetworkInterface");
        diallingDevices = GeneralUtils.loadChunkCoordList(tagCompound, "DialDevice");
        transferEnergy = GeneralUtils.loadChunkCoordList(tagCompound, "TransferEnergy");
        transferFluids = GeneralUtils.loadChunkCoordList(tagCompound, "TransferFluid");
        transferItems = GeneralUtils.loadChunkCoordList(tagCompound, "TransferItems");
        moduleManipulator = GeneralUtils.loadChunkCoord(tagCompound, "ModuleManipulator");
        dimensionalBridgeStabilizer = GeneralUtils.loadWorldCoord(tagCompound, "DimensionalBridgeStabilizer");
        temporaryDBS = GeneralUtils.loadWorldCoord(tagCompound, "TemporaryDBS");

        activeTextureData.readFromNBT(tagCompound, "ActiveTextureData");

        if (tagCompound.hasKey("InactiveTextureData"))
        {
            inactiveTextureData = new PortalTextureManager();
            inactiveTextureData.readFromNBT(tagCompound, "InactiveTextureData");
        }

        if (tagCompound.hasKey("CachedDestinationUID"))
        {
            cachedDestinationLoc = GeneralUtils.loadWorldCoord(tagCompound, "CachedDestinationLoc");
            cachedDestinationUID = new GlyphIdentifier(tagCompound.getString("CachedDestinationUID"));
        }
    }

    public void removeFrame(ChunkPos ChunkPos)
    {
        portalFrames.remove(ChunkPos);
    }

    public void revertTextureData()
    {
        if (inactiveTextureData == null)
        {
            return;
        }

        activeTextureData = new PortalTextureManager(inactiveTextureData);
        inactiveTextureData = null;
    }

    /***
     * Sends an update packet for the TilePortalController. Also sends one packet per chunk to notify the client it needs to re-render its portal/frame blocks.
     *
     * @param updateChunks
     *            Should we send packets to re-render the portal/frame blocks?
     */
    void sendUpdatePacket(boolean updateChunks)
    {
        //worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        markDirty();

        if (updateChunks)
        {
            ArrayList<ChunkPos> chunks = new ArrayList<ChunkPos>();
            chunks.add(new ChunkPos(getPos().getX() >> 4, getPos().getZ() >> 4));

            for (ChunkPos c : getFrames())
            {
                if (!chunks.contains(new ChunkPos(c.chunkXPos >> 4, c.chunkZPos >> 4)))
                {
                    EnhancedPortals.packetPipeline.sendToAllAround(new PacketRerender(getPos()), this);
                    chunks.add(new ChunkPos(c.chunkXPos >> 4, c.chunkZPos >> 4));
                }
            }
        }
    }

    public void setCustomFrameTexture(int tex)
    {
        activeTextureData.setCustomFrameTexture(tex);
        sendUpdatePacket(true);
    }

    public void setCustomPortalTexture(int tex)
    {
        activeTextureData.setCustomPortalTexture(tex);
        sendUpdatePacket(true);
    }

    void setDBS(EntityPlayer player, ItemStack stack) throws PortalException
    {
        DimensionCoordinates stabilizer = ItemLocationCard.getDBSLocation(stack);

        if (stabilizer == null || !(stabilizer.getTileEntity(getPos()) instanceof TileStabilizerMain))
        {
            ItemLocationCard.clearDBSLocation(stack);
            throw new PortalException("voidLinkCard");
        }
        else if (!stabilizer.equals(getDimensionalBridgeStabilizer()))
        {
            if (!player.capabilities.isCreativeMode)
            {
                stack.stackSize--;

                if (stack.stackSize <= 0)
                {
                    player.inventory.mainInventory[player.inventory.currentItem] = null;
                }
            }

            dimensionalBridgeStabilizer = stabilizer;
            getDimensionalBridgeStabilizer().addPortal(this);
        }

        markDirty();
    }



    public void setFrameColour(int colour)
    {
        activeTextureData.setFrameColour(colour);
        markDirty();
        sendUpdatePacket(true);
    }

    public void setFrameItem(ItemStack s)
    {
        activeTextureData.setFrameItem(s);
        markDirty();
        sendUpdatePacket(true);
    }

    public void setIdentifierNetwork(GlyphIdentifier id)
    {
        if (!getHasIdentifierUnique())
        {
            return;
        }

        if (isPortalActive())
        {
            connectionTerminate();
        }

        GlyphIdentifier uID = getIdentifierUnique();

        if (getHasIdentifierNetwork())
        {
            EnhancedPortals.proxy.networkManager.removePortalFromNetwork(uID, getIdentifierNetwork());
        }

        if (id.size() > 0)
        {
            EnhancedPortals.proxy.networkManager.addPortalToNetwork(uID, id);
        }

        //worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        markDirty();
    }

    public void setIdentifierUnique(GlyphIdentifier id) throws PortalException
    {
        if (EnhancedPortals.proxy.networkManager.getPortalLocation(id) != null) // Check to see if we already have a portal with this ID
        {
            if (getHasIdentifierUnique() && getIdentifierUnique().equals(id))
            {
                return;
            }

            throw new PortalException("");
        }

        if (isPortalActive())
        {
            connectionTerminate();
        }

        if (getHasIdentifierUnique()) // If already have an identifier
        {
            GlyphIdentifier networkIdentifier = null;

            if (getHasIdentifierNetwork()) // Check to see if it's in a network
            {
                networkIdentifier = getIdentifierNetwork();
                EnhancedPortals.proxy.networkManager.removePortalFromNetwork(getIdentifierUnique(), networkIdentifier); // Remove it if it is
            }

            EnhancedPortals.proxy.networkManager.removePortal(getDimensionCoordinates()); // Remove the old identifier

            if (id.size() > 0) // If the new identifier isn't blank
            {
                EnhancedPortals.proxy.networkManager.addPortal(id, getDimensionCoordinates()); // Add it

                if (networkIdentifier != null)
                {
                    EnhancedPortals.proxy.networkManager.addPortalToNetwork(id, networkIdentifier); // Re-add it to the network, if it was in one
                }
            }
        }
        else if (id.size() > 0)
        {
            EnhancedPortals.proxy.networkManager.addPortal(id, getDimensionCoordinates()); // Add the portal
        }

        //todo markBlockForUpdate
        //worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        markDirty();
    }

    /***
     * Sets the instability of the portal to the specified value. Value is only used clientside, to display effects.
     *
     * @param instabil
     *            Portal instability level.
     */
    public void setInstability(int instabil)
    {
        instability = instabil;
        markDirty();
        sendUpdatePacket(true);
    }

    public void setModuleManipulator(ChunkPos ChunkPos)
    {
        moduleManipulator = ChunkPos;
        markDirty();
    }

    public void setNID(GlyphIdentifier i)
    {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
        {
            if (i != null && i.size() == 0)
            {
                i = null;
            }

            nID = i;
        }
    }
    public void setParticleColour(int colour)
    {
        activeTextureData.setParticleColour(colour);
        markDirty();
        sendUpdatePacket(false); // Particles are generated by querying this
    }

    public void setParticleType(int type)
    {
        activeTextureData.setParticleType(type);
        markDirty();
        sendUpdatePacket(false); // Particles are generated by querying this
    }

    @Callback(doc = "function(color:number):boolean -- Sets the particle colour to the specified hexadecimal string.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] setParticleColour(Context context, Arguments args) throws Exception
    {
        return comp_SetParticleColour(ComputerUtils.argsToArray(args));
    }


    @Callback(doc = "function(color:number):boolean -- Sets the portal colour to the specified hexadecimal string.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] setPortalColour(Context context, Arguments args) throws Exception
    {
        return comp_SetPortalColour(ComputerUtils.argsToArray(args));
    }

    @Callback(doc = "function(uuid:string):string -- Sets the UID to the specified string. If no string is given it will reset the UID. Must be given as numbers separated by spaces.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] setUniqueIdentifier(Context context, Arguments args) throws Exception
    {
        return comp_SetUniqueIdentifier(ComputerUtils.argsToArray(args));
    }

    @Callback(direct = true, doc = "function():number -- Returns the hexadecimal colour of the particles.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] getParticleColour(Context context, Arguments args) throws Exception
    {
        return new Object[]{activeTextureData.getParticleColour()};
    }

    @Callback(direct = true, doc = "function():number -- Returns the hexadecimal colour of the portal.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] getPortalColour(Context context, Arguments args) throws Exception
    {
        return new Object[]{activeTextureData.getPortalColour()};
    }

    @Callback(direct = true, doc = "function():boolean -- Returns true if the portal has an active connection.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] isPortalActive(Context context, Arguments args)
    {
        return new Object[]{isPortalActive()};
    }


    @Callback(direct = true, doc = "function():string -- Returns a string containing the numeric glyph IDs of each glyph in the unique identifier.")
    @Method(modid = Reference.Dependencies.MODID_OPENCOMPUTERS)
    public Object[] getUniqueIdentifier(Context context, Arguments args) throws Exception
    {
        return comp_GetUniqueIdentifier();
    }
}
