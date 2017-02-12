package enhancedportals.block;

import enhancedportals.Reference;
import enhancedportals.network.CommonProxy;
import enhancedportals.tile.TileStabilizer;
import enhancedportals.tile.TileStabilizerMain;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStabilizer extends Block
{
    public static BlockStabilizer instance;

    public static final PropertyEnum STABILIZER_PART = PropertyEnum.create("stabilizer", BlockStabilizer.StabilizerPart.class);

    public enum StabilizerPart implements IStringSerializable {
        STABILIZER(0,"stabilizer"),
        MAIN(1, "main");

        private String name;
        private int id;

        private StabilizerPart(int id, String name){
            this.name = name;
            this.id = id;
        }

        public int getMetadata() {return this.id;}

        @Override
        public String getName() {
            return name;
        }
    }

    public BlockStabilizer(String n)
    {
        super(Material.ROCK);
        instance = this;
        setHardness(5);
        setResistance(2000);
        setRegistryName(Reference.EPMod.mod_id, n);
        setUnlocalizedName(getRegistryName().toString());
        setSoundType(SoundType.STONE);
        setCreativeTab(CommonProxy.creativeTab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STABILIZER_PART, StabilizerPart.MAIN));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{STABILIZER_PART});
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileStabilizer)
        {
            ((TileStabilizer) tile).breakBlock();
        }
        else if (tile instanceof TileStabilizerMain)
        {
            ((TileStabilizerMain) tile).breakBlock();
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {

        System.out.println("Debug: getStateFromMeta:" + this.getDefaultState().withProperty(STABILIZER_PART,StabilizerPart.STABILIZER));
        return this.getDefaultState().withProperty(STABILIZER_PART, StabilizerPart.STABILIZER);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
     StabilizerPart part = (StabilizerPart) state.getValue(STABILIZER_PART);

        System.out.println("Debug: getMetaFromState:" + part.ordinal());
        return part.ordinal();
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        if (state == getStateFromMeta(StabilizerPart.STABILIZER.getMetadata()))
        {
            return new TileStabilizer();
        }
        else if (state == getStateFromMeta(StabilizerPart.MAIN.getMetadata()));
        {
            return new TileStabilizerMain();
        }
    }


/*
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileStabilizer)
        {
            return ((TileStabilizer) tile).activate(player);
        }
        else if (tile instanceof TileStabilizerMain)
        {
            return ((TileStabilizerMain) tile).activate(player);
        }

        return false;
    }
*/

}
