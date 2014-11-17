package com.hifun.soul.gameserver.predict.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应激活预见 
 *
 * @author SevenSoul
 */
@Component
public class GCActivatePredict extends GCMessage{
	
	/** 预见id */
	private int predictId;
	/** 是否激活 */
	private boolean activated;
	/** 当前属性列表 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] currentProperties;
	/** 下级属性列表 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] nextProperties;
	/** 可激活数量 */
	private int canActivateNum;

	public GCActivatePredict (){
	}
	
	public GCActivatePredict (
			int predictId,
			boolean activated,
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] currentProperties,
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] nextProperties,
			int canActivateNum ){
			this.predictId = predictId;
			this.activated = activated;
			this.currentProperties = currentProperties;
			this.nextProperties = nextProperties;
			this.canActivateNum = canActivateNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		predictId = readInteger();
		activated = readBoolean();
		count = readShort();
		count = count < 0 ? 0 : count;
		currentProperties = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(count);
		for(int i=0; i<count; i++){
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objcurrentProperties = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
			currentProperties[i] = objcurrentProperties;
					objcurrentProperties.setKey(readInteger());
							objcurrentProperties.setValue(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		nextProperties = com.hifun.soul.core.util.KeyValuePair.newKeyValuePairArray(count);
		for(int i=0; i<count; i++){
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objnextProperties = new com.hifun.soul.core.util.KeyValuePair<Integer,Integer>();
			nextProperties[i] = objnextProperties;
					objnextProperties.setKey(readInteger());
							objnextProperties.setValue(readInteger());
				}
		canActivateNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(predictId);
		writeBoolean(activated);
	writeShort(currentProperties.length);
	for(int i=0; i<currentProperties.length; i++){
	com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objcurrentProperties = currentProperties[i];
				writeInteger(objcurrentProperties.getKey());
				writeInteger(objcurrentProperties.getValue());
	}
	writeShort(nextProperties.length);
	for(int i=0; i<nextProperties.length; i++){
	com.hifun.soul.core.util.KeyValuePair<Integer,Integer> objnextProperties = nextProperties[i];
				writeInteger(objnextProperties.getKey());
				writeInteger(objnextProperties.getValue());
	}
		writeInteger(canActivateNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ACTIVATE_PREDICT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ACTIVATE_PREDICT";
	}

	public int getPredictId(){
		return predictId;
	}
		
	public void setPredictId(int predictId){
		this.predictId = predictId;
	}

	public boolean getActivated(){
		return activated;
	}
		
	public void setActivated(boolean activated){
		this.activated = activated;
	}

	public com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] getCurrentProperties(){
		return currentProperties;
	}

	public void setCurrentProperties(com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] currentProperties){
		this.currentProperties = currentProperties;
	}	

	public com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] getNextProperties(){
		return nextProperties;
	}

	public void setNextProperties(com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] nextProperties){
		this.nextProperties = nextProperties;
	}	

	public int getCanActivateNum(){
		return canActivateNum;
	}
		
	public void setCanActivateNum(int canActivateNum){
		this.canActivateNum = canActivateNum;
	}
}