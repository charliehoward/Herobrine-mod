package net.professorshroom.herobrine.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.professorshroom.herobrine.Herobrine;
import net.professorshroom.herobrine.entity.custom.HerobrineEntity;

public class HerobrineRenderer extends MobEntityRenderer<HerobrineEntity, HerobrineModel<HerobrineEntity>> {
    private static final Identifier TEXTURE = Identifier.of(Herobrine.MOD_ID, "textures/entity/herobrine.png");

    public HerobrineRenderer(EntityRendererFactory.Context context) {
        super(context, new HerobrineModel<>(context.getPart(ModModelLayers.HEROBRINE)), 0.6f);
    }

    @Override
    public Identifier getTexture(HerobrineEntity entity) {
        return TEXTURE;
    }
}
