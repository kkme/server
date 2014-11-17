package com.hifun.soul.gameserver.technology.service;

import java.util.Comparator;

import com.hifun.soul.gameserver.technology.template.TechnologyTemplate;

public class TechnologySorter implements Comparator<TechnologyTemplate>{
	
	@Override
	public int compare(TechnologyTemplate template1, TechnologyTemplate template2) {
		return template1.getId() - template2.getId();
	}
	
}
