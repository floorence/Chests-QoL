package com.chestmod.rainy.gui;

import com.chestmod.rainy.ChestMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerShulkerBox;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomShulkerGui extends AbstractCustomGui{
    public CustomShulkerGui(IInventory shulkerInv, EntityPlayer player, BlockPos pos) {
        super(new ContainerShulkerBox(player.inventory, shulkerInv, player), pos);
        TEXTURE = new ResourceLocation(ChestMod.MOD_ID, "textures/gui/custom_shulker_box.png");
        this.title = "Shulker Box";

        this.inventoryLabelY = this.ySize - 93;
        this.iconsY = this.ySize - 138;
    }
}
