package net.antymology.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.antymology.power.BouncingPower;
import net.antymology.register.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class FallDamageMixin {

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void simpleSlime$cancelFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (PowerHolderComponent.hasPower(entity, BouncingPower.class) || entity.hasStatusEffect(ModEffects.BOUNCY_EFFECT)) {
            cir.setReturnValue(false);
        }
    }
}