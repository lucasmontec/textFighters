package com.lucasmontec.textfighters.history;

import alientextgame.core.TaleDriver;
import alientextgame.model.action.Action;
import alientextgame.model.general.Player;
import alientextgame.model.graph.ActionNode;

import com.lucasmontec.textfighters.universe.NPC;

public class Bug extends NPC {

	public Bug() {
		setName("Bug");
		setDescription("A tiny annoying bug. Looks like a beetle with a big cricket back.");
		setDeadDescription("A smashed beetle-cricket bug.");
		setHealth(1);
	}

	@Override
	public ActionNode getActionGraph() {
		ActionNode start = new ActionNode();
		ActionNode node = new ActionNode(new Action() {
			@Override
			public void act(Player context, TaleDriver driver) {
				context.narrate("You smashed the bug.\n");
				setHealth(0);
			}
		}, "hit bug", "kill bug", "smash bug");
		start.addSon(node);
		return start;
	}

	@Override
	public void update(Player p) {
		if (p != null) {
			if (isAlive()) {
				if (Math.random() > 0.6f)
					speaknl(p, "Crieek! Crieek!");
			}
		}
	}

	@Override
	public boolean isActing() {
		return true;
	}

}
