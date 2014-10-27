package com.lucasmontec.textfighters.universe.objects;

import alientextgame.model.item.UsableItem;

/**
 * A item that was made from a material.
 * 
 * @author Lucas M Carvalhaes
 * 
 */
public abstract class CraftedUsableItem extends UsableItem implements ICraftedItem {

	/**
	 * The material that constitutes this item
	 */
	private final Material	material;
	/**
	 * The amount of material to make it
	 */
	private final float		requiredMaterialAmount;

	public CraftedUsableItem(String n, String d, String useTxt, int uss, Material material,
			float requiredMaterialAmount) {
		super(n, d, useTxt, uss);
		this.material = material;
		this.requiredMaterialAmount = requiredMaterialAmount;
	}

	/**
	 * @return The material this item is made of
	 */
	@Override
	public Material getMaterial() {
		return material;
	}

	/**
	 * @return How much of that material is required to make this item
	 */
	@Override
	public float getRequiredMaterialAmount() {
		return requiredMaterialAmount;
	}

}
