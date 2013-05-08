package com.unknownloner.lonelib.util;

public class Time {
	
	public static long SECOND_AS_NANOS = 1000000000L;
	public static long SECOND_AS_MILLIS = 1000L;
	public static long MILLI_AS_NANOS = 1000000L;
	
	public static long timeNanos() {
		return System.nanoTime();
	}
	
	public static long timeMillis() {
		return System.nanoTime() / 1000000L;
	}
	
	public static long timeSeconds() {
		return System.nanoTime() / 1000000000L;
	}
	
	public static long secondsToNanos(long seconds) {
		return seconds * 1000000000L;
	}
	
	public static long secondsToMillis(long seconds) {
		return seconds * 1000L;
	}
	
	public static long millisToSeconds(long millis) {
		return millis / 1000L;
	}
	
	public static long millisToNanos(long millis) {
		return millis * 1000000L;
	}
	
	public static long nanosToSeconds(long nanos) {
		return nanos / 1000000000L;
	}
	
	public static long nanosToMillis(long nanos) {
		return nanos / 1000000L;
	}

}
