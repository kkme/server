package com.hifun.soul.gameserver.legionboss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 用充能攻击
 * 
 * @author SevenSoul
 */
@Component
public class CGChargedLegionBoss extends CGMessage{
	
	
	public CGChargedLegionBoss (){
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
		return MessageType.CG_CHARGED_LEGION_BOSS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CHARGED_LEGION_BOSS";
	}

	@Override
	public void execute() {
	}
}