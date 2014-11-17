package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 进入征战场景
 * 
 * @author SevenSoul
 */
@Component
public class CGEnterBattleScene extends CGMessage{
	
	/** 地图id */
	private int mapId;
	/** 是否判断有没有领取的奖励 */
	private boolean isNeedCheckReward;
	
	public CGEnterBattleScene (){
	}
	
	public CGEnterBattleScene (
			int mapId,
			boolean isNeedCheckReward ){
			this.mapId = mapId;
			this.isNeedCheckReward = isNeedCheckReward;
	}
	
	@Override
	protected boolean readImpl() {
		mapId = readInteger();
		isNeedCheckReward = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(mapId);
		writeBoolean(isNeedCheckReward);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ENTER_BATTLE_SCENE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENTER_BATTLE_SCENE";
	}

	public int getMapId(){
		return mapId;
	}
		
	public void setMapId(int mapId){
		this.mapId = mapId;
	}

	public boolean getIsNeedCheckReward(){
		return isNeedCheckReward;
	}
		
	public void setIsNeedCheckReward(boolean isNeedCheckReward){
		this.isNeedCheckReward = isNeedCheckReward;
	}

	@Override
	public void execute() {
	}
}