package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回开启冥想协助位价格
 *
 * @author SevenSoul
 */
@Component
public class GCOpenAssistPositionPrice extends GCMessage{
	
	/** 自动开启等级 */
	private int levelLimit;
	/** 等级达到开启所需货币类型 */
	private int normalCurrencyType;
	/** 等级达到开启所需货币数量 */
	private int normalcurrencyNum;
	/** 提前开启所需货币类型 */
	private int noLimitCurrencyType;
	/** 提前开启所需货币数量 */
	private int noLimitCurrencyNum;

	public GCOpenAssistPositionPrice (){
	}
	
	public GCOpenAssistPositionPrice (
			int levelLimit,
			int normalCurrencyType,
			int normalcurrencyNum,
			int noLimitCurrencyType,
			int noLimitCurrencyNum ){
			this.levelLimit = levelLimit;
			this.normalCurrencyType = normalCurrencyType;
			this.normalcurrencyNum = normalcurrencyNum;
			this.noLimitCurrencyType = noLimitCurrencyType;
			this.noLimitCurrencyNum = noLimitCurrencyNum;
	}

	@Override
	protected boolean readImpl() {
		levelLimit = readInteger();
		normalCurrencyType = readInteger();
		normalcurrencyNum = readInteger();
		noLimitCurrencyType = readInteger();
		noLimitCurrencyNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(levelLimit);
		writeInteger(normalCurrencyType);
		writeInteger(normalcurrencyNum);
		writeInteger(noLimitCurrencyType);
		writeInteger(noLimitCurrencyNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_ASSIST_POSITION_PRICE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_ASSIST_POSITION_PRICE";
	}

	public int getLevelLimit(){
		return levelLimit;
	}
		
	public void setLevelLimit(int levelLimit){
		this.levelLimit = levelLimit;
	}

	public int getNormalCurrencyType(){
		return normalCurrencyType;
	}
		
	public void setNormalCurrencyType(int normalCurrencyType){
		this.normalCurrencyType = normalCurrencyType;
	}

	public int getNormalcurrencyNum(){
		return normalcurrencyNum;
	}
		
	public void setNormalcurrencyNum(int normalcurrencyNum){
		this.normalcurrencyNum = normalcurrencyNum;
	}

	public int getNoLimitCurrencyType(){
		return noLimitCurrencyType;
	}
		
	public void setNoLimitCurrencyType(int noLimitCurrencyType){
		this.noLimitCurrencyType = noLimitCurrencyType;
	}

	public int getNoLimitCurrencyNum(){
		return noLimitCurrencyNum;
	}
		
	public void setNoLimitCurrencyNum(int noLimitCurrencyNum){
		this.noLimitCurrencyNum = noLimitCurrencyNum;
	}
}