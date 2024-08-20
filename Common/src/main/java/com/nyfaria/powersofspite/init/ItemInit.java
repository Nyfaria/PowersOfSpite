package com.nyfaria.powersofspite.init;

import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.item.SerumSyringeItem;
import com.nyfaria.powersofspite.registration.RegistrationProvider;
import com.nyfaria.powersofspite.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ItemInit {
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, SpiteConstants.MODID);
    public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, SpiteConstants.MODID);
    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register(SpiteConstants.MODID, () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> new ItemStack(ItemInit.SPITE_SERUM.get()))
            .displayItems(
                    (itemDisplayParameters, output) -> {
                        ITEMS.getEntries().forEach((registryObject) -> output.accept(new ItemStack(registryObject.get())));
                    }).title(Component.translatable("itemGroup." + SpiteConstants.MODID + ".tab"))
            .build());
    public static final RegistryObject<Item> SPITE_SERUM = ITEMS.register("spite_serum", () -> new SerumSyringeItem(getItemProperties().stacksTo(1)));
    public static final RegistryObject<Item> CHEMICAL_S = ITEMS.register("chemical_s", ()->new Item(getItemProperties().stacksTo(1)));
    public static final RegistryObject<Item> RAW_SPITE = ITEMS.register("raw_spite", ()->new Item(getItemProperties()));
    public static Item.Properties getItemProperties() {
        return new Item.Properties();
    }

    public static void loadClass() {
    }
}
