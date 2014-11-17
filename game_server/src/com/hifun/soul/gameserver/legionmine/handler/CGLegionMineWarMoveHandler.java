package com.hifun.soul.gameserver.legionmine.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionmine.LegionMine;
import com.hifun.soul.gameserver.legionmine.LegionMineMember;
import com.hifun.soul.gameserver.legionmine.SelfBuf;
import com.hifun.soul.gameserver.legionmine.enums.JoinLegionType;
import com.hifun.soul.gameserver.legionmine.enums.OperateType;
import com.hifun.soul.gameserver.legionmine.enums.SelfBufType;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.legionmine.msg.CGLegionMineWarMove;
import com.hifun.soul.gameserver.legionmine.msg.GCUpdateLegionMineList;
import com.hifun.soul.gameserver.legionmine.msg.GCUseSelfBuf;
import com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo;

@Component
public class CGLegionMineWarMoveHandler implements
		IMessageHandlerWithType<CGLegionMineWarMove> {
	@Autowired
	private GlobalLegionMineWarManager globalLegionMineWarManager;
	private Logger logger = Loggers.LEGION_MINE_LOGGER;

	@Override
	public short getMessageType() {
		return MessageType.CG_LEGION_MINE_WAR_MOVE;
	}

	@Override
	public void execute(CGLegionMineWarMove message) {
		Human human = message.getPlayer().getHuman();
		LegionMineMember mineMember = globalLegionMineWarManager
				.getJoinLegionMineMember(human.getHumanGuid());
		if (mineMember == null) {
			return;
		}
		// 如果是敌对军团不能移动
		LegionMine toMine = globalLegionMineWarManager.getLegionMine(message
				.getToIndex());
		if (toMine == null) {
			return;
		}
		JoinLegionType toLegionType = toMine.getOccupyLegionType();
		logger.info("start move:selfLegionType:" + mineMember.getLegionType()
				+ ", toLegitonType:" + toLegionType);
		if (toLegionType != JoinLegionType.NO_LEGION
				&& toLegionType != mineMember.getLegionType()) {
			return;
		}
		// 判断cd是否冷却
		HumanCdManager cdManager = human.getHumanCdManager();
		long cd = cdManager.getSpendTime(CdType.LEGION_MINE_WAR_MOVE,
				GameServerAssist.getLegionMineWarTemplateManager()
						.getConstantsTemplate().getMoveBaseCdTime()
						* TimeUtils.SECOND);
		if (!cdManager.canAddCd(CdType.LEGION_MINE_WAR_MOVE, cd)) {
			human.sendErrorMessage(LangConstants.CD_LIMIT);
			return;
		}
		// 移动
		globalLegionMineWarManager.move(mineMember, message.getToIndex());
		// 如果使用的是驰，移除掉该buff
		if (mineMember.isUsingRunBuf()) {
			mineMember.removeSelfBuf(SelfBufType.RUN);
			GCUseSelfBuf useBufMsg = new GCUseSelfBuf();
			useBufMsg.setSelfBufs(mineMember.getSelfBufList().toArray(
					new SelfBuf[0]));
			human.sendMessage(useBufMsg);
		}

		GCUpdateLegionMineList mineListMsg = new GCUpdateLegionMineList();
		mineListMsg.setMineInfos(globalLegionMineWarManager
				.generateLegionMineInfos(mineMember, OperateType.COMMON)
				.toArray(new LegionMineInfo[0]));
		human.sendMessage(mineListMsg);

		// 添加CD
		cdManager.addCd(CdType.LEGION_MINE_WAR_MOVE, cd);
		cdManager.snapCdQueueInfo(CdType.LEGION_MINE_WAR_MOVE);
	}

}
