package com.hifun.soul.gameserver.battle.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.msg.CGBattleResourceLoaded;
import com.hifun.soul.gameserver.human.Human;

/**
 * 处理战斗资源加载完毕消息;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGBattleResourceLoadedHandler implements
		IMessageHandlerWithType<CGBattleResourceLoaded> {

	@Override
	public short getMessageType() {
		return MessageType.CG_BATTLE_RESOURCE_LOADED;
	}

	@Override
	public void execute(CGBattleResourceLoaded message) {
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
		// 玩家就绪
		battle.unitReady(human);
		// 是否所有玩家都已经准备好？
		if (battle.allUnitReady()) {
			// 开始战斗
			battle.startBattle();
		}
	}

}
