package net.rainy.chestmod.event;

import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.rainy.chestmod.ChestMod;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.Minecraft;
import net.rainy.chestmod.CustomChestMenu;
import net.rainy.chestmod.CustomChestScreen;

@Mod.EventBusSubscriber(modid = ChestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    @SubscribeEvent
    public static void onChestInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return; // only handle on the server

        BlockPos pos = event.getPos();
        BlockState clickedBlock = event.getLevel().getBlockState(pos);

        if (clickedBlock.getBlock() instanceof ChestBlock) {
            // Cancel the default chest opening
            event.setCanceled(true);

            Player player = event.getEntity(); // This is the player who clicked
            player.displayClientMessage(Component.literal("opened chest"), false);

            BlockEntity blockEntity = event.getLevel().getBlockEntity(pos);
            if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
                player.displayClientMessage(Component.literal("opening menu"), false);
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(
                        (containerId, playerInv, playerEntity) ->
                                new CustomChestMenu(containerId, playerInv, chestBlockEntity),
                        Component.literal("Chest")
                ), pos);
            }
        }
    }
}
