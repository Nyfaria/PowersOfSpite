package com.nyfaria.powersofspite.datagen;

import com.google.common.collect.ImmutableMap;
import com.nyfaria.powersofspite.SpiteConstants;
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
        super(gen, SpiteConstants.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ItemInit.ITEMS.getEntries().forEach(this::itemLang);
        EntityInit.ENTITIES.getEntries().forEach(this::entityLang);
        BlockInit.BLOCKS.getEntries().forEach(this::blockLang);
        add("itemGroup." + SpiteConstants.MODID + ".tab", SpiteConstants.MOD_NAME);
        PowerInit.POWERS.getEntries().forEach(this::powerLang);
        AbilityInit.ABILITIES.getEntries().forEach(this::abilityLang);
        add("key."+ SpiteConstants.MODID+".ability_0", "Ability 0");
        add("key."+ SpiteConstants.MODID+".ability_1", "Ability 1");
        add("key."+ SpiteConstants.MODID+".ability_2", "Ability 2");
        add("key."+ SpiteConstants.MODID+".open_screen", "Open Spite Screen");
        add("key.categories."+ SpiteConstants.MODID, SpiteConstants.MOD_NAME);
        add(SpiteConstants.getPowerDescription(PowerInit.NONE.get(), 0),"• Please select a Power to the Left!");
        add(SpiteConstants.getPowerDescription(PowerInit.FLIGHT.get(), 0),"• Passive Ability of Flight");
        add(SpiteConstants.getPowerDescription(PowerInit.TRITON.get(), 0),"• Powers of a Triton");
        add(SpiteConstants.getPowerDescription(PowerInit.SUPER_STRENGTH.get(), 0),"• Increased Physical Power!");
        add(SpiteConstants.getPowerDescription(PowerInit.SUPER_SPEED.get(), 0),"• Run like a Speedster!");
        add(SpiteConstants.getPowerDescription(PowerInit.GHOST.get(), 0),"• Become intangible and walk through walls");
        add(SpiteConstants.getPowerDescription(PowerInit.GHOST.get(), 1),"• (caution, with great power comes great danger potential)");
        add(SpiteConstants.getPowerDescription(PowerInit.INVISIBILITY.get(), 0),"• Become invisible");
        add(SpiteConstants.getPowerDescription(PowerInit.KEEN_SIGHT.get(), 0),"• Your ability to see is greatly increased");
        add(SpiteConstants.getPowerDescription(PowerInit.TELEPORTATION.get(), 0),"• Teleport like an Enderman!");
        add(SpiteConstants.getPowerDescription(PowerInit.ELASTICITY.get(), 0),"• Bounce like a rubber ball");
        add(SpiteConstants.getPowerDescription(PowerInit.ELASTICITY.get(), 1),"• No Fall Damage");
        add(SpiteConstants.getPowerDescription(PowerInit.ELASTICITY.get(), 2),"• Half Damage");
        add(SpiteConstants.getPowerDescription(PowerInit.DISPLACER.get(), 0),"• Create Clones to fight for you");
        add(SpiteConstants.getPowerDescription(PowerInit.SPATIAL_TRAVEL.get(), 0),"• Create Portals to travel through");




        add(SpiteConstants.getAbilityDescription(AbilityInit.FLIGHT.get(),0), "• Allows you to fly, double jump to start.");
        add(SpiteConstants.getAbilityDescription(AbilityInit.FLIGHT.get(),1), "• While Sprinting during flight, you will move in the direction you are looking");
        add(SpiteConstants.getAbilityDescription(AbilityInit.NONE.get(),0),"• Please select a Power to the Left!");
        add(SpiteConstants.getAbilityDescription(AbilityInit.WATER_BREATHING.get(),0), "• Allows you to breathe underwater");
        add(SpiteConstants.getAbilityDescription(AbilityInit.SWIM_SPEED.get(),0), "• Really fast swim speed");
        add(SpiteConstants.getAbilityDescription(AbilityInit.SUPER_SPEED.get(),0), "• Super fast speed");
        add(SpiteConstants.getAbilityDescription(AbilityInit.HALF_DAMAGE.get(),0), "• Take half damage");
        add(SpiteConstants.getAbilityDescription(AbilityInit.BOUNCE.get(),0), "• Bounce off of the ground");
        add(SpiteConstants.getAbilityDescription(AbilityInit.BOUNCE.get(),1), "• No Fall Damage");
        add(SpiteConstants.getAbilityDescription(AbilityInit.POWERFUL_DAMAGE.get(),0), "• Deal significant more damage");
        add(SpiteConstants.getAbilityDescription(AbilityInit.HEAVY_HITTER.get(),0), "• Increased Knockback to enemies");
        add(SpiteConstants.getAbilityDescription(AbilityInit.SPEED_DIGGING.get(),0), "• Dig blocks faster");
        add(SpiteConstants.getAbilityDescription(AbilityInit.INVISIBILITY.get(),0), "• Activate to become invisible");
        add(SpiteConstants.getAbilityDescription(AbilityInit.INTANGIBILITY.get(),0), "• Activate to become Walk through Blocks (caution, with great power comes great danger potential)");
        add(SpiteConstants.getAbilityDescription(AbilityInit.CLEAR_VISION.get(),0), "• Ability to See through all kinds of Fog");
        add(SpiteConstants.getAbilityDescription(AbilityInit.TELEPORTATION.get(),0), "• Teleport to a location you are looking at");
        add(SpiteConstants.getAbilityDescription(AbilityInit.CLONE.get(),0), "• Create a Clone of yourself to fight for you");
        add(SpiteConstants.getAbilityDescription(AbilityInit.PORTAL.get(),0), "• Create a Portal to travel through");
        add(SpiteConstants.getAbilityDescription(AbilityInit.PORTAL.get(),1), "• Portal will last for 10 minutes");
        add(SpiteConstants.getAbilityDescription(AbilityInit.PORTAL.get(),2), "• Only the last 2 portals made will work.");
    }

    protected void itemLang(RegistryObject<Item> entry) {
        if (!(entry.get() instanceof BlockItem) || entry.get() instanceof ItemNameBlockItem) {
            addItem(entry, checkReplace(entry));
        }
    }
    protected void powerLang(RegistryObject<Power> entry) {
            add(SpiteConstants.getDescriptionId(entry.get()), checkReplace(entry));
    }
    protected void abilityLang(RegistryObject<Ability> entry) {
            add(SpiteConstants.getDescriptionId(entry.get()), checkReplace(entry));
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
