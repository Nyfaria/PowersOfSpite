package com.nyfaria.powersofspite.command.args;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.init.PowerInit;
import com.nyfaria.powersofspite.power.api.Power;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;

public class PowerArgument extends RegistryResourceArgument<Power> {
    public static final DynamicCommandExceptionType ERROR_UNKNOWN_ABILITY = new DynamicCommandExceptionType(arg1 -> Component.translatable("argument." + SpiteConstants.MODID + ".power.id.invalid", arg1));

    private PowerArgument() {
    }

    @Override
    protected Registry<Power> getRegistry() {
        return PowerInit.REG.get();
    }

    @Override
    protected DynamicCommandExceptionType getUnknownExceptionType() {
        return ERROR_UNKNOWN_ABILITY;
    }

    public static PowerArgument power() {
        return new PowerArgument();
    }

    public static Power getPower(final CommandContext<?> context, final String name) {
        return context.getArgument(name, Power.class);
    }


}
