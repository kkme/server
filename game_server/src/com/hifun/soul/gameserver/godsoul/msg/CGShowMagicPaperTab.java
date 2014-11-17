package com.hifun.soul.gameserver.godsoul.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求展示灵图标签页
 * 
 * @author SevenSoul
 */
@Component
public class CGShowMagicPaperTab extends CGMessage{
	
	
	public CGShowMagicPaperTab (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_MAGIC_PAPER_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_MAGIC_PAPER_TAB";
	}

	@Override
	public void execute() {
	}
}