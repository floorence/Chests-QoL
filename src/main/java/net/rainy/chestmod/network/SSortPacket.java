package net.rainy.chestmod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.rainy.chestmod.screen.CustomChestMenu;

public class SSortPacket {

    public SSortPacket() {
    }

    public SSortPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(CustomPayloadEvent.Context context) {
        System.out.println("recieved sort packet");
        ServerPlayer player = context.getSender();
        if (player == null) return;
        if (player.containerMenu instanceof CustomChestMenu menu) {
            menu.sortChest();
        }
    }
}
