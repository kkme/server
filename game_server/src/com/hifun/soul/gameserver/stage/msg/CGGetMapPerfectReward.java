package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取地图完美通关奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetMapPerfectReward extends CGMessage{
	
	/** 地图id */
	private int mapId;
	
	public CGGetMapPerfectReward (){
	}
	
	public CGGetMapPerfectReward (
			int mapId ){
			this.mapId = mapId;
	}
	
	@Override
	protected boolean readImpl() {
		mapId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(mapId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_MAP_PERFECT_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_MAP_PERFECT_REWARD";
	}

	public int getMapId(){
		return mapId;
	}
		
	public void setMapId(int mapId){
		this.mapId = mapId;
	}

	@Override
	public void execute() {
	}
}