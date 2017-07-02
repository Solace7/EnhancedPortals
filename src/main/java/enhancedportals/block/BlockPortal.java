package enhancedportals.block;

import enhancedportals.Reference;
import enhancedportals.client.PortalParticleFX;
import enhancedportals.item.ItemPortalModule;
import enhancedportals.portal.EntityManager;
import enhancedportals.tile.TileController;
import enhancedportals.tile.TilePortal;
import enhancedportals.tile.TilePortalManipulator;
import enhancedportals.utility.ConfigurationHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockPortal extends BlockContainer
{
    public static BlockPortal instance;

    public BlockPortal(String n)
    {
        super(Material.PORTAL);
        instance = this;
        setBlockUnbreakable();
        setResistance(2000);
        setRegistryName(Reference.EPMod.mod_id, n);
        setUnlocalizedName(getRegistryName().toString());
        setLightOpacity(0);
        setSoundType(SoundType.GLASS);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TilePortal)
        {
            ((TilePortal) tile).breakBlock(world, pos, state);
        }

        super.breakBlock(world, pos, state);
    }

    public int colorMultiplier(IBlockAccess blockAccess, BlockPos pos)
    {
        TileEntity tile = blockAccess.getTileEntity(pos);

        if (tile instanceof TilePortal)
        {
            return ((TilePortal) tile).getColour();
        }

        return 0xFFFFFF;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TilePortal();
    }

    /*@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }
    */

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player){

        return null;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return 14;
    }

    /*@Override
    public int getRenderBlockPass()
    {
        return 1;
    }*/

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TilePortal)
        {
            return ((TilePortal) tile).activate(player, player.inventory.getCurrentItem(), pos);
        }

        return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if (!world.isRemote)
        {
            if (EntityManager.isEntityFitForTravel(entity))
            {
                if (entity instanceof EntityPlayer)
                {
                    ((EntityPlayer) entity).closeScreen();
                }

                TileEntity t = world.getTileEntity(pos);

                if (t instanceof TilePortal)
                {
                    ((TilePortal) t).onEntityCollidedWithBlock(entity);
                }
            }

            EntityManager.setEntityPortalCooldown(entity);
        }
    }

    @Override
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random)
    {
        if (ConfigurationHandler.CONFIG_DISABLE_SOUNDS && ConfigurationHandler.CONFIG_DISABLE_PARTICLES)
        {
            return;
        }

        TileEntity tile = world.getTileEntity(pos);

        if (!(tile instanceof TilePortal))
        {
            return;
        }

        //todo get metadata as int
        int metadata = world.getBlockState(pos).getBlock().getMetaFromState(getDefaultState());

        TileController controller = ((TilePortal) tile).getPortalController();
        TilePortalManipulator module = controller == null ? null : controller.getModuleManipulator();
        boolean doSounds = !ConfigurationHandler.CONFIG_DISABLE_SOUNDS && random.nextInt(100) == 0, doParticles = !ConfigurationHandler.CONFIG_DISABLE_PARTICLES;

        if (module != null)
        {
            if (doSounds)
            {
                doSounds = !module.hasModule(ItemPortalModule.PortalModule.REMOVE_SOUNDS.getUniqueID());
            }

            doParticles = !module.hasModule(ItemPortalModule.PortalModule.REMOVE_PARTICLES.getUniqueID());
        }

        if (doSounds)
        {
            world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }

        if (doParticles)
        {
            for (int l = 0; l < 4; ++l)
            {
                double d0 = (double)((float)pos.getX() + random.nextFloat());
                double d1 = (double)((float)pos.getY() + random.nextFloat());
                double d2 = (double)((float)pos.getZ() + random.nextFloat());
                double d3 = 0.0D;
                double d4 = 0.0D;
                double d5 = 0.0D;
                int i1 = random.nextInt(2) * 2 - 1;
                d3 = (random.nextFloat() - 0.5D) * 0.5D;
                d4 = (random.nextFloat() - 0.5D) * 0.5D;
                d5 = (random.nextFloat() - 0.5D) * 0.5D;

                if (metadata == 1)
                {
                    d2 = pos.getZ() + 0.5D + 0.25D * i1;
                    d5 = random.nextFloat() * 2.0F * i1;
                }
                else if (metadata == 2)
                {
                    d0 = pos.getX() + 0.5D + 0.25D * i1;
                    d3 = random.nextFloat() * 2.0F * i1;
                }
                else if (metadata == 3)
                {
                    d1 = pos.getY() + 0.5D + 0.25D * i1;
                    d4 = random.nextFloat() * 2.0F * i1;
                }
                else if (metadata == 4)
                {
                    d3 = d5 = random.nextFloat() * 2F * i1;
                }
                else if (metadata == 5)
                {
                    d3 = d5 = random.nextFloat() * 2F * i1;
                    d3 = -d3;
                }

                PortalParticleFX fx = new PortalParticleFX(world, controller, d0, d1, d2, d3, d4, d5);

                if (module != null)
                {
                    module.particleCreated(fx);
                }

                FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
            }
        }
    }

    /*@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z)
    {
        TileEntity tile = blockAccess.getTileEntity(x, y, z);

        if (tile instanceof TilePortal)
        {
            TilePortal portal = (TilePortal) tile;
            TileController controller = portal.getPortalController();
            TilePortalManipulator manip = controller == null ? null : controller.getModuleManipulator();

            if (controller != null && manip != null && manip.isPortalInvisible())
            {
                setBlockBounds(0f, 0f, 0f, 0f, 0f, 0f);
                return;
            }

            int meta = blockAccess.getBlockMetadata(x, y, z);

            if (meta == 1)
            {
                setBlockBounds(0f, 0f, 0.375f, 1f, 1f, 0.625f);
            }
            else if (meta == 2)
            {
                setBlockBounds(0.375f, 0f, 0f, 0.625f, 1f, 1f);
            }
            else if (meta == 3)
            {
                setBlockBounds(0, 0.375f, 0f, 1f, 0.625f, 1f);
            }
            else
            {
                setBlockBounds(0f, 0f, 0f, 1f, 1, 1f);
            }
        }
    }*/

    /*@Override
    public void setBlockBoundsForItemRender()
    {
        setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
    }*/

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (blockAccess.getBlockState(pos).getBlock() == this || blockAccess.getBlockState(pos).getBlock() == BlockFrame.instance)
        {
            return false;
        }

        return super.shouldSideBeRendered(state, blockAccess, pos, side);
    }
}
