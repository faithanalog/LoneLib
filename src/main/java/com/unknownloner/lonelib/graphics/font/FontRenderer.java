package com.unknownloner.lonelib.graphics.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.unknownloner.lonelib.graphics.buffers.VertexArrayObject;
import com.unknownloner.lonelib.graphics.buffers.VertexAttribPointer;
import com.unknownloner.lonelib.graphics.buffers.VertexBufferObject;
import com.unknownloner.lonelib.graphics.shaders.ShaderProgram;
import com.unknownloner.lonelib.graphics.textures.Texture;
import com.unknownloner.lonelib.math.Vec3;
import com.unknownloner.lonelib.util.BufferUtil;
import com.unknownloner.lonelib.util.MathUtil;

public class FontRenderer {
	
	
	public static ShaderProgram shaderProgram = new ShaderProgram("/shaders/FontShader.vert", "/shaders/FontShader.frag");
	private Texture fontTexture;
	private boolean[] displayable = new boolean[256];
	private float[] widths = new float[256];
	private float[] texWidths = new float[256];
	private FloatBuffer dataBuffer = BufferUtil.createFloatBuffer(128 * 4);
	private VertexBufferObject data = new VertexBufferObject(GL15.GL_ARRAY_BUFFER);
	private VertexBufferObject inds = new VertexBufferObject(GL15.GL_ELEMENT_ARRAY_BUFFER);
	private VertexArrayObject vao = new VertexArrayObject(
			new VertexAttribPointer(data, 0, 3, GL11.GL_FLOAT, false, 9 * 4, 0),     //Position
			new VertexAttribPointer(data, 1, 4, GL11.GL_FLOAT, false, 9 * 4, 3 * 4), //Color
			new VertexAttribPointer(data, 2, 2, GL11.GL_FLOAT, false, 9 * 4, 7 * 4)  //Tex Coords
	);
	
	public FontRenderer(Font font) {
		int size = font.getSize();
		int cellSize = MathUtil.nextPowerOfTwo(size);
		BufferedImage img = new BufferedImage(cellSize * 16, cellSize * 16, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setColor(new Color(255, 255, 255, 0));
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		g.setColor(new Color(255, 255, 255, 255));
		g.setFont(font);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		int descent = g.getFontMetrics().getDescent();
		for(int i = 0; i < 256; i++) {
			if(font.canDisplay(i)) {
				int x = (i % 16) * cellSize;
				int y = (i / 16) * cellSize - descent;
				displayable[i] = true;
				String charString = new StringBuilder().append((char)i).toString();
				int width = g.getFontMetrics().stringWidth(charString);
				widths[i] = width / (float)size;
				texWidths[i] = width / (float)img.getWidth();
				g.drawString(charString, x, y);
			}
		}
		g.dispose();
		fontTexture = new Texture(img, false, true);
	}
	
	private int recurseLevel = 0;
	public void drawString(String text, Vec3 startPos, float charSize) {
		if(recurseLevel == 0) {
			fontTexture.assign(0);
			shaderProgram.assign();
			vao.assign();
			inds.assign();
		}
		if(text.length() > 128) {
			recurseLevel++;
			String str1 = text.substring(0, 128);
			String str2 = text.substring(128);
			drawString(str1, startPos, charSize);
			drawString(str2, new Vec3(startPos.getX() + stringWidth(str1, charSize), startPos.getY(), startPos.getZ()), charSize);
			recurseLevel--;
		}
		
		
	}
	
	public float stringWidth(String text, float charSize) {
		char[] textChars = text.toCharArray();
		float width = 0;
		for(char c : textChars) {
			width += widths[c] * charSize;
		}
		return width;
	}

}
