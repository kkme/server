package com.hifun.soul.gameserver.training.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 领取训练的经验
 *
 * @author SevenSoul
 */
@Component
public class GCCollectTrainingExp extends GCMessage{
	
	/** 训练大类：1普通训练,2vip训练 */
	private int trainingType;
	/** 训练获得的经验数 */
	private int expNum;

	public GCCollectTrainingExp (){
	}
	
	public GCCollectTrainingExp (
			int trainingType,
			int expNum ){
			this.trainingType = trainingType;
			this.expNum = expNum;
	}

	@Override
	protected boolean readImpl() {
		trainingType = readInteger();
		expNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(trainingType);
		writeInteger(expNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_COLLECT_TRAINING_EXP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_COLLECT_TRAINING_EXP";
	}

	public int getTrainingType(){
		return trainingType;
	}
		
	public void setTrainingType(int trainingType){
		this.trainingType = trainingType;
	}

	public int getExpNum(){
		return expNum;
	}
		
	public void setExpNum(int expNum){
		this.expNum = expNum;
	}
}