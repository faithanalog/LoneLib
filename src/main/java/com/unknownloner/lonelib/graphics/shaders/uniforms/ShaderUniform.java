package com.unknownloner.lonelib.graphics.shaders.uniforms;

public abstract class ShaderUniform {
	
	public final int location;
	
	public ShaderUniform(int location) {
		this.location = location;
	}
	
	public abstract void assign();

}
