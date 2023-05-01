package dev.yequi.baraces.draft;

import dev.yequi.baraces.progress.Progress;
import lombok.Data;

@Data
public class TeamProgress
{
	private final Team team;
	private final Progress progress;
}
