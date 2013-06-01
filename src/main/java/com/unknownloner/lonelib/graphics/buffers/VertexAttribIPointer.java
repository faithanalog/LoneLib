package com.unknownloner.lonelib.graphics.buffers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * A VertexAttribPointer which only accepts integer-type, both signed and unsigned.
 */
public class VertexAttribIPointer implements IVertexAttribPointer {

    private VertexBufferObject buffer;
    private int attribIndex;
    private int elemSize;
    private int dataType;
    private int stride;
    private int offset;

    public VertexAttribIPointer(VertexBufferObject buffer, int attribIndex, int elemSize, int dataType, int stride, int offset) {
        this.buffer = buffer;
        this.attribIndex = attribIndex;
        this.elemSize = elemSize;
        switch (dataType) {
            case GL11.GL_INT:
            case GL11.GL_UNSIGNED_INT:
            case GL11.GL_SHORT:
            case GL11.GL_UNSIGNED_SHORT:
            case GL11.GL_BYTE:
            case GL11.GL_UNSIGNED_BYTE:
                this.dataType = dataType;
                break;
            default:
                throw new Error("Invalid data type used for VertexAttribIPointer. must be of type BYTE, SHORT, INT, or UNSIGNED_");
        }

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
        GL30.glVertexAttribIPointer(attribIndex, elemSize, dataType, stride, offset);
    }

}
