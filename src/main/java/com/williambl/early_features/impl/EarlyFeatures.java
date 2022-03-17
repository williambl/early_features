package com.williambl.early_features.impl;

import com.williambl.early_features.api.LivingEntityEarlyFeatureRendererRegistrationCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;

public class EarlyFeatures implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            LivingEntityEarlyFeatureRendererRegistrationCallback.EVENT.invoker().registerRenderers(entityType, entityRenderer, context);
        });
    }
}
