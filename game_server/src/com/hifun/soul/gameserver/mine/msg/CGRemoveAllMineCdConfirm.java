package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 清除所有矿坑cd
 * 
 * @author SevenSoul
 */
@Component
public class CGRemoveAllMineCdConfirm extends CGMessage{
	
	
	public CGRemoveAllMineCdConfirm (){
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
		return MessageType.CG_REMOVE_ALL_MINE_CD_CONFIRM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REMOVE_ALL_MINE_CD_CONFIRM";
	}

	@Override
	public void execute() {
	}
}