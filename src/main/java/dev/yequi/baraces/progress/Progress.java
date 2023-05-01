package dev.yequi.baraces.progress;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * Measure of how far along a particular team is in the race.
 * Owned by TeamManager, but modified by ProgressTracker
 */
@Data
public class Progress
{
	private int wave = 1;
	// teams can be either in wave or in lobby
	private boolean inWave;
	// when the team enters each wave, in ticks elapsed since start
	private final List<Integer> waveStartTicks = new ArrayList<>();
	// when the team leaves each wave, in ticks elapsed since start
	private final List<Integer> waveEndTicks = new ArrayList<>();
	// total round time in ticks
	private Integer roundTime;
	// TODO team readied flag (all players in NE wave 1 or by ladder)
}
