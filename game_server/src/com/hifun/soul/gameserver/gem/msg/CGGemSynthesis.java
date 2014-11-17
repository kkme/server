package com.hifun.soul.gameserver.gem.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 宝石合成
 * 
 * @author SevenSoul
 */
@Component
public class CGGemSynthesis extends CGMessage{
	
	/** 宝石所在背包位置 */
	private int gemItemBagIndex;
	/** 守护石id(-1表示未使用) */
	private int guardStoneId;
	/** 星运石id(-1表示未使用) */
	private int fortuneStoneId;
	
	public CGGemSynthesis (){
	}
	
	public CGGemSynthesis (
			int gemItemBagIndex,
			int guardStoneId,
			int fortuneStoneId ){
			this.gemItemBagIndex = gemItemBagIndex;
			this.guardStoneId = guardStoneId;
			this.fortuneStoneId = fortuneStoneId;
	}
	
	@Override
	protected boolean readImpl() {
		gemItemBagIndex = readInteger();
		guardStoneId = readInteger();
		fortuneStoneId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(gemItemBagIndex);
		writeInteger(guardStoneId);
		writeInteger(fortuneStoneId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GEM_SYNTHESIS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GEM_SYNTHESIS";
	}

	public int getGemItemBagIndex(){
		return gemItemBagIndex;
	}
		
	public void setGemItemBagIndex(int gemItemBagIndex){
		this.gemItemBagIndex = gemItemBagIndex;
	}

	public int getGuardStoneId(){
		return guardStoneId;
	}
		
	public void setGuardStoneId(int guardStoneId){
		this.guardStoneId = guardStoneId;
	}

	public int getFortuneStoneId(){
		return fortuneStoneId;
	}
		
	public void setFortuneStoneId(int fortuneStoneId){
		this.fortuneStoneId = fortuneStoneId;
	}

	@Override
	public void execute() {
	}
}