package com.hifun.soul.gameserver.prison.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.converter.PrisonerToInfoConverter;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGMasterGetSlaveList;
import com.hifun.soul.gameserver.prison.msg.GCMasterGetSlaveList;
import com.hifun.soul.gameserver.prison.msg.SlaveInfo;

@Component
public class CGMasterGetSlaveListHandler implements
		IMessageHandlerWithType<CGMasterGetSlaveList> {

	@Override
	public short getMessageType() {
		return MessageType.CG_MASTER_GET_SLAVE_LIST;
	}

	@Override
	public void execute(CGMasterGetSlaveList message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		Prisoner master = manager.getPrisoner(human.getHumanGuid());
		if (master == null) {
			return;
		}
		if (master.getIdentityType() != IdentityType.MASTER.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		List<Prisoner> slaveList = manager.getSlaveList(master);
		GCMasterGetSlaveList msg = new GCMasterGetSlaveList();
		SlaveInfo[] slaveInfos = PrisonerToInfoConverter
				.convertToSlaveInfoArray(slaveList);
		msg.setSlaveInfoList(slaveInfos);
		human.sendMessage(msg);
	}

}
