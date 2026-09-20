package net.antymology;
import net.antymology.register.ModEffects;
import net.antymology.register.ModScreenHandlers;
import net.antymology.register.SimpleSlimePowerFactories;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentientSlime implements ModInitializer {
	public static final String MOD_ID = "sentient_slime";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item ICON = Registry.register(
		Registries.ITEM,
		SentientSlime.id("attuned_slimeball"),
		new Item(new Item.Settings())
	);

	@Override
	public void onInitialize() {
		SimpleSlimePowerFactories.register();
		ModScreenHandlers.register();
		ModEffects.register();
		LOGGER.info("Sentient Slime loaded!");
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
