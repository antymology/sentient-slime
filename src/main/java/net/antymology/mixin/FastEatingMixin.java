package net.antymology.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.antymology.power.EatFastPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class FastEatingMixin {

    @Shadow
    protected int itemUseTimeLeft;

    @Inject(method = "setCurrentHand", at = @At("TAIL"))
    private void simpleSlime$speedEating(Hand hand, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!(entity instanceof PlayerEntity player)) {
            return;
        }

        ItemStack stack = player.getStackInHand(hand);

        if (!stack.isFood()) {
            return;
        }

        EatFastPower power = PowerHolderComponent.getPowers(player, EatFastPower.class).stream().findFirst().orElse(null);

        if (power == null || !power.isActive()) {
            return;
        }

        int speed = Math.max(1, power.getSpeed());

        itemUseTimeLeft = Math.max(1, itemUseTimeLeft / speed);
    }
}