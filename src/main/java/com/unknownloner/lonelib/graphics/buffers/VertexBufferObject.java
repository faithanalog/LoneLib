package com.unknownloner.lonelib.graphics.buffers;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL15;

import com.unknownloner.lonelib.util.BufferUtil;

public class VertexBufferObject {
	
	public final int vboID;
	private final int target;
	private boolean deleted;
	
	public VertexBufferObject(int target, int usage, int dataSize) {
		vboID = GL15.glGenBuffers();
		this.target = target;
		bufferData(dataSize, usage);
	}
	
	public VertexBufferObject(int target, int usage, ByteBuffer data) {
		vboID = GL15.glGenBuffers();
		this.target = target;
		bufferData(data, usage);
	}
	
	public VertexBufferObject(int target, int usage, ShortBuffer data) {
		vboID = GL15.glGenBuffers();
		this.target = target;
		bufferData(data, usage);
	}
	
	public VertexBufferObject(int target, int usage, IntBuffer data) {
		vboID = GL15.glGenBuffers();
		this.target = target;
		bufferData(data, usage);
	}
	
	public VertexBufferObject(int target, int usage, FloatBuffer data) {
		vboID = GL15.glGenBuffers();
		this.target = target;
		bufferData(data, usage);
	}
	
	public VertexBufferObject(int target, int usage, byte[] data) {
		this(target, usage, BufferUtil.wrap(data));
	}
	
	public VertexBufferObject(int target, int usage, short[] data) {
		this(target, usage, BufferUtil.wrap(data));
	}
	
	public VertexBufferObject(int target, int usage, int[] data) {
		this(target, usage, BufferUtil.wrap(data));
	}
	
	public VertexBufferObject(int target, int usage, float[] data) {
		this(target, usage, BufferUtil.wrap(data));
	}
	
	public void bufferData(ByteBuffer data, int usage) {
		GL15.glBindBuffer(target, vboID);
		GL15.glBufferData(target, data, usage);
	}
	
	public void bufferData(ShortBuffer data, int usage) {
		GL15.glBindBuffer(target, vboID);
		GL15.glBufferData(target, data, usage);
	}
	
	public void bufferData(IntBuffer data, int usage) {
		GL15.glBindBuffer(target, vboID);
		GL15.glBufferData(target, data, usage);
	}
	
	public void bufferData(FloatBuffer data, int usage) {
		GL15.glBindBuffer(target, vboID);
		GL15.glBufferData(target, data, usage);
	}
	
	public void bufferData(int dataSize, int usage) {
		GL15.glBindBuffer(target, vboID);
		GL15.glBufferData(target, dataSize, usage);
	}
	
	public void bufferSubData(ByteBuffer data, int offset) {
		GL15.glBindBuffer(target, vboID);
		GL15.glBufferSubData(target, offset, data);
	}
	
	public void bufferSubData(ShortBuffer data, int offset) {
		GL15.glBindBuffer(target, vboID);
		GL15.glBufferSubData(target, offset, data);
	}
	
	public void bufferSubData(IntBuffer data, int offset) {
		GL15.glBindBuffer(target, vboID);
		GL15.glBufferSubData(target, offset, data);
	}
	
	public void bufferSubData(FloatBuffer data, int offset) {
		GL15.glBindBuffer(target, vboID);
		GL15.glBufferSubData(target, offset, data);
	}
	
	public void assign() {
		GL15.glBindBuffer(target, vboID);
	}
	
	public void delete() {
		if(!deleted) {
			deleted = true;
			GL15.glDeleteBuffers(vboID);
		}
	}
	
	@Override
	protected void finalize() {
		delete();
	}

}
