package com.unknownloner.lonelib.math;

import java.nio.FloatBuffer;

public class Vec4 {
	
	protected float x;
	protected float y;
	protected float z;
	protected float w;
	
	protected Vec4() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
	}
	
	/**
	 * Creates a new vector initialized with the values x, y, z, and w
	 * @param x The x value of the vector
	 * @param y The y value of the vector
	 * @param z The z value of the vector
	 * @param w the w value of the vector
	 */
	public Vec4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/**
	 * Creates a new vector initialized to the next 4 values in src
	 * @param src A FloatBuffer to pull data from
	 */
	public Vec4(FloatBuffer src) {
		this.x = src.get();
		this.y = src.get();
		this.z = src.get();
		this.w = src.get();
	}
	
	/**
	 * Creates a new vector initialized with values in src
	 * @param src Source array for data
	 * @param offset Offset into the array to get values from
	 */
	public Vec4(float[] src, int offset) {
		this.x = src[offset++];
		this.y = src[offset++];
		this.z = src[offset++];
		this.w = src[offset];
	}
	
	public Vec4 copy() {
		return new Vec4(x, y, z, w);
	}
	
	/**
	 * Divides all elements by W and stores it in a new Vec3
	 * @return Vec3 containing (x / w, y / w, z / w)
	 */
	public Vec3 toVec3() {
		return new Vec3(x / w, y / w, z / w);
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
	
	public float getW() {
		return w;
	}
	
	public float getLength() {
		return (float)Math.sqrt(getLengthSq());
	}
	
	public float getLengthSq() {
		return x * x + y * y + z * z + w * w;
	}
	
	/**
	 * Stores this vector in the float buffer at the buffer's current position
	 * @param buffer Buffer for data storaage
	 */
	public void store(FloatBuffer buffer) {
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		buffer.put(w);
	}
	
	/**
	 * Normalizes the vector to a length of 1
	 * @return this
	 */
	public Vec4 normalize() {
		return normalize(this);
	}
	
	/**
	 * Adds a vector to this vector
	 * @param right
	 * @return this + right
	 */
	public Vec4 add(Vec4 right) {
		return add(this, right);
	}
	
	/**
	 * Subtracts a vector from this vector
	 * @param right
	 * @return this - right
	 */
	public Vec4 sub(Vec4 right) {
		return sub(this, right);
	}
	
	/**
	 * Negates the vector
	 * @return -this
	 */
	public Vec4 negate() {
		return negate(this);
	}
	
	/**
	 * Multiplies this vector by another vector
	 * @param right
	 * @return this * right
	 */
	public Vec4 mul(Vec4 right) {
		return mul(this, right);
	}
	
	/**
	 * Calculates the dot product of this vector and another vector
	 * @param right The other component of the calculation
	 * @return The dot product of this and right
	 */
	public float dot(Vec4 right) {
		return dot(this, right);
	}
	
	/**
	 * Calculates the angle between this vector and another vector
	 * @param right The other component of the calculation
	 * @return The angle between this and right
	 */
	public float angle(Vec4 right) {
		return angle(this, right);
	}
	
	@Override
	public String toString() {
		return String.format("(%f, %f, %f, %f)", x, y, z, w);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof Vec4))
			return false;
		Vec4 vec4 = (Vec4)obj;
		return x == vec4.x && y == vec4.y && z == vec4.z && w == vec4.w;
	}
	
	/**
	 * Adds two vectors
	 * @param left Vector on left side
	 * @param right Vector on right side
	 */
	public static Vec4 add(Vec4 left, Vec4 right) {
		return new Vec4(left.x + right.x, left.y + right.y, left.z + right.z, left.w + right.w);
	}
	
	/**
	 * Subtracts the right vector from the left vector
	 * @param left Vector left of the minus sign
	 * @param right Vector right of the minus sign
	 */
	public static Vec4 sub(Vec4 left, Vec4 right) {
		return new Vec4(left.x - right.x, left.y - right.y, left.z - right.z, left.w - right.w);
	}
	
	/**
	 * Normalizes a vector and puts the results in dest
	 * @param vec Vector to normalize
	 */
	public static Vec4 normalize(Vec4 vec) {
		float len = (float)Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z + vec.w * vec.w);
		return new Vec4(vec.x / len, vec.y / len, vec.z / len, vec.w / len);
	}
	
	public static Vec4 negate(Vec4 vec) {
		return new Vec4(-vec.x, -vec.y, -vec.z, -vec.w);
	}
	
	public static Vec4 mul(Vec4 left, Vec4 right) {
		return new Vec4(left.x * right.x, left.y * right.y, left.z * right.z, left.w * right.w);
	}
	
	/**
	 * Calculates the dot product of two vectors
	 * @param left The first component of the calculation
	 * @param right The second component of the calculation
	 * @return The dot product of left and right
	 */
	public static float dot(Vec4 left, Vec4 right) {
		return left.x * right.x + left.y * right.y + left.z * right.z + left.w * right.w;
	}
	
	/**
	 * Calculates the angle between two vectors
	 * @param left The first component of the calculation
	 * @param right The second component of the calculation
	 * @return The angle between left and right
	 */
	public static float angle(Vec4 left, Vec4 right) {
		return (float)Math.acos(dot(left, right));
	}

}
