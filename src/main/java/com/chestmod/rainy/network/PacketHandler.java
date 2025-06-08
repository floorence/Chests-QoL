package com.chestmod.rainy.network;

import com.chestmod.rainy.ChestMod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(ChestMod.MOD_ID);;

    public static void registerPackets() {
        network.registerMessage(SLootAllPacket.Handler.class, SLootAllPacket.class, 0, Side.SERVER);

    }
}
