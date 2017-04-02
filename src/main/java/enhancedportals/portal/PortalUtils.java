package enhancedportals.portal;

import enhancedportals.tile.*;
import enhancedportals.utility.GeneralUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PortalUtils
{
    static final int MAXIMUM_CHANCES = 40;

    /***
     * Adds all the touching blocks to the processing queue.
     */
    static void addNearbyBlocks(World world, ChunkPos chunkPos, int portalDirection, Queue<ChunkPos> q)
    {
        /*// (world controller is in, the offset block from controller, 1-5, blank linkedList neighbors)
        // if portalDirection = 1, then add up, down, west, east
        // if portalDirection = 2, then add up, down, north, south
        // if portalDirection = 3, then add north, south, west, east
        // if portalDirection = 4, then add up, down, north-east, south-west
        // if portalDirection = 5, then add up, down, north-west, south-east
        //
        if (portalDirection == 4)
        {
            //todo manipulate chunks in the y
            q.add(new ChunkPos(chunkPos.chunkXPos, chunkPos.chunkZPos)); // Up
            q.add(new ChunkPos(w.posX, w.posY - 1, w.posZ)); // Down
            q.add(new ChunkPos(w.posX + 1, w.posY, w.posZ - 1)); // North-East
            q.add(new ChunkPos(w.posX - 1, w.posY, w.posZ + 1)); // South-West
            //
        }
        else if (portalDirection == 5)
        {
            q.add(new ChunkPos(w.posX, w.posY + 1, w.posZ)); // Up
            q.add(new ChunkPos(w.posX, w.posY - 1, w.posZ)); // Down
            q.add(new ChunkPos(w.posX - 1, w.posY, w.posZ - 1)); // North-West
            q.add(new ChunkPos(w.posX + 1, w.posY, w.posZ + 1)); // South-East
        }
        else
        // Loop through the different directions for portalDirection 1-3.
        {
            for (int i = 0; i < 6; i++)
            {
                // Skip North (2) and South (3)
                if (portalDirection == 1 && (i == 2 || i == 3))
                {
                    continue;
                }
                // Skip West (4) and East (5)
                else if (portalDirection == 2 && (i == 4 || i == 5))
                {
                    continue;
                }
                // Skip Up (0) and Down (1)
                else if (portalDirection == 3 && (i == 0 || i == 1))
                {
                    continue;
                }
                EnumFacing d = EnumFacing.getFront(i);
                q.add(new ChunkPos(w.posX + d.offsetX, w.posY + d.offsetY, w.posZ + d.offsetZ));
            }
        }*/
    }

    public static ArrayList<ChunkPos> getAllPortalComponents(TileController controller) throws PortalException
    {
        ArrayList<ChunkPos> portalComponents = new ArrayList<ChunkPos>();
        Queue<ChunkPos> toProcess = new LinkedList<ChunkPos>();
        Queue<ChunkPos> portalBlocks = getGhostedPortalBlocks(controller);
        toProcess.add(controller.getChunkPos());

        if (portalBlocks.isEmpty())
        {
            throw new PortalException("couldNotCreatePortalHere");
        }

        boolean program = false, mod = false, dialler = false, network = false;

        while (!toProcess.isEmpty())
        {
            ChunkPos c = toProcess.remove();

            if (!portalComponents.contains(c))
            {
                TileEntity t = controller.getWorld().getTileEntity(controller.getPos());

                if (portalBlocks.contains(c) || t instanceof TilePortalPart)
                {
                    if (t instanceof TileNetworkInterface)
                    {
                        if (dialler)
                        {
                            throw new PortalException("dialAndNetwork");
                        }

                        network = true;
                    }
                    else if (t instanceof TileDialingDevice)
                    {
                        if (network)
                        {
                            throw new PortalException("dialAndNetwork");
                        }

                        dialler = true;
                    }
                    else if (t instanceof TilePortalManipulator)
                    {
                        if (!mod)
                        {
                            mod = true;
                        }
                        else
                        {
                            throw new PortalException("multipleMod");
                        }
                    }

                    portalComponents.add(c);
                    addNearbyBlocks(controller.getWorld(), c, 0, toProcess);

                    if (controller.portalType >= 4)
                    {
                        addNearbyBlocks(controller.getWorld(), c, controller.portalType, toProcess); // Adds diagonals for those that require it
                    }
                }
            }
        }

        if (portalComponents.isEmpty())
        {
            throw new PortalException("unknown");
        }

        return portalComponents;
    }

    static Queue<ChunkPos> getGhostedPortalBlocks(TileController controller)
    {
        for (int j = 0; j < 6; j++)
        {
            for (int i = 1; i < 6; i++)
            {
                // Forge directions: Down, Up, North, South, West, East
                // Get Controller and cycle through forge directions from the coord.
                ChunkPos c = GeneralUtils.offset(controller.getChunkPos(), EnumFacing.getFront(j));
                // portalBlocks = (the world controller is in, the offset from the controller we're exploring, 1-5)
                Queue<ChunkPos> portalBlocks = getGhostedPortalBlocks(controller.getWorld(), c, i, controller.getPos());

                if (!portalBlocks.isEmpty())
                {
                    controller.portalType = i;
                    return portalBlocks;
                }
            }
        }

        return new LinkedList<ChunkPos>();
    }

    static Queue<ChunkPos> getGhostedPortalBlocks(World world, ChunkPos start, int portalType, BlockPos pos)
    {
        Queue<ChunkPos> portalBlocks = new LinkedList<ChunkPos>();
        Queue<ChunkPos> toProcess = new LinkedList<ChunkPos>();
        int chances = 0;
        // Start is the offset block from the controller.
        toProcess.add(start);

        while (!toProcess.isEmpty())
        {
            // c is now the offset block (start).
            ChunkPos c = toProcess.remove();
            // Pass as long as portalBlocks does not already contain the offset block from the controller.
            if (!portalBlocks.contains(c)) // Check if the coords of the offset block happens to be an air block.
            {
//               todo if (world.isAirBlock(c.posX, c.posY, c.posZ))
                IBlockState state = world.getBlockState(pos);
                if(world.isAirBlock(pos))
                {
                    // sides = (world that the controller is in, current list of portalBlocks, 1-5)
                    // Returns the number of portal frame blocks and item already in portalBlocks.
                    int sides = getGhostedSides(world, c, portalBlocks, portalType, pos);

                    if (sides < 2)
                    {
                        if (chances < MAXIMUM_CHANCES)
                        {
                            chances++;
                            sides += 2;
                        }
                        else
                        {
                            return new LinkedList<ChunkPos>();
                        }
                    }

                    if (sides >= 2)
                    {
                        portalBlocks.add(c);
                        addNearbyBlocks(world, c, portalType, toProcess);
                    }
                }
                else if (!isPortalPart(world, c, pos))
                {
                    return new LinkedList<ChunkPos>();
                }
            }
        }

        return portalBlocks;
    }

    static int getGhostedSides(World world, ChunkPos block, Queue<ChunkPos> portalBlocks, int portalType, BlockPos pos)
    {
        int sides = 0;
        Queue<ChunkPos> neighbors = new LinkedList<ChunkPos>();
        // (world controller is in, the offset block from controller, 1-5, blank linkedList neighbors)
        // if portalDirection = 1, then add up, down, west, east
        // if portalDirection = 2, then add up, down, north, south
        // if portalDirection = 3, then add north, south, west, east
        // if portalDirection = 4, then add up, down, north-east, south-west
        // if portalDirection = 5, then add up, down, north-west, south-east
        addNearbyBlocks(world, block, portalType, neighbors);

        // Go through all neighbor blocks.
        for (ChunkPos c : neighbors)
        {
            if (portalBlocks.contains(c) || isPortalPart(world, c, pos))
            {
                sides++;
            }
        }

        return sides;
    }

    static boolean isPortalPart(World world, ChunkPos c, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);
        return tile != null && tile instanceof TilePortalPart;
    }

    public static boolean netherCreatePortal(World world, ChunkPos w, int portalDirection, BlockPos pos)
    {
        Queue<ChunkPos> processed = new LinkedList<ChunkPos>();
        Queue<ChunkPos> toProcess = new LinkedList<ChunkPos>();
        int chances = 0;
        toProcess.add(w);

        while (!toProcess.isEmpty())
        {
            ChunkPos c = toProcess.remove();

            if (!processed.contains(c))
            {
                if (world.isAirBlock(pos))
                {
                    int sides = netherGetSides(world, c, portalDirection, pos);

                    if (sides < 2)
                    {
                        if (chances < MAXIMUM_CHANCES)
                        {
                            chances++;
                            sides += 2;
                        }
                        else
                        {
                            netherRemoveFailedPortal(world, processed, pos);
                            return false;
                        }
                    }

                    if (sides >= 2)
                    {
                        processed.add(c);

//                        todo world.setBlock(c.posX, c.posY, c.posZ, Blocks.portal, 0, 2);
                        IBlockState state = Blocks.PORTAL.getDefaultState();
                        world.setBlockState(pos, state);
                        addNearbyBlocks(world, c, portalDirection, toProcess);
                    }
                }
                else if (!netherIsPortalPart(world, pos))
                {
                    netherRemoveFailedPortal(world, processed, pos);
                    return false;
                }
            }
        }

        return true;
    }

    static int netherGetSides(World world, ChunkPos w, int portalDirection, BlockPos pos)
    {
        int sides = 0;
        Queue<ChunkPos> neighbors = new LinkedList<ChunkPos>();
        addNearbyBlocks(world, w, portalDirection, neighbors);

        for (ChunkPos c : neighbors)
        {
            if (netherIsPortalPart(world, pos))
            {
                sides++;
            }
        }

        return sides;
    }

    static boolean netherIsPortalPart(Block id)
    {
        return id == Blocks.PORTAL || id == Blocks.OBSIDIAN;
    }

    static boolean netherIsPortalPart(World world, BlockPos pos)
    {
        return netherIsPortalPart(world.getBlockState(pos).getBlock());
    }

    static void netherRemoveFailedPortal(World world, Queue<ChunkPos> processed, BlockPos pos)
    {
        while (!processed.isEmpty())
        {
            ChunkPos c = processed.remove();
            world.setBlockToAir(pos);
        }
    }

    /*public static int getSize(World world, )
    */
}