package com.hifun.soul.gameserver.battle.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.msg.CGAnimationOver;
import com.hifun.soul.gameserver.human.Human;

/**
 * 处理客户端动画播放结束的通知;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGAnimationOverHandler implements
		IMessageHandlerWithType<CGAnimationOver> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ANIMATION_OVER;
	}

	@Override
	public void execute(CGAnimationOver message) {
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		IBattleContext context = human.getBattleContext();
		if (context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		battle.onNotifyAnimationOver(human);
	}

}
