package fuzs.armorstatues.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(DataGenerator gen, String modId) {
        super(gen, modId, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("armorstatues.item.armor_stand.description", "Shift + right-click to open configuration screen when placed.");
        this.add("armorstatues.screen.style.showArms", "Show Arms");
        this.add("armorstatues.screen.style.small", "Small");
        this.add("armorstatues.screen.style.invisible", "Invisible");
        this.add("armorstatues.screen.style.noBasePlate", "No Base Plate");
        this.add("armorstatues.screen.style.showName", "Show Name");
        this.add("armorstatues.screen.style.showArms.tooltip", "Enables the armor stand's arms, so it may hold items in both hands.");
        this.add("armorstatues.screen.style.small.tooltip", "Makes the armor stand half it's size like a baby mob.");
        this.add("armorstatues.screen.style.invisible.tooltip", "Makes the armor stand itself invisible, but still shows all equipment.");
        this.add("armorstatues.screen.style.noBasePlate.tooltip", "Hide the stone base plate.");
        this.add("armorstatues.screen.style.showName.tooltip", "Render the armor stand's name tag above it's head.");
    }
}
