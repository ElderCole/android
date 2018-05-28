package com.wit.managers;

import com.wit.beans.Memory;

public class MemoryManager {
	private static MemoryManager memoryManager = new MemoryManager();
	private Memory memory;

	public static MemoryManager getMemoryManger() {
		return memoryManager;
	}

	private MemoryManager() {
	}

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}
}
