package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开招募面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenRecruitPanel extends CGMessage{
	
	/** 招募类型 */
	private int recruitType;
	
	public CGOpenRecruitPanel (){
	}
	
	public CGOpenRecruitPanel (
			int recruitType ){
			this.recruitType = recruitType;
	}
	
	@Override
	protected boolean readImpl() {
		recruitType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(recruitType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_OPEN_RECRUIT_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_RECRUIT_PANEL";
	}

	public int getRecruitType(){
		return recruitType;
	}
		
	public void setRecruitType(int recruitType){
		this.recruitType = recruitType;
	}

	@Override
	public void execute() {
	}
}