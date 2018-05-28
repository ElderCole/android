package com.wit.beans;


public class GridView {
	private final int SIZE = com.wit.utils.Constant.GAME_SIZE;
	private int[][] data = new int[SIZE][SIZE];
	public GridView(int[][] data){
		for(int i=0;i<SIZE;i++){
			System.arraycopy(data[i], 0, this.data[i], 0, SIZE);
		}
	}
	public int[][] getData() {
		return data;
	}
	public void setData(int[][] data) {
		this.data = data;
	}
}
