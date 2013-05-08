package com.unknownloner.lonelib.graphics.shaders.uniforms;

import org.lwjgl.opengl.GL20;

public class UniformInt extends ShaderUniform {

	public final int value;
	
	public UniformInt(int location, int value) {
		super(location);
		this.value = value;
	}

	@Override
	public void assign() {
		GL20.glUniform1i(location, value);
	}

}
