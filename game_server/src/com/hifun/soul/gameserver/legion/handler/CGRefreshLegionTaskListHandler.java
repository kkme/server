package com.hifun.soul.gameserver.legion.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionTask;
import com.hifun.soul.gameserver.legion.info.LegionTaskInfo;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGRefreshLegionTaskList;
import com.hifun.soul.gameserver.legion.msg.GCRefreshLegionTaskList;

@Component
public class CGRefreshLegionTaskListHandler implements
		IMessageHandlerWithType<CGRefreshLegionTaskList> {

	@Override
	public short getMessageType() {
		return MessageType.CG_REFRESH_LEGION_TASK_LIST;
	}

	@Override
	public void execute(CGRefreshLegionTaskList message) {
		Human human = message.getPlayer().getHuman();
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验军团是否存在
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		// 判断是否有可以刷新的任务
		if (!human.getHumanLegionManager().hasCanRefreshTask()) {
			human.sendErrorMessage(LangConstants.LEGION_TASK_CAN_NOT_REFRESH);
			return;
		}

		// 消耗魔晶刷新
		int costCrystal = GameServerAssist.getLegionTemplateManager()
				.getConstantsTemplate().getRefreshTaskCostCrystal();
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
				MoneyLogReason.REFRESH_LEGION_TASK_LIST, "")) {
			human.getHumanLegionManager().refreshTaskList(false);

			GCRefreshLegionTaskList msg = new GCRefreshLegionTaskList();
			List<LegionTaskInfo> taskInfoList = new ArrayList<LegionTaskInfo>();
			for (Integer taskId : human.getHumanLegionManager()
					.getLegionTaskMap().keySet()) {
				LegionTask task = human.getHumanLegionManager()
						.getLegionTaskMap().get(taskId);
				LegionTaskInfo info = human.getHumanLegionManager()
						.generateTaskInfo(task);
				taskInfoList.add(info);
			}
			msg.setTaskInfos(taskInfoList.toArray(new LegionTaskInfo[0]));
			human.sendMessage(msg);
		}
	}

}
