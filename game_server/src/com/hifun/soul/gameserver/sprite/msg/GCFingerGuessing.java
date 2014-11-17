package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 开始对酒响应
 *
 * @author SevenSoul
 */
@Component
public class GCFingerGuessing extends GCMessage{
	
	/** 当前的对酒精灵索引 */
	private int currentIndex;
	/** 选取精灵集合 */
	private com.hifun.soul.gameserver.sprite.model.SpritePubInfo[] selectedList;
	/** 是否是上次未完成的对酒 */
	private boolean isLastGuess;
	/** 必胜消耗的魔晶数量 */
	private int succeedCrystalCost;
	/** 剩余必胜次数 */
	private int remainSucceedNum;

	public GCFingerGuessing (){
	}
	
	public GCFingerGuessing (
			int currentIndex,
			com.hifun.soul.gameserver.sprite.model.SpritePubInfo[] selectedList,
			boolean isLastGuess,
			int succeedCrystalCost,
			int remainSucceedNum ){
			this.currentIndex = currentIndex;
			this.selectedList = selectedList;
			this.isLastGuess = isLastGuess;
			this.succeedCrystalCost = succeedCrystalCost;
			this.remainSucceedNum = remainSucceedNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		currentIndex = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		selectedList = new com.hifun.soul.gameserver.sprite.model.SpritePubInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.sprite.model.SpritePubInfo objselectedList = new com.hifun.soul.gameserver.sprite.model.SpritePubInfo();
			selectedList[i] = objselectedList;
					objselectedList.setSpriteId(readInteger());
							objselectedList.setIconId(readInteger());
							objselectedList.setName(readString());
							objselectedList.setQuality(readInteger());
							objselectedList.setSoulType(readInteger());
							objselectedList.setSoulNum(readInteger());
							objselectedList.setWin(readBoolean());
							objselectedList.setPropId(readInteger());
							objselectedList.setPropValue(readInteger());
				}
		isLastGuess = readBoolean();
		succeedCrystalCost = readInteger();
		remainSucceedNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(currentIndex);
	writeShort(selectedList.length);
	for(int i=0; i<selectedList.length; i++){
	com.hifun.soul.gameserver.sprite.model.SpritePubInfo objselectedList = selectedList[i];
				writeInteger(objselectedList.getSpriteId());
				writeInteger(objselectedList.getIconId());
				writeString(objselectedList.getName());
				writeInteger(objselectedList.getQuality());
				writeInteger(objselectedList.getSoulType());
				writeInteger(objselectedList.getSoulNum());
				writeBoolean(objselectedList.getWin());
				writeInteger(objselectedList.getPropId());
				writeInteger(objselectedList.getPropValue());
	}
		writeBoolean(isLastGuess);
		writeInteger(succeedCrystalCost);
		writeInteger(remainSucceedNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FINGER_GUESSING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FINGER_GUESSING";
	}

	public int getCurrentIndex(){
		return currentIndex;
	}
		
	public void setCurrentIndex(int currentIndex){
		this.currentIndex = currentIndex;
	}

	public com.hifun.soul.gameserver.sprite.model.SpritePubInfo[] getSelectedList(){
		return selectedList;
	}

	public void setSelectedList(com.hifun.soul.gameserver.sprite.model.SpritePubInfo[] selectedList){
		this.selectedList = selectedList;
	}	

	public boolean getIsLastGuess(){
		return isLastGuess;
	}
		
	public void setIsLastGuess(boolean isLastGuess){
		this.isLastGuess = isLastGuess;
	}

	public int getSucceedCrystalCost(){
		return succeedCrystalCost;
	}
		
	public void setSucceedCrystalCost(int succeedCrystalCost){
		this.succeedCrystalCost = succeedCrystalCost;
	}

	public int getRemainSucceedNum(){
		return remainSucceedNum;
	}
		
	public void setRemainSucceedNum(int remainSucceedNum){
		this.remainSucceedNum = remainSucceedNum;
	}
}