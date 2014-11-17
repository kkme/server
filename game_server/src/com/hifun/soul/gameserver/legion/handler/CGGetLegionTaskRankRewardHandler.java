package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGGetLegionTaskRankReward;
import com.hifun.soul.gameserver.legion.msg.GCGetLegionTaskRankReward;
import com.hifun.soul.gameserver.legion.template.LegionTaskRankTemplate;

@Component
public class CGGetLegionTaskRankRewardHandler implements
		IMessageHandlerWithType<CGGetLegionTaskRankReward> {

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_LEGION_TASK_RANK_REWARD;
	}

	@Override
	public void execute(CGGetLegionTaskRankReward message) {
		Human human = message.getPlayer().getHuman();
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验军团是否存在
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		// 军团子功能是否开启
		if (!globalLegionManager.legionFuncIsOpen(human,
				LegionBuildingType.TASK, true)) {
			return;
		}
		// 军团成员
		LegionMember legionMember = globalLegionManager.getLegionMember(human
				.getHumanGuid());
		if (legionMember == null) {
			return;
		}
		// 是否有奖
		if (!globalLegionManager.hasTaskRankReward(human)) {
			return;
		}
		LegionTaskRankTemplate rankTemplate = GameServerAssist
				.getLegionTemplateManager().getTaskRankTemplate(
						legionMember.getYesterdayScoreRank());
		if (rankTemplate == null) {
			return;
		}
		// 奖励贡献
		int rewardContribution = rankTemplate.getRewardContribution();
		globalLegionManager
				.addSelfContribution(human, rewardContribution, true);
		// 奖励勋章
		globalLegionManager.addSelfMedal(human, rankTemplate.getRewardMedal(),
				true);
		human.setIsGodLegionTaskRankReward(true);
		// 返回消息
		GCGetLegionTaskRankReward msg = new GCGetLegionTaskRankReward();
		msg.setHasRankReward(false);
		human.sendMessage(msg);
	}
}
