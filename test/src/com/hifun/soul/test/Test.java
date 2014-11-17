package com.hifun.soul.test;

import java.io.UnsupportedEncodingException;

public class Test {
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println("123456789".getBytes("UTF-8").length);
		System.out.println(5000.0 / 10000);
	}
	
	interface ITest {
		int i = 2;
	}
}
