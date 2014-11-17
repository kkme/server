package com.hifun.soul.mytest.thread;

import java.util.Scanner;

public class JvmHookTest {
	public static void main(String[] args) {
		// 注册钩子
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("JVM is closed");
			}
		});
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						System.out.println("thread");
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

		// 关闭服务的线程
		new Thread() {
			@Override
			public void run() {
				while (true) {
					Scanner sc = new Scanner(System.in);
					System.out.print("Please enter close commander : ");
					if ("0".equals(sc.next())) {
						System.exit(0);
					}
				}
			}
		}.start();
	}
}
