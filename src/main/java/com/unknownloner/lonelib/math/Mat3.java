package com.unknownloner.lonelib.math;

import java.nio.FloatBuffer;

public class Mat3 {

	private final float[] data;

	public static final Mat3 ZERO;
	public static final Mat3 IDENTITY;

	static {
		ZERO = new Mat3();
		IDENTITY = new Mat3(new float[] {
				1, 0, 0,
				0, 1, 0,
				0, 0, 1,
		});
	}

	protected Mat3() {
		data = new float[] { 0, 0, 0, 
				0, 0, 0, 
				0, 0, 0};
	}

	public Mat3(Mat3 src) {
		data = new float[9];
		System.arraycopy(src.data, 0, data, 0, data.length);
	}

	public Mat3(FloatBuffer src) {
		data = new float[9];
		for (int i = 0; i < 9; i++) {
			data[i] = src.get();
		}
	}

	public Mat3(float[] src) {
		this(src, false);
	}

	/**
	 * Creates a Mat3 from 9 consecutive values in src
	 * @param src The data for matrix creation
	 */
	public Mat3(float[] src, boolean copy) {
		if (copy) {
			data = new float[9];
			System.arraycopy(src, 0, data, 0, data.length);
		} else {
			data = src;
		}
	}

	public Mat3(float[] src, int offset) {
		data = new float[9];
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

	public Mat3 copy() {
		return new Mat3(this);
	}

	public Mat3 store(FloatBuffer dest) {
		dest.put(data);
		return this;
	}

	public Mat3 add(Mat3 right) {
		return add(this, right);
	}

	public Mat3 sub(Mat3 right) {
		return sub(this, right);
	}

	public Mat3 mul(Mat3 right) {
		return mul(this, right);
	}

	public Mat3 negate() {
		return negate(this);
	}

	public Mat3 transpose() {
		return transpose(this);
	}

	public static Mat3 add(Mat3 left, Mat3 right) {
		float[] dest = new float[9];
		dest[0] = left.data[0] + right.data[0];
		dest[1] = left.data[1] + right.data[1];
		dest[2] = left.data[2] + right.data[2];
		dest[3] = left.data[3] + right.data[3];
		dest[4] = left.data[4] + right.data[4];
		dest[5] = left.data[5] + right.data[5];
		dest[6] = left.data[6] + right.data[6];
		dest[7] = left.data[7] + right.data[7];
		dest[8] = left.data[8] + right.data[8];
		return new Mat3(dest);
	}

	public static Mat3 sub(Mat3 left, Mat3 right) {
		float[] dest = new float[9];
		dest[1] = left.data[0] - right.data[0];
		dest[2] = left.data[1] - right.data[1];
		dest[3] = left.data[2] - right.data[2];
		dest[4] = left.data[3] - right.data[3];
		dest[5] = left.data[4] - right.data[4];
		dest[6] = left.data[5] - right.data[5];
		dest[7] = left.data[6] - right.data[6];
		dest[8] = left.data[7] - right.data[7];
		dest[9] = left.data[8] - right.data[8];
		return new Mat3(dest);
	}

	public static Mat3 mul(Mat3 left, Mat3 right) {
		float[] dest = new float[9];
		dest[0] = left.data[0] * right.data[0] + left.data[3] * right.data[1] + left.data[6] * right.data[2];
		dest[1] = left.data[1] * right.data[0] + left.data[4] * right.data[1] + left.data[7] * right.data[2];
		dest[2] = left.data[2] * right.data[0] + left.data[5] * right.data[1] + left.data[8] * right.data[2];
		dest[3] = left.data[0] * right.data[3] + left.data[3] * right.data[4] + left.data[6] * right.data[5];
		dest[4] = left.data[1] * right.data[3] + left.data[4] * right.data[4] + left.data[7] * right.data[5];
		dest[5] = left.data[2] * right.data[3] + left.data[5] * right.data[4] + left.data[8] * right.data[5];
		dest[6] = left.data[0] * right.data[6] + left.data[3] * right.data[7] + left.data[6] * right.data[8];
		dest[7] = left.data[1] * right.data[6] + left.data[4] * right.data[7] + left.data[7] * right.data[8];
		dest[8] = left.data[2] * right.data[6] + left.data[5] * right.data[7] + left.data[8] * right.data[8];
		return new Mat3(dest);
	}

	public static Mat3 negate(Mat3 src) {
		float[] dest = new float[9];
		dest[0] = -src.data[0];
		dest[1] = -src.data[1];
		dest[2] = -src.data[2];
		dest[3] = -src.data[3];
		dest[4] = -src.data[4];
		dest[5] = -src.data[5];
		dest[6] = -src.data[6];
		dest[7] = -src.data[7];
		dest[8] = -src.data[8];
		return new Mat3(dest);
	}

	public static Mat3 transpose(Mat3 src) {
		float[] dest = new float[9];
		dest[0] = src.data[0];
		dest[1] = src.data[3];
		dest[2] = src.data[6];

		dest[3] = src.data[1];
		dest[4] = src.data[4];
		dest[5] = src.data[7];

		dest[6] = src.data[2];
		dest[7] = src.data[5];
		dest[8] = src.data[8];
		return new Mat3(dest);
	}

	public static Mat3 mul(Mat3 src, float scalar) {
		float[] dest = new float[9];
		dest[0] = src.data[0] * scalar;
		dest[1] = src.data[1] * scalar;
		dest[2] = src.data[2] * scalar;

		dest[3] = src.data[3] * scalar;
		dest[4] = src.data[4] * scalar;
		dest[5] = src.data[5] * scalar;

		dest[6] = src.data[6] * scalar;
		dest[7] = src.data[7] * scalar;
		dest[8] = src.data[8] * scalar;
		return new Mat3(dest);
	}

	public static float determinant(Mat3 src) {
		return ((src.data[0] * src.data[4] * src.data[8]) + (src.data[3] * src.data[7] * src.data[2]) + (src.data[6] * src.data[1] * src.data[5]))
				- ((src.data[2] * src.data[4] * src.data[6]) + (src.data[5] * src.data[7] * src.data[0]) + (src.data[8] * src.data[1] * src.data[3]));
	}

	public static float determinant(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
		return ((m00 * m11 * m22) + (m10 * m21 * m02) + (m20 * m01 * m12))
				- ((m02 * m11 * m20) + (m12 * m21 * m00) + (m22 * m01 * m10));
	}

	/**
	 * Generates a Normal matrix from the given model matrix,
	 * which is used to rotate the with the rotation defined by the model
	 * while keeping them as normals. (Look up Normal matrices on google for more info)
	 * @param model The input model matrix
	 * @return
	 */
	public static Mat3 toNormalMatrix(Mat4 model) {
		Mat4 mat = Mat4.transpose(Mat4.inverse(model));
		float[] dest = new float[9];
		dest[0] = mat.getData()[0]; // m00
		dest[1] = mat.getData()[1]; // m01
		dest[2] = mat.getData()[2]; // m02
		dest[3] = mat.getData()[4]; // m10
		dest[4] = mat.getData()[5]; // m11
		dest[5] = mat.getData()[6]; // m12
		dest[6] = mat.getData()[8]; // m20
		dest[7] = mat.getData()[9]; // m21
		dest[8] = mat.getData()[10]; // this one was referencing m23 - typo?
		return new Mat3(dest);
	}

}
