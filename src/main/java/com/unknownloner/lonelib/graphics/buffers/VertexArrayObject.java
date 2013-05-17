package com.unknownloner.lonelib.graphics.buffers;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

public class VertexArrayObject {
	
	private VertexAttribPointer[] pointers;
	public final int vaoID;
	public static boolean vaoSupport;
	private boolean deleted;
	
	static {
		vaoSupport = GLContext.getCapabilities().GL_ARB_vertex_array_object;
	}
	
	public VertexArrayObject(VertexAttribPointer... pointers) {
		if(vaoSupport) {
			vaoID = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(vaoID);
			for(VertexAttribPointer pointer : pointers) {
				pointer.assign();
			}
			GL30.glBindVertexArray(0);
		} else {
			this.pointers = pointers;
			vaoID = -1;
		}
	}
	
	public void assign() {
		if(vaoSupport) {
			GL30.glBindVertexArray(vaoID);
		} else {
			VertexBufferObject lastBuffer = null;
			for(VertexAttribPointer pointer : pointers) {
				if(lastBuffer == pointer.getBuffer()) {
					pointer.assign(false);
				} else {
					lastBuffer = pointer.getBuffer();
					pointer.assign(true);
				}
			}
		}
	}
	
	public void unassign() {
		if(vaoSupport) {
			GL30.glBindVertexArray(0);  // with GL30 you should never need to disable VAOs, just enable the next necessary one.
		} else {
			for(VertexAttribPointer pointer : pointers) {
				GL20.glDisableVertexAttribArray(pointer.getAttribIndex());
			}
		}
	}
	
	public void delete() {
		if(!deleted) {
			deleted = true;
			if(vaoSupport) {
				GL30.glDeleteVertexArrays(vaoID);
			} else {
				pointers = null;
			}
		}
	}
	
	@Override
	protected void finalize() {
		delete();
	}
}
