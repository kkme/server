package com.hifun.soul.gameserver.skill;

import java.util.Comparator;

import com.hifun.soul.gameserver.skill.template.SkillInfo;

public class SkillInfoSorter implements Comparator<SkillInfo> {

	@Override
	public int compare(SkillInfo o1, SkillInfo o2) {
		return o1.getSkillId() - o2.getSkillId();
	}

}
