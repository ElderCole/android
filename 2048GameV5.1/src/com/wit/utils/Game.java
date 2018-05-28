package com.wit.utils;

import java.util.ArrayList;
import java.util.List;

import com.wit.managers.GridManager;

public class Game {
	private static int SIZE = com.wit.utils.Constant.GAME_SIZE;

	public static int[][] moveRight(int[][] nums) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE / 2; j++) {
				swap(nums[i], j, SIZE - j - 1);
			}
		}
		for (int i = 0; i < SIZE; i++)
			move(nums[i]);

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE / 2; j++) {
				swap(nums[i], j, SIZE - j - 1);
			}
		}
		return nums;
	}

	public static int[][] moveDown(int[][] nums) {
		int[][] temp = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				temp[i][j] = nums[SIZE - 1 - j][i];
			}
		}
		for (int i = 0; i < SIZE; i++)
			move(temp[i]);
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				nums[i][j] = temp[j][SIZE - 1 - i];
			}
		}
		return nums;
	}

	public static int[][] moveLeft(int[][] nums) {
		for (int i = 0; i < SIZE; i++) {
			move(nums[i]);
		}
		return nums;
	}

	public static int[][] moveUp(int[][] nums) {
		int[][] temp = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				temp[i][j] = nums[j][i];
			}
		}
		for (int i = 0; i < SIZE; i++)
			move(temp[i]);
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				nums[i][j] = temp[j][i];
			}
		}
		return nums;
	}

	/**
	 * ��һ��һά�����е������󻬶�
	 * 
	 * @param arr
	 */
	public static void move(int[] arr) {
		boolean flag = true;
		int firstIndex = 0;
		int cursor = 0;
		for (int i = 0; i < SIZE; i++) {
			if (arr[i] != 0) {
				if (flag) {
					firstIndex = i;
					flag = false;
				} else {
					if (arr[firstIndex] == arr[i]) {
						arr[firstIndex] = arr[firstIndex] << 1;
						getBackTimes(arr[firstIndex]);
						arr[i] = 0;
						swap(arr, cursor, firstIndex);
						firstIndex = i;
						cursor++;
						flag = true;
					} else {
						swap(arr, cursor, firstIndex);
						cursor++;
						firstIndex = i;
					}
				}
			}
		}
		if (arr[firstIndex] != 0 && firstIndex > cursor) {
			swap(arr, cursor, firstIndex);
		}
	}

	/**
	 * ���ݺϳ���ֵ�Ĵ�С������˲��Ĵ������ϳɵ���ֵ��256�ı���������1�λ��� 
	 * 
	 * @param num
	 */
	private static void getBackTimes(int num) {
		int times = num / 256;
		if (times > 0) {
			GridManager.getGridsManager().setStackSize(
					GridManager.getGridsManager().getStackSize() + 1);
		}
	}

	/**
	 * ͬһ�������н�������λ�õ�ֵ
	 * 
	 * @param arr
	 * @param firstIndex
	 * @param secondIndex
	 */
	public static void swap(int[] arr, int firstIndex, int secondIndex) {
		if (firstIndex == secondIndex) {
			return;
		}
		int temp = arr[firstIndex];
		arr[firstIndex] = arr[secondIndex];
		arr[secondIndex] = temp;
	}

	/**
	 * �������������е�λ�õ�ֵ
	 * 
	 * @param firstArr
	 * @param firstIndex
	 * @param secondArr
	 * @param secondIndex
	 */
	public static void swap(int[] firstArr, int firstIndex, int[] secondArr,
			int secondIndex) {
		int temp = firstArr[firstIndex];
		firstArr[firstIndex] = secondArr[secondIndex];
		secondArr[secondIndex] = temp;

	}

	/**
	 * ��һ����ά�����У�ֵΪ0������λ�������һ��λ������һ���������2��4��
	 * 
	 * @param nums
	 */
	public static void createRandomNum(int[][] nums) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (nums[i][j] == 0) {
					list.add(i * SIZE + j);
				}
			}

		}
		int leng = list.size();
		if (leng == 0) {
			// ��Ϸ����
		}
		int position = (int) (Math.random() * leng);
		nums[(Integer) list.get(position) / SIZE][(Integer) list.get(position)
				% SIZE] = 2;
	}

	/**
	 * �������һ����ά�����������ֵ�����ڲ�����
	 * 
	 * @param arr
	 */
	public static void ergodic(int[][] arr) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(arr[i][j] + "\t");
			}
			System.out.println();
		}

	}

	public static boolean isSame(int[][] arr1, int[][] arr2) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (arr1[i][j] != arr2[i][j])
					return false;
			}
		}
		return true;
	}

	/**
	 * ��¡һ����ά����
	 * 
	 * @param array
	 * @return
	 */
	public static int[][] cloneArray(int[][] array) {
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
}
