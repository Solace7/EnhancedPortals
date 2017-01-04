package enhancedportals.network.packet;

import enhancedportals.item.ItemEP;
import enhancedportals.tile.TileEP;
import enhancedportals.utility.LogHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class PacketGui extends PacketEP
{
    TileEP tile;
    ItemEP item;
    int x, y, z;
    ByteBuf buf;

    public PacketGui()
    {

    }

    public PacketGui(TileEP t)
    {
        tile = t;
    }

    public PacketGui(ItemEP i)
    {
        item = i;
    }

    public PacketGui(TileEP t, ItemEP i) {
        tile = t;
        item = i;
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        buf = buffer;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(tile.getPos().getX());
        buffer.writeInt(tile.getPos().getY());
        buffer.writeInt(tile.getPos().getZ());

        tile.packetGuiFill(buffer);
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        TileEntity t = player.worldObj.getTileEntity(tile.getPos());
        ItemStack stack = player.getHeldItemMainhand();

       if (t != null && t instanceof TileEP && stack == null)
        {
            tile = (TileEP) t;
            tile.packetGuiUse(buf);
        }

        if(t != null & t instanceof TileEP && stack != null && stack.getItem() instanceof  ItemEP){
            LogHelper.warn("Tried to use unimplemented feature");

        }

        if (t == null && stack != null && stack.getItem() instanceof ItemEP)
        {
            Item i = stack.getItem();
            item = (ItemEP) i;
            item.packetGuiUse(buf);
        }

    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {

    }
}
