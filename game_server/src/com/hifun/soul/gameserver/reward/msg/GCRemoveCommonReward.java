package com.hifun.soul.gameserver.reward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 从列表中移除奖励
 *
 * @author SevenSoul
 */
@Component
public class GCRemoveCommonReward extends GCMessage{
	
	/** 奖励id */
	private int id;

	public GCRemoveCommonReward (){
	}
	
	public GCRemoveCommonReward (
			int id ){
			this.id = id;
	}

	@Override
	protected boolean readImpl() {
		id = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(id);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REMOVE_COMMON_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REMOVE_COMMON_REWARD";
	}

	public int getId(){
		return id;
	}
		
	public void setId(int id){
		this.id = id;
	}
}