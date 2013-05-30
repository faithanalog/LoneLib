package com.unknownloner.lonelib.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.*;
import java.nio.IntBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL20;

public class GLUtil {
	
	public static void exitOnError(String section) {
		if(printError(section)) {
			System.exit(0);
		}
	}

    public static String getError() {
        int err = GL11.glGetError();
        switch(err) {
            case 0:
                return null;
            case 0x500:
                return "GL_INVALID_ENUM";
            case 0x501:
                return "GL_INVALID_VALUE";
            case 0x502:
                return "GL_INVALID_OPERATION";
            case 0x503:
                return "GL_STACK_OVERFLOW";
            case 0x504:
                return "GL_STACK_UNDERFLOW";
            case 0x505:
                return "GL_OUT_OF_MEMORY";
            case 0x506:
                return "GL_INVALID_FRAMEBUFFER_OPERATION";
            case 0x8031:
                return "GL_TABLE_TOO_LARGE";
            default:
                return "UNKNOWN ERROR";
        }
    }


	public static boolean printError(String section) {
		String errName = getError();
        if (errName == null) {
            return false;
        }
		System.err.println("An OpenGL Error Occured!");
		System.err.println("Section: ".concat(section));
		System.err.println("Error: ".concat(errName));
		return true;
	}
	
	public static void setNativesPath(String path) {
		System.setProperty("org.lwjgl.librarypath", path);
	}
	
	public static BufferedImage takeScreenshot() {
		return takeScreenshot(0, 0, Display.getWidth(), Display.getHeight());
	}
	
	/**
	 * Takes a screenshot
	 * @param x Left x value of capture area
	 * @param y Lower y value of capture area (0 = bottom)
	 * @param width Width of capture area
	 * @param height Height of capture area
	 * @return
	 */
	public static BufferedImage takeScreenshot(int x, int y, int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		IntBuffer rgb = BufferUtil.createIntBuffer(width * height);
		GL11.glReadPixels(x, y, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, rgb); /*Format as ARGB instead of RGBA */
		int[] imgData = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		
		//glReadPixels as 0, 0 as bottom left. BufferedImages have it as top left.
		//This flips the image
		for(y = height - 1; y >= 0; y--) {
			rgb.get(imgData, y * width, width);
		}
		return img;
	}

    public static int loadShader(File file, int type) throws FileNotFoundException, LWJGLException {
        return loadShader(new FileInputStream(file), type);
    }

    /**
     * Loads a shader from the {@link java.io.InputStream} specified.  The {@link java.io.InputStream} must NOT be NULL.
     * @param input stream
     * @param type of shader
     * @return int GL memory address of the loaded shader.
     * @throws LWJGLException if there was an error loading the shader
     */
    public static int loadShader(InputStream input, int type) throws LWJGLException {
        return loadShader(new InputStreamReader(input), type);
    }

    public static int loadShader(String path, int type) throws LWJGLException {
        InputStream stream = GLUtil.class.getResourceAsStream(path);
        if (stream == null) {
            throw new LWJGLException(path + " does not exist");
        }
        return loadShader(stream, type);
    }

    public static int loadShader(Reader shaderReader, int type) throws LWJGLException {
        String shaderSource = loadShaderString(shaderReader, false);
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GLUtil.printError("Shader Compilation") || GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new LWJGLException("Failed to compile the shader: " + GL20.glGetShaderInfoLog(shaderID, Short.MAX_VALUE));
        }
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
                        shaderSource.append(loadShaderString(new InputStreamReader(GLUtil.class.getResourceAsStream(include)), versionSeen));
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
