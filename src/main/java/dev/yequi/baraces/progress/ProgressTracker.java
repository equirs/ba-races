package dev.yequi.baraces.progress;

import com.google.common.collect.ImmutableList;
import dev.yequi.baraces.draft.TeamProgress;
import dev.yequi.baraces.draft.TeamManager;
import dev.yequi.baraces.phase.Phase;
import dev.yequi.baraces.phase.PhaseManager;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;

/**
 * Responsible for updating team progress as players appear and disappear in the wave lobbies.
 * Owns the global race timer and reads from it to update progress.
 */
@Slf4j
@Singleton
public class ProgressTracker
{
	@Value
	public static class Coord
	{
		int x;
		int y;
	}

	private final TeamManager teamManager;
	private final PhaseManager phaseManager;
	private final Timer timer = new Timer();

	// SW tiles of each wave in the region. NE tile is (+7,+7)
	private final List<Coord> waveSwTiles = ImmutableList.of(
		new Coord(16, 43),
		new Coord(24, 43),
		new Coord(35, 43),
		new Coord(43, 43),
		new Coord(16, 33),
		new Coord(24, 33),
		new Coord(35, 33),
		new Coord(43, 33),
		new Coord(16, 23),
		new Coord(24, 23)
	);

	// Where team spawns after completing wave 10
	private final Coord ladderAreaSw = new Coord(27, 11);
	private final Coord ladderAreaNe = new Coord(39, 22);

	@Inject
	public ProgressTracker(TeamManager teamManager, PhaseManager phaseManager)
	{
		this.teamManager = teamManager;
		this.phaseManager = phaseManager;
	}

	public void onGameTick()
	{
		timer.onGameTick();
	}

	public void onPlayerSpawned(Player p)
	{
		Phase phase = phaseManager.getPhase();
		if (phase.equals(Phase.IN_PROGRESS))
		{
			trackWaveEnds(p);
		}
	}

	private void trackWaveEnds(Player p)
	{
		TeamProgress data = teamManager.findTeamData(p.getName());
		if (data == null)
		{
			return;
		}
		Progress progress = data.getProgress();
		// team may have already been detected in lobby
		if (!progress.isInWave())
		{
			return;
		}
		Integer wave = findWave(p);
		if (wave == null)
		{
			return;
		}
		// mark that we've seen this team in this lobby
		progress.setInWave(false);
		// check if the team just finished the round
		if (wave.equals(11))
		{
			if (progress.getWave() != 10)
			{
				return;
			}
			setWaveTime(10, progress, false);
			progress.setRoundTime(timer.getTicks());
			// TODO check if race can be ended automatically
		}
		else
		{
			// if the team is not on this wave yet, then they've finished the last wave.
			// otherwise, they've restarted the wave
			if (progress.getWave() < wave)
			{
				setWaveTime(wave - 1, progress, false);
			}
			progress.setWave(wave);
		}
	}

	public void onPlayerDespawned(Player p)
	{
		Phase phase = phaseManager.getPhase();
		if (phase.equals(Phase.PRE_RACE))
		{
			trackFalseStarts();
		}
		else if (phase.equals(Phase.IN_PROGRESS))
		{
			trackWaveStarts(p);
		}
	}

	private void trackWaveStarts(Player p)
	{
		TeamProgress data = teamManager.findTeamData(p.getName());
		if (data == null)
		{
			return;
		}
		Progress progress = data.getProgress();
		// other members of the team may already be in the wave
		if (progress.isInWave())
		{
			return;
		}
		Integer wave = findWave(p);
		if (wave == null)
		{
			return;
		}
		// mark that this team has entered the wave
		progress.setInWave(true);
		progress.setWave(wave);
		setWaveTime(wave, progress, true);
	}

	private void trackFalseStarts()
	{
		// TODO
		// players may be logging out or running out of render distance
		// the most reliable way to detect this is if the entire team is no longer present anywhere in the region
	}

	/**
	 * Returns the wave lobby number corresponding to the player's location.
	 * Returns 11 for the ladder area post-wave 10.
	 * Returns null if the player is not in any matching area.
	 */
	private Integer findWave(Player p)
	{
		WorldPoint wp = p.getWorldLocation();
		int x = wp.getRegionX();
		int y = wp.getRegionY();
		for (int i = 0; i < waveSwTiles.size(); i++)
		{
			Coord sw = waveSwTiles.get(i);
			if (x >= sw.x && x <= sw.x + 7
				&& y >= sw.y && y <= sw.y + 7)
			{
				return i;
			}
		}
		if (x >= ladderAreaSw.x && x <= ladderAreaNe.getX()
			&& y >= ladderAreaSw.y && y <= ladderAreaNe.getY())
		{
			return 11;
		}
		return null;
	}

	/**
	 * Marks time for a given team's progress starting or ending a wave.
	 */
	private void setWaveTime(int wave, Progress progress, boolean start)
	{
		List<Integer> ticks = start ? progress.getWaveStartTicks() : progress.getWaveEndTicks();
		// may have missed tracking previous waves
		while (ticks.size() < wave)
		{
			ticks.add(null);
		}
		ticks.set(wave - 1, timer.getTicks());
	}

}
