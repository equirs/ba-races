package dev.yequi.baraces.draft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * Names of all five members, ideally in draft order.
 */
public class Team
{
	@Getter
	private final List<String> members = new ArrayList<>(Collections.nCopies(5, ""));
	@Getter
	private final List<RacerStatus> statuses = new ArrayList<>(Collections.nCopies(5, new RacerStatus()));
	@Getter
	@Setter
	private boolean rebel = false;

	public String getCaptain()
	{
		return members.get(0);
	}

	public boolean isFilled()
	{
		return members.stream().noneMatch(StringUtils::isBlank);
	}

	/**
	 * Adds the name to the team, replacing the first blank item
	 */
	public void addMember(String name)
	{
		for (int i = 0; i < members.size(); i++)
		{
			String member = members.get(i);
			if (StringUtils.isBlank(member))
			{
				members.set(i, name);
				break;
			}
		}
	}
}
