package com.hifun.soul.mytest;

import java.util.*;

public class LinkedListTest {
	static LinkedList<Log> linkedList = new LinkedList<Log>();

	public static void main(String[] args) {
		Log log1 = new Log(1);
		Log log2 = new Log(2);
		Log log3 = new Log(3);
		List<Log> list = new ArrayList<Log>();
		list.add(log1);
		list.add(log3);
		list.add(log2);
		Collections.sort(list);
		for (Log log : list) {
			if (linkedList.size() < Constants.NUM) {
				linkedList.addFirst(log);
			}
		}
		add(new Log(4));
		out(linkedList);
		
	}

	public static <T> void add(Log log) {
		if (linkedList.size() >= Constants.NUM) {
			linkedList.removeFirst();
		}
		linkedList.addLast(log);
	}

	public static <T> void out(LinkedList<T> list) {
		for (T t : list) {
			System.out.println(t.toString());
		}
	}
}

class Constants {
	public final static int NUM = 2;
}

class Log implements Comparable<Log> {
	private int id;

	public Log(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.id + "";
	}

	@Override
	public int compareTo(Log o) {
		if (o.getId() > this.id) {
			return 1;
		} else if (o.getId() < this.id) {
			return -1;
		}
		return 0;
	}
}