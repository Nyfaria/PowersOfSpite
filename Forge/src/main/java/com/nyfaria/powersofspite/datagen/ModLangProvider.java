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
//•
        add(SpiteConstants.getPowerDescription(PowerInit.TRITON.get(), 0),"Obtain various nautical abilities equipping you for any epic underwater battle or excursion!");
        add(SpiteConstants.getPowerDescription(PowerInit.SUPER_STRENGTH.get(), 0),"Take 'one powerful punch' to a whole new level, obtaining never-seen-before amounts of power from your fists!");
        add(SpiteConstants.getPowerDescription(PowerInit.SUPER_SPEED.get(), 0),"Get to anywhere within seconds by running faster than you can say 'Powers of Spite'!");
        add(SpiteConstants.getPowerDescription(PowerInit.GHOST.get(), 0),"Allow no objects to stop you as you harness the power of phasing through objects on demand!");
        add(SpiteConstants.getPowerDescription(PowerInit.GHOST.get(), 1),"Remember: With great power comes great responsibility! You can easily get yourself in trouble if you make the wrong moves.");
        add(SpiteConstants.getPowerDescription(PowerInit.INVISIBILITY.get(), 0),"Do whatever you want and never be seen with the outside world as you become totally see-through!");
        add(SpiteConstants.getPowerDescription(PowerInit.KEEN_SIGHT.get(), 0),"See great distances to spot enemies or storms, through the black of the sky and the foggiest clouds!");
        add(SpiteConstants.getPowerDescription(PowerInit.TELEPORTATION.get(), 0),"Even better than getting somewhere faster than you can say 'Powers of Spite' is getting there before you say 'spite'!");
        add(SpiteConstants.getPowerDescription(PowerInit.ELASTICITY.get(), 0),"Harness the abilities of the rubber ball - it may not seem impressive until you can jump off mountains with no problem!");
        add(SpiteConstants.getPowerDescription(PowerInit.ELASTICITY.get(), 1),"Allow yourself to take no fall damage and take only half as much damage as normal.");
        add(SpiteConstants.getPowerDescription(PowerInit.DISPLACER.get(), 0),"Double your army by summoning minions that fight for you and your pets on demand!");
        add(SpiteConstants.getPowerDescription(PowerInit.SPATIAL_TRAVEL.get(), 0),"Warp the basic principles of time itself by creating portals you can teleport through!");

        add(SpiteConstants.getAbilityDescription(AbilityInit.NONE.get(),0), "Please select a Power to the Left!");

        add(SpiteConstants.getAbilityDescription(AbilityInit.FLIGHT.get(),0), "Double jump to start flying");
        add(SpiteConstants.getAbilityDescription(AbilityInit.FLIGHT.get(),1), "Hold down sprint while flying to move fast in the direction you are looking");
        add(SpiteConstants.getAbilityDescription(AbilityInit.WATER_BREATHING.get(),0), "Breathe underwater");
        add(SpiteConstants.getAbilityDescription(AbilityInit.SWIM_SPEED.get(),0), "Swim faster in water");
        add(SpiteConstants.getAbilityDescription(AbilityInit.SUPER_SPEED.get(),0), "Increase base running speed");
        add(SpiteConstants.getAbilityDescription(AbilityInit.HALF_DAMAGE.get(),0), "Take half the damage of a normal hit");
        add(SpiteConstants.getAbilityDescription(AbilityInit.BOUNCE.get(),0), "Bounce off of the ground");
        add(SpiteConstants.getAbilityDescription(AbilityInit.BOUNCE.get(),1), "Remove fall damage");
        add(SpiteConstants.getAbilityDescription(AbilityInit.POWERFUL_DAMAGE.get(),0), "Deal more damage");
        add(SpiteConstants.getAbilityDescription(AbilityInit.HEAVY_HITTER.get(),0), "Increased knockback when hitting something");
        add(SpiteConstants.getAbilityDescription(AbilityInit.SPEED_DIGGING.get(),0), "Dig blocks faster");
        add(SpiteConstants.getAbilityDescription(AbilityInit.INVISIBILITY.get(),0), "Activate to become invisible");
        add(SpiteConstants.getAbilityDescription(AbilityInit.INTANGIBILITY.get(),0), "Activate to walk through blocks");
        add(SpiteConstants.getAbilityDescription(AbilityInit.CLEAR_VISION.get(),0), "See better at night or through fog");
        add(SpiteConstants.getAbilityDescription(AbilityInit.TELEPORTATION.get(),0), "Teleport to a location you are looking at");
        add(SpiteConstants.getAbilityDescription(AbilityInit.CLONE.get(),0), "Create clones of yourself that fight for you");
        add(SpiteConstants.getAbilityDescription(AbilityInit.PORTAL.get(),0), "Create two portal to travel through");
        add(SpiteConstants.getAbilityDescription(AbilityInit.PORTAL.get(),1), "Traveling through portals has a cooldown of 6 seconds");
        add(SpiteConstants.getAbilityDescription(AbilityInit.PORTAL.get(),2), "Portals will disappear in 10 minutes");
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
