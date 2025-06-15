package com.chestmod.rainy.event;

import net.minecraftforge.common.MinecraftForge;

public class EventHandler {
    public static void registerEvents()
    {
        ScreenOpenEvent screenOpenEvent = new ScreenOpenEvent();
        OnKeyInputEvent onKeyInputEvent = new OnKeyInputEvent();

        MinecraftForge.EVENT_BUS.register(screenOpenEvent);
        MinecraftForge.EVENT_BUS.register(onKeyInputEvent);
    }
}

