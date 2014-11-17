package com.hifun.soul.gameserver.cd.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 单个cd信息
 *
 * @author SevenSoul
 */
@Component
public class GCCdQueueInfo extends GCMessage{
	
	/** cd列表 */
	private com.hifun.soul.gameserver.cd.msg.CdQueueInfo cd;

	public GCCdQueueInfo (){
	}
	
	public GCCdQueueInfo (
			com.hifun.soul.gameserver.cd.msg.CdQueueInfo cd ){
			this.cd = cd;
	}

	@Override
	protected boolean readImpl() {
		cd = new com.hifun.soul.gameserver.cd.msg.CdQueueInfo();
						cd.setCdType(readInteger());
						cd.setTimeDiff(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(cd.getCdType());
		writeInteger(cd.getTimeDiff());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CD_QUEUE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CD_QUEUE_INFO";
	}

	public com.hifun.soul.gameserver.cd.msg.CdQueueInfo getCd(){
		return cd;
	}
		
	public void setCd(com.hifun.soul.gameserver.cd.msg.CdQueueInfo cd){
		this.cd = cd;
	}
}