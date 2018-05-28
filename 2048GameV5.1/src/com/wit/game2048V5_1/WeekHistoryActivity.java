package com.wit.game2048V5_1;

import java.util.Iterator;
import java.util.TreeMap;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wit.managers.HistoryManager;

public class WeekHistoryActivity extends Activity {

	private int num = com.wit.utils.Constant.HISTORY_SIZE;
	private LinearLayout historyLine;
	private TextView[] textViews = new TextView[num];
	private int[] scores = new int[num];
	private long[] times = new long[num];
	private float density;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weekhistory);
		initDensity();
		initComponents();
		show();
	}

	private void initDensity() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		density = metric.density;
	}

	private void initComponents() {
		initTextViews();
		historyLine = (LinearLayout) findViewById(R.id.linearLayout_weekHistory);
		for (int i = 0; i < num; i++) {
			textViews[i].setWidth((int) (200 * density));
			textViews[i].setHeight((int) (36 * density));
			textViews[i].setTextSize(20);
			textViews[i].setTextColor(Color.BLACK);
			textViews[i].setGravity(Gravity.CENTER_VERTICAL);
			historyLine.addView(textViews[i]);
		}
	}

	private void initTextViews() {
		TreeMap<Integer, Long> scoreMap = HistoryManager.getHistoryManager()
				.getWeekHistoryScoreMap();
		int size = scoreMap.size();
		Iterator<Integer> it = scoreMap.keySet().iterator();
		while (it.hasNext()) {
			int i = --size;
			Integer score = it.next();
			scores[i] = score;
			times[i] = scoreMap.get(score);
		}
		for (int i = 0; i < num; i++) {
			textViews[i] = new TextView(this);
		}
	}

	private void show() {
		for (int i = 0; i < num; i++) {
			if (scores[i] == 0) {
				textViews[i].setText("第" + (i + 1) + "名：暂无");
			} else {
				textViews[i].setText("第" + (i + 1) + "名：" + scores[i]);
			}
		}
	}

}
