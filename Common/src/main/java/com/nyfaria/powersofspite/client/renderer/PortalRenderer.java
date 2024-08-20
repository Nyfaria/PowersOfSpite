package com.nyfaria.powersofspite.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.nyfaria.powersofspite.SpiteConstants;
import com.nyfaria.powersofspite.client.model.PortalModel;
import com.nyfaria.powersofspite.entity.PortalEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

public class PortalRenderer extends EntityRenderer<PortalEntity> {
    PortalModel<PortalEntity> model;
    public PortalRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        model = new PortalModel<>(pContext.bakeLayer(PortalModel.LAYER_LOCATION));
    }

    @Override
    public void render(PortalEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.translate(0, 0.5, 0);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().player.getViewYRot(pPartialTick)));
        model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.5f);
        pPoseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(PortalEntity pEntity) {
        return SpiteConstants.modLoc("textures/entity/portal.png");
    }
}
