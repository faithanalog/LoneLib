package com.unknownloner.lonelib.graphics.buffers;

import org.lwjgl.opengl.GL20;

/**
 * A VertrexAttribPointer which converts all data bits to floats in the GPU. If set to normalize, data will be normalized as it's sent to the GPU.
 */
public class VertexAttribPointer implements IVertexAttribPointer {

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

    @Override
	public VertexBufferObject getBuffer() {
		return buffer;
	}

    @Override
	public int getAttribIndex() {
		return attribIndex;
	}

    @Override
	public int getElemSize() {
		return elemSize;
	}

    @Override
	public int getDataType() {
		return dataType;
	}

	public boolean shouldNormalize() {
		return shouldNormalize;
	}

    @Override
	public int getStride() {
		return stride;
	}

    @Override
	public int getOffset() {
		return offset;
	}

    @Override
	public void assign() {
		assign(true);
	}

    @Override
	public void assign(boolean assignBuffer) {
		if(assignBuffer)
			buffer.assign();
		GL20.glEnableVertexAttribArray(attribIndex);
		GL20.glVertexAttribPointer(attribIndex, elemSize, dataType, shouldNormalize, stride, offset);
	}

}
