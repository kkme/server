package com.hifun.soul.mytest;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapTest {
	public static void main(String[] args) {
		Map<SS, Integer> map = new TreeMap<SS, Integer>(new Comparator<SS>() {

			@Override
			public int compare(SS o1, SS o2) {
				// 从大到小
				if (o1.getId() > o2.getId()) {
					return -1;
				} else if (o1.getId() < o2.getId()) {
					return 1;
				}
				return 0;
			}
		});
		map.put(new SS(2), 2);
		map.put(new SS(1), 1);
		for (SS ss : map.keySet()) {
			System.out.println(ss.getId());
		}
	}
}

class SS implements Comparable<SS> {
	private int id;

	public SS(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(SS o) {
		// 第一个跟第二个比，返回1就放右边，即从小到大排列
		if (this.id > o.getId())
			return 1;
		else if (this.id < o.getId())
			return -1;
		return 0;
	}

}