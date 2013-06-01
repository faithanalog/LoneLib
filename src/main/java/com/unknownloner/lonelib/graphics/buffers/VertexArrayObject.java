package com.unknownloner.lonelib.graphics.buffers;

import org.lwjgl.opengl.GL30;

public class VertexArrayObject {
	
	public final int vaoID;
	private boolean deleted;
	
	public VertexArrayObject(IVertexAttribPointer... pointers) {
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		for(IVertexAttribPointer pointer : pointers) {
			pointer.assign();
		}
		GL30.glBindVertexArray(0);
	}
	
	public void assign() {
		GL30.glBindVertexArray(vaoID);
	}
	
	public void unassign() {
		GL30.glBindVertexArray(0);  // with GL30 you should never need to disable VAOs, just enable the next necessary one.
	}
	
	public void delete() {
		if(!deleted) {
			deleted = true;
			GL30.glDeleteVertexArrays(vaoID);
		}
	}
	
	@Override
	protected void finalize() {
		delete();
	}
}
