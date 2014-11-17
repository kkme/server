package com.hifun.soul.gameserver.matchbattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 发送战报
 *
 * @author SevenSoul
 */
@Component
public class GCBattleReport extends GCMessage{
	
	/** 战报内容 */
	private String content;

	public GCBattleReport (){
	}
	
	public GCBattleReport (
			String content ){
			this.content = content;
	}

	@Override
	protected boolean readImpl() {
		content = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(content);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BATTLE_REPORT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BATTLE_REPORT";
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}
}