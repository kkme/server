package com.hifun.soul.mytest;

/**
 * 数据拷贝函数测试
 * 
 * @author yandajun
 * 
 */
public class ArrayCopyTest {
	public static void main(String[] args) {
		int[] a1 = new int[10];
		for (int i = 0; i < a1.length; i++) {
			a1[i] = i + 1;
		}

		int[] a2 = new int[a1.length];
		System.arraycopy(a1, 0, a2, 1, 2);
		outArray(a2);
	}

	private static void outArray(int[] a) {
		for (int i : a) {
			System.out.println(i);
		}
	}
}
