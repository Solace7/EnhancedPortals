package enhancedportals.network;

import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.ArrayList;

public class ServerProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }

    @Override
    public void waitForController(ChunkPos controller, ChunkPos frame)
    {
        super.waitForController(controller, frame);
    }

    @Override
    public ArrayList<ChunkPos> getControllerList(ChunkPos controller)
    {
        return super.getControllerList(controller);
    }

    @Override
    public void clearControllerList(ChunkPos controller)
    {
        super.clearControllerList(controller);
    }

    @Override
    public File getBaseDir()
    {
        return super.getBaseDir();
    }

    @Override
    public File getResourcePacksDir()
    {
        return super.getResourcePacksDir();
    }

    @Override
    public File getWorldDir()
    {
        return super.getWorldDir();
    }

    @Override
    public void miscSetup()
    {
        super.miscSetup();
    }

    @Override
    public void setupCrafting()
    {
        super.setupCrafting();
    }
}
