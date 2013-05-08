package com.unknownloner.lonelib.graphics.buffers;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL15;

import com.unknownloner.lonelib.util.BufferUtil;

public class VertexBufferObject {
	
	private final int vboID;
	private final int target;
	public final int elemLength;
	public final int byteLength;
	
	public VertexBufferObject(int target, int usage, Buffer data) {
		vboID = GL15.glGenBuffers();
		this.target = target;
		this.elemLength = data.limit() - data.position();
		this.byteLength = elemLength * BufferUtil.getElemByteSize(data);
		bufferData(data, usage);
	}
	
	public VertexBufferObject(int target, int usage, Object dataArray) {
		vboID = GL15.glGenBuffers();
		this.target = target;
		Buffer data = BufferUtil.wrap(dataArray);
		this.elemLength = data.limit() - data.position();
		this.byteLength = elemLength * BufferUtil.getElemByteSize(data);
		bufferData(data, usage);
	}
	
	public void bufferData(Buffer data, int usage) {
		ByteBuffer buffer = BufferUtil.asByteBuffer(data);
		int oldPos = buffer.position();
		int oldLim = buffer.limit();
		BufferUtil.copyPosAndLimit(data, buffer);
		GL15.glBindBuffer(target, vboID);
		GL15.glBufferData(target, buffer, usage);
		buffer.position(oldPos).limit(oldLim);
	}
	
	public void bufferSubData(Buffer data, int offset) {
		ByteBuffer buffer = BufferUtil.asByteBuffer(data);
		int oldPos = buffer.position();
		int oldLim = buffer.limit();
		BufferUtil.copyPosAndLimit(data, buffer);
		GL15.glBindBuffer(target, vboID);
		GL15.glBufferSubData(target, offset, buffer);
		buffer.position(oldPos).limit(oldLim);
	}
	
	public void assign() {
		GL15.glBindBuffer(target, vboID);
	}

}
