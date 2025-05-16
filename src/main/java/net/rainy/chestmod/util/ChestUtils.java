package net.rainy.chestmod.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.Container;

public class ChestUtils {
    // returns number of items in stack that don't fit in chest
    // might want to add stacking to existing stacks first before empty stacks??
    public static int addStack(Container chest, ItemStack stack, int startAt) {
        for (int i = 0; i < chest.getContainerSize(); i++) {
            int index = (i + startAt) % chest.getContainerSize();
            ItemStack chestStack = chest.getItem(index);
            if (chestStack.isEmpty()) {
                chest.setItem(index, stack.copy());
                return 0;
            } else if (chestStack.getItem() == stack.getItem()){
                int maxStackSize = Math.min(chestStack.getMaxStackSize(), chest.getMaxStackSize());
                int transferAmount = Math.min(stack.getCount(), maxStackSize - chestStack.getCount());
                stack.shrink(transferAmount);
                chestStack.grow(transferAmount);
            }
            if (stack.isEmpty()) return 0;
        }
        return stack.getCount();
    }

    public static void addIfAlreadyInChest(Container chest, ItemStack stack) {
        for (int j = 0; j < chest.getContainerSize(); j++) {
            ItemStack chestStack = chest.getItem(j);

            if (!chestStack.isEmpty() && chestStack.getItem() == stack.getItem()) {
                int remaining = addStack(chest, stack.copy(), j);
                int removeFromInv = stack.getCount() - remaining;
                stack.shrink(removeFromInv);
            }
        }
    }
}
