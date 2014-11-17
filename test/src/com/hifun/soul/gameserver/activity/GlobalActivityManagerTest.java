package com.hifun.soul.gameserver.activity;

import java.net.URL;

import com.hifun.soul.core.template.TemplateService;

public class GlobalActivityManagerTest {

	public void testActivityTimeTask() {
		TemplateService templateService = new TemplateService();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("templates.xml");
		templateService.setResourceFolder("");
		templateService.setDebug(true);
		templateService.init(url);

	}
	
//		/**
//		 * test method
//		 * @param args
//		 */
//		public static void main(String[] args){
//			long todayBegin = TimeUtils.getBeginOfDay(new Date().getTime());
//			List<KeyValuePair<Long, Long>> timeSpanList = new ArrayList<KeyValuePair<Long, Long>>();
//			for(int i=0;i<100;i+=10){
//				KeyValuePair<Long, Long> pair = new KeyValuePair<Long, Long>();
//				pair.setKey(10L+i);
//				pair.setValue(20L+i);
//				timeSpanList.add(pair);
//				i+=10;
//			}
//			int index=0;
//			for(KeyValuePair<Long, Long> pair : timeSpanList){
//				System.out.println("index:" + index +",key:"+pair.getKey()+",value:"+pair.getValue());
//				index++;
//			}
//			
//			GlobalActivityManager manager = new GlobalActivityManager();
//			System.out.println("value:5, index:"+ manager.getTimeSpanIndex(5L+todayBegin, timeSpanList));
//			System.out.println("value:10, index:"+ manager.getTimeSpanIndex(10L+todayBegin, timeSpanList));
//			System.out.println("value:11, index:"+ manager.getTimeSpanIndex(11L+todayBegin, timeSpanList));
//			System.out.println("value:15, index:"+ manager.getTimeSpanIndex(15L+todayBegin, timeSpanList));
//			System.out.println("value:20, index:"+ manager.getTimeSpanIndex(20L+todayBegin, timeSpanList));
//			System.out.println("value:21, index:"+ manager.getTimeSpanIndex(21L+todayBegin, timeSpanList));
//			System.out.println("value:25, index:"+ manager.getTimeSpanIndex(25L+todayBegin, timeSpanList));
//			System.out.println("value:30, index:"+ manager.getTimeSpanIndex(30L+todayBegin, timeSpanList));
//			System.out.println("value:35, index:"+ manager.getTimeSpanIndex(35L+todayBegin, timeSpanList));
//			System.out.println("value:40, index:"+ manager.getTimeSpanIndex(40L+todayBegin, timeSpanList));
//			System.out.println("value:41, index:"+ manager.getTimeSpanIndex(41L+todayBegin, timeSpanList));
//			System.out.println("value:99, index:"+ manager.getTimeSpanIndex(99L+todayBegin, timeSpanList));
//			System.out.println("value:100, index:"+ manager.getTimeSpanIndex(100L+todayBegin, timeSpanList));
//			System.out.println("value:120, index:"+ manager.getTimeSpanIndex(120L+todayBegin, timeSpanList));
//
//		}
//	}
}
