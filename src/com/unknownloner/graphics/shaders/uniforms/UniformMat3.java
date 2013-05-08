package com.unknownloner.graphics.shaders.uniforms;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import com.unknownloner.math.Mat3;

public class UniformMat3 extends ShaderUniform {
	
	private final Mat3 value;
	private static final FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(3 * 3);

	public UniformMat3(int location, Mat3 value) {
		super(location);
		this.value = value;
	}

	@Override
	public void assign() {
		dataBuffer.clear();
		value.store(dataBuffer);
		dataBuffer.flip();
		GL20.glUniformMatrix3(location, false, dataBuffer);
	}

}
