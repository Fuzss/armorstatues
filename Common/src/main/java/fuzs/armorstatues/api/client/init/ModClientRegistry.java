package fuzs.armorstatues.api.client.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class ModClientRegistry {
    public static final KeyMapping CYCLE_TABS_KEY_MAPPING = new KeyMapping("key.cycleStatueTabs", InputConstants.KEY_TAB, "key.categories.inventory");
}
