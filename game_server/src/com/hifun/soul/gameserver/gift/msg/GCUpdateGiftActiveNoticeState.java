package com.hifun.soul.gameserver.gift.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新激活天赋的提醒状态
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateGiftActiveNoticeState extends GCMessage{
	
	/** 提示状态 */
	private boolean noticeState;

	public GCUpdateGiftActiveNoticeState (){
	}
	
	public GCUpdateGiftActiveNoticeState (
			boolean noticeState ){
			this.noticeState = noticeState;
	}

	@Override
	protected boolean readImpl() {
		noticeState = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(noticeState);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_GIFT_ACTIVE_NOTICE_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_GIFT_ACTIVE_NOTICE_STATE";
	}

	public boolean getNoticeState(){
		return noticeState;
	}
		
	public void setNoticeState(boolean noticeState){
		this.noticeState = noticeState;
	}
}