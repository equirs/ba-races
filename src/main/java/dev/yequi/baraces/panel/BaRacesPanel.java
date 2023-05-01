package dev.yequi.baraces.panel;

import dev.yequi.baraces.draft.TeamManager;
import dev.yequi.baraces.phase.PhaseManager;
import dev.yequi.baraces.progress.ProgressTracker;
import java.awt.BorderLayout;
import javax.inject.Inject;
import javax.swing.JPanel;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

public class BaRacesPanel extends PluginPanel
{
	private final PhaseManager phaseManager;
	private final TeamManager teamManager;
	private final ProgressTracker progressTracker;

	@Inject
	BaRacesPanel(PhaseManager phaseManager, TeamManager teamManager, ProgressTracker progressTracker)
	{
		super(false);
		this.phaseManager = phaseManager;
		this.teamManager = teamManager;
		this.progressTracker = progressTracker;
		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARK_GRAY_COLOR);

		JPanel statusBar = new JPanel();
		statusBar.setLayout(new BorderLayout());
		statusBar.setBackground(ColorScheme.LIGHT_GRAY_COLOR);

		// results panel

		// teams panel

		// buttons column
	}
}
