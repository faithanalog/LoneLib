package com.unknownloner.lonelib.math;

import java.nio.FloatBuffer;

public class Mat2 {
	
	protected final float m00;
	protected final float m01;
	protected final float m10;
	protected final float m11;
	
	public static final Mat2 ZERO;
	public static final Mat2 IDENTITY;
	static {
		ZERO = new Mat2();
		IDENTITY = new Mat2(new float[] {
				1, 0,
				0, 1,
		});
	}
	
	protected Mat2() {
		m00 = 0;
		m01 = 0;
		m10 = 0;
		m11 = 0;
	}
	
	public Mat2(Mat2 src) {
		m00 = src.m00;
		m01 = src.m01;
		m10 = src.m10;
		m11 = src.m11;
	}
	
	public Mat2(FloatBuffer src) {
		m00 = src.get();
		m01 = src.get();
		m10 = src.get();
		m11 = src.get();
	}
	
	/**
	 * Creates a Mat2 from 4 consecutive values in src
	 * @param src The data for matrix creation
	 */
	public Mat2(float[] src) {
		m00 = src[0];
		m01 = src[1];
		m10 = src[2];
		m11 = src[3];
	}
	
	public Mat2(float[] src, int offset) {
		m00 = src[offset++];
		m01 = src[offset++];
		m10 = src[offset++];
		m11 = src[offset];
	}
	
	public Mat2 copy() {
		return new Mat2(this);
	}
	
	public Mat2 store(FloatBuffer dest) {
		dest.put(m00);
		dest.put(m01);
		dest.put(m10);
		dest.put(m11);
		return this;
	}
	
	public Mat2 add(Mat2 right) {
		return add(this, right);
	}
	
	public Mat2 sub(Mat2 right) {
		return sub(this, right);
	}
	
	public Mat2 mul(Mat2 right) {
		return mul(this, right);
	}
	
	public Mat2 negate() {
		return negate(this);
	}
	
	public Mat2 transpose() {
		return transpose(this);
	}
	
	public static Mat2 add(Mat2 left, Mat2 right) {
		float[] dest = new float[4];
		dest[0] = left.m00 + right.m00;
		dest[1] = left.m01 + right.m01;
		dest[2] = left.m10 + right.m10;
		dest[3] = left.m11 + right.m11;
		return new Mat2(dest);
	}
	
	public static Mat2 sub(Mat2 left, Mat2 right) {
		float[] dest = new float[4];
		dest[0] = left.m00 - right.m00;
		dest[1] = left.m01 - right.m01;
		dest[2] = left.m10 - right.m10;
		dest[3] = left.m11 - right.m11;
		return new Mat2(dest);
	}
	
	public static Mat2 mul(Mat2 left, Mat2 right) {
		float[] dest = new float[4];
		dest[0] = left.m00 * right.m00 + left.m10 * right.m01;
		dest[1] = left.m00 * right.m10 + left.m10 * right.m11;
		dest[2] = left.m01 * right.m00 + left.m11 * right.m01;
		dest[3] = left.m01 * right.m10 + left.m11 * right.m11;
		return new Mat2(dest);
	}
	
	public static Mat2 negate(Mat2 src) {
		float[] dest = new float[4];
		dest[0] = -src.m00;
		dest[1] = -src.m01;
		dest[2] = -src.m10;
		dest[3] = -src.m11;
		return new Mat2(dest);
	}
	
	public static Mat2 transpose(Mat2 src) {
		float[] dest = new float[4];
		dest[0] = src.m00;
		dest[1] = src.m10;
		dest[2] = src.m01;
		dest[3] = src.m11;
		return new Mat2(dest);
	}
	
	public static float determinant(Mat2 src) {
		return (src.m00 * src.m11) - (src.m01 * src.m10);
	}

}
