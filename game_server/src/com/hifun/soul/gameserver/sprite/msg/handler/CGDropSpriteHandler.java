package com.hifun.soul.gameserver.sprite.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sprite.msg.CGDropSprite;

/**
 * 处理丢弃精灵;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGDropSpriteHandler implements
		IMessageHandlerWithType<CGDropSprite> {

	@Override
	public short getMessageType() {
		return MessageType.CG_DROP_SPRITE;
	}

	@Override
	public void execute(CGDropSprite message) {
		// 丢弃指定的精灵
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
		// 丢弃
		human.getHumanSpriteManager().dropSprite(message.getSpriteUUID());
	}

}
