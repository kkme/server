package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求清除所有矿坑cd需要的花费
 * 
 * @author SevenSoul
 */
@Component
public class CGRemoveAllMineCd extends CGMessage{
	
	
	public CGRemoveAllMineCd (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_REMOVE_ALL_MINE_CD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REMOVE_ALL_MINE_CD";
	}

	@Override
	public void execute() {
	}
}