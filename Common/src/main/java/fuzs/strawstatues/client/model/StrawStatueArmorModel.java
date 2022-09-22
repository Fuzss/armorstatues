package fuzs.strawstatues.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.decoration.ArmorStand;

public class StrawStatueArmorModel<T extends ArmorStand> extends HumanoidModel<T> {

    public StrawStatueArmorModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        StrawStatueModel.setupPoseAnim(this, entity);
    }
}
