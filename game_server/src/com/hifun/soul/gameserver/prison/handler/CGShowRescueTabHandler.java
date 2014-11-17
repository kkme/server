package com.hifun.soul.gameserver.prison.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.PrisonResultType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.converter.PrisonerToInfoConverter;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGShowRescueTab;
import com.hifun.soul.gameserver.prison.msg.GCShowRescueTab;
import com.hifun.soul.gameserver.prison.msg.PrisonerInfo;

/**
 * 展示解救标签页
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowRescueTabHandler implements
		IMessageHandlerWithType<CGShowRescueTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_RESCUE_TAB;
	}

	@Override
	public void execute(CGShowRescueTab message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager prisonManager = GameServerAssist
				.getGlobalPrisonManager();
		Prisoner arrestor = prisonManager.getPrisoner(human.getHumanGuid());
		if (arrestor == null) {
			return;
		}
		if (arrestor.getIdentityType() == IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		GCShowRescueTab msg = new GCShowRescueTab();
		// 军团内需解救的人员
		if (GameServerAssist.getGlobalLegionManager().getLegion(
				human.getHumanGuid()) == null) {
			msg.setResult(PrisonResultType.NOT_JOINED_LEGION.getIndex());
			msg.setToRescueList(new PrisonerInfo[0]);
			human.sendMessage(msg);
			return;
		}
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				human.getHumanGuid());
		List<Prisoner> slaveList = prisonManager.getLegionSlaveList(legion);
		if (slaveList.size() == 0) {
			msg.setResult(PrisonResultType.NO_MEMBER_ARRESTED.getIndex());
			msg.setToRescueList(new PrisonerInfo[0]);
			human.sendMessage(msg);
			return;
		}
		msg.setResult(PrisonResultType.SUCCESS.getIndex());
		msg.setToRescueList(PrisonerToInfoConverter.convertToArray(slaveList));
		human.sendMessage(msg);
	}
}
