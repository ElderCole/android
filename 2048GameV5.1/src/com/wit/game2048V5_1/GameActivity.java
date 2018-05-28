package com.wit.game2048V5_1;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wit.beans.GridView;
import com.wit.beans.Memory;
import com.wit.managers.GridManager;
import com.wit.managers.HistoryManager;
import com.wit.managers.MemoryManager;
import com.wit.utils.Game;
import com.wit.views.CustomTextView;

public class GameActivity extends Activity {

	private int SIZE = com.wit.utils.Constant.GAME_SIZE;
	private int GRID_SIZE;
	private Map<Integer, Integer> colorMap = com.wit.utils.Constant.colorMap;
	private String[] texts = com.wit.utils.Constant.texts;
	private Map<Integer, String> textMap = new HashMap<Integer, String>();
	private GridLayout grids = null;
	private TextView[] textViews = null;
	private int[][] presentView = new int[SIZE][SIZE];
	private Button back = null;
	private Button forward = null;
	private Button restart = null;
	private TextView presentScoreView;
	private TextView bestScoreView;
	private TextView flowerNumView;
	private int lastFlowerNum;
	private int nowFlowerNum;
	private int presentScore;
	private int bestScore;
	private int addScore = 0;
	private int addScoreNum = com.wit.utils.Constant.SCORE_ADD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		GRID_SIZE = getGridSize();
		initTextMap();
		initComponents();
		// 将最高分设置为历史最高分
		if (!HistoryManager.getHistoryManager().getHistoryScoreSet().isEmpty()) {
			bestScore = HistoryManager.getHistoryManager().getHistoryScoreSet()
					.last();
		}
		// 载入上次退出时的状态
		if (MemoryManager.getMemoryManger().getMemory() != null) {
			presentScore = MemoryManager.getMemoryManger().getMemory()
					.getPresentScore();
			bestScore = MemoryManager.getMemoryManger().getMemory()
					.getBestScore();
			addScore = MemoryManager.getMemoryManger().getMemory()
					.getAddScore();
			presentView = Game.cloneArray(MemoryManager.getMemoryManger()
					.getMemory().getView().getData());
			MemoryManager.getMemoryManger().setMemory(null);
		}
		showGrids(textViews, presentView);
		refreshScoreView();
	}

	private int getGridSize() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		return (int) (width * 0.8);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Memory memory = new Memory(new GridView(presentView), presentScore,
				bestScore, addScore);
		MemoryManager.getMemoryManger().setMemory(memory);
	}

	private void initComponents() {
		initGridLayout();
		// initTextViews();
		initButtons();
		initScoreView();
	}

	private void initGridLayout() {
		grids = (GridLayout) findViewById(R.id.grids);
		initTextViews();
		grids.setOnTouchListener(new TouchListener());
	}

	private void initTextViews() {
		Game.createRandomNum(presentView);
		textViews = new TextView[SIZE * SIZE];
		for (int i = 0; i < SIZE * SIZE; i++) {
			GridLayout.Spec rowSpec = GridLayout.spec(i / SIZE);
			GridLayout.Spec columnSpec = GridLayout.spec(i % SIZE);
			GridLayout.LayoutParams params = new GridLayout.LayoutParams(
					rowSpec, columnSpec);
			textViews[i] = new CustomTextView(this);
			textViews[i].setBackgroundColor(0xffbbada0);
			textViews[i].setWidth(GRID_SIZE / SIZE);
			textViews[i].setHeight(GRID_SIZE / SIZE);
			textViews[i].setGravity(Gravity.CENTER);
			textViews[i].setTextSize(20);
			textViews[i].setTextColor(Color.BLACK);
			grids.addView(textViews[i], params);
		}

	}

	// 初始化三个按钮，并注册事件监听
	private void initButtons() {
		back = (Button) findViewById(R.id.button_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (GridManager.getGridsManager().getStack().isEmpty()) {
					return;
				}
				GridManager.getGridsManager().getCache()
						.push(new GridView(presentView));
				GridView gridView = GridManager.getGridsManager().pop();
				for (int i = 0; i < SIZE; i++)
					System.arraycopy(gridView.getData()[i], 0, presentView[i],
							0, SIZE);
				showGrids(textViews, presentView);
				if (GridManager.getGridsManager().getStack().size() == 0) {
					// 后退按钮变灰
				}
				presentScore -= addScore;
				addScore -= addScoreNum;
				nowFlowerNum = GridManager.getGridsManager().getStackSize() - 1;
				GridManager.getGridsManager().setStackSize(nowFlowerNum);
				refreshScoreView();
				// 退步数减1
			}
		});

		forward = (Button) findViewById(R.id.button_forward);
		forward.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (GridManager.getGridsManager().getCache().isEmpty()) {
					return;
				}
				GridManager.getGridsManager().push(new GridView(presentView));
				GridView gView = GridManager.getGridsManager().getCache().pop();
				for (int i = 0; i < SIZE; i++) {
					System.arraycopy(gView.getData()[i], 0, presentView[i], 0,
							SIZE);
				}
				showGrids(textViews, presentView);
				if (GridManager.getGridsManager().getCache().isEmpty()) {
					// 按钮变灰
				}
				addScore += addScoreNum;
				presentScore += addScore;
				refreshScoreView();
			}
		});
		restart = (Button) findViewById(R.id.button_restart);
		restart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						GameActivity.this);
				builder.setPositiveButton("是",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								GridManager.getGridsManager().clearCache();
								GridManager.getGridsManager().clearStack();
								clearArray(presentView);
								Game.createRandomNum(presentView);
								showGrids(textViews, presentView);
								presentScore = 0;
								addScore = 0;
								refreshScoreView();
							}
						});
				builder.setNegativeButton("否", null);
				builder.setMessage("你确定要重新开始吗？");
				builder.show();
			}
		});
	}

	private void initScoreView() {
		presentScoreView = (TextView) findViewById(R.id.textView_scoreNum);
		bestScoreView = (TextView) findViewById(R.id.textView_bestScoreNum);
		flowerNumView = (TextView) findViewById(R.id.textView_flowerNum);

	}

	private void showGrids(TextView[] textViews, int[][] nums) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (nums[i][j] == 0) {
					textViews[i * SIZE + j].setText("");
					textViews[i * SIZE + j].setBackgroundColor(0xffbbada0);
				} else {
					if (textMap != null && textMap.size() >= SIZE * SIZE) {
						textViews[i * SIZE + j].setText(""
								+ textMap.get(nums[i][j]));
					} else {
						textViews[i * SIZE + j].setText("" + nums[i][j]);
					}
					textViews[i * SIZE + j].setBackgroundColor(colorMap
							.get(nums[i][j]));
				}
			}
		}
	}

	class TouchListener implements OnTouchListener {
		float touchX = 0;
		float touchY = 0;
		float lenX = 0;
		float lenY = 0;

		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touchX = event.getX();
				touchY = event.getY();
				break;
			case MotionEvent.ACTION_UP:
				lenX = event.getX() - touchX;
				lenY = event.getY() - touchY;
				if (Math.abs(lenX) > 10 || Math.abs(lenY) > 10) {

					// ---------------------------------------在这里将后退按钮设置为可见--------------------------------
					int[][] cloneArr = cloneArray(presentView);
					if (Math.abs(lenX) > Math.abs(lenY)) {

						if (lenX > 0) {
							presentView = Game.moveRight(presentView);
							if (Game.isSame(cloneArr, presentView)) {
								return true;
							}

						} else {
							presentView = Game.moveLeft(presentView);
							if (Game.isSame(cloneArr, presentView)) {
								return true;
							}
						}
					} else {
						if (lenY > 0) {
							presentView = Game.moveDown(presentView);
							if (Game.isSame(cloneArr, presentView)) {
								return true;
							}
						} else {
							presentView = Game.moveUp(presentView);
							if (Game.isSame(cloneArr, presentView)) {
								return true;
							}
						}
					}
					// 移动成功
					addScore += addScoreNum;
					presentScore += addScore;
					lastFlowerNum = nowFlowerNum;
					nowFlowerNum = GridManager.getGridsManager().getStackSize();
					Game.createRandomNum(presentView);
					GridManager.getGridsManager().clearCache();
					GridManager.getGridsManager().push(new GridView(cloneArr));
					showGrids(textViews, presentView);
					if (checkDeath(presentView)) {
						Toast toast = Toast.makeText(GameActivity.this,
								"少年，你为什么不相信春哥呢？", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						HistoryManager.getHistoryManager().addHistory(
								presentScore);
						HistoryManager.getHistoryManager().addWeekHistory(
								presentScore, new Date().getTime());
					} else {
						if (nowFlowerNum - lastFlowerNum > 0) {
							GridManager.getGridsManager().setStackSize(
									lastFlowerNum + 1);
							nowFlowerNum = lastFlowerNum + 1;
							Toast toast = Toast.makeText(GameActivity.this,
									"恭喜您获得1朵鲜花", Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
					}
					refreshScoreView();
				}
			}
			return true;
		}
	}

	// 检查游戏是否已经结束
	private boolean checkDeath(int[][] array) {
		int stackSize = GridManager.getGridsManager().getStackSize();
		int[][] cloneArr = cloneArray(array);
		Game.moveUp(cloneArr);
		GridManager.getGridsManager().setStackSize(stackSize);
		if (!Game.isSame(cloneArr, array))
			return false;
		Game.moveDown(cloneArr);
		GridManager.getGridsManager().setStackSize(stackSize);
		if (!Game.isSame(cloneArr, array))
			return false;
		Game.moveLeft(cloneArr);
		GridManager.getGridsManager().setStackSize(stackSize);
		if (!Game.isSame(cloneArr, array))
			return false;
		Game.moveRight(cloneArr);
		GridManager.getGridsManager().setStackSize(stackSize);
		if (!Game.isSame(cloneArr, array))
			return false;
		return true;
	}

	public void refreshScoreView() {

		if (presentScore > bestScore) {
			bestScore = presentScore;
		}
		presentScoreView.setText("" + presentScore);
		bestScoreView.setText("" + bestScore);
		flowerNumView.setText("" + nowFlowerNum);
	}

	public int[][] cloneArray(int[][] array) {
		if (array == null) {
			return null;
		}
		if (array[0] == null) {
			return new int[array.length][];
		}
		int rowNum = array.length;
		int columnNum = array[0].length;
		int[][] cloneArr = new int[rowNum][columnNum];
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < columnNum; j++) {
				cloneArr[i][j] = array[i][j];
			}
		}
		return cloneArr;
	}

	/**
	 * 将数组的所有元素清零
	 * 
	 * @param array
	 */
	private void clearArray(int[][] array) {
		if (array == null || array[0] == null)
			return;
		int rowNum = array.length;
		int columnNum = array[0].length;
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < columnNum; j++) {
				array[i][j] = 0;
			}
		}

	}

	private void initTextMap() {
		if (texts == null || texts.length == 0) {
			return;
		}
		int num = 1;
		for (String s : texts) {
			textMap.put(num, s);
			num = num << 1;
		}
	}
}
