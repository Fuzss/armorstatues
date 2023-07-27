package fuzs.armorstatues.client.gui.screens.armorstand;

import com.google.common.collect.Lists;
import fuzs.armorstatues.api.client.gui.components.TickButton;
import fuzs.armorstatues.api.client.gui.screens.armorstand.AbstractArmorStandPositionScreen;
import fuzs.armorstatues.api.network.client.data.DataSyncHandler;
import fuzs.armorstatues.api.world.inventory.ArmorStandHolder;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandAlignment;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOptions;
import fuzs.armorstatues.init.ModRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ArmorStandAlignmentsScreen extends AbstractArmorStandPositionScreen {

    public ArmorStandAlignmentsScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    protected List<PositionScreenWidget> buildWidgets(ArmorStand armorStand) {
        List<PositionScreenWidget> widgets = Lists.newArrayList(new PositionAlignWidget());
        for (ArmorStandAlignment alignment : ArmorStandAlignment.values()) {
            widgets.add(new AlignmentWidget(alignment));
        }
        return widgets;
    }

    @Override
    protected void init() {
        super.init();
        this.addVanillaTweaksCreditsButton();
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return ModRegistry.ALIGNMENTS_SCREEN_TYPE;
    }

    private class AlignmentWidget extends AbstractPositionScreenWidget {
        private final ArmorStandAlignment alignment;

        public AlignmentWidget(ArmorStandAlignment alignment) {
            super(Component.empty());
            this.alignment = alignment;
        }

        @Override
        protected boolean shouldTick() {
            return true;
        }

        @Override
        public void init(int posX, int posY) {
            super.init(posX, posY);
            this.children.add(ArmorStandAlignmentsScreen.this.addRenderableWidget(new TickButton(posX, posY + 1, 194, 20, Component.translatable(this.alignment.getTranslationKey()), Component.translatable(ALIGNED_TRANSLATION_KEY), button -> {
                ArmorStand armorStand = ArmorStandAlignmentsScreen.this.holder.getArmorStand();
                DataSyncHandler dataSyncHandler = ArmorStandAlignmentsScreen.this.dataSyncHandler;
                if (!armorStand.isInvisible()) {
                    dataSyncHandler.sendStyleOption(ArmorStandStyleOptions.INVISIBLE, true, false);
                }
                if (!armorStand.isNoGravity()) {
                    dataSyncHandler.sendStyleOption(ArmorStandStyleOptions.NO_GRAVITY, true, false);
                }
                dataSyncHandler.sendPose(this.alignment.getPose(), false);
                Vec3 alignmentOffset = this.alignment.getAlignmentOffset(armorStand.isSmall());
                Vec3 newPosition = getLocalPosition(armorStand, alignmentOffset);
                dataSyncHandler.sendPosition(newPosition.x(), newPosition.y(), newPosition.z(), false);
                dataSyncHandler.finalizeCurrentOperation();
            }, (button, poseStack, mouseX, mouseY) -> {
                Component component = Component.translatable(this.alignment.getDescriptionsKey());
                List<FormattedCharSequence> lines = ArmorStandAlignmentsScreen.this.font.split(component, 175);
                ArmorStandAlignmentsScreen.this.renderTooltip(poseStack, lines, mouseX, mouseY);
            })));
        }

        /**
         * Copied from {@link net.minecraft.commands.arguments.coordinates.LocalCoordinates#getPosition(CommandSourceStack)}.
         */
        private static Vec3 getLocalPosition(Entity entity, Vec3 offset) {
            Vec2 vec2 = entity.getRotationVector();
            Vec3 vec3 = entity.position();
            float f = Mth.cos((vec2.y + 90.0F) * 0.017453292F);
            float g = Mth.sin((vec2.y + 90.0F) * 0.017453292F);
            float h = Mth.cos(-vec2.x * 0.017453292F);
            float i = Mth.sin(-vec2.x * 0.017453292F);
            float j = Mth.cos((-vec2.x + 90.0F) * 0.017453292F);
            float k = Mth.sin((-vec2.x + 90.0F) * 0.017453292F);
            Vec3 vec32 = new Vec3(f * h, i, g * h);
            Vec3 vec33 = new Vec3(f * j, k, g * j);
            Vec3 vec34 = vec32.cross(vec33).scale(-1.0);
            double d = vec32.x * offset.z() + vec33.x * offset.y() + vec34.x * offset.x();
            double e = vec32.y * offset.z() + vec33.y * offset.y() + vec34.y * offset.x();
            double l = vec32.z * offset.z() + vec33.z * offset.y() + vec34.z * offset.x();
            return new Vec3(vec3.x + d, vec3.y + e, vec3.z + l);
        }
    }
}
