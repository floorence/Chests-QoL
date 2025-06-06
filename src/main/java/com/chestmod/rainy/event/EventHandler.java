package com.chestmod.rainy.event;

import net.minecraftforge.common.MinecraftForge;

public class EventHandler {
    public static void registerEvents()
    {
        ScreenOpenEvent screenOpenEvent = new ScreenOpenEvent();

        MinecraftForge.EVENT_BUS.register(screenOpenEvent);
    }
}

