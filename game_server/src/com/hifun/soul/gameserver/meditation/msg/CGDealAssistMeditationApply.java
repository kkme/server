package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 处理好友协助申请消息
 * 
 * @author SevenSoul
 */
@Component
public class CGDealAssistMeditationApply extends CGMessage{
	
	/** 是否接受 */
	private boolean isAccept;
	/** 申请角色id */
	private long applyHumanId;
	
	public CGDealAssistMeditationApply (){
	}
	
	public CGDealAssistMeditationApply (
			boolean isAccept,
			long applyHumanId ){
			this.isAccept = isAccept;
			this.applyHumanId = applyHumanId;
	}
	
	@Override
	protected boolean readImpl() {
		isAccept = readBoolean();
		applyHumanId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(isAccept);
		writeLong(applyHumanId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_DEAL_ASSIST_MEDITATION_APPLY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DEAL_ASSIST_MEDITATION_APPLY";
	}

	public boolean getIsAccept(){
		return isAccept;
	}
		
	public void setIsAccept(boolean isAccept){
		this.isAccept = isAccept;
	}

	public long getApplyHumanId(){
		return applyHumanId;
	}
		
	public void setApplyHumanId(long applyHumanId){
		this.applyHumanId = applyHumanId;
	}

	@Override
	public void execute() {
	}
}