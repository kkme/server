package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新占星师状态
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateStargazers extends GCMessage{
	
	/** 占星师状态 */
	private com.hifun.soul.gameserver.horoscope.StargazerInfo[] stargazerInfos;

	public GCUpdateStargazers (){
	}
	
	public GCUpdateStargazers (
			com.hifun.soul.gameserver.horoscope.StargazerInfo[] stargazerInfos ){
			this.stargazerInfos = stargazerInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		stargazerInfos = new com.hifun.soul.gameserver.horoscope.StargazerInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.horoscope.StargazerInfo objstargazerInfos = new com.hifun.soul.gameserver.horoscope.StargazerInfo();
			stargazerInfos[i] = objstargazerInfos;
					objstargazerInfos.setStargazerId(readInteger());
							objstargazerInfos.setName(readString());
							objstargazerInfos.setDesc(readString());
							objstargazerInfos.setIcon(readInteger());
							objstargazerInfos.setCostCurrencyType(readShort());
							objstargazerInfos.setCostCurrencyNum(readInteger());
							objstargazerInfos.setOpen(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(stargazerInfos.length);
	for(int i=0; i<stargazerInfos.length; i++){
	com.hifun.soul.gameserver.horoscope.StargazerInfo objstargazerInfos = stargazerInfos[i];
				writeInteger(objstargazerInfos.getStargazerId());
				writeString(objstargazerInfos.getName());
				writeString(objstargazerInfos.getDesc());
				writeInteger(objstargazerInfos.getIcon());
				writeShort(objstargazerInfos.getCostCurrencyType());
				writeInteger(objstargazerInfos.getCostCurrencyNum());
				writeBoolean(objstargazerInfos.getOpen());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_STARGAZERS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_STARGAZERS";
	}

	public com.hifun.soul.gameserver.horoscope.StargazerInfo[] getStargazerInfos(){
		return stargazerInfos;
	}

	public void setStargazerInfos(com.hifun.soul.gameserver.horoscope.StargazerInfo[] stargazerInfos){
		this.stargazerInfos = stargazerInfos;
	}	
}