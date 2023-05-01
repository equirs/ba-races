package dev.yequi.baraces.panel;

import dev.yequi.baraces.phase.PhaseManager;
import javax.inject.Inject;
import javax.swing.JPanel;

public class BottomButtonsPanel extends JPanel
{
	private final PhaseManager phaseManager;

	@Inject
	public BottomButtonsPanel(PhaseManager phaseManager)
	{
		this.phaseManager = phaseManager;
	}
}
