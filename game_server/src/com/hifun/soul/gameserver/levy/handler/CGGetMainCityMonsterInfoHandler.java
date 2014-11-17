package com.hifun.soul.gameserver.levy.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.levy.msg.CGGetMainCityMonsterInfo;

@Component
public class CGGetMainCityMonsterInfoHandler implements
		IMessageHandlerWithType<CGGetMainCityMonsterInfo> {

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_MAIN_CITY_MONSTER_INFO;
	}

	@Override
	public void execute(CGGetMainCityMonsterInfo message) {
		Human human = message.getPlayer().getHuman();
		human.getLevyManager().sendMonsterInfo();
	}

}
