package com.chestmod.rainy.gui;

import com.chestmod.rainy.ChestMod;

import com.chestmod.rainy.util.IconButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public abstract class AbstractCustomGui extends GuiContainer {
    protected static ResourceLocation TEXTURE;
    protected int iconsY;
    protected BlockPos pos;

    public AbstractCustomGui(Container container, BlockPos pos) {
        super(container);
        this.pos = pos;
        this.xSize = 198;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void initGui() {
        super.initGui();

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        this.buttonList.add(new IconButton(
                0,
                x + 172, y + iconsY,
                20, 20,
                0, 0,
                ChestMod.ICONS,
                "Sort"
        ));

        this.buttonList.add(new IconButton(
                1,
                x + 172, y + iconsY + 22,
                20, 20,
                20, 0,
                ChestMod.ICONS,
                "Loot All"
        ));

        this.buttonList.add(new IconButton(
                2,
                x + 172, y + iconsY + 56,
                20, 20,
                40, 0,
                ChestMod.ICONS,
                "Deposit"
        ));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof IconButton) {
            switch (button.id) {
                case 0:
                    onSortPressed();
                    break;
                case 1:
                    onLootAllPressed();
                    break;
                case 2:
                    onDepositPressed();
                    break;
            }
        }
    }

    private void onSortPressed() {}
    private void onLootAllPressed() {
        System.out.println("onLootAllPressed");
    }
    private void onDepositPressed() {}

}
