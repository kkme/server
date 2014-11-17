package com.hifun.soul.gameserver.legion.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.activity.ActivityState;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.template.ActivityTemplate;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.enums.LegionBossMineWarState;
import com.hifun.soul.gameserver.legion.enums.LegionBuildingType;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionMineTab;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionMineTab;
import com.hifun.soul.gameserver.legionmine.enums.JoinLegionType;
import com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo;

@Component
public class CGShowLegionMineTabHandler implements
		IMessageHandlerWithType<CGShowLegionMineTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_MINE_TAB;
	}

	@Override
	public void execute(CGShowLegionMineTab message) {
		Human human = message.getPlayer().getHuman();
		// 军团矿战功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION_MINE_WAR, true)) {
			return;
		}
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验军团是否存在
		Legion legion = globalLegionManager.getLegion(human.getHumanGuid());
		if (legion == null) {
			return;
		}
		// 军团子功能是否开启
		if (!globalLegionManager.legionFuncIsOpen(human,
				LegionBuildingType.MINE_WAR, true)) {
			return;
		}
		GCShowLegionMineTab msg = new GCShowLegionMineTab();
		msg.setWarState(getLegionBossMineWarState(human));
		msg.setBossWarTime(getWarTime(ActivityType.LEGION_BOSS));
		msg.setMineWarTime(getWarTime(ActivityType.LEGION_MINE));
		List<JoinLegionInfo> joinLegionInfoList = GameServerAssist
				.getGlobalLegionMineWarManager().getJoinLegionInfos();
		if (joinLegionInfoList.size() > 1) {
			msg.setSecondLegionName(joinLegionInfoList.get(1).getLegionName());
		} else if (joinLegionInfoList.size() > 0) {
			msg.setFirstLegionName(joinLegionInfoList.get(0).getLegionName());
		}
		msg.setHasMineWarRight(GameServerAssist.getGlobalLegionMineWarManager()
				.getJoinLegionType(human.getHumanGuid()) != JoinLegionType.NO_LEGION);
		human.sendMessage(msg);
	}

	/**
	 * 获取矿战状态
	 */
	public int getLegionBossMineWarState(Human human) {
		int bossState = human.getHumanActivityManager().getActivityState(
				ActivityType.LEGION_BOSS);
		int mineState = human.getHumanActivityManager().getActivityState(
				ActivityType.LEGION_MINE);

		if (bossState == ActivityState.OPEN.getIndex()) {
			return LegionBossMineWarState.BOSS_FIGHTING.getIndex();
		}
		if (mineState == ActivityState.OPEN.getIndex()) {
			return LegionBossMineWarState.MINE_FIGHTING.getIndex();
		}
		if (bossState != ActivityState.FINISH.getIndex()) {
			return LegionBossMineWarState.BOSS_NOT_OPEN.getIndex();
		}
		if (bossState == ActivityState.FINISH.getIndex()
				&& mineState != ActivityState.FINISH.getIndex()) {
			return LegionBossMineWarState.BOSS_END_MINE_NOT_OPEN.getIndex();
		}
		if (mineState == ActivityState.FINISH.getIndex()) {
			return LegionBossMineWarState.MINE_FIGHTING.getIndex();
		}
		return 0;
	}

	/**
	 * 获取活动时间
	 */
	public String getWarTime(ActivityType type) {
		ActivityTemplate activityTempalte = GameServerAssist
				.getActivityTemplateManager().getActivityTemplate(
						type.getIndex());
		// 模板如[1043-1044]
		String timeSpan = activityTempalte.getActiveTimeSpan();
		String[] timeSpans = timeSpan.split(",");
		StringBuffer timeDesc = new StringBuffer();
		for (String span : timeSpans) {
			String innerSpan = span.replace("[", "");
			innerSpan = innerSpan.replace("]", "");
			String startTime = innerSpan.split("-")[0];
			String endTime = innerSpan.split("-")[1];
			String startTimeDesc = GameServerAssist
					.getTimeTaskTemplateManager().getTriggerTimeDesc(
							Integer.valueOf(startTime));
			String endTimeDesc = GameServerAssist.getTimeTaskTemplateManager()
					.getTriggerTimeDesc(Integer.valueOf(endTime));
			timeDesc.append(startTimeDesc + " - " + endTimeDesc + " ");
		}
		return timeDesc.toString();
	}
}
