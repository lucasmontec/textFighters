package com.lucasmontec.textfighters.universe.commands;

import java.util.HashSet;

import alientextgame.base.commandPacks.InventoryCommands;
import alientextgame.base.contexts.BaseContexts;
import alientextgame.core.TaleDriver;
import alientextgame.core.TextCommand;
import alientextgame.core.processing.ActorMatcher;
import alientextgame.core.processing.Context;
import alientextgame.model.general.Actor;
import alientextgame.model.general.Item;
import alientextgame.model.general.ItemContainer;
import alientextgame.model.general.LiveActor;
import alientextgame.model.general.Player;
import alientextgame.model.interfaces.ICommandPack;
import alientextgame.util.Util;

import com.lucasmontec.textfighters.universe.DamageCalculator;
import com.lucasmontec.textfighters.universe.Model;
import com.lucasmontec.textfighters.universe.objects.CraftedItemContainer;
import com.lucasmontec.textfighters.universe.objects.ICraftedItem;

public class StandartContextCommandExpansion implements ICommandPack {

	@Override
	public HashSet<TextCommand> getCommands() {
		HashSet<TextCommand> pack = new HashSet<>();
		pack.add(new TextCommand(false, true, "GET @ GRAB",
				"type GET <ITEMNAME> or GRAB <ITEMNAME> to store an item in the player inventory.") {
			@Override
			public void call(Player caller, String[] args, TaleDriver driver) {
				String itemName = Util.reassembleArgs(args);
				Actor actor = ActorMatcher.matchSingle(itemName, driver.getHistory().getActiveScene(caller)
						.getActors(), true, true);

				if (actor != null && actor instanceof Item) {
					Item item = (Item) actor;

					// Get player inventory
					ItemContainer container = caller.getAttribute(ItemContainer.class, Model.inventory);

					if (container != null) {
						if (container.store(item)) {
							driver.getHistory().getActiveScene(caller).removeActor(item);
							caller.playerNarrator.narrate("Item stored.\n");
						} else {
							caller.playerNarrator.narrate("Can't store item.\n");
						}
					} else {
						caller.playerNarrator.narrate("You don't carry an inventory.\n");
					}
				} else {
					caller.playerNarrator.narrate("There's no such item.\n");
				}
			}
		});

		pack.add(new TextCommand(false, true, "ATTACK @ ATK",
				"Type ATTACK <TARGET> or ATK <TARGET> to use your left hand item as a weapon and attack the target.") {
			@Override
			public void call(Player caller, String[] args, TaleDriver driver) {
				if (args == null || args.length == 0) {
					caller.playerNarrator.narrate("This commands requires a target.");
					return;
				}

				// Lets find the target and see if its valid
				Actor target = driver.getHistory().getActiveScene(caller).macthActor(args[0]);

				// Lets see if this target is valid
				if (target instanceof LiveActor) {
					// Get the item at left hand
					ItemContainer rh = caller.getAttribute(ItemContainer.class, Model.slot_right_hand);

					// Check if the user is holding and item or is bare handed
					if (rh != null) {
						int damage = 0;
						Item atHand = rh.getItem(0);
						if (atHand != null) {
							if (atHand instanceof ICraftedItem) {
								damage = (int) DamageCalculator
										.calculateCraftedItemDamage((ICraftedItem) atHand);
							}
						} else {
							// Calculate damage using the slot
							if (rh instanceof CraftedItemContainer) {
								damage = (int) DamageCalculator.calculateCraftedItemDamage((ICraftedItem) rh);
								System.out.println(DamageCalculator
										.calculateCraftedItemDamage((ICraftedItem) rh));
							}
						}

						if (damage > 0) {
							((LiveActor) target).takeDamage(damage);
							caller.playerNarrator.narrate("You hit the " + target.getName() + " causing "
									+ damage + " damage.\n");
							if (target instanceof Player)
								((Player) target).playerNarrator.narrate("You were hit by "
										+ caller.getName() + " and took " + damage + " damage.\n");
						} else {
							caller.playerNarrator.narrate("You tried to hit the " + target.getName()
									+ " but managed to cause no damage.\n");
							if (target instanceof Player)
								((Player) target).playerNarrator.narrate("You were hit by "
										+ caller.getName() + " but he caused no damage.\n");
						}

					} else {
						caller.playerNarrator.narrate("You don't carry an inventory.\n");
					}
				}
			}
		});

		pack.add(new TextCommand(false, true, "INVENTORY @ INV",
				"Type INVENTORY or INV to access your local inventory.") {
			@Override
			public void call(Player caller, String[] args, TaleDriver driver) {
				// Get player inventory
				ItemContainer container = caller.getAttribute(ItemContainer.class, Model.inventory);

				if (container != null) {
					InventoryCommands.openContainer(caller, container);
					caller.playerNarrator.narrate("Inventory opened.\n");
				} else {
					caller.playerNarrator.narrate("You don't carry an inventory.\n");
				}
			}
		});
		return pack;
	}

	@Override
	public Context getContext() {
		// Adds to normal commands
		return BaseContexts.standart;
	}

}
