package com.unknownloner.lonelib.math;

import java.nio.FloatBuffer;

public class Mat4 {
	
	/*
	 * Matrix Visualized
	 * m00 m10 m20 m30
	 * m01 m11 m21 m31
	 * m02 m12 m22 m32
	 * m03 m13 m23 m33
	 */
	
	protected final float m00;
	protected final float m01;
	protected final float m02;
	protected final float m03;
	protected final float m10;
	protected final float m11;
	protected final float m12;
	protected final float m13;
	protected final float m20;
	protected final float m21;
	protected final float m22;
	protected final float m23;
	protected final float m30;
	protected final float m31;
	protected final float m32;
	protected final float m33;
	
	public static final Mat4 ZERO;
	public static final Mat4 IDENTITY;
	
	static {
		ZERO = new Mat4();
		IDENTITY = new Mat4(new float[] {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1,
		});
	}
	
	/**
	 * Creates a new zeroed Mat4
	 * <br>This is protected because people should use Mat4.ZERO for zero
	 * and Mat4.IDENTITY for the identity.
	 * <br>This is more efficient because of the immutable nature of matrices
	 */
	protected Mat4() {
		m00 = 0;
		m01 = 0;
		m02 = 0;
		m03 = 0;
		m10 = 0;
		m11 = 0;
		m12 = 0;
		m13 = 0;
		m20 = 0;
		m21 = 0;
		m22 = 0;
		m23 = 0;
		m30 = 0;
		m31 = 0;
		m32 = 0;
		m33 = 0;
	}
	
	/**
	 * Creates a copy of src. Equivelant to src.copy()
	 * @param src
	 */
	public Mat4(Mat4 src) {
		m00 = src.m00;
		m01 = src.m01;
		m02 = src.m02;
		m03 = src.m03;
		m10 = src.m10;
		m11 = src.m11;
		m12 = src.m12;
		m13 = src.m13;
		m20 = src.m20;
		m21 = src.m21;
		m22 = src.m22;
		m23 = src.m23;
		m30 = src.m30;
		m31 = src.m31;
		m32 = src.m32;
		m33 = src.m33;
	}
	
	/**
	 * Creates a Mat4 from the next 16 float values in src
	 * Data must be in Column-major order
	 * @param src The data for matrix creation
	 */
	public Mat4(FloatBuffer src) {
		m00 = src.get();
		m01 = src.get();
		m02 = src.get();
		m03 = src.get();
		m10 = src.get();
		m11 = src.get();
		m12 = src.get();
		m13 = src.get();
		m20 = src.get();
		m21 = src.get();
		m22 = src.get();
		m23 = src.get();
		m30 = src.get();
		m31 = src.get();
		m32 = src.get();
		m33 = src.get();
	}
	
	/**
	 * Creates a Mat4 from 16 consecutive values in src
	 * @param src The data for matrix creation
	 */
	public Mat4(float[] src) {
		m00 = src[0];
		m01 = src[1];
		m02 = src[2];
		m03 = src[3];
		m10 = src[4];
		m11 = src[5];
		m12 = src[6];
		m13 = src[7];
		m20 = src[8];
		m21 = src[9];
		m22 = src[10];
		m23 = src[11];
		m30 = src[12];
		m31 = src[13];
		m32 = src[14];
		m33 = src[15];
	}
	
	/**
	 * Creates a Mat4 from 16 consecutive values in src
	 * @param src The data for matrix creation
	 * @param offset offset into the array to pull values from, Leave as 0 to pull values 0-15
	 */
	public Mat4(float[] src, int offset) {
		m00 = src[offset++];
		m01 = src[offset++];
		m02 = src[offset++];
		m03 = src[offset++];
		m10 = src[offset++];
		m11 = src[offset++];
		m12 = src[offset++];
		m13 = src[offset++];
		m20 = src[offset++];
		m21 = src[offset++];
		m22 = src[offset++];
		m23 = src[offset++];
		m30 = src[offset++];
		m31 = src[offset++];
		m32 = src[offset++];
		m33 = src[offset];
	}
	
	public Mat4 copy() {
		return new Mat4(this);
	}
	
