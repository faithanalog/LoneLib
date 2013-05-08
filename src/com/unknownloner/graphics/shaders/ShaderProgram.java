package com.unknownloner.graphics.shaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL20;

import com.unknownloner.graphics.shaders.uniforms.ShaderUniform;
import com.unknownloner.graphics.shaders.uniforms.UniformBool;
import com.unknownloner.graphics.shaders.uniforms.UniformFloat;
import com.unknownloner.graphics.shaders.uniforms.UniformInt;
import com.unknownloner.graphics.shaders.uniforms.UniformMat2;
import com.unknownloner.graphics.shaders.uniforms.UniformMat3;
import com.unknownloner.graphics.shaders.uniforms.UniformMat4;
import com.unknownloner.graphics.shaders.uniforms.UniformVec2;
import com.unknownloner.graphics.shaders.uniforms.UniformVec3;
import com.unknownloner.graphics.shaders.uniforms.UniformVec4;
import com.unknownloner.math.Mat2;
import com.unknownloner.math.Mat3;
import com.unknownloner.math.Mat4;
import com.unknownloner.math.Vec2;
import com.unknownloner.math.Vec3;
import com.unknownloner.math.Vec4;


public class ShaderProgram {
	
	public final int program;
	
	private static Map<String, Integer> varMap = new HashMap<String, Integer>(0x100);
	private List<ShaderUniform> uniformUpdates = new ArrayList<ShaderUniform>(0x100);
	private boolean relinkProgram = false;
	
	public ShaderProgram(String vertShader, String fragShader) {
		program = GL20.glCreateProgram();
		GL20.glAttachShader(program, ShaderLoader.loadShader(vertShader, GL20.GL_VERTEX_SHADER));
		GL20.glAttachShader(program, ShaderLoader.loadShader(fragShader, GL20.GL_FRAGMENT_SHADER));
		GL20.glLinkProgram(program);
	}
	
	public void bindAttribLoc(int location, CharSequence varName) {
		GL20.glBindAttribLocation(program, location, varName);
		varMap.put(varName.toString(), location);
		relinkProgram = true;
	}
	
	public int getAttribLoc(String name) {
		return varMap.get(name);
	}
	
	public int getUniformLoc(String name) {
		Integer location = varMap.get(name);
		if(location == null) {
			location = GL20.glGetUniformLocation(program, name);
			varMap.put(name, location);
		}
		return location;
	}
	
	public void setUniform(String varname, boolean value) {
		uniformUpdates.add(new UniformBool(getUniformLoc(varname), value));
	}
	
	public void setUniform(String varname, int value) {
		uniformUpdates.add(new UniformInt(getUniformLoc(varname), value));
	}
	
	public void setUniform(String varname, float value) {
		uniformUpdates.add(new UniformFloat(getUniformLoc(varname), value));
	}
	
	public void setUniform(String varname, Vec2 value) {
		uniformUpdates.add(new UniformVec2(getUniformLoc(varname), value));
	}
	
	public void setUniform(String varname, Vec3 value) {
		uniformUpdates.add(new UniformVec3(getUniformLoc(varname), value));
	}
	
	public void setUniform(String varname, Vec4 value) {
		uniformUpdates.add(new UniformVec4(getUniformLoc(varname), value));
	}
	
	public void setUniform(String varname, Mat4 value) {
		uniformUpdates.add(new UniformMat4(getUniformLoc(varname), value));
	}
	
	public void setUniform(String varname, Mat3 value) {
		uniformUpdates.add(new UniformMat3(getUniformLoc(varname), value));
	}
	
	public void setUniform(String varname, Mat2 value) {
		uniformUpdates.add(new UniformMat2(getUniformLoc(varname), value));
	}
	
	public void assign() {
		GL20.glUseProgram(program);
		if(relinkProgram) {
			GL20.glLinkProgram(program);
			relinkProgram = false;
		}
		for(ShaderUniform uniform : uniformUpdates)
			uniform.assign();
		uniformUpdates.clear();
	}
	
	public void assignUniforms() {
		for(ShaderUniform uniform : uniformUpdates)
			uniform.assign();
		uniformUpdates.clear();
	}
	
	

}