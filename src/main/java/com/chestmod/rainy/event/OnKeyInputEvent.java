package com.chestmod.rainy.event;

import com.chestmod.rainy.ChestMod;
import com.chestmod.rainy.gui.InventoryGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class OnKeyInputEvent {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        // Only run when no screen is open and key is pressed
        if (mc.currentScreen == null && Keyboard.getEventKeyState()) {

            // Check if the key matches the inventory key (default is 'E')
            if (Keyboard.getEventKey() == mc.gameSettings.keyBindInventory.getKeyCode()) {

                EntityPlayerSP player = mc.player;
                mc.displayGuiScreen(new InventoryGui(player));
            }
        }
    }
}
