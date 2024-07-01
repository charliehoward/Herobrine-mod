package net.professorshroom.herobrine.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
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
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 100f));
        this.goalSelector.add(1, new HerobrineEntity.FleeGoal(this, PlayerEntity.class, 25.0F, 5, 5));
    }


    public static DefaultAttributeContainer.Builder createHerobrineAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 5)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1);

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

    static class FleeGoal<T extends LivingEntity> extends FleeEntityGoal<T> {
        private final HerobrineEntity herobrine;

        public FleeGoal(HerobrineEntity herobrine, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
            super(herobrine, fleeFromType, distance, slowSpeed, fastSpeed);
            this.herobrine = herobrine;
        }
    }

    @Override
    public boolean canWalkOnFluid(FluidState state) {
        return super.canWalkOnFluid(state);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return super.canImmediatelyDespawn(distanceSquared);
    }

    @Override
    public void setDespawnCounter(int despawnCounter) {
        super.setDespawnCounter(1);
    }

}