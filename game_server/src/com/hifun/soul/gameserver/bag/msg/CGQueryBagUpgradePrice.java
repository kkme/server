package com.hifun.soul.gameserver.bag.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端查询升级背包价格
 * 
 * @author SevenSoul
 */
@Component
public class CGQueryBagUpgradePrice extends CGMessage{
	
	/** 背包类型 */
	private short bagType;
	/** 背包提升等级 */
	private int upgradeLevel;
	
	public CGQueryBagUpgradePrice (){
	}
	
	public CGQueryBagUpgradePrice (
			short bagType,
			int upgradeLevel ){
			this.bagType = bagType;
			this.upgradeLevel = upgradeLevel;
	}
	
	@Override
	protected boolean readImpl() {
		bagType = readShort();
		upgradeLevel = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bagType);
		writeInteger(upgradeLevel);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_QUERY_BAG_UPGRADE_PRICE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_QUERY_BAG_UPGRADE_PRICE";
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

	@Override
	public void execute() {
	}
}