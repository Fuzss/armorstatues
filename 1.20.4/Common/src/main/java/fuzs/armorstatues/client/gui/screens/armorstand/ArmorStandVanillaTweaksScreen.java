package fuzs.armorstatues.client.gui.screens.armorstand;

import com.google.common.collect.Lists;
import fuzs.armorstatues.ArmorStatues;
import fuzs.armorstatues.init.ModRegistry;
import fuzs.armorstatues.network.client.data.VanillaTweaksDataSyncHandler;
import fuzs.statuemenus.api.v1.client.gui.screens.ArmorStandButtonsScreen;
import fuzs.statuemenus.api.v1.network.client.data.DataSyncHandler;
import fuzs.statuemenus.api.v1.world.inventory.ArmorStandHolder;
import fuzs.statuemenus.api.v1.world.inventory.data.ArmorStandScreenType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class ArmorStandVanillaTweaksScreen extends ArmorStandButtonsScreen {
    public static final String TRIGGER_SENT_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.triggerSent";
    public static final String CHECK_TARGET_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.checkTarget";
    public static final String CHECK_TARGET_DESCRIPTION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.checkTarget.description";
    public static final String LOCK_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.lock";
    public static final String LOCK_DESCRIPTION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.lock.description";
    public static final String UNLOCK_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.unlock";
    public static final String UNLOCK_DESCRIPTION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.unlock.description";
    public static final String TOOL_RACK_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.toolRack";
    public static final String TOOL_RACK_DESCRIPTION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.toolRack.description";
    public static final String SWAP_MAINHAND_AND_OFFHAND_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.swapMainhandAndOffhand";
    public static final String SWAP_MAINHAND_AND_OFFHAND_DESCRIPTION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.swapMainhandAndOffhand.description";
    public static final String SWAP_MAINHAND_AND_HEAD_TRANSLATION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.swapMainhandAndHead";
    public static final String SWAP_MAINHAND_AND_HEAD_DESCRIPTION_KEY = ArmorStatues.MOD_ID + ".screen.vanillaTweaks.swapMainhandAndHead.description";

    public ArmorStandVanillaTweaksScreen(ArmorStandHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    public VanillaTweaksDataSyncHandler getDataSyncHandler() {
        return (VanillaTweaksDataSyncHandler) super.getDataSyncHandler();
    }

    @Override
    protected List<ArmorStandWidget> buildWidgets(ArmorStand armorStand) {
        List<ArmorStandWidget> widgets = Lists.newArrayList();
        widgets.add(new SingleButtonWidget(Component.translatable(CHECK_TARGET_TRANSLATION_KEY), Component.translatable(CHECK_TARGET_DESCRIPTION_KEY), Component.translatable(TRIGGER_SENT_TRANSLATION_KEY), button -> {
            this.getDataSyncHandler().sendSingleTriggerValue(VanillaTweaksDataSyncHandler.CHECK_TARGET);
            this.onClose();
        }));
        widgets.add(new DoubleButtonWidget(Component.translatable(LOCK_TRANSLATION_KEY), Component.translatable(UNLOCK_TRANSLATION_KEY), Component.translatable(LOCK_DESCRIPTION_KEY), Component.translatable(UNLOCK_DESCRIPTION_KEY), Component.translatable(TRIGGER_SENT_TRANSLATION_KEY), button -> {
            this.getDataSyncHandler().sendSingleTriggerValue(VanillaTweaksDataSyncHandler.UTILITIES_LOCK);
        }, button -> {
            this.getDataSyncHandler().sendSingleTriggerValue(VanillaTweaksDataSyncHandler.UTILITIES_UNLOCK);
        }));
        widgets.add(new SingleButtonWidget(Component.translatable(TOOL_RACK_TRANSLATION_KEY), Component.translatable(TOOL_RACK_DESCRIPTION_KEY), Component.translatable(TRIGGER_SENT_TRANSLATION_KEY), button -> {
            this.getDataSyncHandler().sendSingleTriggerValue(VanillaTweaksDataSyncHandler.AUTO_ALIGNMENT_TOOL_RACK);
        }));
        widgets.add(new SingleButtonWidget(Component.translatable(SWAP_MAINHAND_AND_OFFHAND_TRANSLATION_KEY), Component.translatable(SWAP_MAINHAND_AND_OFFHAND_DESCRIPTION_KEY), Component.translatable(TRIGGER_SENT_TRANSLATION_KEY), button -> {
            this.getDataSyncHandler().sendSingleTriggerValue(VanillaTweaksDataSyncHandler.SWAP_SLOTS_MAINHAND_AND_OFFHAND);
        }));
        widgets.add(new SingleButtonWidget(Component.translatable(SWAP_MAINHAND_AND_HEAD_TRANSLATION_KEY), Component.translatable(SWAP_MAINHAND_AND_HEAD_DESCRIPTION_KEY), Component.translatable(TRIGGER_SENT_TRANSLATION_KEY), button -> {
            this.getDataSyncHandler().sendSingleTriggerValue(VanillaTweaksDataSyncHandler.SWAP_SLOTS_MAINHAND_AND_HEAD);
        }));
        return widgets;
    }

    @Override
    protected void init() {
        super.init();
        this.addVanillaTweaksCreditsButton();
    }

    @Override
    public ArmorStandScreenType getScreenType() {
        return ModRegistry.VANILLA_TWEAKS_SCREEN_TYPE;
    }
}
