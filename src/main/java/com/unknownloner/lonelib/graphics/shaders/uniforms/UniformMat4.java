package com.unknownloner.lonelib.graphics.shaders.uniforms;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import com.unknownloner.lonelib.math.Mat4;

public class UniformMat4 extends ShaderUniform {

	private final Mat4 value;
	private static final FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(16);
	
	public UniformMat4(int location, Mat4 value) {
		super(location);
		this.value = value;
	}

	@Override
	public void assign() {
		dataBuffer.clear();
		value.store(dataBuffer);
		dataBuffer.flip();
		GL20.glUniformMatrix4(location, false, dataBuffer);
	}

}
