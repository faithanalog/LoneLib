package com.unknownloner.lonelib.math;

import java.nio.FloatBuffer;

public class Vec3 {
	
	protected final float x;
	protected final float y;
	protected final float z;
	
	protected Vec3() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	/**
	 * Creates a new vector initialized with the values x, y, and z
	 * @param x The x value of the vector
	 * @param y The y value of the vector
	 * @param z The z value of the vector
	 */
	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Creates a new vector initialized to the next 3 values in src
	 * @param src A FloatBuffer to pull data from
	 */
	public Vec3(FloatBuffer src) {
		this.x = src.get();
		this.y = src.get();
		this.z = src.get();
	}
	
	/**
	 * Creates a new vector initialized with values in src
	 * @param src Source array for data
	 * @param offset Offset into the array to get values from
	 */
	public Vec3(float[] src, int offset) {
		this.x = src[offset++];
		this.y = src[offset++];
		this.z = src[offset];
	}
	
	public Vec3 copy() {
		return new Vec3(x, y, z);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	public float getLength() {
		return (float)Math.sqrt(getLengthSq());
	}
	
	public float getLengthSq() {
		return x * x + y * y + z * z;
	}
	
	/**
	 * Stores this vector in the float buffer at the buffer's current position
	 * @param buffer Buffer for data storaage
	 */
	public void store(FloatBuffer buffer) {
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
	}
	
	/**
	 * Normalizes the vector to a length of 1
	 * @return this
	 */
	public Vec3 normalize() {
		return normalize(this);
	}
	
	/**
	 * Adds a vector to this vector
	 * @param right
	 * @return this + right
	 */
	public Vec3 add(Vec3 right) {
		return add(this, right);
	}
	
	/**
	 * Subtracts a vector from thsi vector
	 * @param right
	 * @return this - right
	 */
	public Vec3 sub(Vec3 right) {
		return sub(this, right);
	}
	
	/**
	 * Negates the vector
	 * @return -this
	 */
	public Vec3 negate() {
		return negate(this);
	}
	
	/**
	 * Multiplies this vector by another vector
	 * @param right
	 * @return this * right
	 */
	public Vec3 mul(Vec3 right) {
		return mul(this, right);
	}
	
	/**
	 * Calculates the dot product of this vector and another vector
	 * @param right The other component of the calculation
	 * @return The dot product of this and right
	 */
	public float dot(Vec3 right) {
		return dot(this, right);
	}
	
	/**
	 * Calculates the angle between this vector and another vector
	 * @param right The other component of the calculation
	 * @return The angle between this and right
	 */
	public float angle(Vec3 right) {
		return angle(this, right);
	}

    /**
     * Multiplies all components of the vector3 by the scalar value.
     * @param val to scale by
     * @return Vec3 scaled
     */
    public Vec3 scale(float val) {
        return new Vec3(x * val, y * val, z * val);
    }

	@Override
	public String toString() {
		return String.format("(%f, %f, %f)", x, y, z);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof Vec3))
			return false;
		Vec3 vec3 = (Vec3)obj;
		return x == vec3.x && y == vec3.y && z == vec3.z;
	}
	
	/**
	 * Adds two vectors
	 * @param left Vector on left side
	 * @param right Vector on right side
	 */
	public static Vec3 add(Vec3 left, Vec3 right) {
		return new Vec3(left.x + right.x, left.y + right.y, left.z + right.z);
	}
	
	/**
	 * Subtracts the right vector from the left vector
	 * @param left Vector left of the minus sign
	 * @param right Vector right of the minus sign
	 */
	public static Vec3 sub(Vec3 left, Vec3 right) {
		return new Vec3(left.x - right.x, left.y - right.y, left.z - right.z);
	}
	
	/**
	 * Normalizes a vector and puts the results in dest
	 * @param vec Vector to normalize
	 */
	public static Vec3 normalize(Vec3 vec) {
		float len = (float)Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z);
		return new Vec3(vec.x / len, vec.y / len, vec.z / len);
	}
	
	public static Vec3 negate(Vec3 vec) {
		return new Vec3(-vec.x, -vec.y, -vec.z);
	}
	
	public static Vec3 mul(Vec3 left, Vec3 right) {
		return new Vec3(left.x * right.x, left.y * right.y, left.z * right.z);
	}
	
	/**
	 * Calculates the dot product of two vectors
	 * @param left The first component of the calculation
	 * @param right The second component of the calculation
	 * @return The dot product of left and right
	 */
	public static float dot(Vec3 left, Vec3 right) {
		return left.x * right.x + left.y * right.y + left.z * right.z;
	}
	
	/**
	 * Calculates the angle between two vectors
	 * @param left The first component of the calculation
	 * @param right The second component of the calculation
	 * @return The angle between left and right
	 */
	public static float angle(Vec3 left, Vec3 right) {
		return (float)Math.acos(dot(left, right));
	}
	
	public static Vec3 cross(Vec3 l, Vec3 r) {
		float[] m0 = new float[] {
				l.y, r.y,
				l.z, r.z,
		};
		float[] m1 = new float[] {
				l.z, r.z,
				l.x, r.x
		};
		float[] m2 = new float[] {
				l.x, r.x,
				l.y, r.y
		};
		
		float x = Mat2.determinant(new Mat2(m0));
		float y = Mat2.determinant(new Mat2(m1));
		float z = Mat2.determinant(new Mat2(m2));
		return new Vec3(x, y, z);
		
	}

}
