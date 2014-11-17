package com.hifun.soul.gameserver.cd.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回cd列表
 *
 * @author SevenSoul
 */
@Component
public class GCCdQueueInfos extends GCMessage{
	
	/** cd列表 */
	private com.hifun.soul.gameserver.cd.msg.CdQueueInfo[] cds;

	public GCCdQueueInfos (){
	}
	
	public GCCdQueueInfos (
			com.hifun.soul.gameserver.cd.msg.CdQueueInfo[] cds ){
			this.cds = cds;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		cds = new com.hifun.soul.gameserver.cd.msg.CdQueueInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.cd.msg.CdQueueInfo objcds = new com.hifun.soul.gameserver.cd.msg.CdQueueInfo();
			cds[i] = objcds;
					objcds.setCdType(readInteger());
							objcds.setTimeDiff(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(cds.length);
	for(int i=0; i<cds.length; i++){
	com.hifun.soul.gameserver.cd.msg.CdQueueInfo objcds = cds[i];
				writeInteger(objcds.getCdType());
				writeInteger(objcds.getTimeDiff());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CD_QUEUE_INFOS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CD_QUEUE_INFOS";
	}

	public com.hifun.soul.gameserver.cd.msg.CdQueueInfo[] getCds(){
		return cds;
	}

	public void setCds(com.hifun.soul.gameserver.cd.msg.CdQueueInfo[] cds){
		this.cds = cds;
	}	
}