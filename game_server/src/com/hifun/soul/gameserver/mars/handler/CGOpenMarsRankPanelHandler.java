package com.hifun.soul.gameserver.mars.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.faction.FactionType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mars.MarsMember;
import com.hifun.soul.gameserver.mars.manager.GlobalMarsManager;
import com.hifun.soul.gameserver.mars.msg.CGOpenMarsRankPanel;
import com.hifun.soul.gameserver.mars.msg.GCOpenMarsRankPanel;
import com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo;

@Component
public class CGOpenMarsRankPanelHandler implements
		IMessageHandlerWithType<CGOpenMarsRankPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_MARS_RANK_PANEL;
	}

	@Override
	public void execute(CGOpenMarsRankPanel message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MARS, true)) {
			return;
		}
		GlobalMarsManager globalMarsManager = GameServerAssist
				.getGlobalMarsManager();
		GCOpenMarsRankPanel msg = new GCOpenMarsRankPanel();
		msg.setBrightTodayKillValue(globalMarsManager
				.getFactionTodayKillValue(FactionType.BRIGHT.getIndex()));
		msg.setDarkTodayKillValue(globalMarsManager
				.getFactionTodayKillValue(FactionType.DARK.getIndex()));
		List<MarsPlayerInfo> brightPlayerInfos = getShowFactionPlayerInfos(FactionType.BRIGHT);
		List<MarsPlayerInfo> darkPlayerInfos = getShowFactionPlayerInfos(FactionType.DARK);
		msg.setMarsBrightPlayerInfos(brightPlayerInfos
				.toArray(new MarsPlayerInfo[0]));
		msg.setMarsDarkPlayerInfos(darkPlayerInfos
				.toArray(new MarsPlayerInfo[0]));
		human.sendMessage(msg);
	}

	private List<MarsPlayerInfo> getShowFactionPlayerInfos(
			FactionType factionType) {
		List<MarsMember> memberList = GameServerAssist.getGlobalMarsManager()
				.getFactionMarsMembers(factionType.getIndex());
		// 按今日杀戮值排序
		Collections.sort(memberList, new Comparator<MarsMember>() {
			@Override
			public int compare(MarsMember o1, MarsMember o2) {
				return o2.getTodayKillValue() - o1.getTodayKillValue();
			}
		});
		// 取出前几个
		List<MarsMember> showMemberList = new ArrayList<MarsMember>();
		for (MarsMember member : memberList) {
			if (showMemberList.size() >= SharedConstants.MARS_FACTION_RANK_SHOW_NUM
					|| member.getTodayKillValue() <= 0) {
				break;
			}
			showMemberList.add(member);
		}
		List<MarsPlayerInfo> marsPlayerInfo = new ArrayList<MarsPlayerInfo>();
		for (MarsMember member : showMemberList) {
			MarsPlayerInfo playerInfo = new MarsPlayerInfo();
			playerInfo.setPlayerId(member.getHumanId());
			playerInfo.setPlayerLevel(member.getHumanLevel());
			playerInfo.setPlayerName(member.getHumanName());
			playerInfo.setOccupationType(member.getOccupation());
			playerInfo.setTodayKillValue(member.getTodayKillValue());
			marsPlayerInfo.add(playerInfo);
		}
		return marsPlayerInfo;
	}
}
