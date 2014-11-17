package com.hifun.soul.mytest;

public class ThreadYeildTest {
	public static class MyThread extends Thread {
		@Override
		public void run() {
			System.out.println("first");
			yield();
			System.out.print("finally");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MyThread().start();
	}

}
