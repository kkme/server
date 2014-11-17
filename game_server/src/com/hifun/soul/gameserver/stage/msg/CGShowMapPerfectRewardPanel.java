package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开地图完美通关奖励的板子
 * 
 * @author SevenSoul
 */
@Component
public class CGShowMapPerfectRewardPanel extends CGMessage{
	
	/** 地图id */
	private int mapId;
	
	public CGShowMapPerfectRewardPanel (){
	}
	
	public CGShowMapPerfectRewardPanel (
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
		return MessageType.CG_SHOW_MAP_PERFECT_REWARD_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_MAP_PERFECT_REWARD_PANEL";
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