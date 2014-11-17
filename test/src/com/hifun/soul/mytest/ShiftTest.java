package com.hifun.soul.mytest;

/**
 * java移位测试
 * 
 * @author yandajun
 * 
 */
public class ShiftTest {
	public static void main(String[] args) {
		int a = 1;
		a = a<<2;
		System.out.println(Integer.toBinaryString(a));
		a = a>>2;
		System.out.println(Integer.toBinaryString(a));
	}
}
