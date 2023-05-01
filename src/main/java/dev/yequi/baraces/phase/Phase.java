package dev.yequi.baraces.phase;

public enum Phase
{
	/**
	 * Number of teams and leader of each team is being decided
	 */
	CAPTAIN,
	/**
	 * Players are added to each team, as chosen by its captain
	 */
	DRAFT,
	/**
	 * Teams are formed and preparing for the race to start
	 */
	PRE_RACE,
	/**
	 * Race is started and teams are going through the waves
	 */
	IN_PROGRESS,
	/**
	 * All teams have finished the round
	 */
	FINISHED
}
