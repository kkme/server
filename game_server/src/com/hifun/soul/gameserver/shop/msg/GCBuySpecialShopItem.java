package com.hifun.soul.gameserver.shop.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 购买神秘商店物品
 *
 * @author SevenSoul
 */
@Component
public class GCBuySpecialShopItem extends GCMessage{
	
	/** 神秘商品id */
	private int specialShopItemId;
	/** 是否已经购买 */
	private boolean isBuy;
	/** 奖品列表 */
	private com.hifun.soul.gameserver.shop.SpecialShopNotify[] specialShopNotifyList;

	public GCBuySpecialShopItem (){
	}
	
	public GCBuySpecialShopItem (
			int specialShopItemId,
			boolean isBuy,
			com.hifun.soul.gameserver.shop.SpecialShopNotify[] specialShopNotifyList ){
			this.specialShopItemId = specialShopItemId;
			this.isBuy = isBuy;
			this.specialShopNotifyList = specialShopNotifyList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		specialShopItemId = readInteger();
		isBuy = readBoolean();
		count = readShort();
		count = count < 0 ? 0 : count;
		specialShopNotifyList = new com.hifun.soul.gameserver.shop.SpecialShopNotify[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.shop.SpecialShopNotify objspecialShopNotifyList = new com.hifun.soul.gameserver.shop.SpecialShopNotify();
			specialShopNotifyList[i] = objspecialShopNotifyList;
					objspecialShopNotifyList.setId(readInteger());
							objspecialShopNotifyList.setRoleName(readString());
							objspecialShopNotifyList.setItemName(readString());
							objspecialShopNotifyList.setItemNum(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(specialShopItemId);
		writeBoolean(isBuy);
	writeShort(specialShopNotifyList.length);
	for(int i=0; i<specialShopNotifyList.length; i++){
	com.hifun.soul.gameserver.shop.SpecialShopNotify objspecialShopNotifyList = specialShopNotifyList[i];
				writeInteger(objspecialShopNotifyList.getId());
				writeString(objspecialShopNotifyList.getRoleName());
				writeString(objspecialShopNotifyList.getItemName());
				writeInteger(objspecialShopNotifyList.getItemNum());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BUY_SPECIAL_SHOP_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BUY_SPECIAL_SHOP_ITEM";
	}

	public int getSpecialShopItemId(){
		return specialShopItemId;
	}
		
	public void setSpecialShopItemId(int specialShopItemId){
		this.specialShopItemId = specialShopItemId;
	}

	public boolean getIsBuy(){
		return isBuy;
	}
		
	public void setIsBuy(boolean isBuy){
		this.isBuy = isBuy;
	}

	public com.hifun.soul.gameserver.shop.SpecialShopNotify[] getSpecialShopNotifyList(){
		return specialShopNotifyList;
	}

	public void setSpecialShopNotifyList(com.hifun.soul.gameserver.shop.SpecialShopNotify[] specialShopNotifyList){
		this.specialShopNotifyList = specialShopNotifyList;
	}	
}