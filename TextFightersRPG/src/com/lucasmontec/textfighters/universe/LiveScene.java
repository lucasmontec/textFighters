package com.lucasmontec.textfighters.universe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import alientextgame.core.TaleDriver;
import alientextgame.core.graph.SceneNode;
import alientextgame.model.action.Action;
import alientextgame.model.general.Actor;
import alientextgame.model.general.Player;
import alientextgame.model.graph.ActionGraphStreamReader;
import alientextgame.model.graph.ActionNode;
import alientextgame.model.specific.triggers.TriggerSceneEntry;
import alientextgame.model.specific.triggers.TriggerSceneExit;

public class LiveScene extends SceneNode {

	/**
	 * Inner NPC system
	 */
	private final ArrayList<ActionNode>	actionGraphs	= new ArrayList<>();
	private final HashMap<Player, HashSet<ActionGraphStreamReader>>	installedReaders	= new HashMap<>();

	public LiveScene() {
	}

	public LiveScene(LiveHistory history) {
		history.registerLiveScene(this);

		// NPC system
		addTrigger(new TriggerSceneEntry("NPCTRIGGER_INSTALL", new Action() {
			@Override
			public void act(Player context, TaleDriver driver) {
				for (ActionNode n : actionGraphs) {
					ActionGraphStreamReader reader = new ActionGraphStreamReader(context, n);
					driver.getCommandProcessor().installStreamReader(reader);
					storeReaderForPlayer(context,reader);
				}
			}
		}));

		addTrigger(new TriggerSceneExit("NPCTRIGGER_REMOVE", new Action() {
			@Override
			public void act(Player context, TaleDriver driver) {
				HashSet<ActionGraphStreamReader> readers = installedReaders.get(context);
				for (ActionGraphStreamReader rd : readers) {
					rd.close();
				}
				readers.clear();
				installedReaders.put(context, null);
			}
		}));
	}

	private void storeReaderForPlayer(Player p, ActionGraphStreamReader reader) {
		HashSet<ActionGraphStreamReader> pset = installedReaders.get(p);
		if (pset == null) {
			pset = new HashSet<>();
		}
		pset.add(reader);
		installedReaders.put(p, pset);
	}

	/**
	 * Update the scene the player is in.
	 * Updates all actors
	 * 
	 * @param p
	 *            The player beeing updated
	 */
	public void update(Player p) {
		for (Actor a : actors) {
			if (a instanceof UpdateActor) {
				((UpdateActor) a).update(p);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see alientextgame.core.Scene#addActor(alientextgame.model.general.Actor)
	 */
	@Override
	public void addActor(Actor a) {
		if (a instanceof NPC) {
			if (((NPC) a).getActionGraph() != null) {
				ActionNode node = ((NPC) a).getActionGraph();
				actionGraphs.add(node);
			}
		}
		super.addActor(a);
	}

}
