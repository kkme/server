package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.battle.EscortRobPVPCallBack;
import com.hifun.soul.gameserver.escort.info.EscortInfo;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.escort.msg.CGRobEscrot;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 拦截押运
 * 
 * @author yandajun
 * 
 */
@Component
public class CGRobEscrotHandler implements IMessageHandlerWithType<CGRobEscrot> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ROB_ESCROT;
	}

	@Override
	public void execute(CGRobEscrot message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		GlobalEscortManager globalEscortManager = GameServerAssist
				.getGlobalEscortManager();
		EscortInfo escortInfo = globalEscortManager.getEscortInfo(message
				.getEscortId());
		if (escortInfo == null) {
			return;
		}
		// 是否是自己运送的货物
		if (escortInfo.getOwnerId() == human.getHumanGuid()) {
			human.sendErrorMessage(LangConstants.ESCORT_CAN_NOT_ROB_SELF);
			return;
		}
		// 是否是自己协助运送的货物
		if (escortInfo.getHelperId() == human.getHumanGuid()) {
			human.sendErrorMessage(LangConstants.ESCORT_CAN_NOT_ROB_HELP);
			return;
		}
		// 是否在CD中
		long baseTime = GameServerAssist.getEscortTemplateManager()
				.getConstantsTemplate().getRobCdTime()
				* TimeUtils.MIN;
		long spendTime = human.getHumanCdManager().getSpendTime(
				CdType.ESCORT_ROB, baseTime);
		if (!human.getHumanCdManager().canAddCd(CdType.ESCORT_ROB, spendTime)) {
			human.sendErrorMessage(LangConstants.CD_LIMIT);
			return;
		}
		// 是否有拦截次数
		if (human.getEscortRobRemainNum() <= 0) {
			human.sendErrorMessage(LangConstants.ESCORT_NO_ROB_NUM);
			return;
		}
		// 被拦截者是否已达最大拦截次数
		if (escortInfo.getRemainBeRobbedNum() <= 0) {
			human.sendErrorMessage(LangConstants.ESCORT_NO_BE_ROBBED_NUM);
			return;
		}
		// 如果有协助护送好友，跟其好友战斗
		long beChallengedId = message.getEscortId();
		if (escortInfo.getHelperId() > 0) {
			beChallengedId = escortInfo.getHelperId();
		}
		// 是否正在被拦截
		if (escortInfo.isRobing()) {
			human.sendErrorMessage(LangConstants.ESCORT_BE_ROBING);
			return;
		}
		// 进入战斗
		GameServerAssist.getBattleManager().pvpBattleEnter(
				human,
				beChallengedId,
				new EscortRobPVPCallBack(human, message.getEscortId(),
						escortInfo.getHelperId() > 0));

	}
}
