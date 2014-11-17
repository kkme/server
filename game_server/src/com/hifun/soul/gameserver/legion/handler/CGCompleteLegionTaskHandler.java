package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionTask;
import com.hifun.soul.gameserver.legion.enums.LegionTaskState;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGCompleteLegionTask;
import com.hifun.soul.gameserver.legion.msg.GCCompleteLegionTask;

@Component
public class CGCompleteLegionTaskHandler implements
		IMessageHandlerWithType<CGCompleteLegionTask> {

	@Override
	public short getMessageType() {
		return MessageType.CG_COMPLETE_LEGION_TASK;
	}

	@Override
	public void execute(CGCompleteLegionTask message) {
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
		long now = GameServerAssist.getSystemTimeService().now();
		int costCrystal = (int) ((task.getEndTime() - now) / TimeUtils.MIN + 1);
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
				MoneyLogReason.LEGION_TASK_COMPLETE, "")) {
			// 加速完成任务
			task.setEndTime(now);
			task.setTaskState(LegionTaskState.END.getIndex());
			task.setHasReward(true);
			human.getHumanLegionManager().updateLegionTask(task);

			GCCompleteLegionTask msg = new GCCompleteLegionTask();
			msg.setTaskInfo(human.getHumanLegionManager()
					.generateTaskInfo(task));
			human.sendMessage(msg);
		}
	}
}
