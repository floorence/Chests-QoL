package com.chestmod.rainy.util;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ContainerChest;
import java.lang.reflect.Field;

public class ChestUtils {
    public static IInventory getPrivateInventory(Object container, String fieldName) {
        try {
            Field field = ContainerChest.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (IInventory) field.get(container);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
