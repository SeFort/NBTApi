package lol.sefort.nbtapi;

import lol.sefort.nbtapi.world.WorldScanner;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NBTApi implements ClientModInitializer {

	public static final String MOD_ID = "nbtapi";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static WorldScanner worldScanner;

	@Override
	public void onInitializeClient() {
		initWorldScanner();

		registerWorldLeaveEvent();
		LOGGER.info("[NBTApi] Mod successfully loaded");
	}

	private void initWorldScanner() {
		try {
			worldScanner = WorldScanner.createDefault();
			LOGGER.info("[NBTApi] WorldScanner initialized: {}", worldScanner.getSavesFolder().getAbsolutePath());
		} catch (Exception e) {
			LOGGER.error("[NBTApi] Error initializing WorldScanner", e);
		}
	}

	private void registerWorldLeaveEvent() {
		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			LOGGER.info("[NBTApi] Leaving the world - rescan...");
			rescanWorlds();
		});
	}

	public static void rescanWorlds() {
		if (worldScanner != null) {
			worldScanner.scan();
			LOGGER.info("[NBTApi] Worlds rescan. Found: {}", worldScanner.getWorldCount());
		}
	}
}