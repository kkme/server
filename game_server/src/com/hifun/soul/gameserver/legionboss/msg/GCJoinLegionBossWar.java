package com.hifun.soul.gameserver.legionboss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 参与军团boss战
 *
 * @author SevenSoul
 */
@Component
public class GCJoinLegionBossWar extends GCMessage{
	
	/** 军团boss信息 */
	private com.hifun.soul.gameserver.legionboss.LegionBossInfo bossInfo;
	/** 鼓舞百分比 */
	private int encourageRate;
	/** 鼓舞是否已经满 */
	private boolean encouragedIsFull;
	/** 充能百分比 */
	private int chargedstrikeRate;
	/** 充能是否已经满 */
	private boolean chargedIsFull;
	/** 剩余时间(单位秒) */
	private int remainTime;
	/** 自己对boss造成的伤害值描述 */
	private String selfDamageDesc;
	/** 自己军团对boss造成的伤害值描述 */
	private String legionDamageDesc;
	/** 军团成员伤害排行榜 */
	private String[] memberDamageRankings;
	/** 军团伤害排行榜 */
	private String[] legionDamageRankings;
	/** 魔晶鼓舞消耗的魔晶 */
	private int crystal;
	/** 冥想鼓舞消耗的冥想力 */
	private int meditation;
	/** 军团boss战活动描述 */
	private String bossWarDesc;
	/** boss状态 */
	private int bossState;
	/** 军团boss战准备CD时间 */
	private int waitCDTime;

	public GCJoinLegionBossWar (){
	}
	
