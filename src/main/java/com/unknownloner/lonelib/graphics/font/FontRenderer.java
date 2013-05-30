package com.unknownloner.lonelib.graphics.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import com.unknownloner.lonelib.graphics.buffers.VertexArrayObject;
import com.unknownloner.lonelib.graphics.buffers.VertexAttribPointer;
import com.unknownloner.lonelib.graphics.buffers.VertexBufferObject;
import com.unknownloner.lonelib.graphics.shaders.ShaderProgram;
import com.unknownloner.lonelib.graphics.textures.Texture;
import com.unknownloner.lonelib.math.Vec3;
import com.unknownloner.lonelib.math.Vec4;
import com.unknownloner.lonelib.util.BufferUtil;
import com.unknownloner.lonelib.util.ImageUtil;
import com.unknownloner.lonelib.util.MathUtil;

public class FontRenderer {


    public static ShaderProgram globalFontProgram;
	private static VertexBufferObject data = new VertexBufferObject(GL15.GL_ARRAY_BUFFER, GL15.GL_STREAM_DRAW, 128 * 9 * 4 * 4);
	private static VertexBufferObject inds = new VertexBufferObject(GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, 128 * 6 * 2);

    static {
        inds.assign();
        ShortBuffer indBuf = BufferUtil.createShortBuffer(128 * 6);
        for (short i = 0; i < 128 * 4; i += 4) {
            short[] ind = new short[]{
                    i,
                    (short) (i + 1),
                    (short) (i + 2),
                    (short) (i + 2),
                    (short) (i + 3),
                    i
            };
            indBuf.put(ind);
        }
        indBuf.flip();
        inds.bufferSubData(indBuf, 0);
        try {
            globalFontProgram = new ShaderProgram("/shaders/FontShader.vert", "/shaders/FontShader.frag");
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }
	
	private Texture fontTexture;
	private ShaderProgram shaderProgram = globalFontProgram;
	private boolean[] displayable = new boolean[256];
	private float[] widths = new float[256];
	private float[] texWidths = new float[256];
	private float texHeight;
	private float sizeOverPt;
	private VertexArrayObject vao = new VertexArrayObject(
			new VertexAttribPointer(data, 0, 3, GL11.GL_FLOAT, false, 9 * 4, 0),     //Position
			new VertexAttribPointer(data, 1, 4, GL11.GL_FLOAT, false, 9 * 4, 3 * 4), //Color
			new VertexAttribPointer(data, 2, 2, GL11.GL_FLOAT, false, 9 * 4, 7 * 4)  //Tex Coords
	);
	
	public FontRenderer(Font font) {
		int size = ImageUtil.ascent(font) + ImageUtil.descent(font);
		sizeOverPt = size / font.getSize2D();
		int cellSize = MathUtil.nextPowerOfTwo(size);
		BufferedImage img = new BufferedImage(cellSize * 16, cellSize * 16, BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < img.getWidth(); x++) {
			for(int y = 0; y < img.getHeight(); y++) {
				img.setRGB(x, y, 0x00FFFFFF);
			}
		}
		texHeight = size / (float)img.getHeight();
		Graphics2D g = img.createGraphics();
		g.setColor(new Color(255, 255, 255, 255));
		g.setFont(font);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		int ascent = g.getFontMetrics().getAscent();
		for(int i = 0; i < 256; i++) {
			if(font.canDisplay(i)) {
				int x = (i % 16) * cellSize;
				int y = (i / 16) * cellSize + ascent; //Text is drawn from the baseline
				displayable[i] = true;
				String charString = Character.toString((char)i);
				int width = g.getFontMetrics().stringWidth(charString);
				widths[i] = width / (float)size;
				texWidths[i] = width / (float)img.getWidth();
				g.drawString(charString, x, y);
			}
		}
		g.dispose();
		fontTexture = new Texture(img, true, true, false);
	}
	
	public void setShaderProgram(ShaderProgram prgm, int posAttrib, int colorAttrib, int texCoordAttrib) {
		this.shaderProgram = prgm;
		vao.delete();
		vao = new VertexArrayObject(
				new VertexAttribPointer(data, posAttrib,      3, GL11.GL_FLOAT, false, 9 * 4, 0),     //Position
				new VertexAttribPointer(data, colorAttrib,    4, GL11.GL_FLOAT, false, 9 * 4, 3 * 4), //Color
				new VertexAttribPointer(data, texCoordAttrib, 2, GL11.GL_FLOAT, false, 9 * 4, 7 * 4)  //Tex Coords
		);
	}
	
	public ShaderProgram getShaderProgram() {
		return this.shaderProgram;
	}
	
	public void bindFontTexture(int textureUnit) {
		fontTexture.assign(textureUnit);
	}
	
	/**
	 * Draws a string at the given position
	 * @param text Text to draw
	 * @param startPos Bottom left hand corner to start at. String will increment x as it renders
	 * @param color Color of the string
	 * @param charSize Point size of text to draw
	 */
	public void drawString(String text, Vec3 startPos, Vec4 color, float charSize) {
		//Adjust to be accurate to the original font's point value
		charSize *= sizeOverPt;
		fontTexture.assign(GL13.GL_TEXTURE0);
		shaderProgram.assign();
		vao.assign();
		data.assign();
		inds.assign();
		int strPos = 0;
		char[] textChars = text.toCharArray();
		float x = startPos.getX();
		float y = startPos.getY();
		float z = startPos.getZ();
		//TODO add support for color changes mid string, possibly with control characters
		float r = color.getX();
		float g = color.getY();
		float b = color.getZ();
		float a = color.getW();
		do {
			int remaining = textChars.length - strPos;
			int bytesToMap = remaining < 128 ? remaining * 144 /* 9 * 4 * 4 */ : 18432 /* 128 * 9 * 4 * 4 */;
			
			//Gets a ByteBuffer as a float buffer, the byte buffer being a java wrapper to a C pointer. This basically
			//creates a memory map of the entire text rendering VBO, which will map directly to the GPU when it can
			//GL_MAP_UNSYCNHRONIZED_BIT is used because it speeds up the process
			FloatBuffer dataBuffer = GL30.glMapBufferRange(GL15.GL_ARRAY_BUFFER, 0, bytesToMap,
					GL30.GL_MAP_WRITE_BIT | GL30.GL_MAP_UNSYNCHRONIZED_BIT, null).asFloatBuffer();
			int charsToDisplay = 0;
			for(int i = strPos, max = (remaining < 128 ? textChars.length : strPos + 128); i < max; i++) {
				char c = textChars[i];
				if(canDisplay(c)) {
					charsToDisplay++;
					float tX = (c % 16) / 16F;
					float tY = (c / 16) / 16F;
					float width = widths[c] * charSize;
					dataBuffer.put(new float[] {
							x,            y + charSize, z, r, g, b, a, tX,                tY,
							x,            y,            z, r, g, b, a, tX,                tY + texHeight,
							x + width,    y,            z, r, g, b, a, tX + texWidths[c], tY + texHeight,
							x + width,    y + charSize, z, r, g, b, a, tX + texWidths[c], tY,
					});
					x += width;
				}
			}
			GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
			GL11.glDrawElements(GL11.GL_TRIANGLES, charsToDisplay * 6, GL11.GL_UNSIGNED_SHORT, 0);
			
			//Orphans the buffer, allowing it to be filled with more data while the previous data renders (For consecutive string draws)
			//More info can be found here http://www.opengl.org/wiki/Buffer_Object_Streaming
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 18432 /* 128*9*4*4 */, GL15.GL_STREAM_DRAW);
			strPos += 128;
		} while(strPos < textChars.length);
	}
	
