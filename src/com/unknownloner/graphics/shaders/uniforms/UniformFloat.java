package com.unknownloner.graphics.shaders.uniforms;

import org.lwjgl.opengl.GL20;

public class UniformFloat extends ShaderUniform {

	public final float value;
	
	public UniformFloat(int location, float value) {
		super(location);
		this.value = value;
	}
	
	public void assign() {
		GL20.glUniform1f(location, value);
	}

}
