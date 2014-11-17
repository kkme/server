package com.hifun.soul.gameserver.human;

import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;

public interface IHumanPropertiesLoadForBattle {

	/**
	 * 从角色实体中加载离线战斗玩家的数据;
	 * 
	 * @param humanEntity
	 *            角色实体;
	 */
	public void onBattlePropertiesLoad(HumanEntity humanEntity, HumanPropertyManager propertyManager);
}
