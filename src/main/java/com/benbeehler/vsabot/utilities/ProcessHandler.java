package com.benbeehler.vsabot.utilities;

import java.text.DecimalFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProcessHandler {

	/*
	 * This class handles threads, memory, and other random, important utilities
	 */
	
	private static Executor spawner = Executors.newCachedThreadPool();
	
	public static void spawn(Runnable runnable) {
		spawner.execute(runnable);
		//spawn thread from a cached pool
	}
	
	public static int seconds(int amount) {
		return 1000 * amount;
	}
	
	public static String getMemory() {
		Runtime runtime = Runtime.getRuntime();
		return formatMem(runtime.freeMemory()) + "/" + formatMem(runtime.totalMemory()) + " (Free/Total)";
	}
	
	public static String formatMem(long size) {
		String hrSize = null;

		double b = size;
		double k = size / 1024.0;
		double m = ((size / 1024.0) / 1024.0);
		double g = (((size / 1024.0) / 1024.0) / 1024.0);
		double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);

		DecimalFormat dec = new DecimalFormat("0.00");

		if (t > 1) {
			hrSize = dec.format(t).concat(" TB");
		} else if (g > 1) {
			hrSize = dec.format(g).concat(" GB");
		} else if (m > 1) {
			hrSize = dec.format(m).concat(" MB");
		} else if (k > 1) {
			hrSize = dec.format(k).concat(" KB");
		} else {
			hrSize = dec.format(b).concat(" Bytes");
		}

		return hrSize; 
		//return human readable memory output
	}
	
	public static double megs(long size) {
		double m = ((size / 1024.0) / 1024.0);

		return m;
	}
}
