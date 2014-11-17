package com.hifun.soul.mytest;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.core.util.MathUtils;

public class WeightTest {
	public static void main(String[] args) {
		int count = 3;
		List<Integer> weightList = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			if (i > 2) {
				weightList.add(0);
			} else {
				weightList.add(1);
			}
		}
		int[] indexs = MathUtils.getRandomUniqueIndex(weightList, count);
		System.out.println("sie:" + indexs.length);
		for (int index : indexs) {
			System.out.println(index);
		}
	}
}
