package com.lucasmontec.textfighters.universe.objects;

import alientextgame.model.item.ItemContainer;

public class CraftedItemContainer extends ItemContainer implements ICraftedItem {

	/**
	 * The material that constitutes this item
	 */
	private final Material	material;
	/**
	 * The amount of material to make it
	 */
	private final float		requiredMaterialAmount;

	/**
	 * 
	 * @param n
	 *            The item name
	 * @param d
	 *            The item description
	 * @param cap
	 *            The container capacity
	 * @param canSeeInside
	 *            If the user can see inside with look commands
	 * @param material
	 *            The material this item is made of
	 * @param requiredMaterialAmount
	 *            How much material it is required to make this container
	 */
	public CraftedItemContainer(String n, String d, int cap, boolean canSeeInside, Material material,
			float requiredMaterialAmount) {
		super(n, d, cap, canSeeInside);
		this.material = material;
		this.requiredMaterialAmount = requiredMaterialAmount;
	}

	@Override
	public Material getMaterial() {
		return material;
	}

	@Override
	public float getRequiredMaterialAmount() {
		return requiredMaterialAmount;
	}

}
