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
        TEXTURE = ResourceLocation.fromNamespaceAndPath(ChestMod.MOD_ID, "textures/gui/custom_chest.png");
        this.imageWidth = 198;
        //this.imageHeight = 167;
        this.inventoryLabelY = this.imageHeight - 94;
        this.iconsY = this.imageHeight - 137;
    }
}
