package com.hifun.soul.gameserver.cd.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.cd.msg.CGCdSpeedUp;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGCdSpeedUpHandler implements IMessageHandlerWithType<CGCdSpeedUp> {

	@Override
	public short getMessageType() {
		return MessageType.CG_CD_SPEED_UP;
	}

	@Override
	public void execute(CGCdSpeedUp message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		CdType cdType = CdType.indexOf(message.getCdType());
		if(cdType == null){
			return;
		}
		
		HumanCdManager manager = human.getHumanCdManager();
		// cd加速
		manager.snapCdSpeedUpInfo(cdType);
		
	}

	
}