	/**
	 * Sames as {@link #drawString(String, Vec3, Vec4, float)}, but centered on startPos
	 */
	public void drawCenteredString(String text, Vec3 startPos, Vec4 color, float charSize) {
		drawString(text, new Vec3(startPos.getX() - stringWidth(text, charSize) / 2F, startPos.getY(), startPos.getZ()), color, charSize);
	}
	
	/**
	 * Returns the width of text based on the point size
	 * @param text Text to measure
	 * @param charSize Point size of text
	 * @return
	 */
	public float stringWidth(String text, float charSize) {
		//Adjust to be accurate to the original font's point value
		charSize *= sizeOverPt;
		char[] textChars = text.toCharArray();
		float width = 0;
		for(char c : textChars) {
			if(canDisplay(c)) {
				width += widths[c] * charSize;
			}
		}
		return width;
	}
	
	/**
	 * Whether or not this FontRenderer can display the character
	 * @param c character to check
	 * @return whether or not c can be displayed by the FontRenderer
	 */
	public boolean canDisplay(char c) {
		return c < 256 && displayable[c];
	}
	
	/**
	 * Adds vertex data for a character to dest
	 * @param dest FloatBuffer to put data in
	 * @param c Character to draw
	 * @param x X position of char
	 * @param y Y position of char
	 * @param z Z position of char
	 * @param r Red color channel of char
	 * @param g Green color channel of char
	 * @param b Blue color channel of char
	 * @param a Alpha color channel of char
	 * @param charSize Character point size
	 * @return
	 */
	private boolean addChar(FloatBuffer dest, char c, float x, float y, float z, float r, float g, float b, float a, float charSize) {
		if(canDisplay(c)) {
			float tX = (c % 16) / 16F;
			float tY = (c / 16) / 16F;
			float width = widths[c] * charSize;
			dest.put(new float[] {
					x,            y + charSize, z, r, g, b, a, tX,                tY,
					x,            y,            z, r, g, b, a, tX,                tY + texHeight,
					x + width,    y,            z, r, g, b, a, tX + texWidths[c], tY + texHeight,
					x + width,    y + charSize, z, r, g, b, a, tX + texWidths[c], tY,
			});
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Adds vertex data for a String to dest
	 * @param dest FloatBuffer to put data in
	 * @param c Character to draw
	 * @param x X position of string
	 * @param y Y position of string
	 * @param z Z position of string
	 * @param r Red color channel of string
	 * @param g Green color channel of string
	 * @param b Blue color channel of string
	 * @param a Alpha color channel of string
	 * @param charSize Character point size
	 * @return
	 */
	public void addString(FloatBuffer dest, String str, float x, float y, float z, float r, float g, float b, float a, float charSize, boolean colorText) {
		//TODO add use for colorText (Color coding)
		charSize *= sizeOverPt;
		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if(addChar(dest, c, x, y, z, r, g, b, a, charSize)) {
				x += widths[c] * charSize;
			}
		}
	}
	
	public TextMeshBatch createMeshBatch(String modelLoc, TextMesh... meshes) {
		TextMeshBatch batch = new TextMeshBatch(this, modelLoc);
		if(meshes != null) {
			batch.addMeshes(meshes);
		}
		return batch;
	}

}
