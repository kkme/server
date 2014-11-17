package com.hifun.soul.gameserver.rank.manager;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.gamedb.entity.VipRankEntity;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.rank.HumanVipRank;
import com.hifun.soul.gameserver.rank.model.HumanVipRankData;

/**
 * 角色VIP排行榜管理器
 * 
 * @author yandajun
 * 
 */
public class HumanVipRankManager {
	private Human human;

	public HumanVipRankManager(Human human) {
		this.human = human;
	}

	/**
	 * 更新VIP榜数据
	 */
	public void updateVipRankData() {
		@SuppressWarnings("unchecked")
		HumanVipRank<HumanVipRankData> vipRank = ApplicationContext
				.getApplicationContext().getBean(HumanVipRank.class);
		VipRankEntity entity = new VipRankEntity();
		entity.setId(human.getHumanGuid());
		entity.setHumanName(human.getName());
		entity.setVipLevel(human.getVipLevel());
		entity.setLevel(human.getLevel());
		entity.setOccupation(human.getOccupation().getIndex());
		entity.setValid(true);
		vipRank.addUpdate(entity);
	}
}
