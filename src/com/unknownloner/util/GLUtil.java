package com.unknownloner.util;

import org.lwjgl.opengl.GL11;

public class GLUtil {
	
	public static void exitOnError(String section) {
		int err = GL11.glGetError();
		if(err == 0)
			return;
		String errName;
		switch(err) {
		case 0x500:
			errName = "GL_INVALID_ENUM";
			break;
		case 0x501:
			errName = "GL_INVALID_VALUE";
			break;
		case 0x502:
			errName = "GL_INVALID_OPERATION";
			break;
		case 0x503:
			errName = "GL_STACK_OVERFLOW";
			break;
		case 0x504:
			errName = "GL_STACK_UNDERFLOW";
			break;
		case 0x505:
			errName = "GL_OUT_OF_MEMORY";
			break;
		case 0x506:
			errName = "GL_INVALID_FRAMEBUFFER_OPERATION";
			break;
		case 0x8031:
			errName = "GL_TABLE_TOO_LARGE";
			break;
		default:
			errName = "UNKNOWN ERROR";
		}
		System.err.println("An OpenGL Error Occured!");
		System.err.println("Section: ".concat(section));
		System.err.println("Error: ".concat(errName));
		System.exit(0);
	}
	
	public static void setNativesPath(String path) {
		System.setProperty("org.lwjgl.librarypath", path);
	}

}
