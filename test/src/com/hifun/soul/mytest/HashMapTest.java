package com.hifun.soul.mytest;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {
	public static void main(String[] args) {
		Map<Long, String> map = new HashMap<Long, String>();
		map.put((long) 1, "ss");
		System.out.println(map.get(Long.parseLong("1")));
		System.out.println(map.size());
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("21", "hah");
		Integer in = new Integer(21);
		System.out.println(in.toString());

		map.put(11L, "aab");
		System.out.println(map.get((long) 11));

		System.out.println(map.get(new SSS()));
		
		mapValuesTest(map);
	}

	public static void mapValuesTest(Map<Long, String> map) {
		System.out.println(map.values());
	}
}

class SSS {
}
