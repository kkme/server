package com.hifun.soul.gameserver.godsoul.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求升级灵图
 * 
 * @author SevenSoul
 */
@Component
public class CGUpgradeMagicPaper extends CGMessage{
	
	/** 灵图ID */
	private int paperId;
	
	public CGUpgradeMagicPaper (){
	}
	
	public CGUpgradeMagicPaper (
			int paperId ){
			this.paperId = paperId;
	}
	
	@Override
	protected boolean readImpl() {
		paperId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(paperId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_UPGRADE_MAGIC_PAPER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_UPGRADE_MAGIC_PAPER";
	}

	public int getPaperId(){
		return paperId;
	}
		
	public void setPaperId(int paperId){
		this.paperId = paperId;
	}

	@Override
	public void execute() {
	}
}