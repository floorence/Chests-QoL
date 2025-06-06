package com.chestmod.rainy;


import com.chestmod.rainy.event.EventHandler;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ChestMod.MOD_ID, name = ChestMod.NAME, version = ChestMod.VERSION)
public class ChestMod {
    public static final String MOD_ID = "chestmod";
    public static final String NAME = "Chests QoL";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        EventHandler.registerEvents();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
    }
}
