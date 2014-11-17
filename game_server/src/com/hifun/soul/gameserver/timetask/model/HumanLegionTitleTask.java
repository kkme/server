package com.hifun.soul.gameserver.timetask.model;

import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionHonor;
import com.hifun.soul.gameserver.legion.template.LegionHonorTemplate;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.timetask.IHeartBeatTask;

/**
 * 角色军团头衔到期任务
 * 
 * @author yandajun
 * 
 */
public class HumanLegionTitleTask implements IHeartBeatTask {

	private Human human;

	public HumanLegionTitleTask(Human human) {
		this.human = human;
	}

	@Override
	public void run() {
		human.setLegionTitleValid(false);
		// 减去加成的属性
		LegionHonorTemplate titleTemplate = GameServerAssist
				.getLegionTemplateManager().getHonorTemplate(
						human.getLegionTitleId());
		if (titleTemplate != null) {
			int propertyId = titleTemplate.getPropertyId();
			int propertyValue = titleTemplate.getAmendEffect();
			AmendMethod amendType = AmendMethod.valueOf(titleTemplate
					.getAmendMethod());
			human.getPropertyManager().amendProperty(human, amendType,
					propertyId, -propertyValue, PropertyLogReason.LEGION_TITLE,
					"");
		}
		// 修改头衔兑换人数
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				human.getHumanGuid());
		if (legion != null) {
			LegionHonor legionHonor = GameServerAssist.getGlobalLegionManager()
					.getHonor(legion, human.getLegionTitleId());
			if (legionHonor != null) {
				legionHonor.setExchangeNum(legionHonor.getExchangeNum() - 1);
				if (legionHonor.getExchangeNum() < 0) {
					legionHonor.setExchangeNum(0);
				}
				GameServerAssist.getGlobalLegionManager().updateHonor(
						legionHonor);
			}
		}
		// 下发头衔信息
		human.getHumanLegionManager().sendHumanLegionTitleInfo();
	}

	@Override
	public void stop() {

	}

	@Override
	public boolean isTimeUp(long now) {
		// 有效的军团头衔到期处理
		if (human.getLegionTitleId() > 0
				&& human.isLegionTitleValid()
				&& human.getPropertyManager().getLongPropertyValue(
						HumanLongProperty.LEGION_TITLE_END_TIME) <= GameServerAssist
						.getSystemTimeService().now()) {
			return true;
		}
		return false;
	}

	@Override
	public void nextRound() {

	}

	@Override
	public long getRunTime() {
		return 0;
	}

}
