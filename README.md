This is a library for use with OpenGL and Java, specifically LWJGL.
Feel free to fork and optimize/add anything you think may be useful, pull requests to add new features are welcome.

To build to a jar:

1. Install maven: http://maven.apache.org/
2. Open command prompt/terminal
3. Navigate to the root directory of LoneLib (which contains pom.xml)
4. Type the following:
```bash
mvn clean install
```

And it will put a Jar called LoneLib-1.0-SNAPSHOT.jar in a new folder called target

------------------------------------------------------------

Guidelines for usage: (Will be moved to wiki possibly later)

1. Generally avoid making references to any classes in LoneLib before creating a GL context (Display.create()).
This is because some classes (such as FontRenderer) execute code which requires a GL context to exist upon class 
initialization.

2. When updating data in a VertexBufferObject/Texture, using the object's methods will always bind the buffer/texture
every time a change is made. Only use them when updating data if you don't know that the buffer/texture is already
bound. 
EX: We have a VBO created like with
`VertexBufferObject vbo = new VertexBufferObject(GL15.GL_ARRAY_BUFFER, GL15.GL_STREAM_DRAW, 1000);`
And we want to update it's data. We have 3 ByteBuffers, `data1`, `data2`, `data3`

Don't do this:
```
public void update() {
  vbo.bufferSubData(data1, 0);
  vbo.bufferSubData(data2, 0);
  vbo.bufferSubData(data3, 0);
}
```
Do this instead:
```
public void update() {
  vbo.assign();
  GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, data1);
  GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, data2);
  GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, data3);
}
```
But it's perfectly fine to do this:
```
public void update() {
  vbo.bufferSubData(data1, 0);
}
```
3.When rendering dynamic text, the font renderer splits the input string into chunks of 128 characters for rendering. 
The less chunks of 128 characters there are, the more efficient the renderer will be.

More to come as more features are put in...
