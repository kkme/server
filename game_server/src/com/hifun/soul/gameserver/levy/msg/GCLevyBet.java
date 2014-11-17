package com.hifun.soul.gameserver.levy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应押注
 *
 * @author SevenSoul
 */
@Component
public class GCLevyBet extends GCMessage{
	
	/** 骰子点数 */
	private int[] betPoints;
	/** 押注结果 */
	private int result;
	/** 剩余押注次数 */
	private int remainBetNum;
	/** 剩余必胜次数 */
	private int remainCertainWinNum;
	/** 必胜消费魔晶 */
	private int certainWinCost;
	/** 税收加成 */
	private int levyExtraRate;

	public GCLevyBet (){
	}
	
	public GCLevyBet (
			int[] betPoints,
			int result,
			int remainBetNum,
			int remainCertainWinNum,
			int certainWinCost,
			int levyExtraRate ){
			this.betPoints = betPoints;
			this.result = result;
			this.remainBetNum = remainBetNum;
			this.remainCertainWinNum = remainCertainWinNum;
			this.certainWinCost = certainWinCost;
			this.levyExtraRate = levyExtraRate;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		betPoints = new int[count];
		for(int i=0; i<count; i++){
			betPoints[i] = readInteger();
		}
		result = readInteger();
		remainBetNum = readInteger();
		remainCertainWinNum = readInteger();
		certainWinCost = readInteger();
		levyExtraRate = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(betPoints.length);
	for(int i=0; i<betPoints.length; i++){
	Integer objbetPoints = betPoints[i];
			writeInteger(objbetPoints);
}
		writeInteger(result);
		writeInteger(remainBetNum);
		writeInteger(remainCertainWinNum);
		writeInteger(certainWinCost);
		writeInteger(levyExtraRate);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LEVY_BET;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LEVY_BET";
	}

	public int[] getBetPoints(){
		return betPoints;
	}

	public void setBetPoints(int[] betPoints){
		this.betPoints = betPoints;
	}	

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}

	public int getRemainBetNum(){
		return remainBetNum;
	}
		
	public void setRemainBetNum(int remainBetNum){
		this.remainBetNum = remainBetNum;
	}

	public int getRemainCertainWinNum(){
		return remainCertainWinNum;
	}
		
	public void setRemainCertainWinNum(int remainCertainWinNum){
		this.remainCertainWinNum = remainCertainWinNum;
	}

	public int getCertainWinCost(){
		return certainWinCost;
	}
		
	public void setCertainWinCost(int certainWinCost){
		this.certainWinCost = certainWinCost;
	}

	public int getLevyExtraRate(){
		return levyExtraRate;
	}
		
	public void setLevyExtraRate(int levyExtraRate){
		this.levyExtraRate = levyExtraRate;
	}
}