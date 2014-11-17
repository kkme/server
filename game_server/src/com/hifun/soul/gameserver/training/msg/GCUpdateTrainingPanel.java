package com.hifun.soul.gameserver.training.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新训练面板信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateTrainingPanel extends GCMessage{
	
	/** 普通训练类型 */
	private com.hifun.soul.gameserver.training.model.NormalTrainingInfo[] normalTrainings;
	/** vip训练类型 */
	private com.hifun.soul.gameserver.training.model.VipTrainingInfo[] vipTrainings;
	/** 普通训练剩余分钟数 */
	private int normalTrainingTimeRemain;
	/** vip训练剩余可消耗魔晶数 */
	private int vipTrainingConsumableCrystalNum;
	/** 每日普通训练分钟数限额 */
	private int maxNormalTrainingTime;
	/** 每日可用vip训练剩余可消耗魔晶数限额 */
	private int maxVipTrainingCrystalNum;

	public GCUpdateTrainingPanel (){
	}
	
	public GCUpdateTrainingPanel (
			com.hifun.soul.gameserver.training.model.NormalTrainingInfo[] normalTrainings,
			com.hifun.soul.gameserver.training.model.VipTrainingInfo[] vipTrainings,
			int normalTrainingTimeRemain,
			int vipTrainingConsumableCrystalNum,
			int maxNormalTrainingTime,
			int maxVipTrainingCrystalNum ){
			this.normalTrainings = normalTrainings;
			this.vipTrainings = vipTrainings;
			this.normalTrainingTimeRemain = normalTrainingTimeRemain;
			this.vipTrainingConsumableCrystalNum = vipTrainingConsumableCrystalNum;
			this.maxNormalTrainingTime = maxNormalTrainingTime;
			this.maxVipTrainingCrystalNum = maxVipTrainingCrystalNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		normalTrainings = new com.hifun.soul.gameserver.training.model.NormalTrainingInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.training.model.NormalTrainingInfo objnormalTrainings = new com.hifun.soul.gameserver.training.model.NormalTrainingInfo();
			normalTrainings[i] = objnormalTrainings;
					objnormalTrainings.setTrainingId(readInteger());
							objnormalTrainings.setTrainingName(readString());
							objnormalTrainings.setTrainingType(readInteger());
							objnormalTrainings.setCostCoinNum(readInteger());
							objnormalTrainings.setExp(readInteger());
							objnormalTrainings.setTrainingState(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		vipTrainings = new com.hifun.soul.gameserver.training.model.VipTrainingInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.training.model.VipTrainingInfo objvipTrainings = new com.hifun.soul.gameserver.training.model.VipTrainingInfo();
			vipTrainings[i] = objvipTrainings;
					objvipTrainings.setTrainingId(readInteger());
							objvipTrainings.setTrainingName(readString());
							objvipTrainings.setTrainingType(readInteger());
							objvipTrainings.setCostCurrencyType(readInteger());
							objvipTrainings.setCostCurrencyNum(readInteger());
							objvipTrainings.setExp(readInteger());
							objvipTrainings.setOpenVipLevel(readInteger());
				}
		normalTrainingTimeRemain = readInteger();
		vipTrainingConsumableCrystalNum = readInteger();
		maxNormalTrainingTime = readInteger();
		maxVipTrainingCrystalNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(normalTrainings.length);
	for(int i=0; i<normalTrainings.length; i++){
	com.hifun.soul.gameserver.training.model.NormalTrainingInfo objnormalTrainings = normalTrainings[i];
				writeInteger(objnormalTrainings.getTrainingId());
				writeString(objnormalTrainings.getTrainingName());
				writeInteger(objnormalTrainings.getTrainingType());
				writeInteger(objnormalTrainings.getCostCoinNum());
				writeInteger(objnormalTrainings.getExp());
				writeInteger(objnormalTrainings.getTrainingState());
	}
	writeShort(vipTrainings.length);
	for(int i=0; i<vipTrainings.length; i++){
	com.hifun.soul.gameserver.training.model.VipTrainingInfo objvipTrainings = vipTrainings[i];
				writeInteger(objvipTrainings.getTrainingId());
				writeString(objvipTrainings.getTrainingName());
				writeInteger(objvipTrainings.getTrainingType());
				writeInteger(objvipTrainings.getCostCurrencyType());
				writeInteger(objvipTrainings.getCostCurrencyNum());
				writeInteger(objvipTrainings.getExp());
				writeInteger(objvipTrainings.getOpenVipLevel());
	}
		writeInteger(normalTrainingTimeRemain);
		writeInteger(vipTrainingConsumableCrystalNum);
		writeInteger(maxNormalTrainingTime);
		writeInteger(maxVipTrainingCrystalNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_TRAINING_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_TRAINING_PANEL";
	}

	public com.hifun.soul.gameserver.training.model.NormalTrainingInfo[] getNormalTrainings(){
		return normalTrainings;
	}

	public void setNormalTrainings(com.hifun.soul.gameserver.training.model.NormalTrainingInfo[] normalTrainings){
		this.normalTrainings = normalTrainings;
	}	

	public com.hifun.soul.gameserver.training.model.VipTrainingInfo[] getVipTrainings(){
		return vipTrainings;
	}

	public void setVipTrainings(com.hifun.soul.gameserver.training.model.VipTrainingInfo[] vipTrainings){
		this.vipTrainings = vipTrainings;
	}	

	public int getNormalTrainingTimeRemain(){
		return normalTrainingTimeRemain;
	}
		
	public void setNormalTrainingTimeRemain(int normalTrainingTimeRemain){
		this.normalTrainingTimeRemain = normalTrainingTimeRemain;
	}

	public int getVipTrainingConsumableCrystalNum(){
		return vipTrainingConsumableCrystalNum;
	}
		
	public void setVipTrainingConsumableCrystalNum(int vipTrainingConsumableCrystalNum){
		this.vipTrainingConsumableCrystalNum = vipTrainingConsumableCrystalNum;
	}

	public int getMaxNormalTrainingTime(){
		return maxNormalTrainingTime;
	}
		
	public void setMaxNormalTrainingTime(int maxNormalTrainingTime){
		this.maxNormalTrainingTime = maxNormalTrainingTime;
	}

	public int getMaxVipTrainingCrystalNum(){
		return maxVipTrainingCrystalNum;
	}
		
	public void setMaxVipTrainingCrystalNum(int maxVipTrainingCrystalNum){
		this.maxVipTrainingCrystalNum = maxVipTrainingCrystalNum;
	}
}