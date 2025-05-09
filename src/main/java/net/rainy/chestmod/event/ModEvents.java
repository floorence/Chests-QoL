package net.rainy.chestmod.event;

import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraftforge.client.event.ScreenEvent;
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
    public static void onScreenOpen(ScreenEvent.Opening event) {
        // might need to intercept Opening ScreenEvent instead ??? NVM ITS THE CORRECT EVENT!!!
        //Minecraft.getInstance().player.displayClientMessage(Component.literal("opened chest 1"), false);
        // wtf is pActionBar ???
        // a screen is an array of menus???
        if (event.getScreen() instanceof ContainerScreen screen) {
            ChestMenu menu = screen.getMenu();
            Inventory playerInventory = Minecraft.getInstance().player.getInventory();
            Component title = screen.getTitle();
            Minecraft.getInstance().player.displayClientMessage(Component.literal("opened chest 2"), false);

            // Cancel the original screen
            event.setCanceled(true);

            // Open your custom one
            CustomChestMenu customMenu = CustomChestMenu.fromVanilla(menu, playerInventory);
            CustomChestScreen customScreen = new CustomChestScreen(customMenu, playerInventory, title);
            //Minecraft.getInstance().setScreen(customScreen);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if(event.getEntity() instanceof Sheep sheep && event.getSource().getDirectEntity() instanceof Player player) {
            if(player.getMainHandItem().getItem() == Items.END_ROD) {
                player.displayClientMessage(Component.literal(player.getName().getString() + " JUST HIT A SHEEP WITH AN END ROD! YOU SICK FRICK!"), false);
                sheep.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 5));
                player.getMainHandItem().shrink(1);
            }
        }
    }
}
