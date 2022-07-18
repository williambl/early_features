package com.williambl.early_features.mixin;

import com.williambl.early_features.api.EarlyFeatureAdder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements EarlyFeatureAdder {
    protected final List<FeatureRenderer<T, M>> earlyFeatures = new ArrayList<>();

    protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addEarlyFeature(FeatureRenderer<?, ?> feature) {
        return this.earlyFeatures.add((FeatureRenderer<T, M>) feature);
    }

    @Inject(
            method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getRenderLayer(Lnet/minecraft/entity/LivingEntity;ZZZ)Lnet/minecraft/client/render/RenderLayer;"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    void renderEarlyFeatures(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci, float h, float j, float k, float m, float l, float n, float o, MinecraftClient minecraftClient, boolean bl, boolean bl2, boolean bl3) {
        if (!livingEntity.isSpectator()) {
            for (FeatureRenderer<T, M> earlyFeature : this.earlyFeatures) {
                earlyFeature.render(matrixStack, vertexConsumerProvider, i, livingEntity, o, n, g, l, k, m);
            }
        }
    }
}
