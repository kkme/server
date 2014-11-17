package com.hifun.soul.gameserver.sign.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回星座列表 
 *
 * @author SevenSoul
 */
@Component
public class GCGetSignList extends GCMessage{
	
	/** 星图id */
	private int starMapId;
	/** 星座列表 */
	private com.hifun.soul.gameserver.sprite.model.SpriteSignInfo[] signList;

	public GCGetSignList (){
	}
	
	public GCGetSignList (
			int starMapId,
			com.hifun.soul.gameserver.sprite.model.SpriteSignInfo[] signList ){
			this.starMapId = starMapId;
			this.signList = signList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		starMapId = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		signList = new com.hifun.soul.gameserver.sprite.model.SpriteSignInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.sprite.model.SpriteSignInfo objsignList = new com.hifun.soul.gameserver.sprite.model.SpriteSignInfo();
			signList[i] = objsignList;
					objsignList.setStarMapId(readInteger());
							objsignList.setSignId(readInteger());
							objsignList.setName(readString());
							objsignList.setCostStarSoul(readInteger());
							objsignList.setPropId(readInteger());
							objsignList.setPropValue(readInteger());
							objsignList.setAmendType(readInteger());
							objsignList.setLight(readBoolean());
							objsignList.setX(readInteger());
							objsignList.setY(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(starMapId);
	writeShort(signList.length);
	for(int i=0; i<signList.length; i++){
	com.hifun.soul.gameserver.sprite.model.SpriteSignInfo objsignList = signList[i];
				writeInteger(objsignList.getStarMapId());
				writeInteger(objsignList.getSignId());
				writeString(objsignList.getName());
				writeInteger(objsignList.getCostStarSoul());
				writeInteger(objsignList.getPropId());
				writeInteger(objsignList.getPropValue());
				writeInteger(objsignList.getAmendType());
				writeBoolean(objsignList.getLight());
				writeInteger(objsignList.getX());
				writeInteger(objsignList.getY());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_SIGN_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_SIGN_LIST";
	}

	public int getStarMapId(){
		return starMapId;
	}
		
	public void setStarMapId(int starMapId){
		this.starMapId = starMapId;
	}

	public com.hifun.soul.gameserver.sprite.model.SpriteSignInfo[] getSignList(){
		return signList;
	}

	public void setSignList(com.hifun.soul.gameserver.sprite.model.SpriteSignInfo[] signList){
		this.signList = signList;
	}	
}