package com.chestmod.rainy.event;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ScreenOpenEvent {
    @SubscribeEvent
    public static void onScreenOpened(GuiOpenEvent event) {
        GuiScreen screen = event.getGui();
        if (screen instanceof GuiChest || screen instanceof GuiShulkerBox) {
            System.out.println("Screen opened!");
        }
    }
}
