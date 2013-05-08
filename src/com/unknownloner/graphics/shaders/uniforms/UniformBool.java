package com.unknownloner.graphics.shaders.uniforms;

public class UniformBool extends UniformInt {

	public UniformBool(int location, boolean value) {
		super(location, value ? 1 : 0);
	}

}
