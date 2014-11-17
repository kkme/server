package com.hifun.soul.gameserver.predict.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 激活指定预见 
 * 
 * @author SevenSoul
 */
@Component
public class CGActivatePredict extends CGMessage{
	
	/** 预见id */
	private int predictId;
	
	public CGActivatePredict (){
	}
	
	public CGActivatePredict (
			int predictId ){
			this.predictId = predictId;
	}
	
	@Override
	protected boolean readImpl() {
		predictId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(predictId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ACTIVATE_PREDICT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ACTIVATE_PREDICT";
	}

	public int getPredictId(){
		return predictId;
	}
		
	public void setPredictId(int predictId){
		this.predictId = predictId;
	}

	@Override
	public void execute() {
	}
}