package com.hifun.soul.mytest;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.gameserver.cd.CdType;

public class EnumTest {
	public static void main(String[] args) {
		CdType[] types = CdType.values();
		List<CdType> slaveCdTypes = new ArrayList<CdType>();
		for (CdType type : types) {
			if (type.toString().startsWith("PRISON_SLAVE_CD")) {
				slaveCdTypes.add(type);
			}
		}
		System.out.println(slaveCdTypes.contains(CdType.indexOf(25)));
		for (int i = 0; i < slaveCdTypes.size(); i++) {
			System.out.println(slaveCdTypes.get(i).getIndex());
		}
	}
}
