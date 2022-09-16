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
        this.add("armorstatues.screen.type.equipment", "Equipment");
        this.add("armorstatues.screen.type.rotations", "Rotations");
        this.add("armorstatues.screen.type.style", "Style");
        this.add("armorstatues.screen.type.poses", "Poses");
        this.add("armorstatues.screen.type.position", "Position");
        this.add("armorstatues.screen.style.showArms", "Show Arms");
        this.add("armorstatues.screen.style.small", "Small");
        this.add("armorstatues.screen.style.invisible", "Invisible");
        this.add("armorstatues.screen.style.noBasePlate", "No Base Plate");
        this.add("armorstatues.screen.style.showName", "Show Name");
        this.add("armorstatues.screen.style.noGravity", "No Gravity");
        this.add("armorstatues.screen.style.showArms.description", "Shows the armor stand's arms, so it may hold items in both hands.");
        this.add("armorstatues.screen.style.small.description", "Makes the armor stand half it's size like a baby mob.");
        this.add("armorstatues.screen.style.invisible.description", "Makes the armor stand itself invisible, but still shows all equipped items.");
        this.add("armorstatues.screen.style.noBasePlate.description", "Hide the stone base plate at the armor stand's feet.");
        this.add("armorstatues.screen.style.showName.description", "Render the armor stand's name tag above it's head.");
        this.add("armorstatues.screen.style.noGravity.description", "Prevents the armor stand from falling down, so it may float freely.");
    }
}
