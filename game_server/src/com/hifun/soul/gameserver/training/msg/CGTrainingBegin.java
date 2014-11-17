package com.hifun.soul.gameserver.training.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 发起训练
 * 
 * @author SevenSoul
 */
@Component
public class CGTrainingBegin extends CGMessage{
	
	/** 训练大类：1普通训练,2vip训练 */
	private int trainingType;
	/** 训练小类型 */
	private int trainingDetailType;
	
	public CGTrainingBegin (){
	}
	
	public CGTrainingBegin (
			int trainingType,
			int trainingDetailType ){
			this.trainingType = trainingType;
			this.trainingDetailType = trainingDetailType;
	}
	
	@Override
	protected boolean readImpl() {
		trainingType = readInteger();
		trainingDetailType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(trainingType);
		writeInteger(trainingDetailType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_TRAINING_BEGIN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TRAINING_BEGIN";
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

	@Override
	public void execute() {
	}
}