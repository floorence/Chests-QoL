package com.chestmod.rainy.util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerShulkerBox;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Field;

public class ChestUtils {
    private static boolean canStacksMerge(ItemStack stack1, ItemStack stack2) {
        return stack1.isStackable() && stack2.isStackable() &&
                stack1.getItem() == stack2.getItem() &&
                stack1.getItemDamage() == stack2.getItemDamage() &&
                ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

    public static int addStack(IInventory chest, ItemStack stack, int startAt) {
        for (int i = 0; i < chest.getSizeInventory(); i++) {
            int index = (i + startAt) % chest.getSizeInventory();
            ItemStack chestStack = chest.getStackInSlot(index);
            if (chestStack.isEmpty()) {
                chest.setInventorySlotContents(index, stack.copy());
                return 0;
            } else if (canStacksMerge(chestStack, stack)) {
                int maxStackSize = Math.min(chestStack.getMaxStackSize(), chest.getInventoryStackLimit());
                int transferAmount = Math.min(stack.getCount(), maxStackSize - chestStack.getCount());
                stack.shrink(transferAmount);
                chestStack.grow(transferAmount);
            }
            if (stack.isEmpty()) return 0;
        }
        return stack.getCount();
    }

    public static void addIfAlreadyInChest(IInventory chest, ItemStack stack) {
        for (int j = 0; j < chest.getSizeInventory(); j++) {
            ItemStack chestStack = chest.getStackInSlot(j);

            if (!chestStack.isEmpty() && canStacksMerge(chestStack, stack)) {
                int remaining = addStack(chest, stack.copy(), j);
                int removeFromInv = stack.getCount() - remaining;
                stack.shrink(removeFromInv);
                return;
            }
        }
    }

    public static IInventory getInventoryFromContext(EntityPlayerMP player, BlockPos pos) {
        World world = player.world;
        if (!world.isBlockLoaded(pos)) return null;

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        TileEntity te = world.getTileEntity(pos);

        if (block instanceof BlockChest && te instanceof TileEntityChest) {
            return ((BlockChest) block).getContainer(world, pos, true);
        }

        if (te instanceof IInventory) {
            return (IInventory) te;
        }

        return null;
    }


    public static IInventory getPrivateInventory(Object container, String fieldName) {
        try {
            Field field = ContainerShulkerBox.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (IInventory) field.get(container);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
