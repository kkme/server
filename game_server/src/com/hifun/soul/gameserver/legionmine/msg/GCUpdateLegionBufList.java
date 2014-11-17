package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 通知客户端更新军团buf列表 
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateLegionBufList extends GCMessage{
	
	/** 军团buf信息 */
	private com.hifun.soul.gameserver.legionmine.LegionBuf[] legionBufs;

	public GCUpdateLegionBufList (){
	}
	
	public GCUpdateLegionBufList (
			com.hifun.soul.gameserver.legionmine.LegionBuf[] legionBufs ){
			this.legionBufs = legionBufs;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		legionBufs = new com.hifun.soul.gameserver.legionmine.LegionBuf[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legionmine.LegionBuf objlegionBufs = new com.hifun.soul.gameserver.legionmine.LegionBuf();
			legionBufs[i] = objlegionBufs;
					objlegionBufs.setLegionBufType(readInteger());
							objlegionBufs.setBufIcon(readInteger());
							objlegionBufs.setBufDesc(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(legionBufs.length);
	for(int i=0; i<legionBufs.length; i++){
	com.hifun.soul.gameserver.legionmine.LegionBuf objlegionBufs = legionBufs[i];
				writeInteger(objlegionBufs.getLegionBufType());
				writeInteger(objlegionBufs.getBufIcon());
				writeString(objlegionBufs.getBufDesc());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_LEGION_BUF_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_LEGION_BUF_LIST";
	}

	public com.hifun.soul.gameserver.legionmine.LegionBuf[] getLegionBufs(){
		return legionBufs;
	}

	public void setLegionBufs(com.hifun.soul.gameserver.legionmine.LegionBuf[] legionBufs){
		this.legionBufs = legionBufs;
	}	
}