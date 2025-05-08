package net.rainy.chestmod;


import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.rainy.chestmod.ModMenuTypes;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CustomChestMenu extends ChestMenu {

    public CustomChestMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, getContainerFromBuf(extraData));
    }

    public CustomChestMenu(int pContainerId, Inventory inv, Container pContainer) {
        super(ModMenuTypes.CUSTOM_CHEST_MENU.get(), pContainerId, inv, pContainer, 4);

    }

    private static Container getContainerFromBuf(FriendlyByteBuf extraData) {
        // Retrieve container position from the packet
        BlockPos pos = extraData.readBlockPos();
        Level level = Minecraft.getInstance().level; // Only works client-side
        BlockEntity entity = level.getBlockEntity(pos);

        // Ensure entity is a valid container (e.g., chest or custom block entity)
        if (entity instanceof Container container) {
            return container;
        }
        throw new IllegalStateException("No container found at " + pos);
    }
}
