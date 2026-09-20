package net.antymology.register;

import net.antymology.SentientSlime;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEffects {

    public static final StatusEffect BOUNCY_EFFECT = Registry.register(
        Registries.STATUS_EFFECT,
        SentientSlime.id("bouncy"),
        new StatusEffect(StatusEffectCategory.BENEFICIAL, 0x07c900) {}
    );

    public static void register() {
    }
}