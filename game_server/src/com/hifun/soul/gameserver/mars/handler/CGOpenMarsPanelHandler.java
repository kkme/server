package com.hifun.soul.gameserver.mars.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.GameConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mars.manager.MarsTemplateManager;
import com.hifun.soul.gameserver.mars.msg.CGOpenMarsPanel;
import com.hifun.soul.gameserver.mars.msg.GCOpenMarsPanel;
import com.hifun.soul.gameserver.mars.msg.info.MarsFieldInfo;
import com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo;
import com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo;
import com.hifun.soul.gameserver.mars.msg.info.MarsSelfInfo;
import com.hifun.soul.gameserver.mars.template.MarsFieldTemplate;

@Component
public class CGOpenMarsPanelHandler implements
		IMessageHandlerWithType<CGOpenMarsPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_MARS_PANEL;
	}

	@Override
	public void execute(CGOpenMarsPanel message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MARS, true)) {
			return;
		}
		// 如果是第一次开板，先增加战神之巅玩家信息
		if (GameServerAssist.getGlobalMarsManager().getMarsMember(
				human.getHumanGuid()) == null) {
			GameServerAssist.getGlobalMarsManager().addMarsMember(human);
		}
		MarsTemplateManager marsTemplateManager = GameServerAssist
				.getMarsTemplateManager();
		GameConstants gameConstants = GameServerAssist.getGameConstants();
		GCOpenMarsPanel msg = new GCOpenMarsPanel();
		// 战场类别信息
		MarsFieldInfo fieldInfo = new MarsFieldInfo();
		MarsFieldTemplate fieldTemplate = marsTemplateManager
				.getMarsFieldTempalte(human.getLevel());
		if (fieldTemplate == null) {
			return;
		}
		fieldInfo.setFieldName(fieldTemplate.getFieldName());
		fieldInfo.setStartLevel(fieldTemplate.getStartLevel());
		fieldInfo.setEndLevel(fieldTemplate.getEndLevel());
		msg.setFieldInfo(fieldInfo);
		// 房间信息
		List<MarsRoomInfo> roomInfoList = human.getHumanMarsManager()
				.getRoomInfoList();
		msg.setRoomInfos(roomInfoList.toArray(new MarsRoomInfo[0]));
		// 花费信息
		msg.setRefreshCostNum(gameConstants.getRefreshMarsRoomCost());
		msg.setUnlockCostNum(gameConstants.getUnlockMarsRoomCost());
		// 个人信息
		MarsSelfInfo selfInfo = new MarsSelfInfo();
		selfInfo.setBuyMultipleNumCost(marsTemplateManager
				.getBuyMultipleNumCost(human.getMarsBuyMultipleNum() + 1));
		selfInfo.setRemainKillNum(human.getMarsRemainKillNum());
		selfInfo.setTotalKillNum(gameConstants.getDayKillNum());
		selfInfo.setRemainMultipleNum(human.getMarsRemainMultipleNum());
		selfInfo.setTotalMultipleNum(gameConstants.getMultipleNum()
				+ human.getMarsBuyMultipleNum());
		selfInfo.setTodayKillValue(human.getMarsTodayKillValue());
		// 奖励金币 + 威望(次日领取)
		selfInfo.setRewardCoin(human.getMarsTodayKillCoin());
		msg.setSelfInfo(selfInfo);
		// 手下败将信息
		List<MarsPlayerInfo> loserInfoList = human.getHumanMarsManager()
				.getLoserInfoList();
		// 按时间倒序排列
		Collections.sort(loserInfoList, new Comparator<MarsPlayerInfo>() {

			@Override
			public int compare(MarsPlayerInfo o1, MarsPlayerInfo o2) {
				if (o1.getKillTime() > o2.getKillTime()) {
					return -1;
				} else if (o1.getKillTime() < o2.getKillTime()) {
					return 1;
				}
				return 0;
			}
		});
		List<MarsPlayerInfo> showLoserInfoList = new ArrayList<MarsPlayerInfo>();
		for (MarsPlayerInfo loseInfo : loserInfoList) {
			if (showLoserInfoList.size() >= SharedConstants.MARS_LOSER_SHOW_NUM) {
				break;
			}
			showLoserInfoList.add(loseInfo);
		}
		msg.setLoserInfos(showLoserInfoList.toArray(new MarsPlayerInfo[0]));
		msg.setMaxAcceptRewardNum(GameServerAssist.getGameConstants()
				.getDayReceiveRewardNum());
		msg.setRemainAcceptRewardNum(human.getHumanMarsManager()
				.getRemainAcceptRewardNum());
		human.sendMessage(msg);

		// 新手引导
		human.getHumanGuideManager().showGuide(
				GuideType.OPEN_MARS_PANEL.getIndex());
	}
}
