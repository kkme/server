package com.hifun.soul.mytest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListSortTest {
	public static void main(String[] args) {
		List<S> list = new ArrayList<S>();
		list.add(new S(1, "abc"));
		list.add(new S(5, "x"));
		list.add(new S(1, "23"));
		list.add(new S(1, "45"));
		list.add(new S(44, "麦福"));
		list.add(new S(55, "麦尔达"));
		list.add(new S(44, "波妮芙"));
		Collections.sort(list, new Comparator<S>() {

			@Override
			public int compare(S o1, S o2) {
				// 第一个跟第二个比，返回1就放右边，即从小到大排列
				// 如果倒序排列，反之
				if (o1.getAge() > o2.getAge()) {
					return -1;
				} else if (o1.getAge() < o2.getAge()) {
					return 1;
				}
				return 0;
			}
		});
		for (S s : list) {
			System.out.println(s);
		}
	}
}

class S extends BS implements Comparable<S> {
	private int age;
	private String name;

	@Override
	public int compareTo(S o) {
		// 第一个跟第二个比，返回-1就放左边，表示优先，即从小到大排列
		if (this.age < o.getAge()) {
			return -1;
		} else if (this.age > o.getAge()) {
			return 1;
		} else {
			return o.getName().compareTo(this.name);
		}
	}

	public S(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name + ":" + this.age;
	}
}

class BS {
}