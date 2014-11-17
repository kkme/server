package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应恢复体力值
 *
 * @author SevenSoul
 */
@Component
public class GCRecoverEnergy extends GCMessage{
	
	/** 剩余次数 */
	private int remainRecoverNum;

	public GCRecoverEnergy (){
	}
	
	public GCRecoverEnergy (
			int remainRecoverNum ){
			this.remainRecoverNum = remainRecoverNum;
	}

	@Override
	protected boolean readImpl() {
		remainRecoverNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainRecoverNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RECOVER_ENERGY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RECOVER_ENERGY";
	}

	public int getRemainRecoverNum(){
		return remainRecoverNum;
	}
		
	public void setRemainRecoverNum(int remainRecoverNum){
		this.remainRecoverNum = remainRecoverNum;
	}
}