package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应军团冥想
 *
 * @author SevenSoul
 */
@Component
public class GCLegionMeditation extends GCMessage{
	
	/** 是否已冥想 */
	private boolean isMeditationed;
	/** 军团冥想日志信息 */
	private String[] meditationLogs;

	public GCLegionMeditation (){
	}
	
	public GCLegionMeditation (
			boolean isMeditationed,
			String[] meditationLogs ){
			this.isMeditationed = isMeditationed;
			this.meditationLogs = meditationLogs;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		isMeditationed = readBoolean();
		count = readShort();
		count = count < 0 ? 0 : count;
		meditationLogs = new String[count];
		for(int i=0; i<count; i++){
			meditationLogs[i] = readString();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(isMeditationed);
	writeShort(meditationLogs.length);
	for(int i=0; i<meditationLogs.length; i++){
	String objmeditationLogs = meditationLogs[i];
			writeString(objmeditationLogs);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LEGION_MEDITATION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LEGION_MEDITATION";
	}

	public boolean getIsMeditationed(){
		return isMeditationed;
	}
		
	public void setIsMeditationed(boolean isMeditationed){
		this.isMeditationed = isMeditationed;
	}

	public String[] getMeditationLogs(){
		return meditationLogs;
	}

	public void setMeditationLogs(String[] meditationLogs){
		this.meditationLogs = meditationLogs;
	}	
}