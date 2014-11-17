package com.hifun.soul.gameserver.escort.manager;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

public class HumanEscortManager implements ILoginManager {
	private Human human;
	private boolean isOpenPanel;
	private GlobalEscortManager globalEscortManager;

	public HumanEscortManager(Human human) {
		this.human = human;
		this.human.registerLoginManager(this);
		globalEscortManager = GameServerAssist.getGlobalEscortManager();
	}

	@Override
	public void onLogin() {
		// 默认怪物类型
		int monsterType = globalEscortManager.getEscortMonsterType(human
				.getHumanGuid());
		if (monsterType == GlobalEscortManager.initMonsterType) {
			VipPrivilegeTemplate vipTemplate = GameServerAssist
					.getVipPrivilegeTemplateManager().getVipTemplate(
							human.getVipLevel());
			globalEscortManager.setEscortMonsterType(human.getHumanGuid(),
					vipTemplate.getDefaultEscortMonsterType());
		}
	}

	/**
	 * 重置角色每日在押运系统的数据
	 */
	public void resetEscortData() {
		human.setEscortRemainNum(GameServerAssist.getEscortTemplateManager()
				.getConstantsTemplate().getMaxEscortNum());
		human.setEscortRobRemainNum(GameServerAssist.getEscortTemplateManager()
				.getConstantsTemplate().getMaxRobNum());
		human.setEscortRobBuyNum(0);
		human.setEscortRefreshMonsterNum(0);
		human.setEscortHelpRemainNum(GameServerAssist
				.getEscortTemplateManager().getConstantsTemplate()
				.getMaxHelpNum());
	}

	public void setLastResetDataTime(long lastTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_RESET_ESCORT_DATA_TIME, lastTime);
	}

	public long getLastResetDataTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_RESET_ESCORT_DATA_TIME);
	}

	public boolean isOpenPanel() {
		return isOpenPanel;
	}

	public void setOpenPanel(boolean isOpenPanel) {
		this.isOpenPanel = isOpenPanel;
	}

}
