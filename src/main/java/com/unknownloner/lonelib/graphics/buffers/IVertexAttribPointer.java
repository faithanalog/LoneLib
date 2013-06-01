package com.unknownloner.lonelib.graphics.buffers;


public interface IVertexAttribPointer {

    VertexBufferObject getBuffer();

    int getAttribIndex();

    int getElemSize();

    int getDataType();

    int getStride();

    int getOffset();

    void assign();

    void assign(boolean assignBuffer);
}
