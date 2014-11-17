package com.hifun.soul.gameserver.sign.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知激活下一张星图 
 *
 * @author SevenSoul
 */
@Component
public class GCActivateNewStarMap extends GCMessage{
	
	/** 星图id */
	private int starMapId;

	public GCActivateNewStarMap (){
	}
	
	public GCActivateNewStarMap (
			int starMapId ){
			this.starMapId = starMapId;
	}

	@Override
	protected boolean readImpl() {
		starMapId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(starMapId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ACTIVATE_NEW_STAR_MAP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ACTIVATE_NEW_STAR_MAP";
	}

	public int getStarMapId(){
		return starMapId;
	}
		
	public void setStarMapId(int starMapId){
		this.starMapId = starMapId;
	}
}