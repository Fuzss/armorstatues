package fuzs.armorstatues.client.gui.screens.armorstand;

import fuzs.armorstatues.network.client.data.VanillaTweaksDataSyncHandler;
import fuzs.armorstatues.world.inventory.data.ArmorStandScreenTypes;
import fuzs.statuemenus.api.v1.client.gui.screens.StatueButtonsScreen;
import fuzs.statuemenus.api.v1.network.client.data.DataSyncHandler;
import fuzs.statuemenus.api.v1.world.inventory.StatueHolder;
import fuzs.statuemenus.api.v1.world.inventory.data.StatueScreenType;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class ArmorStandVanillaTweaksScreen extends StatueButtonsScreen {
    public static final String TRIGGER_SENT_TRANSLATION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "trigger_sent");
    public static final String CHECK_TARGET_TRANSLATION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "check_target");
    public static final String CHECK_TARGET_DESCRIPTION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "check_target.description");
    public static final String LOCK_TRANSLATION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "lock");
    public static final String LOCK_DESCRIPTION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "lock.description");
    public static final String UNLOCK_TRANSLATION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "unlock");
    public static final String UNLOCK_DESCRIPTION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "unlock.description");
    public static final String TOOL_RACK_TRANSLATION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "tool_rack");
    public static final String TOOL_RACK_DESCRIPTION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "tool_rack.description");
    public static final String SWAP_MAINHAND_AND_OFFHAND_TRANSLATION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "swap_hands");
    public static final String SWAP_MAINHAND_AND_OFFHAND_DESCRIPTION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "swap_hands.description");
    public static final String SWAP_MAINHAND_AND_HEAD_TRANSLATION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "swap_head");
    public static final String SWAP_MAINHAND_AND_HEAD_DESCRIPTION_KEY = ArmorStandScreenTypes.VANILLA_TWEAKS.id()
            .toLanguageKey("screen", "swap_head.description");

    public ArmorStandVanillaTweaksScreen(StatueHolder holder, Inventory inventory, Component component, DataSyncHandler dataSyncHandler) {
        super(holder, inventory, component, dataSyncHandler);
    }

    @Override
    public VanillaTweaksDataSyncHandler getDataSyncHandler() {
        return (VanillaTweaksDataSyncHandler) super.getDataSyncHandler();
    }

    @Override
    protected List<ArmorStandWidget> buildWidgets(LivingEntity livingEntity) {
        List<ArmorStandWidget> widgets = new ArrayList<>();
        widgets.add(new SingleButtonWidget(Component.translatable(CHECK_TARGET_TRANSLATION_KEY),
                Component.translatable(CHECK_TARGET_DESCRIPTION_KEY),
                Component.translatable(TRIGGER_SENT_TRANSLATION_KEY),
                (Button button) -> {
                    this.getDataSyncHandler().sendSingleTriggerValue(VanillaTweaksDataSyncHandler.CHECK_TARGET);
                    this.onClose();
                }));
        widgets.add(new DoubleButtonWidget(Component.translatable(LOCK_TRANSLATION_KEY),
                Component.translatable(UNLOCK_TRANSLATION_KEY),
                Component.translatable(LOCK_DESCRIPTION_KEY),
                Component.translatable(UNLOCK_DESCRIPTION_KEY),
                Component.translatable(TRIGGER_SENT_TRANSLATION_KEY),
                (Button button) -> {
                    this.getDataSyncHandler().sendSingleTriggerValue(VanillaTweaksDataSyncHandler.UTILITIES_LOCK);
                },
                (Button button) -> {
                    this.getDataSyncHandler().sendSingleTriggerValue(VanillaTweaksDataSyncHandler.UTILITIES_UNLOCK);
                }));
        widgets.add(new SingleButtonWidget(Component.translatable(TOOL_RACK_TRANSLATION_KEY),
                Component.translatable(TOOL_RACK_DESCRIPTION_KEY),
                Component.translatable(TRIGGER_SENT_TRANSLATION_KEY),
                (Button button) -> {
                    this.getDataSyncHandler()
                            .sendSingleTriggerValue(VanillaTweaksDataSyncHandler.AUTO_ALIGNMENT_TOOL_RACK);
                }));
        widgets.add(new SingleButtonWidget(Component.translatable(SWAP_MAINHAND_AND_OFFHAND_TRANSLATION_KEY),
                Component.translatable(SWAP_MAINHAND_AND_OFFHAND_DESCRIPTION_KEY),
                Component.translatable(TRIGGER_SENT_TRANSLATION_KEY),
                (Button button) -> {
                    this.getDataSyncHandler()
                            .sendSingleTriggerValue(VanillaTweaksDataSyncHandler.SWAP_SLOTS_MAINHAND_AND_OFFHAND);
                }));
        widgets.add(new SingleButtonWidget(Component.translatable(SWAP_MAINHAND_AND_HEAD_TRANSLATION_KEY),
                Component.translatable(SWAP_MAINHAND_AND_HEAD_DESCRIPTION_KEY),
                Component.translatable(TRIGGER_SENT_TRANSLATION_KEY),
                (Button button) -> {
                    this.getDataSyncHandler()
                            .sendSingleTriggerValue(VanillaTweaksDataSyncHandler.SWAP_SLOTS_MAINHAND_AND_HEAD);
                }));
        return widgets;
    }

    @Override
    protected void init() {
        super.init();
        this.addVanillaTweaksCreditsButton();
    }

    @Override
    public StatueScreenType getScreenType() {
        return ArmorStandScreenTypes.VANILLA_TWEAKS;
    }
}
