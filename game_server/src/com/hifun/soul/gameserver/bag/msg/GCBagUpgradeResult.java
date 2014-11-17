package com.hifun.soul.gameserver.bag.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回背包升级结果
 *
 * @author SevenSoul
 */
@Component
public class GCBagUpgradeResult extends GCMessage{
	
	/** 背包类型 */
	private short bagType;
	/** 背包提升等级，大于0表示升级成功，否则表示升级失败 */
	private int upgradeLevel;
	/** 每一等级所开启的背包格式数 */
	private int levelSize;

	public GCBagUpgradeResult (){
	}
	
	public GCBagUpgradeResult (
			short bagType,
			int upgradeLevel,
			int levelSize ){
			this.bagType = bagType;
			this.upgradeLevel = upgradeLevel;
			this.levelSize = levelSize;
	}

	@Override
	protected boolean readImpl() {
		bagType = readShort();
		upgradeLevel = readInteger();
		levelSize = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bagType);
		writeInteger(upgradeLevel);
		writeInteger(levelSize);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAG_UPGRADE_RESULT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAG_UPGRADE_RESULT";
	}

	public short getBagType(){
		return bagType;
	}
		
	public void setBagType(short bagType){
		this.bagType = bagType;
	}

	public int getUpgradeLevel(){
		return upgradeLevel;
	}
		
	public void setUpgradeLevel(int upgradeLevel){
		this.upgradeLevel = upgradeLevel;
	}

	public int getLevelSize(){
		return levelSize;
	}
		
	public void setLevelSize(int levelSize){
		this.levelSize = levelSize;
	}
}