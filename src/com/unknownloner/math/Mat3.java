package com.unknownloner.math;

import java.nio.FloatBuffer;

public class Mat3 {
	
	protected float m00;
	protected float m01;
	protected float m02;
	protected float m10;
	protected float m11;
	protected float m12;
	protected float m20;
	protected float m21;
	protected float m22;
	
	public static final Mat3 ZERO;
	public static final Mat3 IDENTITY;
	
	static {
		ZERO = new Mat3();
		IDENTITY = new Mat3();
		IDENTITY.m00 = 1F;
		IDENTITY.m11 = 1F;
		IDENTITY.m22 = 1F;
	}
	
	protected Mat3() {
		
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
		Mat3 dest = new Mat3();
		dest.m00 = left.m00 + right.m00;
		dest.m01 = left.m01 + right.m01;
		dest.m02 = left.m02 + right.m02;
		dest.m10 = left.m10 + right.m10;
		dest.m11 = left.m11 + right.m11;
		dest.m12 = left.m12 + right.m12;
		dest.m20 = left.m20 + right.m20;
		dest.m21 = left.m21 + right.m21;
		dest.m22 = left.m22 + right.m22;
		return dest;
	}
	
	public static Mat3 sub(Mat3 left, Mat3 right) {
		Mat3 dest = new Mat3();
		dest.m00 = left.m00 - right.m00;
		dest.m01 = left.m01 - right.m01;
		dest.m02 = left.m02 - right.m02;
		dest.m10 = left.m10 - right.m10;
		dest.m11 = left.m11 - right.m11;
		dest.m12 = left.m12 - right.m12;
		dest.m20 = left.m20 - right.m20;
		dest.m21 = left.m21 - right.m21;
		dest.m22 = left.m22 - right.m22;
		return dest;
	}
	
	public static Mat3 mul(Mat3 left, Mat3 right) {
		Mat3 dest = new Mat3();
		dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02;
		dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12;
		dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22;
		
		dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02;
		dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12;
		dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22;
		
		dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02;
		dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12;
		dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22;
		return dest;
	}
	
	public static Mat3 negate(Mat3 src) {
		Mat3 dest = new Mat3();
		dest.m00 = -src.m00;
		dest.m01 = -src.m01;
		dest.m02 = -src.m02;
		dest.m10 = -src.m10;
		dest.m11 = -src.m11;
		dest.m12 = -src.m12;
		dest.m20 = -src.m20;
		dest.m21 = -src.m21;
		dest.m22 = -src.m22;
		return dest;
	}
	
	public static Mat3 transpose(Mat3 src) {
		Mat3 dest = new Mat3();
		dest.m00 = src.m00;
		dest.m01 = src.m10;
		dest.m02 = src.m20;
		
		dest.m10 = src.m01;
		dest.m11 = src.m11;
		dest.m12 = src.m21;
		
		dest.m20 = src.m02;
		dest.m21 = src.m12;
		dest.m22 = src.m22;
		return dest;
	}
	
	public static Mat3 mul(Mat3 src, float scalar) {
		Mat3 dest = src.copy();
		dest.m00 *= scalar;
		dest.m01 *= scalar;
		dest.m02 *= scalar;
		dest.m10 *= scalar;
		dest.m11 *= scalar;
		dest.m12 *= scalar;
		dest.m20 *= scalar;
		dest.m21 *= scalar;
		dest.m22 *= scalar;
		return dest;
	}
	
	public static float determinant(Mat3 src) {
		return ((src.m00 * src.m11 * src.m22) + (src.m10 * src.m21 * src.m02) + (src.m20 * src.m01 * src.m12))
				- ((src.m02 * src.m11 * src.m20) + (src.m12 * src.m21 * src.m00) + (src.m22 * src.m01 * src.m10));
	}
	
	public static float determinant(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
		return ((m00 * m11 * m22) + (m10 * m21 * m02) + (m20 * m01 * m12))
				- ((m02 * m11 * m20) + (m12 * m21 * m00) + (m22 * m01 * m10));
	}
	
	public static Mat3 toNormalMatrix(Mat4 model) {
		Mat4 mat = Mat4.transpose(Mat4.inverse(model));
		Mat3 ret = new Mat3();
		ret.m00 = mat.m00;
		ret.m01 = mat.m01;
		ret.m02 = mat.m02;
		ret.m10 = mat.m10;
		ret.m11 = mat.m11;
		ret.m12 = mat.m12;
		ret.m20 = mat.m20;
		ret.m21 = mat.m21;
		ret.m22 = mat.m22;
		return ret;
	}

}
