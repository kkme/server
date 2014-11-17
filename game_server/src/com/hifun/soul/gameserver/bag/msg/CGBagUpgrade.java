package com.hifun.soul.gameserver.bag.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端发送背包升级请求
 * 
 * @author SevenSoul
 */
@Component
public class CGBagUpgrade extends CGMessage{
	
	/** 背包类型 */
	private short bagType;
	/** 背包提升等级 */
	private int upgradeLevel;
	
	public CGBagUpgrade (){
	}
	
	public CGBagUpgrade (
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
		return MessageType.CG_BAG_UPGRADE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAG_UPGRADE";
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