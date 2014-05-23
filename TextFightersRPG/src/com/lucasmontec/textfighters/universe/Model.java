package com.lucasmontec.textfighters.universe;

import alientextgame.base.commandPacks.MultiUserCommands;
import alientextgame.core.TaleDriver;
import alientextgame.model.general.Player;

import com.lucasmontec.textfighters.history.things.Materials;
import com.lucasmontec.textfighters.universe.commands.InventoryContextCommandExpansion;
import com.lucasmontec.textfighters.universe.commands.StandartContextCommandExpansion;
import com.lucasmontec.textfighters.universe.objects.CraftedItemContainer;

/**
 * A helper class that setup the things.
 * 
 * @author Lucas M Carvalhaes
 * 
 */
public class Model {

	public static String	slot_left_hand	= "lefthand";
	public static String	slot_right_hand	= "righthand";
	public static String	inventory		= "inventory";

	/**
	 * Prepare the player for this game model
	 * 
	 * @param p
	 */
	public static void setupPlayer(Player p) {
		// Give the player a bag
		p.setAttribute(inventory, new CraftedItemContainer("Cheap leather bag",
				"A cheaply crafted leather bag", 5, false, Materials.leather, 1f));

		p.setAttribute(slot_left_hand, new CraftedItemContainer("hand", "A human hand.", 1, false,
				Materials.flesh, 0.25f));
		p.setAttribute(slot_right_hand, new CraftedItemContainer("hand", "A human hand.", 1, false,
				Materials.flesh, 0.25f));
	}

	/**
	 * Prepare the driver for this game model
	 * 
	 * @param driver
	 */
	public static void setupDriver(TaleDriver driver) {
		driver.getCommandProcessor().addPack(new StandartContextCommandExpansion());
		driver.getCommandProcessor().addPack(new InventoryContextCommandExpansion());
		driver.getCommandProcessor().addToMasterPack(new MultiUserCommands());
	}

}
