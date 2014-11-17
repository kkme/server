package com.hifun.soul.gameserver.legionmine.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionmine.LegionMine;
import com.hifun.soul.gameserver.legionmine.LegionMineMember;
import com.hifun.soul.gameserver.legionmine.SelfBuf;
import com.hifun.soul.gameserver.legionmine.enums.OperateType;
import com.hifun.soul.gameserver.legionmine.enums.SelfBufType;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.legionmine.msg.CGUseSelfBuf;
import com.hifun.soul.gameserver.legionmine.msg.GCUpdateLegionMineList;
import com.hifun.soul.gameserver.legionmine.msg.GCUseSelfBuf;
import com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo;

@Component
public class CGUseSelfBufHandler implements
		IMessageHandlerWithType<CGUseSelfBuf> {
	@Autowired
	private GlobalLegionMineWarManager globalLegionMineWarManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_USE_SELF_BUF;
	}

	@Override
	public void execute(CGUseSelfBuf message) {
		Human human = message.getPlayer().getHuman();
		LegionMineMember mineMember = globalLegionMineWarManager
				.getJoinLegionMineMember(human.getHumanGuid());
		if (mineMember == null) {
			return;
		}
		SelfBufType bufType = SelfBufType.indexOf(message.getSelfBufType());
		if (bufType == null) {
			return;
		}
		GCUpdateLegionMineList mineListMsg = new GCUpdateLegionMineList();
		SelfBuf buf;
		switch (bufType) {
		case RUN:
			// 驰(行动时间内，可以直接到达自己军团或联盟军团占据的任意一个矿位)
			buf = mineMember.getSelfBuf(bufType);
			if (buf == null) {
				return;
			}
			if (buf.getUsing()) {
				return;
			}
			buf.setUsing(true);
			mineListMsg.setMineInfos(globalLegionMineWarManager
					.generateLegionMineInfos(mineMember, OperateType.COMMON)
					.toArray(new LegionMineInfo[0]));
			break;
		case RAID:
			// 袭(可以攻打非联盟军团的任意一个矿位)
			buf = mineMember.getSelfBuf(bufType);
			if (buf == null) {
				return;
			}
			if (buf.getUsing()) {
				return;
			}
			buf.setUsing(true);
			mineListMsg.setMineInfos(globalLegionMineWarManager
					.generateLegionMineInfos(mineMember, OperateType.COMMON)
					.toArray(new LegionMineInfo[0]));
			break;
		case DISTURB:
			// 扰(增加相邻矿位上敌对军团玩家的采矿CD60秒)
			buf = mineMember.getSelfBuf(bufType);
			if (buf == null) {
				return;
			}
			LegionMine legionMine = globalLegionMineWarManager
					.getLegionMine(mineMember.getMineIndex());
			// 在大本营中不能用干扰
			if (legionMine == null) {
				human.sendWarningMessage(LangConstants.LEGION_MINE_CANT_DISTURB);
				return;
			}
			// 判断周围有没有敌对军团
			boolean canDisturb = false;
			for (String mineIndex : legionMine.getSurroundIndexes().split(",")) {
				List<LegionMineMember> mineMemberList = globalLegionMineWarManager
						.getMembersOnMine(Integer.parseInt(mineIndex));
				if (mineMemberList.size() > 0
						&& mineMemberList.get(0).getLegionType() != mineMember
								.getLegionType()) {
					canDisturb = true;
					break;
				}
			}
			if (!canDisturb) {
				human.sendWarningMessage(LangConstants.LEGION_MINE_DISTURB_NONE);
				return;
			}

			for (String mineIndex : legionMine.getSurroundIndexes().split(",")) {
				List<LegionMineMember> mineMemberList = globalLegionMineWarManager
						.getMembersOnMine(Integer.parseInt(mineIndex));
				for (LegionMineMember surroundMember : mineMemberList) {
					Human surroundHuman = GameServerAssist.getGameWorld()
							.getSceneHumanManager()
							.getHumanByGuid(surroundMember.getHumanId());
					int addCdSeconds = GameServerAssist
							.getLegionMineWarTemplateManager()
							.getConstantsTemplate().getDisturbCdTime();
					long addTime = addCdSeconds * TimeUtils.SECOND;
					if (surroundHuman != null) {
						surroundHuman.getHumanCdManager().addCdTime(
								CdType.LEGION_MINE_WAR_HARVEST, addTime);
						surroundHuman.getHumanCdManager().snapCdQueueInfo(
								CdType.LEGION_MINE_WAR_HARVEST);
						// 给包围的这些玩家发送干扰的提示信息
						surroundHuman.sendImportantMessage(
								LangConstants.LEGION_MINE_BE_DISTURBED,
								addCdSeconds);
					}
				}

			}
			// 扰是直接使用了
			mineListMsg.setMineInfos(globalLegionMineWarManager
					.generateLegionMineInfos(mineMember, OperateType.DISTURB)
					.toArray(new LegionMineInfo[0]));
			mineMember.removeSelfBuf(SelfBufType.DISTURB);
			human.sendSuccessMessage(LangConstants.LEGION_MINE_DISTURB_SUCCESS);
			break;
		}
		human.sendMessage(mineListMsg);
		// 发送个人buf列表
		GCUseSelfBuf useBufMsg = new GCUseSelfBuf();
		useBufMsg.setSelfBufs(mineMember.getSelfBufList().toArray(
				new SelfBuf[0]));
		human.sendMessage(useBufMsg);
	}
}
