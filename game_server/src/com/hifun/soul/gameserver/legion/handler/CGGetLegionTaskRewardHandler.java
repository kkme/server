package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.LegionTask;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.info.LegionBuildingInfo;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.manager.LegionTemplateManager;
import com.hifun.soul.gameserver.legion.msg.CGGetLegionTaskReward;
import com.hifun.soul.gameserver.legion.msg.GCGetLegionTaskReward;
import com.hifun.soul.gameserver.legion.template.LegionTaskTemplate;

@Component
public class CGGetLegionTaskRewardHandler implements
		IMessageHandlerWithType<CGGetLegionTaskReward> {

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_LEGION_TASK_REWARD;
	}

	@Override
	public void execute(CGGetLegionTaskReward message) {
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
		LegionMember member = globalLegionManager.getLegionMember(human
				.getHumanGuid());
		if (member == null) {
			return;
		}
		// 建筑
		LegionBuildingInfo buildingInfo = globalLegionManager
				.generateBuildingInfo(legion, LegionBuildingType.TASK);
		if (buildingInfo == null) {
			return;
		}
		int taskId = message.getTaskId();
		LegionTask task = human.getHumanLegionManager().getLegionTask(taskId);
		if (task == null) {
			return;
		}
		// 剩余任务次数
		if (human.getLegionReceivedTaskNum() >= buildingInfo.getCurrentNum()) {
			human.sendWarningMessage(LangConstants.LEGION_TASK_NO_TIMES);
			return;
		}
		// 减少任务次数
		human.setLegionReceivedTaskNum(human.getLegionReceivedTaskNum() + 1);
		// 收获奖励(如果跟系统主题一致加倍)
		LegionTemplateManager templateManager = GameServerAssist
				.getLegionTemplateManager();
		LegionTaskTemplate taskTemplate = templateManager.getTaskTemplate(task
				.getTemplateId());
		int multiple = 1;
		if (taskTemplate.getShemeType() == human.getLegionTaskThemeType()) {
			multiple = templateManager.getConstantsTemplate()
					.getSameThemeMultiple();
			human.sendSuccessMessage(LangConstants.LEGION_TASK_SAME_THEME);
		}
		// 个人贡献
		globalLegionManager.addSelfContribution(human,
				taskTemplate.getRewardContribution() * multiple, true);
		// 个人勋章
		globalLegionManager.addSelfMedal(human, taskTemplate.getRewardMedal()
				* multiple, true);
		// 个人积分(不需要记录日志)
		member.addTodayTaskScore(human, taskTemplate.getRewardTaskScore()
				* multiple, true);
		// 军团经验
		globalLegionManager.addExperience(human, legion,
				taskTemplate.getRewardLegionExperience() * multiple, true);
		// 军团资金
		globalLegionManager.addLegionCoin(human,
				taskTemplate.getRewardLegionCoin() * multiple, true);

		// 刷新军团任务
		human.getHumanLegionManager().refreshTask(taskId, true);
		// 返回消息
		GCGetLegionTaskReward msg = new GCGetLegionTaskReward();
		msg.setRemainTaskNum(buildingInfo.getCurrentNum()
				- human.getLegionReceivedTaskNum());
		LegionTask newTask = human.getHumanLegionManager()
				.getLegionTask(taskId);
		msg.setTaskInfo(human.getHumanLegionManager().generateTaskInfo(newTask));
		msg.setTodayTaskScore(member.getTodayTaskScore());
		msg.setLegionCoin(legion.getLegionCoin());
		human.sendMessage(msg);
	}
}
