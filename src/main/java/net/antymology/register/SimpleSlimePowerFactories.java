package net.antymology.register;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.PowerFactorySupplier;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.antymology.power.BouncingPower;
import net.antymology.power.EatFastPower;
import net.antymology.power.SlimeInventoryPower;
import net.minecraft.registry.Registry;

public class SimpleSlimePowerFactories {
    public static void register() {
        register(BouncingPower::createFactory);
        register(SlimeInventoryPower::createFactory);
        register(EatFastPower::createFactory);
    }

    private static void register(PowerFactory<?> factory) {
        Registry.register(ApoliRegistries.POWER_FACTORY, factory.getSerializerId(), factory);
    }

    private static void register(PowerFactorySupplier<?> supplier) {
        register(supplier.createFactory());
    }
}
