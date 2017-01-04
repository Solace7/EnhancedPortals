package enhancedportals.portal;

import enhancedportals.block.BlockPortal;
import enhancedportals.tile.TileController;
import enhancedportals.tile.TilePortalManipulator;
import enhancedportals.utility.ConfigurationHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EntityManager
{
    static Random rand = new Random();
    static final int PLAYER_COOLDOWN_RATE = 10;


    static ChunkPos getActualExitLocation(Entity entity, TileController controller, BlockPos pos)
    {
        World world = controller.getWorld();
        int entityHeight = Math.round(entity.height);
        boolean horizontal = controller.portalType == 3;

        forloop:
        for (ChunkPos c : new ArrayList<ChunkPos>(controller.getPortals()))
        {
            for (int i = 0; i < entityHeight; i++)
            {
                if (world.getBlockState(new BlockPos(pos.getX(), pos.getY() + i, pos.getZ())) != BlockPortal.instance && !world.isAirBlock(new BlockPos(pos.getX(), pos.getY() + i, pos.getZ())));
                {
                    continue forloop;
                }
            }

            if (horizontal && !world.isAirBlock(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())))
            {
                return new ChunkPos(new BlockPos (pos.getX(), pos.getY() - 1, pos.getZ()));
            }
            else
            {
                return new ChunkPos(c.chunkXPos, c.chunkZPos);
            }
        }

        return null;
    }

    //todo getRotation

   /* static float getRotation(Entity entity, TileController controller, ChunkPos loc)
    {
        BlockPos pos = entity.getPosition();

        World world = controller.getWorld();
        TilePortalManipulator module = controller.getModuleManipulator();

        if (module != null)
        {
            ItemStack s = module.getModule("ep3." + ItemPortalModule.PortalModules.FACING.ordinal());

            if (s != null)
            {
                NBTTagCompound tag = s.getTagCompound();
                int facing = 0;

                if (tag != null)
                {
                    facing = tag.getInteger("facing");
                }

                return facing * 90F - 180F;
            }
        }

        if (controller.portalType == 1)
        {
//            if (world.isSideSolid(loc.chunkXPos, , loc.chunkZPos + 1, EnumFacing.NORTH))
            if (world.isSideSolid(new BlockPos(loc.chunkXPos, pos.getY(), loc.chunkZPos), EnumFacing.NORTH))
            {
                return 180f;
            }

            return 0f;
        }
        else if (controller.portalType == 2)
        {
//            if (world.isSideSolid(loc.posX - 1, loc.posY, loc.posZ, EnumFacing.EAST))
            if(world.isSideSolid(new BlockPos(loc.chunkXPos, pos.getY(), loc.chunkZPos), EnumFacing.EAST))
            {
                return -90f;
            }

            return 90f;
        }
        else if (controller.portalType == 4)
        {
//            if (world.isBlockNormalCube(loc.posX + 1, loc.posY, loc.posZ + 1, true))
            if (world.isBlockNormalCube(new BlockPos(loc.chunkXPos + 1, pos.getY(), loc.chunkZPos + 1), true))
            {
                return 135f;
            }

            return -45f;
        }
        else if (controller.portalType == 5)
        {
//           todo if (world.isBlockNormalCubeDefault(loc.posX - 1, loc.posY, loc.posZ + 1, true))
            if (world.isBlockNormalCube())
            {
                return -135f;
            }

            return 45f;
        }

        return entity.rotationYaw;
    }
*/
    static void handleMomentum(Entity entity, int touchedPortalType, int exitPortalType, float exitYaw, boolean keepMomentum)
    {
        if (!keepMomentum)
        {
            entity.motionX = entity.motionY = entity.motionZ = 0;
            return;
        }
        else if (touchedPortalType == 1)
        {
            if (exitPortalType == 2)
            {
                double temp = entity.motionZ;
                entity.motionZ = entity.motionX;
                entity.motionX = exitYaw == -90 ? -temp : temp;
            }
            else if (exitPortalType == 3)
            {
                double temp = entity.motionZ;
                entity.motionZ = entity.motionY;
                entity.motionY = temp;
            }
            else if (exitPortalType == 4)
            {
                double temp = entity.motionZ;
                entity.motionZ = entity.motionY;
                entity.motionY = -temp;
            }
        }
        else if (touchedPortalType == 2)
        {
            if (exitPortalType == 1)
            {
                double temp = entity.motionZ;
                entity.motionZ = entity.motionX;
                entity.motionX = exitYaw == 0 ? -temp : temp;
            }
            else if (exitPortalType == 3)
            {
                double temp = entity.motionX;
                entity.motionX = entity.motionY;
                entity.motionY = temp;
            }
            else if (exitPortalType == 4)
            {
                double temp = entity.motionX;
                entity.motionX = entity.motionY;
                entity.motionY = -temp;
            }
        }
        else if (touchedPortalType == 3 || touchedPortalType == 4)
        {
            if (exitPortalType == 1)
            {
                double temp = entity.motionY;
                entity.motionY = entity.motionZ;
                entity.motionZ = exitYaw == 0 ? -temp : temp;
            }
            else if (exitPortalType == 2)
            {
                double temp = entity.motionY;
                entity.motionY = entity.motionX;
                entity.motionX = exitYaw == -90 ? -temp : temp;
            }
            else if (exitPortalType == 3)
            {
                entity.motionY = touchedPortalType == 3 ? -entity.motionY : entity.motionY;
            }
        }

        entity.velocityChanged = true;
    }

    public static boolean isEntityFitForTravel(Entity entity)
    {
        return entity != null && entity.timeUntilPortal == 0;
    }

    public static void setEntityPortalCooldown(Entity entity)
    {
        if (entity == null)
        {
            return;
        }

        if (ConfigurationHandler.CONFIG_FASTER_PORTAL_COOLDOWN || entity instanceof EntityPlayer || entity instanceof EntityMinecart || entity instanceof EntityBoat || entity instanceof EntityHorse)
        {
            entity.timeUntilPortal = entity.timeUntilPortal == -1 ? 0 : PLAYER_COOLDOWN_RATE;
        }
        else
        {
            entity.timeUntilPortal = entity.timeUntilPortal == -1 ? 0 : 300; // Reduced to 300 ticks from 900.
        }
    }

    public static void teleportEntityHighestInstability(Entity par1Entity) // TODO: CRIMSON
    {
        BlockPos spawn = par1Entity.worldObj.getSpawnPoint();
//        spawn.posY = par1Entity.worldObj.getTopSolidOrLiquidBlock(spawn.posX, spawn.posY);
//        spawn.getY() = par1Entity.worldObj.getTopSolidOrLiquidBlock(par1Entity.worldObj.getSpawnPoint().getY());

//        if (par1Entity.worldObj.isAirBlock(new BlockPos(spawn.getX(), spawn.getY(), spawn.getZ())) || par1Entity.worldObj.getBlock(spawn.posX, spawn.posY, spawn.posZ) instanceof BlockFluidBase)
        if (par1Entity.worldObj.isAirBlock(new BlockPos(spawn.getX(), spawn.getY(), spawn.getZ())) || par1Entity.worldObj.getBlockState(new BlockPos(spawn.getX(), spawn.getY(), spawn.getZ())) instanceof BlockFluidBase)
        {
            par1Entity.worldObj.setBlockState(new BlockPos(spawn.getX(), spawn.getY(), spawn.getZ()), par1Entity.worldObj.getBlockState(new BlockPos(spawn.getX(), spawn.getY(), spawn.getZ())));
        }

        transferEntityWithinDimension(par1Entity, par1Entity.getPosition(), spawn.getX(), spawn.getY() + 1, spawn.getZ(), false, -1);
    }

    static Entity transferEntity(Entity entity, BlockPos pos, double x, double y, double z, float yaw, WorldServer world, int touchedPortalType, int exitPortalType, boolean keepMomentum, int instability)
    {
        // If entity is going to the same dimension...
        if (entity.worldObj.provider.getDimension() == world.provider.getDimension())
        {
            return transferEntityWithinDimension(entity, pos, yaw, touchedPortalType, exitPortalType, keepMomentum, instability);
        }
        else
        {
            return transferEntityToDimension(entity,pos, yaw, (WorldServer) entity.worldObj, world, touchedPortalType, exitPortalType, keepMomentum, instability);
        }
    }

    public static void transferEntity(Entity entity, BlockPos pos, TileController entry, TileController exit) throws PortalException
    {
        ChunkPos exitLoc = getActualExitLocation(entity, exit, pos);

        if (exitLoc == null)
        {
            throw new PortalException("failedToTransfer");
        }
        else
        {
            boolean keepMomentum = false;
            TilePortalManipulator manip = exit.getModuleManipulator();

            if (manip != null)
            {
                keepMomentum = manip.shouldKeepMomentumOnTeleport();
            }

            while (entity.getRidingEntity() != null)
            {
                entity = entity.getRidingEntity();
            }

            int instability = exit.getDimensionalBridgeStabilizer().instability;

         //todo transfEntitywRider
//            transferEntityWithRider(entity, pos, exitLoc.chunkXPos + 0.5, pos.getY(), exitLoc.chunkZPos + 0.5, getRotation(entity, exit, exitLoc), (WorldServer) exit.getWorld(), entry.portalType, exit.portalType, keepMomentum, instability);
        }
    }

    static Entity transferEntityToDimension(Entity entity, BlockPos pos, float yaw, WorldServer exitingWorld, WorldServer enteringWorld, int touchedPortalType, int exitPortalType, boolean keepMomentum, int instability)
    {

        double x = (double) pos.getX();
        double y = (double) pos.getY();
        double z = (double) pos.getZ();
        if (touchedPortalType == -1 && exitPortalType == -1)
        {
            // Look for an open airblock to teleport entity to in other dimension.
            while (!enteringWorld.isAirBlock(pos) || !enteringWorld.isAirBlock(pos))
            {
                y++;

                if (y > 250)
                {
                    break;
                }
            }

            y++;
        }

        if (entity == null)
        {
            return null;
        }
        else if (!isEntityFitForTravel(entity))
        {
            return entity;
        }
        else if (entity instanceof EntityPlayer)
        {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            player.worldObj.theProfiler.startSection("portal");

            if (!player.worldObj.isRemote)
            {
                player.worldObj.theProfiler.startSection("changeDimension");
                PlayerList config = player.mcServer.getPlayerList();

                player.closeScreen();
                player.dimension = enteringWorld.provider.getDimension();
                player.connection.sendPacket(new SPacketRespawn(player.dimension, player.worldObj.getDifficulty(), enteringWorld.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));

                exitingWorld.removeEntity(player);
                player.isDead = false;
                player.setLocationAndAngles(x, y, z, yaw, player.rotationPitch);
                handleMomentum(player, touchedPortalType, exitPortalType, yaw, keepMomentum);

                enteringWorld.spawnEntityInWorld(player);
                player.setWorld(enteringWorld);

//                todo config.func_72375_a(player, exitingWorld);
                player.connection.setPlayerLocation(x, y, z, yaw, entity.rotationPitch);
                player.setWorld(enteringWorld);

                config.updateTimeAndWeatherForPlayer(player, enteringWorld);
                config.syncPlayerInventory(player);

                player.worldObj.theProfiler.endSection();
                exitingWorld.resetUpdateEntityTick();
                enteringWorld.resetUpdateEntityTick();
                player.worldObj.theProfiler.endSection();

                // Instate any potion effects the player had when teleported.
                for (Iterator<PotionEffect> potion = player.getActivePotionEffects().iterator(); potion.hasNext(); )
                {
                    player.connection.sendPacket(new SPacketEntityEffect(player.getEntityId(), potion.next()));
                }

                // If there is instability, give effects.
                checkInstabilityEffects(entity, instability);

                player.connection.sendPacket(new SPacketSetExperience(player.experience, player.experienceTotal, player.experienceLevel));

                FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, exitingWorld.provider.getDimension(), player.dimension);

                setEntityPortalCooldown(player);
            }
            player.worldObj.theProfiler.endSection();
            return player;
        }
        // If the entity teleporting is something other than a player:
        else
        {
            NBTTagCompound tag = new NBTTagCompound();
            entity.writeToNBTOptional(tag);

            // Clear their inventory
            if (entity instanceof IInventory)
            {
                IInventory entityInventory = (IInventory) entity;

                for (int i = 0; i < entityInventory.getSizeInventory(); i++)
                {
                    entityInventory.setInventorySlotContents(i, null);
                }
            }

            // Delete the entity. Will be taken care of next tick.
            entity.setDead();

            // Create new entity.
            Entity newEntity = EntityList.createEntityFromNBT(tag, enteringWorld);

            // Set position, momentum of new entity at the other portal.
            if (newEntity != null)
            {
                handleMomentum(newEntity, touchedPortalType, exitPortalType, yaw, keepMomentum);
                newEntity.setLocationAndAngles(x, y, z, yaw, entity.rotationPitch);
                newEntity.forceSpawn = true;
                enteringWorld.spawnEntityInWorld(newEntity);
                newEntity.setWorld(enteringWorld);
                setEntityPortalCooldown(newEntity);
            }

            exitingWorld.resetUpdateEntityTick();
            enteringWorld.resetUpdateEntityTick();

            return newEntity;
        }
    }

    static Entity transferEntityWithinDimension(Entity entity, BlockPos pos, float yaw, int touchedPortalType, int exitPortalType, boolean keepMomentum, int instability)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (entity == null)
        {
            return null;
        }
        else if (!isEntityFitForTravel(entity))
        {
            return entity;
        }
        else if (entity instanceof EntityPlayer)
        {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            // The actual transporting.
            player.rotationYaw = yaw;
            player.setPositionAndUpdate(x, y, z);
            // For the momentum module.
            handleMomentum(player, touchedPortalType, exitPortalType, yaw, keepMomentum);
            player.worldObj.updateEntityWithOptionalForce(player, false);

            player.connection.sendPacket(new SPacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
            setEntityPortalCooldown(player);

            // If there is instability, give effects.
            checkInstabilityEffects(entity, instability);
            return player;
        }
        // If the entity teleporting is something other than a player:
        else
        {
            WorldServer world = (WorldServer) entity.worldObj;
            NBTTagCompound tag = new NBTTagCompound();
            entity.writeToNBTOptional(tag);

            if (entity instanceof IInventory)
            {
                IInventory entityInventory = (IInventory) entity;

                for (int i = 0; i < entityInventory.getSizeInventory(); i++)
                {
                    entityInventory.setInventorySlotContents(i, null);
                }
            }

            // Delete the entity. Will be taken care of next tick.
            entity.setDead();

            // Create new entity.
            Entity newEntity = EntityList.createEntityFromNBT(tag, world);

            // Set position, momentum of new entity at the other portal.
            if (newEntity != null)
            {
                handleMomentum(newEntity, touchedPortalType, exitPortalType, yaw, keepMomentum);
                newEntity.setLocationAndAngles(x, y, z, yaw, entity.rotationPitch);
                newEntity.forceSpawn = true;
                world.spawnEntityInWorld(newEntity);
                newEntity.setWorld(world);
                setEntityPortalCooldown(newEntity);
            }

            world.resetUpdateEntityTick();

            return newEntity;
        }
    }

    static Entity transferEntityWithRider(Entity entity, BlockPos pos, double x, double y, double z, float yaw, WorldServer world, int touchedPortalType, int exitPortalType, boolean keepMomentum, int instability)
    {
        Entity rider = entity.getRidingEntity();

        // If Entity has a rider...
        if (rider != null)
        {
            // Unmount rider
            rider.dismountRidingEntity();
            // Send it back through as it's own entity
            rider = transferEntityWithRider(rider, pos, (double) pos.getX(), (double)pos.getY(),(double)pos.getZ(), yaw, world, touchedPortalType, exitPortalType, keepMomentum, instability);
        }

        // Transfer the entity.
        entity = transferEntity(entity, pos,(double) pos.getX(), (double)pos.getY(),(double)pos.getZ(), yaw, world, touchedPortalType, exitPortalType, keepMomentum, instability);

        // Remount entity with rider.
        if (rider != null)
        {
            entity.updatePassenger(rider);
        }

        return entity;
    }

    protected static void checkInstabilityEffects(Entity entity, int instability)
    {
        int chance_of_effect = rand.nextInt(100);
        if (chance_of_effect < instability)
        {
            if (instability >= 70)
            {
                addHighInstabilityEffects(entity);
            }
            else if (instability >= 50)
            {
                addMediumInstabilityEffects(entity);
            }
            else if (instability >= 20)
            {
                addLowInstabilityEffects(entity);
            }
        }
    }

    static void addHighInstabilityEffects(Entity entity)
    {
        if (entity instanceof EntityLivingBase)
        {
            PotionEffect blindness = new PotionEffect(MobEffects.BLINDNESS, 600, 1);
            PotionEffect hunger = new PotionEffect(MobEffects.HUNGER, 600, 1);
            PotionEffect poison = new PotionEffect(MobEffects.POISON, 600, 1);

            blindness.setCurativeItems(new ArrayList<ItemStack>());
            hunger.setCurativeItems(new ArrayList<ItemStack>());
            poison.setCurativeItems(new ArrayList<ItemStack>());

            ((EntityLivingBase) entity).addPotionEffect(blindness);
            ((EntityLivingBase) entity).addPotionEffect(hunger);
            ((EntityLivingBase) entity).addPotionEffect(poison);
        }
    }

    static void addLowInstabilityEffects(Entity entity)
    {
        if (entity instanceof EntityLivingBase)
        {
            PotionEffect blindness = new PotionEffect(MobEffects.BLINDNESS, 200, 1);
            PotionEffect hunger = new PotionEffect(MobEffects.HUNGER, 200, 1);
            PotionEffect poison = new PotionEffect(MobEffects.POISON, 200, 1);

            blindness.setCurativeItems(new ArrayList<ItemStack>());
            hunger.setCurativeItems(new ArrayList<ItemStack>());
            poison.setCurativeItems(new ArrayList<ItemStack>());

            int effect = rand.nextInt(3);
            ((EntityLivingBase) entity).addPotionEffect(effect == 0 ? blindness : effect == 1 ? hunger : poison);
        }
    }

    static void addMediumInstabilityEffects(Entity entity)
    {
        if (entity instanceof EntityLivingBase)
        {
            PotionEffect blindness = new PotionEffect(MobEffects.BLINDNESS, 400, 1);
            PotionEffect hunger = new PotionEffect(MobEffects.HUNGER, 400, 1);
            PotionEffect poison = new PotionEffect(MobEffects.POISON, 400, 1);

            blindness.setCurativeItems(new ArrayList<ItemStack>());
            hunger.setCurativeItems(new ArrayList<ItemStack>());
            poison.setCurativeItems(new ArrayList<ItemStack>());

            int effect = rand.nextInt(3);

            if (effect == 0)
            {
                ((EntityLivingBase) entity).addPotionEffect(blindness);
                ((EntityLivingBase) entity).addPotionEffect(hunger);
            }
            else if (effect == 1)
            {
                ((EntityLivingBase) entity).addPotionEffect(blindness);
                ((EntityLivingBase) entity).addPotionEffect(poison);
            }
            else
            {
                ((EntityLivingBase) entity).addPotionEffect(poison);
                ((EntityLivingBase) entity).addPotionEffect(hunger);
            }
        }
    }
}
