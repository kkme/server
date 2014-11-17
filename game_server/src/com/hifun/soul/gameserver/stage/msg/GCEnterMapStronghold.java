package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回据点的信息
 *
 * @author SevenSoul
 */
@Component
public class GCEnterMapStronghold extends GCMessage{
	
	/** 地图据点id */
	private int strongholdId;
	/** 精力值 */
	private int energy;
	/** 选中的关卡id */
	private int selectStageId;
	/** 据点关卡信息 */
	private com.hifun.soul.gameserver.stage.model.StageSimpleInfo[] stageSimpleInfos;

	public GCEnterMapStronghold (){
	}
	
	public GCEnterMapStronghold (
			int strongholdId,
			int energy,
			int selectStageId,
			com.hifun.soul.gameserver.stage.model.StageSimpleInfo[] stageSimpleInfos ){
			this.strongholdId = strongholdId;
			this.energy = energy;
			this.selectStageId = selectStageId;
			this.stageSimpleInfos = stageSimpleInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		strongholdId = readInteger();
		energy = readInteger();
		selectStageId = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		stageSimpleInfos = new com.hifun.soul.gameserver.stage.model.StageSimpleInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.stage.model.StageSimpleInfo objstageSimpleInfos = new com.hifun.soul.gameserver.stage.model.StageSimpleInfo();
			stageSimpleInfos[i] = objstageSimpleInfos;
					objstageSimpleInfos.setStageId(readInteger());
							objstageSimpleInfos.setX(readInteger());
							objstageSimpleInfos.setY(readInteger());
							objstageSimpleInfos.setStageName(readString());
							objstageSimpleInfos.setStageDesc(readString());
							objstageSimpleInfos.setMinLevel(readInteger());
							objstageSimpleInfos.setPass(readBoolean());
							objstageSimpleInfos.setMonsterIcon(readInteger());
							objstageSimpleInfos.setMapId(readInteger());
							objstageSimpleInfos.setStar(readInteger());
							objstageSimpleInfos.setAutoBattle(readBoolean());
							objstageSimpleInfos.setMonsterName(readString());
							objstageSimpleInfos.setMonsterLevel(readInteger());
							objstageSimpleInfos.setMonsterOccupation(readInteger());
							objstageSimpleInfos.setRewardExperience(readInteger());
							objstageSimpleInfos.setRewardCurrencyType(readShort());
							objstageSimpleInfos.setRewardCurrencyNum(readInteger());
							objstageSimpleInfos.setMonsterFirstAttack(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(strongholdId);
		writeInteger(energy);
		writeInteger(selectStageId);
	writeShort(stageSimpleInfos.length);
	for(int i=0; i<stageSimpleInfos.length; i++){
	com.hifun.soul.gameserver.stage.model.StageSimpleInfo objstageSimpleInfos = stageSimpleInfos[i];
				writeInteger(objstageSimpleInfos.getStageId());
				writeInteger(objstageSimpleInfos.getX());
				writeInteger(objstageSimpleInfos.getY());
				writeString(objstageSimpleInfos.getStageName());
				writeString(objstageSimpleInfos.getStageDesc());
				writeInteger(objstageSimpleInfos.getMinLevel());
				writeBoolean(objstageSimpleInfos.getPass());
				writeInteger(objstageSimpleInfos.getMonsterIcon());
				writeInteger(objstageSimpleInfos.getMapId());
				writeInteger(objstageSimpleInfos.getStar());
				writeBoolean(objstageSimpleInfos.getAutoBattle());
				writeString(objstageSimpleInfos.getMonsterName());
				writeInteger(objstageSimpleInfos.getMonsterLevel());
				writeInteger(objstageSimpleInfos.getMonsterOccupation());
				writeInteger(objstageSimpleInfos.getRewardExperience());
				writeShort(objstageSimpleInfos.getRewardCurrencyType());
				writeInteger(objstageSimpleInfos.getRewardCurrencyNum());
				writeInteger(objstageSimpleInfos.getMonsterFirstAttack());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ENTER_MAP_STRONGHOLD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ENTER_MAP_STRONGHOLD";
	}

	public int getStrongholdId(){
		return strongholdId;
	}
		
	public void setStrongholdId(int strongholdId){
		this.strongholdId = strongholdId;
	}

	public int getEnergy(){
		return energy;
	}
		
	public void setEnergy(int energy){
		this.energy = energy;
	}

	public int getSelectStageId(){
		return selectStageId;
	}
		
	public void setSelectStageId(int selectStageId){
		this.selectStageId = selectStageId;
	}

	public com.hifun.soul.gameserver.stage.model.StageSimpleInfo[] getStageSimpleInfos(){
		return stageSimpleInfos;
	}

	public void setStageSimpleInfos(com.hifun.soul.gameserver.stage.model.StageSimpleInfo[] stageSimpleInfos){
		this.stageSimpleInfos = stageSimpleInfos;
	}	
}