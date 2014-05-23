package com.lucasmontec.textfighters.userinterface;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.lucasmontec.textfighters.history.TestMap;

public class ServerUI extends JFrame {
	private static final long	serialVersionUID	= 1L;


	ServerTextFighters			server;
	JTextArea					console				= new JTextArea();

	public ServerUI() {
		// Set frame specifics
		setSize(380, 250);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Colors
		console.setBackground(Color.black);
		console.setForeground(Color.green);

		add(new JScrollPane(console));

		new TestMap();
		// Just a random history
		server = new ServerTextFighters(console, TestMap.getHistory());
		server.start();
		server.log = true;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				server.close();
			}
		});
	}

	public static void main(String... args) {
		new ServerUI();
	}
}
