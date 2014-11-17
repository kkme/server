package com.hifun.soul.gameserver.manage.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bulletin.manager.BulletinManager;
import com.hifun.soul.gameserver.manage.msg.GMBulletinRemove;
import com.hifun.soul.gameserver.manage.msg.MGBulletinRemove;

@Component
public class MGBulletinRemoveHandler implements IMessageHandlerWithType<MGBulletinRemove> {
	@Autowired
	private BulletinManager bulletinManager;
	@Override
	public short getMessageType() {		
		return MessageType.MG_BULLETIN_REMOVE;
	}

	@Override
	public void execute(MGBulletinRemove message) {
		bulletinManager.removeBulletin(message.getBulletinId());
		GMBulletinRemove gmBulletinRemoveMsg = new GMBulletinRemove();
		gmBulletinRemoveMsg.setResult(true);
		gmBulletinRemoveMsg.setContextId(message.getContextId());
		message.getSession().write(gmBulletinRemoveMsg);		
	}

}
