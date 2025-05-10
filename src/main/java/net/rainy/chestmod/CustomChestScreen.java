package net.rainy.chestmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.Builder;
import net.minecraft.client.renderer.RenderType;
import net.rainy.chestmod.ChestMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CustomChestScreen extends AbstractContainerScreen<CustomChestMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ChestMod.MOD_ID, "textures/gui/custom_chest.png");

    public CustomChestScreen(CustomChestMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 198;
        this.imageHeight = 167;
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

        this.addRenderableWidget(Button.builder(
                        Component.literal("Loot"),
                        button -> onLootAllPressed()
                )
                .pos(x + 172, y + 54)
                .size(40, 20)
                .build());

        this.addRenderableWidget(Button.builder(
                        Component.literal("Stack"),
                        button -> onStackPressed()
                )
                .pos(x + 172, y + 86)
                .size(40, 20)
                .build());
    }

    private void onLootAllPressed() {
        Minecraft.getInstance().player.displayClientMessage(Component.literal("Looted all!"), false);
        this.getMenu().lootAll();
    }

    private void onStackPressed() {

    }
}
