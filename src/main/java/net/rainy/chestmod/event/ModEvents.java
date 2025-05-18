package net.rainy.chestmod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.rainy.chestmod.ChestMod;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import net.rainy.chestmod.screen.AbstractCustomScreen;
import net.rainy.chestmod.screen.CustomChestScreen;
import net.rainy.chestmod.screen.CustomShulkerScreen;

@Mod.EventBusSubscriber(modid = ChestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    public static BlockPos lastChestInteractionPos = null;

    @SubscribeEvent
    public static void onChestInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;

        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        BlockState state = player.level().getBlockState(pos);

        if (state.getBlock() instanceof ChestBlock || state.getBlock() instanceof ShulkerBoxBlock) {
            lastChestInteractionPos = pos;
        }
    }

    @SubscribeEvent
    public static void onScreenOpened(ScreenEvent.Opening event) {
        Screen screen = event.getScreen();
        System.out.println("1");
        if (screen instanceof ContainerScreen || screen instanceof ShulkerBoxScreen) {
            System.out.println("2");
            AbstractContainerMenu menu = ((AbstractContainerScreen<AbstractContainerMenu>) screen).getMenu();
            Inventory playerInventory = Minecraft.getInstance().player.getInventory();
            Component title = screen.getTitle();
            //Minecraft.getInstance().player.displayClientMessage(Component.literal("opened chest 2"), false);
            System.out.println("3");
            if (lastChestInteractionPos == null) return;

            Screen customScreen = null;
            if (menu instanceof ChestMenu chestMenu) {
                customScreen = new CustomChestScreen(chestMenu, playerInventory, title, lastChestInteractionPos);
            } else if (menu instanceof ShulkerBoxMenu shulkerBoxMenu) {
                customScreen = new CustomShulkerScreen(shulkerBoxMenu, playerInventory, title, lastChestInteractionPos);
            }

            System.out.println("4");
            if (customScreen == null) return;

            event.setCanceled(true);
            Minecraft.getInstance().setScreen(customScreen);
            lastChestInteractionPos = null;
        }
    }
}
