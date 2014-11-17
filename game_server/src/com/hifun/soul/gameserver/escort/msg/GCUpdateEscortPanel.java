package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 通知客户端更新押运面板
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateEscortPanel extends GCMessage{
	
	/** 剩余押运次数 */
	private int remainEscortNum;
	/** 剩余抢劫次数 */
	private int remainRobNum;
	/** 购买拦截次数消费 */
	private int buyRobNumCost;
	/** 剩余协助护送次数 */
	private int remainHelpNum;
	/** 是否有押运奖励 */
	private boolean hasEscortReward;
	/** 是否有协助护送奖励 */
	private boolean hasHelpReward;
	/** 军团祈福信息 */
	private com.hifun.soul.gameserver.escort.info.LegionPrayInfo prayInfo;
	/** 押运信息 */
	private com.hifun.soul.gameserver.escort.info.EscortInfo[] escortInfos;
	/** 每页显示怪物数 */
	private int pageMonsterSize;
	/** 每页显示跑道数 */
	private int pageRoadNum;
	/** 拦截日志 */
	private String[] robLogs;
	/** 军团祈福魔晶消耗 */
	private int legionPrayCost;
	/** 押运收益加成信息 */
	private com.hifun.soul.gameserver.escort.info.EscortAmendInfo escortAmendInfo;

	public GCUpdateEscortPanel (){
	}
	
	public GCUpdateEscortPanel (
			int remainEscortNum,
			int remainRobNum,
			int buyRobNumCost,
			int remainHelpNum,
			boolean hasEscortReward,
			boolean hasHelpReward,
			com.hifun.soul.gameserver.escort.info.LegionPrayInfo prayInfo,
			com.hifun.soul.gameserver.escort.info.EscortInfo[] escortInfos,
			int pageMonsterSize,
			int pageRoadNum,
			String[] robLogs,
			int legionPrayCost,
			com.hifun.soul.gameserver.escort.info.EscortAmendInfo escortAmendInfo ){
			this.remainEscortNum = remainEscortNum;
			this.remainRobNum = remainRobNum;
			this.buyRobNumCost = buyRobNumCost;
			this.remainHelpNum = remainHelpNum;
			this.hasEscortReward = hasEscortReward;
			this.hasHelpReward = hasHelpReward;
			this.prayInfo = prayInfo;
			this.escortInfos = escortInfos;
			this.pageMonsterSize = pageMonsterSize;
			this.pageRoadNum = pageRoadNum;
			this.robLogs = robLogs;
			this.legionPrayCost = legionPrayCost;
			this.escortAmendInfo = escortAmendInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		remainEscortNum = readInteger();
		remainRobNum = readInteger();
		buyRobNumCost = readInteger();
		remainHelpNum = readInteger();
		hasEscortReward = readBoolean();
		hasHelpReward = readBoolean();
		prayInfo = new com.hifun.soul.gameserver.escort.info.LegionPrayInfo();
						prayInfo.setPrayMemberName(readString());
						prayInfo.setPrayRemainTime(readInteger());
						prayInfo.setPrayRevenue(readInteger());
				count = readShort();
		count = count < 0 ? 0 : count;
		escortInfos = new com.hifun.soul.gameserver.escort.info.EscortInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.escort.info.EscortInfo objescortInfos = new com.hifun.soul.gameserver.escort.info.EscortInfo();
			escortInfos[i] = objescortInfos;
					objescortInfos.setEscortId(readLong());
							objescortInfos.setMonsterModelId(readInteger());
							objescortInfos.setMonsterType(readInteger());
							objescortInfos.setOwnerId(readLong());
							objescortInfos.setOwnerName(readString());
							objescortInfos.setOwnerLevel(readInteger());
							objescortInfos.setOwnerLegionName(readString());
							objescortInfos.setHelperId(readLong());
							objescortInfos.setHelperName(readString());
							objescortInfos.setHelperLevel(readInteger());
							objescortInfos.setRemainBeRobbedNum(readInteger());
							objescortInfos.setMaxBeRobbedNum(readInteger());
							objescortInfos.setEscortRemainTime(readInteger());
							objescortInfos.setEncourageRate(readInteger());
							objescortInfos.setEscortRewardCoin(readInteger());
							objescortInfos.setHelpRewardCoin(readInteger());
							objescortInfos.setRobRewardCoin(readInteger());
							objescortInfos.setEscortTime(readInteger());
				}
		pageMonsterSize = readInteger();
		pageRoadNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		robLogs = new String[count];
		for(int i=0; i<count; i++){
			robLogs[i] = readString();
		}
		legionPrayCost = readInteger();
		escortAmendInfo = new com.hifun.soul.gameserver.escort.info.EscortAmendInfo();
						escortAmendInfo.setAmendDesc(readString());
						escortAmendInfo.setRemainTime(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainEscortNum);
		writeInteger(remainRobNum);
		writeInteger(buyRobNumCost);
		writeInteger(remainHelpNum);
		writeBoolean(hasEscortReward);
		writeBoolean(hasHelpReward);
		writeString(prayInfo.getPrayMemberName());
		writeInteger(prayInfo.getPrayRemainTime());
		writeInteger(prayInfo.getPrayRevenue());
	writeShort(escortInfos.length);
	for(int i=0; i<escortInfos.length; i++){
	com.hifun.soul.gameserver.escort.info.EscortInfo objescortInfos = escortInfos[i];
				writeLong(objescortInfos.getEscortId());
				writeInteger(objescortInfos.getMonsterModelId());
				writeInteger(objescortInfos.getMonsterType());
				writeLong(objescortInfos.getOwnerId());
				writeString(objescortInfos.getOwnerName());
				writeInteger(objescortInfos.getOwnerLevel());
				writeString(objescortInfos.getOwnerLegionName());
				writeLong(objescortInfos.getHelperId());
				writeString(objescortInfos.getHelperName());
				writeInteger(objescortInfos.getHelperLevel());
				writeInteger(objescortInfos.getRemainBeRobbedNum());
				writeInteger(objescortInfos.getMaxBeRobbedNum());
				writeInteger(objescortInfos.getEscortRemainTime());
				writeInteger(objescortInfos.getEncourageRate());
				writeInteger(objescortInfos.getEscortRewardCoin());
				writeInteger(objescortInfos.getHelpRewardCoin());
				writeInteger(objescortInfos.getRobRewardCoin());
				writeInteger(objescortInfos.getEscortTime());
	}
		writeInteger(pageMonsterSize);
		writeInteger(pageRoadNum);
	writeShort(robLogs.length);
	for(int i=0; i<robLogs.length; i++){
	String objrobLogs = robLogs[i];
			writeString(objrobLogs);
}
		writeInteger(legionPrayCost);
		writeString(escortAmendInfo.getAmendDesc());
		writeInteger(escortAmendInfo.getRemainTime());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_ESCORT_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_ESCORT_PANEL";
	}

	public int getRemainEscortNum(){
		return remainEscortNum;
	}
		
	public void setRemainEscortNum(int remainEscortNum){
		this.remainEscortNum = remainEscortNum;
	}

	public int getRemainRobNum(){
		return remainRobNum;
	}
		
	public void setRemainRobNum(int remainRobNum){
		this.remainRobNum = remainRobNum;
	}

	public int getBuyRobNumCost(){
		return buyRobNumCost;
	}
		
	public void setBuyRobNumCost(int buyRobNumCost){
		this.buyRobNumCost = buyRobNumCost;
	}

	public int getRemainHelpNum(){
		return remainHelpNum;
	}
		
	public void setRemainHelpNum(int remainHelpNum){
		this.remainHelpNum = remainHelpNum;
	}

	public boolean getHasEscortReward(){
		return hasEscortReward;
	}
		
	public void setHasEscortReward(boolean hasEscortReward){
		this.hasEscortReward = hasEscortReward;
	}

	public boolean getHasHelpReward(){
		return hasHelpReward;
	}
		
	public void setHasHelpReward(boolean hasHelpReward){
		this.hasHelpReward = hasHelpReward;
	}

	public com.hifun.soul.gameserver.escort.info.LegionPrayInfo getPrayInfo(){
		return prayInfo;
	}
		
	public void setPrayInfo(com.hifun.soul.gameserver.escort.info.LegionPrayInfo prayInfo){
		this.prayInfo = prayInfo;
	}

	public com.hifun.soul.gameserver.escort.info.EscortInfo[] getEscortInfos(){
		return escortInfos;
	}

	public void setEscortInfos(com.hifun.soul.gameserver.escort.info.EscortInfo[] escortInfos){
		this.escortInfos = escortInfos;
	}	

	public int getPageMonsterSize(){
		return pageMonsterSize;
	}
		
	public void setPageMonsterSize(int pageMonsterSize){
		this.pageMonsterSize = pageMonsterSize;
	}

	public int getPageRoadNum(){
		return pageRoadNum;
	}
		
	public void setPageRoadNum(int pageRoadNum){
		this.pageRoadNum = pageRoadNum;
	}

	public String[] getRobLogs(){
		return robLogs;
	}

	public void setRobLogs(String[] robLogs){
		this.robLogs = robLogs;
	}	

	public int getLegionPrayCost(){
		return legionPrayCost;
	}
		
	public void setLegionPrayCost(int legionPrayCost){
		this.legionPrayCost = legionPrayCost;
	}

	public com.hifun.soul.gameserver.escort.info.EscortAmendInfo getEscortAmendInfo(){
		return escortAmendInfo;
	}
		
	public void setEscortAmendInfo(com.hifun.soul.gameserver.escort.info.EscortAmendInfo escortAmendInfo){
		this.escortAmendInfo = escortAmendInfo;
	}
}