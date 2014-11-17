package com.hifun.soul.mytest;

import java.util.ArrayList;
import java.util.List;

public class ListTest {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		int size = list.size();
		for (int i = size - 1; list.size() > 1; i--) {
			list.remove(i);
		}
		List<Integer> newList = list.subList(0, 1);
		for (int i : newList) {
			System.out.println(i);
		}
		ConcurrentExceptionTest(list);
	}

	/**
	 * 同步异常测试
	 */
	public static void ConcurrentExceptionTest(List<Integer> list) {
		int size = list.size();
		System.out.println("size1:" + list.size());
		for (int i = 0; i < size; i++) {
			if (i == 1) {
				list.remove(i);
			}
			System.out.print("i:" + i + " ");
		}
	}
}
