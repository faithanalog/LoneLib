package com.unknownloner.test;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.PixelFormat;

import com.unknownloner.graphics.buffers.VertexArrayObject;
import com.unknownloner.graphics.buffers.VertexAttribPointer;
import com.unknownloner.graphics.buffers.VertexBufferObject;
import com.unknownloner.graphics.shaders.ShaderProgram;
import com.unknownloner.math.Mat4;
import com.unknownloner.math.Vec3;
import com.unknownloner.math.Vec4;
import com.unknownloner.util.GLUtil;
import com.unknownloner.util.MathUtil;

public class Test {
	
	public static ShaderProgram program;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		initGL();
		program.bindAttribLoc(0, "vPosition");
		program.bindAttribLoc(1, "vColor");
		program.setUniform("Projection", Mat4.ortho(0, 854, 0, 480, -1, 1));
		program.setUniform("Model", Mat4.IDENTITY.translate(new Vec3(854 / 2, 480 / 2, 0)).scale(new Vec3(2, 2, 2)));
		
		VertexBufferObject quadBuffer = new VertexBufferObject(
		GL15.GL_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, new float[] {
				-50F,  50F, 0F, 1F, 0F, 0F, 1F,
				-50F, -50F, 0F, 0F, 1F, 0F, 1F,
				 50F, -50F, 0F, 0F, 0F, 1F, 1F,
				 50F,  50F, 0F, 1F, 1F, 1F, 1F
		});
		VertexBufferObject indBuffer = new VertexBufferObject(
		GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_STATIC_DRAW, new short[] {
				0, 1, 2, 2, 3, 0
		});
		VertexArrayObject vao = new VertexArrayObject(
				new VertexAttribPointer(quadBuffer, 0, 3, GL11.GL_FLOAT, false, 28, 0),
				new VertexAttribPointer(quadBuffer, 1, 4, GL11.GL_FLOAT, false, 28, 3 * 4)
		);
		float x = 854 / 2;
		float y = 480 / 2;
		float velX = 1;
		float velY = 1;
		if(args.length != 0) {
			try {
				float vel = Float.parseFloat(args[0]);
				velX = vel;
				velY = vel;
			} catch (Exception e) {
				
			}
		}
		while(!Display.isCloseRequested()) {
			x += velX;
			y += velY;
			if(x >= 754 || x <= 100) {
				velX *= -1;
			}
			if(y >= 380 || y <= 100) {
				velY *= -1;
			}
			
			program.setUniform("Model", Mat4.IDENTITY.translate(new Vec3(x, y, 0))
					.rotate(MathUtil.toRadians(x), new Vec3(0F, 0F, -1F))
					.scale(new Vec3(2, 2, 2)));
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			program.assign();
			vao.assign();
			indBuffer.assign();
			GL11.glDrawElements(GL11.GL_TRIANGLES, indBuffer.elemLength, GL11.GL_UNSIGNED_SHORT, 0);
			vao.unassign();
			GLUtil.exitOnError("Main Section");
			Display.update();
			Display.sync(60);
			
		}
		Display.destroy();
		System.exit(0);
	}
	
	private static void initGL() {
		PixelFormat pixelFormat = new PixelFormat(0, 8, 0, 4);
		ContextAttribs contextAttributes = new ContextAttribs(2, 0);
		Display.setTitle("Test");
//		Display.setVSyncEnabled(true);
		try {
			Display.setDisplayMode(new DisplayMode(854, 480));
			Display.create(pixelFormat, contextAttributes);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		program = new ShaderProgram("/com/unknownloner/graphics/shaders/PrimaryShader.vert",
				"/com/unknownloner/graphics/shaders/PrimaryShader.frag");
	}
}
