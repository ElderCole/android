package com.wit.game2048V5_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.wit.managers.GridManager;
import com.wit.managers.HistoryManager;
import com.wit.managers.MemoryManager;
import com.wit.services.BackgroundMusicService;
import com.wit.utils.LoadProperties;

public class MainActivity extends Activity {
	private Button begin;
	private Button continues;
	private Button history;
	private Button weekHistory;
	private Button playMusic;
	private Button settings;
	private Button explain;
	private Intent playMusicIntent;
	private Intent beginIntent;
	private Intent historyIntent;
	private Intent weekHistoryIntent;
	private Intent explainIntent;
	private LoadProperties loader = new LoadProperties(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initComponents();
		setIntent();
		
		new Thread() {
			@Override
			public void run() {
				loader.load();
			}
		}.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(playMusicIntent);
		loader.save();
	}

	private void initComponents() {
		begin = (Button) findViewById(R.id.button_begin);
		begin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MemoryManager.getMemoryManger().setMemory(null);
				GridManager.getGridsManager().clearStack();
				GridManager.getGridsManager().clearCache();
				startActivity(beginIntent);
			}
		});
		continues = (Button) findViewById(R.id.button_continue);
		continues.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(beginIntent);
			}
		});
		history = (Button) findViewById(R.id.button_history);
		history.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(historyIntent);
			}

		});
		weekHistory = (Button) findViewById(R.id.button_weekHistory);
		weekHistory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HistoryManager.getHistoryManager().checkTime();
				startActivity(weekHistoryIntent);
			}
		});
		playMusic = (Button) findViewById(R.id.button_playMusic);
		playMusic.setOnClickListener(new OnClickListener() {
			private boolean flag = false;

			@Override
			public void onClick(View v) {
				if (!flag) {
					((Button) v).setText("πÿ±’±≥æ∞“Ù¿÷");
					startService(playMusicIntent);
					flag = true;
				} else {
					((Button) v).setText("≤•∑≈±≥æ∞“Ù¿÷");
					stopService(playMusicIntent);
					flag = false;
				}
			}
		});

		settings = (Button) findViewById(R.id.button_settings);
		settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//TODO Ã¯◊™µΩ…Ë÷√ΩÁ√Ê
			}
		});
		explain = (Button) findViewById(R.id.button_explain);
		explain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(explainIntent);
			}

		});
	}

	private void setIntent() {
		playMusicIntent = new Intent(this, BackgroundMusicService.class);

		beginIntent = new Intent(this, GameActivity.class);

		historyIntent = new Intent(this, HistoryActivity.class);

		weekHistoryIntent = new Intent(this, WeekHistoryActivity.class);

		explainIntent = new Intent(this, ExplainActivity.class);
	}
}
