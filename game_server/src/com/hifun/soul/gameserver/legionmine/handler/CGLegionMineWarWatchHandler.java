package com.hifun.soul.gameserver.legionmine.handler;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionmine.LegionMine;
import com.hifun.soul.gameserver.legionmine.LegionMineMember;
import com.hifun.soul.gameserver.legionmine.enums.OperateType;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.legionmine.msg.CGLegionMineWarWatch;
import com.hifun.soul.gameserver.legionmine.msg.GCUpdateLegionMineList;
import com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo;

@Component
public class CGLegionMineWarWatchHandler implements
		IMessageHandlerWithType<CGLegionMineWarWatch> {
	@Autowired
	private GlobalLegionMineWarManager globalLegionMineWarManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_LEGION_MINE_WAR_WATCH;
	}

	@Override
	public void execute(CGLegionMineWarWatch message) {
		final Human human = message.getPlayer().getHuman();
		final LegionMineMember mineMember = globalLegionMineWarManager
				.getJoinLegionMineMember(human.getHumanGuid());
		if (mineMember == null) {
			return;
		}
		// 在大本营中不能侦查
		if (mineMember.getMineIndex() == 0) {
			human.sendWarningMessage(LangConstants.LEGION_MINE_CANT_WATCH);
			return;
		}
		// 判断周围有没有敌对军团
		LegionMine legionMine = globalLegionMineWarManager
				.getLegionMine(mineMember.getMineIndex());
		boolean canWatch = false;
		for (String mineIndex : legionMine.getSurroundIndexes().split(",")) {
			List<LegionMineMember> mineMemberList = globalLegionMineWarManager
					.getMembersOnMine(Integer.parseInt(mineIndex));
			if (mineMemberList.size() > 0
					&& mineMemberList.get(0).getLegionType() != mineMember
							.getLegionType()) {
				canWatch = true;
				break;
			}
		}
		if (!canWatch) {
			human.sendWarningMessage(LangConstants.LEGION_MINE_WATCH_NONE);
			return;
		}
		int costNum = GameServerAssist.getLegionMineWarTemplateManager()
				.getConstantsTemplate().getWatchCostCraystal();
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costNum,
				MoneyLogReason.LEGION_MINE_WATCH, "")) {
			final GCUpdateLegionMineList msg = new GCUpdateLegionMineList();
			mineMember.setWatching(true);
			msg.setMineInfos(globalLegionMineWarManager
					.generateLegionMineInfos(mineMember, OperateType.COMMON)
					.toArray(new LegionMineInfo[0]));
			human.sendMessage(msg);
			// 侦查持续时间到更新
			Timer timer = new Timer();
			int watchHoldTime = GameServerAssist
					.getLegionMineWarTemplateManager().getConstantsTemplate()
					.getWatchHoldTime();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					mineMember.setWatching(false);
					msg.setMineInfos(globalLegionMineWarManager
							.generateLegionMineInfos(mineMember,
									OperateType.COMMON).toArray(
									new LegionMineInfo[0]));
					human.sendMessage(msg);
				}
			}, watchHoldTime * TimeUtils.SECOND);

		}
	}
}
