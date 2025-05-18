package net.rainy.chestmod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.rainy.chestmod.ChestMod;
import net.rainy.chestmod.screen.CustomChestScreen;
import net.rainy.chestmod.screen.CustomInventoryScreen;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = ChestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        //System.out.println("1");
        Minecraft mc = Minecraft.getInstance();

        // Only run when no screen is open
        if (mc.screen == null && event.getAction() == GLFW.GLFW_PRESS) {

            // Check if it's the inventory key
            if (event.getKey() == mc.options.keyInventory.getKey().getValue()) {
                //System.out.println("2");
                Player player = mc.player;

                mc.setScreen(new CustomInventoryScreen(player));
            }
        }
    }
}
