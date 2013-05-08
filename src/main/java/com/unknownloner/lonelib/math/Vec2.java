package com.unknownloner.lonelib.math;

import java.nio.FloatBuffer;

public class Vec2 {
	
	protected float x;
	protected float y;
	
	protected Vec2() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Creates a new vector initialized with the values x and y
	 * @param x The x value of the vector
	 * @param y The y value of the vector
	 */
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Creates a new vector initialized to the next 2 values in src
	 * @param src A FloatBuffer to pull data from
	 */
	public Vec2(FloatBuffer src) {
		this.x = src.get();
		this.y = src.get();
	}
	
	/**
	 * Creates a new vector initialized with values in src
	 * @param src Source array for data
	 * @param offset Offset into the array to get values from
	 */
	public Vec2(float[] src, int offset) {
		this.x = src[offset++];
		this.y = src[offset];
	}
	
	public Vec2 copy() {
		return new Vec2(x, y);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getLength() {
		return (float)Math.sqrt(getLengthSq());
	}
	
	public float getLengthSq() {
		return x * x + y * y;
	}
	
	/**
	 * Stores this vector in the float buffer at the buffer's current position
	 * @param buffer Buffer for data storaage
	 */
	public void store(FloatBuffer buffer) {
		buffer.put(x);
		buffer.put(y);
	}
	
	/**
	 * Normalizes the vector to a length of 1
	 * @return this
	 */
	public Vec2 normalize() {
		return normalize(this);
	}
	
	/**
	 * Adds a vector to this vector
	 * @param right
	 * @return this + right
	 */
	public Vec2 add(Vec2 right) {
		return add(this, right);
	}
	
	/**
	 * Subtracts a vector from thsi vector
	 * @param right
	 * @return this - right
	 */
	public Vec2 sub(Vec2 right) {
		return sub(this, right);
	}
	
	/**
	 * Negates the vector
	 * @return -this
	 */
	public Vec2 negate() {
		return negate(this);
	}
	
	/**
	 * Multiplies this vector by another vector
	 * @param right
	 * @return this * right
	 */
	public Vec2 mul(Vec2 right) {
		return mul(this, right);
	}
	
	/**
	 * Calculates the dot product of this vector and another vector
	 * @param right The other component of the calculation
	 * @return The dot product of this and right
	 */
	public float dot(Vec2 right) {
		return dot(this, right);
	}
	
	/**
	 * Calculates the angle between this vector and another vector
	 * @param right The other component of the calculation
	 * @return The angle between this and right
	 */
	public float angle(Vec2 right) {
		return angle(this, right);
	}
	
	@Override
	public String toString() {
		return String.format("(%f, %f)", x, y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof Vec2))
			return false;
		Vec2 vec2 = (Vec2)obj;
		return x == vec2.x && y == vec2.y;
	}
	
	/**
	 * Adds two vectors
	 * @param left Vector on left side
	 * @param right Vector on right side
	 */
	public static Vec2 add(Vec2 left, Vec2 right) {
		return new Vec2(left.x + right.x, left.y + right.y);
	}
	
	/**
	 * Subtracts the right vector from the left vector
	 * @param left Vector left of the minus sign
	 * @param right Vector right of the minus sign
	 */
	public static Vec2 sub(Vec2 left, Vec2 right) {
		return new Vec2(left.x - right.x, left.y - right.y);
	}
	
	/**
	 * Normalizes a vector and puts the results in dest
	 * @param vec Vector to normalize
	 */
	public static Vec2 normalize(Vec2 vec) {
		float len = (float)Math.sqrt(vec.x * vec.x + vec.y * vec.y);
		return new Vec2(vec.x / len, vec.y / len);
	}
	
	public static Vec2 negate(Vec2 vec) {
		return new Vec2(-vec.x, -vec.y);
	}
	
	public static Vec2 mul(Vec2 left, Vec2 right) {
		return new Vec2(left.x * right.x, left.y * right.y);
	}
	
	/**
	 * Calculates the dot product of two vectors
	 * @param left The first component of the calculation
	 * @param right The second component of the calculation
	 * @return The dot product of left and right
	 */
	public static float dot(Vec2 left, Vec2 right) {
		return left.x * right.x + left.y * right.y;
	}
	
	/**
	 * Calculates the angle between two vectors
	 * @param left The first component of the calculation
	 * @param right The second component of the calculation
	 * @return The angle between left and right
	 */
	public static float angle(Vec2 left, Vec2 right) {
		return (float)Math.acos(dot(left, right));
	}

}
