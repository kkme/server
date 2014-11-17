package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新采矿面板消息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateMinePannel extends GCMessage{
	
	/** 剩余开采次数 */
	private int remainMineNum;
	/** 还可购买的开采次数 */
	private int canBuyNum;
	/** 购买开采次数消耗货币类型 */
	private int buyMineTimeCostType;
	/** 购买开采次数消耗货币数量 */
	private int buyMineTimeCostNum;
	/** 重置矿坑消耗货币类型 */
	private int resetMineCostType;
	/** 重置矿坑消耗货币数量 */
	private int resetMineCostNum;

	public GCUpdateMinePannel (){
	}
	
	public GCUpdateMinePannel (
			int remainMineNum,
			int canBuyNum,
			int buyMineTimeCostType,
			int buyMineTimeCostNum,
			int resetMineCostType,
			int resetMineCostNum ){
			this.remainMineNum = remainMineNum;
			this.canBuyNum = canBuyNum;
			this.buyMineTimeCostType = buyMineTimeCostType;
			this.buyMineTimeCostNum = buyMineTimeCostNum;
			this.resetMineCostType = resetMineCostType;
			this.resetMineCostNum = resetMineCostNum;
	}

	@Override
	protected boolean readImpl() {
		remainMineNum = readInteger();
		canBuyNum = readInteger();
		buyMineTimeCostType = readInteger();
		buyMineTimeCostNum = readInteger();
		resetMineCostType = readInteger();
		resetMineCostNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainMineNum);
		writeInteger(canBuyNum);
		writeInteger(buyMineTimeCostType);
		writeInteger(buyMineTimeCostNum);
		writeInteger(resetMineCostType);
		writeInteger(resetMineCostNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_MINE_PANNEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_MINE_PANNEL";
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

	public int getResetMineCostType(){
		return resetMineCostType;
	}
		
	public void setResetMineCostType(int resetMineCostType){
		this.resetMineCostType = resetMineCostType;
	}

	public int getResetMineCostNum(){
		return resetMineCostNum;
	}
		
	public void setResetMineCostNum(int resetMineCostNum){
		this.resetMineCostNum = resetMineCostNum;
	}
}