package com.hifun.soul.gameserver.sprite.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sprite.msg.CGEquipSprite;

/**
 * 请求装备精灵;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGEquipSpriteHandler implements
		IMessageHandlerWithType<CGEquipSprite> {

	@Override
	public short getMessageType() {
		return MessageType.CG_EQUIP_SPRITE;
	}

	@Override
	public void execute(CGEquipSprite message) {
		// 装备指定的精灵
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
				GameFuncType.SPRITE, true)) {
			return;
		}
		// 装备精灵
		human.getHumanSpriteManager().equipSprite(message.getSpriteUUID());
	}

}
