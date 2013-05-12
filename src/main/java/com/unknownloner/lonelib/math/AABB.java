package com.unknownloner.lonelib.math;

import com.unknownloner.lonelib.util.MathUtil;

public class AABB {
	
	protected final float minx;
	protected final float miny;
	protected final float minz;
	protected final float maxx;
	protected final float maxy;
	protected final float maxz;
	
	public AABB(float minx, float miny, float minz, float maxx, float maxy, float maxz) {
		this.minx = minx;
		this.miny = miny;
		this.minz = minz;
		this.maxx = maxx;
		this.maxy = maxy;
		this.maxz = maxz;
	}
	
	public AABB(Vec3 minPos, Vec3 maxPos) {
		this.minx = minPos.x;
		this.miny = minPos.y;
		this.minz = minPos.z;
		this.maxx = maxPos.x;
		this.maxy = maxPos.y;
		this.maxz = maxPos.z;
	}
	
	public float getMinx() {
		return minx;
	}

	public float getMiny() {
		return miny;
	}

	public float getMinz() {
		return minz;
	}

	public float getMaxx() {
		return maxx;
	}

	public float getMaxy() {
		return maxy;
	}

	public float getMaxz() {
		return maxz;
	}
	
	public Vec3 getMinPos() {
		return new Vec3(minx, miny, minz);
	}
	
	public Vec3 getMaxPos() {
		return new Vec3(maxx, maxy, maxz);
	}
	
	public float getXSize() {
		return maxx - minx;
	}
	
	public float getYSize() {
		return maxy - miny;
	}
	
	public float getZSize() {
		return maxz - minz;
	}
	
	public Vec3 getCenter() {
		return new Vec3(
			(maxx + minx) / 2F,
			(maxy + miny) / 2F,
			(maxz + minz) / 2F
		);
	}
	
	public boolean intersects(AABB bounds) {
		return bounds.maxx > this.minx && bounds.minx < this.maxx
				&& bounds.maxy > this.miny && bounds.miny < this.maxy
				&& bounds.maxz > this.minz && bounds.minz < this.maxz;
	}
	
	/**
	 * Returns the distances from pos that a ray in direction of dir intersects this AABB
	 * @param pos The starting point of the ray
	 * @param dir The direction of the ray
	 * @return A {@link Vec2} where pos + vec2.x = entry point and pos + vec2.y = exit point, Or null if no intersection
	 */
	public Vec2 intersectDists(Vec3 pos, Vec3 dir) {
		dir = dir.normalize();
		float tx1 = (this.minx - pos.x) / dir.x;
		float tx2 = (this.maxx - pos.x) / dir.x;
	 
		float ty1 = (this.miny - pos.y) / dir.y;
		float ty2 = (this.maxy - pos.y) / dir.y;
		
		float tz1 = (this.minz - pos.z) / dir.z;
		float tz2 = (this.maxz - pos.z) / dir.z;
		
		float tmin = MathUtil.max(Math.min(tx1, tx2), Math.min(ty1, ty2), Math.min(tz1, tz2));
		float tmax = MathUtil.min(Math.max(tx1, tx2), Math.max(ty1, ty2), Math.max(tz1, tz2));
		if(tmax >= tmin) {
			return new Vec2(tmin, tmax);
		} else {
			return null;
		}
	}
	
	/**
	 * Checks if a ray starting from pos in direction of dir intersects this AABB
	 * @param pos Starting point of ray
	 * @param dir Direction of the ray
	 * @return Whether or not the ray intersects this AABB
	 */
	public boolean intersects(Vec3 pos, Vec3 dir) {
		return intersectDists(pos, dir) != null;
	}
	
	/**
	 * Checks if a ray starting from pos in direction of dir intersects this AABB,
	 * and return the point of entry with this AABB.
	 * @param pos Starting point of ray
	 * @param dir Direction of the ray
	 * @return The point of entry with this AABB, or null if no intersection
	 */
	public Vec3 intersectEntry(Vec3 pos, Vec3 dir) {
		Vec2 dists = intersectDists(pos, dir);
		if(dists == null)
			return null;
		return pos.add(new Vec3(dir.x * dists.x, dir.y * dists.x, dir.z * dists.x));
	}
	
	/**
	 * Checks if a ray starting from pos in direction of dir intersects this AABB,
	 * and return the point of exit with this AABB.
	 * @param pos Starting point of ray
	 * @param dir Direction of the ray
	 * @return The point of exit with this AABB, or null if no intersection
	 */
	public Vec3 intersectExit(Vec3 pos, Vec3 dir) {
		Vec2 dists = intersectDists(pos, dir);
		if(dists == null)
			return null;
		return pos.add(new Vec3(dir.x * dists.y, dir.y * dists.y, dir.z * dists.y));
	}
	
