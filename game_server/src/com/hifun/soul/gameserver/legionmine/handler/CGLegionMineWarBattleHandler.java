package com.hifun.soul.gameserver.legionmine.handler;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.battle.callback.LegionMineWarCallback;
import com.hifun.soul.gameserver.battle.manager.IBattleManager;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionmine.LegionMineMember;
import com.hifun.soul.gameserver.legionmine.SelfBuf;
import com.hifun.soul.gameserver.legionmine.enums.JoinLegionType;
import com.hifun.soul.gameserver.legionmine.enums.SelfBufType;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.legionmine.msg.CGLegionMineWarBattle;
import com.hifun.soul.gameserver.legionmine.msg.GCUseSelfBuf;
import com.hifun.soul.gameserver.player.state.PlayerState;

@Component
public class CGLegionMineWarBattleHandler implements
		IMessageHandlerWithType<CGLegionMineWarBattle> {
	@Autowired
	private GlobalLegionMineWarManager globalLegionMineWarManager;
	@Autowired
	private IBattleManager battleManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_LEGION_MINE_WAR_BATTLE;
	}

	@Override
	public void execute(CGLegionMineWarBattle message) {
		Human human = message.getPlayer().getHuman();
		LegionMineMember mineMember = globalLegionMineWarManager
				.getJoinLegionMineMember(human.getHumanGuid());
		if (mineMember == null) {
			return;
		}
		List<LegionMineMember> battleMembers = globalLegionMineWarManager
				.getMembersOnMine(message.getToIndex());
		if (battleMembers.size() == 0) {
			return;
		}
		// 如果不是敌对军团
		if (battleMembers.get(0).getLegionType() == JoinLegionType.NO_LEGION
				|| battleMembers.get(0).getLegionType() == mineMember
						.getLegionType()) {
			return;
		}
		// 判断cd是否冷却
		HumanCdManager cdManager = human.getHumanCdManager();
		long cd = cdManager.getSpendTime(CdType.LEGION_MINE_WAR_BATTLE,
				GameServerAssist.getLegionMineWarTemplateManager()
						.getConstantsTemplate().getMoveBaseCdTime()
						* TimeUtils.SECOND);
		if (!cdManager.canAddCd(CdType.LEGION_MINE_WAR_BATTLE, cd)) {
			human.sendErrorMessage(LangConstants.CD_LIMIT);
			return;
		}
		// 是否都在战斗, 取出占领时间最早的那个
		Collections.sort(battleMembers, new Comparator<LegionMineMember>() {
			@Override
			public int compare(LegionMineMember o1, LegionMineMember o2) {
				return (int) (o1.getOccupyTime() - o2.getOccupyTime());
			}
		});
		LegionMineMember beAttackedMember = null;
		for (LegionMineMember battleMember : battleMembers) {
			Human battleHuman = GameServerAssist.getGameWorld()
					.getSceneHumanManager()
					.getHumanByGuid(battleMember.getHumanId());
			if (battleHuman != null) {
				if (battleHuman.getPlayer().getState() != PlayerState.BATTLING) {
					beAttackedMember = battleMember;
				}
			}
		}
		if (beAttackedMember == null) {
			human.sendGenericMessage(LangConstants.LEGION_MINE_BATTLING);
			return;
		}
		Human beAttacked = GameServerAssist.getGameWorld()
				.getSceneHumanManager()
				.getHumanByGuid(beAttackedMember.getHumanId());
		if (beAttacked != null) {
			// 如果主动方是使用的袭，移除掉该buff
			if (mineMember.isUsingRaidBuf()) {
				mineMember.removeSelfBuf(SelfBufType.RAID);
				GCUseSelfBuf useBufMsg = new GCUseSelfBuf();
				useBufMsg.setSelfBufs(mineMember.getSelfBufList().toArray(
						new SelfBuf[0]));
				human.sendMessage(useBufMsg);
			}
			// 如果被动方正在使用袭或驰，状态置为取消
			if (beAttackedMember.isUsingRaidBuf()) {
				beAttackedMember.getSelfBuf(SelfBufType.RAID).setUsing(false);
			}
			if (beAttackedMember.isUsingRunBuf()) {
				beAttackedMember.getSelfBuf(SelfBufType.RUN).setUsing(false);
			}
			// 进入战斗
			battleManager.startBattleWithOnlineHuman(human, beAttacked,
					new LegionMineWarCallback(human, mineMember));
		}
	}
}
