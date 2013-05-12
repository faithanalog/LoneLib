This is a library for use with OpenGL and Java, specifically LWJGL
Feel free to fork and optimize/add anything you think may be useful, pull requets to add new features are welcome

Guidelines for usage: (Will be moved to wiki possibly later)
1. Generally avoid making referrences to any classes in LoneLib before creating a GL context (Display.create()).
This is because some classes (such as FontRenderer) execute code which requires a GL context to exist
upon class initialization.

2. When updating data in a VertexBufferObject/Texture, using the object's methods will always
bind the buffer/texture every time a change is made. Only use them when updating data if you
don't know that the buffer/texture is already bound.
EX: We have a VBO created like tihs
VertexBufferObject vbo = new VertexBufferObject(GL15.GL_ARRAY_BUFFER, GL15.GL_STREAM_DRAW, 1000);
And we want to update it's data. We have 3 ByteBuffers, data1, data2, data3

Don't do this:
public void update() {
  vbo.bufferSubData(data1, 0);
  vbo.bufferSubData(data2, 0);
  vbo.bufferSubData(data3, 0);
}

Do this instead:
public void update() {
  vbo.assign();
  GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, data1);
  GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, data2);
  GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, data3);
}

But it's perfectly fine to do this:
public void update() {
  vbo.bufferSubData(data1, 0);
}

3. When rendering dynamic text, the font renderer splits the input string into
chunks of 128 characters for rendering. The less chunks of 128 characters there are,
the more effecient the renderer will be.

More to come as more features are put in...
