package net.rainy.chestmod.screen;

import net.rainy.chestmod.util.ChestUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomChestMenu extends ChestMenu {
    private int numRows;

    private final Comparator<ItemStack> itemStackComparator = (a, b) -> {
        if (a.isEmpty() && b.isEmpty()) return 0;
        if (a.isEmpty()) return 1;
        if (b.isEmpty()) return -1;

        // Compare by item type
        return Item.getId(a.getItem()) - Item.getId(b.getItem());
    };

    public CustomChestMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, getContainerFromBuf(extraData));
        //System.out.println("constructor 1");
    }

    public CustomChestMenu(int pContainerId, Inventory inv, Container pContainer) {
        super(ModMenuTypes.CUSTOM_CHEST_MENU.get(), pContainerId, inv, pContainer, pContainer.getContainerSize()/9);
        this.numRows = pContainer.getContainerSize()/9;

    }

    public boolean isDoubleChest() {
        return (numRows == 6);
    }

    public void lootAll(ServerPlayer player) {
        for (int i = 0; i < this.getContainer().getContainerSize(); i++) {
            ItemStack stack = this.getContainer().getItem(i);

            if (!stack.isEmpty()) {
                boolean worked = player.getInventory().add(stack.copy());
                if (worked) {
                    this.getContainer().setItem(i, ItemStack.EMPTY);
                }
            }
        }
    }

    public void depositToStacks(ServerPlayer player) {
        // Skip hotbar (slots 0–8), start from slot 9
        for (int i = 9; i < player.getInventory().getContainerSize(); i++) {
            ItemStack invStack = player.getInventory().getItem(i);

            if (invStack.isEmpty()) continue;
            ChestUtils.addIfAlreadyInChest(this.getContainer(), invStack);
        }
    }

    public void sortChest() {
        List<ItemStack> stacks = new ArrayList<>();

        // Pull from chest
        for (int i = 0; i < this.getContainer().getContainerSize(); i++) {
            stacks.add(this.getContainer().getItem(i));
        }

        // Sort
        stacks.sort(itemStackComparator);

        // Put back
        for (int i = 0; i < this.getContainer().getContainerSize(); i++) {
            this.getContainer().setItem(i, i < stacks.size() ? stacks.get(i) : ItemStack.EMPTY);
        }

    }

    private static Container getContainerFromBuf(FriendlyByteBuf extraData) {
        // Retrieve container position from the packet
        BlockPos pos = extraData.readBlockPos();
        Level level = Minecraft.getInstance().level; // Only works client-side
        BlockEntity entity = level.getBlockEntity(pos);
        Block block = level.getBlockState(pos).getBlock();
        // Ensure entity is a valid container (e.g., chest or custom block entity)
        if (entity instanceof Container) {
            Container container = ChestBlock.getContainer((ChestBlock) block, entity.getBlockState(), level, pos, true);
            return container;
        }
        throw new IllegalStateException("No container found at " + pos);
    }
}
