package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开军团面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenLegionPanel extends GCMessage{
	
	/** 军团列表  */
	private com.hifun.soul.gameserver.legion.msg.LegionListInfo[] legions;
	/** 能否编辑公告 */
	private boolean canEditNotice;
	/** 所在军团信息  */
	private com.hifun.soul.gameserver.legion.Legion ownLegion;
	/** 军团成员列表  */
	private com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo[] legionMembers;
	/** 创建军团需要金币 */
	private int createLegionNeedCoin;
	/** 创建军团需要等级 */
	private int createLegionNeedLevel;
	/** 军团功能开启列表  */
	private com.hifun.soul.gameserver.legion.info.LegionFuncInfo[] funcInfos;
	/** 捐赠需要VIP等级 */
	private int donateNeedVip;

	public GCOpenLegionPanel (){
	}
	
	public GCOpenLegionPanel (
			com.hifun.soul.gameserver.legion.msg.LegionListInfo[] legions,
			boolean canEditNotice,
			com.hifun.soul.gameserver.legion.Legion ownLegion,
			com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo[] legionMembers,
			int createLegionNeedCoin,
			int createLegionNeedLevel,
			com.hifun.soul.gameserver.legion.info.LegionFuncInfo[] funcInfos,
			int donateNeedVip ){
			this.legions = legions;
			this.canEditNotice = canEditNotice;
			this.ownLegion = ownLegion;
			this.legionMembers = legionMembers;
			this.createLegionNeedCoin = createLegionNeedCoin;
			this.createLegionNeedLevel = createLegionNeedLevel;
			this.funcInfos = funcInfos;
			this.donateNeedVip = donateNeedVip;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		legions = new com.hifun.soul.gameserver.legion.msg.LegionListInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.msg.LegionListInfo objlegions = new com.hifun.soul.gameserver.legion.msg.LegionListInfo();
			legions[i] = objlegions;
					objlegions.setLegionId(readLong());
							objlegions.setLegionName(readString());
							objlegions.setCommanderName(readString());
							objlegions.setLegionLevel(readInteger());
							objlegions.setMemberLimit(readInteger());
							objlegions.setMemberNum(readInteger());
							objlegions.setApplyButtonStatus(readInteger());
				}
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
				count = readShort();
		count = count < 0 ? 0 : count;
		legionMembers = new com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo objlegionMembers = new com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo();
			legionMembers[i] = objlegionMembers;
					objlegionMembers.setMemberId(readLong());
							objlegionMembers.setMemberName(readString());
							objlegionMembers.setLevel(readInteger());
							objlegionMembers.setArenaRank(readInteger());
							objlegionMembers.setPositionName(readString());
							objlegionMembers.setTodayContribution(readInteger());
							objlegionMembers.setTotalContribution(readInteger());
							objlegionMembers.setOffLineTimeInterval(readInteger());
							objlegionMembers.setTransferButtonVisible(readBoolean());
							objlegionMembers.setRemoveButtonVisible(readBoolean());
				}
		createLegionNeedCoin = readInteger();
		createLegionNeedLevel = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		funcInfos = new com.hifun.soul.gameserver.legion.info.LegionFuncInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.info.LegionFuncInfo objfuncInfos = new com.hifun.soul.gameserver.legion.info.LegionFuncInfo();
			funcInfos[i] = objfuncInfos;
					objfuncInfos.setBuildingType(readInteger());
							objfuncInfos.setOpenLegionLevel(readInteger());
				}
		donateNeedVip = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(legions.length);
	for(int i=0; i<legions.length; i++){
	com.hifun.soul.gameserver.legion.msg.LegionListInfo objlegions = legions[i];
				writeLong(objlegions.getLegionId());
				writeString(objlegions.getLegionName());
				writeString(objlegions.getCommanderName());
				writeInteger(objlegions.getLegionLevel());
				writeInteger(objlegions.getMemberLimit());
				writeInteger(objlegions.getMemberNum());
				writeInteger(objlegions.getApplyButtonStatus());
	}
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
	writeShort(legionMembers.length);
	for(int i=0; i<legionMembers.length; i++){
	com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo objlegionMembers = legionMembers[i];
				writeLong(objlegionMembers.getMemberId());
				writeString(objlegionMembers.getMemberName());
				writeInteger(objlegionMembers.getLevel());
				writeInteger(objlegionMembers.getArenaRank());
				writeString(objlegionMembers.getPositionName());
				writeInteger(objlegionMembers.getTodayContribution());
				writeInteger(objlegionMembers.getTotalContribution());
				writeInteger(objlegionMembers.getOffLineTimeInterval());
				writeBoolean(objlegionMembers.getTransferButtonVisible());
				writeBoolean(objlegionMembers.getRemoveButtonVisible());
	}
		writeInteger(createLegionNeedCoin);
		writeInteger(createLegionNeedLevel);
	writeShort(funcInfos.length);
	for(int i=0; i<funcInfos.length; i++){
	com.hifun.soul.gameserver.legion.info.LegionFuncInfo objfuncInfos = funcInfos[i];
				writeInteger(objfuncInfos.getBuildingType());
				writeInteger(objfuncInfos.getOpenLegionLevel());
	}
		writeInteger(donateNeedVip);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_LEGION_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_LEGION_PANEL";
	}

	public com.hifun.soul.gameserver.legion.msg.LegionListInfo[] getLegions(){
		return legions;
	}

	public void setLegions(com.hifun.soul.gameserver.legion.msg.LegionListInfo[] legions){
		this.legions = legions;
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

	public com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo[] getLegionMembers(){
		return legionMembers;
	}

	public void setLegionMembers(com.hifun.soul.gameserver.legion.msg.LegionMemberListInfo[] legionMembers){
		this.legionMembers = legionMembers;
	}	

	public int getCreateLegionNeedCoin(){
		return createLegionNeedCoin;
	}
		
	public void setCreateLegionNeedCoin(int createLegionNeedCoin){
		this.createLegionNeedCoin = createLegionNeedCoin;
	}

	public int getCreateLegionNeedLevel(){
		return createLegionNeedLevel;
	}
		
	public void setCreateLegionNeedLevel(int createLegionNeedLevel){
		this.createLegionNeedLevel = createLegionNeedLevel;
	}

	public com.hifun.soul.gameserver.legion.info.LegionFuncInfo[] getFuncInfos(){
		return funcInfos;
	}

	public void setFuncInfos(com.hifun.soul.gameserver.legion.info.LegionFuncInfo[] funcInfos){
		this.funcInfos = funcInfos;
	}	

	public int getDonateNeedVip(){
		return donateNeedVip;
	}
		
	public void setDonateNeedVip(int donateNeedVip){
		this.donateNeedVip = donateNeedVip;
	}
}