package com.hifun.soul.gameserver.cd.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.cd.msg.CGRemoveCd;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGRemoveCdHandler implements IMessageHandlerWithType<CGRemoveCd> {

	@Override
	public short getMessageType() {
		return MessageType.CG_REMOVE_CD;
	}

	@Override
	public void execute(CGRemoveCd message) {
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
		// 清除cd
		manager.removeCd(cdType);
		// 发送cd更新消息
		manager.snapCdQueueInfo(cdType);
	}

}
