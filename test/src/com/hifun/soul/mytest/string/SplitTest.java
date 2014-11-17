package com.hifun.soul.mytest.string;

public class SplitTest {
	public static void main(String[] args) {
		String s = "288797724151645161,288797724151645161";
		String[] array = s.split(",");
		System.out.println(array.length);
		for (int i = 0; i < array.length; i++) {
			System.out.println(Long.parseLong(array[i]));
		}
	}
}
