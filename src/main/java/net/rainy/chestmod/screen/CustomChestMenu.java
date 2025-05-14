package net.rainy.chestmod.screen;


import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.RegistryObject;

public class CustomChestMenu extends ChestMenu {
    private int numRows;

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
            // TODO: need to handle case where u loot a stack but already have the stack in ur inventory
            if (!stack.isEmpty()) {
                boolean worked = player.getInventory().add(stack.copy());
                if (worked) {
                    this.getContainer().setItem(i, ItemStack.EMPTY);
                }
            }
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
