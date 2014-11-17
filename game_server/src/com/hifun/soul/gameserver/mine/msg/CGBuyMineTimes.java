package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 购买矿坑开采权次数
 * 
 * @author SevenSoul
 */
@Component
public class CGBuyMineTimes extends CGMessage{
	
	
	public CGBuyMineTimes (){
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
		return MessageType.CG_BUY_MINE_TIMES;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BUY_MINE_TIMES";
	}

	@Override
	public void execute() {
	}
}