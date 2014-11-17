package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 主人申请提取奴隶经验
 * 
 * @author SevenSoul
 */
@Component
public class CGMasterApplyExtractExperience extends CGMessage{
	
	/** 要提取的奴隶角色ID */
	private long slaveHumanId;
	/** 提取类型 */
	private int extractType;
	
	public CGMasterApplyExtractExperience (){
	}
	
	public CGMasterApplyExtractExperience (
			long slaveHumanId,
			int extractType ){
			this.slaveHumanId = slaveHumanId;
			this.extractType = extractType;
	}
	
	@Override
	protected boolean readImpl() {
		slaveHumanId = readLong();
		extractType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(slaveHumanId);
		writeInteger(extractType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_MASTER_APPLY_EXTRACT_EXPERIENCE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MASTER_APPLY_EXTRACT_EXPERIENCE";
	}

	public long getSlaveHumanId(){
		return slaveHumanId;
	}
		
	public void setSlaveHumanId(long slaveHumanId){
		this.slaveHumanId = slaveHumanId;
	}

	public int getExtractType(){
		return extractType;
	}
		
	public void setExtractType(int extractType){
		this.extractType = extractType;
	}

	@Override
	public void execute() {
	}
}