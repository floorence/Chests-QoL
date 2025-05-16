package net.rainy.chestmod.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.rainy.chestmod.util.ChestUtils;

public class SQuickStackPacket {

    public SQuickStackPacket() {
    }

    public SQuickStackPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(CustomPayloadEvent.Context context) {
        System.out.println("recieved stack packet");
        ServerPlayer player = context.getSender();

        if (player == null) return;

        Level level = player.level();
        BlockPos playerPos = player.blockPosition();
        int radius = 6;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;
            for (BlockPos pos : BlockPos.betweenClosed(
                    playerPos.offset(-radius, -radius, -radius),
                    playerPos.offset(radius, radius, radius))) {

                Block block = level.getBlockState(pos).getBlock();
                BlockEntity be = level.getBlockEntity(pos);

                if (be instanceof ChestBlockEntity chestBlockEntity) {
                    Container container = ChestBlock.getContainer((ChestBlock) block, chestBlockEntity.getBlockState(), level, pos, true);
                    if (container == null) {
                        System.out.println("container is null");
                        continue;
                    }
                    ChestUtils.addIfAlreadyInChest(container, stack);
                }
            }
        }
    }
}
