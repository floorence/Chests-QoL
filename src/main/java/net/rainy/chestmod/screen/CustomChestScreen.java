package net.rainy.chestmod.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.rainy.chestmod.ChestMod;
import net.rainy.chestmod.network.PacketHandler;
import net.rainy.chestmod.network.SDepositPacket;
import net.rainy.chestmod.network.SLootAllPacket;
import net.rainy.chestmod.network.SSortPacket;
import net.rainy.chestmod.util.IconButton;

public class CustomChestScreen extends AbstractCustomScreen {

    public CustomChestScreen(ChestMenu menu, Inventory playerInventory, Component title, BlockPos pos) {
        super(menu, playerInventory, title, pos);
        if (menu.getRowCount() == 6) { // double chest
            this.imageHeight = 221;
            TEXTURE = ResourceLocation.fromNamespaceAndPath(ChestMod.MOD_ID, "textures/gui/custom_chest_large.png");
        } else {
            TEXTURE = ResourceLocation.fromNamespaceAndPath(ChestMod.MOD_ID, "textures/gui/custom_chest.png");
        }
        this.inventoryLabelY = this.imageHeight - 93;
        this.iconsY = this.imageHeight - 138;
    }
}
