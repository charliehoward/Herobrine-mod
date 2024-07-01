package net.professorshroom.herobrine.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.professorshroom.herobrine.Herobrine;
import net.professorshroom.herobrine.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

public class HerobrineEntity extends AnimalEntity {
    public HerobrineEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 25f));
    }


    public static DefaultAttributeContainer.Builder createHerobrineAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 5);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.HEROBRINE.create(world);
    }

    public static void registerModEntities() {

        Herobrine.LOGGER.info("Registering Entities for " + Herobrine.MOD_ID);

    }

}
