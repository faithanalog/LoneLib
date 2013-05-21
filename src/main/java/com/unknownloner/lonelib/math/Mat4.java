package com.unknownloner.lonelib.math;

import java.nio.FloatBuffer;

import com.unknownloner.lonelib.util.MathUtil;

public class Mat4 {

	private final float[] data;

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
	 * <br>This is private because people should use Mat4.ZERO for zero
	 * and Mat4.IDENTITY for the identity.
	 * <br>This is more efficient because of the immutable nature of matrices
	 */
	private Mat4() {
		this(new float[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
	}

	/**
	 * Creates a copy of src. Equivelant to src.copy()
	 * @param src
	 */
	public Mat4(Mat4 src) {
		this(src.data, true);
	}

	/**
	 * Creates a Mat4 from the next 16 float values in src
	 * Data must be in Column-major order
	 * @param src The data for matrix creation
	 */
	public Mat4(FloatBuffer src) {
		data = new float[16];
		for (int i = 0; i < 16; i++) {
			data[i] = src.get();
		}
	}

	/**
	 * Creates a Mat4 from 16 consecutive values in src, the float array will not be copied, so changes to the array will be reflected in the matrix.
	 * @param src The data for matrix creation
	 */
	public Mat4(float[] src) {
		this(src, false);
	}

	/**
	 * Constructs a new Matrix from the given float array.
	 * @param src
	 * @param copy, true to copy the data
	 */
	public Mat4(float[] src, boolean copy) {
		if (copy) {
			data = new float[16];
			System.arraycopy(src, 0, data, 0, src.length);
		} else {
			data = src;
		}
	}

	/**
	 * Creates a Mat4 from 16 consecutive values in src
	 * @param src The data for matrix creation
	 * @param offset offset into the array to pull values from, Leave as 0 to pull values 0-15
	 */
	public Mat4(float[] src, int offset) {
		this.data = new float[16];
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
	
	/**
	 * Constructs a copy of this matrix
	 * @return
	 */
	public Mat4 copy() {
		return new Mat4(this);
	}

	/**
	 * Stores this matrix in a FloatBuffer in column-major order
	 * @param dest
	 * @return this
	 */
	public Mat4 store(FloatBuffer dest) {
		dest.put(data);
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
						data[0], data[4], data[8], data[12],
						data[1], data[5], data[9], data[13],
						data[2], data[6], data[10], data[14],
						data[3], data[7], data[11], data[15]);
	}

	/**
	 * Adds 2 matrices
	 * @param left Value 1
	 * @param right Value 2
	 * @return left + right
	 */
	public static Mat4 add(Mat4 left, Mat4 right) {
		float[] dest = new float[16];
		dest[0] = left.data[0] + right.data[0];
		dest[1] = left.data[1] + right.data[1];
		dest[2] = left.data[2] + right.data[2];
		dest[3] = left.data[3] + right.data[3];
		dest[4] = left.data[4] + right.data[4];
		dest[5] = left.data[5] + right.data[5];
		dest[6] = left.data[6] + right.data[6];
		dest[7] = left.data[7] + right.data[7];
		dest[8] = left.data[8] + right.data[8];
		dest[9] = left.data[9] + right.data[9];
		dest[10] = left.data[10] + right.data[10];
		dest[11] = left.data[11] + right.data[11];
		dest[12] = left.data[12] + right.data[12];
		dest[13] = left.data[13] + right.data[13];
		dest[14] = left.data[14] + right.data[14];
		dest[15] = left.data[15] + right.data[15];
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
		dest[0] = left.data[0] - right.data[0];
		dest[1] = left.data[1] - right.data[1];
		dest[2] = left.data[2] - right.data[2];
		dest[3] = left.data[3] - right.data[3];
		dest[4] = left.data[4] - right.data[4];
		dest[5] = left.data[5] - right.data[5];
		dest[6] = left.data[6] - right.data[6];
		dest[7] = left.data[7] - right.data[7];
		dest[8] = left.data[8] - right.data[8];
		dest[9] = left.data[9] - right.data[9];
		dest[10] = left.data[10] - right.data[10];
		dest[11] = left.data[11] - right.data[11];
		dest[12] = left.data[12] - right.data[12];
		dest[13] = left.data[13] - right.data[13];
		dest[14] = left.data[14] - right.data[14];
		dest[15] = left.data[15] - right.data[15];
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
		dest[0] = left.data[0] * right.data[0] + left.data[4] * right.data[1] + left.data[8] * right.data[2] + left.data[12] * right.data[3];
		dest[1] = left.data[1] * right.data[0] + left.data[5] * right.data[1] + left.data[9] * right.data[2] + left.data[13] * right.data[3];
		dest[2] = left.data[2] * right.data[0] + left.data[6] * right.data[1] + left.data[10] * right.data[2] + left.data[14] * right.data[3];
		dest[3] = left.data[3] * right.data[0] + left.data[7] * right.data[1] + left.data[11] * right.data[2] + left.data[15] * right.data[3];

		dest[4] = left.data[0] * right.data[4] + left.data[4] * right.data[5] + left.data[8] * right.data[6] + left.data[12] * right.data[7];
		dest[5] = left.data[1] * right.data[4] + left.data[5] * right.data[5] + left.data[9] * right.data[6] + left.data[13] * right.data[7];
		dest[6] = left.data[2] * right.data[4] + left.data[6] * right.data[5] + left.data[10] * right.data[6] + left.data[14] * right.data[7];
		dest[7] = left.data[3] * right.data[4] + left.data[7] * right.data[5] + left.data[11] * right.data[6] + left.data[15] * right.data[7];

		dest[8] = left.data[0] * right.data[8] + left.data[4] * right.data[9] + left.data[8] * right.data[10] + left.data[12] * right.data[11];
		dest[9] = left.data[1] * right.data[8] + left.data[5] * right.data[9] + left.data[9] * right.data[10] + left.data[13] * right.data[11];
		dest[10] = left.data[2] * right.data[8] + left.data[6] * right.data[9] + left.data[10] * right.data[10] + left.data[14] * right.data[11];
		dest[11] = left.data[3] * right.data[8] + left.data[7] * right.data[9] + left.data[11] * right.data[10] + left.data[15] * right.data[11];

		dest[12] = left.data[0] * right.data[12] + left.data[4] * right.data[13] + left.data[8] * right.data[14] + left.data[12] * right.data[15];
		dest[13] = left.data[1] * right.data[12] + left.data[5] * right.data[13] + left.data[9] * right.data[14] + left.data[13] * right.data[15];
		dest[14] = left.data[2] * right.data[12] + left.data[6] * right.data[13] + left.data[10] * right.data[14] + left.data[14] * right.data[15];
		dest[15] = left.data[3] * right.data[12] + left.data[7] * right.data[13] + left.data[11] * right.data[14] + left.data[15] * right.data[15];
		return new Mat4(dest);
	}

	/**
	 * Negates a matrix
	 * @param mat4 Matrix to negate
	 * @return -mat4
	 */
	public static Mat4 negate(Mat4 mat4) {
		float[] dest = new float[16];
		dest[0] = -mat4.data[0];
		dest[1] = -mat4.data[1];
		dest[2] = -mat4.data[2];
		dest[3] = -mat4.data[3];
		dest[4] = -mat4.data[4];
		dest[5] = -mat4.data[5];
		dest[6] = -mat4.data[6];
		dest[7] = -mat4.data[7];
		dest[8] = -mat4.data[8];
		dest[9] = -mat4.data[9];
		dest[10] = -mat4.data[10];
		dest[11] = -mat4.data[11];
		dest[12] = -mat4.data[12];
		dest[13] = -mat4.data[13];
		dest[14] = -mat4.data[14];
		dest[15] = -mat4.data[15];
		return new Mat4(dest);
	}

	/**
	 * Transposes a matrix
	 * @param mat4 Matrix to transpose
	 * @return mat4 transposed
	 */
	public static Mat4 transpose(Mat4 mat4) {
		float[] dest = new float[16];
		dest[0] = mat4.data[0];
		dest[1] = mat4.data[4];
		dest[2] = mat4.data[8];
		dest[3] = mat4.data[12];

		dest[4] = mat4.data[1];
		dest[5] = mat4.data[5];
		dest[6] = mat4.data[9];
		dest[7] = mat4.data[13];

		dest[8] = mat4.data[2];
		dest[9] = mat4.data[6];
		dest[10] = mat4.data[10];
		dest[11] = mat4.data[14];

		dest[12] = mat4.data[3];
		dest[13] = mat4.data[7];
		dest[14] = mat4.data[11];
		dest[15] = mat4.data[15];
		return new Mat4(dest);
	}

	public static Mat4 mul(Mat4 src, float scalar) {
		float[] dest = new float[16];
		dest[0] = src.data[0] * scalar;
		dest[1] = src.data[1] * scalar;
		dest[2] = src.data[2] * scalar;
		dest[3] = src.data[3] * scalar;
		dest[4] = src.data[4] * scalar;
		dest[5] = src.data[5] * scalar;
		dest[6] = src.data[6] * scalar;
		dest[7] = src.data[7] * scalar;
		dest[8] = src.data[8] * scalar;
		dest[9] = src.data[9] * scalar;
		dest[10] = src.data[10] * scalar;
		dest[11] = src.data[11] * scalar;
		dest[12] = src.data[12] * scalar;
		dest[13] = src.data[13] * scalar;
		dest[14] = src.data[14] * scalar;
		dest[15] = src.data[15] * scalar;
		return new Mat4(dest);
	}

	public static float determinant(Mat4 src) {
		float determ = Mat3.determinant(src.data[5], src.data[6], src.data[7], src.data[9], src.data[10], src.data[11], src.data[13], src.data[14], src.data[15]) * src.data[0];
		determ += Mat3.determinant(src.data[1], src.data[2], src.data[3], src.data[9], src.data[10], src.data[11], src.data[13], src.data[14], src.data[15]) * -src.data[4];
		determ += Mat3.determinant(src.data[1], src.data[2], src.data[3], src.data[5], src.data[6], src.data[7], src.data[13], src.data[14], src.data[15]) * src.data[8];
		determ += Mat3.determinant(src.data[1], src.data[2], src.data[3], src.data[5], src.data[6], src.data[7], src.data[9], src.data[10], src.data[11]) * -src.data[12];
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
		dest[0] =  Mat3.determinant(src.data[5], src.data[6], src.data[7], src.data[9], src.data[10], src.data[11], src.data[13], src.data[14], src.data[15]);
		dest[1] = -Mat3.determinant(src.data[4], src.data[6], src.data[7], src.data[8], src.data[10], src.data[11], src.data[12], src.data[14], src.data[15]);
		dest[2] =  Mat3.determinant(src.data[4], src.data[5], src.data[7], src.data[8], src.data[9], src.data[11], src.data[12], src.data[13], src.data[15]);
		dest[3] = -Mat3.determinant(src.data[4], src.data[5], src.data[6], src.data[8], src.data[9], src.data[10], src.data[12], src.data[13], src.data[14]);
		// second row
		dest[4] = -Mat3.determinant(src.data[1], src.data[2], src.data[3], src.data[9], src.data[10], src.data[11], src.data[13], src.data[14], src.data[15]);
		dest[5] =  Mat3.determinant(src.data[0], src.data[2], src.data[3], src.data[8], src.data[10], src.data[11], src.data[12], src.data[14], src.data[15]);
		dest[6] = -Mat3.determinant(src.data[0], src.data[1], src.data[3], src.data[8], src.data[9], src.data[11], src.data[12], src.data[13], src.data[15]);
		dest[7] =  Mat3.determinant(src.data[0], src.data[1], src.data[2], src.data[8], src.data[9], src.data[10], src.data[12], src.data[13], src.data[14]);
		// third row
		dest[8] =  Mat3.determinant(src.data[1], src.data[2], src.data[3], src.data[5], src.data[6], src.data[7], src.data[13], src.data[14], src.data[15]);
		dest[9] = -Mat3.determinant(src.data[0], src.data[2], src.data[3], src.data[4], src.data[6], src.data[7], src.data[12], src.data[14], src.data[15]);
		dest[10] =  Mat3.determinant(src.data[0], src.data[1], src.data[3], src.data[4], src.data[5], src.data[7], src.data[12], src.data[13], src.data[15]);
		dest[11] = -Mat3.determinant(src.data[0], src.data[1], src.data[2], src.data[4], src.data[5], src.data[6], src.data[12], src.data[13], src.data[14]);
		// fourth row
		dest[12] = -Mat3.determinant(src.data[1], src.data[2], src.data[3], src.data[5], src.data[6], src.data[7], src.data[9], src.data[10], src.data[11]);
		dest[13] =  Mat3.determinant(src.data[0], src.data[2], src.data[3], src.data[4], src.data[6], src.data[7], src.data[8], src.data[10], src.data[11]);
		dest[14] = -Mat3.determinant(src.data[0], src.data[1], src.data[3], src.data[4], src.data[5], src.data[7], src.data[8], src.data[9], src.data[11]);
		dest[15] =  Mat3.determinant(src.data[0], src.data[1], src.data[2], src.data[4], src.data[5], src.data[6], src.data[8], src.data[9], src.data[10]);
		return Mat4.mul(new Mat4(dest), invdet);
	}

	/**
	 * Multiplies a matrix by a translation matrix
	 * @param dist A vector containing the x, y, and z distances to translate over
	 * @param mat4 The initial matrix
	 * @return A transformation matrix representing mat4 * new translation matrix
	 */
	public static Mat4 translate(Vec3 dist, Mat4 mat4) {
		Mat4 dest = mat4.copy();
		dest.data[12] += mat4.data[0] * dist.x + mat4.data[4] * dist.y + mat4.data[8] * dist.z;
		dest.data[13] += mat4.data[1] * dist.x + mat4.data[5] * dist.y + mat4.data[9] * dist.z;
		dest.data[14] += mat4.data[2] * dist.x + mat4.data[6] * dist.y + mat4.data[10] * dist.z;
		dest.data[15] += mat4.data[3] * dist.x + mat4.data[7] * dist.y + mat4.data[11] * dist.z;
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
		dest[0] = mat4.data[0] * scale.x;
		dest[1] = mat4.data[1] * scale.x;
		dest[2] = mat4.data[2] * scale.x;
		dest[3] = mat4.data[3] * scale.x;

		dest[4] = mat4.data[4] * scale.y;
		dest[5] = mat4.data[5] * scale.y;
		dest[6] = mat4.data[6] * scale.y;
		dest[7] = mat4.data[7] * scale.y;

		dest[8]  = mat4.data[8] * scale.z;
		dest[9]  = mat4.data[9] * scale.z;
		dest[10] = mat4.data[10] * scale.z;
		dest[11] = mat4.data[11] * scale.z;

		dest[12] = mat4.data[12];
		dest[13] = mat4.data[13];
		dest[14] = mat4.data[14];
		dest[15] = mat4.data[15];
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

		dest[0] = mat4.data[0] * n00 + mat4.data[4] * n01 + mat4.data[8] * n02;
		dest[1] = mat4.data[1] * n00 + mat4.data[5] * n01 + mat4.data[9] * n02;
		dest[2] = mat4.data[2] * n00 + mat4.data[6] * n01 + mat4.data[10] * n02;
		dest[3] = mat4.data[3] * n00 + mat4.data[7] * n01 + mat4.data[11] * n02;
		dest[4] = mat4.data[0] * n10 + mat4.data[4] * n11 + mat4.data[8] * n12;
		dest[5] = mat4.data[1] * n10 + mat4.data[5] * n11 + mat4.data[9] * n12;
		dest[6] = mat4.data[2] * n10 + mat4.data[6] * n11 + mat4.data[10] * n12;
		dest[7] = mat4.data[3] * n10 + mat4.data[7] * n11 + mat4.data[11] * n12;
		dest[8] = mat4.data[0] * n20 + mat4.data[4] * n21 + mat4.data[8] * n22;
		dest[9] = mat4.data[1] * n20 + mat4.data[5] * n21 + mat4.data[9] * n22;
		dest[10] = mat4.data[2] * n20 + mat4.data[6] * n21 + mat4.data[10] * n22;
		dest[11] = mat4.data[3] * n20 + mat4.data[7] * n21 + mat4.data[11] * n22;
		dest[12] = mat4.data[12];
		dest[13] = mat4.data[13];
		dest[14] = mat4.data[14];
		dest[15] = mat4.data[15];
		return new Mat4(dest);
	}

	/**
	 * Transforms a Vec3 by the upper-left 3x3 matrix in the mat4
	 * @param mat4 A transformation matrix
	 * @param vec3 Vector to be transformed
	 * @return The result of the top-left 3x3 matrix * vec3
	 */
	public static Vec3 transform(Mat4 mat4, Vec3 vec3) {
		float x = mat4.data[0] * vec3.x + mat4.data[4] * vec3.y + mat4.data[8] * vec3.z;
		float y = mat4.data[1] * vec3.x + mat4.data[5] * vec3.y + mat4.data[9] * vec3.z;
		float z = mat4.data[2] * vec3.x + mat4.data[6] * vec3.y + mat4.data[10] * vec3.z;
		return new Vec3(x, y, z);
	}

	/**
	 * Transforms a Vec4 by a mat4
	 * @param mat4 A transformation matrix
	 * @param vec4 Vector to be transformed
	 * @return mat4 * vec4
	 */
	public static Vec4 transform(Mat4 mat4, Vec4 vec4) {
		float x = mat4.data[0] * vec4.x + mat4.data[4] * vec4.y + mat4.data[8] * vec4.z + mat4.data[12] * vec4.w;
		float y = mat4.data[1] * vec4.x + mat4.data[5] * vec4.y + mat4.data[9] * vec4.z + mat4.data[13] * vec4.w;
		float z = mat4.data[2] * vec4.x + mat4.data[6] * vec4.y + mat4.data[10] * vec4.z + mat4.data[14] * vec4.w;
		float w = mat4.data[3] * vec4.x + mat4.data[7] * vec4.y + mat4.data[11] * vec4.z + mat4.data[15] * vec4.w;
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
		float top = (float)(near * Math.tan(fovInDegrees * MathUtil.PI360));
		float right = top * aspectRatio;
		return frustrum(-right, right, -top, top, near, far);
	}
	
	public static Mat4 makeTranslation(Vec3 dist) {
		float[] dest = new float[16];
		dest[0] = dest[5] = dest[10] = dest[15] = 1F;
		dest[12] = dist.x;
		dest[13] = dist.y;
		dest[14] = dist.z;
		return new Mat4(dest);
	}
	
	public static Mat4 makeRotation(float radians, Vec3 axis) {
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

		dest[0] = xx + (1 - xx) * cos;
		dest[1] = xy * oneMinusCos + axis.z * sin;
		dest[2] = xz * oneMinusCos - axis.y * sin;

		dest[4] = xy * oneMinusCos - axis.z * sin;
		dest[5] = yy + (1 - yy) * cos;
		dest[6] = yz * oneMinusCos + axis.x * sin;

		dest[8] = xz * oneMinusCos + axis.y * sin;
		dest[9] = yz * oneMinusCos - axis.x * sin;
		dest[10] = zz + (1 - zz) * cos;
		
		dest[15] = 1;
		return new Mat4(dest);
	}
	
	public static Mat4 makeScale(Vec3 scale) {
		float[] dest = new float[16];
		dest[0] = scale.x;
		dest[5] = scale.y;
		dest[10] = scale.z;
		dest[15] = 1;
		return new Mat4(dest);
	}

}
