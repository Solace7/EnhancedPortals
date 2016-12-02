package enhancedportals.utility;

import buildcraft.api.tools.IToolWrench;
import cofh.api.energy.IEnergyContainerItem;
import enhancedportals.item.ItemGlasses;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

public class GeneralUtils
{
    public static double getPowerMultiplier()
    {
        return hasEnergyCost() ? ConfigurationHandler.CONFIG_POWER_MULTIPLIER : 0;
    }

    public static boolean hasEnergyCost()
    {
        return ConfigurationHandler.CONFIG_REQUIRE_POWER;
    }

    public static boolean isEnergyContainerItem(ItemStack i)
    {
        return i != null && i.getItem() instanceof IEnergyContainerItem;
    }

    public static boolean isWearingGoggles()
    {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            if (Minecraft.getMinecraft().thePlayer == null)
            {
                return false;
            }

            ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.armorItemInSlot(3);
            return stack != null && stack.getItem() == ItemGlasses.instance;
        }

        return false;
    }

    public static boolean isWrench(ItemStack i)
    {
        return i != null && i.getItem() instanceof IToolWrench;
    }

    public static ChunkPos loadChunkCoord(NBTTagCompound tagCompound, String string)
    {
        if (tagCompound.getTag(string) == null)
        {
            return null;
        }

        NBTTagCompound t = (NBTTagCompound) tagCompound.getTag(string);

        return t.getInteger("Y") == -1 ? null : new ChunkPos(t.getInteger("X"), t.getInteger("Z"));
    }

    public static ArrayList<ChunkPos> loadChunkCoordList(NBTTagCompound tag, String name)
    {
        ArrayList<ChunkPos> list = new ArrayList<ChunkPos>();

        NBTTagList tagList = tag.getTagList(name, 10);

        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound t = tagList.getCompoundTagAt(i);

            list.add(new ChunkPos(t.getInteger("X"), t.getInteger("Z")));
        }

        return list;
    }

    public static DimensionCoordinates loadWorldCoord(NBTTagCompound tagCompound, String string)
    {
        if (tagCompound.getTag(string) == null)
        {
            return null;
        }

        NBTTagCompound t = (NBTTagCompound) tagCompound.getTag(string);

        return t.getInteger("Y") == -1 ? null : new DimensionCoordinates(t.getInteger("X"), t.getInteger("Z"), t.getInteger("D"));
    }

    public static void saveChunkCoord(NBTTagCompound tagCompound, ChunkPos c, String string)
    {
        if (c == null)
        {
            return;
        }

        NBTTagCompound t = new NBTTagCompound();
        t.setInteger("X", c.chunkXPos);
        t.setInteger("Z", c.chunkZPos);

        tagCompound.setTag(string, t);
    }

    public static void saveChunkCoordList(NBTTagCompound tag, List<ChunkPos> list, String name)
    {
        NBTTagList tagList = new NBTTagList();

        for (ChunkPos c : list)
        {
            NBTTagCompound t = new NBTTagCompound();
            t.setInteger("X", c.chunkXPos);
            t.setInteger("Z", c.chunkZPos);

            tagList.appendTag(t);
        }

        tag.setTag(name, tagList);
    }

    public static void saveWorldCoord(NBTTagCompound tagCompound, DimensionCoordinates c, String string)
    {
        if (c == null)
        {
            return;
        }

        NBTTagCompound t = new NBTTagCompound();
        t.setInteger("X", c.chunkXPos);
        t.setInteger("Z", c.chunkZPos);
        t.setInteger("D", c.dimension);

        tagCompound.setTag(string, t);
    }

    public static ChunkPos offset(ChunkPos c, EnumFacing f)
    {
        return new ChunkPos(c.chunkXPos+ f.getFrontOffsetX() + f.getFrontOffsetY(), c.chunkZPos + f.getFrontOffsetZ());
    }
}
