package com.hifun.soul.gameserver.target.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;
import com.hifun.soul.gameserver.target.msg.CGOpenTargetPanel;
import com.hifun.soul.gameserver.target.msg.GCOpenTargetPanel;
import com.hifun.soul.gameserver.target.msg.info.TargetInfo;
import com.hifun.soul.gameserver.target.msg.info.TargetRewardInfo;
import com.hifun.soul.gameserver.target.template.TargetReward;
import com.hifun.soul.gameserver.target.template.TargetTemplate;

@Component
public class CGOpenTargetPanelHandler implements
		IMessageHandlerWithType<CGOpenTargetPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_TARGET_PANEL;
	}

	@Override
	public void execute(CGOpenTargetPanel message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.TARGET, true)) {
			return;
		}
		// 个人目标是否全部完成
		if (human.getHumanTargetManager().isAllTargetsCompleted()) {
			return;
		}
		GCOpenTargetPanel msg = new GCOpenTargetPanel();
		Map<Integer, TargetTemplate> targetTemplates = GameServerAssist
				.getTargetTemplateManager().getTargetTemplates();
		List<TargetInfo> targetInfoList = new ArrayList<TargetInfo>();
		for (int targetId : targetTemplates.keySet()) {
			TargetTemplate template = targetTemplates.get(targetId);
			TargetInfo targetInfo = new TargetInfo();
			// 从模板中获取目标基本信息
			targetInfo.setTargetId(targetId);
			targetInfo.setTargetName(template.getTargetName());
			targetInfo.setTargetDesc(template.getTargetDesc());
			targetInfo.setTargetTaskDesc(template.getTargetTaskDesc());
			targetInfo.setTargetIcon(template.getTargetIcon());
			targetInfo.setTargetType(template.getTargetType());
			targetInfo.setTargetParam(template.getTargetParam());
			// 领奖状态
			targetInfo.setRewardState(human.getHumanTargetManager()
					.getRewardState(targetId));
			// 奖励信息
			TargetReward[] rewards = template.getTargetRewards();
			List<TargetRewardInfo> rewardInfoList = new ArrayList<TargetRewardInfo>();
			for (int i = 0; i < rewards.length; i++) {
				TargetRewardInfo rewardInfo = new TargetRewardInfo();
				rewardInfo.setItemNum(rewards[i].getItemNum());
				SimpleCommonItem commonItem = CommonItemBuilder
						.genSimpleCommonItem(rewards[i].getItemId());
				if (commonItem == null) {
					continue;
				}
				rewardInfo.setCommonItem(commonItem);
				rewardInfoList.add(rewardInfo);
			}
			targetInfo.setRewardInfos(rewardInfoList
					.toArray(new TargetRewardInfo[0]));
			targetInfoList.add(targetInfo);
		}
		Collections.sort(targetInfoList, new TargetListSorter());
		msg.setTargetInfos(targetInfoList.toArray(new TargetInfo[0]));
		human.sendMessage(msg);
	}
}

/**
 * 目标排序
 * 
 */
class TargetListSorter implements Comparator<TargetInfo> {

	@Override
	public int compare(TargetInfo o1, TargetInfo o2) {
		return o1.getTargetId() - o2.getTargetId();
	}

}
