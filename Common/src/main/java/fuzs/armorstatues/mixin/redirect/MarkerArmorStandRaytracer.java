package fuzs.armorstatues.mixin.redirect;

import fuzs.armorstatues.mixin.accessor.ArmorStandAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin(ProjectileUtil.class)
public class MarkerArmorStandRaytracer {
    @Redirect(method = "getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"))
    private static AABB getBoundingBox(Entity a, Entity p) {
        if (a instanceof ArmorStand armorStand && p instanceof Player player && player.isShiftKeyDown() && armorStand.isMarker()) {
            return ((ArmorStandAccessor) armorStand).getArmorstandDimensions(false).makeBoundingBox(armorStand.position());
        }
        return a.getBoundingBox();
    }

    @Redirect(method = "getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"))
    private static java.util.List<Entity> getEntities(Level level, Entity p, AABB aabb, Predicate<Entity> predicate) {
        if (p instanceof Player player && player.isShiftKeyDown()) {
            Predicate<Entity> allowMarkerArmorStands = entity -> (entity instanceof ArmorStand armorStand && armorStand.isMarker());
            return level.getEntities(p, aabb, predicate.or(allowMarkerArmorStands));
        }
        return level.getEntities(p, aabb, predicate);
    }
}
