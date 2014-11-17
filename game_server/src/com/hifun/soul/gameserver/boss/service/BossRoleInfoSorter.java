package com.hifun.soul.gameserver.boss.service;

import java.util.Comparator;

import com.hifun.soul.gameserver.boss.BossRoleInfo;

public class BossRoleInfoSorter implements Comparator<BossRoleInfo>{

	@Override
	public int compare(BossRoleInfo roleA, BossRoleInfo roleB) {
		return roleB.getDamage() - roleA.getDamage();
	}
	
}
