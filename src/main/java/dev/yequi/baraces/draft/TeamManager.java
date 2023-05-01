package dev.yequi.baraces.draft;

import dev.yequi.baraces.progress.Progress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.inject.Singleton;
import lombok.Getter;

/**
 * Owns the teams and their progress for a race. Modifies the team structure.
 */
@Singleton
public class TeamManager
{
	@Getter
	private final List<TeamProgress> teamData = new ArrayList<>();

	public void createTeam(String captainName)
	{
		Team t = new Team();
		if (captainName != null)
		{
			t.getMembers().set(0, captainName);
		}
		teamData.add(new TeamProgress(t, new Progress()));
	}

	public boolean moveTeam(int position, boolean up)
	{
		if (up && position == 0 || !up && (position == teamData.size() - 1))
		{
			return false;
		}
		Collections.swap(teamData, position, up ? position - 1 : position + 1);
		return true;
	}

	/**
	 * Adds a player by name to the next available spot in the draft order.
	 * Assuming snake draft, the player will be added to the team according to
	 * even-odd parity of each team:
	 * - if min(members) is even, insert in first team with even parity
	 * - if min(members) is odd, insert in last team with odd parity
	 */
	public void addToDraft(String name)
	{
		List<Integer> parities = teamData.stream()
			.map(d -> d.getTeam().getMembers().size() % 2)
			.collect(Collectors.toList());
		int min = teamData.stream()
			.map(d -> d.getTeam().getMembers().size())
			.min(Integer::compareTo)
			.orElse(0);
		if (min >= 5)
		{
			return;
		}
		int foundIdx;
		Stream<Integer> indices = IntStream.range(0, teamData.size()).boxed();
		if (min % 2 == 0)
		{
			foundIdx = indices
				.filter(i -> parities.get(i) % 2 == 0)
				.findFirst()
				.orElse(0);
		}
		else
		{
			foundIdx = indices
				.map(i -> teamData.size() - 1 - i)
				.filter(i -> parities.get(i) % 2 == 1)
				.findFirst()
				.orElse(0);
		}
		teamData.get(foundIdx).getTeam().addMember(name);
	}

	@Nullable
	public TeamProgress findTeamData(String playerName)
	{
		return teamData.stream()
			.filter(t -> t.getTeam().getMembers().contains(playerName))
			.findFirst()
			.orElse(null);
	}
}
