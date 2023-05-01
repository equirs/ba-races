package dev.yequi.baraces;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;

@ConfigGroup(BaRacesConfig.GROUP)
public interface BaRacesConfig extends Config
{
	String GROUP = "baRaces";

	@ConfigItem(
		keyName = "countdownTicks",
		name = "Countdown ticks",
		description = "Number of game ticks between input and timer start"
	)
	default int countdownTicks()
	{
		return 3;
	}

	@ConfigItem(
		keyName = "hotkey",
		name = "Race start hotkey",
		description = "Hotkey that initiates the countdown to start"
	)
	default Keybind hotkey()
	{
		return new Keybind(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
	}

	@ConfigItem(
		keyName = "exportCsv",
		name = "Export CSV",
		description = "Export finished races to .csv files"
	)
	default boolean exportCsv()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showOverlays",
		name = "Show overlays",
		description = "Enables overlays for races in progress"
	)
	default boolean showOverlays()
	{
		return true;
	}
}
