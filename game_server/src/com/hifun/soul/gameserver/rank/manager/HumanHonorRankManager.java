package com.hifun.soul.gameserver.rank.manager;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.gamedb.entity.HonorRankEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.rank.HumanHonorRank;
import com.hifun.soul.gameserver.rank.model.HumanHonorRankData;

/**
 * 角色荣誉排行榜管理器
 * 
 * @author yandajun
 * 
 */
public class HumanHonorRankManager {
	private Human human;

	public HumanHonorRankManager(Human human) {
		this.human = human;
	}

	/**
	 * 更新荣誉榜数据
	 */
	public void updateHonorRankData() {
		@SuppressWarnings("unchecked")
		HumanHonorRank<HumanHonorRankData> honorRank = ApplicationContext
				.getApplicationContext().getBean(HumanHonorRank.class);
		HonorRankEntity entity = new HonorRankEntity();
		entity.setId(human.getHumanGuid());
		entity.setHumanName(human.getName());
		entity.setHonor(human.getArenaHonor());
		entity.setLevel(human.getLevel());
		entity.setOccupation(human.getOccupation().getIndex());
		entity.setValid(true);
		honorRank.addUpdate(entity);
	}
}
