package com.hifun.soul.gameserver.onlinereward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 在线奖励
 *
 * @author SevenSoul
 */
@Component
public class GCShowOnlineReward extends GCMessage{
	
	/** 秒数 */
	private int seconds;
	/** 奖品 */
	private com.hifun.soul.gameserver.item.assist.SimpleCommonItem[] reward;

	public GCShowOnlineReward (){
	}
	
	public GCShowOnlineReward (
			int seconds,
			com.hifun.soul.gameserver.item.assist.SimpleCommonItem[] reward ){
			this.seconds = seconds;
			this.reward = reward;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		seconds = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		reward = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.item.assist.SimpleCommonItem objreward = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem();
			reward[i] = objreward;
					objreward.setUUID(readString());
							objreward.setItemId(readInteger());
							objreward.setName(readString());
							objreward.setDesc(readString());
							objreward.setIcon(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(seconds);
	writeShort(reward.length);
	for(int i=0; i<reward.length; i++){
	com.hifun.soul.gameserver.item.assist.SimpleCommonItem objreward = reward[i];
				writeString(objreward.getUUID());
				writeInteger(objreward.getItemId());
				writeString(objreward.getName());
				writeString(objreward.getDesc());
				writeInteger(objreward.getIcon());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_ONLINE_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_ONLINE_REWARD";
	}

	public int getSeconds(){
		return seconds;
	}
		
	public void setSeconds(int seconds){
		this.seconds = seconds;
	}

	public com.hifun.soul.gameserver.item.assist.SimpleCommonItem[] getReward(){
		return reward;
	}

	public void setReward(com.hifun.soul.gameserver.item.assist.SimpleCommonItem[] reward){
		this.reward = reward;
	}	
}