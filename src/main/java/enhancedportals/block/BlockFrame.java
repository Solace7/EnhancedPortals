package enhancedportals.block;

import enhancedportals.network.CommonProxy;
import enhancedportals.tile.*;
import enhancedportals.utility.ConnectedTexturesDetailed;
import enhancedportals.utility.IDismantleable;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static enhancedportals.block.BlockFrame.FrameType.*;

public class BlockFrame extends BlockContainer implements IDismantleable
{
    public static BlockFrame instance;
    public static ConnectedTexturesDetailed connectedTextures;
   /* public static int PORTAL_CONTROLLER = 1, REDSTONE_INTERFACE = 2, NETWORK_INTERFACE = 3, DIALLING_DEVICE = 4, UNUSED = 5, MODULE_MANIPULATOR = 6, TRANSFER_FLUID = 7, TRANSFER_ITEM = 8, TRANSFER_ENERGY = 9;*/
    public static int FRAME_TYPES = 10;

    public static final PropertyEnum FRAME_TYPE = PropertyEnum.create("frames", BlockFrame.FrameType.class);

    public enum FrameType implements IStringSerializable {
        FRAME(0,"frame"),
        PORTAL_CONTROLLER(1, "controller"),
        REDSTONE_INTERFACE(2, "redstone_interface"),
        NETWORK_INTERFACE(3, "network_interface"),
        DIALLING_DEVICE(4, "dialling_device"),
        UNUSED(5, "unused"),
        MODULE_MANIPULATOR(6, "module_manipulator"),
        TRANSFER_FLUID(7, "transfer_fluid"),
        TRANSFER_ITEM(8, "transfer_item"),
        TRANSFER_ENERGY(9, "transfer_energy");

        private String name;
        private int id;

        private FrameType(int id, String name){
            this.name = name;
            this.id = id;
        }

        public int getMetadata(){
            return this.id;
        }

        @Override
        public String getName()
        {
            return name;
        }
    }


    public BlockFrame()
    {
        super(Material.ROCK);
        instance = this;
        setCreativeTab(CommonProxy.creativeTab);
        setHardness(5);
        setResistance(2000);
        setUnlocalizedName("frame");
        setSoundType(SoundType.STONE);
//        connectedTextures = new ConnectedTexturesDetailed("enhancedportals:frame/%s", this, -1);
        this.setRegistryName("frame");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FRAME_TYPE, FRAME));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FRAME_TYPE});
    }

/*
    @Override
    public IBlockState getStateFromMeta(int meta){

        return this.getDefaultState().withProperty(FRAME_TYPE, meta);
    }
*/

    @Override
    public int getMetaFromState(IBlockState state){
        FrameType type = (FrameType) state.getValue(FRAME_TYPE);

        return type.ordinal();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity t = world.getTileEntity(pos);

        if (t != null && t instanceof TileFrame)
        {
            ((TileFrame) t).breakBlock(world, pos, state);
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

    /*@Override
    public boolean canRenderInPass(int pass)
    {
        ClientProxy.renderPass = pass;
        return pass < 2;
    }*/


    //todo color multiplier?
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
    {
        TileEntity tile = blockAccess.getTileEntity(new BlockPos(x, y, z));

        if (tile instanceof TileFrame)
        {
            return ((TileFrame) tile).getColour();
        }

        return 0xFFFFFF;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        if (metadata == FRAME.ordinal())
        {
            return new TileFrameBasic();
        }
        else if (metadata == PORTAL_CONTROLLER.ordinal())
        {
            return new TileController();
        }
        else if (metadata == REDSTONE_INTERFACE.ordinal())
        {
            return new TileRedstoneInterface();
        }
        else if (metadata == NETWORK_INTERFACE.ordinal())
        {
            return new TileNetworkInterface();
        }
        else if (metadata == DIALLING_DEVICE.ordinal())
        {
            return new TileDialingDevice();
        }
        else if (metadata == MODULE_MANIPULATOR.ordinal())
        {
            return new TilePortalManipulator();
        }
        else if (metadata == TRANSFER_FLUID.ordinal())
        {
            return new TileTransferFluid();
        }
        else if (metadata == TRANSFER_ITEM.ordinal())
        {
            return new TileTransferItem();
        }
        else if (metadata == TRANSFER_ENERGY.ordinal())
        {
            return new TileTransferEnergy();
        }

        return null;
    }

    @Override
    public void dismantleBlock(EntityPlayer player, World world, BlockPos pos)
    {
        ItemStack dropBlock = new ItemStack(this, 1, world.getBlockState(pos).getBlock().getMetaFromState(getDefaultState()));

        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileFrame)
        {
            ((TileFrame) tile).onBlockDismantled(pos);
        }

        world.setBlockToAir(pos);

        if (dropBlock != null)
        {
            float f = 0.3F;
            double x2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            double y2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            double z2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            EntityItem item = new EntityItem(world, pos.getX() + x2, pos.getY() + y2, pos.getZ() + z2, dropBlock);
            item.setPickupDelay(10);
            world.spawnEntityInWorld(item);
        }
    }

    /*@Override
    public int getRenderBlockPass()
    {
        return 1;
    }*/

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs creativeTab, List list)
    {

        FrameType[] allFrames = FrameType.values();
        for (FrameType frame: allFrames) {
            list.add(new ItemStack(par1, 1, frame.getMetadata()));
        }

        /*for (int i = 0; i < FRAME_TYPES; i++)
        {
            list.add(new ItemStack(this, 1, i));
        }*/
    }

    @Override
    public boolean isBlockSolid(IBlockAccess p_149747_1_, BlockPos pos, EnumFacing side)
    {
        return false;
    }

   /* @Override
    public boolean isBlockNormalCube()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }*/

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {

        return state.getMaterial().isOpaque() && state.isFullCube() && !state.canProvidePower();
    }

    public int isProvidingStrongPower(IBlockAccess blockAccess, BlockPos pos, int side)
    {
        TileEntity tile = blockAccess.getTileEntity(pos);

        if (tile instanceof TileRedstoneInterface)
        {
            return ((TileRedstoneInterface) tile).isProvidingPower(side);
        }

        return 0;
    }

    public int isProvidingWeakPower(IBlockAccess blockAccess, BlockPos pos, int side)
    {
        TileEntity tile = blockAccess.getTileEntity(pos);

        if (tile instanceof TileRedstoneInterface)
        {
            return ((TileRedstoneInterface) tile).isProvidingPower(side);
        }

        return 0;
    }

    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileFrame)
        {
            return ((TileFrame) tile).activate(player, player.inventory.getCurrentItem(), pos);
        }

        return false;
    }

    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor, EnumFacing side)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileRedstoneInterface)
        {
            ((TileRedstoneInterface) tile).onNeighborBlockChange(pos, side);
        }
        else if (tile instanceof TileFrameTransfer)
        {
            ((TileFrameTransfer) tile).onNeighborChanged();
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (blockAccess.getBlockState(pos).getBlock() == this) return false;
        return super.shouldSideBeRendered(state, blockAccess, pos, side);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {


        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)));
    }

}
