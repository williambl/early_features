package com.williambl.early_features.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

/**
 * Called when early {@link net.minecraft.client.render.entity.feature.FeatureRenderer feature renderers} for a {@link LivingEntityRenderer living entity renderer} are registered.
 *
 * <p>Early feature renderers are feature renderers which render <em>before</em> the living entity's model is rendered.</p>
 *
 * @see net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback
 */
@FunctionalInterface
@Environment(EnvType.CLIENT)
public interface LivingEntityEarlyFeatureRendererRegistrationCallback {
	Event<LivingEntityEarlyFeatureRendererRegistrationCallback> EVENT = EventFactory.createArrayBacked(LivingEntityEarlyFeatureRendererRegistrationCallback.class, callbacks -> (entityType, entityRenderer, context) -> {
		for (var callback : callbacks) {
			callback.registerRenderers(entityType, entityRenderer, context);
		}
	});

	/**
	 * Called when early feature renderers may be registered.
	 *
	 * @param entityType     the entity type of the renderer
	 * @param entityRenderer the entity renderer
	 * @param context		 the entity renderer factory context
	 */
	void registerRenderers(EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?> entityRenderer, EntityRendererFactory.Context context);
}
