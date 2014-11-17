package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 星运移动
 * 
 * @author SevenSoul
 */
@Component
public class CGHoroscopeSwap extends CGMessage{
	
	/** from背包类型 */
	private int fromBagType;
	/** from背包位置 */
	private int fromBagIndex;
	/** to背包类型 */
	private int toBagType;
	/** to背包位置 */
	private int toBagIndex;
	
	public CGHoroscopeSwap (){
	}
	
	public CGHoroscopeSwap (
			int fromBagType,
			int fromBagIndex,
			int toBagType,
			int toBagIndex ){
			this.fromBagType = fromBagType;
			this.fromBagIndex = fromBagIndex;
			this.toBagType = toBagType;
			this.toBagIndex = toBagIndex;
	}
	
	@Override
	protected boolean readImpl() {
		fromBagType = readInteger();
		fromBagIndex = readInteger();
		toBagType = readInteger();
		toBagIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(fromBagType);
		writeInteger(fromBagIndex);
		writeInteger(toBagType);
		writeInteger(toBagIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_HOROSCOPE_SWAP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HOROSCOPE_SWAP";
	}

	public int getFromBagType(){
		return fromBagType;
	}
		
	public void setFromBagType(int fromBagType){
		this.fromBagType = fromBagType;
	}

	public int getFromBagIndex(){
		return fromBagIndex;
	}
		
	public void setFromBagIndex(int fromBagIndex){
		this.fromBagIndex = fromBagIndex;
	}

	public int getToBagType(){
		return toBagType;
	}
		
	public void setToBagType(int toBagType){
		this.toBagType = toBagType;
	}

	public int getToBagIndex(){
		return toBagIndex;
	}
		
	public void setToBagIndex(int toBagIndex){
		this.toBagIndex = toBagIndex;
	}

	@Override
	public void execute() {
	}
}