package net.rainy.chestmod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
import net.rainy.chestmod.ChestMod;

public class PacketHandler {
    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
            ResourceLocation.fromNamespaceAndPath(ChestMod.MOD_ID, "main"))
            .serverAcceptedVersions((status, version) -> true)
            .clientAcceptedVersions((status, version) -> true)
            .networkProtocolVersion(1)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(SLootAllPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SLootAllPacket::encode)
                .decoder(SLootAllPacket::new)
                .consumerMainThread(SLootAllPacket::handle)
                .add();
        INSTANCE.messageBuilder(SDepositPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SDepositPacket::encode)
                .decoder(SDepositPacket::new)
                .consumerMainThread(SDepositPacket::handle)
                .add();

    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }

}
