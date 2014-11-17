package com.hifun.soul.mytest;

public class ThreadTest {
	public static class MyThread extends Thread {
		public MyThread(MyRun myRun) {
			super(myRun);
		}

		public MyThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			super.run();
			System.out.println(this.getClass().getSimpleName() + "run");
		}
	}
	
	public static class MyRun implements Runnable {

		@Override
		public void run() {
			System.out.println(this.getClass().getSimpleName() + "run");
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MyThread().start();
		new MyThread(new MyRun()).start();
	}

}
