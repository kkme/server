package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 购买矿坑开采权返回结果
 *
 * @author SevenSoul
 */
@Component
public class GCBuyMineTimes extends GCMessage{
	
	/** 剩余开采次数 */
	private int remainMineNum;
	/** 还可购买的开采次数 */
	private int canBuyNum;
	/** 购买开采次数消耗货币类型 */
	private int buyMineTimeCostType;
	/** 购买开采次数消耗货币数量 */
	private int buyMineTimeCostNum;

	public GCBuyMineTimes (){
	}
	
	public GCBuyMineTimes (
			int remainMineNum,
			int canBuyNum,
			int buyMineTimeCostType,
			int buyMineTimeCostNum ){
			this.remainMineNum = remainMineNum;
			this.canBuyNum = canBuyNum;
			this.buyMineTimeCostType = buyMineTimeCostType;
			this.buyMineTimeCostNum = buyMineTimeCostNum;
	}

	@Override
	protected boolean readImpl() {
		remainMineNum = readInteger();
		canBuyNum = readInteger();
		buyMineTimeCostType = readInteger();
		buyMineTimeCostNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainMineNum);
		writeInteger(canBuyNum);
		writeInteger(buyMineTimeCostType);
		writeInteger(buyMineTimeCostNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BUY_MINE_TIMES;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BUY_MINE_TIMES";
	}

	public int getRemainMineNum(){
		return remainMineNum;
	}
		
	public void setRemainMineNum(int remainMineNum){
		this.remainMineNum = remainMineNum;
	}

	public int getCanBuyNum(){
		return canBuyNum;
	}
		
	public void setCanBuyNum(int canBuyNum){
		this.canBuyNum = canBuyNum;
	}

	public int getBuyMineTimeCostType(){
		return buyMineTimeCostType;
	}
		
	public void setBuyMineTimeCostType(int buyMineTimeCostType){
		this.buyMineTimeCostType = buyMineTimeCostType;
	}

	public int getBuyMineTimeCostNum(){
		return buyMineTimeCostNum;
	}
		
	public void setBuyMineTimeCostNum(int buyMineTimeCostNum){
		this.buyMineTimeCostNum = buyMineTimeCostNum;
	}
}