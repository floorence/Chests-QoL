package com.chestmod.rainy.util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Field;

public class ChestUtils {
    public static IInventory getInventoryFromContext(EntityPlayerMP player, BlockPos pos) {
        World world = player.world;
        if (!world.isBlockLoaded(pos)) return null;

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block instanceof BlockChest) {
            TileEntity te = world.getTileEntity(pos);
            if (!(te instanceof TileEntityChest)) return null;

            return ((BlockChest) block).getContainer(world, pos, true);
        }

        return null;
    }

}
