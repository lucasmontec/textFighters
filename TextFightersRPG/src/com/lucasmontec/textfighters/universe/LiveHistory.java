package com.lucasmontec.textfighters.universe;


import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import alientextgame.core.graph.GraphHistory;
import alientextgame.model.general.Player;

/**
 * this game history model.
 * 
 * @author Lucas M Carvalhaes
 * 
 */
public class LiveHistory extends GraphHistory {

	private final HashSet<LiveScene>	liveScenes	= new HashSet<>();

	private final Timer					updater		= new Timer();
	private final boolean				updateOnlyScenesWithPlayers	= true;

	/**
	 * How many s take for a 'frame' to pass in this context
	 * 
	 * @param lifeDelay
	 */
	public LiveHistory(int lifeDelay) {

		// Creates an updater that update everything simulating 'time'
		// The timestep is fixed.
		updater.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				updateAllLivingScenes();
			}
		}, lifeDelay, lifeDelay);
	}

	/**
	 * Inner. This makes everyting alive
	 */
	private void updateAllLivingScenes() {
		if (getDriver() == null) {
			return;
		}

		if (updateOnlyScenesWithPlayers) {
			// COOL
			for (Player p : getDriver().players) {
				Object pos = p.getAttribute(Player.positionPropertyKey);
				if (pos != null && pos instanceof LiveScene) {
					((LiveScene) pos).update(p);
				}
			}
		} else {

			// UGLY
			for (LiveScene scene : liveScenes) {
				Player inScene = null;
				for (Player p : getDriver().players) {
					Object pos = p.getAttribute(Player.positionPropertyKey);
					if (pos != null && pos.equals(scene)) {
						inScene = p;
						break;
					}
				}
				if (scene != null)
					scene.update(inScene);
			}
		}
	}

	/**
	 * Live scenes call this automatically.
	 * 
	 * @param node
	 *            The scne to register
	 */
	public void registerLiveScene(LiveScene node) {
		liveScenes.add(node);
	}

	@Override
	public Class<?> getPositionClass() {
		return LiveScene.class;
	}

	@Override
	public void setupPlayer(Player p) {
		super.setupPlayer(p);
		Model.setupPlayer(p);
	}

}
