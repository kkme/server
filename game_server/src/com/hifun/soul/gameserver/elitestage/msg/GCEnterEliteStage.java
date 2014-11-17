package com.hifun.soul.gameserver.elitestage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 进入精英副本
 *
 * @author SevenSoul
 */
@Component
public class GCEnterEliteStage extends GCMessage{
	
	/** 精英副本类型id */
	private int stageTypeId;
	/** 精英副本类型描述 */
	private String stageTypeDesc;
	/** 当前能进的精英副本id */
	private int currentStageId;
	/** 精英副本信息 */
	private com.hifun.soul.gameserver.elitestage.model.EliteStageInfo[] eliteStages;
	/** 评星总数 */
	private int totalStar;

	public GCEnterEliteStage (){
	}
	
	public GCEnterEliteStage (
			int stageTypeId,
			String stageTypeDesc,
			int currentStageId,
			com.hifun.soul.gameserver.elitestage.model.EliteStageInfo[] eliteStages,
			int totalStar ){
			this.stageTypeId = stageTypeId;
			this.stageTypeDesc = stageTypeDesc;
			this.currentStageId = currentStageId;
			this.eliteStages = eliteStages;
			this.totalStar = totalStar;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		stageTypeId = readInteger();
		stageTypeDesc = readString();
		currentStageId = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		eliteStages = new com.hifun.soul.gameserver.elitestage.model.EliteStageInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.elitestage.model.EliteStageInfo objeliteStages = new com.hifun.soul.gameserver.elitestage.model.EliteStageInfo();
			eliteStages[i] = objeliteStages;
					objeliteStages.setStageId(readInteger());
							objeliteStages.setMonsterName(readString());
							objeliteStages.setMonsterIconId(readInteger());
							objeliteStages.setOpenLevel(readInteger());
							objeliteStages.setOpenState(readInteger());
							objeliteStages.setChallengeState(readBoolean());
							objeliteStages.setCoinNum(readInteger());
							objeliteStages.setExp(readInteger());
							objeliteStages.setTechPoint(readInteger());
								{
	int subCountitemNames = readShort();
		String[] subListitemNames = new String[subCountitemNames];
		objeliteStages.setItemNames(subListitemNames);
	for(int jitemNames = 0; jitemNames < subCountitemNames; jitemNames++){
						subListitemNames[jitemNames] = readString();
			}
	}
							objeliteStages.setEnergyNum(readInteger());
							objeliteStages.setStar(readInteger());
							objeliteStages.setPassState(readBoolean());
								{
	com.hifun.soul.gameserver.elitestage.model.EliteStageTypeInfo objtypeInfo = new com.hifun.soul.gameserver.elitestage.model.EliteStageTypeInfo();
	objeliteStages.setTypeInfo(objtypeInfo);
				objtypeInfo.setStageType(readInteger());
				objtypeInfo.setTypeName(readString());
				objtypeInfo.setOpenLevel(readInteger());
			}
				}
		totalStar = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(stageTypeId);
		writeString(stageTypeDesc);
		writeInteger(currentStageId);
	writeShort(eliteStages.length);
	for(int i=0; i<eliteStages.length; i++){
	com.hifun.soul.gameserver.elitestage.model.EliteStageInfo objeliteStages = eliteStages[i];
				writeInteger(objeliteStages.getStageId());
				writeString(objeliteStages.getMonsterName());
				writeInteger(objeliteStages.getMonsterIconId());
				writeInteger(objeliteStages.getOpenLevel());
				writeInteger(objeliteStages.getOpenState());
				writeBoolean(objeliteStages.getChallengeState());
				writeInteger(objeliteStages.getCoinNum());
				writeInteger(objeliteStages.getExp());
				writeInteger(objeliteStages.getTechPoint());
					String[] itemNames_objeliteStages=objeliteStages.getItemNames();
	writeShort(itemNames_objeliteStages.length);
	for(int jitemNames=0; jitemNames<itemNames_objeliteStages.length; jitemNames++){
					writeString(itemNames_objeliteStages[jitemNames]);
			}
				writeInteger(objeliteStages.getEnergyNum());
				writeInteger(objeliteStages.getStar());
				writeBoolean(objeliteStages.getPassState());
					com.hifun.soul.gameserver.elitestage.model.EliteStageTypeInfo typeInfo_objeliteStages = objeliteStages.getTypeInfo();
					writeInteger(typeInfo_objeliteStages.getStageType());
					writeString(typeInfo_objeliteStages.getTypeName());
					writeInteger(typeInfo_objeliteStages.getOpenLevel());
			}
		writeInteger(totalStar);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ENTER_ELITE_STAGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ENTER_ELITE_STAGE";
	}

	public int getStageTypeId(){
		return stageTypeId;
	}
		
	public void setStageTypeId(int stageTypeId){
		this.stageTypeId = stageTypeId;
	}

	public String getStageTypeDesc(){
		return stageTypeDesc;
	}
		
	public void setStageTypeDesc(String stageTypeDesc){
		this.stageTypeDesc = stageTypeDesc;
	}

	public int getCurrentStageId(){
		return currentStageId;
	}
		
	public void setCurrentStageId(int currentStageId){
		this.currentStageId = currentStageId;
	}

	public com.hifun.soul.gameserver.elitestage.model.EliteStageInfo[] getEliteStages(){
		return eliteStages;
	}

	public void setEliteStages(com.hifun.soul.gameserver.elitestage.model.EliteStageInfo[] eliteStages){
		this.eliteStages = eliteStages;
	}	

	public int getTotalStar(){
		return totalStar;
	}
		
	public void setTotalStar(int totalStar){
		this.totalStar = totalStar;
	}
}