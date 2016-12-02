package enhancedportals.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IDismantleable
{
     void dismantleBlock(EntityPlayer player, World world, BlockPos pos);
}
