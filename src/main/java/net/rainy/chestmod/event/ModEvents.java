package net.rainy.chestmod.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.rainy.chestmod.ChestMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.common.Mod;
import net.rainy.chestmod.screen.CustomChestMenu;

@Mod.EventBusSubscriber(modid = ChestMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    @SubscribeEvent
    public static void onChestInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;

        BlockPos pos = event.getPos();
        BlockState clickedBlock = event.getLevel().getBlockState(pos);
        Block block = clickedBlock.getBlock();

        BlockEntity blockEntity = event.getLevel().getBlockEntity(pos);
        if (blockEntity instanceof MenuProvider provider) {
            event.setCanceled(true);

            Player player = event.getEntity();

            ((ServerPlayer) player).openMenu(new SimpleMenuProvider(
                    (containerId, playerInv, playerEntity) -> {

                        if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
                            Container container = ChestBlock.getContainer((ChestBlock) block, chestBlockEntity.getBlockState(), event.getLevel(), event.getPos(), true);
                            return new CustomChestMenu(containerId, playerInv, container);
                        } else if (blockEntity instanceof Container container){
                            System.out.println("1");
                            return new CustomChestMenu(containerId, playerInv, container);
                        } else {
                            return provider.createMenu(containerId, playerInv, player); // fallback
                        }
                    },
                    provider.getDisplayName()
            ), pos);
        }
    }
}
