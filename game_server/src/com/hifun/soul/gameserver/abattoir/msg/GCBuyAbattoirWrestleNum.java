package com.hifun.soul.gameserver.abattoir.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应购买角斗次数
 *
 * @author SevenSoul
 */
@Component
public class GCBuyAbattoirWrestleNum extends GCMessage{
	
	/** 剩余角斗次数 */
	private int remainWrestleNum;
	/** 下次购买次数消费 */
	private int nextBuyNumCost;

	public GCBuyAbattoirWrestleNum (){
	}
	
	public GCBuyAbattoirWrestleNum (
			int remainWrestleNum,
			int nextBuyNumCost ){
			this.remainWrestleNum = remainWrestleNum;
			this.nextBuyNumCost = nextBuyNumCost;
	}

	@Override
	protected boolean readImpl() {
		remainWrestleNum = readInteger();
		nextBuyNumCost = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainWrestleNum);
		writeInteger(nextBuyNumCost);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BUY_ABATTOIR_WRESTLE_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BUY_ABATTOIR_WRESTLE_NUM";
	}

	public int getRemainWrestleNum(){
		return remainWrestleNum;
	}
		
	public void setRemainWrestleNum(int remainWrestleNum){
		this.remainWrestleNum = remainWrestleNum;
	}

	public int getNextBuyNumCost(){
		return nextBuyNumCost;
	}
		
	public void setNextBuyNumCost(int nextBuyNumCost){
		this.nextBuyNumCost = nextBuyNumCost;
	}
}