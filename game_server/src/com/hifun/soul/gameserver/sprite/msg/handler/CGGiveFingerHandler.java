package com.hifun.soul.gameserver.sprite.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sprite.FingerType;
import com.hifun.soul.gameserver.sprite.msg.CGGiveFinger;

/**
 * 处理出拳请求;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGGiveFingerHandler implements
		IMessageHandlerWithType<CGGiveFinger> {

	@Override
	public short getMessageType() {
		return MessageType.CG_GIVE_FINGER;
	}

	@Override
	public void execute(CGGiveFinger message) {
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
				GameFuncType.SPRITE_PUB, true)) {
			return;
		}
		// 是否可以出拳
		if (!human.getHumanSpritePubManager().canGiveFinger()) {
			return;
		}
		// 取出出拳类型
		FingerType fingerType = FingerType.typeOf(message.getFingerType());
		if (fingerType == null) {
			return;
		}
		// 开始本回合猜拳
		startFingerGuessRound(human, fingerType);
	}

	/**
	 * 開始猜拳回合;
	 * 
	 * @param human
	 * @param fingerType
	 */
	private void startFingerGuessRound(Human human, FingerType fingerType) {
		human.getHumanSpritePubManager().startFingerGuessRound(fingerType);
	}

}
