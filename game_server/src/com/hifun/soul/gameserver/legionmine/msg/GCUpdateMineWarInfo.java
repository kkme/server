package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新矿战信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateMineWarInfo extends GCMessage{
	
	/** 所在矿堆索引 */
	private int holdMineIndex;
	/** 所在矿上占领人数 */
	private int occupyNum;
	/** 所在矿上最大占领人数 */
	private int maxOccupyNum;
	/** 可收获占领值 */
	private int occupyValue;
	/** 所在矿富裕度描述 */
	private String richRateDesc;
	/** 所在矿收益比例 */
	private int revenueRate;

	public GCUpdateMineWarInfo (){
	}
	
	public GCUpdateMineWarInfo (
			int holdMineIndex,
			int occupyNum,
			int maxOccupyNum,
			int occupyValue,
			String richRateDesc,
			int revenueRate ){
			this.holdMineIndex = holdMineIndex;
			this.occupyNum = occupyNum;
			this.maxOccupyNum = maxOccupyNum;
			this.occupyValue = occupyValue;
			this.richRateDesc = richRateDesc;
			this.revenueRate = revenueRate;
	}

	@Override
	protected boolean readImpl() {
		holdMineIndex = readInteger();
		occupyNum = readInteger();
		maxOccupyNum = readInteger();
		occupyValue = readInteger();
		richRateDesc = readString();
		revenueRate = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(holdMineIndex);
		writeInteger(occupyNum);
		writeInteger(maxOccupyNum);
		writeInteger(occupyValue);
		writeString(richRateDesc);
		writeInteger(revenueRate);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_MINE_WAR_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_MINE_WAR_INFO";
	}

	public int getHoldMineIndex(){
		return holdMineIndex;
	}
		
	public void setHoldMineIndex(int holdMineIndex){
		this.holdMineIndex = holdMineIndex;
	}

	public int getOccupyNum(){
		return occupyNum;
	}
		
	public void setOccupyNum(int occupyNum){
		this.occupyNum = occupyNum;
	}

	public int getMaxOccupyNum(){
		return maxOccupyNum;
	}
		
	public void setMaxOccupyNum(int maxOccupyNum){
		this.maxOccupyNum = maxOccupyNum;
	}

	public int getOccupyValue(){
		return occupyValue;
	}
		
	public void setOccupyValue(int occupyValue){
		this.occupyValue = occupyValue;
	}

	public String getRichRateDesc(){
		return richRateDesc;
	}
		
	public void setRichRateDesc(String richRateDesc){
		this.richRateDesc = richRateDesc;
	}

	public int getRevenueRate(){
		return revenueRate;
	}
		
	public void setRevenueRate(int revenueRate){
		this.revenueRate = revenueRate;
	}
}