package com.hifun.soul.gameserver.mall.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 显示商城道具列表
 * 
 * @author SevenSoul
 */
@Component
public class CGShowMallItemList extends CGMessage{
	
	/** 商城道具种类 */
	private int mallItemType;
	/** 页面索引 */
	private short pageIndex;
	
	public CGShowMallItemList (){
	}
	
	public CGShowMallItemList (
			int mallItemType,
			short pageIndex ){
			this.mallItemType = mallItemType;
			this.pageIndex = pageIndex;
	}
	
	@Override
	protected boolean readImpl() {
		mallItemType = readInteger();
		pageIndex = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(mallItemType);
		writeShort(pageIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_MALL_ITEM_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_MALL_ITEM_LIST";
	}

	public int getMallItemType(){
		return mallItemType;
	}
		
	public void setMallItemType(int mallItemType){
		this.mallItemType = mallItemType;
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