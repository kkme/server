package com.hifun.soul.gameserver.cd.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * cd加速
 *
 * @author SevenSoul
 */
@Component
public class GCCdSpeedUp extends GCMessage{
	
	/** cd类型 */
	private int cdType;
	/** 货币类型 */
	private short currencyType;
	/** 货币数量 */
	private int currencyNum;

	public GCCdSpeedUp (){
	}
	
	public GCCdSpeedUp (
			int cdType,
			short currencyType,
			int currencyNum ){
			this.cdType = cdType;
			this.currencyType = currencyType;
			this.currencyNum = currencyNum;
	}

	@Override
	protected boolean readImpl() {
		cdType = readInteger();
		currencyType = readShort();
		currencyNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(cdType);
		writeShort(currencyType);
		writeInteger(currencyNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CD_SPEED_UP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CD_SPEED_UP";
	}

	public int getCdType(){
		return cdType;
	}
		
	public void setCdType(int cdType){
		this.cdType = cdType;
	}

	public short getCurrencyType(){
		return currencyType;
	}
		
	public void setCurrencyType(short currencyType){
		this.currencyType = currencyType;
	}

	public int getCurrencyNum(){
		return currencyNum;
	}
		
	public void setCurrencyNum(int currencyNum){
		this.currencyNum = currencyNum;
	}
}