package net.antymology.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.antymology.SentientSlime;
import net.minecraft.entity.LivingEntity;

public class EatFastPower extends Power {

    private final int speed;

    public EatFastPower(PowerType<?> type, LivingEntity entity, int speed) {
        super(type, entity);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(
            SentientSlime.id("fast_eating"),
            new SerializableData()
                .add("speed", SerializableDataTypes.INT, 2),
            data -> (type, entity) ->
                new EatFastPower(
                    type,
                    entity,
                    data.getInt("speed")
                )
        ).allowCondition();
    }
}