package com.hifun.soul.mytest;

import java.text.ParseException;

import com.hifun.soul.core.util.TimeUtils;

public class TimeUtilsTest {
	public static void main(String[] args) {
		System.out.println(TimeUtils.HOUR);// 计算对应的毫秒
		String startDate = "2013-12-25";
		String endDate = "2013-12-27";
		System.out.println(isInTheDay(startDate, endDate));
	}

	private static boolean isInTheDay(String start, String end) {
		long now = System.currentTimeMillis();
		try {
			long startTime = TimeUtils.getYMDTime(start);
			System.out.println(startTime);
			long endTime = TimeUtils.getYMDTime(end) + TimeUtils.DAY - 1;
			System.out.println(endTime);
			if (now >= startTime && now <= endTime) {
				return true;
			}
		} catch (ParseException e) {
			System.out.println("date is illegal");
			e.printStackTrace();
		}
		return false;
	}
}
