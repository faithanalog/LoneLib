package com.unknownloner.lonelib.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.unknownloner.lonelib.graphics.buffers.VertexArrayObject;
import com.unknownloner.lonelib.graphics.buffers.VertexAttribPointer;
import com.unknownloner.lonelib.graphics.buffers.VertexBufferObject;
import com.unknownloner.lonelib.math.Vec3;

/**
 * Represents a Mesh for use with a shader program that uses vertices(3), color data(4), and normals(3) as floats.
 */
public class ColorMesh {

	private final Vec3 position;
	private final VertexBufferObject vertBuffer;
	private final VertexBufferObject indexBuffer;
	private final VertexArrayObject vertArray;

	private final FloatBuffer verts;
	private final IntBuffer indices;
	private short index = 0; 
	private short numIndices = 0;

	/**
	 * Construct a mesh for use at the given world position, with the number of maximum faces
	 * @param position
	 * @param faces
	 */
	public ColorMesh(Vec3 position, int numTriangles) {
		this.position = position;
		vertBuffer = new VertexBufferObject(GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, 0);
		indexBuffer = new VertexBufferObject(GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, 0);
		vertArray = new VertexArrayObject(
				new VertexAttribPointer(vertBuffer, 0, 3, GL11.GL_FLOAT, false, (3 + 4 + 3) * 4, 0), // vertices
				new VertexAttribPointer(vertBuffer, 1, 4, GL11.GL_FLOAT, false, (3 + 4 + 3) * 4, 3 * 4), // color data
				new VertexAttribPointer(vertBuffer, 2, 3, GL11.GL_FLOAT, false, (3 + 4 + 3) * 4, (3 + 4) * 4) // normals
				);
		verts = BufferUtils.createFloatBuffer(numTriangles * 3 * (3 + 4 + 3)); // 3 vertices per Tri * numFloats used
		indices = BufferUtils.createIntBuffer(numTriangles * 3); // 3 indices per tri
	}

	/**
	 * Adds vertex data to the float buffer and returns the index the vertex was put.
	 * 
	 * @param point
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 * @param normX
	 * @param normY
	 * @param normZ
	 * @return
	 */
	public short addVertex(Vec3 point, float r, float g, float b, float a, float normX, float normY, float normZ) {
		verts.put(point.getX())
		.put(point.getY())
		.put(point.getZ())
		.put(r)
		.put(g)
		.put(b)
		.put(a)
		.put(normX)
		.put(normY)
		.put(normZ);
		return index++;
	}

	public void addTriangle(short p1, short p2, short p3) {
		indices.put(p1).put(p2).put(p3);
		numIndices += 3;
	}


	/**
	 * Readies this mesh for use with the GPU
	 */
	public void finalize() {
		verts.flip();
		indices.flip();
		vertBuffer.bufferData(verts, GL15.GL_STATIC_DRAW);
		indexBuffer.bufferData(indices, GL15.GL_STATIC_DRAW);
	}

	/**
	 * Resets the vertex and index buffers for re-generating the mesh
	 */
	public void reset() {
		verts.clear();
		indices.clear();
	}

	public void render() {
		// render the mesh
		// Assumes that the proper shaderProgram is already active
		//TODO: translate to the position necessary for rendering
		vertArray.assign();
		indexBuffer.assign();
		GL11.glDrawElements(GL11.GL_TRIANGLES, numIndices, GL11.GL_UNSIGNED_INT, 0);
		vertArray.unassign();
	}
	
	public void delete() {
		vertBuffer.delete();
		indexBuffer.delete();
		vertArray.delete();
	}
}
