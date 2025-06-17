package com.chestmod.rainy.gui;

import com.chestmod.rainy.ChestMod;
import com.chestmod.rainy.network.PacketHandler;
import com.chestmod.rainy.network.SQuickStackPacket;
import com.chestmod.rainy.network.SSortPacket;
import com.chestmod.rainy.util.IconButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class InventoryGui extends GuiInventory {
    public InventoryGui(EntityPlayer player) {
        super(player);
    }

    @Override
    public void initGui() {
        super.initGui();

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        this.buttonList.add(new IconButton(
                0,
                x + 150, y + 60,
                20, 20,
                60, 0,
                ChestMod.ICONS,
                "Quick stack to nearby chests"
        ));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        boolean recipeBookVisible = this.func_194310_f().isVisible();
        boolean screenTooNarrow = this.width < 379;

        int x;
        if (recipeBookVisible && !screenTooNarrow) {
            x = 77 + (this.width - this.xSize) / 2;
        } else {
            x = (this.width - this.xSize) / 2;
        }

        int y = (this.height - this.ySize) / 2;

        for (GuiButton button : this.buttonList) {
            if (button.id == 0) {
                button.x = x + 150;
                button.y = y + 60;
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button instanceof IconButton) {
            if (button.id == 0) {
                onQuickStackPressed();
            }
        }
    }

    private void onQuickStackPressed() {
        PacketHandler.network.sendToServer(new SQuickStackPacket());
    }
}
