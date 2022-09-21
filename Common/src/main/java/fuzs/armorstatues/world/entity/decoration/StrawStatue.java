package fuzs.armorstatues.world.entity.decoration;

import com.mojang.authlib.GameProfile;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.mixin.accessor.ArmorStandAccessor;
import fuzs.armorstatues.world.inventory.ArmorStandScreenType;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class StrawStatue extends ArmorStand implements ArmorStandDataProvider {
    private static final String OWNER_KEY = "Owner";
    private static final String SLIM_ARMS_KEY = "SlimArms";
    private static final String MODEL_PARTS_KEY = "ModelParts";
    public static final EntityDataAccessor<Optional<GameProfile>> DATA_OWNER = SynchedEntityData.defineId(StrawStatue.class, ModRegistry.GAME_PROFILE_ENTITY_DATA_SERIALIZER);
    public static final EntityDataAccessor<Boolean> DATA_SLIM_ARMS = SynchedEntityData.defineId(StrawStatue.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Byte> DATA_PLAYER_MODE_CUSTOMISATION = SynchedEntityData.defineId(StrawStatue.class, EntityDataSerializers.BYTE);

    public StrawStatue(EntityType<? extends StrawStatue> entityType, Level level) {
        super(entityType, level);
    }

    public StrawStatue(Level level, double x, double y, double z) {
        this(ModRegistry.STRAW_STATUE_ENTITY_TYPE.get(), level);
        this.setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_OWNER, Optional.empty());
        this.entityData.define(DATA_SLIM_ARMS, false);
        this.entityData.define(DATA_PLAYER_MODE_CUSTOMISATION, getAllModelParts());
    }

    private static byte getAllModelParts() {
        byte value = 0;
        for (PlayerModelPart modelPart : PlayerModelPart.values()) {
            value |= modelPart.getMask();
        }
        return value;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean(SLIM_ARMS_KEY, this.slimArms());
        compound.putByte(MODEL_PARTS_KEY, this.entityData.get(DATA_PLAYER_MODE_CUSTOMISATION));
        this.entityData.get(DATA_OWNER).ifPresent(owner -> {
            CompoundTag compoundtag = new CompoundTag();
            NbtUtils.writeGameProfile(compoundtag, owner);
            compoundtag.put(OWNER_KEY, compoundtag);
        });
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains(SLIM_ARMS_KEY, Tag.TAG_BYTE)) {
            this.setSlimArms(compound.getBoolean(SLIM_ARMS_KEY));
        }
        if (compound.contains(MODEL_PARTS_KEY, Tag.TAG_BYTE)) {
            this.entityData.set(DATA_PLAYER_MODE_CUSTOMISATION, compound.getByte(MODEL_PARTS_KEY));
        }
        if (compound.contains(OWNER_KEY, Tag.TAG_COMPOUND)) {
            this.setOwner(NbtUtils.readGameProfile(compound.getCompound(OWNER_KEY)));
        }
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        if (name == null) {
            this.setOwner(null);
        } else {
            GameProfile gameprofile = new GameProfile(null, name.getString());
            SkullBlockEntity.updateGameprofile(gameprofile, this::setOwner);
        }
    }

    @Nullable
    public GameProfile getOwner() {
        return this.entityData.get(DATA_OWNER).orElse(null);
    }

    private void setOwner(@Nullable GameProfile value) {
        this.entityData.set(DATA_OWNER, Optional.ofNullable(value));
    }

    public boolean slimArms() {
        return this.entityData.get(DATA_SLIM_ARMS);
    }

    public void setSlimArms(boolean slimArms) {
        this.entityData.set(DATA_SLIM_ARMS, slimArms);
    }

    public boolean isModelPartShown(PlayerModelPart part) {
        return (this.getEntityData().get(DATA_PLAYER_MODE_CUSTOMISATION) & part.getMask()) == part.getMask();
    }

    public void setModelPart(PlayerModelPart modelPart, boolean enable) {
        this.entityData.set(DATA_PLAYER_MODE_CUSTOMISATION, this.setBit(this.entityData.get(DATA_PLAYER_MODE_CUSTOMISATION), modelPart.getMask(), enable));
    }

    private byte setBit(byte oldBit, int offset, boolean value) {
        if (value) {
            oldBit = (byte) (oldBit | offset);
        } else {
            oldBit = (byte) (oldBit & ~offset);
        }
        return oldBit;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!this.level.isClientSide && !this.isRemoved()) {
            if (DamageSource.OUT_OF_WORLD.equals(source)) {
                this.kill();
                return false;
            } else if (!this.isInvulnerableTo(source) && !this.isInvisible() && !this.isMarker()) {
                if (source.isExplosion()) {
                    ((ArmorStandAccessor) this).callBrokenByAnything(source);
                    this.kill();
                    return false;
                } else if (DamageSource.IN_FIRE.equals(source)) {
                    if (this.isOnFire()) {
                        ((ArmorStandAccessor) this).callCauseDamage(source, 0.15F);
                    } else {
                        this.setSecondsOnFire(5);
                    }

                    return false;
                } else if (DamageSource.ON_FIRE.equals(source) && this.getHealth() > 0.5F) {
                    ((ArmorStandAccessor) this).callCauseDamage(source, 4.0F);
                    return false;
                } else {
                    boolean bl = source.getDirectEntity() instanceof AbstractArrow;
                    boolean bl2 = bl && ((AbstractArrow)source.getDirectEntity()).getPierceLevel() > 0;
                    boolean bl3 = "player".equals(source.getMsgId());
                    if (!bl3 && !bl) {
                        return false;
                    } else if (source.getEntity() instanceof Player && !((Player)source.getEntity()).getAbilities().mayBuild) {
                        return false;
                    } else if (source.isCreativePlayer()) {
                        this.playBrokenSound();
                        this.showBreakingParticles();
                        this.kill();
                        return bl2;
                    } else {
                        long l = this.level.getGameTime();
                        if (l - this.lastHit > 5L && !bl) {
                            this.level.broadcastEntityEvent(this, (byte)32);
                            this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
                            this.lastHit = l;
                        } else {
                            this.brokenByPlayer(source);
                            this.showBreakingParticles();
                            this.kill();
                        }

                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void brokenByPlayer(DamageSource source) {
        Block.popResource(this.level, this.blockPosition(), new ItemStack(ModRegistry.STRAW_STATUE_ITEM.get()));
        ((ArmorStandAccessor) this).callBrokenByAnything(source);
    }

    private void playBrokenSound() {
        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GRASS_BREAK, this.getSoundSource(), 1.0F, 1.0F);
    }

    private void showBreakingParticles() {
        if (this.level instanceof ServerLevel) {
            ((ServerLevel)this.level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.HAY_BLOCK.defaultBlockState()), this.getX(), this.getY(0.6666666666666666), this.getZ(), 10, (double)(this.getBbWidth() / 4.0F), (double)(this.getBbHeight() / 4.0F), (double)(this.getBbWidth() / 4.0F), 0.05);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 32) {
            if (this.level.isClientSide) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GRASS_HIT, this.getSoundSource(), 0.3F, 1.0F, false);
                this.lastHit = this.level.getGameTime();
            }
        } else {
            super.handleEntityEvent(id);
        }

    }

    @Override
    public LivingEntity.Fallsounds getFallSounds() {
        return new LivingEntity.Fallsounds(SoundEvents.GRASS_FALL, SoundEvents.GRASS_FALL);
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.GRASS_HIT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.GRASS_BREAK;
    }

    @Override
    @Nullable
    public ItemStack getPickResult() {
        return new ItemStack(ModRegistry.STRAW_STATUE_ITEM.get());
    }

    @Override
    public ArmorStandScreenType[] getScreenTypes() {
        return new ArmorStandScreenType[]{ArmorStandScreenType.POSES, ArmorStandScreenType.ROTATIONS, ArmorStandScreenType.STRAW_STATUE_STYLE, ArmorStandScreenType.MODEL_PARTS, ArmorStandScreenType.POSITION, ArmorStandScreenType.EQUIPMENT};
    }
}
