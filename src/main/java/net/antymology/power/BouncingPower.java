package net.antymology.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.antymology.SentientSlime;
import net.minecraft.entity.LivingEntity;

public class BouncingPower extends Power {
    private final double velocity;

    public BouncingPower(PowerType<?> type, LivingEntity entity, double velocity) {
        super(type, entity);
        this.velocity = velocity;
    }

    public double getVelocity() {
        return velocity;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(SentientSlime.id("bouncy"),
            new SerializableData().add("amount", SerializableDataTypes.DOUBLE, 0.8), data -> (type, livingEntity) -> new BouncingPower(type, livingEntity, data.getDouble("amount"))).allowCondition();
    }
}