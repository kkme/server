package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求刷新日常任务列表
 * 
 * @author SevenSoul
 */
@Component
public class CGRefreshDailyQuestList extends CGMessage{
	
	
	public CGRefreshDailyQuestList (){
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
		return MessageType.CG_REFRESH_DAILY_QUEST_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REFRESH_DAILY_QUEST_LIST";
	}

	@Override
	public void execute() {
	}
}