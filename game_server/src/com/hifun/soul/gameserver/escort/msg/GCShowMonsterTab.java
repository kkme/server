package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示怪物标签页
 *
 * @author SevenSoul
 */
@Component
public class GCShowMonsterTab extends GCMessage{
	
	/** 默认怪物类型 */
	private int defaultMonsterType;
	/** 押运怪物信息 */
	private com.hifun.soul.gameserver.escort.info.EscortMonsterInfo[] monsterInfos;
	/** 是否可召唤 */
	private boolean canCall;
	/** 召唤怪物消费 */
	private int callMonsterCost;
	/** 刷新怪物消费 */
	private int refreshMonsterCost;
	/** 鼓舞消费 */
	private int encourageCost;
	/** 鼓舞攻击力加成 */
	private int encourageAttackRate;
	/** 鼓舞生命值加成 */
	private int encourageHpRate;
	/** 护送好友信息 */
	private com.hifun.soul.gameserver.escort.info.EscortFriendInfo[] friendInfos;
	/** 邀请好友协助状态 */
	private int inviteFriendState;
	/** 邀请有效时间 */
	private int inviteRemainTime;

	public GCShowMonsterTab (){
	}
	
	public GCShowMonsterTab (
			int defaultMonsterType,
			com.hifun.soul.gameserver.escort.info.EscortMonsterInfo[] monsterInfos,
			boolean canCall,
			int callMonsterCost,
			int refreshMonsterCost,
			int encourageCost,
			int encourageAttackRate,
			int encourageHpRate,
			com.hifun.soul.gameserver.escort.info.EscortFriendInfo[] friendInfos,
			int inviteFriendState,
			int inviteRemainTime ){
			this.defaultMonsterType = defaultMonsterType;
			this.monsterInfos = monsterInfos;
			this.canCall = canCall;
			this.callMonsterCost = callMonsterCost;
			this.refreshMonsterCost = refreshMonsterCost;
			this.encourageCost = encourageCost;
			this.encourageAttackRate = encourageAttackRate;
			this.encourageHpRate = encourageHpRate;
			this.friendInfos = friendInfos;
			this.inviteFriendState = inviteFriendState;
			this.inviteRemainTime = inviteRemainTime;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		defaultMonsterType = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		monsterInfos = new com.hifun.soul.gameserver.escort.info.EscortMonsterInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.escort.info.EscortMonsterInfo objmonsterInfos = new com.hifun.soul.gameserver.escort.info.EscortMonsterInfo();
			monsterInfos[i] = objmonsterInfos;
					objmonsterInfos.setEscortTime(readInteger());
							objmonsterInfos.setMonsterType(readInteger());
							objmonsterInfos.setEscortRewardCoin(readInteger());
							objmonsterInfos.setMonsterIconId(readInteger());
							objmonsterInfos.setMonsterName(readString());
				}
		canCall = readBoolean();
		callMonsterCost = readInteger();
		refreshMonsterCost = readInteger();
		encourageCost = readInteger();
		encourageAttackRate = readInteger();
		encourageHpRate = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		friendInfos = new com.hifun.soul.gameserver.escort.info.EscortFriendInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.escort.info.EscortFriendInfo objfriendInfos = new com.hifun.soul.gameserver.escort.info.EscortFriendInfo();
			friendInfos[i] = objfriendInfos;
					objfriendInfos.setFriendId(readLong());
							objfriendInfos.setFriendName(readString());
							objfriendInfos.setFriendLevel(readInteger());
							objfriendInfos.setRemainHelpNum(readInteger());
							objfriendInfos.setMaxHelpNum(readInteger());
				}
		inviteFriendState = readInteger();
		inviteRemainTime = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(defaultMonsterType);
	writeShort(monsterInfos.length);
	for(int i=0; i<monsterInfos.length; i++){
	com.hifun.soul.gameserver.escort.info.EscortMonsterInfo objmonsterInfos = monsterInfos[i];
				writeInteger(objmonsterInfos.getEscortTime());
				writeInteger(objmonsterInfos.getMonsterType());
				writeInteger(objmonsterInfos.getEscortRewardCoin());
				writeInteger(objmonsterInfos.getMonsterIconId());
				writeString(objmonsterInfos.getMonsterName());
	}
		writeBoolean(canCall);
		writeInteger(callMonsterCost);
		writeInteger(refreshMonsterCost);
		writeInteger(encourageCost);
		writeInteger(encourageAttackRate);
		writeInteger(encourageHpRate);
	writeShort(friendInfos.length);
	for(int i=0; i<friendInfos.length; i++){
	com.hifun.soul.gameserver.escort.info.EscortFriendInfo objfriendInfos = friendInfos[i];
				writeLong(objfriendInfos.getFriendId());
				writeString(objfriendInfos.getFriendName());
				writeInteger(objfriendInfos.getFriendLevel());
				writeInteger(objfriendInfos.getRemainHelpNum());
				writeInteger(objfriendInfos.getMaxHelpNum());
	}
		writeInteger(inviteFriendState);
		writeInteger(inviteRemainTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_MONSTER_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_MONSTER_TAB";
	}

	public int getDefaultMonsterType(){
		return defaultMonsterType;
	}
		
	public void setDefaultMonsterType(int defaultMonsterType){
		this.defaultMonsterType = defaultMonsterType;
	}

	public com.hifun.soul.gameserver.escort.info.EscortMonsterInfo[] getMonsterInfos(){
		return monsterInfos;
	}

	public void setMonsterInfos(com.hifun.soul.gameserver.escort.info.EscortMonsterInfo[] monsterInfos){
		this.monsterInfos = monsterInfos;
	}	

	public boolean getCanCall(){
		return canCall;
	}
		
	public void setCanCall(boolean canCall){
		this.canCall = canCall;
	}

	public int getCallMonsterCost(){
		return callMonsterCost;
	}
		
	public void setCallMonsterCost(int callMonsterCost){
		this.callMonsterCost = callMonsterCost;
	}

	public int getRefreshMonsterCost(){
		return refreshMonsterCost;
	}
		
	public void setRefreshMonsterCost(int refreshMonsterCost){
		this.refreshMonsterCost = refreshMonsterCost;
	}

	public int getEncourageCost(){
		return encourageCost;
	}
		
	public void setEncourageCost(int encourageCost){
		this.encourageCost = encourageCost;
	}

	public int getEncourageAttackRate(){
		return encourageAttackRate;
	}
		
	public void setEncourageAttackRate(int encourageAttackRate){
		this.encourageAttackRate = encourageAttackRate;
	}

	public int getEncourageHpRate(){
		return encourageHpRate;
	}
		
	public void setEncourageHpRate(int encourageHpRate){
		this.encourageHpRate = encourageHpRate;
	}

	public com.hifun.soul.gameserver.escort.info.EscortFriendInfo[] getFriendInfos(){
		return friendInfos;
	}

	public void setFriendInfos(com.hifun.soul.gameserver.escort.info.EscortFriendInfo[] friendInfos){
		this.friendInfos = friendInfos;
	}	

	public int getInviteFriendState(){
		return inviteFriendState;
	}
		
	public void setInviteFriendState(int inviteFriendState){
		this.inviteFriendState = inviteFriendState;
	}

	public int getInviteRemainTime(){
		return inviteRemainTime;
	}
		
	public void setInviteRemainTime(int inviteRemainTime){
		this.inviteRemainTime = inviteRemainTime;
	}
}