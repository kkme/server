package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 开启冥想协助位
 *
 * @author SevenSoul
 */
@Component
public class GCOpenAssistPosition extends GCMessage{
	
	/** 开启的训练位索引 */
	private int index;
	/** 等级限制 */
	private int levelLimit;
	/** 金币限制 */
	private int coinLimit;
	/** 魔晶限制 */
	private int crystalLimit;

	public GCOpenAssistPosition (){
	}
	
	public GCOpenAssistPosition (
			int index,
			int levelLimit,
			int coinLimit,
			int crystalLimit ){
			this.index = index;
			this.levelLimit = levelLimit;
			this.coinLimit = coinLimit;
			this.crystalLimit = crystalLimit;
	}

	@Override
	protected boolean readImpl() {
		index = readInteger();
		levelLimit = readInteger();
		coinLimit = readInteger();
		crystalLimit = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(index);
		writeInteger(levelLimit);
		writeInteger(coinLimit);
		writeInteger(crystalLimit);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_ASSIST_POSITION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_ASSIST_POSITION";
	}

	public int getIndex(){
		return index;
	}
		
	public void setIndex(int index){
		this.index = index;
	}

	public int getLevelLimit(){
		return levelLimit;
	}
		
	public void setLevelLimit(int levelLimit){
		this.levelLimit = levelLimit;
	}

	public int getCoinLimit(){
		return coinLimit;
	}
		
	public void setCoinLimit(int coinLimit){
		this.coinLimit = coinLimit;
	}

	public int getCrystalLimit(){
		return crystalLimit;
	}
		
	public void setCrystalLimit(int crystalLimit){
		this.crystalLimit = crystalLimit;
	}
}