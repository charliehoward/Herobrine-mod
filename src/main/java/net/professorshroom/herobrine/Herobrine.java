package net.professorshroom.herobrine;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.professorshroom.herobrine.entity.ModEntities;
import net.professorshroom.herobrine.entity.custom.HerobrineEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Herobrine implements ModInitializer {
	public static final String MOD_ID = "herobrine";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("herobrine");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		FabricDefaultAttributeRegistry.register(ModEntities.HEROBRINE, HerobrineEntity.createHerobrineAttributes());
	}
}