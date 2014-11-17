package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应军团祈福
 *
 * @author SevenSoul
 */
@Component
public class GCEscortLegionPray extends GCMessage{
	
	/** 军团祈福信息 */
	private com.hifun.soul.gameserver.escort.info.LegionPrayInfo prayInfo;

	public GCEscortLegionPray (){
	}
	
	public GCEscortLegionPray (
			com.hifun.soul.gameserver.escort.info.LegionPrayInfo prayInfo ){
			this.prayInfo = prayInfo;
	}

	@Override
	protected boolean readImpl() {
		prayInfo = new com.hifun.soul.gameserver.escort.info.LegionPrayInfo();
						prayInfo.setPrayMemberName(readString());
						prayInfo.setPrayRemainTime(readInteger());
						prayInfo.setPrayRevenue(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(prayInfo.getPrayMemberName());
		writeInteger(prayInfo.getPrayRemainTime());
		writeInteger(prayInfo.getPrayRevenue());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ESCORT_LEGION_PRAY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ESCORT_LEGION_PRAY";
	}

	public com.hifun.soul.gameserver.escort.info.LegionPrayInfo getPrayInfo(){
		return prayInfo;
	}
		
	public void setPrayInfo(com.hifun.soul.gameserver.escort.info.LegionPrayInfo prayInfo){
		this.prayInfo = prayInfo;
	}
}