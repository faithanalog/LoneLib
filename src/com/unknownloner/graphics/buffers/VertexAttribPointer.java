package com.unknownloner.graphics.buffers;

import org.lwjgl.opengl.GL20;

public class VertexAttribPointer {

	private VertexBufferObject buffer;
	private int attribIndex;
	private int elemSize;
	private int dataType;
	private boolean shouldNormalize;
	private int stride;
	private int offset;
	
	public VertexAttribPointer(VertexBufferObject buffer, int attribIndex, int elemSize,
			int dataType, boolean shouldNormalize, int stride, int offset) {
		this.buffer = buffer;
		this.attribIndex = attribIndex;
		this.elemSize = elemSize;
		this.dataType = dataType;
		this.shouldNormalize = shouldNormalize;
		this.stride = stride;
		this.offset = offset;
	}
	
	public VertexBufferObject getBuffer() {
		return buffer;
	}

	public int getAttribIndex() {
		return attribIndex;
	}

	public int getElemSize() {
		return elemSize;
	}

	public int getDataType() {
		return dataType;
	}

	public boolean shouldNormalize() {
		return shouldNormalize;
	}

	public int getStride() {
		return stride;
	}

	public int getOffset() {
		return offset;
	}

	public void assign() {
		assign(true);
	}
	
	public void assign(boolean assignBuffer) {
		if(assignBuffer)
			buffer.assign();
		GL20.glEnableVertexAttribArray(attribIndex);
		GL20.glVertexAttribPointer(attribIndex, elemSize, dataType, shouldNormalize, stride, offset);
	}

}
