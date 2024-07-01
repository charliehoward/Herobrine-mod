package net.professorshroom.herobrine.entity.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.professorshroom.herobrine.Herobrine;

public class ModModelLayers {
    public static final EntityModelLayer HEROBRINE =
            new EntityModelLayer(Identifier.of(Herobrine.MOD_ID, "herobrine"), "main");
}
