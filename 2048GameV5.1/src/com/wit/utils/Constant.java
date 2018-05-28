package com.wit.utils;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;

public class Constant {
	public static int GAME_SIZE = 4;
	public static int RETURN_NUM = 3;
	public static int SCORE_ADD = 1;
	public static int HISTORY_SIZE = 10;
	public static String[] texts =new String[]{"武","汉","工","程","大","学","2012","级","计","算","机","科","学","与","技"};
	public static Map<Integer, Integer> colorMap = new HashMap<Integer, Integer>();
	static {
		colorMap.put(2, Color.rgb(0xcc, 0xcc, 0x00));
		colorMap.put(4, Color.rgb(0xff, 0x99, 0x33));
		colorMap.put(8, Color.rgb(0x66, 0x00, 0x99));
		colorMap.put(16, Color.rgb(0xcc, 0xff, 0xff));
		colorMap.put(32, Color.rgb(0xff, 0x66, 0x33));
		colorMap.put(64, Color.rgb(0x33, 0x66, 0x99));
		colorMap.put(128, Color.rgb(0x99, 0xcc, 0xcc));
		colorMap.put(256, Color.rgb(0x99, 0x66, 0x66));
		colorMap.put(512, Color.rgb(0x00, 0x00, 0xff));
		colorMap.put(1024, Color.rgb(0x00, 0xff, 0xcc));
		colorMap.put(2048, Color.rgb(0xff, 0x00, 0x00));
		colorMap.put(4096, Color.rgb(0x33, 0x33, 0x00));
		colorMap.put(8192, Color.rgb(0xff, 0xff, 0x00));
		colorMap.put(16384, Color.rgb(0x00, 0x33, 0x33));
		colorMap.put(32768, Color.rgb(0xff, 0xcc, 0xcc));
		colorMap.put(65536, Color.rgb(0x00, 0x00, 0x66));
		
		colorMap.put(131072, Color.rgb(0xF4, 0xA4, 0x60));
		colorMap.put(262144, Color.rgb(0xF0, 0xFF, 0xFF));
		colorMap.put(524288, Color.rgb(0xB2, 0x22, 0x22));
		colorMap.put(1048576, Color.rgb(0xAA, 0xF0, 0xF5));
		colorMap.put(2097152, Color.rgb(0xDD, 0xA0, 0xDD));
		colorMap.put(4194304, Color.rgb(0xAF, 0xEE, 0xEE));
		colorMap.put(8388608, Color.rgb(0x22, 0x22, 0x22));
		colorMap.put(16777216, Color.rgb(0x55, 0xff, 0x11));
		colorMap.put(33554432, Color.rgb(0x80, 0x00, 0x00));
	}

}
