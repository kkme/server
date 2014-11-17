package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 领取地图完美通关奖励
 *
 * @author SevenSoul
 */
@Component
public class GCGetMapPerfectReward extends GCMessage{
	
	/** 地图id */
	private int mapId;
	/** 地图完美通关奖励领取状态 */
	private int state;

	public GCGetMapPerfectReward (){
	}
	
	public GCGetMapPerfectReward (
			int mapId,
			int state ){
			this.mapId = mapId;
			this.state = state;
	}

	@Override
	protected boolean readImpl() {
		mapId = readInteger();
		state = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(mapId);
		writeInteger(state);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_MAP_PERFECT_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_MAP_PERFECT_REWARD";
	}

	public int getMapId(){
		return mapId;
	}
		
	public void setMapId(int mapId){
		this.mapId = mapId;
	}

	public int getState(){
		return state;
	}
		
	public void setState(int state){
		this.state = state;
	}
}