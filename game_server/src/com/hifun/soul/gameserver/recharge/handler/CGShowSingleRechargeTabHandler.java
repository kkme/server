package com.hifun.soul.gameserver.recharge.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.recharge.RechargeActivityState;
import com.hifun.soul.gameserver.recharge.RechargeActivityType;
import com.hifun.soul.gameserver.recharge.msg.CGShowSingleRechargeTab;
import com.hifun.soul.gameserver.recharge.msg.GCShowSingleRechargeTab;
import com.hifun.soul.gameserver.recharge.msg.SingleRechargeGradeInfo;
import com.hifun.soul.gameserver.recharge.template.RechargeActivityTimeTemplate;

@Component
public class CGShowSingleRechargeTabHandler implements
		IMessageHandlerWithType<CGShowSingleRechargeTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_SINGLE_RECHARGE_TAB;
	}

	@Override
	public void execute(CGShowSingleRechargeTab message) {
		Human human = message.getPlayer().getHuman();
		// 校验是否在活动时间内
		RechargeActivityState activityState = GameServerAssist
				.getRechargeTemplateManager().getRechargeActivityState(
						RechargeActivityType.SINGLE_RECHARGE);
		GCShowSingleRechargeTab msg = new GCShowSingleRechargeTab();
		// 还没有到时间，或没有活动
		if (activityState == RechargeActivityState.NO_ACTIVITY
				|| activityState == RechargeActivityState.PRE_TIME) {
			return;
		}
		RechargeActivityTimeTemplate template = GameServerAssist
				.getRechargeTemplateManager().getRechargeActivityTimeTemplate(
						RechargeActivityType.SINGLE_RECHARGE);
		List<SingleRechargeGradeInfo> rewardGradeInfoList = human
				.getHumanRechargeManager().getSingleRechargeRewardList();
		// 发送首充奖励面板消息
		msg.setActivityDesc(template.getActivityDesc());
		msg.setStartDate(template.getStartDate());
		msg.setEndDate(template.getEndDate());
		msg.setRewardGrades(rewardGradeInfoList
				.toArray(new SingleRechargeGradeInfo[0]));
		human.sendMessage(msg);
	}
}
