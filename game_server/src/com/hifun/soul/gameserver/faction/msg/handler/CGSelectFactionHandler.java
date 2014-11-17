package com.hifun.soul.gameserver.faction.msg.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.faction.FactionType;
import com.hifun.soul.gameserver.faction.GlobalFactionManager;
import com.hifun.soul.gameserver.faction.msg.CGSelectFaction;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

/**
 * 处理加入阵营;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGSelectFactionHandler implements
		IMessageHandlerWithType<CGSelectFaction> {
	@Autowired
	private GlobalFactionManager factionManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_SELECT_FACTION;
	}

	@Override
	public void execute(CGSelectFaction message) {
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
		// 选择一个阵营;
		if (factionManager.areadyJoinFaction(human)) {
			// FIXME: crazyjohn 提示已经加入过了阵营;
			return;
		}
		FactionType factionType = FactionType.typeOf(message.getFactionType());
		if (factionType == null) {
			return;
		}
		factionManager.selectFaction(human, factionType);
	}

}
