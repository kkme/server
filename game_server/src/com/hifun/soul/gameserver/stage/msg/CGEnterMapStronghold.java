package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求进入指定的地图据点
 * 
 * @author SevenSoul
 */
@Component
public class CGEnterMapStronghold extends CGMessage{
	
	/** 地图据点id */
	private int strongholdId;
	
	public CGEnterMapStronghold (){
	}
	
	public CGEnterMapStronghold (
			int strongholdId ){
			this.strongholdId = strongholdId;
	}
	
	@Override
	protected boolean readImpl() {
		strongholdId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(strongholdId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ENTER_MAP_STRONGHOLD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENTER_MAP_STRONGHOLD";
	}

	public int getStrongholdId(){
		return strongholdId;
	}
		
	public void setStrongholdId(int strongholdId){
		this.strongholdId = strongholdId;
	}

	@Override
	public void execute() {
	}
}