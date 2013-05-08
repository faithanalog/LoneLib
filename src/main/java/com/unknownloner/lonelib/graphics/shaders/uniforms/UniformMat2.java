package com.unknownloner.lonelib.graphics.shaders.uniforms;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import com.unknownloner.lonelib.math.Mat2;

public class UniformMat2 extends ShaderUniform {
	
	private final Mat2 value;
	private static final FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(2 * 2);

	public UniformMat2(int location, Mat2 value) {
		super(location);
		this.value = value;
	}

	@Override
	public void assign() {
		dataBuffer.clear();
		value.store(dataBuffer);
		dataBuffer.flip();
		GL20.glUniformMatrix2(location, false, dataBuffer);
	}

}
