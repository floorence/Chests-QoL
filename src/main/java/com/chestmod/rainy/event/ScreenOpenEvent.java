package com.chestmod.rainy.event;

import com.chestmod.rainy.gui.CustomChestGui;
import com.chestmod.rainy.util.ChestUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ScreenOpenEvent {
    private static BlockPos lastChestInteractionPos = null;
    @SubscribeEvent
    public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.getSide().isClient()) return;

        BlockPos pos = event.getPos();
        World world = event.getWorld();
        EntityPlayer player = event.getEntityPlayer();

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block instanceof BlockChest || block instanceof BlockShulkerBox) {
            lastChestInteractionPos = pos;
            System.out.println("Interacted with a chest or shulker at: " + pos);
        }
    }

    @SubscribeEvent
    public static void onScreenOpened(GuiOpenEvent event) {
        GuiScreen screen = event.getGui();
        if (screen instanceof GuiChest) {
            GuiContainer guiContainer = (GuiContainer) screen;
            Container container = guiContainer.inventorySlots;

            if (lastChestInteractionPos == null) return;

            if (container instanceof ContainerChest) {
                ContainerChest chest = (ContainerChest) container;
                EntityPlayer player = Minecraft.getMinecraft().player;

                IInventory playerInv = player.inventory;
                IInventory chestInv = chest.getLowerChestInventory();
                event.setGui(new CustomChestGui(playerInv, chestInv, player, lastChestInteractionPos));
            }
            lastChestInteractionPos = null;
        }
    }
}
