package com.lucasmontec.textfighters.universe;

import alientextgame.model.general.LiveActor;
import alientextgame.model.general.Player;

public abstract class UpdateActor extends LiveActor {

	public abstract void update(Player p);

	/**
	 * @return the alive
	 */
	public boolean isAlive() {
		return health > 0;
	}

}
