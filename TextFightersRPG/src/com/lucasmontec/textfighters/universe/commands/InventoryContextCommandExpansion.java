package com.lucasmontec.textfighters.universe.commands;

import java.util.HashSet;

import alientextgame.base.contexts.BaseContexts;
import alientextgame.core.TaleDriver;
import alientextgame.core.TextCommand;
import alientextgame.core.processing.Context;
import alientextgame.model.general.ItemContainer;
import alientextgame.model.general.Player;
import alientextgame.model.interfaces.ICommandPack;
import alientextgame.util.Util;

import com.lucasmontec.textfighters.universe.Model;

public class InventoryContextCommandExpansion implements ICommandPack {

	@Override
	public HashSet<TextCommand> getCommands() {
		HashSet<TextCommand> pack = new HashSet<TextCommand>();
		pack.add(new TextCommand(false, true, "EQUIP @ EQP",
				"Type EQUIP <ITEMNAME> @ <SLOT> or EQP <ITEMNAME> @ <SLOT> to equip an item to a slot.\n"
						+ "Avaliable slots are: LEFTHAND, RIGHTHAND") {
			@Override
			public void call(Player caller, String[] args, TaleDriver driver) {
				String[] sptArgs = Util.getSeparatorArgs(args);

				String slot = "";
				String itemname = "";
				if (sptArgs.length == 2) {
					itemname = sptArgs[0];
					slot = sptArgs[1];
				} else {
					caller.playerNarrator.narrate("This command needs two arguments. See help EQUIP.\n");
					return;
				}

				ItemContainer res = null;

				// Get the slot
				if (Util.editDistance(slot.toLowerCase(), Model.slot_left_hand) < 2) {
					res = caller.getAttribute(ItemContainer.class, Model.slot_left_hand);
				}
				if (Util.editDistance(slot.toLowerCase(), Model.slot_right_hand) < 2) {
					res = caller.getAttribute(ItemContainer.class, Model.slot_right_hand);
				}

				if (res != null) {
					ItemContainer localInventory = caller.getAttribute(ItemContainer.class, Model.inventory);

					if (localInventory != null) {
						if (localInventory.transfer(itemname, res)) {
							caller.playerNarrator.narrate("Item equiped.\n");
						} else {
							caller.playerNarrator.narrate("Couldn't transfer the item.\n");
						}
					} else {
						caller.playerNarrator.narrate("You don't carry an inventory.\n");
					}
				} else {
					caller.playerNarrator.narrate("You don't have that slot.\n");
				}
			}
		});

		pack.add(new TextCommand(false, true, "DROPLEFTHAND @ DLH",
				"Type DROPLEFTHAND or DLH to drop the item equiped in the left hand to the inventory.") {
			@Override
			public void call(Player caller, String[] args, TaleDriver driver) {
				ItemContainer lh = caller.getAttribute(ItemContainer.class, Model.slot_left_hand);
				ItemContainer localInventory = caller.getAttribute(ItemContainer.class, Model.inventory);

				if (lh != null) {
					if (localInventory != null) {
						if (lh.getItem(0) != null) {
							if (lh.transfer(lh.getItem(0).name, localInventory)) {
								caller.playerNarrator.narrate("Item dropped.\n");
							} else {
								caller.playerNarrator.narrate("Couldn't transfer the item.\n");
							}
						} else {
							caller.playerNarrator.narrate("You don't have an item equiped.\n");
						}
					} else {
						caller.playerNarrator.narrate("You don't carry an inventory.\n");
					}
				} else {
					caller.playerNarrator.narrate("You don't have that slot.\n");
				}
			}
		});

		pack.add(new TextCommand(false, true, "DROPRIGHTHAND @ DRH",
				"Type DROPRIGHTHAND or DRH to drop the item equiped in the right hand to the inventory.") {
			@Override
			public void call(Player caller, String[] args, TaleDriver driver) {
				ItemContainer lh = caller.getAttribute(ItemContainer.class, Model.slot_right_hand);
				ItemContainer localInventory = caller.getAttribute(ItemContainer.class, Model.inventory);

				if (lh != null) {
					if (localInventory != null) {
						if (lh.getItem(0) != null) {
							if (lh.transfer(lh.getItem(0).name, localInventory)) {
								caller.playerNarrator.narrate("Item dropped.\n");
							} else {
								caller.playerNarrator.narrate("Couldn't transfer the item.\n");
							}
						} else {
							caller.playerNarrator.narrate("You don't have an item equiped.\n");
						}
					} else {
						caller.playerNarrator.narrate("You don't carry an inventory.\n");
					}
				} else {
					caller.playerNarrator.narrate("You don't have that slot.\n");
				}
			}
		});
		return pack;
	}

	@Override
	public Context getContext() {
		return BaseContexts.inventory;
	}

}
