package com.unknownloner.lonelib.graphics;

import java.util.ArrayList;
import java.util.List;

import com.unknownloner.lonelib.graphics.shaders.ShaderProgram;

public class MeshBatch {

	private final ShaderProgram program;
	private List<Mesh> displayList = new ArrayList<Mesh>();

	public MeshBatch(ShaderProgram program) {
		this.program = program;
	}

	public void render() {
		// set uniforms in the program
		program.assign();

		// render each mesh that's active
		for (Mesh mesh : displayList) {
			mesh.render();
		}
	}
}
