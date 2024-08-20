package com.nyfaria.powersofspite.datagen;

import com.google.common.collect.ImmutableMap;
import com.nyfaria.powersofspite.Constants;
import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.init.AbilityInit;
import com.nyfaria.powersofspite.init.PowerInit;
import com.nyfaria.powersofspite.power.api.Power;
import com.nyfaria.powersofspite.registration.RegistryObject;
import com.nyfaria.powersofspite.init.BlockInit;
import com.nyfaria.powersofspite.init.EntityInit;
import com.nyfaria.powersofspite.init.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ModLangProvider extends LanguageProvider {
    protected static final Map<String, String> REPLACE_LIST = ImmutableMap.of(
            "tnt", "TNT",
            "sus", ""
    );

    public ModLangProvider(PackOutput gen) {
        super(gen, Constants.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ItemInit.ITEMS.getEntries().forEach(this::itemLang);
        EntityInit.ENTITIES.getEntries().forEach(this::entityLang);
        BlockInit.BLOCKS.getEntries().forEach(this::blockLang);
        add("itemGroup." + Constants.MODID, Constants.MOD_NAME);
        PowerInit.POWERS.getEntries().forEach(this::powerLang);
        AbilityInit.ABILITIES.getEntries().forEach(this::abilityLang);
        add(Constants.getPowerDescription(PowerInit.FLIGHT.get(), 0),"• Passive Ability of Flight");
        add(Constants.getAbilityDescription(AbilityInit.FLIGHT.get(),0), "• Allows you to fly, double jump to start.");
        add(Constants.getAbilityDescription(AbilityInit.FLIGHT.get(),1), "• While Sprinting during flight, you will move in the direction you are looking");
        add(Constants.getAbilityDescription(AbilityInit.NONE.get(),0),"• Please select a Power to the Left!");
    }

    protected void itemLang(RegistryObject<Item> entry) {
        if (!(entry.get() instanceof BlockItem) || entry.get() instanceof ItemNameBlockItem) {
            addItem(entry, checkReplace(entry));
        }
    }
    protected void powerLang(RegistryObject<Power> entry) {
            add(Constants.getDescriptionId(entry.get()), checkReplace(entry));
    }
    protected void abilityLang(RegistryObject<Ability> entry) {
            add(Constants.getDescriptionId(entry.get()), checkReplace(entry));
    }

    protected void blockLang(RegistryObject<Block> entry) {
        addBlock(entry, checkReplace(entry));
    }

    protected void entityLang(RegistryObject<EntityType<?>> entry) {
        addEntityType(entry, checkReplace(entry));
    }

    protected String checkReplace(RegistryObject<?> registryObject) {
        return Arrays.stream(registryObject.getId().getPath().split("_"))
                .map(this::checkReplace)
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining(" "))
                .trim();
    }

    protected String checkReplace(String string) {
        return REPLACE_LIST.containsKey(string) ? REPLACE_LIST.get(string) : StringUtils.capitalize(string);
    }
}
