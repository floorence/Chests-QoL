package com.chestmod.rainy.network;

import com.chestmod.rainy.util.ChestUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SSortPacket implements IMessage {
    private BlockPos pos;

    public SSortPacket(BlockPos pos) {
        this.pos = pos;
    }

    public SSortPacket() {
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

    public static class Handler implements IMessageHandler<SSortPacket, IMessage> {
        @Override
        public IMessage onMessage(SSortPacket message, MessageContext ctx) {
            MinecraftServer server = ctx.getServerHandler().player.mcServer;
            server.addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(SSortPacket message, MessageContext ctx) {
            //System.out.println("Received packet");
            EntityPlayerMP player = ctx.getServerHandler().player;

            if (player == null) return;
            IInventory chestInv = ChestUtils.getInventoryFromContext(player, message.pos);
            if (chestInv == null) return;
            this.sortChest(chestInv);

        }

        private final Comparator<ItemStack> itemStackComparator = (a, b) -> {
            if (a.isEmpty() && b.isEmpty()) return 0;
            if (a.isEmpty()) return 1;
            if (b.isEmpty()) return -1;

            // Compare by item type
            return Item.getIdFromItem(a.getItem()) - Item.getIdFromItem(b.getItem());
        };

        private void sortChest(IInventory container) {
            List<ItemStack> stacks = new ArrayList<>();

            // Pull from chest
            for (int i = 0; i < container.getSizeInventory(); i++) {
                stacks.add(container.getStackInSlot(i));
            }

            // Sort
            stacks.sort(itemStackComparator);

            // Put back
            for (int i = 0; i < container.getSizeInventory(); i++) {
                container.setInventorySlotContents(i, i < stacks.size() ? stacks.get(i) : ItemStack.EMPTY);
            }
        }
    }
}