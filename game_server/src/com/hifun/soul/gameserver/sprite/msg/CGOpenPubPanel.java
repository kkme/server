package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开精灵酒馆面板 
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenPubPanel extends CGMessage{
	
	/** 页面id(首次打开传-1) */
	private int pageId;
	
	public CGOpenPubPanel (){
	}
	
	public CGOpenPubPanel (
			int pageId ){
			this.pageId = pageId;
	}
	
	@Override
	protected boolean readImpl() {
		pageId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pageId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_OPEN_PUB_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_PUB_PANEL";
	}

	public int getPageId(){
		return pageId;
	}
		
	public void setPageId(int pageId){
		this.pageId = pageId;
	}

	@Override
	public void execute() {
	}
}