package dev.yequi.baraces.phase;

import javax.inject.Singleton;
import lombok.Getter;
import lombok.Setter;

/**
 * Stores the current race phase, which enables different functionality across the plugin.
 */
@Singleton
public class PhaseManager
{
	@Getter
	@Setter
	private Phase phase = Phase.CAPTAIN;
}
