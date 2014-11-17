package com.hifun.soul.mytest;

public class LongTest {
	public static void main(String[] args) {
		Long s = null;
		new C(s);
		Long l = 288797724151645162L;
		System.out.println(l.toString());
	}
}

class C {
	private Long a;

	C(Long a) {
		this.a = a;
	}

	void out() {
		System.out.println(a);
	}
}