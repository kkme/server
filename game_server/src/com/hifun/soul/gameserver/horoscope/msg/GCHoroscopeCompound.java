package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 星运合成确认框
 *
 * @author SevenSoul
 */
@Component
public class GCHoroscopeCompound extends GCMessage{
	
	/** from背包类型 */
	private int fromBagType;
	/** from背包位置 */
	private int fromBagIndex;
	/** to背包类型 */
	private int toBagType;
	/** to背包位置 */
	private int toBagIndex;
	/** 合成描述 */
	private String desc;

	public GCHoroscopeCompound (){
	}
	
	public GCHoroscopeCompound (
			int fromBagType,
			int fromBagIndex,
			int toBagType,
			int toBagIndex,
			String desc ){
			this.fromBagType = fromBagType;
			this.fromBagIndex = fromBagIndex;
			this.toBagType = toBagType;
			this.toBagIndex = toBagIndex;
			this.desc = desc;
	}

	@Override
	protected boolean readImpl() {
		fromBagType = readInteger();
		fromBagIndex = readInteger();
		toBagType = readInteger();
		toBagIndex = readInteger();
		desc = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(fromBagType);
		writeInteger(fromBagIndex);
		writeInteger(toBagType);
		writeInteger(toBagIndex);
		writeString(desc);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HOROSCOPE_COMPOUND;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HOROSCOPE_COMPOUND";
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

	public String getDesc(){
		return desc;
	}
		
	public void setDesc(String desc){
		this.desc = desc;
	}
}