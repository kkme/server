package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应使用个人buf
 *
 * @author SevenSoul
 */
@Component
public class GCUseSelfBuf extends GCMessage{
	
	/** 个人buf信息 */
	private com.hifun.soul.gameserver.legionmine.SelfBuf[] selfBufs;

	public GCUseSelfBuf (){
	}
	
	public GCUseSelfBuf (
			com.hifun.soul.gameserver.legionmine.SelfBuf[] selfBufs ){
			this.selfBufs = selfBufs;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		selfBufs = new com.hifun.soul.gameserver.legionmine.SelfBuf[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legionmine.SelfBuf objselfBufs = new com.hifun.soul.gameserver.legionmine.SelfBuf();
			selfBufs[i] = objselfBufs;
					objselfBufs.setSelfBufType(readInteger());
							objselfBufs.setBufIcon(readInteger());
							objselfBufs.setBufDesc(readString());
							objselfBufs.setUsing(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(selfBufs.length);
	for(int i=0; i<selfBufs.length; i++){
	com.hifun.soul.gameserver.legionmine.SelfBuf objselfBufs = selfBufs[i];
				writeInteger(objselfBufs.getSelfBufType());
				writeInteger(objselfBufs.getBufIcon());
				writeString(objselfBufs.getBufDesc());
				writeBoolean(objselfBufs.getUsing());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_USE_SELF_BUF;
	}
	
	@Override
	public String getTypeName() {
		return "GC_USE_SELF_BUF";
	}

	public com.hifun.soul.gameserver.legionmine.SelfBuf[] getSelfBufs(){
		return selfBufs;
	}

	public void setSelfBufs(com.hifun.soul.gameserver.legionmine.SelfBuf[] selfBufs){
		this.selfBufs = selfBufs;
	}	
}