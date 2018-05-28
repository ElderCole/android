package com.wit.managers;

import java.util.LinkedList;
import java.util.Stack;

import com.wit.beans.GridView;

public class GridManager {
	private static GridManager manager = new GridManager();

	private GridManager() {
	};

	public static GridManager getGridsManager() {
		return manager;
	}

	private int stackSize;

	public int getStackSize() {
		return stackSize;
	}

	public void setStackSize(int stackSize) {
		this.stackSize = stackSize;
	}

	private LinkedList<GridView> stack = new LinkedList<GridView>();
	private Stack<GridView> cache = new Stack<GridView>();

	public void push(GridView config) {
		if (stackSize !=0){
			while (stack.size() >= stackSize) {
				stack.removeFirst();
			}
			stack.addLast(config);
		}
	}

	public GridView pop() {
		return stack.pollLast();
	}

	public GridView peek() {
		return stack.getLast();
	}

	public void clearCache() {
		cache.removeAllElements();
	}

	public void clearStack() {
		stack.clear();
	}

	public LinkedList<GridView> getStack() {
		return stack;
	}

	public Stack<GridView> getCache() {
		return cache;
	}
}
