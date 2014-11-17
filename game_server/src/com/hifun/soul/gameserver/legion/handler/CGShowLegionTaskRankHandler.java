package com.hifun.soul.gameserver.legion.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.info.LegionTaskRankInfo;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionTaskRank;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionTaskRank;

@Component
public class CGShowLegionTaskRankHandler implements
		IMessageHandlerWithType<CGShowLegionTaskRank> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_TASK_RANK;
	}

	@Override
	public void execute(CGShowLegionTaskRank message) {
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
		List<LegionMember> memberList = globalLegionManager
				.getLegionMemberList(legion);
		// 任务排行
		List<LegionTaskRankInfo> rankInfoList = new ArrayList<LegionTaskRankInfo>();
		for (LegionMember member : memberList) {
			// 积分为0，不参与排行
			if (member.getTodayTaskScore() == 0) {
				continue;
			}
			LegionTaskRankInfo info = new LegionTaskRankInfo();
			info.setHumanName(member.getMemberName());
			info.setHumanLevel(member.getLevel());
			int rank = globalLegionManager.getTaskScoreRank(member
					.getHumanGuid());
			info.setRank(rank);
			info.setRewardMedal(GameServerAssist.getLegionTemplateManager()
					.getTaskRankTemplate(rank).getRewardMedal());
			info.setScore(member.getTodayTaskScore());
			rankInfoList.add(info);
		}
		Collections.sort(rankInfoList);
		// 返回消息
		GCShowLegionTaskRank msg = new GCShowLegionTaskRank();
		msg.setRankInfos(rankInfoList.toArray(new LegionTaskRankInfo[0]));
		human.sendMessage(msg);
	}
}
