package com.lucasmontec.textfighters.universe.objects;

/**
 * A item that was made from a material.
 * 
 * @author Lucas M Carvalhaes
 * 
 */
public interface ICraftedItem {

	/**
	 * @return The material this item is made of
	 */
	public Material getMaterial();

	/**
	 * @return How much of that material is required to make this item (KG)
	 */
	public float getRequiredMaterialAmount();

}
