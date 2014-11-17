package com.hifun.soul.gameserver.technology.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开科技面板
 * 
 * @author SevenSoul
 */
@Component
public class CGShowTechnologyPanel extends CGMessage{
	
	/** 页面索引 */
	private short pageIndex;
	
	public CGShowTechnologyPanel (){
	}
	
	public CGShowTechnologyPanel (
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
		return MessageType.CG_SHOW_TECHNOLOGY_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_TECHNOLOGY_PANEL";
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