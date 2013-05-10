package com.unknownloner.lonelib.math;

import java.nio.FloatBuffer;

public class Mat2 {

	private float[] data;

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
		data = new float[] { 0, 0, 0, 0 };
	}

	public Mat2(Mat2 src) {
		data = new float[4];
		System.arraycopy(src.data, 0, data, 0, data.length);
	}

	public Mat2(FloatBuffer src) {
		data = new float[4];
		for (int i = 0; i < 4; i++) {
			data[i] = src.get();
		}
	}

	/**
	 * Creates a Mat2 from 4 consecutive values in src
	 * @param src The data for matrix creation
	 */
	public Mat2(float[] src) {
		this(src, false);
	}

	public Mat2(float[] src, boolean copy) {
		if (copy) {
			data = new float[4];
			System.arraycopy(src, 0, data, 0, data.length);
		} else {
			data = src;
		}
	}

	public Mat2(float[] src, int offset) {
		data = new float[4];
		System.arraycopy(src, offset, data, 0, data.length);
	}

	/**
	 * Returns a copy of the Matrix data as an array.
	 * @return copy of data
	 */
	public float[] toArray() {
		return data.clone();
	}

	/**
	 * return the raw Matrix data, changes to the array will be reflected in the matrix.
	 * @return data array
	 */
	public float[] getData() {
		return data;
	}


	public Mat2 copy() {
		return new Mat2(this);
	}

	public Mat2 store(FloatBuffer dest) {
		dest.put(data);
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
		dest[0] = left.data[0] + right.data[0];
		dest[1] = left.data[1] + right.data[1];
		dest[2] = left.data[2] + right.data[2];
		dest[3] = left.data[3] + right.data[3];
		return new Mat2(dest);
	}

	public static Mat2 sub(Mat2 left, Mat2 right) {
		float[] dest = new float[4];
		dest[0] = left.data[0] - right.data[0];
		dest[1] = left.data[1] - right.data[1];
		dest[2] = left.data[2] - right.data[2];
		dest[3] = left.data[3] - right.data[3];
		return new Mat2(dest);
	}

	public static Mat2 mul(Mat2 left, Mat2 right) {
		float[] dest = new float[4];
		dest[0] = left.data[0] * right.data[0] + left.data[2] * right.data[1];
		dest[1] = left.data[0] * right.data[2] + left.data[2] * right.data[3];
		dest[2] = left.data[1] * right.data[0] + left.data[3] * right.data[1];
		dest[3] = left.data[1] * right.data[2] + left.data[3] * right.data[3];
		return new Mat2(dest);
	}

	public static Mat2 negate(Mat2 src) {
		float[] dest = new float[4];
		dest[0] = -src.data[0];
		dest[1] = -src.data[1];
		dest[2] = -src.data[2];
		dest[3] = -src.data[3];
		return new Mat2(dest);
	}

	public static Mat2 transpose(Mat2 src) {
		float[] dest = new float[4];
		dest[0] = src.data[0];
		dest[1] = src.data[2];
		dest[2] = src.data[1];
		dest[3] = src.data[3];
		return new Mat2(dest);
	}

	public static float determinant(Mat2 src) {
		return (src.data[0] * src.data[3]) - (src.data[1] * src.data[2]);
	}

}
