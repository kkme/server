package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新冥想面板消息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateMeditationPannel extends GCMessage{
	
	/** 冥想模式列表 */
	private com.hifun.soul.gameserver.meditation.template.MeditationModeTemplate[] meditationMode;
	/** 冥想时长列表 */
	private com.hifun.soul.gameserver.meditation.template.MeditationInfo[] meditationInfo;
	/** 开启的好友协助位数量 */
	private int assistPositionNum;
	/** 冥想状态：未开始0，进行中1，待收获2 */
	private int meditationState;
	/** 选中的冥想模式索引 */
	private int selectedModeIndex;
	/** 选中的冥想时长模式索引 */
	private int selectedTimeIndex;
	/** 剩余协助好友的次数 */
	private int helpFriendTimesRemain;
	/** 累计获得科技点数 */
	private int currentTechPoint;
	/** 协助加成比例 */
	private int assistRate;
	/** 最高科技点数 */
	private int totalTechPoint;
	/** 等级限制 */
	private int levelLimit;
	/** 金币限制 */
	private int coinLimit;
	/** 魔晶限制 */
	private int crystalLimit;
	/** 好友信息 */
	private com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo[] friendAssist;

	public GCUpdateMeditationPannel (){
	}
	
	public GCUpdateMeditationPannel (
			com.hifun.soul.gameserver.meditation.template.MeditationModeTemplate[] meditationMode,
			com.hifun.soul.gameserver.meditation.template.MeditationInfo[] meditationInfo,
			int assistPositionNum,
			int meditationState,
			int selectedModeIndex,
			int selectedTimeIndex,
			int helpFriendTimesRemain,
			int currentTechPoint,
			int assistRate,
			int totalTechPoint,
			int levelLimit,
			int coinLimit,
			int crystalLimit,
			com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo[] friendAssist ){
			this.meditationMode = meditationMode;
			this.meditationInfo = meditationInfo;
			this.assistPositionNum = assistPositionNum;
			this.meditationState = meditationState;
			this.selectedModeIndex = selectedModeIndex;
			this.selectedTimeIndex = selectedTimeIndex;
			this.helpFriendTimesRemain = helpFriendTimesRemain;
			this.currentTechPoint = currentTechPoint;
			this.assistRate = assistRate;
			this.totalTechPoint = totalTechPoint;
			this.levelLimit = levelLimit;
			this.coinLimit = coinLimit;
			this.crystalLimit = crystalLimit;
			this.friendAssist = friendAssist;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		meditationMode = new com.hifun.soul.gameserver.meditation.template.MeditationModeTemplate[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.meditation.template.MeditationModeTemplate objmeditationMode = new com.hifun.soul.gameserver.meditation.template.MeditationModeTemplate();
			meditationMode[i] = objmeditationMode;
					objmeditationMode.setName(readString());
							objmeditationMode.setRate(readInteger());
							objmeditationMode.setCurrencyType(readInteger());
							objmeditationMode.setCurrencyNum(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		meditationInfo = new com.hifun.soul.gameserver.meditation.template.MeditationInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.meditation.template.MeditationInfo objmeditationInfo = new com.hifun.soul.gameserver.meditation.template.MeditationInfo();
			meditationInfo[i] = objmeditationInfo;
					objmeditationInfo.setMeditationTime(readInteger());
							objmeditationInfo.setCurrencyType(readInteger());
							objmeditationInfo.setCurrencyNum(readInteger());
				}
		assistPositionNum = readInteger();
		meditationState = readInteger();
		selectedModeIndex = readInteger();
		selectedTimeIndex = readInteger();
		helpFriendTimesRemain = readInteger();
		currentTechPoint = readInteger();
		assistRate = readInteger();
		totalTechPoint = readInteger();
		levelLimit = readInteger();
		coinLimit = readInteger();
		crystalLimit = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		friendAssist = new com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo objfriendAssist = new com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo();
			friendAssist[i] = objfriendAssist;
					objfriendAssist.setIndex(readInteger());
								{
	com.hifun.soul.gameserver.friend.FriendInfo objfriendInfo = new com.hifun.soul.gameserver.friend.FriendInfo();
	objfriendAssist.setFriendInfo(objfriendInfo);
				objfriendInfo.setRoleId(readLong());
				objfriendInfo.setRoleName(readString());
				objfriendInfo.setLevel(readInteger());
				objfriendInfo.setOccupation(readInteger());
				objfriendInfo.setSendState(readBoolean());
				objfriendInfo.setGetState(readInteger());
				objfriendInfo.setIsOnLine(readBoolean());
				objfriendInfo.setYellowVipLevel(readInteger());
				objfriendInfo.setIsYearYellowVip(readBoolean());
				objfriendInfo.setIsYellowHighVip(readBoolean());
			}
							objfriendAssist.setAssistRate(readInteger());
							objfriendAssist.setTotalTechPoint(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(meditationMode.length);
	for(int i=0; i<meditationMode.length; i++){
	com.hifun.soul.gameserver.meditation.template.MeditationModeTemplate objmeditationMode = meditationMode[i];
				writeString(objmeditationMode.getName());
				writeInteger(objmeditationMode.getRate());
				writeInteger(objmeditationMode.getCurrencyType());
				writeInteger(objmeditationMode.getCurrencyNum());
	}
	writeShort(meditationInfo.length);
	for(int i=0; i<meditationInfo.length; i++){
	com.hifun.soul.gameserver.meditation.template.MeditationInfo objmeditationInfo = meditationInfo[i];
				writeInteger(objmeditationInfo.getMeditationTime());
				writeInteger(objmeditationInfo.getCurrencyType());
				writeInteger(objmeditationInfo.getCurrencyNum());
	}
		writeInteger(assistPositionNum);
		writeInteger(meditationState);
		writeInteger(selectedModeIndex);
		writeInteger(selectedTimeIndex);
		writeInteger(helpFriendTimesRemain);
		writeInteger(currentTechPoint);
		writeInteger(assistRate);
		writeInteger(totalTechPoint);
		writeInteger(levelLimit);
		writeInteger(coinLimit);
		writeInteger(crystalLimit);
	writeShort(friendAssist.length);
	for(int i=0; i<friendAssist.length; i++){
	com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo objfriendAssist = friendAssist[i];
				writeInteger(objfriendAssist.getIndex());
					com.hifun.soul.gameserver.friend.FriendInfo friendInfo_objfriendAssist = objfriendAssist.getFriendInfo();
					writeLong(friendInfo_objfriendAssist.getRoleId());
					writeString(friendInfo_objfriendAssist.getRoleName());
					writeInteger(friendInfo_objfriendAssist.getLevel());
					writeInteger(friendInfo_objfriendAssist.getOccupation());
					writeBoolean(friendInfo_objfriendAssist.getSendState());
					writeInteger(friendInfo_objfriendAssist.getGetState());
					writeBoolean(friendInfo_objfriendAssist.getIsOnLine());
					writeInteger(friendInfo_objfriendAssist.getYellowVipLevel());
					writeBoolean(friendInfo_objfriendAssist.getIsYearYellowVip());
					writeBoolean(friendInfo_objfriendAssist.getIsYellowHighVip());
						writeInteger(objfriendAssist.getAssistRate());
				writeInteger(objfriendAssist.getTotalTechPoint());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_MEDITATION_PANNEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_MEDITATION_PANNEL";
	}

	public com.hifun.soul.gameserver.meditation.template.MeditationModeTemplate[] getMeditationMode(){
		return meditationMode;
	}

	public void setMeditationMode(com.hifun.soul.gameserver.meditation.template.MeditationModeTemplate[] meditationMode){
		this.meditationMode = meditationMode;
	}	

	public com.hifun.soul.gameserver.meditation.template.MeditationInfo[] getMeditationInfo(){
		return meditationInfo;
	}

	public void setMeditationInfo(com.hifun.soul.gameserver.meditation.template.MeditationInfo[] meditationInfo){
		this.meditationInfo = meditationInfo;
	}	

	public int getAssistPositionNum(){
		return assistPositionNum;
	}
		
	public void setAssistPositionNum(int assistPositionNum){
		this.assistPositionNum = assistPositionNum;
	}

	public int getMeditationState(){
		return meditationState;
	}
		
	public void setMeditationState(int meditationState){
		this.meditationState = meditationState;
	}

	public int getSelectedModeIndex(){
		return selectedModeIndex;
	}
		
	public void setSelectedModeIndex(int selectedModeIndex){
		this.selectedModeIndex = selectedModeIndex;
	}

	public int getSelectedTimeIndex(){
		return selectedTimeIndex;
	}
		
	public void setSelectedTimeIndex(int selectedTimeIndex){
		this.selectedTimeIndex = selectedTimeIndex;
	}

	public int getHelpFriendTimesRemain(){
		return helpFriendTimesRemain;
	}
		
	public void setHelpFriendTimesRemain(int helpFriendTimesRemain){
		this.helpFriendTimesRemain = helpFriendTimesRemain;
	}

	public int getCurrentTechPoint(){
		return currentTechPoint;
	}
		
	public void setCurrentTechPoint(int currentTechPoint){
		this.currentTechPoint = currentTechPoint;
	}

	public int getAssistRate(){
		return assistRate;
	}
		
	public void setAssistRate(int assistRate){
		this.assistRate = assistRate;
	}

	public int getTotalTechPoint(){
		return totalTechPoint;
	}
		
	public void setTotalTechPoint(int totalTechPoint){
		this.totalTechPoint = totalTechPoint;
	}

	public int getLevelLimit(){
		return levelLimit;
	}
		
	public void setLevelLimit(int levelLimit){
		this.levelLimit = levelLimit;
	}

	public int getCoinLimit(){
		return coinLimit;
	}
		
	public void setCoinLimit(int coinLimit){
		this.coinLimit = coinLimit;
	}

	public int getCrystalLimit(){
		return crystalLimit;
	}
		
	public void setCrystalLimit(int crystalLimit){
		this.crystalLimit = crystalLimit;
	}

	public com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo[] getFriendAssist(){
		return friendAssist;
	}

	public void setFriendAssist(com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo[] friendAssist){
		this.friendAssist = friendAssist;
	}	
}