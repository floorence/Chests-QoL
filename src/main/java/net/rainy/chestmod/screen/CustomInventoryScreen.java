package net.rainy.chestmod.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.rainy.chestmod.network.PacketHandler;
import net.rainy.chestmod.network.SQuickStackPacket;

public class CustomInventoryScreen extends InventoryScreen {
    public CustomInventoryScreen(Player pPlayer) {
        super(pPlayer);
    }

    @Override
    protected void init() {
        super.init();

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.addRenderableWidget(Button.builder(
                        Component.literal("Stack"),
                        button -> onQuickStackPressed()
                )
                .pos(x + 150, y + 62)
                .size(20, 20)
                .build());
    }

    private void onQuickStackPressed() {
        PacketHandler.sendToServer(new SQuickStackPacket());
    }
}