	public boolean contains(Vec3 pos) {
		return pos.x > this.minx && pos.x < this.maxx
				&& pos.y > this.miny && pos.y < this.maxy
				&& pos.z > this.minz && pos.z < this.maxz;
	}
	
	/**
	 * Expands the AABB's bounds by amnt. Negative values will not shrink the bounds,
	 * just expand it in a negative direction
	 * @param amnt The amount to expand the AABB in the x, y, and z direction.
	 * @return A new AABB expanded by amnt
	 */
	public AABB add(Vec3 amnt) {
		float nMinx = minx;
		float nMiny = miny;
		float nMinz = minz;
		float nMaxx = maxx;
		float nMaxy = maxy;
		float nMaxz = maxz;
		if(amnt.x < 0)
			nMinx += amnt.x;
		else
			nMaxx += amnt.x;
		if(amnt.y < 0)
			nMiny += amnt.y;
		else
			nMaxy += amnt.y;
		if(amnt.z < 0)
			nMinz += amnt.z;
		else
			nMaxz += amnt.z;
		return new AABB(nMinx, nMiny, nMinz, nMaxx, nMaxy, nMaxz);
	}
	
	/**
	 * Moves the bounds by amnt
	 * @param amnt The amount to offset the AABB by
	 * @return A new AABB offset from the original by amnt
	 */
	public AABB offset(Vec3 amnt) {
		return new AABB(minx + amnt.x, miny + amnt.y, minz + amnt.z, maxx + amnt.x, maxy + amnt.y, maxz + amnt.z);
	}
	
	/**
	 * Keeps the bounds dimensions but changes it's position to center
	 * @param pos The new center point
	 * @return A new AABB of the same dimensions but a center of pos
	 */
	public AABB setCenter(Vec3 pos) {
		float x = getXSize() / 2F;
		float y = getYSize() / 2F;
		float z = getZSize() / 2F;
		return new AABB(pos.x - x, pos.y - y, pos.z - z, pos.x + x, pos.y + y, pos.z + z);
	}
	
	/**
	 * Calculates the x distance between the edges of this bound and another.
	 * @param bounds The other bounding box
	 * @param curOff A current distance. If the new offset is further from 0
	 * than curOff, curOff will be returned.
	 * @return
	 */
	public float calcXOffset(AABB bounds, float curOff) {
		if(bounds.maxy > this.miny && bounds.miny < this.maxy && bounds.maxz > this.minz && bounds.minz < this.maxz) {
			float newOff;
			if(curOff > 0 && bounds.maxx <= this.minx) {
				newOff = this.minx - bounds.maxx;
				if(newOff < curOff) {
					curOff = newOff;
				}
			}
			if(curOff < 0 && bounds.minx >= this.maxx) {
				newOff = this.maxx - bounds.minx;
				if(newOff > curOff) {
					curOff = newOff;
				}
			}
		}
		return curOff;
	};
	
	
	/**
	 * Calculates the y distance between the edges of this bound and another.
	 * @param bounds The other bounding box
	 * @param curOff A current distance. If the new offset is further from 0
	 * than curOff, curOff will be returned.
	 * @return
	 */
	public float calcYOffset(AABB bounds, float curOff) {
		if(bounds.maxx > this.minx && bounds.minx < this.maxx && bounds.maxz > this.minz && bounds.minz < this.maxz) {
			float newOff;
			if(curOff > 0 && bounds.maxy <= this.miny) {
				newOff = this.miny - bounds.maxy;
				if(newOff < curOff) {
					curOff = newOff;
				}
			}
			if(curOff < 0 && bounds.miny >= this.maxy) {
				newOff = this.maxy - bounds.miny;
				if(newOff > curOff) {
					curOff = newOff;
				}
			}
		}
		return curOff;
	}
	
	/**
	 * Calculates the z distance between the edges of this bound and another.
	 * @param bounds The other bounding box
	 * @param curOff A current distance. If the new offset is further from 0
	 * than curOff, curOff will be returned.
	 * @return
	 */
	public float calcZOffset(AABB bounds, float curOff) {
		if(bounds.maxy > this.miny && bounds.miny < this.maxy && bounds.maxx > this.minx && bounds.minx < this.maxx) {
			float newOff;
			if(curOff > 0 && bounds.maxz <= this.minz) {
				newOff = this.minz - bounds.maxz;
				if(newOff < curOff) {
					curOff = newOff;
				}
			}
			if(curOff < 0 && bounds.minz >= this.maxz) {
				newOff = this.maxz - bounds.minz;
				if(newOff > curOff) {
					curOff = newOff;
				}
			}
			
		}
		return curOff;
	};

}
