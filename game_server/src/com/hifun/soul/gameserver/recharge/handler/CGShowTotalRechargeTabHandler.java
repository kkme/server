package com.hifun.soul.gameserver.recharge.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.recharge.RechargeActivityState;
import com.hifun.soul.gameserver.recharge.RechargeActivityType;
import com.hifun.soul.gameserver.recharge.msg.CGShowTotalRechargeTab;
import com.hifun.soul.gameserver.recharge.msg.GCShowTotalRechargeTab;
import com.hifun.soul.gameserver.recharge.msg.TotalRechargeGradeInfo;
import com.hifun.soul.gameserver.recharge.template.RechargeActivityTimeTemplate;

@Component
public class CGShowTotalRechargeTabHandler implements
		IMessageHandlerWithType<CGShowTotalRechargeTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_TOTAL_RECHARGE_TAB;
	}

	@Override
	public void execute(CGShowTotalRechargeTab message) {
		Human human = message.getPlayer().getHuman();
		// 校验是否在活动时间内
		RechargeActivityState activityState = GameServerAssist
				.getRechargeTemplateManager().getRechargeActivityState(
						RechargeActivityType.TOTAL_RECHARGE);
		// 还没有到时间，或没有活动(活动进行中或是已经过时，都显示)
		if (activityState == RechargeActivityState.NO_ACTIVITY
				|| activityState == RechargeActivityState.PRE_TIME) {
			return;
		}
		GCShowTotalRechargeTab msg = new GCShowTotalRechargeTab();
		RechargeActivityTimeTemplate template = GameServerAssist
				.getRechargeTemplateManager().getRechargeActivityTimeTemplate(
						RechargeActivityType.TOTAL_RECHARGE);
		List<TotalRechargeGradeInfo> rewardGradeInfoList = human
				.getHumanRechargeManager().getTotalRechargeRewardList();
		// 发送累计充值奖励面板消息
		msg.setActivityDesc(template.getActivityDesc());
		msg.setStartDate(template.getStartDate());
		msg.setEndDate(template.getEndDate());
		msg.setRewardGrades(rewardGradeInfoList
				.toArray(new TotalRechargeGradeInfo[0]));
		human.sendMessage(msg);
	}
}
