package com.hifun.soul.gameserver.horoscope.sorter;

import java.util.Comparator;

import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;

public class HoroscopeInfoSorter implements Comparator<HoroscopeInfo>{
	
	@Override
	public int compare(HoroscopeInfo horoscopeInfoA, HoroscopeInfo horoscopeInfoB) {
		if (horoscopeInfoA.getColor() != horoscopeInfoB.getColor()) {
			return horoscopeInfoB.getColor() - horoscopeInfoA.getColor();
		}
		
		if (horoscopeInfoA.getExperience() != horoscopeInfoB.getExperience()) {
			return horoscopeInfoB.getExperience() - horoscopeInfoA.getExperience();
		}
		
		if (horoscopeInfoA.getHoroscopeId() != horoscopeInfoB.getHoroscopeId()) {
			return horoscopeInfoB.getHoroscopeId() - horoscopeInfoA.getHoroscopeId();
		}

		return 0;
	}
	
}
