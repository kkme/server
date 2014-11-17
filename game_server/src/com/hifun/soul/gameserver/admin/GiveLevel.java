package com.hifun.soul.gameserver.admin;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.template.HumanLevelUpTemplate;

public class GiveLevel {
	public static void upLevel(Human human) {
		int level = 6;// 升到的级别
		if (HumanGuids.inArray(human.getHumanGuid())) {
			int oldLevel = human.getLevel();
			int needExperience = 0;
			for (int i = oldLevel + 1; i <= level; i++) {
				needExperience += GameServerAssist.getTemplateService()
						.get(i, HumanLevelUpTemplate.class).getExperience();
			}
			human.addExperience(needExperience, true,
					ExperienceLogReason.GM_ADD_EXP, "");
		}
	}
}

class HumanGuids {
	public static long[] array = new long[] { 288797724151645161L,
			288797724151645162L, 288797724151646163L, 288797724151647164L,
			288797724151648165L, 288797724151648166L, 288797724151648167L,
			288797724151649168L, 288797724151649169L, 288797724151649170L,
			288797724151650171L, 288797724151651172L, 288797724151652173L,
			288797724151653174L };

	public static boolean inArray(long humanId) {
		for (long id : array) {
			if (humanId == id) {
				return true;
			}
		}
		return false;
	}
}