package com.williambl.early_features.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;

/**
 * Implemented on {@link net.minecraft.client.render.entity.LivingEntityRenderer} with interface injection.
 *
 * @param <T> a LivingEntity class
 * @param <M> the LivingEntity's model class
 */
@ApiStatus.NonExtendable
@Environment(EnvType.CLIENT)
public interface EarlyFeatureAdder {
    /**
     * Adds an early-rendering feature.
     *
     * @param feature the feature to add
     * @return whether the feature was added
     */
    default boolean addEarlyFeature(FeatureRenderer<?, ?> feature) {
        return false;
    }
}
