package com.lucasmontec.textfighters.userinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

import alientextgame.core.History;
import alientextgame.core.TaleDriver;
import alientextgame.model.general.Player;
import alientextgame.util.SetupUtil;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.lucasmontec.textfighters.universe.Model;
import com.lucasmontec.textfighters.userinterface.Networking.INPUT_MESSAGE;
import com.lucasmontec.textfighters.userinterface.Networking.LOGIN;
import com.lucasmontec.textfighters.userinterface.Networking.LOGIN_RESPONSE;
import com.lucasmontec.textfighters.userinterface.Networking.LOGOUT;

/**
 * The game server!
 * 
 * @author Lucas M Carvalhaes
 * 
 */
public class ServerTextFighters {

	private final Server	server;
	private final JTextArea		console;

	private final TaleDriver				serverDriver;

	private final HashMap<String, Player>	players	= new HashMap<>();
	private final HashMap<Integer, String>	connectionIDtoPlayerID	= new HashMap<>();
	private final ArrayList<String>			names	= new ArrayList<>();

	/**
	 * Setting this to true will enable the server auto log.
	 */
	public boolean			log	= false;

	public ServerTextFighters(History h) {
		this(null, h);
	}

	/**
	 * Initialize the server with an external console.
	 * 
	 * @param econsole
	 */
	public ServerTextFighters(JTextArea econsole, History h) {
		console = econsole;
		serverDriver = new TaleDriver(h);
		SetupUtil.defaultSetup(serverDriver);
		Model.setupDriver(serverDriver);
		console.setColumns(16);
	}

	{
		// Intantiate the server
		server = new Server();
		server.addListener(new ServerListener());
		Networking.setup(server);
	}

	/**
	 * Starts the server and binds it
	 */
	public void start() {
		try {
			server.start();
			server.bind(Networking.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stops the server
	 */
	public void close() {
		server.stop();
	}

	/**
	 * Log stuff either to a external console or to the system console
	 * 
	 * @param s
	 *            Thing to log
	 */
	public void log(String s) {
		if (log) {
			if (console == null) {
				System.out.println(s);
			} else {
				console.append(s + "\n");
			}
		}
	}

	/**
	 * Registers a player at the driver and internal controler.<br>
	 * Name must be unique.
	 * 
	 * @param playerName
	 *            The name of the player
	 * @param con
	 *            The connection asking for register
	 * @return The new player ID or -1 if invalid
	 */
	public String registerPlayer(String playerName, Connection con) {
		if (names.contains(playerName))
			return null;

		// regsiter both a input and a output
		names.add(playerName);
		Player newPlayer = new Player(playerName, new ServerInput(), new ServerNarrator(con));
		players.put(newPlayer.ID, newPlayer);
		connectionIDtoPlayerID.put(con.getID(), newPlayer.ID);
		String registermsg = serverDriver.registerPlayer(newPlayer);

		// Log
		log("Registering " + newPlayer);
		if (registermsg != null) {
			log("Error registering: " + registermsg);
		}

		return newPlayer.ID;
	}

	public void removePlayer(String pid) {
		Player rem = players.get(pid);
		players.remove(rem);
		serverDriver.removePlayer(rem);
	}

	public class ServerListener extends Listener {

		@Override
		public void connected(Connection connection) {
			super.connected(connection);
			log("A user connected.");
		}

		@Override
		public void disconnected(Connection connection) {
			super.disconnected(connection);
			log("A user disconnected.");
			//Find who disconnected and remove the bastard
			String pid = connectionIDtoPlayerID.get(connection.getID());
			removePlayer(pid);
		}

		@Override
		public void received(Connection connection, Object object) {
			super.received(connection, object);

			// Log all non keep alive activities
			if (!(object instanceof KeepAlive))
				log("Received " + object);

			// Check login response message
			if (object instanceof LOGIN) {
				LOGIN login = (LOGIN) object;

				// Check if the player is already registered
				String registeredID = registerPlayer(login.pname, connection);
				if (registeredID == null) {
					connection.sendTCP(LOGIN_RESPONSE.makeFail());
				} else {
					connection.sendTCP(LOGIN_RESPONSE.makeSucess(registeredID));
				}
			}

			// Input from player
			if (object instanceof INPUT_MESSAGE) {
				INPUT_MESSAGE input = (INPUT_MESSAGE) object;

				// Get the player server input
				ServerInput sinput = (ServerInput) players.get(input.pid).playerInput;
				sinput.processFromClient(input.data);
			}

			// Lgout msg
			if (object instanceof LOGOUT) {
				removePlayer(connectionIDtoPlayerID.get(connection.getID()));
			}
		}
	}
}
