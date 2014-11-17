package com.hifun.soul.mytest;

import com.hifun.soul.common.constants.SharedConstants;

public class FloatTest {
	public static void main(String[] args) {
		float f = 3000 / SharedConstants.DEFAULT_FLOAT_BASE;
		int i = 25;
		i += i * f;
		System.out.println(i);
	}
}
