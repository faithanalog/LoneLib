package com.unknownloner.lonelib.math;

/**
 * Represents a Quaternion
 */
public class Quat {

	private final float x, y, z, w;

	public static final Quat IDENTITY = new Quat(0, 0, 0, 1);

	public static final Quat UNIT_X = new Quat(1, 0, 0, 0);

	public static final Quat UNIT_Y = new Quat(0, 1, 0, 0);

	public static final Quat UNIT_Z = new Quat(0, 0, 1, 0);

	/**
	 * Constructs a Quaternion with the given xyzw which represents a Vector in 4d space.
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public Quat(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * Constructs a new Quaternion from an Axis and Angle.
	 * The Axis given must be a unit Vector
	 * 
	 * @param angle
	 * @param vec
	 */
	public Quat(float angle, Vec3 vec) {
		float halfAngle = (float) (Math.toRadians(angle) /2);
		float q = (float) Math.sin(halfAngle);
		this.x = vec.x * q;
		this.y = vec.y * q;
		this.z = vec.z * q;
		this.w = (float) Math.cos(halfAngle);
	}

	/**
	 * Constructs a new Quaternion from 3 angles: yaw, pitch, and roll.
	 * @param yaw
	 * @param pitch
	 * @param roll
	 */
	public Quat(float yaw, float pitch, float roll) {
		float c1 = (float) Math.cos(Math.toRadians(yaw) / 2);
		float c2 = (float) Math.cos(Math.toRadians(pitch) / 2);
		float c3 = (float) Math.cos(Math.toRadians(roll) / 2);
		float s1 = (float) Math.sin(Math.toRadians(yaw) / 2);
		float s2 = (float) Math.sin(Math.toRadians(pitch) / 2);
		float s3 = (float) Math.sin(Math.toRadians(roll) / 2);
		this.w = c1 * c2 * c3 - s1 * s2 *s3;
		this.x = s1 * s2 * c3 + c1 * c2 * s3;
		this.y = s1 * c2 * c3 + c1 * s2 * s3;
		this.z = c1 * s2 * c3 + s1 * c2 * s3;
	}

	/**
	 * @return the x component
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return the Y component
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return the Z component
	 */
	public float getZ() {
		return z;
	}

	/**
	 * @return the W component
	 */
	public float getW() {
		return w;
	}

	/**
	 * @return length squared of the quaternion
	 */
	public float getLengthSq() {
		return x * x + y * y + z * z + w * w;
	}

	/**
	 * @return length of the Quaternion
	 */
	public float getLength() {
		return (float) Math.sqrt(getLengthSq());
	}

	/**
	 * X component represents the pitch, Y component represents the yaw, and z component represents the roll.
	 * @return angle of each axis packed into a Vec3.  
	 */
	public Vec3 getAxisAngles() {
		final float q0 = this.w;
		float roll = this.z; // roll
		float pitch = this.x; // pitch
		float yaw = this.y; // yaw

		final double r1, r2, r3, test;
		test = q0 * pitch - yaw * roll;
		if (Math.abs(test) < 0.4999) { // Test for singularities
			r1 = Math.atan2(2 * (q0 * roll + pitch * yaw), 1 - 2 * (roll * roll + pitch * pitch));
			r2 = Math.asin(2 * test);
			r3 = Math.atan2(2 * (q0 * yaw + roll * pitch), 1 - 2 * (pitch * pitch + yaw * yaw));
		} else {
			int sign = (test < 0) ? -1 : 1;
			r1 = 0;
			r2 = sign * Math.PI / 2;
			r3 = -sign * 2 * Math.atan2(roll, q0);
		}

		roll = (float) Math.toDegrees(r1);
		pitch = (float) Math.toDegrees(r2);
		yaw = (float) Math.toDegrees(r3);
		if (yaw > 180) {
			yaw -= 360;
		} else if (yaw < -180) {
			yaw += 360;
		}
		return new Vec3(pitch, yaw, roll);
	}
	
	/**
	 * Converts this Quaternion into a 4x4 rotational Matrix, only works with normalized quaternions.
	 * @return
	 */
	public Mat4 toMatrix() {
		float sqw = this.w * this.w;
		float sqx = this.x * this.x;
		float sqy = this.y * this.y;
		float sqz = this.z * this.z;
		
		float[] mat = new float[16];
		mat[0] = sqx - sqy -sqz + sqw;
		mat[5] = -sqx + sqy -sqz + sqw;
		mat[10] = -sqx - sqy + sqz + sqw;
		
		float tmp1 = this.x * this.y;
		float tmp2 = this.z * this.w;
		mat[4] = 2.0f * (tmp1 + tmp2);
		mat[1] = 2.0f * (tmp1 - tmp2);
		
		tmp1 = this.x * this.z;
		tmp2 = this.y * this.w;
		mat[8] = 2.0f * (tmp1 - tmp2);
		mat[2] = 2.0f * (tmp1 + tmp2);
		tmp1 = this.y * this.z;
		tmp2 = this.x * this.w;
		mat[9] = 2.0f * (tmp1 + tmp2);
		mat[6] = 2.0f * (tmp1 - tmp2);
		mat[15] = 1;
		return new Mat4(mat);
	}

	public Quat normalize() {
		float length = getLength();
		return new Quat(this.x / length, this.y / length, this.z / length, this.w / length);
	}

	public static Quat fromPitch(float pitch) {
		return new Quat(0f, 0f, (float) Math.sin(Math.toRadians(pitch) / 2), (float) Math.cos(Math.toRadians(pitch) / 2));
	}

	public static Quat mul(Quat l, Quat r) {
		float x = l.w * r.x + l.x * r.w + l.y * r.z - l.z * r.y;
		float y = l.w * r.y + l.y * r.w + l.z * r.x - l.x * r.z;
		float z = l.w * r.z + l.z * r.w + l.x * r.y - l.y * r.x;
		float w = l.w * r.w - l.x * r.x - l.y * r.y - l.z * r.z;

		return new Quat(x, y, z, w);
	}
}
