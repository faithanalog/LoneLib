package com.unknownloner.lonelib.math;

import java.nio.FloatBuffer;

public class Mat4 {
	
	protected float m00;
	protected float m01;
	protected float m02;
	protected float m03;
	protected float m10;
	protected float m11;
	protected float m12;
	protected float m13;
	protected float m20;
	protected float m21;
	protected float m22;
	protected float m23;
	protected float m30;
	protected float m31;
	protected float m32;
	protected float m33;
	
	public static final Mat4 ZERO;
	public static final Mat4 IDENTITY;
	
	static {
		ZERO = new Mat4();
		IDENTITY = new Mat4();
		IDENTITY.m00 = 1F;
		IDENTITY.m11 = 1F;
		IDENTITY.m22 = 1F;
		IDENTITY.m33 = 1F;
	}
	
	protected Mat4() {
		
	}
	
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
	
	public Mat4 add(Mat4 right) {
		return add(this, right);
	}
	
	public Mat4 sub(Mat4 right) {
		return sub(this, right);
	}
	public Mat4 mul(Mat4 right) {
		return mul(this, right);
	}
	
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
		Mat4 dest = new Mat4();
		dest.m00 = left.m00 + right.m00;
		dest.m01 = left.m01 + right.m01;
		dest.m02 = left.m02 + right.m02;
		dest.m03 = left.m03 + right.m03;
		dest.m10 = left.m10 + right.m10;
		dest.m11 = left.m11 + right.m11;
		dest.m12 = left.m12 + right.m12;
		dest.m13 = left.m13 + right.m13;
		dest.m20 = left.m20 + right.m20;
		dest.m21 = left.m21 + right.m21;
		dest.m22 = left.m22 + right.m22;
		dest.m23 = left.m23 + right.m23;
		dest.m30 = left.m30 + right.m30;
		dest.m31 = left.m31 + right.m31;
		dest.m32 = left.m32 + right.m32;
		dest.m33 = left.m33 + right.m33;
		return dest;
	}
	
	/**
	 * Subtracts one matrix from another
	 * @param left Initial value
	 * @param right Value to subtract
	 * @return left - right
	 */
	public static Mat4 sub(Mat4 left, Mat4 right) {
		Mat4 dest = new Mat4();
		dest.m00 = left.m00 - right.m00;
		dest.m01 = left.m01 - right.m01;
		dest.m02 = left.m02 - right.m02;
		dest.m03 = left.m03 - right.m03;
		dest.m10 = left.m10 - right.m10;
		dest.m11 = left.m11 - right.m11;
		dest.m12 = left.m12 - right.m12;
		dest.m13 = left.m13 - right.m13;
		dest.m20 = left.m20 - right.m20;
		dest.m21 = left.m21 - right.m21;
		dest.m22 = left.m22 - right.m22;
		dest.m23 = left.m23 - right.m23;
		dest.m30 = left.m30 - right.m30;
		dest.m31 = left.m31 - right.m31;
		dest.m32 = left.m32 - right.m32;
		dest.m33 = left.m33 - right.m33;
		return dest;
	}
	
	/**
	 * Multiplies to matrix
	 * @param left Left side of multiplication
	 * @param right Right side of multiplication
	 * @return left * right
	 */
	public static Mat4 mul(Mat4 left, Mat4 right) {
		Mat4 dest = new Mat4();
		dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02 + left.m30 * right.m03;
		dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12 + left.m30 * right.m13;
		dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22 + left.m30 * right.m23;
		dest.m30 = left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30 * right.m33;
		
		dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02 + left.m31 * right.m03;
		dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12 + left.m31 * right.m13;
		dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22 + left.m31 * right.m23;
		dest.m31 = left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31 * right.m33;
		
		dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02 + left.m32 * right.m03;
		dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12 + left.m32 * right.m13;
		dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 + left.m32 * right.m23;
		dest.m32 = left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32 * right.m33;
		
		dest.m03 = left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02 + left.m33 * right.m03;
		dest.m13 = left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12 + left.m33 * right.m13;
		dest.m23 = left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22 + left.m33 * right.m23;
		dest.m33 = left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33 * right.m33;
		return dest;
	}
	
	/**
	 * Negates a matrix
	 * @param mat4 Matrix to negate
	 * @return -mat4
	 */
	public static Mat4 negate(Mat4 mat4) {
		Mat4 dest = new Mat4();
		dest.m00 = -mat4.m00;
		dest.m01 = -mat4.m01;
		dest.m02 = -mat4.m02;
		dest.m03 = -mat4.m03;
		dest.m10 = -mat4.m10;
		dest.m11 = -mat4.m11;
		dest.m12 = -mat4.m12;
		dest.m13 = -mat4.m13;
		dest.m20 = -mat4.m20;
		dest.m21 = -mat4.m21;
		dest.m22 = -mat4.m22;
		dest.m23 = -mat4.m23;
		dest.m30 = -mat4.m30;
		dest.m31 = -mat4.m31;
		dest.m32 = -mat4.m32;
		dest.m33 = -mat4.m33;
		return dest;
	}
	
	/**
	 * Transposes a matrix
	 * @param mat4 Matrix to transpose
	 * @return mat4 transposed
	 */
	public static Mat4 transpose(Mat4 mat4) {
		Mat4 dest = new Mat4();
		dest.m00 = mat4.m00;
		dest.m01 = mat4.m10;
		dest.m02 = mat4.m20;
		dest.m03 = mat4.m30;
		
		dest.m10 = mat4.m01;
		dest.m11 = mat4.m11;
		dest.m12 = mat4.m21;
		dest.m13 = mat4.m31;
		
		dest.m20 = mat4.m02;
		dest.m21 = mat4.m12;
		dest.m22 = mat4.m22;
		dest.m23 = mat4.m32;
		
		dest.m30 = mat4.m03;
		dest.m31 = mat4.m13;
		dest.m32 = mat4.m23;
		dest.m33 = mat4.m33;
		return dest;
	}
	
	public static Mat4 mul(Mat4 src, float scalar) {
		Mat4 dest = src.copy();
		dest.m00 *= scalar;
		dest.m01 *= scalar;
		dest.m02 *= scalar;
		dest.m03 *= scalar;
		dest.m10 *= scalar;
		dest.m11 *= scalar;
		dest.m12 *= scalar;
		dest.m13 *= scalar;
		dest.m20 *= scalar;
		dest.m21 *= scalar;
		dest.m22 *= scalar;
		dest.m23 *= scalar;
		dest.m30 *= scalar;
		dest.m31 *= scalar;
		dest.m32 *= scalar;
		dest.m33 *= scalar;
		return dest;
	}
	
	public static float determinant(Mat4 src) {
		Mat3 mat = new Mat3();
		
		mat.m00 = src.m11;
		mat.m01 = src.m12;
		mat.m02 = src.m13;
		mat.m10 = src.m21;
		mat.m11 = src.m22;
		mat.m12 = src.m23;
		mat.m20 = src.m31;
		mat.m21 = src.m32;
		mat.m22 = src.m33;
		float determ = Mat3.determinant(mat) * src.m00;
		
		mat.m00 = src.m01;
		mat.m01 = src.m02;
		mat.m02 = src.m03;
		mat.m10 = src.m21;
		mat.m11 = src.m22;
		mat.m12 = src.m23;
		mat.m20 = src.m31;
		mat.m21 = src.m32;
		mat.m22 = src.m33;
		determ += Mat3.determinant(mat) * -src.m10;
		
		mat.m00 = src.m01;
		mat.m01 = src.m02;
		mat.m02 = src.m03;
		mat.m10 = src.m11;
		mat.m11 = src.m12;
		mat.m12 = src.m13;
		mat.m20 = src.m31;
		mat.m21 = src.m32;
		mat.m22 = src.m33;
		determ += Mat3.determinant(mat) * src.m20;
		
		mat.m00 = src.m01;
		mat.m01 = src.m02;
		mat.m02 = src.m03;
		mat.m10 = src.m11;
		mat.m11 = src.m12;
		mat.m12 = src.m13;
		mat.m20 = src.m21;
		mat.m21 = src.m22;
		determ += Mat3.determinant(mat) * -src.m30;
		return determ;
	}
	
	public static Mat4 inverse(Mat4 src) {
		Mat4 dest = new Mat4();
		float det = Mat4.determinant(src);
		if(det == 0) {
			return Mat4.IDENTITY;
		}
		float invdet = 1 / det;
		dest.m00 =  Mat3.determinant(src.m11, src.m12, src.m13, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
		dest.m01 = -Mat3.determinant(src.m10, src.m12, src.m13, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
		dest.m02 =  Mat3.determinant(src.m10, src.m11, src.m13, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
		dest.m03 = -Mat3.determinant(src.m10, src.m11, src.m12, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
		// second row
		dest.m10 = -Mat3.determinant(src.m01, src.m02, src.m03, src.m21, src.m22, src.m23, src.m31, src.m32, src.m33);
		dest.m11 =  Mat3.determinant(src.m00, src.m02, src.m03, src.m20, src.m22, src.m23, src.m30, src.m32, src.m33);
		dest.m12 = -Mat3.determinant(src.m00, src.m01, src.m03, src.m20, src.m21, src.m23, src.m30, src.m31, src.m33);
		dest.m13 =  Mat3.determinant(src.m00, src.m01, src.m02, src.m20, src.m21, src.m22, src.m30, src.m31, src.m32);
		// third row
		dest.m20 =  Mat3.determinant(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m31, src.m32, src.m33);
		dest.m21 = -Mat3.determinant(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m30, src.m32, src.m33);
		dest.m22 =  Mat3.determinant(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m30, src.m31, src.m33);
		dest.m23 = -Mat3.determinant(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m30, src.m31, src.m32);
		// fourth row
		dest.m30 = -Mat3.determinant(src.m01, src.m02, src.m03, src.m11, src.m12, src.m13, src.m21, src.m22, src.m23);
		dest.m31 =  Mat3.determinant(src.m00, src.m02, src.m03, src.m10, src.m12, src.m13, src.m20, src.m22, src.m23);
		dest.m32 = -Mat3.determinant(src.m00, src.m01, src.m03, src.m10, src.m11, src.m13, src.m20, src.m21, src.m23);
		dest.m33 =  Mat3.determinant(src.m00, src.m01, src.m02, src.m10, src.m11, src.m12, src.m20, src.m21, src.m22);
		return Mat4.mul(dest, invdet);
	}
	
	/**
	 * Multiplies a matrix by a translation matrix
	 * @param dist A vector containing the x, y, and z distances to translate over
	 * @param mat4 The initial matrix
	 * @return A transformation matrix representing mat4 * new translation matrix
	 */
	public static Mat4 translate(Vec3 dist, Mat4 mat4) {
		Mat4 dest = mat4.copy();
		dest.m30 += mat4.m00 * dist.x + mat4.m10 * dist.y + mat4.m20 * dist.z;
		dest.m31 += mat4.m01 * dist.x + mat4.m11 * dist.y + mat4.m21 * dist.z;
		dest.m32 += mat4.m02 * dist.x + mat4.m12 * dist.y + mat4.m22 * dist.z;
		dest.m33 += mat4.m03 * dist.x + mat4.m13 * dist.y + mat4.m23 * dist.z;
		return dest;
	}
	
	/**
	 * Multiplies a matrix by a scale matrix
	 * @param scale A vector containing the scale factor for the x, y, and z axis
	 * @param mat4 The initial matrix
	 * @return A transformation matrix representing mat4 * new scale matrix
	 */
	public static Mat4 scale(Vec3 scale, Mat4 mat4) {
		Mat4 dest = mat4.copy();
		dest.m00 *= scale.x;
		dest.m01 *= scale.x;
		dest.m02 *= scale.x;
		dest.m03 *= scale.x;
		dest.m10 *= scale.y;
		dest.m11 *= scale.y;
		dest.m12 *= scale.y;
		dest.m13 *= scale.y;
		dest.m20 *= scale.z;
		dest.m21 *= scale.z;
		dest.m22 *= scale.z;
		dest.m23 *= scale.z;
		return dest;
	}
	
	/**
	 * Multiplies a matrix by a rotation matrix
	 * @param radians Amount of radians to rotate
	 * @param axis A Vec3 which serves as the axis to rotate around
	 * @param mat4 The initial matrix
	 * @return A transformation matrix representing mat4 multiplied by the new rotation matrix
	 */
	public static Mat4 rotate(float radians, Vec3 axis, Mat4 mat4) {
		axis = axis.normalize();
		Mat4 dest = mat4.copy();
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
		
		dest.m00 = mat4.m00 * n00 + mat4.m10 * n01 + mat4.m20 * n02;
		dest.m10 = mat4.m00 * n10 + mat4.m10 * n11 + mat4.m20 * n12;
		dest.m20 = mat4.m00 * n20 + mat4.m10 * n21 + mat4.m20 * n22;
		
		dest.m01 = mat4.m01 * n00 + mat4.m11 * n01 + mat4.m21 * n02;
		dest.m11 = mat4.m01 * n10 + mat4.m11 * n11 + mat4.m21 * n12;
		dest.m21 = mat4.m01 * n20 + mat4.m11 * n21 + mat4.m21 * n22;
		
		dest.m02 = mat4.m02 * n00 + mat4.m12 * n01 + mat4.m22 * n02;
		dest.m12 = mat4.m02 * n10 + mat4.m12 * n11 + mat4.m22 * n12;
		dest.m22 = mat4.m02 * n20 + mat4.m12 * n21 + mat4.m22 * n22;
		
		dest.m03 = mat4.m03 * n00 + mat4.m13 * n01 + mat4.m23 * n02;
		dest.m13 = mat4.m03 * n10 + mat4.m13 * n11 + mat4.m23 * n12;
		dest.m23 = mat4.m03 * n20 + mat4.m13 * n21 + mat4.m23 * n22;
		return dest;
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
		Mat4 dest = new Mat4();
		dest.m00 = 2 / width;
		dest.m01 = 0;
		dest.m02 = 0;
		dest.m03 = 0;
		dest.m10 = 0;
		dest.m11 = 2 / height;
		dest.m12 = 0;
		dest.m13 = 0;
		dest.m20 = 0;
		dest.m21 = 0;
		dest.m22 = -2 / length;
		dest.m23 = 0;
		dest.m30 = -(right + left) / width;
		dest.m31 = -(top + bottom) / height;
		dest.m32 = -(far + near) / length;
		dest.m33 = 1;
		return dest;
	}
	
	public static Mat4 frustrum(float left, float right, float bottom, float top, float near, float far) {
		float width = right - left;
		float height = top - bottom;
		float length = far - near;
		Mat4 dest = new Mat4();
		dest.m00 = (near * 2) / width;
		dest.m01 = 0;
		dest.m02 = 0;
		dest.m03 = 0;
		dest.m10 = 0;
		dest.m11 = (near * 2) / height;
		dest.m12 = 0;
		dest.m13 = 0;
		dest.m20 = (left + right) / width;
		dest.m21 = (top + bottom) / height;
		dest.m22 = -(far + near) / length;
		dest.m23 = -1;
		dest.m30 = 0;
		dest.m31 = 0;
		dest.m32 = -(far * near * 2) / length;
		dest.m33 = 0;
		return dest;
	}
	
	public static Mat4 perspective(float fovInDegrees, float aspectRatio, float near, float far) {
		float top = (float)(near * Math.tan(fovInDegrees * Math.PI / 360D));
		float right = top * aspectRatio;
		return frustrum(-right, right, -top, top, near, far);
	}

}
