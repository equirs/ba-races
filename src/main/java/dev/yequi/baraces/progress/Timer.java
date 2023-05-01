package dev.yequi.baraces.progress;

import lombok.Getter;

public class Timer
{
	@Getter
	private int ticks = 0;
	@Getter
	private boolean started = false;

	public void start()
	{
		started = true;
	}

	public void finish()
	{
		started = false;
	}

	public void onGameTick()
	{
		if (started)
		{
			ticks++;
		}
	}
}