	public GCJoinLegionBossWar (
			com.hifun.soul.gameserver.legionboss.LegionBossInfo bossInfo,
			int encourageRate,
			boolean encouragedIsFull,
			int chargedstrikeRate,
			boolean chargedIsFull,
			int remainTime,
			String selfDamageDesc,
			String legionDamageDesc,
			String[] memberDamageRankings,
			String[] legionDamageRankings,
			int crystal,
			int meditation,
			String bossWarDesc,
			int bossState,
			int waitCDTime ){
			this.bossInfo = bossInfo;
			this.encourageRate = encourageRate;
			this.encouragedIsFull = encouragedIsFull;
			this.chargedstrikeRate = chargedstrikeRate;
			this.chargedIsFull = chargedIsFull;
			this.remainTime = remainTime;
			this.selfDamageDesc = selfDamageDesc;
			this.legionDamageDesc = legionDamageDesc;
			this.memberDamageRankings = memberDamageRankings;
			this.legionDamageRankings = legionDamageRankings;
			this.crystal = crystal;
			this.meditation = meditation;
			this.bossWarDesc = bossWarDesc;
			this.bossState = bossState;
			this.waitCDTime = waitCDTime;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		bossInfo = new com.hifun.soul.gameserver.legionboss.LegionBossInfo();
						bossInfo.setBossId(readInteger());
						bossInfo.setName(readString());
						bossInfo.setIcon(readInteger());
						bossInfo.setLevel(readInteger());
						bossInfo.setBossState(readInteger());
						bossInfo.setRemainBlood(readInteger());
						bossInfo.setTotalBlood(readInteger());
						bossInfo.setJoinPeopleNum(readInteger());
				encourageRate = readInteger();
		encouragedIsFull = readBoolean();
		chargedstrikeRate = readInteger();
		chargedIsFull = readBoolean();
		remainTime = readInteger();
		selfDamageDesc = readString();
		legionDamageDesc = readString();
		count = readShort();
		count = count < 0 ? 0 : count;
		memberDamageRankings = new String[count];
		for(int i=0; i<count; i++){
			memberDamageRankings[i] = readString();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		legionDamageRankings = new String[count];
		for(int i=0; i<count; i++){
			legionDamageRankings[i] = readString();
		}
		crystal = readInteger();
		meditation = readInteger();
		bossWarDesc = readString();
		bossState = readInteger();
		waitCDTime = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bossInfo.getBossId());
		writeString(bossInfo.getName());
		writeInteger(bossInfo.getIcon());
		writeInteger(bossInfo.getLevel());
		writeInteger(bossInfo.getBossState());
		writeInteger(bossInfo.getRemainBlood());
		writeInteger(bossInfo.getTotalBlood());
		writeInteger(bossInfo.getJoinPeopleNum());
		writeInteger(encourageRate);
		writeBoolean(encouragedIsFull);
		writeInteger(chargedstrikeRate);
		writeBoolean(chargedIsFull);
		writeInteger(remainTime);
		writeString(selfDamageDesc);
		writeString(legionDamageDesc);
	writeShort(memberDamageRankings.length);
	for(int i=0; i<memberDamageRankings.length; i++){
	String objmemberDamageRankings = memberDamageRankings[i];
			writeString(objmemberDamageRankings);
}
	writeShort(legionDamageRankings.length);
	for(int i=0; i<legionDamageRankings.length; i++){
	String objlegionDamageRankings = legionDamageRankings[i];
			writeString(objlegionDamageRankings);
}
		writeInteger(crystal);
		writeInteger(meditation);
		writeString(bossWarDesc);
		writeInteger(bossState);
		writeInteger(waitCDTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_JOIN_LEGION_BOSS_WAR;
	}
	
	@Override
	public String getTypeName() {
		return "GC_JOIN_LEGION_BOSS_WAR";
	}

	public com.hifun.soul.gameserver.legionboss.LegionBossInfo getBossInfo(){
		return bossInfo;
	}
		
	public void setBossInfo(com.hifun.soul.gameserver.legionboss.LegionBossInfo bossInfo){
		this.bossInfo = bossInfo;
	}

	public int getEncourageRate(){
		return encourageRate;
	}
		
	public void setEncourageRate(int encourageRate){
		this.encourageRate = encourageRate;
	}

	public boolean getEncouragedIsFull(){
		return encouragedIsFull;
	}
		
	public void setEncouragedIsFull(boolean encouragedIsFull){
		this.encouragedIsFull = encouragedIsFull;
	}

	public int getChargedstrikeRate(){
		return chargedstrikeRate;
	}
		
	public void setChargedstrikeRate(int chargedstrikeRate){
		this.chargedstrikeRate = chargedstrikeRate;
	}

	public boolean getChargedIsFull(){
		return chargedIsFull;
	}
		
	public void setChargedIsFull(boolean chargedIsFull){
		this.chargedIsFull = chargedIsFull;
	}

	public int getRemainTime(){
		return remainTime;
	}
		
	public void setRemainTime(int remainTime){
		this.remainTime = remainTime;
	}

	public String getSelfDamageDesc(){
		return selfDamageDesc;
	}
		
	public void setSelfDamageDesc(String selfDamageDesc){
		this.selfDamageDesc = selfDamageDesc;
	}

	public String getLegionDamageDesc(){
		return legionDamageDesc;
	}
		
	public void setLegionDamageDesc(String legionDamageDesc){
		this.legionDamageDesc = legionDamageDesc;
	}

	public String[] getMemberDamageRankings(){
		return memberDamageRankings;
	}

	public void setMemberDamageRankings(String[] memberDamageRankings){
		this.memberDamageRankings = memberDamageRankings;
	}	

	public String[] getLegionDamageRankings(){
		return legionDamageRankings;
	}

	public void setLegionDamageRankings(String[] legionDamageRankings){
		this.legionDamageRankings = legionDamageRankings;
	}	

	public int getCrystal(){
		return crystal;
	}
		
	public void setCrystal(int crystal){
		this.crystal = crystal;
	}

	public int getMeditation(){
		return meditation;
	}
		
	public void setMeditation(int meditation){
		this.meditation = meditation;
	}

	public String getBossWarDesc(){
		return bossWarDesc;
	}
		
	public void setBossWarDesc(String bossWarDesc){
		this.bossWarDesc = bossWarDesc;
	}

	public int getBossState(){
		return bossState;
	}
		
	public void setBossState(int bossState){
		this.bossState = bossState;
	}

	public int getWaitCDTime(){
		return waitCDTime;
	}
		
	public void setWaitCDTime(int waitCDTime){
		this.waitCDTime = waitCDTime;
	}
}