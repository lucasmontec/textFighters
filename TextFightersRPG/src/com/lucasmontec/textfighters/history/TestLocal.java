/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lucasmontec.textfighters.history;

import java.util.Locale;

import alientextgame.AlienTextEngine;
import alientextgame.base.commandPacks.BaseCommands;
import alientextgame.base.commandPacks.BaseMasterCommands;
import alientextgame.base.commandPacks.ItemContainerCommands;
import alientextgame.core.TaleDriver;
import alientextgame.language.Language;
import alientextgame.model.general.Player;

/**
 * Just a testing bench using the swing simple input/output.
 * 
 * @author Lucas M Carvalhaes
 */
public class TestLocal extends javax.swing.JFrame {
	private static final long	serialVersionUID	= -1030799164830909650L;

	TaleDriver					driver;

	/**
	 * Creates new form GameWindow
	 */
	public TestLocal() {
		initComponents();
		setLocationRelativeTo(null);

		setTitle(AlienTextEngine.VERSION);
		Language.changeLanguage(Locale.US);

		Player player = new Player("Robson J. Coffe Swinger", InputArea, narrator);

		new TestMap();
		driver = new TaleDriver(TestMap.getHistory());
		driver.registerPlayer(player);

		// Add the master commands
		driver.getCommandProcessor().addToMasterPack(new BaseMasterCommands());
		//Add the base commands
		driver.getCommandProcessor().addPack(new BaseCommands());
		// Add inventory support
		driver.getCommandProcessor().addPack(new ItemContainerCommands());
	}

	private void initComponents() {

		jScrollPane2 = new javax.swing.JScrollPane();
		narrator = new alientextgame.swing.JTextAreaNarrator();
		InputArea = new alientextgame.swing.JTextFieldUserInput();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setBackground(new java.awt.Color(153, 153, 153));
		setResizable(false);

		narrator.setBackground(new java.awt.Color(0, 0, 0));
		narrator.setColumns(25);
		narrator.setEditable(false);
		narrator.setForeground(new java.awt.Color(0, 255, 0));
		narrator.setLineWrap(true);
		narrator.setRows(10);
		narrator.setWrapStyleWord(true);
		narrator.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
		jScrollPane2.setViewportView(narrator);

		InputArea.setBackground(new java.awt.Color(0, 0, 0));
		InputArea.setForeground(new java.awt.Color(0, 255, 0));
		InputArea.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		InputArea.setText("Type action and enter. 'Help' display actions.");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
								.addComponent(InputArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(InputArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				);

		pack();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/*
		 * Set the Nimbus look and feel
		 */
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(TestLocal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(TestLocal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(TestLocal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(TestLocal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new TestLocal().setVisible(true);
			}
		});
	}

	private alientextgame.swing.JTextFieldUserInput InputArea;
	private javax.swing.JScrollPane jScrollPane2;
	private alientextgame.swing.JTextAreaNarrator narrator;
}
