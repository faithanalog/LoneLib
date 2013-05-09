package com.unknownloner.lonelib.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

public class BufferUtil {
	
	public static ByteBuffer createByteBuffer(int size) {
		return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
	}
	
	public static ShortBuffer createShortBuffer(int size) {
		return createByteBuffer(size * 2).asShortBuffer();
	}
	
	public static CharBuffer createCharBuffer(int size) {
		return createByteBuffer(size * 2).asCharBuffer();
	}
	
	public static IntBuffer createIntBuffer(int size) {
		return createByteBuffer(size * 4).asIntBuffer();
	}
	
	public static FloatBuffer createFloatBuffer(int size) {
		return createByteBuffer(size * 4).asFloatBuffer();
	}
	
	public static LongBuffer createLongBuffer(int size) {
		return createByteBuffer(size * 8).asLongBuffer();
	}
	
	public static DoubleBuffer createDoubleBuffer(int size) {
		return createByteBuffer(size * 8).asDoubleBuffer();
	}
	
	public static ByteBuffer wrap(byte[] bytes) {
		return (ByteBuffer) createByteBuffer(bytes.length).put(bytes).flip();
	}
	
	public static ShortBuffer wrap(short[] shorts) {
		return (ShortBuffer) createShortBuffer(shorts.length).put(shorts).flip();
	}
	
	public static CharBuffer wrap(char[] chars) {
		return (CharBuffer) createCharBuffer(chars.length).put(chars).flip();
	}
	
	public static IntBuffer wrap(int[] ints) {
		return (IntBuffer) createIntBuffer(ints.length).put(ints).flip();
	}
	
	public static FloatBuffer wrap(float[] floats) {
		return (FloatBuffer) createFloatBuffer(floats.length).put(floats).flip();
	}
	
	public static LongBuffer wrap(long[] longs) {
		return (LongBuffer) createLongBuffer(longs.length).put(longs).flip();
	}
	
	public static DoubleBuffer wrap(double[] doubles) {
		return (DoubleBuffer) createDoubleBuffer(doubles.length).put(doubles).flip();
	}
	
	public static Buffer wrap(Object array) {
		if(array instanceof byte[])
			return BufferUtil.wrap((byte[])array);
		else if(array instanceof short[])
			 return BufferUtil.wrap((short[])array);
		else if(array instanceof char[])
			 return BufferUtil.wrap((char[])array);
		else if(array instanceof int[])
			 return BufferUtil.wrap((int[])array);
		else if(array instanceof float[])
			 return BufferUtil.wrap((float[])array);
		else if(array instanceof long[])
			 return BufferUtil.wrap((long[])array);
		else if(array instanceof double[])
			 return BufferUtil.wrap((double[])array);
		else return null;
	}
	
