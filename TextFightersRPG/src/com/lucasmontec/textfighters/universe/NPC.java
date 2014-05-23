package com.lucasmontec.textfighters.universe;

import alientextgame.model.general.Player;
import alientextgame.model.graph.ActionNode;

/**
 * Just an actor that updates it self when {@link LiveScene} updates.
 * Also installs a action graph in {@link LiveScene} for players automatically.
 * 
 * @author Lucas M Carvalhaes
 * 
 */
public abstract class NPC extends UpdateActor {

	private String name;
	private String description;
	private String	deadDescription;

	public NPC(String name, String description, String deadDescription) {
		super();
		this.name = name;
		this.description = description;
		this.deadDescription = deadDescription;
	}

	public abstract ActionNode getActionGraph();

	public NPC() {
		super();
	}

	protected void speaknl(Player p, String s) {
		speak(p, s + "\n");
	}

	protected void speak(Player p, String s) {
		p.playerNarrator.narrate(name + ": " + s + "\n");
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public String lookText() {
		return (isAlive() ? description : deadDescription);
	}

	/**
	 * To show when the npc is dead (look command)
	 * 
	 * @return the deadDescription
	 */
	public String getDeadDescription() {
		return deadDescription;
	}

	/**
	 * @param deadDescription
	 *            the deadDescription to set
	 */
	public void setDeadDescription(String deadDescription) {
		this.deadDescription = deadDescription;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
