package com.hifun.soul.gameserver.rank.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取排行奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetRankReward extends CGMessage{
	
	/** 排行榜类型 */
	private int rankType;
	
	public CGGetRankReward (){
	}
	
	public CGGetRankReward (
			int rankType ){
			this.rankType = rankType;
	}
	
	@Override
	protected boolean readImpl() {
		rankType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rankType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_RANK_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_RANK_REWARD";
	}

	public int getRankType(){
		return rankType;
	}
		
	public void setRankType(int rankType){
		this.rankType = rankType;
	}

	@Override
	public void execute() {
	}
}