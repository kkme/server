package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端已经离开了战斗场景通知 
 * 
 * @author SevenSoul
 */
@Component
public class CGAlreadyLeaveBattleScene extends CGMessage{
	
	/** 战斗返回的场景类型 */
	private int clientSceneType;
	
	public CGAlreadyLeaveBattleScene (){
	}
	
	public CGAlreadyLeaveBattleScene (
			int clientSceneType ){
			this.clientSceneType = clientSceneType;
	}
	
	@Override
	protected boolean readImpl() {
		clientSceneType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(clientSceneType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ALREADY_LEAVE_BATTLE_SCENE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ALREADY_LEAVE_BATTLE_SCENE";
	}

	public int getClientSceneType(){
		return clientSceneType;
	}
		
	public void setClientSceneType(int clientSceneType){
		this.clientSceneType = clientSceneType;
	}

	@Override
	public void execute() {
	}
}