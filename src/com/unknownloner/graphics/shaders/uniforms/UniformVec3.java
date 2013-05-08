package com.unknownloner.graphics.shaders.uniforms;

import org.lwjgl.opengl.GL20;

import com.unknownloner.math.Vec3;

public class UniformVec3 extends ShaderUniform {

	private final Vec3 value;

	public UniformVec3(int location, Vec3 value) {
		super(location);
		this.value = value;
	}

	@Override
	public void assign() {
		GL20.glUniform3f(location, value.getX(), value.getY(), value.getZ());
	}

}
