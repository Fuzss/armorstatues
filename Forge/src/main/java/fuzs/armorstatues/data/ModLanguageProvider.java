package fuzs.armorstatues.data;

import fuzs.armorstatues.api.world.inventory.data.ArmorStandPose;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandScreenType;
import fuzs.armorstatues.api.world.inventory.data.ArmorStandStyleOptions;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(DataGenerator gen, String modId) {
        super(gen, modId, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("armorstatues.item.armor_stand.description", "Use %s + %s with an empty hand to open configuration screen.");
        this.add("armorstatues.screen.style.name", "Set a name to display above the entity if enabled.");
        this.add("armorstatues.entity.armor_stand.pose.by", "By %s");
        this.add(ArmorStandPose.ATHENA.getTranslationKey(), "Athena");
        this.add(ArmorStandPose.BRANDISH.getTranslationKey(), "Brandish");
        this.add(ArmorStandPose.CANCAN.getTranslationKey(), "Cancan");
        this.add(ArmorStandPose.DEFAULT.getTranslationKey(), "Default");
        this.add(ArmorStandPose.ENTERTAIN.getTranslationKey(), "Entertain");
        this.add(ArmorStandPose.HERO.getTranslationKey(), "Hero");
        this.add(ArmorStandPose.HONOR.getTranslationKey(), "Honor");
        this.add(ArmorStandPose.RIPOSTE.getTranslationKey(), "Riposte");
        this.add(ArmorStandPose.SALUTE.getTranslationKey(), "Salute");
        this.add(ArmorStandPose.SOLEMN.getTranslationKey(), "Solemn");
        this.add(ArmorStandPose.ZOMBIE.getTranslationKey(), "Zombie");
        this.add(ArmorStandPose.WALKING.getTranslationKey(), "Walking");
        this.add(ArmorStandPose.RUNNING.getTranslationKey(), "Running");
        this.add(ArmorStandPose.POINTING.getTranslationKey(), "Pointing");
        this.add(ArmorStandPose.BLOCKING.getTranslationKey(), "Blocking");
        this.add(ArmorStandPose.LUNGEING.getTranslationKey(), "Lungeing");
        this.add(ArmorStandPose.WINNING.getTranslationKey(), "Winning");
        this.add(ArmorStandPose.SITTING.getTranslationKey(), "Sitting");
        this.add(ArmorStandPose.ARABESQUE.getTranslationKey(), "Arabesque");
        this.add(ArmorStandPose.CUPID.getTranslationKey(), "Cupid");
        this.add(ArmorStandPose.CONFIDENT.getTranslationKey(), "Confident");
        this.add(ArmorStandPose.DEATH.getTranslationKey(), "Death");
        this.add(ArmorStandPose.FACEPALM.getTranslationKey(), "Facepalm");
        this.add(ArmorStandPose.LAZING.getTranslationKey(), "Lazing");
        this.add(ArmorStandPose.CONFUSED.getTranslationKey(), "Confused");
        this.add(ArmorStandPose.FORMAL.getTranslationKey(), "Formal");
        this.add(ArmorStandPose.SAD.getTranslationKey(), "Sad");
        this.add(ArmorStandPose.JOYOUS.getTranslationKey(), "Joyous");
        this.add(ArmorStandPose.STARGAZING.getTranslationKey(), "Stargazing");
        this.add(ArmorStandScreenType.EQUIPMENT.getTranslationKey(), "Equipment");
        this.add(ArmorStandScreenType.ROTATIONS.getTranslationKey(), "Rotations");
        this.add(ArmorStandScreenType.STYLE.getTranslationKey(), "Style");
        this.add(ArmorStandScreenType.POSES.getTranslationKey(), "Poses");
        this.add(ArmorStandScreenType.POSITION.getTranslationKey(), "Position");
        this.add(ArmorStandScreenType.ALIGNMENTS.getTranslationKey(), "Alignments");
        this.add(ArmorStandStyleOptions.SHOW_ARMS.getTranslationKey(), "Show Arms");
        this.add(ArmorStandStyleOptions.SHOW_ARMS.getDescriptionKey(), "Shows the statue's arms, so it may hold items in both hands.");
        this.add(ArmorStandStyleOptions.SMALL.getTranslationKey(), "Small");
        this.add(ArmorStandStyleOptions.SMALL.getDescriptionKey(), "Makes the statue half it's size like a baby mob.");
        this.add(ArmorStandStyleOptions.INVISIBLE.getTranslationKey(), "Invisible");
        this.add(ArmorStandStyleOptions.INVISIBLE.getDescriptionKey(), "Makes the statue itself invisible, but still shows all equipped items.");
        this.add(ArmorStandStyleOptions.NO_BASE_PLATE.getTranslationKey(), "No Base Plate");
        this.add(ArmorStandStyleOptions.NO_BASE_PLATE.getDescriptionKey(), "Hide the stone base plate at the statue's feet.");
        this.add(ArmorStandStyleOptions.SHOW_NAME.getTranslationKey(), "Show Name");
        this.add(ArmorStandStyleOptions.SHOW_NAME.getDescriptionKey(), "Render the statue's name tag above it's head.");
        this.add(ArmorStandStyleOptions.NO_GRAVITY.getTranslationKey(), "No Gravity");
        this.add(ArmorStandStyleOptions.NO_GRAVITY.getDescriptionKey(), "Prevents the statue from falling down, so it may float freely.");
        this.add(ArmorStandStyleOptions.SEALED.getTranslationKey(), "Sealed");
        this.add(ArmorStandStyleOptions.SEALED.getDescriptionKey(), "The statue can no longer be broken, equipment cannot be changed. Disallows opening this menu in survival mode.");
        this.add("armorstatues.screen.position.rotation", "Rotation:");
        this.add("armorstatues.screen.position.degrees", "%s\u00B0");
        this.add("armorstatues.screen.position.moveBy", "Move By:");
        this.add("armorstatues.screen.position.pixels", "%s Pixel(s)");
        this.add("armorstatues.screen.position.blocks", "%s Block(s)");
        this.add("armorstatues.screen.position.x", "X-Position:");
        this.add("armorstatues.screen.position.y", "Y-Position:");
        this.add("armorstatues.screen.position.z", "Z-Position:");
        this.add("armorstatues.screen.position.increment", "Increment by %s");
        this.add("armorstatues.screen.position.decrement", "Decrement by %s");
        this.add("armorstatues.screen.pose.randomize", "Randomize");
        this.add("armorstatues.screen.pose.randomized", "Applied!");
        this.add("armorstatues.screen.rotations.pose.head", "Head");
        this.add("armorstatues.screen.rotations.pose.body", "Body");
        this.add("armorstatues.screen.rotations.pose.leftArm", "Left Arm");
        this.add("armorstatues.screen.rotations.pose.rightArm", "Right Arm");
        this.add("armorstatues.screen.rotations.pose.leftLeg", "Left Leg");
        this.add("armorstatues.screen.rotations.pose.rightLeg", "Right Leg");
        this.add("armorstatues.screen.rotations.tip1", "Hold the Shift or Alt key to lock sliders to a single axis!");
        this.add("armorstatues.screen.rotations.tip2", "Use the arrow keys to move sliders more precisely! Focus a slider first by clicking.");
        this.add("armorstatues.screen.rotations.reset", "Reset");
        this.add("armorstatues.screen.rotations.randomize", "Randomize");
        this.add("armorstatues.screen.rotations.limited", "Limited Rotations");
        this.add("armorstatues.screen.rotations.unlimited", "Unlimited Rotations");
        this.add("armorstatues.screen.rotations.copy", "Copy");
        this.add("armorstatues.screen.rotations.paste", "Paste");
        this.add("armorstatues.screen.rotations.mirror", "Mirror");
        this.add("armorstatues.screen.rotations.x", "X: %s");
        this.add("armorstatues.screen.rotations.y", "Y: %s");
        this.add("armorstatues.screen.rotations.z", "Z: %s");
        this.add("armorstatues.screen.alignments.centered", "Align Centered");
        this.add("armorstatues.screen.alignments.centered.description", "Align an armor stand in the center of the block position it is placed on.");
        this.add("armorstatues.screen.alignments.cornered", "Align Cornered");
        this.add("armorstatues.screen.alignments.cornered.description", "Align an armor stand at the corner of the block position it is placed on.");
        this.add("armorstatues.screen.alignments.aligned", "Aligned!");
        this.add("armorstatues.screen.alignments.block", "Align Block on Surface");
        this.add("armorstatues.screen.alignments.block.description", "Align an armor stand placed on a surface so that a block held by it appears on the surface.");
        this.add("armorstatues.screen.alignments.itemFloating", "Align Item On Surface");
        this.add("armorstatues.screen.alignments.itemFloating.description", "Align an armor stand placed on a surface so that an item held by it appears upright on the surface.");
        this.add("armorstatues.screen.alignments.itemFlat", "Align Item Flat On Surface");
        this.add("armorstatues.screen.alignments.itemFlat.description", "Align an armor stand placed on a surface so that a non-tool item held by it appears flat on the surface.");
        this.add("armorstatues.screen.alignments.tool", "Align Tool Flat On Surface");
        this.add("armorstatues.screen.alignments.tool.description", "Align an armor stand placed on a surface so that a tool held by it appears flat on the surface.");
        this.add("armorstatues.screen.vanillaTweaksCredit", "Some content on this page originates from the Vanilla Tweaks \"Armor Statues\" data pack. Click this button to go to their website!");
        this.add("armorstatues.screen.failure", "Unable to modify armor stand data: %s");
        this.add("armorstatues.screen.failure.noPermission", "No Permission");
        this.add("armorstatues.screen.failure.notFinished", "Queue Not Empty");
        this.add("armorstatues.screen.finished", "Finished sending queued armor stand data");
    }
}
