package fuzs.armorstatues.world.entity.decoration;

import com.mojang.authlib.GameProfile;
import fuzs.armorstatues.client.gui.screens.armorstand.data.ArmorStandStyleOption;
import fuzs.armorstatues.init.ModRegistry;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PlayerStatue extends ArmorStand {
    private static final String TRUE_COLORS_KEY = "TrueColors";
    private static final String BLOCK_MATERIAL_KEY = "BlockMaterial";
    private static final String OWNER_KEY = "Owner";
    private static final String SLIM_ARMS_KEY = "SlimArms";
    private static final String MODEL_PARTS_KEY = "ModelParts";
    public static final EntityDataAccessor<Boolean> DATA_TRUE_COLORS = SynchedEntityData.defineId(PlayerStatue.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Block> DATA_BLOCK_MATERIAL = SynchedEntityData.defineId(PlayerStatue.class, ModRegistry.BLOCK_ENTITY_DATA_SERIALIZER);
    public static final EntityDataAccessor<Optional<GameProfile>> DATA_OWNER = SynchedEntityData.defineId(PlayerStatue.class, ModRegistry.GAME_PROFILE_ENTITY_DATA_SERIALIZER);
    public static final EntityDataAccessor<Boolean> DATA_SLIM_ARMS = SynchedEntityData.defineId(PlayerStatue.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Byte> DATA_PLAYER_MODE_CUSTOMISATION = SynchedEntityData.defineId(PlayerStatue.class, EntityDataSerializers.BYTE);

    public PlayerStatue(EntityType<? extends PlayerStatue> entityType, Level level) {
        super(entityType, level);
        ArmorStandStyleOption.setArmorStandData(this, true, ArmorStand.CLIENT_FLAG_SHOW_ARMS);
        ArmorStandStyleOption.setArmorStandData(this, true, ArmorStand.CLIENT_FLAG_NO_BASEPLATE);
    }

    public PlayerStatue(Level level, Block blockMaterial) {
        this(ModRegistry.PLAYER_STATUE_ENTITY_TYPE.get(), level);
        this.entityData.set(DATA_BLOCK_MATERIAL, blockMaterial);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TRUE_COLORS, false);
        this.entityData.define(DATA_BLOCK_MATERIAL, Blocks.AIR);
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
        compound.putBoolean(TRUE_COLORS_KEY, this.showsTrueColors());
        compound.putString(BLOCK_MATERIAL_KEY, Registry.BLOCK.getKey(this.getBlockMaterial()).toString());
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
        this.setTrueColors(compound.getBoolean(TRUE_COLORS_KEY));
        this.setBlockMaterial(Registry.BLOCK.get(new ResourceLocation(compound.getString(BLOCK_MATERIAL_KEY))));
        this.setSlimArms(compound.getBoolean(SLIM_ARMS_KEY));
        this.entityData.set(DATA_PLAYER_MODE_CUSTOMISATION, compound.getByte(MODEL_PARTS_KEY));
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

    public boolean showsTrueColors() {
        return this.entityData.get(DATA_TRUE_COLORS);
    }

    public void setTrueColors(boolean trueColors) {
        this.entityData.set(DATA_TRUE_COLORS, trueColors);
    }

    public Block getBlockMaterial() {
        return this.entityData.get(DATA_BLOCK_MATERIAL);
    }

    private void setBlockMaterial(Block blockMaterial) {
        this.entityData.set(DATA_BLOCK_MATERIAL, blockMaterial);
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
}
