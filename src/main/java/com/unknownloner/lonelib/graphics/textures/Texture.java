package com.unknownloner.lonelib.graphics.textures;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import com.unknownloner.lonelib.util.BufferUtil;
import com.unknownloner.lonelib.util.ImageUtil;

public class Texture {
	
	public final int texID = GL11.glGenTextures();
	private boolean smoothScale;
	private boolean clamp;
	private boolean mipmap;
	private boolean deleted;
	
	/**
	 * Creates a texture based on the image's data
	 * @param srcImage An image to load
	 */
	public Texture(BufferedImage srcImage, boolean smoothScale, boolean clamp, boolean mipmap) {
		this.smoothScale = smoothScale;
		this.clamp = clamp;
		this.mipmap = mipmap;
		int width = srcImage.getWidth();
		int height = srcImage.getHeight();
		int[] data = new int[width * height];
		srcImage.getRGB(0, 0, width, height, data, 0, width);
		ByteBuffer dataBuffer = BufferUtils.createByteBuffer(width * height * 4);
		for(int color : data) {
			dataBuffer.put((byte) (color >> 16 & 0xFF));
			dataBuffer.put((byte) (color >> 8 & 0xFF));
			dataBuffer.put((byte) (color & 0xFF));
			dataBuffer.put((byte) (color >> 24 & 0xFF));
		}
		dataBuffer.flip();
		bufferData(dataBuffer, width, height);
	}
	
	/**
	 * Loads an image from imgPath and uses it to create a texture
	 * @param imgPath The path to an image, in jar or out of jar
	 */
	public Texture(String imgPath, boolean smoothScale, boolean clamp, boolean mipmap) {
		this(ImageUtil.loadImg(imgPath), smoothScale, clamp, mipmap);
	}
	
	
	/**
	 * Creates a texture based on the provided data
	 * @param colorBytes a byte array containing color in the order R, G, B, A
	 * Does not have to be direct
	 */
	public Texture(ByteBuffer colorBytes, int width, int height, boolean smoothScale, boolean clamp) {
		this.smoothScale = smoothScale;
		this.clamp = clamp;
		ByteBuffer dataBuffer = BufferUtil.createByteBuffer(width * height * 4);
		for(int i = width * height * 4; i > 0; i--) {
			dataBuffer.put(colorBytes.get());
		}
		dataBuffer.flip();
		bufferData(colorBytes, width, height);
	}
	
	/**
	 * Creates a texture based on the provided data
	 * @param colorFloats a float array containing color in the order R, G, B, A
	 * Does not have to be direct
	 */
	public Texture(FloatBuffer colorFloats, int width, int height, boolean smoothScale, boolean clamp) {
		this.smoothScale = smoothScale;
		this.clamp = clamp;
		ByteBuffer dataBuffer = BufferUtils.createByteBuffer(width * height * 4);
		for(int i = width * height * 4; i > 0; i--) {
			dataBuffer.put((byte) (colorFloats.get() * 0xFF));
		}
		dataBuffer.flip();
		bufferData(dataBuffer, width, height);
	}
	
	/**
	 * Creates a texture based on the provided data
	 * @param packedColorInts An int buffer containing color data as ARGB packed integers or RGB packed integers
	 * Does not have to be direct
	 * @param alpha Whether the data has alpha included. If false, alpha will default to 100%
	 */
	public Texture(IntBuffer packedColorInts, int width, int height, boolean alpha, boolean smoothScale, boolean clamp) {
		this.smoothScale = smoothScale;
		this.clamp = clamp;
		ByteBuffer dataBuffer = BufferUtils.createByteBuffer(width * height * 4);
		for(int i = width * height; i > 0; i--) {
			int color = packedColorInts.get();
			dataBuffer.put((byte) (color >> 16 & 0xFF));
			dataBuffer.put((byte) (color >> 8 & 0xFF));
			dataBuffer.put((byte) (color & 0xFF));
			if(alpha)
				dataBuffer.put((byte) (color >> 24 & 0xFF));
			else
				dataBuffer.put((byte)0xFF);
		}
		dataBuffer.flip();
		bufferData(dataBuffer, width, height);
	}
	
	/**
	 * Generates the texture object with the given bytes
	 * @param bytes A byte buffer containing color data as R G B A
	 * @param width Width of the texture
	 * @param height Height of the texture
	 */
	public void bufferData(ByteBuffer bytes, int width, int height) {
		if(!bytes.isDirect()) {
			if(!bytes.hasArray()) {
				System.err.println("Error generating texture: Buffer is not direct and has no backing array");
				return;
			}
			ByteBuffer directBuffer = BufferUtil.createByteBuffer(width * height * 4);
			directBuffer.put(bytes.array(), bytes.position(), width * height * 4);
			bytes = directBuffer;
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, (smoothScale ? GL11.GL_LINEAR : GL11.GL_NEAREST));
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, (smoothScale ? 
				(mipmap ? GL11.GL_LINEAR_MIPMAP_LINEAR : GL11.GL_LINEAR) :
				(mipmap ? GL11.GL_NEAREST_MIPMAP_LINEAR : GL11.GL_NEAREST)));
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, (clamp ? GL12.GL_CLAMP_TO_EDGE : GL11.GL_REPEAT));
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, (clamp ? GL12.GL_CLAMP_TO_EDGE : GL11.GL_REPEAT));
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bytes);
		if(mipmap) {
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		}
	}
	
	/**
	 * Updates data in the texture
	 * @param bytes A byte buffer containing color data as R G B A
	 * @param xOff X offset into the texture
	 * @param yOff Y offset into the texture
	 * @param width Width of the data
	 * @param height Height of the data
	 */
	public void bufferSubData(ByteBuffer bytes, int xOff, int yOff, int width, int height) {
		if(!bytes.isDirect()) {
			if(!bytes.hasArray()) {
				System.err.println("Error generating texture: Buffer is not direct and has no backing array");
				return;
			}
			ByteBuffer directBuffer = BufferUtil.createByteBuffer(width * height * 4);
			directBuffer.put(bytes.array(), bytes.position(), width * height * 4);
			bytes = directBuffer;
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, xOff, yOff, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bytes);
	}
	
	public void assign(int textureUnit) {
		GL13.glActiveTexture(textureUnit);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
	}
	
	public void assign() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
	}
	
	public void delete() {
		if(!deleted) {
			deleted = true;
			GL11.glDeleteTextures(texID);
		}
	}
	
	@Override
	protected void finalize() {
		delete();
	}
	
	

}
