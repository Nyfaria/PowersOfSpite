package com.nyfaria.powersofspite.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.cap.PowerHolder;
import com.nyfaria.powersofspite.command.args.PowerArgument;
import com.nyfaria.powersofspite.platform.Services;
import com.nyfaria.powersofspite.registration.ArgumentTypeHelper;
import com.nyfaria.powersofspite.registration.RegistrationProvider;
import com.nyfaria.powersofspite.registration.RegistryObject;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.BuiltInRegistries;

public class CommandInit {

    public static final RegistrationProvider<ArgumentTypeInfo<?,?>> ARG = RegistrationProvider.get(BuiltInRegistries.COMMAND_ARGUMENT_TYPE, SpiteConstants.MODID);
    public static final RegistryObject<SingletonArgumentInfo<PowerArgument>> POWER_HOLDER = ArgumentTypeHelper.INSTANCE.register(ARG,"power",PowerArgument.class, ()->SingletonArgumentInfo.contextFree(PowerArgument::power));

    public static void initCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("powers").requires(source -> source.hasPermission(2))
                .then(Commands.literal("clear")
                        .executes(ctx -> {
                            PowerHolder holder = Services.PLATFORM.getPowerHolder(ctx.getSource().getPlayerOrException());
                            holder.clearPowers();
                            return 1;
                        })
                )
                .then(Commands.literal("add")
                        .then(Commands.argument("power", PowerArgument.power())
                                .executes(ctx -> {
                                    PowerHolder holder = Services.PLATFORM.getPowerHolder(ctx.getSource().getPlayerOrException());
                                    holder.addPower(PowerArgument.getPower(ctx, "power"));
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("power", PowerArgument.power())
                                .executes(ctx -> {
                                    PowerHolder holder = Services.PLATFORM.getPowerHolder(ctx.getSource().getPlayerOrException());
                                    holder.removePower(PowerArgument.getPower(ctx, "power"));
                                    return 1;
                                })
                        )
                )
        );
    }
    public static void loadClass() {
    }
}
