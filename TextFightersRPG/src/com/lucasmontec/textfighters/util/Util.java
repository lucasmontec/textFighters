package com.lucasmontec.textfighters.util;

public class Util {

	public static float clamp(float val, float min, float max) {
		return Math.max(Math.min(val, max), min);
	}

}
