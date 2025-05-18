package net.rainy.chestmod.screen;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.rainy.chestmod.ChestMod;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, ChestMod.MOD_ID);

//    public static final RegistryObject<MenuType<CustomChestMenu>> CUSTOM_CHEST_MENU =
//            MENUS.register("custom_chest_menu", () -> IForgeMenuType.create(CustomChestMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}