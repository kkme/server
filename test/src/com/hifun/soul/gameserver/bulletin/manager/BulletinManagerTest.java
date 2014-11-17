package com.hifun.soul.gameserver.bulletin.manager;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hifun.soul.gameserver.bulletin.Bulletin;
import com.hifun.soul.gameserver.bulletin.RegularBulletin;

public class BulletinManagerTest {
	List<Bulletin> blist=null;
	RegularBulletin regular = null;
	BulletinManager manager=null;
	Method getSuitableBulletins=null;
	@Before
	public void setUp() throws Exception {
		// 构建测试环境
				blist = new ArrayList<Bulletin>();
				regular = new RegularBulletin();
				regular.setContent("regular bulletin");
				Calendar startDate = Calendar.getInstance();
				startDate.set(2012, 5, 18, 0, 0, 0);// 2012-6-18
				regular.setStartDate(startDate.getTime());
				Calendar endDate = Calendar.getInstance();
				endDate.set(2012, 5, 20, 0, 0, 0);
				regular.setEndDate(endDate.getTime());
				Calendar startTime = Calendar.getInstance();
				startTime.set(2012, 5, 18, 12, 0, 0);
				regular.setStartTime(startTime.getTime());
				Calendar endTime = Calendar.getInstance();
				endTime.set(2012, 5, 18, 18, 0, 0);
				regular.setEndTime(endTime.getTime());
				regular.setPublishInterval(30 * 60); // 30分钟循环一次
				
//				periodical = new PeriodicalBulletin();
//				periodical.setContent("periodical bulletin");
//				startDate.set(2012, 5, 18, 0, 0, 0);// 2012-6-18
//				periodical.setStartDate(startDate.getTime());
//				endDate.set(2012, 5, 20, 0, 0, 0);
//				periodical.setEndDate(endDate.getTime());
//				Calendar time1 = Calendar.getInstance();
//				time1.set(2012, 5, 18, 12, 0, 0);
//				periodical.getPublishTimeList().add(time1.getTime());
//				Calendar time2 = Calendar.getInstance();
//				time2.set(2012, 5, 18, 14, 0, 0);
//				periodical.getPublishTimeList().add(time2.getTime());
//				Calendar time3 = Calendar.getInstance();
//				time3.set(2012, 5, 18, 20, 0, 0);
//				periodical.getPublishTimeList().add(time3.getTime());
				
				manager = new BulletinManager();
				Field bulletins = manager.getClass().getDeclaredField("bulletins");
				bulletins.setAccessible(true);
				bulletins.set(manager, blist);
				getSuitableBulletins = manager.getClass().getDeclaredMethod(
						"getSuitableBulletins", Date.class);
				getSuitableBulletins.setAccessible(true);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSuitableBulletins() throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			NoSuchFieldException {

		// 用于测试的时间
		Calendar calTest = Calendar.getInstance();
		
		int count = 0;
		System.out.println(blist.size());
		
		//Case 1
		blist.clear();
		blist.add(regular);
		calTest.set(2012, 5, 18, 13, 29, 0);		
		count = testRegularBulletinPublish(manager,getSuitableBulletins,calTest);
		assertEquals(23, count);
		
		//Case 2
		blist.clear();
		blist.add(regular);
		calTest.set(2012, 5, 18, 13, 34, 0);
		count = testRegularBulletinPublish(manager,getSuitableBulletins,calTest);
		assertEquals(22, count);
	
		//Case 3
		blist.clear();
		blist.add(regular);
		calTest.set(2012, 5, 19, 17, 26, 0);
		count = testRegularBulletinPublish(manager,getSuitableBulletins,calTest);
		assertEquals(2, count);
		

		blist.clear();
//		blist.add(periodical);
		calTest.set(2012, 5, 18, 13, 57, 0);
//		calTest.set(2012, 5, 19, 11, 54, 0);
//		calTest.set(2012, 5, 18, 19, 59, 0);
		count = testRegularBulletinPublish(manager,getSuitableBulletins,calTest);
		assertEquals(5, count);
		
	}
	
	private int testRegularBulletinPublish(Object instance,Method getSuitableBulletins, Calendar time) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		int count = 0;
		int heartbeat = 1 * 60; // 1分钟
		int runtimes = 3 * 24 * 60 * 60 / heartbeat;// 3天运行次数
		for (int i = 0; i < runtimes; i++) {
			Object[] args = new Object[1];
			args[0] = time.getTime();
			Object result = getSuitableBulletins.invoke(instance, args);
			Bulletin[] suitableBulletins = (Bulletin[]) result;
			for (Bulletin b : suitableBulletins) {
				System.out.println(b.getContent());
				System.out.println(time.getTime().toString());
				count++;
			}
			time.add(Calendar.SECOND, heartbeat);
		}
		return count;
	}
	

	
}
