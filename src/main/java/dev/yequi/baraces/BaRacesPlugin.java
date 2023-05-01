package dev.yequi.baraces;

import com.google.inject.Provides;
import dev.yequi.baraces.draft.TeamManager;
import dev.yequi.baraces.panel.BaRacesPanel;
import dev.yequi.baraces.phase.Phase;
import dev.yequi.baraces.phase.PhaseManager;
import dev.yequi.baraces.progress.ProgressTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.Player;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.PlayerDespawned;
import net.runelite.api.events.PlayerSpawned;
import net.runelite.client.RuneLite;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyManager;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;

@Slf4j
@PluginDescriptor(
	name = "BA Races",
	description = "Utilities for hosting Barbarian Assault races"
)
public class BaRacesPlugin extends Plugin
{
	public static final File EXPORT_DIR = new File(RuneLite.RUNELITE_DIR, "ba-races");
	private static final String CAPTAIN_PLAYER = "Captain";
	private static final String DRAFT_PLAYER = "Draft";
	// regionID of BA wave lobbies
	public static final int REGION_BA_LOBBIES = 10322;

	@Inject
	private Client client;

	@Inject
	private BaRacesConfig config;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private KeyManager keyManager;

	@Inject
	private MenuManager menuManager;

	@Inject
	private TeamManager teamManager;

	@Inject
	private PhaseManager phaseManager;

	@Inject
	private ProgressTracker progressTracker;

	private boolean inBaDownstairs;
	private BaRacesPanel panel;
	private NavigationButton navButton;

	@Provides
	BaRacesConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BaRacesConfig.class);
	}

	@Override
	protected void startUp()
	{
		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "panelicon.png");
		panel = injector.getInstance(BaRacesPanel.class);
		navButton = NavigationButton.builder()
			.tooltip("BA Races")
			.icon(icon)
			.panel(panel)
			.priority(5)
			.build();
		clientToolbar.addNavigation(navButton);
		// TODO hotkey
		refreshIsDownstairs();
	}

	@Override
	protected void shutDown()
	{
		clientToolbar.removeNavigation(navButton);
		menuManager.removePlayerMenuItem(CAPTAIN_PLAYER);
		menuManager.removePlayerMenuItem(DRAFT_PLAYER);
		// TODO reset all singleton states
		// TODO hotkey
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		// TODO
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (event.getMenuAction() == MenuAction.RUNELITE_PLAYER)
		{
			if (event.getMenuOption().equals(CAPTAIN_PLAYER))
			{
				teamManager.createTeam(event.getMenuTarget());
				// TODO update teams
				panel.updateUI();
			}
			else if (event.getMenuOption().equals(DRAFT_PLAYER))
			{
				teamManager.addToDraft(event.getMenuTarget());
				// TODO update teams
				panel.updateUI();
			}
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOADING)
		{
			refreshIsDownstairs();
		}
	}

	@Subscribe
	public void onPlayerSpawned(PlayerSpawned event)
	{
		if (!inBaDownstairs)
		{
			return;
		}
		Player p = event.getPlayer();
		progressTracker.onPlayerSpawned(p);
	}

	@Subscribe
	public void onPlayerDespawned(PlayerDespawned event)
	{
		if (!inBaDownstairs)
		{
			return;
		}
		Player p = event.getPlayer();
		if (p != null)
		{
			progressTracker.onPlayerDespawned(p);
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		progressTracker.onGameTick();
		// TODO correctness checks based on phase (every few ticks)
	}

	private void refreshIsDownstairs()
	{
		boolean downstairs = checkInBaDownstairs();
		if (downstairs != inBaDownstairs)
		{
			log.warn("changing downstairs to " + downstairs);
			if (downstairs)
			{
				if (phaseManager.getPhase().equals(Phase.CAPTAIN))
				{
					menuManager.addPlayerMenuItem(CAPTAIN_PLAYER);
				}
				else if (phaseManager.getPhase().equals(Phase.DRAFT))
				{
					menuManager.addPlayerMenuItem(DRAFT_PLAYER);
				}
			}
			else
			{
				menuManager.removePlayerMenuItem(CAPTAIN_PLAYER);
				menuManager.removePlayerMenuItem(DRAFT_PLAYER);
			}
		}
		inBaDownstairs = downstairs;
	}

	private boolean checkInBaDownstairs()
	{
		GameState gameState = client.getGameState();
		if (gameState != GameState.LOGGED_IN && gameState != GameState.LOADING)
		{
			return false;
		}
		int[] currentMapRegions = client.getMapRegions();
		return currentMapRegions.length > 0 && currentMapRegions[0] == REGION_BA_LOBBIES;
	}

}
