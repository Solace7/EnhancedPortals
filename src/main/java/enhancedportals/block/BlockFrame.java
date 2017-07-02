package enhancedportals.block;

import enhancedportals.Reference;
import enhancedportals.network.CommonProxy;
import enhancedportals.tile.TileFrame;
import enhancedportals.tile.TileFrameTransfer;
import enhancedportals.tile.TileRedstoneInterface;
import enhancedportals.utility.IDismantleable;
import net.minecraft.block.Block;
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

public class BlockFrame extends Block implements IDismantleable
{
    public static BlockFrame instance;

    public static final PropertyEnum<BlockFrame.FrameType> FRAME_TYPE = PropertyEnum.<BlockFrame.FrameType>create("frame_type", BlockFrame.FrameType.class);

    public enum FrameType implements IStringSerializable {
        FRAME(0,"frame"),
        PORTAL_CONTROLLER(1, "controller"),
        REDSTONE_INTERFACE(2, "redstone_interface"),
        NETWORK_INTERFACE(3, "network_interface"),
        DIALLING_DEVICE(4, "dialling_device"),
        MODULE_MANIPULATOR(5, "module_manipulator"),
        TRANSFER_FLUID(6, "transfer_fluid"),
        TRANSFER_ITEM(7, "transfer_item"),
        TRANSFER_ENERGY(8, "transfer_energy");

        private final String name;
        private final int id;

        private FrameType(int id, String name){
            this.name = name;
            this.id = id;
        }

        public int getID(){
            return this.id;
        }

        @Override
        public String getName()
        {
            return name;
        }
    }


    public BlockFrame(String n)
    {
        super(Material.ROCK);
        instance = this;
        this.setDefaultState(this.blockState.getBaseState().withProperty(FRAME_TYPE, FrameType.FRAME));
        setCreativeTab(CommonProxy.creativeTab);
        setHardness(5);
        setResistance(2000);
        setRegistryName(Reference.EPMod.mod_id, n);
        setUnlocalizedName(getRegistryName().toString());
        setSoundType(SoundType.STONE);
    }

    /*@Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }*/

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FRAME_TYPE});
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        System.out.println("Debugging: MetaValue: " + meta );
        System.out.println("Debugging: getStateFromMeta " + this.getDefaultState().withProperty(FRAME_TYPE, FrameType.values()[meta]));
        return this.getDefaultState().withProperty(FRAME_TYPE, FrameType.values()[meta]);
    }


    @Override
    public int getMetaFromState(IBlockState state){
        System.out.println("Debugging getFromState stateValue: " + state);
        System.out.println("Debugging getMetaFromState metaValue: " + ((BlockFrame.FrameType)state.getValue(FRAME_TYPE)).getID());

       return state.getValue(FRAME_TYPE).getID();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
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
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        if (state == getDefaultState().withProperty(FRAME_TYPE, FrameType.FRAME))
        {
            System.out.println("Debugging: TileFrameBasic Created");
            return new TileFrameBasic();
        }
        else if (state == getDefaultState().withProperty(FRAME_TYPE, FrameType.PORTAL_CONTROLLER))
        {
            System.out.println("Debugging: TileController Created");
            return new TileController();
        }
        else if (state == getDefaultState().withProperty(FRAME_TYPE, FrameType.REDSTONE_INTERFACE))
        {
            return new TileRedstoneInterface();
        }
        else if (state == getDefaultState().withProperty(FRAME_TYPE, FrameType.NETWORK_INTERFACE))
        {
            return new TileNetworkInterface();
        }
        else if (state == getDefaultState().withProperty(FRAME_TYPE, FrameType.DIALLING_DEVICE))
        {
            return new TileDialingDevice();
        }
        else if (state == getDefaultState().withProperty(FRAME_TYPE, FrameType.MODULE_MANIPULATOR))
        {
            return new TilePortalManipulator();
        }
        else if (state == getDefaultState().withProperty(FRAME_TYPE, FrameType.TRANSFER_FLUID))
        {
            return new TileTransferFluid();
        }
        else if (state == getDefaultState().withProperty(FRAME_TYPE, FrameType.TRANSFER_ITEM))
        {
            return new TileTransferItem();
        }
        else if (state == getDefaultState().withProperty(FRAME_TYPE, FrameType.TRANSFER_ENERGY))
        {
            return new TileTransferEnergy();
        }

        return null;
    }*/

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

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs creativeTab, List list)
    {
        FrameType[] allFrames = FrameType.values();
        for (FrameType frame: allFrames) {
            System.out.println("Debugging SubBlocks: " + frame);
            list.add(new ItemStack(par1, 1, frame.getID()));
        }
    }

/*    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.SOLID;
    }*/

/*    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }*/

/*    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state)
    {
        return true;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return true;
    }*/

/*    @Override
    public boolean isBlockSolid(IBlockAccess p_149747_1_, BlockPos pos, EnumFacing side)
    {
        return true;
    }*/

    /*    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return true;
    }*/

/*    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {

        return state.getMaterial().isOpaque() && state.isFullCube() && !state.canProvidePower();
    }*/

/*    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (blockAccess.getBlockState(pos).getBlock() == this) return false;
        return super.shouldSideBeRendered(state, blockAccess, pos, side);
    }*/

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
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {

        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)));
    }

}
