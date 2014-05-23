package com.lucasmontec.textfighters.history;

import alientextgame.core.graph.GraphHistory;
import alientextgame.core.graph.SceneNode;

import com.lucasmontec.textfighters.history.things.Materials;
import com.lucasmontec.textfighters.universe.LiveHistory;
import com.lucasmontec.textfighters.universe.LiveScene;
import com.lucasmontec.textfighters.universe.objects.CraftedItem;

public class TestMap {

	public static SceneNode	startingNode;

	public static GraphHistory getHistory() {
		LiveHistory history = new LiveHistory(2000);

		// Starting node
		startingNode = new LiveScene(history);
		startingNode.addActor(new Bug());
		startingNode.addActor(new Bug());
		startingNode.addActor(new CraftedItem("Stone", "A 800g rock.", Materials.stone, 0.8f));
		startingNode.addActor(new CraftedItem("Leather ball",
				"A 1kg leather bag that is so destroyed is more like a ball.", Materials.leather, 1f));

		history.setInitialNode(startingNode);
		history.setHistoryHeader("Zombie", "Testing assets for the text fighters game (RPG)");

		return history;
	}

}
