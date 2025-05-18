package net.rainy.chestmod.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.rainy.chestmod.util.ChestUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SSortPacket {
    BlockPos pos;

    private final Comparator<ItemStack> itemStackComparator = (a, b) -> {
        if (a.isEmpty() && b.isEmpty()) return 0;
        if (a.isEmpty()) return 1;
        if (b.isEmpty()) return -1;

        // Compare by item type
        return Item.getId(a.getItem()) - Item.getId(b.getItem());
    };

    public SSortPacket(BlockPos pos) {
        this.pos = pos;
    }

    public SSortPacket(FriendlyByteBuf buf) {
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
        this.sortContainer(container);
    }

    public void sortContainer(Container container) {
        List<ItemStack> stacks = new ArrayList<>();

        // Pull from chest
        for (int i = 0; i < container.getContainerSize(); i++) {
            stacks.add(container.getItem(i));
        }

        // Sort
        stacks.sort(itemStackComparator);

        // Put back
        for (int i = 0; i < container.getContainerSize(); i++) {
            container.setItem(i, i < stacks.size() ? stacks.get(i) : ItemStack.EMPTY);
        }
    }
}
