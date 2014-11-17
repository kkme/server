package com.hifun.soul.gameserver.human.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.HumanInfoHelper;
import com.hifun.soul.gameserver.human.msg.CGCharacterShowInfo;


/**
 * 显示角色信息请求处理程序
 * @author magicstone
 *
 */
@Component
public class CGCharacterShowInfoHandler implements IMessageHandlerWithType<CGCharacterShowInfo> {

	@Override
	public short getMessageType() {		
		return MessageType.CG_CHARACTER_SHOW_INFO;
	}

	@Override
	public void execute(CGCharacterShowInfo message) {
		if(message.getPlayer() == null)
		{
			return;
		}
		final Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		final long showCharacterId = message.getHumanGuid();
		HumanInfoHelper.sendHumanBaseInfoMessage(human,showCharacterId);
	}

}
