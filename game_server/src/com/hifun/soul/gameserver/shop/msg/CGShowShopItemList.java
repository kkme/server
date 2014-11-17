package com.hifun.soul.gameserver.shop.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 显示商店道具列表
 * 
 * @author SevenSoul
 */
@Component
public class CGShowShopItemList extends CGMessage{
	
	/** 页面索引 */
	private short pageIndex;
	
	public CGShowShopItemList (){
	}
	
	public CGShowShopItemList (
			short pageIndex ){
			this.pageIndex = pageIndex;
	}
	
	@Override
	protected boolean readImpl() {
		pageIndex = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(pageIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_SHOP_ITEM_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_SHOP_ITEM_LIST";
	}

	public short getPageIndex(){
		return pageIndex;
	}
		
	public void setPageIndex(short pageIndex){
		this.pageIndex = pageIndex;
	}

	@Override
	public void execute() {
	}
}