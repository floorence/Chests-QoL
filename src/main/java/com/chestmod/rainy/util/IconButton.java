package com.chestmod.rainy.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class IconButton extends GuiButton {
    private final int texU, texV;
    private final ResourceLocation texture;
    private final String tooltip;

    public IconButton(int id, int x, int y, int width, int height,
                      int texU, int texV,
                      ResourceLocation texture,
                      String tooltip) {
        super(id, x, y, width, height, "");
        this.texU = texU;
        this.texV = texV;
        this.texture = texture;
        this.tooltip = tooltip;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(texture);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int vOffset = this.hovered ? this.height : 0;
            drawTexturedModalRect(this.x, this.y, texU, texV + vOffset, this.width, this.height);
        }
    }

    public String getTooltip() {
        return tooltip;
    }
}
