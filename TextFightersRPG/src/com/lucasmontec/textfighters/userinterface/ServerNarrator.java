package com.lucasmontec.textfighters.userinterface;

import alientextgame.model.interfaces.INarrator;

import com.esotericsoftware.kryonet.Connection;
import com.lucasmontec.textfighters.userinterface.Networking.OUTPUT_MESSAGE;

public class ServerNarrator implements INarrator {

	/**
	 * The player connection
	 */
	private final Connection	connection;

	public ServerNarrator(Connection playerCon) {
		connection = playerCon;
	}

	@Override
	public void narrate(String text) {
		if (text != null && text.length() > 0)
			connection.sendTCP(OUTPUT_MESSAGE.make((text.replace("null", ""))));
	}

}
