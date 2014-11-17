package com.hifun.soul.gameserver.training.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新训练场状态
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateTrainingState extends GCMessage{
	
	/** 训练场状态：1:待训练，2:正在训练中,3:可收获训练经验 */
	private int trainingState;

	public GCUpdateTrainingState (){
	}
	
	public GCUpdateTrainingState (
			int trainingState ){
			this.trainingState = trainingState;
	}

	@Override
	protected boolean readImpl() {
		trainingState = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(trainingState);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_TRAINING_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_TRAINING_STATE";
	}

	public int getTrainingState(){
		return trainingState;
	}
		
	public void setTrainingState(int trainingState){
		this.trainingState = trainingState;
	}
}