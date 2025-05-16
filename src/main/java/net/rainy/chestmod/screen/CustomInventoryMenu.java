package net.rainy.chestmod.screen;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;

public class CustomInventoryMenu extends InventoryMenu {
    public CustomInventoryMenu(Player player) {
        super(player.getInventory(), true, player);
        // TODO: find out wtf is pActive???
    }

    public void stackToNearbyChests(ServerPlayer player) {
        // TODO
    }
}
