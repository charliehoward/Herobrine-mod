package net.professorshroom.herobrine.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.professorshroom.herobrine.Herobrine;
import net.professorshroom.herobrine.entity.custom.HerobrineEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<HerobrineEntity> HEROBRINE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Herobrine.MOD_ID, "herobrine"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HerobrineEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)).build());
}
