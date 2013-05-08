package com.unknownloner.lonelib.graphics.shaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.lwjgl.opengl.GL20;

public class ShaderLoader {
	
	public static int loadShader(File file, int type) {
		try {
			return loadShader(new FileInputStream(file), type);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int loadShader(InputStream input, int type) {
		return loadShader(new InputStreamReader(input), type);
	}
	
	public static int loadShader(String path, int type) {
		return loadShader(ShaderLoader.class.getResourceAsStream(path), type);
	}
	
	public static int loadShader(Reader shaderReader, int type) {
		String shaderSource = loadShaderString(shaderReader, false);
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		System.out.println(
		GL20.glGetShaderInfoLog(shaderID, Short.MAX_VALUE));
		
		return shaderID;
	}
	
	private static String loadShaderString(Reader shaderReader, boolean versionSeen) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader reader = new BufferedReader(shaderReader);
		String line;
		try {
			while((line = reader.readLine()) != null) {
				if(line.startsWith("#include")) {
					try {
						String include = line.substring(9);
						shaderSource.append(loadShaderString(new InputStreamReader(ShaderLoader.class.getResourceAsStream(include)), versionSeen));
					} catch (Exception e) {
						System.out.println("Warning: No file specified for include");
					}
				} else if(line.startsWith("#version") && !versionSeen) {
					versionSeen = true;
					shaderSource.append(line).append('\n');
				} else {
					shaderSource.append(line).append('\n');
				}
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Error reading shader reader");
			e.printStackTrace();
			return "";
		}
		return shaderSource.toString();
	}

}
