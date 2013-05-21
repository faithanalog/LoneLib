package com.unknownloner.lonelib.graphics;

import com.unknownloner.lonelib.math.Vec3;

public interface Mesh {

	/**
	 * Tells the GPU to render the given mesh
	 */
	public void render();

	/**
	 * Deletes the VBO data from the GPU
	 */
	public void delete();

	/**
	 * Resets the vertex and index buffers for re-generating the mesh
	 */
	public void reset();

	/**
	 * Readies this mesh for use with the GPU
	 */
	public void finalizeMesh();

    /**
     * @return world position of this mesh
     */
    public Vec3 getPosition();
}
