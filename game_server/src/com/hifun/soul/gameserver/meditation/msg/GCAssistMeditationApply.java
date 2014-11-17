package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 收到好友协助申请消息
 *
 * @author SevenSoul
 */
@Component
public class GCAssistMeditationApply extends GCMessage{
	
	/** 申请角色id */
	private long applyHumanId;
	/** 申请角色名称 */
	private String applyHumanName;

	public GCAssistMeditationApply (){
	}
	
	public GCAssistMeditationApply (
			long applyHumanId,
			String applyHumanName ){
			this.applyHumanId = applyHumanId;
			this.applyHumanName = applyHumanName;
	}

	@Override
	protected boolean readImpl() {
		applyHumanId = readLong();
		applyHumanName = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(applyHumanId);
		writeString(applyHumanName);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ASSIST_MEDITATION_APPLY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ASSIST_MEDITATION_APPLY";
	}

	public long getApplyHumanId(){
		return applyHumanId;
	}
		
	public void setApplyHumanId(long applyHumanId){
		this.applyHumanId = applyHumanId;
	}

	public String getApplyHumanName(){
		return applyHumanName;
	}
		
	public void setApplyHumanName(String applyHumanName){
		this.applyHumanName = applyHumanName;
	}
}