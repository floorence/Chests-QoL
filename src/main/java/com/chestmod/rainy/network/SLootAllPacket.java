package com.chestmod.rainy.network;

import com.chestmod.rainy.ChestMod;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SLootAllPacket implements IMessage {
    private BlockPos pos;

    public SLootAllPacket(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        this.pos = new BlockPos(x, y, z);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    public static class Handler implements IMessageHandler<SLootAllPacket, IMessage> {
        @Override
        public IMessage onMessage(SLootAllPacket message, MessageContext ctx) {
            ChestMod.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(SLootAllPacket message, MessageContext ctx) {

        }
    }
}