	/**
	 * Stores this matrix in a FloatBuffer in column-major order
	 * @param dest
	 * @return this
	 */
	public Mat4 store(FloatBuffer dest) {
		dest.put(m00);
		dest.put(m01);
		dest.put(m02);
		dest.put(m03);
		dest.put(m10);
		dest.put(m11);
		dest.put(m12);
		dest.put(m13);
		dest.put(m20);
		dest.put(m21);
		dest.put(m22);
		dest.put(m23);
		dest.put(m30);
		dest.put(m31);
		dest.put(m32);
		dest.put(m33);
		return this;
	}
	
	/**
	 * Stores this matrix in a float array in column-major order starting at element 0
	 * @param dest
	 * @return this
	 */
	public Mat4 store(float[] dest) {
		dest[0] = m00;
		dest[1] = m01;
		dest[2] = m02;
		dest[3] = m03;
		dest[4] = m10;
		dest[5] = m11;
		dest[6] = m12;
		dest[7] = m13;
		dest[8] = m20;
		dest[9] = m21;
		dest[10] = m22;
		dest[11] = m23;
		dest[12] = m30;
		dest[13] = m31;
		dest[14] = m32;
		dest[15] = m33;
		return this;
	}
	
	/**
	 * Stores this matrix in a float array in column-major order starting at element offset
	 * @param dest
	 * @return this
	 */
	public Mat4 store(float[] dest, int offset) {
		dest[offset++] = m00;
		dest[offset++] = m01;
		dest[offset++] = m02;
		dest[offset++] = m03;
		dest[offset++] = m10;
		dest[offset++] = m11;
		dest[offset++] = m12;
		dest[offset++] = m13;
		dest[offset++] = m20;
		dest[offset++] = m21;
		dest[offset++] = m22;
		dest[offset++] = m23;
		dest[offset++] = m30;
		dest[offset++] = m31;
		dest[offset++] = m32;
		dest[offset++] = m33;
		return this;
	}
	
	/**
	 * Adds each value in this matrix to the corresponding value in right
	 * @param right The value to add
	 * @return this + right
	 */
	public Mat4 add(Mat4 right) {
		return add(this, right);
	}
	
	/**
	 * Subtracts each value in right from the corresponding value in this matrix
	 * @param right The value to subtract
	 * @return this - right
	 */
	public Mat4 sub(Mat4 right) {
		return sub(this, right);
	}
	
	/**
	 * Multiplies this matrix by another matrix, with this matrix as the left argument
	 * @param right The right side of the multiplication
	 * @return this * right
	 */
	public Mat4 mul(Mat4 right) {
		return mul(this, right);
	}
	
	/**
	 * Negates every value in this matrix
	 * @return -this
	 */
	public Mat4 negate() {
		return negate(this);
	}
	
	public Mat4 transpose() {
		return transpose(this);
	}
	
	public Mat4 translate(Vec3 dist) {
		return translate(dist, this);
	}
	
	public Mat4 scale(Vec3 scaleFactor) {
		return scale(scaleFactor, this);
	}
	
	public Mat4 rotate(float radians, Vec3 axis) {
		return rotate(radians, axis, this);
	}
	
	public Vec3 transform(Vec3 toTransform) {
		return transform(this, toTransform);
	}
	
	public Vec4 transform(Vec4 toTransform) {
		return transform(this, toTransform);
	}
	
