package com.williambl.early_features.test;

import com.williambl.early_features.api.LivingEntityEarlyFeatureRendererRegistrationCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.CowEntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.MooshroomMushroomFeatureRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class EarlyFeaturesTest implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LivingEntityEarlyFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, context) -> {
            if (entityRenderer instanceof CowEntityRenderer cowRenderer) {
                cowRenderer.addEarlyFeature(new CowBackItemFeatureRenderer<>(cowRenderer));
            }
        });
    }

    private static class CowBackItemFeatureRenderer<T extends CowEntity> extends FeatureRenderer<T, CowEntityModel<T>> {
        public CowBackItemFeatureRenderer(FeatureRendererContext<T, CowEntityModel<T>> featureRendererContext) {
            super(featureRendererContext);
        }

        @Override
        public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T cow, float f, float g, float h, float j, float k, float l) {
            if (cow.isBaby()) {
                return;
            }
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            boolean bl = minecraftClient.hasOutline(cow) && cow.isInvisible();
            if (cow.isInvisible() && !bl) {
                return;
            }
            BlockRenderManager blockRenderManager = minecraftClient.getBlockRenderManager();
            BlockState blockState = Blocks.DIAMOND_BLOCK.getDefaultState();
            int m = LivingEntityRenderer.getOverlay(cow, 0.0f);
            BakedModel bakedModel = blockRenderManager.getModel(blockState);
            matrixStack.push();
            matrixStack.translate(0.2f, -0.35f, 0.5);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-48.0f));
            matrixStack.scale(-1.0f, -1.0f, 1.0f);
            matrixStack.translate(-0.5, -0.5, -0.5);
            this.renderMushroom(matrixStack, vertexConsumerProvider, i, blockRenderManager, blockState, m);
            matrixStack.pop();
            matrixStack.push();
            matrixStack.translate(0.2f, -0.35f, 0.5);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(42.0f));
            matrixStack.translate(0.1f, 0.0, -0.6f);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-48.0f));
            matrixStack.scale(-1.0f, -1.0f, 1.0f);
            matrixStack.translate(-0.5, -0.5, -0.5);
            this.renderMushroom(matrixStack, vertexConsumerProvider, i, blockRenderManager, blockState, m);
            matrixStack.pop();
            matrixStack.push();
            this.getContextModel().getHead().rotate(matrixStack);
            matrixStack.translate(0.0, -0.7f, -0.2f);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-78.0f));
            matrixStack.scale(-1.0f, -1.0f, 1.0f);
            matrixStack.translate(-0.5, -0.5, -0.5);
            this.renderMushroom(matrixStack, vertexConsumerProvider, i, blockRenderManager, blockState, m);
            matrixStack.pop();
        }

        private void renderMushroom(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BlockRenderManager blockRenderManager, BlockState mushroomState, int overlay) {
            blockRenderManager.renderBlockAsEntity(mushroomState, matrices, vertexConsumers, light, overlay);
        }
    }
}
