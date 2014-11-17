package com.hifun.soul.gameserver.warrior.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新对手信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateOpponent extends GCMessage{
	
	/** 对手Id */
	private long opponentId;
	/** 对手名称 */
	private String opponentName;
	/** 对手职业 */
	private int opponentOccupation;
	/** 对手等级 */
	private int opponentLevel;
	/** 对手类型 */
	private int opponentType;
	/** 战胜获得勇者之心数 */
	private int battleWinRewardNum;

	public GCUpdateOpponent (){
	}
	
	public GCUpdateOpponent (
			long opponentId,
			String opponentName,
			int opponentOccupation,
			int opponentLevel,
			int opponentType,
			int battleWinRewardNum ){
			this.opponentId = opponentId;
			this.opponentName = opponentName;
			this.opponentOccupation = opponentOccupation;
			this.opponentLevel = opponentLevel;
			this.opponentType = opponentType;
			this.battleWinRewardNum = battleWinRewardNum;
	}

	@Override
	protected boolean readImpl() {
		opponentId = readLong();
		opponentName = readString();
		opponentOccupation = readInteger();
		opponentLevel = readInteger();
		opponentType = readInteger();
		battleWinRewardNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(opponentId);
		writeString(opponentName);
		writeInteger(opponentOccupation);
		writeInteger(opponentLevel);
		writeInteger(opponentType);
		writeInteger(battleWinRewardNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_OPPONENT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_OPPONENT";
	}

	public long getOpponentId(){
		return opponentId;
	}
		
	public void setOpponentId(long opponentId){
		this.opponentId = opponentId;
	}

	public String getOpponentName(){
		return opponentName;
	}
		
	public void setOpponentName(String opponentName){
		this.opponentName = opponentName;
	}

	public int getOpponentOccupation(){
		return opponentOccupation;
	}
		
	public void setOpponentOccupation(int opponentOccupation){
		this.opponentOccupation = opponentOccupation;
	}

	public int getOpponentLevel(){
		return opponentLevel;
	}
		
	public void setOpponentLevel(int opponentLevel){
		this.opponentLevel = opponentLevel;
	}

	public int getOpponentType(){
		return opponentType;
	}
		
	public void setOpponentType(int opponentType){
		this.opponentType = opponentType;
	}

	public int getBattleWinRewardNum(){
		return battleWinRewardNum;
	}
		
	public void setBattleWinRewardNum(int battleWinRewardNum){
		this.battleWinRewardNum = battleWinRewardNum;
	}
}