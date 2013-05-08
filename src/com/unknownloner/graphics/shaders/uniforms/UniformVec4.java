package com.unknownloner.graphics.shaders.uniforms;

import org.lwjgl.opengl.GL20;

import com.unknownloner.math.Vec4;

public class UniformVec4 extends ShaderUniform {

	private final Vec4 value;

	public UniformVec4(int location, Vec4 value) {
		super(location);
		this.value = value;
	}

	@Override
	public void assign() {
		GL20.glUniform4f(location, value.getX(), value.getY(), value.getZ(), value.getW());
	}

}
