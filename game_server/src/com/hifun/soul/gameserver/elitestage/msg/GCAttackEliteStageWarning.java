package com.hifun.soul.gameserver.elitestage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 进入精英副本战斗前的警告
 *
 * @author SevenSoul
 */
@Component
public class GCAttackEliteStageWarning extends GCMessage{
	
	/** 精英副本id */
	private int stageId;
	/** 警示信息 */
	private String warningInfo;

	public GCAttackEliteStageWarning (){
	}
	
	public GCAttackEliteStageWarning (
			int stageId,
			String warningInfo ){
			this.stageId = stageId;
			this.warningInfo = warningInfo;
	}

	@Override
	protected boolean readImpl() {
		stageId = readInteger();
		warningInfo = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(stageId);
		writeString(warningInfo);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ATTACK_ELITE_STAGE_WARNING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ATTACK_ELITE_STAGE_WARNING";
	}

	public int getStageId(){
		return stageId;
	}
		
	public void setStageId(int stageId){
		this.stageId = stageId;
	}

	public String getWarningInfo(){
		return warningInfo;
	}
		
	public void setWarningInfo(String warningInfo){
		this.warningInfo = warningInfo;
	}
}