package com.chestmod.rainy.gui;

import com.chestmod.rainy.ChestMod;

import com.chestmod.rainy.network.PacketHandler;
import com.chestmod.rainy.network.SLootAllPacket;
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
    protected String title;
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
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(title, 8, 6, 4210752);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
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
        PacketHandler.network.sendToServer(new SLootAllPacket(pos));
    }
    private void onDepositPressed() {}

}
