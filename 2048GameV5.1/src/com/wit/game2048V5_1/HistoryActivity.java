package com.wit.game2048V5_1;

import java.util.Iterator;
import java.util.TreeSet;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wit.managers.HistoryManager;

public class HistoryActivity extends Activity {

	private int num = com.wit.utils.Constant.HISTORY_SIZE;
	private float density;
	private LinearLayout historyLine;
	private TextView[] textViews = new TextView[num];
	private int[] scores = new int[num];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
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
		historyLine = (LinearLayout) findViewById(R.id.linearLayout_history);
		for (int i = 0; i < num; i++) {
			textViews[i].setWidth((int) (200 * density));//默认以px为单位
			textViews[i].setHeight((int) (36 * density));
			textViews[i].setTextSize(20);//默认以sp为单位
			textViews[i].setGravity(Gravity.CENTER_VERTICAL);
			textViews[i].setTextColor(Color.BLACK);
			historyLine.addView(textViews[i]);
		}
	}

	private void initTextViews() {
		TreeSet<Integer> set = HistoryManager.getHistoryManager()
				.getHistoryScoreSet();
		int size = set.size();
		Iterator<Integer> it = set.iterator();
		while (it.hasNext()) {
			scores[--size] = it.next();
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
