package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知网络环境太差, 请重试 
 *
 * @author SevenSoul
 */
@Component
public class GCBadNetEnvironment extends GCMessage{
	
	/** 网络环境差的单元GUID */
	private long badGuid;

	public GCBadNetEnvironment (){
	}
	
	public GCBadNetEnvironment (
			long badGuid ){
			this.badGuid = badGuid;
	}

	@Override
	protected boolean readImpl() {
		badGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(badGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAD_NET_ENVIRONMENT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAD_NET_ENVIRONMENT";
	}

	public long getBadGuid(){
		return badGuid;
	}
		
	public void setBadGuid(long badGuid){
		this.badGuid = badGuid;
	}
}