package net.rainy.chestmod.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.rainy.chestmod.ChestMod;
import net.rainy.chestmod.network.PacketHandler;
import net.rainy.chestmod.network.SDepositPacket;
import net.rainy.chestmod.network.SLootAllPacket;
import net.rainy.chestmod.network.SSortPacket;
import net.rainy.chestmod.util.IconButton;

public class CustomChestScreen extends AbstractContainerScreen<CustomChestMenu> {
    private static ResourceLocation TEXTURE;
    private int iconsY;

    public CustomChestScreen(CustomChestMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 197;
        if (menu.isDoubleChest()) {
            this.imageHeight = 221;
            TEXTURE = ResourceLocation.fromNamespaceAndPath(ChestMod.MOD_ID, "textures/gui/custom_chest_large.png");
        } else {
            this.imageHeight = 167;
            TEXTURE = ResourceLocation.fromNamespaceAndPath(ChestMod.MOD_ID, "textures/gui/custom_chest.png");
        }
        this.inventoryLabelY = this.imageHeight - 94;
        this.iconsY = this.imageHeight - 137;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        //pGuiGraphics.blitInscribed(TEXTURE, x, y, imageWidth, imageHeight, 198, 167);
        pGuiGraphics.blit(
                RenderType::guiTextured,
                TEXTURE,
                x, y,
                0f, 0f,
                imageWidth, imageHeight,
                256, 256
        );
    }

    @Override
    protected void init() {
        super.init();

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        //TODO: use icons instead of text

        this.addRenderableWidget(new IconButton(
                x + 172, y + iconsY,
                20, 20,                  // size of button
                0, 0,                    // texU, texV — top-left of your icon on the sheet
                20, 20,                  // width & height of icon
                ChestMod.ICONS,
                256, 256,                // total texture size
                button -> onSortPressed(),
                Component.literal("Sort")  // tooltip
        ));

        this.addRenderableWidget(new IconButton(
                x + 172, y + iconsY + 22,
                20, 20,                  // size of button
                20, 0,                    // texU, texV — top-left of your icon on the sheet
                20, 20,                  // width & height of icon
                ChestMod.ICONS,
                256, 256,                // total texture size
                button -> onLootAllPressed(),
                Component.literal("Loot All")  // tooltip
        ));

        this.addRenderableWidget(new IconButton(
                x + 172, y + iconsY + 56,
                20, 20,                  // size of button
                40, 0,                    // texU, texV — top-left of your icon on the sheet
                20, 20,                  // width & height of icon
                ChestMod.ICONS,
                256, 256,                // total texture size
                button -> onDepositPressed(),
                Component.literal("Deposit to existing stacks")  // tooltip
        ));
    }

    private void onSortPressed() {
        PacketHandler.sendToServer(new SSortPacket());
    }

    private void onLootAllPressed() {
        //Minecraft.getInstance().player.displayClientMessage(Component.literal("Looted all!"), false);
        PacketHandler.sendToServer(new SLootAllPacket());
    }

    private void onDepositPressed() {
        PacketHandler.sendToServer(new SDepositPacket());
    }
}
