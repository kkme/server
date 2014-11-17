package com.hifun.soul.gameserver.foster.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回培养所获得的数值
 *
 * @author SevenSoul
 */
@Component
public class GCFoster extends GCMessage{
	
	/** 培养获得的属性值 */
	private int[] fosterAttributeNum;

	public GCFoster (){
	}
	
	public GCFoster (
			int[] fosterAttributeNum ){
			this.fosterAttributeNum = fosterAttributeNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		fosterAttributeNum = new int[count];
		for(int i=0; i<count; i++){
			fosterAttributeNum[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(fosterAttributeNum.length);
	for(int i=0; i<fosterAttributeNum.length; i++){
	Integer objfosterAttributeNum = fosterAttributeNum[i];
			writeInteger(objfosterAttributeNum);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FOSTER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FOSTER";
	}

	public int[] getFosterAttributeNum(){
		return fosterAttributeNum;
	}

	public void setFosterAttributeNum(int[] fosterAttributeNum){
		this.fosterAttributeNum = fosterAttributeNum;
	}	
}