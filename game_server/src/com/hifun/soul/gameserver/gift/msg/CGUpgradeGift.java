package com.hifun.soul.gameserver.gift.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求升级天赋
 * 
 * @author SevenSoul
 */
@Component
public class CGUpgradeGift extends CGMessage{
	
	/** 天赋id */
	private int giftId;
	
	public CGUpgradeGift (){
	}
	
	public CGUpgradeGift (
			int giftId ){
			this.giftId = giftId;
	}
	
	@Override
	protected boolean readImpl() {
		giftId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(giftId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_UPGRADE_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_UPGRADE_GIFT";
	}

	public int getGiftId(){
		return giftId;
	}
		
	public void setGiftId(int giftId){
		this.giftId = giftId;
	}

	@Override
	public void execute() {
	}
}