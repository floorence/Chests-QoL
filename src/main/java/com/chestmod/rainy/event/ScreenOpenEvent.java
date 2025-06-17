package com.chestmod.rainy.event;

import com.chestmod.rainy.gui.CustomChestGui;
import com.chestmod.rainy.gui.CustomShulkerGui;
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
import net.minecraft.inventory.ContainerShulkerBox;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
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
            //System.out.println("Interacted with a chest or shulker at: " + pos);
        }
    }

    @SubscribeEvent
    public static void onScreenOpened(GuiOpenEvent event) {
        GuiScreen screen = event.getGui();
        if (screen instanceof GuiChest || screen instanceof GuiShulkerBox) {
            GuiContainer guiContainer = (GuiContainer) screen;
            Container container = guiContainer.inventorySlots;

            if (lastChestInteractionPos == null) return;

            EntityPlayer player = Minecraft.getMinecraft().player;

            if (container instanceof ContainerChest) {
                ContainerChest chest = (ContainerChest) container;
                IInventory playerInv = player.inventory;
                IInventory chestInv = chest.getLowerChestInventory();
                event.setGui(new CustomChestGui(playerInv, chestInv, player, lastChestInteractionPos));
            } else if (container instanceof ContainerShulkerBox) {
                ContainerShulkerBox shulkerBox = (ContainerShulkerBox) container;
                System.out.println("Container class: " + shulkerBox.getClass().getName());
                //IInventory shulkerInv = ObfuscationReflectionHelper.getPrivateValue(ContainerShulkerBox.class, shulkerBox, "inventory");
                //IInventory shulkerInv = ChestUtils.getPrivateInventory(shulkerBox);
                IInventory shulkerInv = container.getSlot(0).inventory;
                event.setGui(new CustomShulkerGui(shulkerInv, player, lastChestInteractionPos));
            }
            lastChestInteractionPos = null;
        }
    }
}