	private static Method getBufferCleaner;
	private static Method cleanBuffer;
	private static Class<?> directBufferClass;
	static {
		try {
			Buffer directBuffer = ByteBuffer.allocateDirect(1);
			Class<?> bufferClass = directBuffer.getClass();
			for(Class<?> clazz : bufferClass.getInterfaces()) {
				if(clazz.getSimpleName().equals("DirectBuffer")) {
					Method getCleaner = clazz.getMethod("cleaner");
					if(getCleaner != null) {
						directBufferClass = clazz;
						getCleaner.setAccessible(true);
						getBufferCleaner = getCleaner;
						cleanBuffer = getCleaner.invoke(directBuffer).getClass().getMethod("clean");
						cleanBuffer.setAccessible(true);
						cleanBuffer.invoke(getCleaner.invoke(directBuffer));
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void cleanBuffer(Buffer buffer) {
		Class<?> clazz = buffer.getClass();
		if(directBufferClass.isAssignableFrom(clazz)) {
			try {
				Object cleaner = getBufferCleaner.invoke(buffer);
				if(cleaner == null) {
					Field byteBuffer;
					byteBuffer = clazz.getDeclaredField("att");
					if(!byteBuffer.isAccessible())
						byteBuffer.setAccessible(true);
					cleaner = getBufferCleaner.invoke(byteBuffer.get(buffer));
				}
				cleanBuffer.invoke(cleaner);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns the buffer's data as a ByteBuffer
	 * @param buffer data
	 * @return a ByteBuffer containing the data in buffer. The limit will be the capacity and the position will be 0
	 */
	public static ByteBuffer asByteBuffer(Buffer buffer) {
		//Return a ByteBuffer if buffer is already a ByteBuffer
		if(buffer instanceof ByteBuffer)
			return (ByteBuffer)buffer;
		
		//If the buffer is direct, it probably has a DirectByteBuffer holding the data
		//This will get that buffer and return it
		if(buffer.isDirect()) {
			try {
				Method getBuffer = buffer.getClass().getMethod("attachment");
				if(!getBuffer.isAccessible())
					getBuffer.setAccessible(true);
				ByteBuffer bBuffer = (ByteBuffer)getBuffer.invoke(buffer);
				return bBuffer;
			} catch (Exception e) {
			}
		}
		
		//If the buffer is not direct or we can't get the DirectByteBuffer holding the data
		//We will create a new buffer and copy data into it
		//The new buffer will be direct if the original buffer was direct
		//This code is rather bulky, but we have to check for every type of buffer there is
		//So that's what we do
		int oldPos = buffer.position();
		int oldLim = buffer.limit();
		buffer.clear();
		ByteBuffer bBuffer;
		if(buffer.isDirect())
			bBuffer = createByteBuffer(buffer.capacity() * getElemByteSize(buffer));
		else
			bBuffer = ByteBuffer.allocate(buffer.capacity() * getElemByteSize(buffer));
		if(buffer instanceof ShortBuffer) {
			ShortBuffer sBuffer = (ShortBuffer)buffer;
			for(int i = sBuffer.limit(); i > 0; i--)
				bBuffer.putShort(sBuffer.get());
		} else if(buffer instanceof CharBuffer) {
			CharBuffer cBuffer = (CharBuffer)buffer;
			for(int i = cBuffer.limit(); i > 0; i--)
				bBuffer.putChar(cBuffer.get());
		} else if(buffer instanceof IntBuffer) {
			IntBuffer iBuffer = (IntBuffer)buffer;
			for(int i = iBuffer.limit(); i > 0; i--)
				bBuffer.putInt(iBuffer.get());
		} else if(buffer instanceof FloatBuffer) {
			FloatBuffer fBuffer = (FloatBuffer)buffer;
			for(int i = fBuffer.limit(); i > 0; i--)
				bBuffer.putFloat(fBuffer.get());
		} else if(buffer instanceof LongBuffer) {
			LongBuffer lBuffer = (LongBuffer)buffer;
			for(int i = lBuffer.limit(); i > 0; i--)
				bBuffer.putLong(lBuffer.get());
		} else if(buffer instanceof DoubleBuffer) {
			DoubleBuffer dBuffer = (DoubleBuffer)buffer;
			for(int i = dBuffer.limit(); i > 0; i--)
				bBuffer.putDouble(dBuffer.get());
		} else {
			bBuffer = null;
		}
		buffer.position(oldPos);
		buffer.limit(oldLim);
		return bBuffer;
	}
	
	public static void copyPosAndLimit(Buffer src, Buffer dest) {
		int newPos = src.position() * getElemByteSize(src) / getElemByteSize(dest);
		int newLim = src.limit() * getElemByteSize(src) / getElemByteSize(dest);
		dest.position(newPos);
		dest.limit(newLim);
	}
	
	public static int getElemByteSize(Buffer buffer) {
		if(buffer instanceof ByteBuffer) {
			return 1;
		} else if(buffer instanceof ShortBuffer || buffer instanceof CharBuffer) {
			return 2;
		} else if(buffer instanceof IntBuffer || buffer instanceof FloatBuffer) {
			return 4;
		} else if(buffer instanceof LongBuffer || buffer instanceof DoubleBuffer) {
			return 8;
		} else {
			return 0;
		}
	}

}
