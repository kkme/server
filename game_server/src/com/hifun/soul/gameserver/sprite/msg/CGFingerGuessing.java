package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求开始对酒
 * 
 * @author SevenSoul
 */
@Component
public class CGFingerGuessing extends CGMessage{
	
	/** 页面id */
	private int pageId;
	/** 对酒类型(普通或者高级) */
	private int guessType;
	
	public CGFingerGuessing (){
	}
	
	public CGFingerGuessing (
			int pageId,
			int guessType ){
			this.pageId = pageId;
			this.guessType = guessType;
	}
	
	@Override
	protected boolean readImpl() {
		pageId = readInteger();
		guessType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pageId);
		writeInteger(guessType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_FINGER_GUESSING;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FINGER_GUESSING";
	}

	public int getPageId(){
		return pageId;
	}
		
	public void setPageId(int pageId){
		this.pageId = pageId;
	}

	public int getGuessType(){
		return guessType;
	}
		
	public void setGuessType(int guessType){
		this.guessType = guessType;
	}

	@Override
	public void execute() {
	}
}