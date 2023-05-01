package dev.yequi.baraces;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BaRacesPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BaRacesPlugin.class);
		RuneLite.main(args);
	}
}