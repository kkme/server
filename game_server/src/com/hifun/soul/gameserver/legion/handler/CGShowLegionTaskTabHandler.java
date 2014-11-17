package com.hifun.soul.gameserver.legion.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.LegionTask;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.info.LegionBuildingInfo;
import com.hifun.soul.gameserver.legion.info.LegionTaskInfo;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.manager.LegionTemplateManager;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionTaskTab;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionTaskTab;

@Component
public class CGShowLegionTaskTabHandler implements
		IMessageHandlerWithType<CGShowLegionTaskTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_TASK_TAB;
	}

	@Override
	public void execute(CGShowLegionTaskTab message) {
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
		// 建筑信息
		LegionBuildingInfo buildingInfo = globalLegionManager
				.generateBuildingInfo(legion, LegionBuildingType.TASK);
		if (buildingInfo == null) {
			return;
		}
		// 军团成员
		LegionMember legionMember = globalLegionManager.getLegionMember(human
				.getHumanGuid());
		if (legionMember == null) {
			return;
		}
		LegionTemplateManager templateManager = GameServerAssist
				.getLegionTemplateManager();
		GCShowLegionTaskTab msg = new GCShowLegionTaskTab();
		msg.setLegionBuildingInfo(buildingInfo);
		msg.setSystemRefreshTime(templateManager.getConstantsTemplate()
				.getSystemRefreshTaskTime());
		msg.setRefreshCost(templateManager.getConstantsTemplate()
				.getRefreshTaskCostCrystal());
		msg.setSystemTheme(globalLegionManager.getTaskThemeMap().get(
				human.getLegionTaskThemeType()));
		msg.setTodayScore(legionMember.getTodayTaskScore());
		msg.setHasRankReward(globalLegionManager.hasTaskRankReward(human));
		msg.setMaxTaskNum(buildingInfo.getCurrentNum());
		msg.setRemainTaskNum(buildingInfo.getCurrentNum()
				- human.getLegionReceivedTaskNum());
		// 悬赏任务列表
		List<LegionTaskInfo> taskInfoList = new ArrayList<LegionTaskInfo>();
		for (Integer taskId : human.getHumanLegionManager().getLegionTaskMap()
				.keySet()) {
			LegionTask task = human.getHumanLegionManager().getLegionTaskMap()
					.get(taskId);
			LegionTaskInfo info = human.getHumanLegionManager()
					.generateTaskInfo(task);
			taskInfoList.add(info);
		}
		msg.setTaskInfos(taskInfoList.toArray(new LegionTaskInfo[0]));
		human.sendMessage(msg);
	}

}
