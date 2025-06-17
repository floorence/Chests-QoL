package com.chestmod.rainy.network;

import com.chestmod.rainy.util.ChestUtils;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SLootAllPacket implements IMessage {
    private BlockPos pos;

    public SLootAllPacket(BlockPos pos) {
        this.pos = pos;
    }

    public SLootAllPacket() {}

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
            MinecraftServer server = ctx.getServerHandler().player.mcServer;
            server.addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(SLootAllPacket message, MessageContext ctx) {
            //System.out.println("Received packet");
            EntityPlayerMP player = ctx.getServerHandler().player;

            if (player == null) return;
            IInventory chestInv = ChestUtils.getInventoryFromContext(player, message.pos);
            if (chestInv == null) return;
            this.lootAll(chestInv, player);

        }

        private void lootAll(IInventory chestInv, EntityPlayer player) {
            for (int i = 0; i < chestInv.getSizeInventory(); i++) {
                ItemStack stack = chestInv.getStackInSlot(i);
                // might not work
                player.inventory.addItemStackToInventory(stack);
            }
        }
    }
}