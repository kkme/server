package com.hifun.soul.gameserver.sign.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求星座列表 
 * 
 * @author SevenSoul
 */
@Component
public class CGGetSignList extends CGMessage{
	
	/** 星图id */
	private int starMapId;
	
	public CGGetSignList (){
	}
	
	public CGGetSignList (
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
		return MessageType.CG_GET_SIGN_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_SIGN_LIST";
	}

	public int getStarMapId(){
		return starMapId;
	}
		
	public void setStarMapId(int starMapId){
		this.starMapId = starMapId;
	}

	@Override
	public void execute() {
	}
}