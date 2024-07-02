package net.professorshroom.herobrine.entity.custom;

import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class HerobrineEntity extends AnimalEntity {
    public static final double CROUCHING_SPEED = 0.6;
    public static final double NORMAL_SPEED = 1;
    public static final double SPRINTING_SPEED = 1.5;
    private static final TrackedData<Boolean> TRUSTING = DataTracker.registerData(HerobrineEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    @Nullable
    private HerobrineEntity.FleeGoal<PlayerEntity> fleeGoal;
    @Nullable
    private HerobrineEntity.HerobrineTemptGoal temptGoal;

    public HerobrineEntity(EntityType<? extends HerobrineEntity> entityType, World world) {
        super(entityType, world);
        this.updateFleeing();
    }

    boolean isTrusting() {
        return this.dataTracker.get(TRUSTING);
    }

    private void setTrusting(boolean trusting) {
        this.dataTracker.set(TRUSTING, trusting);
        this.updateFleeing();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Trusting", this.isTrusting());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setTrusting(nbt.getBoolean("Trusting"));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TRUSTING, false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1, 1.0000001E-5F));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 200.0F));
    }

    @Override
    public void mobTick() {
        if (this.getMoveControl().isMoving()) {
            double d = this.getMoveControl().getSpeed();
            if (d == 0.6) {
                this.setPose(EntityPose.CROUCHING);
                this.setSprinting(false);
            } else if (d == 1.33) {
                this.setPose(EntityPose.STANDING);
                this.setSprinting(true);
            } else {
                this.setPose(EntityPose.STANDING);
                this.setSprinting(false);
            }
        } else {
            this.setPose(EntityPose.STANDING);
            this.setSprinting(false);
        }
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isTrusting() && this.age > 2400;
    }

    public static DefaultAttributeContainer.Builder createHerobrineAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }

    private void showEmoteParticle(boolean positive) {
        ParticleEffect particleEffect = ParticleTypes.HEART;
        if (!positive) {
            particleEffect = ParticleTypes.SMOKE;
        }

        for (int i = 0; i < 7; i++) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.getWorld().addParticle(particleEffect, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
        }
    }

    protected void updateFleeing() {
        if (this.fleeGoal == null) {
            this.fleeGoal = new HerobrineEntity.FleeGoal<>(this, PlayerEntity.class, 60.0F, 1.5, 2);
        }

        this.goalSelector.remove(this.fleeGoal);
        if (!this.isTrusting()) {
            this.goalSelector.add(1, this.fleeGoal);
        }
    }
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    public static boolean canSpawn(EntityType<HerobrineEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return random.nextInt(3) != 0;
    }

    @Override
    public boolean canSpawn(WorldView world) {
        if (world.doesNotIntersectEntities(this) && !world.containsFluid(this.getBoundingBox())) {
            BlockPos blockPos = this.getBlockPos();
            if (blockPos.getY() < world.getSeaLevel()) {
                return false;
            }

            BlockState blockState = world.getBlockState(blockPos.down());
            if (blockState.isOf(Blocks.GRASS_BLOCK) || blockState.isIn(BlockTags.LEAVES)) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        if (entityData == null) {
            entityData = new PassiveEntity.PassiveData(1.0F);
        }

        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, (double)(0.5F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    @Override
    public boolean bypassesSteppingEffects() {
        return this.isInSneakingPose() || super.bypassesSteppingEffects();
    }

    static class FleeGoal<T extends LivingEntity> extends FleeEntityGoal<T> {
        private final HerobrineEntity herobrine;

        public FleeGoal(HerobrineEntity herobrine, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
            super(herobrine, fleeFromType, distance, slowSpeed, fastSpeed, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test);
            this.herobrine = herobrine;
        }

        @Override
        public boolean canStart() {
            return !this.herobrine.isTrusting() && super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            return !this.herobrine.isTrusting() && super.shouldContinue();
        }
    }

    static class HerobrineTemptGoal extends TemptGoal {
        private final HerobrineEntity herobrine;

        public HerobrineTemptGoal(HerobrineEntity herobrine, double speed, Predicate<ItemStack> foodPredicate, boolean canBeScared) {
            super(herobrine, speed, foodPredicate, canBeScared);
            this.herobrine = herobrine;
        }

        @Override
        protected boolean canBeScared() {
            return super.canBeScared() && !this.herobrine.isTrusting();
        }
    }

    @Override
    public boolean canWalkOnFluid(FluidState state) {
        return super.canWalkOnFluid(state);
    }
}
