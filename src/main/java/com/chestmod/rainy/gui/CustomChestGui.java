package com.chestmod.rainy.gui;

import com.chestmod.rainy.ChestMod;

import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class CustomChestGui extends AbstractCustomGui {
    public CustomChestGui(Container container, BlockPos pos) {
        super(container, pos);
//        if (container.() == 6) { // double chest
//            this.ySize = 221;
//            TEXTURE = new ResourceLocation(ChestMod.MOD_ID + "textures/gui/custom_chest_large.png");
//        } else {
        TEXTURE = new ResourceLocation(ChestMod.MOD_ID + "textures/gui/custom_chest.png");
        //}
        //this.inventoryLabelY = this.imageHeight - 93;
        this.iconsY = this.ySize - 138;
    }
}
