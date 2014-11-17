package com.hifun.soul.gameserver.levy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求攻城怪物信息
 * 
 * @author SevenSoul
 */
@Component
public class CGGetMainCityMonsterInfo extends CGMessage{
	
	
	public CGGetMainCityMonsterInfo (){
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
		return MessageType.CG_GET_MAIN_CITY_MONSTER_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_MAIN_CITY_MONSTER_INFO";
	}

	@Override
	public void execute() {
	}
}