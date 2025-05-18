package net.rainy.chestmod.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.rainy.chestmod.util.ChestUtils;

public class SDepositPacket {
    BlockPos pos;

    public SDepositPacket(BlockPos pos) {
        this.pos = pos;
    }

    public SDepositPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null) return;
        Container container = ChestUtils.getContainerFromContext(player, pos);
        if (container == null) return;
        this.depositToStacks(container, player);
    }

    public void depositToStacks(Container container, ServerPlayer player) {
        // Skip hotbar (slots 0–8), start from slot 9
        for (int i = 9; i < player.getInventory().getContainerSize(); i++) {
            ItemStack invStack = player.getInventory().getItem(i);

            if (invStack.isEmpty()) continue;
            ChestUtils.addIfAlreadyInChest(container, invStack);
        }
    }
}
