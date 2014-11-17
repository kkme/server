package com.hifun.soul.mytest;

import java.util.HashMap;
import java.util.Map;

public class RandomArrestListTest {
	public static void main(String[] args) {
		int arrestNum = 10;
		Map<Integer, Integer> prisoner = new HashMap<Integer, Integer>();
		prisoner.put(1, 1);
		prisoner.put(2, 1);
		prisoner.put(3, 0);
		prisoner.put(4, 1);

		prisoner.put(5, 1);

		prisoner.put(6, 2);
		prisoner.put(7, 3);
		int level = 5;
		int num = prisoner.get(level);
		if (num < arrestNum) {
			System.out.println("init:" + num);
			for (int lower = level - 1, higher = level + 1; !(lower == 0 && higher == 8);) {
				if (lower >= 1) {
					num += prisoner.get(lower);
					System.out.print("lower:" + prisoner.get(lower) + " ");
					lower--;
				}
				if (higher <= 7) {
					num += prisoner.get(higher);
					System.out.print("higher:" + prisoner.get(higher));
					higher++;
				}
				System.out.println();
				if (num >= arrestNum) {
					break;
				}

			}
		}
		System.out.println("total:" + num);
	}
}
