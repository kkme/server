package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 主人展示压榨奴隶页面
 * 
 * @author SevenSoul
 */
@Component
public class CGShowPressTab extends CGMessage{
	
	/** 要提取的奴隶角色ID */
	private long slaveHumanId;
	
	public CGShowPressTab (){
	}
	
	public CGShowPressTab (
			long slaveHumanId ){
			this.slaveHumanId = slaveHumanId;
	}
	
	@Override
	protected boolean readImpl() {
		slaveHumanId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(slaveHumanId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_PRESS_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_PRESS_TAB";
	}

	public long getSlaveHumanId(){
		return slaveHumanId;
	}
		
	public void setSlaveHumanId(long slaveHumanId){
		this.slaveHumanId = slaveHumanId;
	}

	@Override
	public void execute() {
	}
}