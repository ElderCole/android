package com.wit.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.wit.game2048V5_1.R;

public class BackgroundMusicService extends Service {

	private MediaPlayer player;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		player.stop();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (player == null) {
			player = MediaPlayer.create(this, R.raw.music);
			player.setLooping(true);
			player.start();
		}
		return super.onStartCommand(intent, flags, startId);
		
	}
}
