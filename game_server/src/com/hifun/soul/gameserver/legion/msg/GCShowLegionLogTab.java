package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示军团日志标签页
 *
 * @author SevenSoul
 */
@Component
public class GCShowLegionLogTab extends GCMessage{
	
	/** 军团列表  */
	private com.hifun.soul.gameserver.legion.LegionLog[] legionLogs;

	public GCShowLegionLogTab (){
	}
	
	public GCShowLegionLogTab (
			com.hifun.soul.gameserver.legion.LegionLog[] legionLogs ){
			this.legionLogs = legionLogs;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		legionLogs = new com.hifun.soul.gameserver.legion.LegionLog[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.LegionLog objlegionLogs = new com.hifun.soul.gameserver.legion.LegionLog();
			legionLogs[i] = objlegionLogs;
					objlegionLogs.setContent(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(legionLogs.length);
	for(int i=0; i<legionLogs.length; i++){
	com.hifun.soul.gameserver.legion.LegionLog objlegionLogs = legionLogs[i];
				writeString(objlegionLogs.getContent());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LEGION_LOG_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LEGION_LOG_TAB";
	}

	public com.hifun.soul.gameserver.legion.LegionLog[] getLegionLogs(){
		return legionLogs;
	}

	public void setLegionLogs(com.hifun.soul.gameserver.legion.LegionLog[] legionLogs){
		this.legionLogs = legionLogs;
	}	
}