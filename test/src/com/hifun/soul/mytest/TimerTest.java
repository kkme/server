package com.hifun.soul.mytest;

import java.util.*;
import com.hifun.soul.core.util.TimeUtils;

public class TimerTest {
	public static void main(String[] args) {
		test(new String("sss"));
	}

	private static void test(final String s) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println(s);
			}
		}, 5 * TimeUtils.SECOND);

	}
}
