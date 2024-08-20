package com.nyfaria.powersofspite.client.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class DoubleImageButton extends Button {
    protected final ImageInfo foreGround;
    protected final ImageInfo background;


    public DoubleImageButton(int pX, int pY, ImageInfo foreGround,ImageInfo background, Button.OnPress pOnPress, Component pMessage) {
        super(pX, pY, background.width, background.height, pMessage, pOnPress, DEFAULT_NARRATION);
        this.foreGround = foreGround;
        this.background = background;
    }

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderTexture(pGuiGraphics, this.background.location, this.getX(), this.getY(), 0, 0, 0, this.width, this.height, background.width, background.height);
        this.renderTexture(pGuiGraphics, this.foreGround.location, this.getX() + this.background.width - this.foreGround.width, this.getY() + this.background.height - this.foreGround.width, 0, 0, 0, this.width, this.height, foreGround.width, foreGround.height);
    }
    record ImageInfo(ResourceLocation location, int x, int y, int width, int height) {}
}