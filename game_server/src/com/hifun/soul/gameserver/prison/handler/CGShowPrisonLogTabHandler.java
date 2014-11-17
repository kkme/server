package com.hifun.soul.gameserver.prison.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.PrisonLog;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.converter.PrisonLogToInfoConverter;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGShowPrisonLogTab;
import com.hifun.soul.gameserver.prison.msg.GCShowPrisonLogTab;

/**
 * 展示战俘营日志标签页
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowPrisonLogTabHandler implements
		IMessageHandlerWithType<CGShowPrisonLogTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_PRISON_LOG_TAB;
	}

	@Override
	public void execute(CGShowPrisonLogTab message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		Prisoner prisoner = manager.getPrisoner(human.getHumanGuid());
		if (prisoner == null) {
			return;
		}
		List<PrisonLog> logList = GameServerAssist.getGlobalPrisonManager()
				.getPrisonLogList(human);
		GCShowPrisonLogTab msg = new GCShowPrisonLogTab();
		msg.setPrisonLogInfoList(PrisonLogToInfoConverter.convertToArray(human,
				logList));
		human.sendMessage(msg);
	}

}
