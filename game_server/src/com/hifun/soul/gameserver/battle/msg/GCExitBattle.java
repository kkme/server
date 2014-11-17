package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务端通知退出战斗
 *
 * @author SevenSoul
 */
@Component
public class GCExitBattle extends GCMessage{
	
	/** 退出战斗后,回到哪个场景 */
	private int gameSceneType;
	/** 战斗胜利者的guid */
	private long winnerGuid;

	public GCExitBattle (){
	}
	
	public GCExitBattle (
			int gameSceneType,
			long winnerGuid ){
			this.gameSceneType = gameSceneType;
			this.winnerGuid = winnerGuid;
	}

	@Override
	protected boolean readImpl() {
		gameSceneType = readInteger();
		winnerGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(gameSceneType);
		writeLong(winnerGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_EXIT_BATTLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EXIT_BATTLE";
	}

	public int getGameSceneType(){
		return gameSceneType;
	}
		
	public void setGameSceneType(int gameSceneType){
		this.gameSceneType = gameSceneType;
	}

	public long getWinnerGuid(){
		return winnerGuid;
	}
		
	public void setWinnerGuid(long winnerGuid){
		this.winnerGuid = winnerGuid;
	}
}