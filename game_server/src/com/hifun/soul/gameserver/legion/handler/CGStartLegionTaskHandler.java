package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionTask;
import com.hifun.soul.gameserver.legion.enums.LegionTaskState;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGStartLegionTask;
import com.hifun.soul.gameserver.legion.msg.GCStartLegionTask;
import com.hifun.soul.gameserver.legion.template.LegionTaskTemplate;

@Component
public class CGStartLegionTaskHandler implements
		IMessageHandlerWithType<CGStartLegionTask> {

	@Override
	public short getMessageType() {
		return MessageType.CG_START_LEGION_TASK;
	}

	@Override
	public void execute(CGStartLegionTask message) {
		Human human = message.getPlayer().getHuman();
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验军团是否存在
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		int taskId = message.getTaskId();
		LegionTask task = human.getHumanLegionManager().getLegionTask(taskId);
		if (task == null) {
			return;
		}
		// 开始任务
		LegionTaskTemplate template = GameServerAssist
				.getLegionTemplateManager().getTaskTemplate(
						task.getTemplateId());
		task.setEndTime(template.getTaskTime() * TimeUtils.MIN
				+ GameServerAssist.getSystemTimeService().now());
		task.setTaskState(LegionTaskState.UNDERWAY.getIndex());
		human.getHumanLegionManager().updateLegionTask(task);

		GCStartLegionTask msg = new GCStartLegionTask();
		msg.setTaskInfo(human.getHumanLegionManager().generateTaskInfo(task));
		human.sendMessage(msg);
	}
}
