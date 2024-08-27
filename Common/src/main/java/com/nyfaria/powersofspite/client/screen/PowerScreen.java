package com.nyfaria.powersofspite.client.screen;

import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.ability.api.Ability;
import com.nyfaria.powersofspite.cap.PowerHolder;
import com.nyfaria.powersofspite.init.PowerInit;
import com.nyfaria.powersofspite.platform.Services;
import com.nyfaria.powersofspite.power.api.Power;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import java.util.List;

public class PowerScreen extends Screen {
    private final int maxScreenHeight = 200;
    private final int maxScreenWidth = 200;
    private final int maxIconSize = 20;
    private int screenWidth = 10;
    private int screenHeight = 10;
    private int iconSize = 2;
    private boolean buttonsAdded = false;
    private int topLeftX;
    private int topLeftY;
    private Power renderingPower = null;

    public PowerScreen() {
        super(Component.literal("Power Screen"));
    }

    @Override
    protected void init() {
        super.init();
        topLeftX = width / 2 - screenWidth / 2;
        topLeftY = height / 2 - screenHeight / 2;
        screenWidth = 10;
        screenHeight = 10;
        iconSize = 2;
        buttonsAdded = false;
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        pGuiGraphics.blitNineSliced(SpiteConstants.modLoc("textures/gui/background.png"), Mth.floor(width / 2f - Mth.floor(screenWidth / 2f)), Mth.floor(height / 2f - Mth.floor(screenHeight / 2f)), screenWidth, screenHeight, 4, 16, 16, 0, 0);
        if (maxScreenHeight <= screenHeight) {
            PowerHolder holder = Services.PLATFORM.getPowerHolder(Minecraft.getInstance().player);
            if (holder != null) {
                int yO = 10;
                for (Power power : holder.getPowers()) {
                    int offset = Mth.floor((maxIconSize - iconSize) / 2f);
                    pGuiGraphics.blit(SpiteConstants.modLoc("textures/gui/power_slot.png"), topLeftX + 10 + offset, topLeftY + yO + offset, 0, 0, iconSize, iconSize, iconSize, iconSize);
//                    if(iconSize == maxIconSize) {
//                        pGuiGraphics.blit(Constants.modLoc("textures/power/" + PowerInit.REG.get().getKey(power).getPath() + ".png"), topLeftX + 12, topLeftY + yO + 2, 0, 0, 16, 16, 16, 16);
//                    }
                    yO += 22;
                }
            }
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        if (renderingPower != null) {
            int yOffset = 10;
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().scale(1.5f, 1.5f, 1.5f);
            pGuiGraphics.drawCenteredString(font, Component.translatable(SpiteConstants.getDescriptionId(renderingPower)), Mth.floor((topLeftX + 20 + 10 + ((screenWidth - 20 - 10) / 2f)) * (2 / 3f)), Mth.floor((topLeftY + yOffset) * (2 / 3f)), 0xdfa0ff);
            pGuiGraphics.pose().popPose();
            yOffset += 15;
//            pGuiGraphics.hLine(topLeftX + 40, topLeftX + screenWidth - 10, topLeftY + yOffset, 0xFFFFFFFF);
            yOffset += 5;
            for (int i = 0; i < 6; i++) {
                String id = SpiteConstants.getPowerDescription(renderingPower, i);
                Component text = Component.translatable(id);
                if (text.getString().isEmpty()) {
                    break;
                }
                if (text.getString().equals(id)) {
                    break;
                }
                List<FormattedCharSequence> lines = font.split(text, ((screenWidth - 20 - 30)));
                for (FormattedCharSequence line : lines) {
                    pGuiGraphics.drawString(font, line, topLeftX + 45, topLeftY + yOffset, 0xFFFFFF);
                    yOffset += 10;
                }
            }
//            pGuiGraphics.hLine(topLeftX + 40, topLeftX + screenWidth - 10, topLeftY + yOffset, 0xFFFFFFFF);
            yOffset += 10;
            for(Ability ability : renderingPower.getAbilities()){
                pGuiGraphics.pose().pushPose();
                pGuiGraphics.pose().scale(1.25f, 1.25f, 1.25f);
                pGuiGraphics.drawCenteredString(font, Component.translatable(SpiteConstants.getDescriptionId(ability)), Mth.floor((topLeftX + 20 + 10 + ((screenWidth - 20 - 10) / 2f)) * (4 / 5f)), Mth.floor((topLeftY + yOffset) * (4 / 5f)), 0xFFFFFF);
                pGuiGraphics.pose().popPose();
//                pGuiGraphics.hLine(topLeftX + 40, topLeftX + screenWidth - 10, topLeftY + yOffset, 0xFFFFFFFF);
                yOffset += 15;
                for (int i = 0; i < 6; i++) {
                    String id = SpiteConstants.getAbilityDescription(ability, i);
                    Component text = Component.translatable(id);
                    if (text.getString().isEmpty()) {
                        break;
                    }
                    if (text.getString().equals(id)) {
                        break;
                    }
                    pGuiGraphics.pose().pushPose();
                    pGuiGraphics.pose().scale(0.75f, 0.75f, 0.75f);
                    List<FormattedCharSequence> lines = font.split(text, ((screenWidth - 20 - 10)));
                    for (FormattedCharSequence line : lines) {
                        pGuiGraphics.drawString(font, line, Mth.floor((topLeftX + 45) * (4f/3f)), Mth.floor((topLeftY + yOffset) * (4f/3f)), 0xc1c1c1);
                        yOffset += 10;
                    }
                    pGuiGraphics.pose().popPose();
                }
//                pGuiGraphics.hLine(topLeftX + 40, topLeftX + screenWidth - 10, topLeftY + yOffset, 0xFFFFFFFF);
//                yOffset += 10;
            }
        }
    }

    @Override
    public void tick() {
        if (screenWidth < maxScreenWidth) {
            screenWidth += 10;
        }
        if (screenHeight < maxScreenHeight) {
            screenHeight += 10;
        }
        if (!buttonsAdded && iconSize == maxIconSize) {
            buttonsAdded = true;
            PowerHolder holder = Services.PLATFORM.getPowerHolder(Minecraft.getInstance().player);
            if (holder != null) {
                int yO = 12;
                for (Power power : holder.getPowers()) {
                    ImageButton button;
                    this.addRenderableWidget(button = new ImageButton(topLeftX + 12, topLeftY + yO, 16, 16, 0, 0, 0, SpiteConstants.modLoc("textures/power/" + PowerInit.REG.get().getKey(power).getPath() + ".png"), 16, 16, button3 -> {
                        renderingPower = power;
                    }));
                    button.setTooltip(Tooltip.create(Component.translatable(SpiteConstants.getDescriptionId(power))));
                    yO += 22;
                }
                renderingPower = holder.getPowers().get(0);
            }
        }
        if (maxScreenHeight == screenHeight && iconSize < maxIconSize) {
            iconSize += 3;
        }
        topLeftX = width / 2 - screenWidth / 2;
        topLeftY = height / 2 - screenHeight / 2;
    }
}
