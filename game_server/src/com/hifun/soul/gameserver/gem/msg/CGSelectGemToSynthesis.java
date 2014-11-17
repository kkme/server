package com.hifun.soul.gameserver.gem.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 选择需要合成的宝石
 * 
 * @author SevenSoul
 */
@Component
public class CGSelectGemToSynthesis extends CGMessage{
	
	/** 主宝石所在背包类型 */
	private int bagType;
	/** 主宝石所在背包索引 */
	private int mainGemBagIndex;
	
	public CGSelectGemToSynthesis (){
	}
	
	public CGSelectGemToSynthesis (
			int bagType,
			int mainGemBagIndex ){
			this.bagType = bagType;
			this.mainGemBagIndex = mainGemBagIndex;
	}
	
	@Override
	protected boolean readImpl() {
		bagType = readInteger();
		mainGemBagIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bagType);
		writeInteger(mainGemBagIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SELECT_GEM_TO_SYNTHESIS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SELECT_GEM_TO_SYNTHESIS";
	}

	public int getBagType(){
		return bagType;
	}
		
	public void setBagType(int bagType){
		this.bagType = bagType;
	}

	public int getMainGemBagIndex(){
		return mainGemBagIndex;
	}
		
	public void setMainGemBagIndex(int mainGemBagIndex){
		this.mainGemBagIndex = mainGemBagIndex;
	}

	@Override
	public void execute() {
	}
}