package com.lucasmontec.textfighters.userinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.lucasmontec.textfighters.userinterface.Networking.INPUT_MESSAGE;
import com.lucasmontec.textfighters.userinterface.Networking.LOGIN;
import com.lucasmontec.textfighters.userinterface.Networking.LOGIN_RESPONSE;
import com.lucasmontec.textfighters.userinterface.Networking.LOGOUT;
import com.lucasmontec.textfighters.userinterface.Networking.OUTPUT_MESSAGE;

public class ClientUI extends JFrame {
	private static final long	serialVersionUID	= 1L;

	private final Client		client;
	private final JPanel		loginPanel			= new JPanel();
	private final JPanel		gamePanel			= new JPanel();

	private String				playerID			= "";
	private boolean				intentionalDisconnect	= false;

	/*
	 * CONTENT
	 */
	// Fields - Login
	private final JTextField	tf_ip				= new JTextField();
	private final JTextField	tf_pname			= new JTextField();
	private final JButton		btn_login			= new JButton("Connect");

	// Fields - game
	private final JTextArea		ta_narrator			= new JTextArea();
	private final JTextField	tf_userinput		= new JTextField();

	public ClientUI() {
		client = new Client();
		client.start();
		Networking.setup(client);
		client.addListener(new ClientListener());

		// Setup
		ta_narrator.setEditable(false);
		ta_narrator.setWrapStyleWord(true);
		ta_narrator.setColumns(25);
		ta_narrator.setMaximumSize(new Dimension(595, 450));
		tf_userinput.setPreferredSize(new Dimension(450, 30));
		tf_userinput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
					send();
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		makeGameInterface();
		makeLoginMenu();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				client.sendTCP(LOGOUT.make());
				intentionalDisconnect = true;
				client.close();
			}
		});

		// Make the frame
		assembleFrame();
	}

	/**
	 * Tries to connect to a server. 5s timeout.
	 * 
	 * @param IP
	 *            The server IP
	 * @param port
	 *            The server door
	 */
	public void connect(String IP) {
		try {
			client.connect(5000, IP, Networking.port);
		} catch (IOException e) {
			/*
			 * JOptionPane.showMessageDialog(
			 * null,
			 * "Message: " + e.getMessage(),
			 * "IO EXCEPTION",
			 * JOptionPane.ERROR_MESSAGE);
			 */
			e.printStackTrace();
		}
	}

	/**
	 * Shows the frame.
	 */
	public void display() {
		setVisible(true);
	}

	/**
	 * Creates the JFrame with all UI components.
	 */
	private void assembleFrame() {
		setSize(300, 200);
		setTitle(TextFighters.gameName);
		setLocationRelativeTo(null);

		// Add a content panel
		add(loginPanel);

		// Add the login menu
		switchToLoginMenu();

		// gameInterface();
	}

	/**
	 * Removes everthing from the frame and add the login menu stuff.
	 */
	private void makeLoginMenu() {

		// Set the layout
		loginPanel.setLayout(new GridLayout(5, 1, 5, 5));

		// Add the IP field
		loginPanel.add(new JLabel("Server IP:"));
		loginPanel.add(tf_ip);

		// Add the Player Name field
		loginPanel.add(new JLabel("Player name:"));
		loginPanel.add(tf_pname);

		// Add the connect button
		loginPanel.add(btn_login);

		// Connect button functonality
		final ClientUI self = this;
		btn_login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent rv) {
				self.connect(tf_ip.getText());
				btn_login.setEnabled(false);

				// Check if the connection was sucessfull
				if (!self.isConnected()) {
					JOptionPane.showMessageDialog(
							null,
							"Connection failed (TIMEDOUT) after 5s.",
							"Connection failed",
							JOptionPane.WARNING_MESSAGE);
					btn_login.setEnabled(true);
				}
			}
		});
	}

	public boolean isConnected() {
		return client.isConnected();
	}

	private void switchToLoginMenu() {
		// Set frame specifics
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		btn_login.setEnabled(true);

		setContentPane(loginPanel);
	}

	private void switchToGameInterface() {
		// Set frame specifics
		setResizable(true);
		setSize(600, 500);
		setLocationRelativeTo(null);

		setContentPane(gamePanel);
	}

	private void makeGameInterface() {
		// Layout
		gamePanel.setLayout(new BorderLayout());

		// Add fields
		JPanel south = new JPanel();
		south.setLayout(new FlowLayout());
		gamePanel.add(new JScrollPane(ta_narrator), BorderLayout.CENTER);
		south.add(tf_userinput);

		JButton send = new JButton("send");
		send.setMnemonic(KeyEvent.VK_ENTER);
		south.add(send);

		// Add the south panel
		gamePanel.add(south, BorderLayout.SOUTH);

		// Make it work
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				send();
			}
		});
	}

	private void send() {
		if (client.isConnected()) {
			client.sendTCP(INPUT_MESSAGE.make(playerID, tf_userinput.getText()));
			tf_userinput.setText("");
		} else {
			JOptionPane.showMessageDialog(
					null,
					"You lost connection to the server...",
					"LOST CONNECTION",
					JOptionPane.WARNING_MESSAGE);
			disconnect();
		}
	}

	private void disconnect() {
		playerID = "";
		switchToLoginMenu();
		btn_login.setEnabled(true);
	}

	/**
	 * Client talking!
	 * 
	 * @author Lucas The same as above
	 * 
	 */
	public class ClientListener extends Listener {

		@Override
		public void connected(Connection connection) {
			super.connected(connection);

			// Send a login message
			client.sendTCP(LOGIN.make(tf_pname.getText()));
		}

		@Override
		public void disconnected(Connection connection) {
			super.disconnected(connection);
			disconnect();
			if (!intentionalDisconnect)
				JOptionPane.showMessageDialog(
						null,
						"Lost connection to the server.",
						"Server shutdown",
						JOptionPane.WARNING_MESSAGE);
		}

		@Override
		public void received(Connection connection, Object object) {
			super.received(connection, object);

			// Check login response message
			if (object instanceof LOGIN_RESPONSE) {
				LOGIN_RESPONSE res = (LOGIN_RESPONSE) object;

				// Check success
				if (res.success) {
					// Store the player ID
					playerID = res.pid;

					// Change interface
					switchToGameInterface();
				} else {
					JOptionPane.showMessageDialog(
							null,
							"Login failed (Player probably already registered).",
							"Login failed",
							JOptionPane.WARNING_MESSAGE);
					connection.close();
					switchToLoginMenu();
					tf_pname.setText("");
				}
			}

			// Get server game messages
			if (object instanceof OUTPUT_MESSAGE) {
				OUTPUT_MESSAGE msg = (OUTPUT_MESSAGE) object;
				// Narrate
				ta_narrator.append(msg.data);
			}
		}

	}

}
