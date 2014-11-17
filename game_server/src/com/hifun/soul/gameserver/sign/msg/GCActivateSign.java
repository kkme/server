package com.hifun.soul.gameserver.sign.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应激活星座 
 *
 * @author SevenSoul
 */
@Component
public class GCActivateSign extends GCMessage{
	
	/** 星座id */
	private int signId;
	/** 剩余星魂 */
	private int remainStarSoul;

	public GCActivateSign (){
	}
	
	public GCActivateSign (
			int signId,
			int remainStarSoul ){
			this.signId = signId;
			this.remainStarSoul = remainStarSoul;
	}

	@Override
	protected boolean readImpl() {
		signId = readInteger();
		remainStarSoul = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(signId);
		writeInteger(remainStarSoul);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ACTIVATE_SIGN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ACTIVATE_SIGN";
	}

	public int getSignId(){
		return signId;
	}
		
	public void setSignId(int signId){
		this.signId = signId;
	}

	public int getRemainStarSoul(){
		return remainStarSoul;
	}
		
	public void setRemainStarSoul(int remainStarSoul){
		this.remainStarSoul = remainStarSoul;
	}
}