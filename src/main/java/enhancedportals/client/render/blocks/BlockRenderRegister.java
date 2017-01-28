package enhancedportals.client.render.blocks;

import enhancedportals.registration.RegisterBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class BlockRenderRegister
{
    @SideOnly(Side.CLIENT)
    private static void register(Block block)
    {
        if (Item.getItemFromBlock(block) != null) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        }
    }

    public static void init()
    {
//        register(RegisterBlocks.blockStabalizer);
        register(RegisterBlocks.blockDecorBorderedQuartz);
        register(RegisterBlocks.blockDecorEnderInfusedMetal);

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RegisterBlocks.blockDecorEnderInfusedMetal), 0, new ModelResourceLocation(Item.getItemFromBlock(RegisterBlocks.blockDecorEnderInfusedMetal).getRegistryName(), "inventory"));

//        register(RegisterBlocks.blockFrame);
//        register(RegisterBlocks.blockPortal);
    }
}
