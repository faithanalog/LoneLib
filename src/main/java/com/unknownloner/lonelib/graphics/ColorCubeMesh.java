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
public class ColorCubeMesh implements Mesh {

	private final Vec3 position;
	private final VertexBufferObject vertBuffer;
	private final VertexBufferObject indexBuffer;
	private final VertexArrayObject vao;

	private final FloatBuffer verts;
	private final IntBuffer indices;
	private int index = 0; 
	private int numIndices = 0;
	
	public static final short BYTES_PER_TRI = 40; // 4 bytes per * 10 floats

	/**
	 * Construct a mesh for use at the given world position, with the number of maximum triangles
	 * @param position
	 * @param max triangles to render
	 */
	public ColorCubeMesh(Vec3 position, int numTriangles) {
		this.position = position;
		vertBuffer = new VertexBufferObject(GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, 0);
		indexBuffer = new VertexBufferObject(GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, 0);
		vao = new VertexArrayObject(
				new VertexAttribPointer(vertBuffer, 0, 3, GL11.GL_FLOAT, false, BYTES_PER_TRI, 0), // vertices
				new VertexAttribPointer(vertBuffer, 1, 4, GL11.GL_FLOAT, false, BYTES_PER_TRI, 3 * 4), // color data
				new VertexAttribPointer(vertBuffer, 2, 3, GL11.GL_FLOAT, false, BYTES_PER_TRI, (3 + 4) * 4) // normals
				);
		verts = BufferUtils.createFloatBuffer(numTriangles * 10); // 10 floats per face (3 verts, 4 colors, 3 normals
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
	public int addVertex(Vec3 point, float r, float g, float b, float a, float normX, float normY, float normZ) {
		verts.put(new float[] {point.getX(), point.getY(), point.getZ(), r, g, b, a, normX, normY, normZ});
		return index++;
	}

	public void addTriangle(int p1, int p2, int p3) {
		indices.put(new int[] {p1, p2, p3});
		numIndices += 3;
	}

	@Override
	public void finalizeMesh() {
		verts.flip();
		indices.flip();
		vertBuffer.bufferData(verts, GL15.GL_STATIC_DRAW);
		indexBuffer.bufferData(indices, GL15.GL_STATIC_DRAW);
	}

	@Override
	public void reset() {
		verts.clear();
		indices.clear();
		index = 0;
	}

	@Override
	public void render() {
		// render the mesh
		// Assumes that the proper shaderProgram is already active
		//TODO: translate to the position necessary for rendering
		vao.assign();
		indexBuffer.assign();
		GL11.glDrawElements(GL11.GL_TRIANGLES, numIndices, GL11.GL_UNSIGNED_INT, 0);
		if(!VertexArrayObject.vaoSupport) {
			vao.unassign();
		}
	}

	@Override
	public void delete() {
		vertBuffer.delete();
		indexBuffer.delete();
		vao.delete();
	}
	
	/**
	 * Get this meshes position
	 * @return position
	 */
	public Vec3 getPosition() {
		return position;
	}
}
