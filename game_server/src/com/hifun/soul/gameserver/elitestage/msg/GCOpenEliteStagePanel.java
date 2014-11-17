package com.hifun.soul.gameserver.elitestage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开精英副本面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenEliteStagePanel extends GCMessage{
	
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
	/** 总共的次数 */
	private int totalCount;
	/** 剩余次数 */
	private int remainCount;
	/** 当前操作花费魔晶数量 */
	private int currencyNum;

	public GCOpenEliteStagePanel (){
	}
	
	public GCOpenEliteStagePanel (
			int stageTypeId,
			String stageTypeDesc,
			int currentStageId,
			com.hifun.soul.gameserver.elitestage.model.EliteStageInfo[] eliteStages,
			int totalStar,
			int totalCount,
			int remainCount,
			int currencyNum ){
			this.stageTypeId = stageTypeId;
			this.stageTypeDesc = stageTypeDesc;
			this.currentStageId = currentStageId;
			this.eliteStages = eliteStages;
			this.totalStar = totalStar;
			this.totalCount = totalCount;
			this.remainCount = remainCount;
			this.currencyNum = currencyNum;
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
		totalCount = readInteger();
		remainCount = readInteger();
		currencyNum = readInteger();
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
		writeInteger(totalCount);
		writeInteger(remainCount);
		writeInteger(currencyNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_ELITE_STAGE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_ELITE_STAGE_PANEL";
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

	public int getTotalCount(){
		return totalCount;
	}
		
	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getRemainCount(){
		return remainCount;
	}
		
	public void setRemainCount(int remainCount){
		this.remainCount = remainCount;
	}

	public int getCurrencyNum(){
		return currencyNum;
	}
		
	public void setCurrencyNum(int currencyNum){
		this.currencyNum = currencyNum;
	}
}