package com.hifun.soul.gameserver.sign.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开星图面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenStarMapPanel extends GCMessage{
	
	/** 星图id */
	private int starMapId;
	/** 当前星魂值 */
	private int starSoul;
	/** 星图列表 */
	private com.hifun.soul.gameserver.sprite.model.SpriteStarMapInfo[] starMapList;
	/** 星座列表 */
	private com.hifun.soul.gameserver.sprite.model.SpriteSignInfo[] signList;

	public GCOpenStarMapPanel (){
	}
	
	public GCOpenStarMapPanel (
			int starMapId,
			int starSoul,
			com.hifun.soul.gameserver.sprite.model.SpriteStarMapInfo[] starMapList,
			com.hifun.soul.gameserver.sprite.model.SpriteSignInfo[] signList ){
			this.starMapId = starMapId;
			this.starSoul = starSoul;
			this.starMapList = starMapList;
			this.signList = signList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		starMapId = readInteger();
		starSoul = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		starMapList = new com.hifun.soul.gameserver.sprite.model.SpriteStarMapInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.sprite.model.SpriteStarMapInfo objstarMapList = new com.hifun.soul.gameserver.sprite.model.SpriteStarMapInfo();
			starMapList[i] = objstarMapList;
					objstarMapList.setStarMapId(readInteger());
							objstarMapList.setName(readString());
							objstarMapList.setOpenLevel(readInteger());
							objstarMapList.setActivated(readBoolean());
				}
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
		writeInteger(starSoul);
	writeShort(starMapList.length);
	for(int i=0; i<starMapList.length; i++){
	com.hifun.soul.gameserver.sprite.model.SpriteStarMapInfo objstarMapList = starMapList[i];
				writeInteger(objstarMapList.getStarMapId());
				writeString(objstarMapList.getName());
				writeInteger(objstarMapList.getOpenLevel());
				writeBoolean(objstarMapList.getActivated());
	}
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
		return MessageType.GC_OPEN_STAR_MAP_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_STAR_MAP_PANEL";
	}

	public int getStarMapId(){
		return starMapId;
	}
		
	public void setStarMapId(int starMapId){
		this.starMapId = starMapId;
	}

	public int getStarSoul(){
		return starSoul;
	}
		
	public void setStarSoul(int starSoul){
		this.starSoul = starSoul;
	}

	public com.hifun.soul.gameserver.sprite.model.SpriteStarMapInfo[] getStarMapList(){
		return starMapList;
	}

	public void setStarMapList(com.hifun.soul.gameserver.sprite.model.SpriteStarMapInfo[] starMapList){
		this.starMapList = starMapList;
	}	

	public com.hifun.soul.gameserver.sprite.model.SpriteSignInfo[] getSignList(){
		return signList;
	}

	public void setSignList(com.hifun.soul.gameserver.sprite.model.SpriteSignInfo[] signList){
		this.signList = signList;
	}	
}