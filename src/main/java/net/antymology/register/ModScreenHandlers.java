package net.antymology.register;

import net.antymology.SentientSlime;
import net.antymology.screen.SlimeInventoryScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static final ScreenHandlerType<SlimeInventoryScreenHandler> SLIME_INVENTORY =
        Registry.register(
            Registries.SCREEN_HANDLER,
            new Identifier(SentientSlime.MOD_ID, "slime_inventory"),
            new ScreenHandlerType<>(
                SlimeInventoryScreenHandler::new,
                FeatureSet.empty()
            )
        );

    public static void register() {
    }
}