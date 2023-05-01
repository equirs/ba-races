package dev.yequi.baraces.draft;

import lombok.Data;

@Data
public class RacerStatus
{
	public enum Icon
	{
		NONE,
		GOOD,
		WARNING,
		ERROR
	}

	private Icon icon = Icon.NONE;
	private String message = "";
}
