package com.hifun.soul.gameserver.human;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.LevelUpEvent;
import com.hifun.soul.gameserver.human.msg.GCHumanLevelUp;
import com.hifun.soul.gameserver.human.msg.GCHumanLevelUpInfo;
import com.hifun.soul.gameserver.human.template.HumanLevelUpTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;

/**
 * 角色升级管理器;
 * 
 * @author crazyjohn
 * 
 */
public class HumanLevelUpManager {
	private Human owner;

	public HumanLevelUpManager(Human owner) {
		this.owner = owner;
	}

	public Human getOwner() {
		return this.owner;
	}

	/**
	 * 升级;
	 */
	public void onLevelUp() {
		int oldLevel = owner.getLevel();
		// 是否到满级
		if (oldLevel >= SharedConstants.MAX_HUMAN_LEVEL) {
			// 置零
			owner.setExperience(SharedConstants.MAX_HUMAN_LEVEL_EXP);
			return;
		}
		// 升级相关业务; 血量增加&分配潜能点;
		this.owner.setLevel(oldLevel + 1);
		
		// 通知客户单角色升级
		GCHumanLevelUp levelUpMsg = new GCHumanLevelUp();
		levelUpMsg.setCurrentLevel(this.owner.getLevel());
		this.owner.sendMessage(levelUpMsg);
		
		HumanLevelUpTemplate oldLevelTemplate = GameServerAssist.getTemplateService().get(oldLevel,
				HumanLevelUpTemplate.class);
		if (oldLevelTemplate == null) {
			return;
		}
		// 通知客户端角色升级信息
		GCHumanLevelUpInfo levelUpInfoMsg = new GCHumanLevelUpInfo();
		levelUpInfoMsg.setOldLevel(oldLevel);
		levelUpInfoMsg.setCurrentLevel(oldLevel + 1);
		levelUpInfoMsg.setOldHp(owner.getHp());
		
		levelUpInfoMsg.setOldPropPoint(
				GameServerAssist.getLevelUpTemplateManager().getTotalPropPoints(oldLevel));
		levelUpInfoMsg.setCurrentPropPoint(
				GameServerAssist.getLevelUpTemplateManager().getTotalPropPoints(oldLevel+1));
		levelUpInfoMsg.setOldSkillPoint(
				GameServerAssist.getLevelUpTemplateManager().getTotalSkillPoints(oldLevel));
		levelUpInfoMsg.setCurrentSkillPoint(
				GameServerAssist.getLevelUpTemplateManager().getTotalSkillPoints(oldLevel+1));
		//计算当前血量
		HumanLevelUpTemplate currentLevelTemplate = GameServerAssist.getTemplateService().get(owner.getLevel(),
				HumanLevelUpTemplate.class);
		if (currentLevelTemplate == null) {
			levelUpInfoMsg.setCurrentHp(owner.getHp());
		}else{
			levelUpInfoMsg.setCurrentHp(owner.getHp()+currentLevelTemplate.getHp()-oldLevelTemplate.getHp());
		}		
		this.owner.sendMessage(levelUpInfoMsg);
		// 添加点数
		owner.setUnDistributePropertyPoint(owner.getUnDistributePropertyPoint()
				+ oldLevelTemplate.getLevel1propCount());
		// 设置血量
		// FIXME: crazyjohn 这里有问题
		//owner.setHp(owner.getHp() + oldLevelTemplate.getHp());

		HumanLevelUpTemplate newLevelTemplate = GameServerAssist.getTemplateService().get(owner.getLevel(),
				HumanLevelUpTemplate.class);
		if (newLevelTemplate == null) {
			return;
		}
		// 设置角色的最大经验值
		owner.getPropertyManager()
				.getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.setPropertyValue(HumanIntProperty.MAX_EXPERIENCE,
						newLevelTemplate.getExperience());

		// 发送升级事件;
		LevelUpEvent levelUpEvent = new LevelUpEvent();
		levelUpEvent.setCurrentLevel(owner.getLevel());
		owner.getEventBus().fireEvent(levelUpEvent);
		// 重新计算属性
		this.owner.getPropertyManager().recalculateInitProperties(this.owner);
	}
	
	protected int getMaxExpericen(int level) {
		if (level <= 0) {
			throw new IllegalArgumentException(
					"Leval must larger than 0, level: " + level);
		}
		HumanLevelUpTemplate nextTemplate = GameServerAssist.getTemplateService().get(level,
				HumanLevelUpTemplate.class);
		if (nextTemplate == null) {
			return GameServerAssist.getLevelUpTemplateManager().getMaxLevelTemplate()
					.getExperience();
		}
		return nextTemplate.getExperience();
	}

	/**
	 * 获取从某个From等级升级到To等级所得的一级属性点;
	 * 
	 * @param from
	 *            开始等级;
	 * @param to
	 *            升级到的等级;
	 * @return
	 */
	protected int getLeve1PropCount(int from, int to) {
		if (from > to) {
			throw new IllegalArgumentException(
					"Level error, from larger than to, from: " + from
							+ "- to: " + to);
		}
		if (from == to) {
			return 0;
		}
		HumanLevelUpTemplate toTemplate = GameServerAssist.getTemplateService().get(to,
				HumanLevelUpTemplate.class);
		if (to - from == 1) {
			return toTemplate.getLevel1propCount();
		}
		int result = 0;
		for (int i = from + 1; i <= to; i++) {
			result += GameServerAssist.getTemplateService().get(i, HumanLevelUpTemplate.class)
					.getLevel1propCount();
		}
		return result;
	}

	/**
	 * 获取合适的升级模版;
	 * 
	 * @param experience
	 *            角色现有的经验值;
	 * @return
	 */
	protected HumanLevelUpTemplate getSuitLevelTemplate(int experience) {
		HumanLevelUpTemplate result = null;
		for (HumanLevelUpTemplate each : GameServerAssist.getLevelUpTemplateManager()
				.getSortedTemplates()) {
			if (each.getExperience() <= experience) {
				continue;
			}
			result = each;
			break;

		}
		if (result == null) {
			return GameServerAssist.getLevelUpTemplateManager().getMaxLevelTemplate();
		}
		return result;
	}
	
	/**
	 * 是否达到顶级
	 * @return
	 */
	public boolean maxLevelReached(){
		return owner.getLevel() >= GameServerAssist.getLevelUpTemplateManager().getHumanMaxLevel();
	}
}
