package com.hifun.soul.gameserver.recharge.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开充值活动面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenRechargeActivityPanel extends GCMessage{
	
	/** 充值活动类型信息 */
	private int[] rechargeActivityTypes;

	public GCOpenRechargeActivityPanel (){
	}
	
	public GCOpenRechargeActivityPanel (
			int[] rechargeActivityTypes ){
			this.rechargeActivityTypes = rechargeActivityTypes;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		rechargeActivityTypes = new int[count];
		for(int i=0; i<count; i++){
			rechargeActivityTypes[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(rechargeActivityTypes.length);
	for(int i=0; i<rechargeActivityTypes.length; i++){
	Integer objrechargeActivityTypes = rechargeActivityTypes[i];
			writeInteger(objrechargeActivityTypes);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_RECHARGE_ACTIVITY_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_RECHARGE_ACTIVITY_PANEL";
	}

	public int[] getRechargeActivityTypes(){
		return rechargeActivityTypes;
	}

	public void setRechargeActivityTypes(int[] rechargeActivityTypes){
		this.rechargeActivityTypes = rechargeActivityTypes;
	}	
}