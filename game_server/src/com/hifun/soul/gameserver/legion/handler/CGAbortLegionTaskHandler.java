package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionTask;
import com.hifun.soul.gameserver.legion.enums.LegionTaskState;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGAbortLegionTask;
import com.hifun.soul.gameserver.legion.msg.GCAbortLegionTask;

@Component
public class CGAbortLegionTaskHandler implements
		IMessageHandlerWithType<CGAbortLegionTask> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ABORT_LEGION_TASK;
	}

	@Override
	public void execute(CGAbortLegionTask message) {
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
		// 取消任务
		task.setEndTime(GameServerAssist.getSystemTimeService().now());
		task.setTaskState(LegionTaskState.END.getIndex());
		human.getHumanLegionManager().updateLegionTask(task);

		GCAbortLegionTask msg = new GCAbortLegionTask();
		msg.setTaskInfo(human.getHumanLegionManager().generateTaskInfo(task));
		human.sendMessage(msg);
	}
}
