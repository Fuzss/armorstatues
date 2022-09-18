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
        this.add("armorstatues.entity.armor_stand.pose.athena", "Athena");
        this.add("armorstatues.entity.armor_stand.pose.brandish", "Brandish");
        this.add("armorstatues.entity.armor_stand.pose.cancanA", "Cancan #1");
        this.add("armorstatues.entity.armor_stand.pose.cancanB", "Cancan #2");
        this.add("armorstatues.entity.armor_stand.pose.default", "Default");
        this.add("armorstatues.entity.armor_stand.pose.entertain", "Entertain");
        this.add("armorstatues.entity.armor_stand.pose.hero", "Hero");
        this.add("armorstatues.entity.armor_stand.pose.honor", "Honor");
        this.add("armorstatues.entity.armor_stand.pose.none", "None");
        this.add("armorstatues.entity.armor_stand.pose.riposte", "Riposte");
        this.add("armorstatues.entity.armor_stand.pose.salute", "Salute");
        this.add("armorstatues.entity.armor_stand.pose.solemn", "Solemn");
        this.add("armorstatues.entity.armor_stand.pose.zombie", "Zombie");
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
        this.add("armorstatues.screen.style.glowing", "Glowing");
        this.add("armorstatues.screen.style.sealed", "Sealed");
        this.add("armorstatues.screen.style.showArms.description", "Shows the armor stand's arms, so it may hold items in both hands.");
        this.add("armorstatues.screen.style.small.description", "Makes the armor stand half it's size like a baby mob.");
        this.add("armorstatues.screen.style.invisible.description", "Makes the armor stand itself invisible, but still shows all equipped items.");
        this.add("armorstatues.screen.style.noBasePlate.description", "Hide the stone base plate at the armor stand's feet.");
        this.add("armorstatues.screen.style.showName.description", "Render the armor stand's name tag above it's head.");
        this.add("armorstatues.screen.style.noGravity.description", "Prevents the armor stand from falling down, so it may float freely.");
        this.add("armorstatues.screen.style.glowing.description", "Adds a glowing outline to the armor stand, visible through blocks.");
        this.add("armorstatues.screen.style.sealed.description", "The armor stand can no longer be broken, equipment cannot be changed. Disallows opening this menu in survival mode.");
        this.add("armorstatues.screen.position.moveBy", "Move By:");
        this.add("armorstatues.screen.position.pixels", "%s Pixel(s)");
        this.add("armorstatues.screen.position.blocks", "%s Block(s)");
        this.add("armorstatues.screen.position.x", "X-Position:");
        this.add("armorstatues.screen.position.y", "Y-Position:");
        this.add("armorstatues.screen.position.z", "Z-Position:");
        this.add("armorstatues.screen.position.increment", "Increment By %s");
        this.add("armorstatues.screen.position.decrement", "Decrement By %s");
        this.add("armorstatues.screen.position.centered", "Align Centered");
        this.add("armorstatues.screen.position.cornered", "Align Cornered");
        this.add("armorstatues.screen.position.aligned", "Aligned!");
        this.add("armorstatues.screen.pose.randomize", "Randomize");
        this.add("armorstatues.screen.pose.randomized", "Applied!");
        this.add("armorstatues.screen.copy", "Copy");
        this.add("armorstatues.screen.copied", "Copied!");
        this.add("armorstatues.screen.paste", "Paste");
        this.add("armorstatues.screen.pasted", "Pasted!");
    }
}
