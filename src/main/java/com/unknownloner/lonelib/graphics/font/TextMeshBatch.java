package com.unknownloner.lonelib.graphics.font;

import org.lwjgl.opengl.GL13;

import com.unknownloner.lonelib.graphics.MeshBatch;
import com.unknownloner.lonelib.math.Vec3;

public class TextMeshBatch extends MeshBatch {

	private FontRenderer fr;
	
	public TextMeshBatch(FontRenderer fr, String modelLocation) {
		super(fr.getShaderProgram(), modelLocation, new Vec3(1, 1, 1));
		this.fr = fr;
	}
	
	@Override
	public void render() {
		fr.bindFontTexture(GL13.GL_TEXTURE0);
		super.render();
	}

}
