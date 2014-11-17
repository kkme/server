package com.hifun.soul.gameserver.rank.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开排行榜
 * 
 * @author SevenSoul
 */
@Component
public class CGShowRank extends CGMessage{
	
	/** 排行榜类型 */
	private int rankType;
	/** 排行榜页面索引 */
	private int rankPageIndex;
	
	public CGShowRank (){
	}
	
	public CGShowRank (
			int rankType,
			int rankPageIndex ){
			this.rankType = rankType;
			this.rankPageIndex = rankPageIndex;
	}
	
	@Override
	protected boolean readImpl() {
		rankType = readInteger();
		rankPageIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rankType);
		writeInteger(rankPageIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_RANK";
	}

	public int getRankType(){
		return rankType;
	}
		
	public void setRankType(int rankType){
		this.rankType = rankType;
	}

	public int getRankPageIndex(){
		return rankPageIndex;
	}
		
	public void setRankPageIndex(int rankPageIndex){
		this.rankPageIndex = rankPageIndex;
	}

	@Override
	public void execute() {
	}
}