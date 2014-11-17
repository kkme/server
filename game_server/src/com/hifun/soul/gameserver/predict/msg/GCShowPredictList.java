package com.hifun.soul.gameserver.predict.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示预见列表
 *
 * @author SevenSoul
 */
@Component
public class GCShowPredictList extends GCMessage{
	
	/** 页码 */
	private int pageIndex;
	/** 等级下限 */
	private int minLevel;
	/** 等级上限 */
	private int maxLevel;
	/** 预见列表 */
	private com.hifun.soul.gameserver.predict.msg.info.PredictInfo[] predictInfos;
	/** 是否是最后一页 */
	private boolean isLastPage;

	public GCShowPredictList (){
	}
	
	public GCShowPredictList (
			int pageIndex,
			int minLevel,
			int maxLevel,
			com.hifun.soul.gameserver.predict.msg.info.PredictInfo[] predictInfos,
			boolean isLastPage ){
			this.pageIndex = pageIndex;
			this.minLevel = minLevel;
			this.maxLevel = maxLevel;
			this.predictInfos = predictInfos;
			this.isLastPage = isLastPage;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		pageIndex = readInteger();
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
		isLastPage = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pageIndex);
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
		writeBoolean(isLastPage);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_PREDICT_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_PREDICT_LIST";
	}

	public int getPageIndex(){
		return pageIndex;
	}
		
	public void setPageIndex(int pageIndex){
		this.pageIndex = pageIndex;
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

	public boolean getIsLastPage(){
		return isLastPage;
	}
		
	public void setIsLastPage(boolean isLastPage){
		this.isLastPage = isLastPage;
	}
}