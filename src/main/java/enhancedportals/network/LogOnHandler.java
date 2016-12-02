package enhancedportals.network;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import enhancedportals.EnhancedPortals;
import net.minecraft.entity.player.EntityPlayer;

public class LogOnHandler
{
    boolean displayed;

    @SubscribeEvent
    public void onLogIn(PlayerEvent.PlayerLoggedInEvent login)
    {
        if (!displayed && login.player != null && !CommonProxy.UPDATE_LATEST_VER.equals(EnhancedPortals.MOD_VERSION))
        {
            EntityPlayer player = login.player;
            String lateVers = CommonProxy.UPDATE_LATEST_VER;
            CommonProxy.Notify(player, lateVers);
            displayed = true;
        }
    }
}
