package com.wit.utils;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import android.content.Context;

import com.wit.beans.GridView;
import com.wit.beans.Memory;
import com.wit.managers.HistoryManager;
import com.wit.managers.MemoryManager;

public class LoadProperties {
	private Context context;
	private String historyFileName = "history.light";
	private String memoryFileName = "memory.light";
	private String weekHistoryFileName = "weekHistory.light";
	private int SIZE = com.wit.utils.Constant.GAME_SIZE;

	public LoadProperties(Context context) {
		this.context = context;
	}

	public void load() {
		try {
			loadHistory();
			loadWeekHistory();
			loadMemory();
		} catch (IOException e) {
			System.out.println("º”‘ÿ ß∞‹...");
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			saveHistory();
			saveWeekHistory();
			saveMemory();
		} catch (IOException e) {
			System.out.println("±£¥Ê ß∞‹...");
			e.printStackTrace();
		}

	}

	public void loadHistory() throws IOException {
		FileInputStream input = null;
		try {
			input = context.openFileInput(historyFileName);
		} catch (FileNotFoundException e) {
			return;
		}

		Properties historyProperty = new Properties();
		historyProperty.load(input);
		Set<Object> keySet = historyProperty.keySet();
		if (keySet == null) {
			input.close();
			return;
		}
		for (Object obj : keySet) {
			HistoryManager.getHistoryManager()
					.addHistory(
							Integer.parseInt(historyProperty
									.getProperty((String) obj)));
		}
		input.close();
	}

	public void loadWeekHistory() throws IOException {
		FileInputStream input = null;
		try {
			input = context.openFileInput(weekHistoryFileName);
		} catch (FileNotFoundException e) {
			return;
		}

		Properties weekHistoryProperty = new Properties();
		weekHistoryProperty.load(input);
		Set<Object> keySet = weekHistoryProperty.keySet();
		if (keySet == null) {
			input.close();
			return;
		}
		Iterator<Object> it = keySet.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			HistoryManager.getHistoryManager().addWeekHistory(
					Integer.parseInt((String) obj),
					Long.parseLong((String) weekHistoryProperty.get(obj)));
		}
		input.close();
	}

	public void saveHistory() throws IOException {
		TreeSet<Integer> historySet = HistoryManager.getHistoryManager()
				.getHistoryScoreSet();
		int size = historySet.size();
		if (size == 0) {
			return;
		}
		FileOutputStream output = context.openFileOutput(historyFileName,
				Context.MODE_PRIVATE);
		BufferedOutputStream bos = new BufferedOutputStream(output);

		for (Integer score : historySet) {
			bos.write((size-- + "=" + score + "\n").getBytes());
		}
		bos.close();
	}

	public void saveWeekHistory() throws IOException {
		TreeMap<Integer, Long> weekHistoryMap = HistoryManager
				.getHistoryManager().getWeekHistoryScoreMap();
		int size = weekHistoryMap.size();
		if (size == 0) {
			return;
		}
		FileOutputStream output = context.openFileOutput(weekHistoryFileName,
				Context.MODE_PRIVATE);
		BufferedOutputStream bos = new BufferedOutputStream(output);

		Object[] scoreSet = weekHistoryMap.keySet().toArray();
		for (int i = size - 1; i >= 0; i--) {
			bos.write((scoreSet[i] + "=" + weekHistoryMap.get(scoreSet[i]) + "\n")
					.getBytes());
		}
		bos.close();
	}

	public void loadMemory() throws IOException {
		FileInputStream input = null;
		try {
			input = context.openFileInput(memoryFileName);
		} catch (FileNotFoundException e) {
			return;
		}
		Properties prop = new Properties();
		prop.load(input);
		String present = prop.getProperty("presentScore");
		String best = prop.getProperty("bestScore");
		String add = prop.getProperty("addScore");
		String views = prop.getProperty("views");

		if (present == null || present.equals(""))
			return;
		if (best == null || present.equals(""))
			return;
		if (add == null || add.equals(""))
			return;
		if (views == null || views.equals(""))
			return;

		int presentScore = 0;
		int bestScore = 0;
		int addScore = 0;
		int[][] arr = new int[SIZE][SIZE];

		bestScore = Integer.parseInt(best);
		presentScore = Integer.parseInt(present);
		addScore = Integer.parseInt(add);
		arr = parseArr(views);

		MemoryManager.getMemoryManger()
				.setMemory(
						new Memory(new GridView(arr), presentScore, bestScore,
								addScore));
		input.close();
	}

	public void saveMemory() throws IOException {
		if (MemoryManager.getMemoryManger().getMemory() == null) {
			return;
		}
		FileOutputStream output = context.openFileOutput(memoryFileName,
				Context.MODE_PRIVATE);
		BufferedOutputStream bos = new BufferedOutputStream(output);
		int presentScore = MemoryManager.getMemoryManger().getMemory()
				.getPresentScore();
		int bestScore = MemoryManager.getMemoryManger().getMemory()
				.getBestScore();
		int addScore = MemoryManager.getMemoryManger().getMemory()
				.getAddScore();
		int[][] arr = MemoryManager.getMemoryManger().getMemory().getView()
				.getData();
		bos.write(("presentScore=" + presentScore + "\n").getBytes());
		bos.write(("bestScore=" + bestScore + "\n").getBytes());
		bos.write(("addScore=" + addScore + "\n").getBytes());
		bos.write("views=".getBytes());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				sb.append(arr[i][j] + ",");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		bos.write((new String(sb) + "\n").getBytes());
		bos.close();
	}

	private int[][] parseArr(String views) {
		String[] s = views.split(",");
		int[][] arr = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				arr[i][j] = Integer.parseInt(s[i * SIZE + j]);
			}
		}
		return arr;
	}
}
