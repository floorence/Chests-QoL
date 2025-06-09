package com.chestmod.rainy.gui;

import com.chestmod.rainy.ChestMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class CustomChestGui extends AbstractCustomGui {
    public CustomChestGui(IInventory upper, IInventory lower, EntityPlayer player, BlockPos pos) {
        super(new ContainerChest(upper, lower, player), pos);
//        if (container.() == 6) { // double chest
//            this.ySize = 221;
//            TEXTURE = new ResourceLocation(ChestMod.MOD_ID + "textures/gui/custom_chest_large.png");
//        } else {
        TEXTURE = new ResourceLocation(ChestMod.MOD_ID, "gui/custom_chest.png");
        //}
        //this.inventoryLabelY = this.imageHeight - 93;
        this.iconsY = this.ySize - 138;
    }
}
