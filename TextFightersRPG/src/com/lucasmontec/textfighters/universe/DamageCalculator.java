package com.lucasmontec.textfighters.universe;

import com.lucasmontec.textfighters.universe.objects.ICraftedItem;
import com.lucasmontec.textfighters.util.Util;

/**
 * 
 * @author Lucas M Carvalhaes
 * 
 */
public class DamageCalculator {

	/**
	 * Calculate an item damage based on how the item is composed.<br>
	 * The current formula is:<br>
	 * material amount*10 + material amount * <br>
	 * (Material hardness * 10f) -<br>
	 * (Material malleability * 2.2f)<br>
	 * 
	 * @param item
	 *            The crafted item to calculate
	 * @return How much damage the item does.
	 */
	public static float calculateCraftedItemDamage(ICraftedItem item) {
		return item.getRequiredMaterialAmount()
				* 10
				+ item.getRequiredMaterialAmount()
				* Util.clamp((item.getMaterial().getHardness() * 10f)
						- (item.getMaterial().getMalleability() * 2.2f), 0f, 1000f);
	}

}
