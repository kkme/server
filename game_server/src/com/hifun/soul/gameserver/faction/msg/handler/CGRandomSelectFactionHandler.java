package com.hifun.soul.gameserver.faction.msg.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.faction.GlobalFactionManager;
import com.hifun.soul.gameserver.faction.msg.CGRandomSelectFaction;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

/**
 * 请求随机加入阵营;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGRandomSelectFactionHandler implements
		IMessageHandlerWithType<CGRandomSelectFaction> {
	@Autowired
	private GlobalFactionManager factionManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_RANDOM_SELECT_FACTION;
	}

	@Override
	public void execute(CGRandomSelectFaction message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.FACTION, true)) {
			return;
		}
		// 随机选择一个阵营
		factionManager.randomSelectFaction(human);
	}

}
