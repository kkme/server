package com.hifun.soul.gameserver.mars.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开战神之巅面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenMarsPanel extends GCMessage{
	
	/** 场别信息  */
	private com.hifun.soul.gameserver.mars.msg.info.MarsFieldInfo fieldInfo;
	/** 战神之巅房间列表  */
	private com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo[] roomInfos;
	/** 刷新房间花费 */
	private int refreshCostNum;
	/** 解锁房间花费 */
	private int unlockCostNum;
	/** 个人信息  */
	private com.hifun.soul.gameserver.mars.msg.info.MarsSelfInfo selfInfo;
	/** 手下败将列表  */
	private com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[] loserInfos;
	/** 最大接受挑战奖励次数 */
	private int maxAcceptRewardNum;
	/** 剩余接受挑战奖励次数 */
	private int remainAcceptRewardNum;

	public GCOpenMarsPanel (){
	}
	
	public GCOpenMarsPanel (
			com.hifun.soul.gameserver.mars.msg.info.MarsFieldInfo fieldInfo,
			com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo[] roomInfos,
			int refreshCostNum,
			int unlockCostNum,
			com.hifun.soul.gameserver.mars.msg.info.MarsSelfInfo selfInfo,
			com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[] loserInfos,
			int maxAcceptRewardNum,
			int remainAcceptRewardNum ){
			this.fieldInfo = fieldInfo;
			this.roomInfos = roomInfos;
			this.refreshCostNum = refreshCostNum;
			this.unlockCostNum = unlockCostNum;
			this.selfInfo = selfInfo;
			this.loserInfos = loserInfos;
			this.maxAcceptRewardNum = maxAcceptRewardNum;
			this.remainAcceptRewardNum = remainAcceptRewardNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		fieldInfo = new com.hifun.soul.gameserver.mars.msg.info.MarsFieldInfo();
						fieldInfo.setFieldName(readString());
						fieldInfo.setStartLevel(readInteger());
						fieldInfo.setEndLevel(readInteger());
				count = readShort();
		count = count < 0 ? 0 : count;
		roomInfos = new com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo objroomInfos = new com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo();
			roomInfos[i] = objroomInfos;
					objroomInfos.setRoomType(readInteger());
							objroomInfos.setIsLocked(readInteger());
							objroomInfos.setProduceStarSoul(readInteger());
							objroomInfos.setProduceKillValue(readInteger());
								{
	com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo objownerInfo = new com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo();
	objroomInfos.setOwnerInfo(objownerInfo);
				objownerInfo.setPlayerId(readLong());
				objownerInfo.setPlayerName(readString());
				objownerInfo.setPlayerLevel(readInteger());
				objownerInfo.setOccupationType(readInteger());
				objownerInfo.setTodayKillValue(readInteger());
			}
								{
	int subCountbetInfos = readShort();
		com.hifun.soul.gameserver.mars.msg.info.MarsBetInfo[] subListbetInfos = new com.hifun.soul.gameserver.mars.msg.info.MarsBetInfo[subCountbetInfos];
		objroomInfos.setBetInfos(subListbetInfos);
	for(int jbetInfos = 0; jbetInfos < subCountbetInfos; jbetInfos++){
						com.hifun.soul.gameserver.mars.msg.info.MarsBetInfo objbetInfos = new com.hifun.soul.gameserver.mars.msg.info.MarsBetInfo();
		subListbetInfos[jbetInfos] = objbetInfos;
						objbetInfos.setMultiple(readInteger());
								objbetInfos.setCostNum(readInteger());
								objbetInfos.setVisible(readBoolean());
							}
	}
				}
		refreshCostNum = readInteger();
		unlockCostNum = readInteger();
		selfInfo = new com.hifun.soul.gameserver.mars.msg.info.MarsSelfInfo();
						selfInfo.setRemainKillNum(readInteger());
						selfInfo.setTotalKillNum(readInteger());
						selfInfo.setRemainMultipleNum(readInteger());
						selfInfo.setBuyMultipleNumCost(readInteger());
						selfInfo.setTotalMultipleNum(readInteger());
						selfInfo.setTodayKillValue(readInteger());
						selfInfo.setRewardCoin(readInteger());
				count = readShort();
		count = count < 0 ? 0 : count;
		loserInfos = new com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo objloserInfos = new com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo();
			loserInfos[i] = objloserInfos;
					objloserInfos.setPlayerId(readLong());
							objloserInfos.setPlayerName(readString());
							objloserInfos.setPlayerLevel(readInteger());
							objloserInfos.setOccupationType(readInteger());
							objloserInfos.setTodayKillValue(readInteger());
				}
		maxAcceptRewardNum = readInteger();
		remainAcceptRewardNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(fieldInfo.getFieldName());
		writeInteger(fieldInfo.getStartLevel());
		writeInteger(fieldInfo.getEndLevel());
	writeShort(roomInfos.length);
	for(int i=0; i<roomInfos.length; i++){
	com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo objroomInfos = roomInfos[i];
				writeInteger(objroomInfos.getRoomType());
				writeInteger(objroomInfos.getIsLocked());
				writeInteger(objroomInfos.getProduceStarSoul());
				writeInteger(objroomInfos.getProduceKillValue());
					com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo ownerInfo_objroomInfos = objroomInfos.getOwnerInfo();
					writeLong(ownerInfo_objroomInfos.getPlayerId());
					writeString(ownerInfo_objroomInfos.getPlayerName());
					writeInteger(ownerInfo_objroomInfos.getPlayerLevel());
					writeInteger(ownerInfo_objroomInfos.getOccupationType());
					writeInteger(ownerInfo_objroomInfos.getTodayKillValue());
							com.hifun.soul.gameserver.mars.msg.info.MarsBetInfo[] betInfos_objroomInfos=objroomInfos.getBetInfos();
	writeShort(betInfos_objroomInfos.length);
	for(int jbetInfos=0; jbetInfos<betInfos_objroomInfos.length; jbetInfos++){
					com.hifun.soul.gameserver.mars.msg.info.MarsBetInfo betInfos_objroomInfos_jbetInfos = betInfos_objroomInfos[jbetInfos];
													writeInteger(betInfos_objroomInfos_jbetInfos.getMultiple());
													writeInteger(betInfos_objroomInfos_jbetInfos.getCostNum());
													writeBoolean(betInfos_objroomInfos_jbetInfos.getVisible());
									}
	}
		writeInteger(refreshCostNum);
		writeInteger(unlockCostNum);
		writeInteger(selfInfo.getRemainKillNum());
		writeInteger(selfInfo.getTotalKillNum());
		writeInteger(selfInfo.getRemainMultipleNum());
		writeInteger(selfInfo.getBuyMultipleNumCost());
		writeInteger(selfInfo.getTotalMultipleNum());
		writeInteger(selfInfo.getTodayKillValue());
		writeInteger(selfInfo.getRewardCoin());
	writeShort(loserInfos.length);
	for(int i=0; i<loserInfos.length; i++){
	com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo objloserInfos = loserInfos[i];
				writeLong(objloserInfos.getPlayerId());
				writeString(objloserInfos.getPlayerName());
				writeInteger(objloserInfos.getPlayerLevel());
				writeInteger(objloserInfos.getOccupationType());
				writeInteger(objloserInfos.getTodayKillValue());
	}
		writeInteger(maxAcceptRewardNum);
		writeInteger(remainAcceptRewardNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_MARS_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_MARS_PANEL";
	}

	public com.hifun.soul.gameserver.mars.msg.info.MarsFieldInfo getFieldInfo(){
		return fieldInfo;
	}
		
	public void setFieldInfo(com.hifun.soul.gameserver.mars.msg.info.MarsFieldInfo fieldInfo){
		this.fieldInfo = fieldInfo;
	}

	public com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo[] getRoomInfos(){
		return roomInfos;
	}

	public void setRoomInfos(com.hifun.soul.gameserver.mars.msg.info.MarsRoomInfo[] roomInfos){
		this.roomInfos = roomInfos;
	}	

	public int getRefreshCostNum(){
		return refreshCostNum;
	}
		
	public void setRefreshCostNum(int refreshCostNum){
		this.refreshCostNum = refreshCostNum;
	}

	public int getUnlockCostNum(){
		return unlockCostNum;
	}
		
	public void setUnlockCostNum(int unlockCostNum){
		this.unlockCostNum = unlockCostNum;
	}

	public com.hifun.soul.gameserver.mars.msg.info.MarsSelfInfo getSelfInfo(){
		return selfInfo;
	}
		
	public void setSelfInfo(com.hifun.soul.gameserver.mars.msg.info.MarsSelfInfo selfInfo){
		this.selfInfo = selfInfo;
	}

	public com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[] getLoserInfos(){
		return loserInfos;
	}

	public void setLoserInfos(com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[] loserInfos){
		this.loserInfos = loserInfos;
	}	

	public int getMaxAcceptRewardNum(){
		return maxAcceptRewardNum;
	}
		
	public void setMaxAcceptRewardNum(int maxAcceptRewardNum){
		this.maxAcceptRewardNum = maxAcceptRewardNum;
	}

	public int getRemainAcceptRewardNum(){
		return remainAcceptRewardNum;
	}
		
	public void setRemainAcceptRewardNum(int remainAcceptRewardNum){
		this.remainAcceptRewardNum = remainAcceptRewardNum;
	}
}