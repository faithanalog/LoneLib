package com.unknownloner.lonelib.util;


public class MathUtil {
	
	public static float PI = (float)Math.PI;
	public static float PI180 = PI / 180f;
	public static float PI360 = PI / 360f;
	
	public static float toDegrees(float radians) {
		return radians * 180 / PI;
	}
	
	public static float toRadians(float degrees) {
		return degrees * PI180;
	}
	
	public static short[] quadToTri(short[] inds) {
		short[] tri = new short[inds.length * 6 / 4];
		for(int i = 0, j = 0; i < inds.length; i += 4, j += 6) {
			tri[j] = inds[i];
			tri[j + 1] = inds[i + 1];
			tri[j + 2] = inds[i + 2];
			tri[j + 3] = inds[i + 2];
			tri[j + 4] = inds[i + 3];
			tri[j + 5] = inds[i];
		}
		return tri;
	}

}
