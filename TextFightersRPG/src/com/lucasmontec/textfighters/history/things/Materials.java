package com.lucasmontec.textfighters.history.things;

import com.lucasmontec.textfighters.universe.objects.Material;

/**
 * All game materials.
 * 
 * @author Lucas M Carvalhaes
 * 
 */
public abstract class Materials {

	/**
	 * Relativelly hard and with great maleabillity
	 */
	public static Material	iron	= new Material("iron", 0.6f, 0.7f,
			"A metal with a shine. Malleable. Relativelly hard, needs to be hot to mold.");

	public static Material	leather	= new Material("leather", 0.35f, 0.8f,
			"Made from animal skin. Soft enough to make clothing. Hard enough to make light armor.");

	public static Material	stone	= new Material("stone", 0.85f, 0.0f,
			"Not maleable, will break when hit hard. High hardness makes a great material for building.");

	public static Material	dirt	= new Material("dirt", 0.05f, 0.05f,
			"Neigther maleable or hard. Composes most of the worlds ground. Can be planted.");

	public static Material	flesh	= new Material("flesh", 0.28f, 0.76f,
			"Flesh and skin. Soft enough but can hit hard.");

}
