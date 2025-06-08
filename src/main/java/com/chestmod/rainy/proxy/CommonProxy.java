package com.chestmod.rainy.proxy;

import com.google.common.util.concurrent.ListenableFuture;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

// credit: https://github.com/DaRealTurtyWurty/1.12-Tutorial-Mod
public class CommonProxy {
    public void registerItemRenderer(Item item, int meta, String id) {

    }

    public void render() {

    }

    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }

    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called from client side");
    }
}