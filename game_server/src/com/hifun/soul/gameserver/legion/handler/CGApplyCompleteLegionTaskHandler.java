package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionTask;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGApplyCompleteLegionTask;
import com.hifun.soul.gameserver.legion.msg.GCApplyCompleteLegionTask;

@Component
public class CGApplyCompleteLegionTaskHandler implements
		IMessageHandlerWithType<CGApplyCompleteLegionTask> {

	@Override
	public short getMessageType() {
		return MessageType.CG_APPLY_COMPLETE_LEGION_TASK;
	}

	@Override
	public void execute(CGApplyCompleteLegionTask message) {
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
		// 计算消费
		int costCrystal = (int) ((task.getEndTime() - GameServerAssist
				.getSystemTimeService().now()) / TimeUtils.MIN + 1);

		GCApplyCompleteLegionTask msg = new GCApplyCompleteLegionTask();
		msg.setTaskId(taskId);
		msg.setCostCrystal(costCrystal);
		human.sendMessage(msg);
	}
}
