package com.unknownloner.lonelib.graphics.font;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import com.unknownloner.lonelib.graphics.Mesh;
import com.unknownloner.lonelib.graphics.buffers.VertexArrayObject;
import com.unknownloner.lonelib.graphics.buffers.VertexAttribPointer;
import com.unknownloner.lonelib.graphics.buffers.VertexBufferObject;
import com.unknownloner.lonelib.math.Vec3;
import com.unknownloner.lonelib.math.Vec4;

public class TextMesh implements Mesh {
	
	private List<String> lines = new ArrayList<String>();
	private VertexBufferObject vertBuffer = new VertexBufferObject(GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, 0);
	private VertexBufferObject indBuffer = new VertexBufferObject(GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, 0);
	private VertexArrayObject vao = new VertexArrayObject(
			new VertexAttribPointer(vertBuffer, 0, 3, GL11.GL_FLOAT, false, 9 * 4, 0),     //Position
			new VertexAttribPointer(vertBuffer, 1, 4, GL11.GL_FLOAT, false, 9 * 4, 3 * 4), //Color
			new VertexAttribPointer(vertBuffer, 2, 2, GL11.GL_FLOAT, false, 9 * 4, 7 * 4)  //Tex Coords
	);
	private FontRenderer fr;
	private int vertCount;
	private int indCount;
	private float textSize;
	private Vec3 pos;
	private Vec4 color;
	
	/**
	 * Creates a TextMesh which will use the given FontRenderer to generate vertices
	 * @param fr FontRenderer used to gen vertices. the FontRenderer's shader is <b>NOT</b> assigned by {@link #render()}
	 * @param textSize Point size of text
	 * @param topLeft Top left corner of the text mesh
	 * @param color Color of the text mesh
	 */
	public TextMesh(FontRenderer fr, float textSize, Vec3 topLeft, Vec4 color) {
		this.fr = fr;
		this.textSize = textSize;
		this.pos = topLeft.sub(new Vec3(0, textSize, 0));
		this.color = color;
	}
	
	/**
	 * Creates a TextMesh which will use the given FontRenderer to generate vertices, initialized with provided
	 * lines of text
	 * @param fr FontRenderer used to gen vertices. the FontRenderer's shader is <b>NOT</b> assigned by {@link #render()}
	 * @param textSize Point size of text
	 * @param topLeft Top left corner of the text mesh
	 * @param color Color of the text mesh
	 * @param initialText Initial lines of text in the mesh
	 */
	public TextMesh(FontRenderer fr, float textSize, Vec3 topLeft, Vec4 color, String... initialText) {
		this(fr, textSize, topLeft, color);
		if(initialText != null) {
			for(String str : initialText) {
				addLine(str);
			}
		}
	}
	
	/**
	 * Adds a line of text to the mesh.
	 * <br>The line will appear under all the preceding lines
	 * @param line Line to add
	 * @return The line's index in the list of lines. Use with {@link #removeLine(int)}
	 */
	public int addLine(String line) {
		StringBuilder filteredLine = new StringBuilder();
		for(char c : line.toCharArray()) {
			if(fr.canDisplay(c)) {
				filteredLine.append(c);
			}
		}
		line = filteredLine.toString();
		vertCount += line.length() * 4;
		indCount += line.length() * 6;
		lines.add(line);
		return lines.size() - 1;
	}
	
	/**
	 * Removes the line of text at index from the mesh
	 * @param index Index of line to remove. The index will have been
	 * returned by {@link #addLine(String)}
	 */
	public void removeLine(int index) {
		String removal = lines.remove(index);
		vertCount -= removal.length() * 4;
		indCount -= removal.length() * 6;
	}
	
	/**
	 * Returns underlying list of the lines of text in this mesh.
	 * <br>Changes in this list will be reflected in the mesh after the next
	 * invocation of {@link #finalizeMesh()}
	 * @return The underlying list of the lines of text in this mesh.
	 */
	public List<String> getLines() {
		return lines;
	}
	
	@Override
	public void finalizeMesh() {
		int vertByteAmnt = vertCount * 9 * 4; /*9 floats per vert*/
		int indByteAmnt = indCount * 2; /* shorts */
		vertBuffer.bufferData(vertByteAmnt, GL15.GL_STATIC_DRAW);
		indBuffer.bufferData(indByteAmnt, GL15.GL_STATIC_DRAW);
		FloatBuffer verts = GL30.glMapBufferRange(GL15.GL_ARRAY_BUFFER, 0, vertByteAmnt,
				GL30.GL_MAP_WRITE_BIT | GL30.GL_MAP_UNSYNCHRONIZED_BIT, null).asFloatBuffer();
		ShortBuffer inds = GL30.glMapBufferRange(GL15.GL_ELEMENT_ARRAY_BUFFER, 0, indByteAmnt,
				GL30.GL_MAP_WRITE_BIT | GL30.GL_MAP_UNSYNCHRONIZED_BIT, null).asShortBuffer();
		float x = pos.getX();
		float y = pos.getY();
		float z = pos.getZ();
		float r = color.getX();
		float g = color.getY();
		float b = color.getZ();
		float a = color.getW();
		short ind = 0;
		for(String line : lines) {
			fr.addString(verts, line, x, y, z, r, g, b, a, textSize, true);
			for(int i = 0; i < line.length(); i++) {
				inds.put(new short[] {
						ind,
						(short)(ind + 1),
						(short)(ind + 2),
						(short)(ind + 2),
						(short)(ind + 3),
						ind
				});
				ind += 4;
			}
			y -= textSize;
			
		}
		GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
		GL15.glUnmapBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER);
	}

	@Override
	public void render() {
		vao.assign();
		indBuffer.assign();
		GL11.glDrawElements(GL11.GL_TRIANGLES, indCount, GL11.GL_UNSIGNED_SHORT, 0);
	}

	@Override
	public void delete() {
		vao.delete();
		indBuffer.delete();
		vertBuffer.delete();
	}

	@Override
	public void reset() {
		vertBuffer.bufferData(0, GL15.GL_STATIC_DRAW);
		indBuffer.bufferData(0, GL15.GL_STATIC_DRAW);
	}

}
