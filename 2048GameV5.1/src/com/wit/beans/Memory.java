package com.wit.beans;

public class Memory {
	private GridView view;
	private int presentScore;
	private int bestScore;
	private int addScore;

	public Memory(GridView view, int present, int best, int add) {
		this.view = view;
		this.presentScore = present;
		this.bestScore = best;
		this.addScore = add;
	}

	public GridView getView() {
		return view;
	}

	public void setView(GridView view) {
		this.view = view;
	}

	public int getPresentScore() {
		return presentScore;
	}

	public void setPresentScore(int presentScore) {
		this.presentScore = presentScore;
	}

	public int getBestScore() {
		return bestScore;
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}

	public int getAddScore() {
		return addScore;
	}

	public void setAddScore(int addScore) {
		this.addScore = addScore;
	}

}
