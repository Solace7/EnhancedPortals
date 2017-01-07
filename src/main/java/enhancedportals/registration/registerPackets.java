package enhancedportals.registration;

import enhancedportals.EnhancedPortals;
import enhancedportals.network.packet.*;

public class registerPackets
{
    public static final void init()
    {
        EnhancedPortals.packetPipeline.registerPacket(PacketRequestGui.class);
        EnhancedPortals.packetPipeline.registerPacket(PacketTextureData.class);
        EnhancedPortals.packetPipeline.registerPacket(PacketRerender.class);
        EnhancedPortals.packetPipeline.registerPacket(PacketGuiData.class);
        EnhancedPortals.packetPipeline.registerPacket(PacketGui.class);
    }
}
