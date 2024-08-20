package com.nyfaria.powersofspite.init;

import com.mojang.brigadier.CommandDispatcher;
import com.nyfaria.powersofspite.cap.PowerHolder;
import com.nyfaria.powersofspite.platform.Services;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandInit {

    public static void initCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("powers")
                        .then(Commands.literal("clear")
                                .executes(ctx -> {
                                    PowerHolder holder = Services.PLATFORM.getPowerHolder(ctx.getSource().getPlayerOrException());
                                    holder.clearPowers();
                                    return 1;
                                })
                        )
        );
    }
}
