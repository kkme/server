package com.hifun.soul.gameserver.sprite.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sprite.msg.CGBuySpriteCell;

/**
 * 请求购买背包格子;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGBuySpriteCellHandler implements
		IMessageHandlerWithType<CGBuySpriteCell> {

	@Override
	public short getMessageType() {
		return MessageType.CG_BUY_SPRITE_CELL;
	}

	@Override
	public void execute(CGBuySpriteCell message) {
		// 购买精灵背包格子
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
		// 购买格子
		human.getHumanSpriteManager().buySpriteCell(message.getOpenIndex());
	}

}
