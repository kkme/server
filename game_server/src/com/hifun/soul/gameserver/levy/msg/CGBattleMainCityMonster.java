package com.hifun.soul.gameserver.levy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 攻打主城怪物
 * 
 * @author SevenSoul
 */
@Component
public class CGBattleMainCityMonster extends CGMessage{
	
	
	public CGBattleMainCityMonster (){
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
		return MessageType.CG_BATTLE_MAIN_CITY_MONSTER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BATTLE_MAIN_CITY_MONSTER";
	}

	@Override
	public void execute() {
	}
}