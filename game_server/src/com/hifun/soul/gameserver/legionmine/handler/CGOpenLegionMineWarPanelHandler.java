package com.hifun.soul.gameserver.legionmine.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.legionmine.msg.CGOpenLegionMineWarPanel;

@Component
public class CGOpenLegionMineWarPanelHandler implements
		IMessageHandlerWithType<CGOpenLegionMineWarPanel> {
	@Autowired
	private GlobalLegionMineWarManager globalLegionMineWarManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_LEGION_MINE_WAR_PANEL;
	}

	@Override
	public void execute(CGOpenLegionMineWarPanel message) {
		Human human = message.getPlayer().getHuman();
		globalLegionMineWarManager.onEnterWar(human);
	}

}
