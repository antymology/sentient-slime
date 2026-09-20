package net.antymology.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.antymology.power.BouncingPower;
import net.antymology.register.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Entity.class)
public abstract class BounceMixin {

	@Unique
	private static final double MIN_IMPACT_SPEED = 0.1;

	@Unique
	private static final double SOUND_IMPACT_SPEED = 0.6;

	@Unique
	private Vec3d eywam$velocityBeforeMove = Vec3d.ZERO;

	@Unique
	private Vec3d eywam$positionBeforeMove = Vec3d.ZERO;

	@Inject(method = "move", at = @At("HEAD"))
	private void eywam$beforeMove(MovementType movementType, Vec3d movement, CallbackInfo ci) {
		Entity entity = (Entity) (Object) this;
		if (entity.isLiving() && (PowerHolderComponent.hasPower(entity, BouncingPower.class) || ((LivingEntity) entity).hasStatusEffect(ModEffects.BOUNCY_EFFECT)) && !entity.bypassesLandingEffects()) {
			eywam$velocityBeforeMove = entity.getVelocity();
			eywam$positionBeforeMove = entity.getPos();
		}
	}

	@Inject(method = "move", at = @At("TAIL"))
	private void eywam$afterMove(MovementType movementType, Vec3d movement, CallbackInfo ci) {
		Entity entity = (Entity) (Object) this;
		if (entity.isLiving() && (PowerHolderComponent.hasPower(entity, BouncingPower.class) || ((LivingEntity) entity).hasStatusEffect(ModEffects.BOUNCY_EFFECT)) && !entity.bypassesLandingEffects()) {
			double COR;

			if (PowerHolderComponent.hasPower(entity, BouncingPower.class)) {
				COR = PowerHolderComponent.getPowers(entity, BouncingPower.class)
					.stream()
					.map(BouncingPower::getVelocity)
					.reduce(Double::sum)
					.orElse(1.0);
			} else {
				COR = 0.9;
			}
			Vec3d velocity = eywam$velocityBeforeMove;
			Vec3d actualMovement = entity.getPos().subtract(eywam$positionBeforeMove);
			Vec3d blocked = movement.subtract(actualMovement);

			if (blocked.lengthSquared() < 1.0E-8) {
				return;
			}

			Vec3d normal;

			if (entity.verticalCollision) {
				normal = new Vec3d(0, velocity.y > 0 ? -1 : 1, 0);
			} else if (entity.horizontalCollision) {
				Vec3d horizontalBlocked = new Vec3d(blocked.x, 0, blocked.z);

				if (horizontalBlocked.lengthSquared() < 1.0E-8) {
					return;
				}

				normal = horizontalBlocked.normalize();
			} else {
				return;
			}

			double impactSpeed = Math.abs(velocity.dotProduct(normal));

			if (impactSpeed < MIN_IMPACT_SPEED) {
				return;
			}

			Vec3d reflected = velocity.subtract(
				normal.multiply(2.0 * velocity.dotProduct(normal))
			).multiply(COR);

			// sloppy addition to make horizontal velocity in minecraft noticeable and/or fun
			if (entity.verticalCollision) {
				reflected = new Vec3d(
					reflected.x * 1.7,
					reflected.y,
					reflected.z * 1.7
				);
			}

			entity.setVelocity(reflected);

			if (entity.getWorld().isClient
				&& entity instanceof PlayerEntity
				&& impactSpeed >= SOUND_IMPACT_SPEED) {
				eywam$playImpactEffect(entity, impactSpeed);
			}
		}
	}

	@Unique
	private void eywam$playImpactEffect(Entity entity, double impactSpeed) {
		float strength = (float) Math.min(1.0, impactSpeed / 2.0);

		entity.getWorld().playSound(
			entity.getX(),
			entity.getY(),
			entity.getZ(),
			SoundEvents.ENTITY_SLIME_SQUISH,
			SoundCategory.PLAYERS,
			0.2F + strength * 0.5F,
			0.7F + entity.getWorld().random.nextFloat() * 0.3F,
			false
		);

		int count = Math.min(40, 4 + (int) (impactSpeed * 12.0));
		double spread = Math.min(1.5, 0.3 + impactSpeed * 0.4);

		for (int i = 0; i < count; i++) {
			double angle = entity.getWorld().random.nextDouble() * Math.PI * 2.0;
			double distance = entity.getWorld().random.nextDouble() * spread;

			double x = entity.getX() + Math.cos(angle) * distance;
			double y = entity.getY() + 0.05;
			double z = entity.getZ() + Math.sin(angle) * distance;

			double vx = Math.cos(angle) * (0.03 + impactSpeed * 0.04);
			double vy = 0.03 + entity.getWorld().random.nextDouble()
				* (0.05 + impactSpeed * 0.05);
			double vz = Math.sin(angle) * (0.03 + impactSpeed * 0.04);

			entity.getWorld().addParticle(
				ParticleTypes.ITEM_SLIME,
				x,
				y,
				z,
				vx,
				vy,
				vz
			);
		}
	}
}