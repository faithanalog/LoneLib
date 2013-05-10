package com.unknownloner.lonelib.math;

import java.nio.FloatBuffer;

public class Mat3 {
	
	protected final float m00;
	protected final float m01;
	protected final float m02;
	protected final float m10;
	protected final float m11;
	protected final float m12;
	protected final float m20;
	protected final float m21;
	protected final float m22;
	
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
		m00 = 0;
		m01 = 0;
		m02 = 0;
		m10 = 0;
		m11 = 0;
		m12 = 0;
		m20 = 0;
		m21 = 0;
		m22 = 0;
	}
	
	public Mat3(Mat3 src) {
		m00 = src.m00;
		m01 = src.m01;
		m02 = src.m02;
		m10 = src.m10;
		m11 = src.m11;
		m12 = src.m12;
		m20 = src.m20;
		m21 = src.m21;
		m22 = src.m22;
	}
	
	public Mat3(FloatBuffer src) {
		m00 = src.get();
		m01 = src.get();
		m02 = src.get();
		m10 = src.get();
		m11 = src.get();
		m12 = src.get();
		m20 = src.get();
		m21 = src.get();
		m22 = src.get();
	}
	
	/**
	 * Creates a Mat3 from 9 consecutive values in src
	 * @param src The data for matrix creation
	 */
	public Mat3(float[] src) {
		m00 = src[0];
		m01 = src[1];
		m02 = src[2];
		m10 = src[3];
		m11 = src[4];
		m12 = src[5];
		m20 = src[6];
		m21 = src[7];
		m22 = src[8];
	}
	
	public Mat3(float[] src, int offset) {
		m00 = src[offset++];
		m01 = src[offset++];
		m02 = src[offset++];
		m10 = src[offset++];
		m11 = src[offset++];
		m12 = src[offset++];
		m20 = src[offset++];
		m21 = src[offset++];
		m22 = src[offset];
	}
	
	public Mat3 copy() {
		return new Mat3(this);
	}
	
	public Mat3 store(FloatBuffer dest) {
		dest.put(m00);
		dest.put(m01);
		dest.put(m02);
		dest.put(m10);
		dest.put(m11);
		dest.put(m12);
		dest.put(m20);
		dest.put(m21);
		dest.put(m22);
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
		dest[0] = left.m00 + right.m00;
		dest[1] = left.m01 + right.m01;
		dest[2] = left.m02 + right.m02;
		dest[3] = left.m10 + right.m10;
		dest[4] = left.m11 + right.m11;
		dest[5] = left.m12 + right.m12;
		dest[6] = left.m20 + right.m20;
		dest[7] = left.m21 + right.m21;
		dest[8] = left.m22 + right.m22;
		return new Mat3(dest);
	}
	
	public static Mat3 sub(Mat3 left, Mat3 right) {
		float[] dest = new float[9];
		dest[1] = left.m00 - right.m00;
		dest[2] = left.m01 - right.m01;
		dest[3] = left.m02 - right.m02;
		dest[4] = left.m10 - right.m10;
		dest[5] = left.m11 - right.m11;
		dest[6] = left.m12 - right.m12;
		dest[7] = left.m20 - right.m20;
		dest[8] = left.m21 - right.m21;
		dest[9] = left.m22 - right.m22;
		return new Mat3(dest);
	}
	
	public static Mat3 mul(Mat3 left, Mat3 right) {
		float[] dest = new float[9];
		dest[0] = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02;
		dest[1] = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02;
		dest[2] = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02;
		dest[3] = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12;
		dest[4] = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12;
		dest[5] = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12;
		dest[6] = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22;
		dest[7] = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22;
		dest[8] = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22;
		return new Mat3(dest);
	}
	
	public static Mat3 negate(Mat3 src) {
		float[] dest = new float[9];
		dest[0] = -src.m00;
		dest[1] = -src.m01;
		dest[2] = -src.m02;
		dest[3] = -src.m10;
		dest[4] = -src.m11;
		dest[5] = -src.m12;
		dest[6] = -src.m20;
		dest[7] = -src.m21;
		dest[8] = -src.m22;
		return new Mat3(dest);
	}
	
	public static Mat3 transpose(Mat3 src) {
		float[] dest = new float[9];
		dest[0] = src.m00;
		dest[1] = src.m10;
		dest[2] = src.m20;
		
		dest[3] = src.m01;
		dest[4] = src.m11;
		dest[5] = src.m21;
		
		dest[6] = src.m02;
		dest[7] = src.m12;
		dest[8] = src.m22;
		return new Mat3(dest);
	}
	
	public static Mat3 mul(Mat3 src, float scalar) {
		float[] dest = new float[9];
		dest[0] = src.m00 * scalar;
		dest[1] = src.m01 * scalar;
		dest[2] = src.m02 * scalar;
		
		dest[3] = src.m10 * scalar;
		dest[4] = src.m11 * scalar;
		dest[5] = src.m12 * scalar;
		
		dest[6] = src.m20 * scalar;
		dest[7] = src.m21 * scalar;
		dest[8] = src.m22 * scalar;
		return new Mat3(dest);
	}
	
	public static float determinant(Mat3 src) {
		return ((src.m00 * src.m11 * src.m22) + (src.m10 * src.m21 * src.m02) + (src.m20 * src.m01 * src.m12))
				- ((src.m02 * src.m11 * src.m20) + (src.m12 * src.m21 * src.m00) + (src.m22 * src.m01 * src.m10));
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
		dest[0] = mat.m00;
		dest[1] = mat.m01;
		dest[2] = mat.m02;
		dest[3] = mat.m10;
		dest[4] = mat.m11;
		dest[5] = mat.m12;
		dest[6] = mat.m20;
		dest[7] = mat.m21;
		dest[8] = mat.m23;
		return new Mat3(dest);
	}

}
