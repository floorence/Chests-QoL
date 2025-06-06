package com.chestmod.rainy.gui;

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
}