	@Override
	public String toString() {
		return String.format(
				"[% 15f % 15f % 15f % 15f]\n" +
				"[% 15f % 15f % 15f % 15f]\n" +
				"[% 15f % 15f % 15f % 15f]\n" +
				"[% 15f % 15f % 15f % 15f]",
				m00, m10, m20, m30,
				m01, m11, m21, m31,
				m02, m12, m22, m32,
				m03, m13, m23, m33);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof Mat4))
			return false;
		Mat4 mat4 = (Mat4)obj;
		return  m00 == mat4.m00 &&
				m01 == mat4.m01 &&
				m02 == mat4.m02 &&
				m03 == mat4.m03 &&
				m10 == mat4.m10 &&
				m11 == mat4.m11 &&
				m12 == mat4.m12 &&
				m13 == mat4.m13 &&
				m20 == mat4.m20 &&
				m21 == mat4.m21 &&
				m22 == mat4.m22 &&
				m23 == mat4.m23 &&
				m30 == mat4.m30 &&
				m31 == mat4.m31 &&
				m32 == mat4.m32 &&
				m33 == mat4.m33;
	}
	
	/**
	 * Adds 2 matrices
	 * @param left Value 1
	 * @param right Value 2
	 * @return left + right
	 */
	public static Mat4 add(Mat4 left, Mat4 right) {
		float[] dest = new float[16];
		dest[0] = left.m00 + right.m00;
		dest[1] = left.m01 + right.m01;
		dest[2] = left.m02 + right.m02;
		dest[3] = left.m03 + right.m03;
		dest[4] = left.m10 + right.m10;
		dest[5] = left.m11 + right.m11;
		dest[6] = left.m12 + right.m12;
		dest[7] = left.m13 + right.m13;
		dest[8] = left.m20 + right.m20;
		dest[9] = left.m21 + right.m21;
		dest[10] = left.m22 + right.m22;
		dest[11] = left.m23 + right.m23;
		dest[12] = left.m30 + right.m30;
		dest[13] = left.m31 + right.m31;
		dest[14] = left.m32 + right.m32;
		dest[15] = left.m33 + right.m33;
		return new Mat4(dest);
	}
	
	/**
	 * Subtracts one matrix from another
	 * @param left Initial value
	 * @param right Value to subtract
	 * @return left - right
	 */
	public static Mat4 sub(Mat4 left, Mat4 right) {
		float[] dest = new float[16];
		dest[0] = left.m00 - right.m00;
		dest[1] = left.m01 - right.m01;
		dest[2] = left.m02 - right.m02;
		dest[3] = left.m03 - right.m03;
		dest[4] = left.m10 - right.m10;
		dest[5] = left.m11 - right.m11;
		dest[6] = left.m12 - right.m12;
		dest[7] = left.m13 - right.m13;
		dest[8] = left.m20 - right.m20;
		dest[9] = left.m21 - right.m21;
		dest[10] = left.m22 - right.m22;
		dest[11] = left.m23 - right.m23;
		dest[12] = left.m30 - right.m30;
		dest[13] = left.m31 - right.m31;
		dest[14] = left.m32 - right.m32;
		dest[15] = left.m33 - right.m33;
		return new Mat4(dest);
	}
	
	/**
	 * Multiplies to matrix
	 * @param left Left side of multiplication
	 * @param right Right side of multiplication
	 * @return left * right
	 */
	public static Mat4 mul(Mat4 left, Mat4 right) {
		float[] dest = new float[16];
		dest[0] = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02 + left.m30 * right.m03;
		dest[1] = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02 + left.m31 * right.m03;
		dest[2] = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02 + left.m32 * right.m03;
		dest[3] = left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02 + left.m33 * right.m03;
		
		dest[4] = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12 + left.m30 * right.m13;
		dest[5] = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12 + left.m31 * right.m13;
		dest[6] = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12 + left.m32 * right.m13;
		dest[7] = left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12 + left.m33 * right.m13;
		
		dest[8] = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22 + left.m30 * right.m23;
		dest[9] = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22 + left.m31 * right.m23;
		dest[10] = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 + left.m32 * right.m23;
		dest[11] = left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22 + left.m33 * right.m23;
		
		dest[12] = left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30 * right.m33;
		dest[13] = left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31 * right.m33;
		dest[14] = left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32 * right.m33;
		dest[15] = left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33 * right.m33;
		return new Mat4(dest);
	}
	
	/**
	 * Negates a matrix
	 * @param mat4 Matrix to negate
	 * @return -mat4
	 */
	public static Mat4 negate(Mat4 mat4) {
		float[] dest = new float[16];
		dest[0] = -mat4.m00;
		dest[1] = -mat4.m01;
		dest[2] = -mat4.m02;
		dest[3] = -mat4.m03;
		dest[4] = -mat4.m10;
		dest[5] = -mat4.m11;
		dest[6] = -mat4.m12;
		dest[7] = -mat4.m13;
		dest[8] = -mat4.m20;
		dest[9] = -mat4.m21;
		dest[10] = -mat4.m22;
		dest[11] = -mat4.m23;
		dest[12] = -mat4.m30;
		dest[13] = -mat4.m31;
		dest[14] = -mat4.m32;
		dest[15] = -mat4.m33;
		return new Mat4(dest);
	}
	
	/**
	 * Transposes a matrix
	 * @param mat4 Matrix to transpose
	 * @return mat4 transposed
	 */
	public static Mat4 transpose(Mat4 mat4) {
		float[] dest = new float[16];
		dest[0] = mat4.m00;
		dest[1] = mat4.m10;
		dest[2] = mat4.m20;
		dest[3] = mat4.m30;
		
		dest[4] = mat4.m01;
		dest[5] = mat4.m11;
		dest[6] = mat4.m21;
		dest[7] = mat4.m31;
		
		dest[8] = mat4.m02;
		dest[9] = mat4.m12;
		dest[10] = mat4.m22;
		dest[11] = mat4.m32;
		
		dest[12] = mat4.m03;
		dest[13] = mat4.m13;
		dest[14] = mat4.m23;
		dest[15] = mat4.m33;
		return new Mat4(dest);
	}
	
	public static Mat4 mul(Mat4 src, float scalar) {
		float[] dest = new float[16];
		dest[0] = src.m00 * scalar;
		dest[1] = src.m01 * scalar;
		dest[2] = src.m02 * scalar;
		dest[3] = src.m03 * scalar;
		dest[4] = src.m10 * scalar;
		dest[5] = src.m11 * scalar;
		dest[6] = src.m12 * scalar;
		dest[7] = src.m13 * scalar;
		dest[8] = src.m20 * scalar;
		dest[9] = src.m21 * scalar;
		dest[10] = src.m22 * scalar;
		dest[11] = src.m23 * scalar;
		dest[12] = src.m30 * scalar;
		dest[13] = src.m31 * scalar;
		dest[14] = src.m32 * scalar;
		dest[15] = src.m33 * scalar;
		return new Mat4(dest);
	}
	
	public static float determinant(Mat4 src) {
		float determ = Mat3.determinant(src.m11, src.m12, src.m13, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33) * src.m00;
		determ += Mat3.determinant(src.m01, src.m02, src.m03, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33) * -src.m10;
		determ += Mat3.determinant(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m31, src.m32, src.m33) * src.m20;
		determ += Mat3.determinant(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m21, src.m22, src.m23) * -src.m30;
		return determ;
	}
	
	public static Mat4 inverse(Mat4 src) {
		float[] dest = new float[16];
		float det = Mat4.determinant(src);
		if(det == 0) {
			return Mat4.IDENTITY;
		}
		float invdet = 1 / det;
		//first row
		dest[0] =  Mat3.determinant(src.m11, src.m12, src.m13, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
		dest[1] = -Mat3.determinant(src.m10, src.m12, src.m13, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
		dest[2] =  Mat3.determinant(src.m10, src.m11, src.m13, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
		dest[3] = -Mat3.determinant(src.m10, src.m11, src.m12, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
		// second row
		dest[4] = -Mat3.determinant(src.m01, src.m02, src.m03, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
		dest[5] =  Mat3.determinant(src.m00, src.m02, src.m03, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
		dest[6] = -Mat3.determinant(src.m00, src.m01, src.m03, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
		dest[7] =  Mat3.determinant(src.m00, src.m01, src.m02, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
		// third row
		dest[8] =  Mat3.determinant(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m31, src.m32, src.m33);
		dest[9] = -Mat3.determinant(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m30, src.m32, src.m33);
		dest[10] =  Mat3.determinant(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m30, src.m31, src.m33);
		dest[11] = -Mat3.determinant(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m30, src.m31, src.m32);
		// fourth row
		dest[12] = -Mat3.determinant(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m21, src.m22, src.m23);
		dest[13] =  Mat3.determinant(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m20, src.m22, src.m23);
		dest[14] = -Mat3.determinant(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m20, src.m21, src.m23);
		dest[15] =  Mat3.determinant(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m20, src.m21, src.m22);
		return Mat4.mul(new Mat4(dest), invdet);
	}
	
	/**
	 * Multiplies a matrix by a translation matrix
	 * @param dist A vector containing the x, y, and z distances to translate over
	 * @param mat4 The initial matrix
	 * @return A transformation matrix representing mat4 * new translation matrix
	 */
	public static Mat4 translate(Vec3 dist, Mat4 mat4) {
		float[] dest = new float[16];
		mat4.store(dest);
		dest[12] += mat4.m00 * dist.x + mat4.m10 * dist.y + mat4.m20 * dist.z;
		dest[13] += mat4.m01 * dist.x + mat4.m11 * dist.y + mat4.m21 * dist.z;
		dest[14] += mat4.m02 * dist.x + mat4.m12 * dist.y + mat4.m22 * dist.z;
		dest[15] += mat4.m03 * dist.x + mat4.m13 * dist.y + mat4.m23 * dist.z;
		return new Mat4(dest);
	}
	
	/**
	 * Multiplies a matrix by a scale matrix
	 * @param scale A vector containing the scale factor for the x, y, and z axis
	 * @param mat4 The initial matrix
	 * @return A transformation matrix representing mat4 * new scale matrix
	 */
	public static Mat4 scale(Vec3 scale, Mat4 mat4) {
		float[] dest = new float[16];
		dest[0] = mat4.m00 * scale.x;
		dest[1] = mat4.m01 * scale.x;
		dest[2] = mat4.m02 * scale.x;
		dest[3] = mat4.m03 * scale.x;
		
		dest[4] = mat4.m10 * scale.y;
		dest[5] = mat4.m11 * scale.y;
		dest[6] = mat4.m12 * scale.y;
		dest[7] = mat4.m13 * scale.y;
		
		dest[8]  = mat4.m20 * scale.z;
		dest[9]  = mat4.m21 * scale.z;
		dest[10] = mat4.m22 * scale.z;
		dest[11] = mat4.m23 * scale.z;
		
		dest[12] = mat4.m30;
		dest[13] = mat4.m31;
		dest[14] = mat4.m32;
		dest[15] = mat4.m33;
		return new Mat4(dest);
	}
	
	/**
	 * Multiplies a matrix by a rotation matrix
	 * @param radians Amount of radians to rotate
	 * @param axis A Vec3 which serves as the axis to rotate around
	 * @param mat4 The initial matrix
	 * @return A transformation matrix representing mat4 multiplied by the new rotation matrix
	 */
	public static Mat4 rotate(float radians, Vec3 axis, Mat4 mat4) {
		float[] dest = new float[16];
		axis = axis.normalize();
		float sin = (float)Math.sin(radians);
		float cos = (float)Math.cos(radians);
		float oneMinusCos = 1F - cos;
		float xx = axis.x * axis.x;
		float xy = axis.x * axis.y;
		float xz = axis.x * axis.z;
		
		float yy = axis.y * axis.y;
		float yz = axis.y * axis.z;
		
		float zz = axis.z * axis.z;
		
		float n00 = xx + (1 - xx) * cos;
		float n01 = xy * oneMinusCos + axis.z * sin;
		float n02 = xz * oneMinusCos - axis.y * sin;
		
		float n10 = xy * oneMinusCos - axis.z * sin;
		float n11 = yy + (1 - yy) * cos;
		float n12 = yz * oneMinusCos + axis.x * sin;
		
		float n20 = xz * oneMinusCos + axis.y * sin;
		float n21 = yz * oneMinusCos - axis.x * sin;
		float n22 = zz + (1 - zz) * cos;
		
		dest[0] = mat4.m00 * n00 + mat4.m10 * n01 + mat4.m20 * n02;
		dest[1] = mat4.m01 * n00 + mat4.m11 * n01 + mat4.m21 * n02;
		dest[2] = mat4.m02 * n00 + mat4.m12 * n01 + mat4.m22 * n02;
		dest[3] = mat4.m03 * n00 + mat4.m13 * n01 + mat4.m23 * n02;
		dest[4] = mat4.m00 * n10 + mat4.m10 * n11 + mat4.m20 * n12;
		dest[5] = mat4.m01 * n10 + mat4.m11 * n11 + mat4.m21 * n12;
		dest[6] = mat4.m02 * n10 + mat4.m12 * n11 + mat4.m22 * n12;
		dest[7] = mat4.m03 * n10 + mat4.m13 * n11 + mat4.m23 * n12;
		dest[8] = mat4.m00 * n20 + mat4.m10 * n21 + mat4.m20 * n22;
		dest[9] = mat4.m01 * n20 + mat4.m11 * n21 + mat4.m21 * n22;
		dest[10] = mat4.m02 * n20 + mat4.m12 * n21 + mat4.m22 * n22;
		dest[11] = mat4.m03 * n20 + mat4.m13 * n21 + mat4.m23 * n22;
		dest[12] = mat4.m30;
		dest[13] = mat4.m31;
		dest[14] = mat4.m32;
		dest[15] = mat4.m33;
		return new Mat4(dest);
	}
	
	/**
	 * Transforms a Vec3 by the upper-left 3x3 matrix in the mat4
	 * @param mat4 A transformation matrix
	 * @param vec3 Vector to be transformed
	 * @return The result of the top-left 3x3 matrix * vec3
	 */
	public static Vec3 transform(Mat4 mat4, Vec3 vec3) {
		float x = mat4.m00 * vec3.x + mat4.m10 * vec3.y + mat4.m20 * vec3.z;
		float y = mat4.m01 * vec3.x + mat4.m11 * vec3.y + mat4.m21 * vec3.z;
		float z = mat4.m02 * vec3.x + mat4.m12 * vec3.y + mat4.m22 * vec3.z;
		return new Vec3(x, y, z);
	}
	
	/**
	 * Transforms a Vec4 by a mat4
	 * @param mat4 A transformation matrix
	 * @param vec4 Vector to be transformed
	 * @return mat4 * vec4
	 */
	public static Vec4 transform(Mat4 mat4, Vec4 vec4) {
		float x = mat4.m00 * vec4.x + mat4.m10 * vec4.y + mat4.m20 * vec4.z + mat4.m30 * vec4.w;
		float y = mat4.m01 * vec4.x + mat4.m11 * vec4.y + mat4.m21 * vec4.z + mat4.m31 * vec4.w;
		float z = mat4.m02 * vec4.x + mat4.m12 * vec4.y + mat4.m22 * vec4.z + mat4.m32 * vec4.w;
		float w = mat4.m03 * vec4.x + mat4.m13 * vec4.y + mat4.m23 * vec4.z + mat4.m33 * vec4.w;
		return new Vec4(x, y, z, w);
	}
	
	/**
	 * Generates an orthographic projection matrix
	 * @param left Left boundary
	 * @param right Right boundary
	 * @param bottom Bottom boundary
	 * @param top Top boundary
	 * @param near Near boundary
	 * @param far Far boundary
	 * @return An orthographic projection matrix based on the given values
	 */
	public static Mat4 ortho(float left, float right, float bottom, float top, float near, float far) {
		float width = right - left;
		float height = top - bottom;
		float length = far - near;
		float[] dest = new float[16];
		dest[0] = 2 / width;
		dest[5] = 2 / height;
		dest[10] = -2 / length;
		dest[12] = -(right + left) / width;
		dest[13] = -(top + bottom) / height;
		dest[14] = -(far + near) / length;
		dest[15] = 1;
		return new Mat4(dest);
	}
	
	public static Mat4 frustrum(float left, float right, float bottom, float top, float near, float far) {
		float width = right - left;
		float height = top - bottom;
		float length = far - near;
		float[] dest = new float[16];
		dest[0] = (near * 2) / width;
		dest[5] = (near * 2) / height;
		dest[8] = (left + right) / width;
		dest[9] = (top + bottom) / height;
		dest[10] = -(far + near) / length;
		dest[11] = -1;
		dest[14] = -(far * near * 2) / length;
		return new Mat4(dest);
	}
	
	public static Mat4 perspective(float fovInDegrees, float aspectRatio, float near, float far) {
		float top = (float)(near * Math.tan(fovInDegrees * Math.PI / 360D));
		float right = top * aspectRatio;
		return frustrum(-right, right, -top, top, near, far);
	}

}
