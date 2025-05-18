package net.rainy.chestmod.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IconButton extends Button {
    private final ResourceLocation texture;
    private final int texU, texV;
    private final int texW, texH;
    private final int textureWidth, textureHeight;

    public IconButton(int x, int y, int width, int height,
                      int texU, int texV, int texW, int texH,
                      ResourceLocation texture,
                      int textureWidth, int textureHeight,
                      OnPress onPress,
                      Component tooltip) {
        super(x, y, width, height, tooltip, onPress, DEFAULT_NARRATION);
        this.texture = texture;
        this.texU = texU;
        this.texV = texV;
        this.texW = texW;
        this.texH = texH;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int vOffset = isHoveredOrFocused() ? texH : 0;
        guiGraphics.blit(RenderType::guiTextured, texture, getX(), getY(), texU, texV + vOffset, texW, texH, textureWidth, textureHeight);
    }
}
