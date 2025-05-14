package net.rainy.chestmod.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.rainy.chestmod.CustomChestMenu;

public class SLootAllPacket {
    //private final BlockPos pos;

    public SLootAllPacket() {
        //this.pos = pos;
    }

    public SLootAllPacket(FriendlyByteBuf buf) {
        //this(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        //buf.writeBlockPos(this.pos);
    }

    public void handle(CustomPayloadEvent.Context context) {
        System.out.println("recieved packet");
        ServerPlayer player = context.getSender();
        if (player == null) return;
        if (player.containerMenu instanceof CustomChestMenu menu) {
            menu.lootAll(player);
        }
    }
}
