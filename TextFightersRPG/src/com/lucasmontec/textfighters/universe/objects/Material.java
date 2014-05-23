package com.lucasmontec.textfighters.universe.objects;

import com.lucasmontec.textfighters.util.Util;

/**
 * A material is a thing that composes things.<br>
 * hardness is measured from 0.0f to 1.0f.<br>
 * a 0.0f material is very soft. Soft and maleable materials are such
 * as leather or slik. Leather hardness is greater than silk.<br>
 * <br>
 * Malleability is how the material molds, or holds stretching.
 * a 0.0f malleable material, shatters easily. Though a 1.0f material
 * is very strecheable.<br>
 * 
 * @author Lucas M Carvalhaes
 * 
 */
public class Material {

	private String	name;
	/**
	 * This is how hard or soft the material.
	 */
	private float	hardness;
	/**
	 * This is how the material behaves when beeing molded.
	 */
	private float	malleability;
	/**
	 * A brief description of the materials properties
	 */
	private String	characteristics;

	public Material(String name, float hardness, float malleability, String characteristics) {
		super();
		this.name = name;
		this.hardness = Util.clamp(hardness, 0f, 1f);
		this.malleability = Util.clamp(malleability, 0f, 1f);
		this.characteristics = characteristics;
	}


	/*
	 * Automatic getters and setters
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getHardness() {
		return hardness;
	}

	public void setHardness(float hardness) {
		this.hardness = hardness;
	}

	public float getMalleability() {
		return malleability;
	}

	public void setMalleability(float malleability) {
		this.malleability = malleability;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

}
