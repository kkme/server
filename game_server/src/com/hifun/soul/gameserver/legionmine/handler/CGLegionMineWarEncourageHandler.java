package com.hifun.soul.gameserver.legionmine.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionmine.LegionMineMember;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.legionmine.manager.LegionMineWarTemplateManager;
import com.hifun.soul.gameserver.legionmine.msg.CGLegionMineWarEncourage;
import com.hifun.soul.gameserver.legionmine.msg.GCLegionMineWarEncourage;
import com.hifun.soul.gameserver.legionmine.template.LegionMineConstantsTemplate;
import com.hifun.soul.gameserver.matchbattle.EncourageType;

@Component
public class CGLegionMineWarEncourageHandler implements
		IMessageHandlerWithType<CGLegionMineWarEncourage> {
	@Autowired
	private GlobalLegionMineWarManager globalLegionMineWarManager;
	@Autowired
	private LegionMineWarTemplateManager templateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_LEGION_MINE_WAR_ENCOURAGE;
	}

	@Override
	public void execute(CGLegionMineWarEncourage message) {
		Human human = message.getPlayer().getHuman();
		LegionMineMember mineMember = globalLegionMineWarManager
				.getJoinLegionMineMember(human.getHumanGuid());
		if (mineMember == null) {
			return;
		}
		LegionMineConstantsTemplate constantsTemplate = templateManager
				.getConstantsTemplate();
		// 是否达到鼓舞上限
		if (mineMember.getEncourageRate() >= constantsTemplate
				.getMaxEncourageRate()) {
			human.sendWarningMessage(LangConstants.LEGION_MINE_MAX_ENCOURAGE_RATE);
			return;
		}
		int encourageSuccessRate = 0;
		int costNum = 0;
		EncourageType encourageType = EncourageType.indexOf(message
				.getEncourageType());
		switch (encourageType) {
		// 魔晶鼓舞
		case CRYSTAL:
			encourageSuccessRate = constantsTemplate.getCrystalEncourageRate();
			costNum = constantsTemplate.getCrystalEncourageCost();
			if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costNum,
					MoneyLogReason.LEGION_MINE_ENCOURAGE, "")) {
				if (MathUtils.shake(encourageSuccessRate
						/ SharedConstants.DEFAULT_FLOAT_BASE)) {
					encourageSuccess(human, mineMember);
				} else {
					human.sendGenericMessage(LangConstants.LEGION_MINE_ENCOURAGE_FAIL);
				}
			}
			break;
		// 冥想力鼓舞
		case MEDITATION:
			encourageSuccessRate = constantsTemplate
					.getMeditationEncourageRate();
			costNum = constantsTemplate.getMeditationEncourageCost();
			// 消耗冥想力鼓舞
			if (human.getHumanTechnologyManager().costTechnologyPoints(costNum)) {
				if (MathUtils.shake(encourageSuccessRate
						/ SharedConstants.DEFAULT_FLOAT_BASE)) {
					encourageSuccess(human, mineMember);
				} else {
					human.sendGenericMessage(LangConstants.LEGION_MINE_ENCOURAGE_FAIL);
				}
			}
			break;
		}
	}

	/**
	 * 鼓舞成功处理
	 * 
	 */
	public void encourageSuccess(Human human, LegionMineMember mineMember) {
		LegionMineConstantsTemplate constantsTemplate = templateManager
				.getConstantsTemplate();
		// 加鼓舞
		int newEncourageRate = mineMember.getEncourageRate()
				+ constantsTemplate.getEncourageRate();
		if (newEncourageRate > constantsTemplate.getMaxEncourageRate()) {
			newEncourageRate = constantsTemplate.getMaxEncourageRate();
		}
		mineMember.setEncourageRate(newEncourageRate);
		// 更新数据库
		globalLegionMineWarManager.updateLegionMineMember(mineMember);
		// 剩余鼓舞次数
		int remainEncourageTimes = (constantsTemplate.getMaxEncourageRate() - newEncourageRate) / 1000;
		human.sendImportantMessage(LangConstants.LEGION_MINE_ENCOURAGE_SUCCESS,
				constantsTemplate.getEncourageRate() / 100,
				remainEncourageTimes);

		GCLegionMineWarEncourage gcMsg = new GCLegionMineWarEncourage();
		gcMsg.setEncourageRate(newEncourageRate);
		gcMsg.setIsFull(newEncourageRate >= constantsTemplate
				.getMaxEncourageRate());
		gcMsg.setAttackRate(mineMember.getAttackRate());
		human.sendMessage(gcMsg);

	}
}
