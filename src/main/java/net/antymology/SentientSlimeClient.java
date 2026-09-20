package net.antymology;

import net.antymology.register.ModScreenHandlers;
import net.antymology.screen.SlimeInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class SentientSlimeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(
            ModScreenHandlers.SLIME_INVENTORY,
            SlimeInventoryScreen::new
        );
    }
}