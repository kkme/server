package com.hifun.soul.gameserver.legionboss.service;

import java.util.Comparator;

import com.hifun.soul.gameserver.legionboss.LegionBossRoleInfo;

public class LegionBossRoleInfoSorter implements Comparator<LegionBossRoleInfo> {

	@Override
	public int compare(LegionBossRoleInfo roleA, LegionBossRoleInfo roleB) {
		return roleB.getDamage() - roleA.getDamage();
	}

}
