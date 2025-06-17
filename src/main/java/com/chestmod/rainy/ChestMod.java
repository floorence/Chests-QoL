package com.chestmod.rainy;


import com.chestmod.rainy.event.EventHandler;
import com.chestmod.rainy.network.PacketHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ChestMod.MOD_ID, name = ChestMod.NAME, version = ChestMod.VERSION)
public class ChestMod {
    public static final String MOD_ID = "chestmod";
    public static final String NAME = "Chests QoL";
    public static final String VERSION = "1.0";

    public static final ResourceLocation ICONS = new ResourceLocation(ChestMod.MOD_ID, "textures/gui/icons.png");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        EventHandler.registerEvents();
        PacketHandler.registerPackets();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
    }
}
