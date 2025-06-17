package com.chestmod.rainy.network;

import com.chestmod.rainy.util.ChestUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SQuickStackPacket implements IMessage {

    public SQuickStackPacket() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<SQuickStackPacket, IMessage> {
        @Override
        public IMessage onMessage(SQuickStackPacket message, MessageContext ctx) {
            MinecraftServer server = ctx.getServerHandler().player.mcServer;
            server.addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(SQuickStackPacket message, MessageContext ctx) {
            //System.out.println("Received packet");
            EntityPlayerMP player = ctx.getServerHandler().player;

            if (player == null) return;
            this.quickStack(player);

        }

        private void quickStack(EntityPlayerMP player) {
            int radius = 6;

            for (int i = 9; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack.isEmpty()) continue;
                for (BlockPos pos : BlockPos.getAllInBox(
                        new BlockPos(player.posX - radius, player.posY - radius, player.posZ - radius),
                        new BlockPos(player.posX + radius, player.posY + radius, player.posZ + radius))) {

                    IInventory chestInv = ChestUtils.getInventoryFromContext(player, pos);
                    if (chestInv != null)
                        ChestUtils.addIfAlreadyInChest(chestInv, stack);
                }
            }
        }
    }
}