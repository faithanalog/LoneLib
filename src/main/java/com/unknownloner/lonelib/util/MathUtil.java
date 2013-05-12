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
	
	public static int nextPowerOfTwo(int v) {
		v--;
		v |= v >> 1;
	    v |= v >> 2;
	    v |= v >> 4;
	    v |= v >> 8;
	    v |= v >> 16;
	    v++;
	    return v;
	}
	
	public static int min(int... vals) {
		int min = vals[0];
		for(int i = 1; i < vals.length; i++) {
			if(vals[i] < min)
				min = vals[i];
		}
		return min;
	}
	
	public static int max(int... vals) {
		int max = vals[0];
		for(int i = 1; i < vals.length; i++) {
			if(vals[i] > max)
				max = vals[i];
		}
		return max;
	}
	
	public static float min(float... vals) {
		float min = vals[0];
		for(int i = 1; i < vals.length; i++) {
			if(vals[i] < min)
				min = vals[i];
		}
		return min;
	}
	
	public static float max(float... vals) {
		float max = vals[0];
		for(int i = 1; i < vals.length; i++) {
			if(vals[i] > max)
				max = vals[i];
		}
		return max;
	}
	
	public static double min(double... vals) {
		double min = vals[0];
		for(int i = 1; i < vals.length; i++) {
			if(vals[i] < min)
				min = vals[i];
		}
		return min;
	}
	
	public static double max(double... vals) {
		double max = vals[0];
		for(int i = 1; i < vals.length; i++) {
			if(vals[i] > max)
				max = vals[i];
		}
		return max;
	}

}
