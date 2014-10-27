package com.lucasmontec.textfighters.universe.objects;

import alientextgame.model.item.Item;

/**
 * An implementation of item that is also a crafted item
 * 
 * @author Lucas M Carvalhaes Zombie
 * 
 */
public class CraftedItem extends Item implements ICraftedItem {

	private Material	material;

	private float		materialAmount;

	public CraftedItem(String n, String d, Material material, float materialAmount) {
		super(n, d);
		setMaterial(material);
		setMaterialAmount(materialAmount);
	}

	@Override
	public Material getMaterial() {
		return material;
	}

	@Override
	public float getRequiredMaterialAmount() {
		return materialAmount;
	}

	public void setMaterialAmount(float materialAmount) {
		this.materialAmount = materialAmount;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

}
