package net.professorshroom.herobrine;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.professorshroom.herobrine.entity.ModEntities;
import net.professorshroom.herobrine.entity.client.HerobrineModel;
import net.professorshroom.herobrine.entity.client.HerobrineRenderer;
import net.professorshroom.herobrine.entity.client.ModModelLayers;

import static net.professorshroom.herobrine.entity.ModEntities.HEROBRINE;

public class HerobrineClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.HEROBRINE, HerobrineRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.HEROBRINE, HerobrineModel::getTexturedModelData);

    }
}