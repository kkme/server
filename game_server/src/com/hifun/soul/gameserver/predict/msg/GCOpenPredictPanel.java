package com.hifun.soul.gameserver.predict.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开预见面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenPredictPanel extends GCMessage{
	
	/** 页码 */
	private int pageIndex;
	/** 可激活数量 */
	private int canActivateNum;
	/** 等级下限 */
	private int minLevel;
	/** 等级上限 */
	private int maxLevel;
	/** 预见列表 */
	private com.hifun.soul.gameserver.predict.msg.info.PredictInfo[] predictInfos;
	/** 当前属性列表 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] currentProperties;
	/** 下级属性列表 */
	private com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] nextProperties;
	/** 是否是最后一页 */
	private boolean isLastPage;

	public GCOpenPredictPanel (){
	}
	
	public GCOpenPredictPanel (
			int pageIndex,
			int canActivateNum,
			int minLevel,
			int maxLevel,
			com.hifun.soul.gameserver.predict.msg.info.PredictInfo[] predictInfos,
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] currentProperties,
			com.hifun.soul.core.util.KeyValuePair<Integer,Integer>[] nextProperties,
			boolean isLastPage ){
			this.pageIndex = pageIndex;
			this.canActivateNum = canActivateNum;
			this.minLevel = minLevel;
			this.maxLevel = maxLevel;
			this.predictInfos = predictInfos;
			this.currentProperties = currentProperties;
			this.nextProperties = nextProperties;
			this.isLastPage = isLastPage;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		pageIndex = readInteger();
		canActivateNum = readInteger();
		minLevel = readInteger();
		maxLevel = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		predictInfos = new com.hifun.soul.gameserver.predict.msg.info.PredictInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.predict.msg.info.PredictInfo objpredictInfos = new com.hifun.soul.gameserver.predict.msg.info.PredictInfo();
			predictInfos[i] = objpredictInfos;
					objpredictInfos.setPredictId(readInteger());
							objpredictInfos.setActivateNeedLevel(readInteger());
							objpredictInfos.setActivated(readBoolean());
								{
	int subCountfunctions = readShort();
		int[] subListfunctions = new int[subCountfunctions];
		objpredictInfos.setFunctions(subListfunctions);
	for(int jfunctions = 0; jfunctions < subCountfunctions; jfunctions++){
						subListfunctions[jfunctions] = readInteger();
			}
	}
							objpredictInfos.setX(readInteger());
							objpredictInfos.setY(readInteger());
				}
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
		isLastPage = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pageIndex);
		writeInteger(canActivateNum);
		writeInteger(minLevel);
		writeInteger(maxLevel);
	writeShort(predictInfos.length);
	for(int i=0; i<predictInfos.length; i++){
	com.hifun.soul.gameserver.predict.msg.info.PredictInfo objpredictInfos = predictInfos[i];
				writeInteger(objpredictInfos.getPredictId());
				writeInteger(objpredictInfos.getActivateNeedLevel());
				writeBoolean(objpredictInfos.getActivated());
					int[] functions_objpredictInfos=objpredictInfos.getFunctions();
	writeShort(functions_objpredictInfos.length);
	for(int jfunctions=0; jfunctions<functions_objpredictInfos.length; jfunctions++){
					writeInteger(functions_objpredictInfos[jfunctions]);
			}
				writeInteger(objpredictInfos.getX());
				writeInteger(objpredictInfos.getY());
	}
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
		writeBoolean(isLastPage);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_PREDICT_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_PREDICT_PANEL";
	}

	public int getPageIndex(){
		return pageIndex;
	}
		
	public void setPageIndex(int pageIndex){
		this.pageIndex = pageIndex;
	}

	public int getCanActivateNum(){
		return canActivateNum;
	}
		
	public void setCanActivateNum(int canActivateNum){
		this.canActivateNum = canActivateNum;
	}

	public int getMinLevel(){
		return minLevel;
	}
		
	public void setMinLevel(int minLevel){
		this.minLevel = minLevel;
	}

	public int getMaxLevel(){
		return maxLevel;
	}
		
	public void setMaxLevel(int maxLevel){
		this.maxLevel = maxLevel;
	}

	public com.hifun.soul.gameserver.predict.msg.info.PredictInfo[] getPredictInfos(){
		return predictInfos;
	}

	public void setPredictInfos(com.hifun.soul.gameserver.predict.msg.info.PredictInfo[] predictInfos){
		this.predictInfos = predictInfos;
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

	public boolean getIsLastPage(){
		return isLastPage;
	}
		
	public void setIsLastPage(boolean isLastPage){
		this.isLastPage = isLastPage;
	}
}