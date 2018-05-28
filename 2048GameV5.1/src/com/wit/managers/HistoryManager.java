package com.wit.managers;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class HistoryManager {
	private int SIZE = com.wit.utils.Constant.HISTORY_SIZE;
	private static HistoryManager historyManager = new HistoryManager();
	private long weekStartTime;

	public static HistoryManager getHistoryManager() {
		return historyManager;
	}

	private HistoryManager() {
	}

	private TreeSet<Integer> history = new TreeSet<Integer>();
	private TreeMap<Integer, Long> weekHistory = new TreeMap<Integer, Long>();

	public void addHistory(Integer score) {
		history.add(score);
		while (history.size() > SIZE) {
			history.pollFirst();
		}
	}

	public void addWeekHistory(Integer score, Long time) {

		weekHistory.put(score, time);
		checkTime();
		while (weekHistory.size() > SIZE) {
			weekHistory.pollFirstEntry();
		}
	}

	public void checkTime() {
		Calendar c = Calendar.getInstance(Locale.CHINESE);
		// 设置日期为周一的 00：00：00
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		weekStartTime = c.getTimeInMillis();
		Set<Integer> keySet = weekHistory.keySet();
		Iterator<Integer> it = keySet.iterator();
		while (it.hasNext()) {
			Integer score = it.next();
			if (weekHistory.get(score) < weekStartTime) {
				weekHistory.remove(score);
			}
		}

	}

	public TreeSet<Integer> getHistoryScoreSet() {
		return history;
	}

	public TreeMap<Integer, Long> getWeekHistoryScoreMap() {
		return weekHistory;
	}
}
