package com.hifun.soul.gameserver.prison.handler;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.converter.PrisonerToInfoConverter;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGSlaveShowMasterTab;
import com.hifun.soul.gameserver.prison.msg.GCSlaveShowMasterTab;
import com.hifun.soul.gameserver.prison.msg.MasterInfo;

/**
 * 奴隶展示主人页面
 * 
 * @author yandajun
 * 
 */
@Component
public class CGSlaveShowMasterTabHandler implements
		IMessageHandlerWithType<CGSlaveShowMasterTab> {
	private Logger logger = Loggers.PRISON_LOGGER;

	@Override
	public short getMessageType() {
		return MessageType.CG_SLAVE_SHOW_MASTER_TAB;
	}

	@Override
	public void execute(CGSlaveShowMasterTab message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		Prisoner slave = manager.getPrisoner(human.getHumanGuid());
		if (slave == null) {
			return;
		}
		if (slave.getIdentityType() != IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			logger.error("CGSlaveShowMasterTab error: slaveId = "
					+ slave.getHumanId());
			return;
		}

		Prisoner master = GameServerAssist.getGlobalPrisonManager()
				.getPrisoner(slave.getMasterId());
		if (master == null) {
			return;
		}
		if (master.getIdentityType() != IdentityType.MASTER.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			logger.error("CGSlaveShowMasterTab error: masterId = "
					+ master.getHumanId());
			return;
		}
		MasterInfo masterInfo = PrisonerToInfoConverter.convertToMasterInfo(
				master, slave);
		GCSlaveShowMasterTab msg = new GCSlaveShowMasterTab();
		msg.setRemainInteractNum(slave.getTotalInteractNum()
				- slave.getInteractedNum());
		msg.setRemainRevoltNum(slave.getTotalRevoltNum()
				- slave.getRevoltedNum());
		msg.setRemainSosNum(slave.getTotalForHelpNum()
				- slave.getForHelpedNum());
		msg.setMaster(masterInfo);
		human.sendMessage(msg);
	}
}
