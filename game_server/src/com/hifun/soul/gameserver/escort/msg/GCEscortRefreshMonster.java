package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应刷新押运怪物
 *
 * @author SevenSoul
 */
@Component
public class GCEscortRefreshMonster extends GCMessage{
	
	/** 押运怪物类型 */
	private int defaultMonsterType;
	/** 刷新怪物消费 */
	private int refreshMonsterCost;

	public GCEscortRefreshMonster (){
	}
	
	public GCEscortRefreshMonster (
			int defaultMonsterType,
			int refreshMonsterCost ){
			this.defaultMonsterType = defaultMonsterType;
			this.refreshMonsterCost = refreshMonsterCost;
	}

	@Override
	protected boolean readImpl() {
		defaultMonsterType = readInteger();
		refreshMonsterCost = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(defaultMonsterType);
		writeInteger(refreshMonsterCost);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ESCORT_REFRESH_MONSTER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ESCORT_REFRESH_MONSTER";
	}

	public int getDefaultMonsterType(){
		return defaultMonsterType;
	}
		
	public void setDefaultMonsterType(int defaultMonsterType){
		this.defaultMonsterType = defaultMonsterType;
	}

	public int getRefreshMonsterCost(){
		return refreshMonsterCost;
	}
		
	public void setRefreshMonsterCost(int refreshMonsterCost){
		this.refreshMonsterCost = refreshMonsterCost;
	}
}