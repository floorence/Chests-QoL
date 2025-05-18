package net.rainy.chestmod.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.rainy.chestmod.util.ChestUtils;

public class SLootAllPacket {
    private final BlockPos pos;

    public SLootAllPacket(BlockPos pos) {
        this.pos = pos;
    }

    public SLootAllPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    public void handle(CustomPayloadEvent.Context context) {
        //System.out.println("recieved packet");
        ServerPlayer player = context.getSender();
        if (player == null) return;
        Container container = ChestUtils.getContainerFromContext(player, pos);
        if (container == null) return;
        this.lootAll(container, player);
    }

    private void lootAll(Container container, ServerPlayer player) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);

            player.getInventory().add(stack);
        }
    }
}
