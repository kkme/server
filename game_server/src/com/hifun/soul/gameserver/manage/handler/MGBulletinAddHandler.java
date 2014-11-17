package com.hifun.soul.gameserver.manage.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bulletin.Bulletin;
import com.hifun.soul.gameserver.bulletin.BulletinType;
import com.hifun.soul.gameserver.bulletin.DelayBulletin;
import com.hifun.soul.gameserver.bulletin.RegularBulletin;
import com.hifun.soul.gameserver.bulletin.manager.BulletinManager;
import com.hifun.soul.gameserver.manage.msg.GMBulletinAdd;
import com.hifun.soul.gameserver.manage.msg.MGBulletinAdd;

@Component
public class MGBulletinAddHandler implements IMessageHandlerWithType<MGBulletinAdd> {
	
	@Autowired
	private BulletinManager bulletinManager;
	@Override
	public short getMessageType() {		
		return MessageType.MG_BULLETIN_ADD;
	}

	@Override
	public void execute(MGBulletinAdd message) {
		com.hifun.soul.proto.services.Services.Bulletin bulletin = message.getBulletin();
		Bulletin bulletin_model = null;
		BulletinType bulletinType = BulletinType.indexOf(bulletin.getBulletinType());
		switch(bulletinType){
		case DELAY_BULLETIN:
			bulletin_model = new DelayBulletin(bulletin);
			break;		
		case REGULAR_BULLETIN:
			bulletin_model = new RegularBulletin(bulletin);
			break;
		}
		bulletinManager.addBulletin(bulletin_model);
		GMBulletinAdd gmBulletinAddMsg = new GMBulletinAdd();
		gmBulletinAddMsg.setResult(true);
		gmBulletinAddMsg.setContextId(message.getContextId());
		message.getSession().write(gmBulletinAddMsg);
	}

}
