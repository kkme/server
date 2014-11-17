package com.hifun.soul.gameserver.training.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 发起训练后的更新消息
 *
 * @author SevenSoul
 */
@Component
public class GCTrainingBeginUpdate extends GCMessage{
	
	/** 训练大类：1普通训练,2vip训练 */
	private int trainingType;
	/** 训练小类型 */
	private int trainingDetailType;
	/** 普通训练剩余分钟数 */
	private int normalTrainingTimeRemain;
	/** vip训练剩余可消耗魔晶数 */
	private int vipTrainingConsumableCrystalNum;

	public GCTrainingBeginUpdate (){
	}
	
	public GCTrainingBeginUpdate (
			int trainingType,
			int trainingDetailType,
			int normalTrainingTimeRemain,
			int vipTrainingConsumableCrystalNum ){
			this.trainingType = trainingType;
			this.trainingDetailType = trainingDetailType;
			this.normalTrainingTimeRemain = normalTrainingTimeRemain;
			this.vipTrainingConsumableCrystalNum = vipTrainingConsumableCrystalNum;
	}

	@Override
	protected boolean readImpl() {
		trainingType = readInteger();
		trainingDetailType = readInteger();
		normalTrainingTimeRemain = readInteger();
		vipTrainingConsumableCrystalNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(trainingType);
		writeInteger(trainingDetailType);
		writeInteger(normalTrainingTimeRemain);
		writeInteger(vipTrainingConsumableCrystalNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TRAINING_BEGIN_UPDATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TRAINING_BEGIN_UPDATE";
	}

	public int getTrainingType(){
		return trainingType;
	}
		
	public void setTrainingType(int trainingType){
		this.trainingType = trainingType;
	}

	public int getTrainingDetailType(){
		return trainingDetailType;
	}
		
	public void setTrainingDetailType(int trainingDetailType){
		this.trainingDetailType = trainingDetailType;
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
}