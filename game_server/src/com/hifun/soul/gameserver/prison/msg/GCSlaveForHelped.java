package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 给被求救的角色发求救消息
 *
 * @author SevenSoul
 */
@Component
public class GCSlaveForHelped extends GCMessage{
	
	/** 需要解救者信息  */
	private com.hifun.soul.gameserver.prison.msg.PrisonerInfo toRescuer;

	public GCSlaveForHelped (){
	}
	
	public GCSlaveForHelped (
			com.hifun.soul.gameserver.prison.msg.PrisonerInfo toRescuer ){
			this.toRescuer = toRescuer;
	}

	@Override
	protected boolean readImpl() {
		toRescuer = new com.hifun.soul.gameserver.prison.msg.PrisonerInfo();
						toRescuer.setHumanId(readLong());
						toRescuer.setHumanName(readString());
						toRescuer.setHumanLevel(readInteger());
						toRescuer.setIdentityType(readInteger());
						toRescuer.setLegionId(readLong());
						toRescuer.setLegionName(readString());
						toRescuer.setOccupationType(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(toRescuer.getHumanId());
		writeString(toRescuer.getHumanName());
		writeInteger(toRescuer.getHumanLevel());
		writeInteger(toRescuer.getIdentityType());
		writeLong(toRescuer.getLegionId());
		writeString(toRescuer.getLegionName());
		writeInteger(toRescuer.getOccupationType());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLAVE_FOR_HELPED;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLAVE_FOR_HELPED";
	}

	public com.hifun.soul.gameserver.prison.msg.PrisonerInfo getToRescuer(){
		return toRescuer;
	}
		
	public void setToRescuer(com.hifun.soul.gameserver.prison.msg.PrisonerInfo toRescuer){
		this.toRescuer = toRescuer;
	}
}