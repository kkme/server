package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应主人申请提取奴隶经验
 *
 * @author SevenSoul
 */
@Component
public class GCMasterApplyExtractExperience extends GCMessage{
	
	/** 提取经验 */
	private int extractExperience;
	/** 提取经验花费 */
	private int extracExperienceCost;

	public GCMasterApplyExtractExperience (){
	}
	
	public GCMasterApplyExtractExperience (
			int extractExperience,
			int extracExperienceCost ){
			this.extractExperience = extractExperience;
			this.extracExperienceCost = extracExperienceCost;
	}

	@Override
	protected boolean readImpl() {
		extractExperience = readInteger();
		extracExperienceCost = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(extractExperience);
		writeInteger(extracExperienceCost);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MASTER_APPLY_EXTRACT_EXPERIENCE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MASTER_APPLY_EXTRACT_EXPERIENCE";
	}

	public int getExtractExperience(){
		return extractExperience;
	}
		
	public void setExtractExperience(int extractExperience){
		this.extractExperience = extractExperience;
	}

	public int getExtracExperienceCost(){
		return extracExperienceCost;
	}
		
	public void setExtracExperienceCost(int extracExperienceCost){
		this.extracExperienceCost = extracExperienceCost;
	}
}