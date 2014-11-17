package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示所在军团信息标签页
 *
 * @author SevenSoul
 */
@Component
public class GCShowLegionInfoTab extends GCMessage{
	
	/** 能否编辑公告 */
	private boolean canEditNotice;
	/** 所在军团信息  */
	private com.hifun.soul.gameserver.legion.Legion ownLegion;

	public GCShowLegionInfoTab (){
	}
	
	public GCShowLegionInfoTab (
			boolean canEditNotice,
			com.hifun.soul.gameserver.legion.Legion ownLegion ){
			this.canEditNotice = canEditNotice;
			this.ownLegion = ownLegion;
	}

	@Override
	protected boolean readImpl() {
		canEditNotice = readBoolean();
		ownLegion = new com.hifun.soul.gameserver.legion.Legion();
						ownLegion.setLegionName(readString());
						ownLegion.setCommanderName(readString());
						ownLegion.setNotice(readString());
						ownLegion.setLegionLevel(readInteger());
						ownLegion.setMemberLimit(readInteger());
						ownLegion.setMemberNum(readInteger());
						ownLegion.setExperience(readInteger());
						ownLegion.setLevelMaxExperience(readInteger());
						ownLegion.setDonateGetLegionExp(readInteger());
						ownLegion.setDonateGetSelfContri(readInteger());
						ownLegion.setDonateGetMedal(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(canEditNotice);
		writeString(ownLegion.getLegionName());
		writeString(ownLegion.getCommanderName());
		writeString(ownLegion.getNotice());
		writeInteger(ownLegion.getLegionLevel());
		writeInteger(ownLegion.getMemberLimit());
		writeInteger(ownLegion.getMemberNum());
		writeInteger(ownLegion.getExperience());
		writeInteger(ownLegion.getLevelMaxExperience());
		writeInteger(ownLegion.getDonateGetLegionExp());
		writeInteger(ownLegion.getDonateGetSelfContri());
		writeInteger(ownLegion.getDonateGetMedal());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LEGION_INFO_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LEGION_INFO_TAB";
	}

	public boolean getCanEditNotice(){
		return canEditNotice;
	}
		
	public void setCanEditNotice(boolean canEditNotice){
		this.canEditNotice = canEditNotice;
	}

	public com.hifun.soul.gameserver.legion.Legion getOwnLegion(){
		return ownLegion;
	}
		
	public void setOwnLegion(com.hifun.soul.gameserver.legion.Legion ownLegion){
		this.ownLegion = ownLegion;
	}
}