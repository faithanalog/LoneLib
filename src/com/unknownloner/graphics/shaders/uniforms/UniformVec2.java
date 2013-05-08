package com.unknownloner.graphics.shaders.uniforms;

import org.lwjgl.opengl.GL20;

import com.unknownloner.math.Vec2;

public class UniformVec2 extends ShaderUniform {
	
	private final Vec2 value;

	public UniformVec2(int location, Vec2 value) {
		super(location);
		this.value = value;
	}

	@Override
	public void assign() {
		GL20.glUniform2f(location, value.getX(), value.getY());
	}

}
