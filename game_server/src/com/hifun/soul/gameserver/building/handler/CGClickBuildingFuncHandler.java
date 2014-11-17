package com.hifun.soul.gameserver.building.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.building.msg.CGClickBuildingFunc;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGClickBuildingFuncHandler implements
		IMessageHandlerWithType<CGClickBuildingFunc> {

	@Override
	public short getMessageType() {
		return MessageType.CG_CLICK_BUILDING_FUNC;
	}

	@Override
	public void execute(CGClickBuildingFunc message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}

		human.getHumanBuildingManager()
			.onClickBuildingFunc(message.getUUID(), message.getBuildingType(), message.getFuncId());
	}

}
