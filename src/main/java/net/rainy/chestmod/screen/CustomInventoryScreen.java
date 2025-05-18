package net.rainy.chestmod.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.rainy.chestmod.ChestMod;
import net.rainy.chestmod.network.PacketHandler;
import net.rainy.chestmod.network.SQuickStackPacket;
import net.rainy.chestmod.util.IconButton;

public class CustomInventoryScreen extends InventoryScreen {
    public CustomInventoryScreen(Player pPlayer) {
        super(pPlayer);
        //System.out.println("screen constructor");
    }

    @Override
    protected void init() {
        super.init();
        //System.out.println("screen init");

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.addRenderableWidget(new IconButton(
                x + 150, y + 62,
                20, 20,                  // size of button
                60, 0,                    // texU, texV — top-left of your icon on the sheet
                20, 20,                  // width & height of icon
                ChestMod.ICONS,
                256, 256,                // total texture size
                button -> onQuickStackPressed(),
                Component.literal("Quick stack to nearby chests")  // tooltip
        ));
    }

    private void onQuickStackPressed() {
        PacketHandler.sendToServer(new SQuickStackPacket());
    }
}
