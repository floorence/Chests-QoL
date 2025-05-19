package net.rainy.chestmod.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.rainy.chestmod.ChestMod;

public class CustomShulkerScreen extends AbstractCustomScreen {
    public CustomShulkerScreen(ShulkerBoxMenu menu, Inventory playerInventory, Component title, BlockPos pos) {
        super(menu, playerInventory, title, pos);
        TEXTURE = ResourceLocation.fromNamespaceAndPath(ChestMod.MOD_ID, "textures/gui/custom_shulker_box.png");
        this.inventoryLabelY = this.imageHeight - 93;
        this.iconsY = this.imageHeight - 138;
    }
}
