package com.lucasmontec.textfighters.userinterface;

import alientextgame.core.TaleDriver;
import alientextgame.model.general.IUserInput;
import alientextgame.model.general.Player;

public class ServerInput implements IUserInput {

	private Player		p;
	private TaleDriver	driver;

	@Override
	public String getText() {
		return "";
	}

	@Override
	public void setText(String text) {
	}

	@Override
	public void install(Player owner, TaleDriver dv) {
		p = owner;
		driver = dv;
	}

	/**
	 * This is called from the server
	 */
	public void processFromClient(String line) {
		driver.processCommandLine(p, line);
	}

}
