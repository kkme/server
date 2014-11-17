package com.hifun.soul.gameserver.boss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 参与BOSS战
 *
 * @author SevenSoul
 */
@Component
public class GCJoinBossWar extends GCMessage{
	
	/** boss信息 */
	private com.hifun.soul.gameserver.boss.BossInfo bossInfo;
	/** 鼓舞百分比 */
	private int encourageRate;
	/** 充能百分比 */
	private int chargedstrikeRate;
	/** 剩余时间(单位秒) */
	private int remainTime;
	/** 自己对boss造成的伤害值描述 */
	private String damageDesc;
	/** boss伤害排行榜 */
	private String[] damageRankings;
	/** 是否已经鼓舞满 */
	private boolean ifFull;
	/** 金币鼓舞消耗的金币 */
	private int coin;
	/** 魔晶鼓舞消耗的魔晶 */
	private int crystal;
	/** 灵石鼓舞消耗的灵石 */
	private int encourageForgeStoneCost;
	/** boss战活动描述 */
	private String bossWarDesc;
	/** boss状态 */
	private int bossState;
	/** boss战准备CD时间 */
	private int waitCDTime;
	/** 冥想力鼓舞消耗的冥想力 */
	private int meditation;

	public GCJoinBossWar (){
	}
	
	public GCJoinBossWar (
			com.hifun.soul.gameserver.boss.BossInfo bossInfo,
			int encourageRate,
			int chargedstrikeRate,
			int remainTime,
			String damageDesc,
			String[] damageRankings,
			boolean ifFull,
			int coin,
			int crystal,
			int encourageForgeStoneCost,
			String bossWarDesc,
			int bossState,
			int waitCDTime,
			int meditation ){
			this.bossInfo = bossInfo;
			this.encourageRate = encourageRate;
			this.chargedstrikeRate = chargedstrikeRate;
			this.remainTime = remainTime;
			this.damageDesc = damageDesc;
			this.damageRankings = damageRankings;
			this.ifFull = ifFull;
			this.coin = coin;
			this.crystal = crystal;
			this.encourageForgeStoneCost = encourageForgeStoneCost;
			this.bossWarDesc = bossWarDesc;
			this.bossState = bossState;
			this.waitCDTime = waitCDTime;
			this.meditation = meditation;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		bossInfo = new com.hifun.soul.gameserver.boss.BossInfo();
						bossInfo.setBossId(readInteger());
						bossInfo.setName(readString());
						bossInfo.setIcon(readInteger());
						bossInfo.setLevel(readInteger());
						bossInfo.setBossState(readInteger());
						bossInfo.setRemainBlood(readInteger());
						bossInfo.setTotalBlood(readInteger());
						bossInfo.setJoinPeopleNum(readInteger());
				encourageRate = readInteger();
		chargedstrikeRate = readInteger();
		remainTime = readInteger();
		damageDesc = readString();
		count = readShort();
		count = count < 0 ? 0 : count;
		damageRankings = new String[count];
		for(int i=0; i<count; i++){
			damageRankings[i] = readString();
		}
		ifFull = readBoolean();
		coin = readInteger();
		crystal = readInteger();
		encourageForgeStoneCost = readInteger();
		bossWarDesc = readString();
		bossState = readInteger();
		waitCDTime = readInteger();
		meditation = readInteger();
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
		writeInteger(chargedstrikeRate);
		writeInteger(remainTime);
		writeString(damageDesc);
	writeShort(damageRankings.length);
	for(int i=0; i<damageRankings.length; i++){
	String objdamageRankings = damageRankings[i];
			writeString(objdamageRankings);
}
		writeBoolean(ifFull);
		writeInteger(coin);
		writeInteger(crystal);
		writeInteger(encourageForgeStoneCost);
		writeString(bossWarDesc);
		writeInteger(bossState);
		writeInteger(waitCDTime);
		writeInteger(meditation);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_JOIN_BOSS_WAR;
	}
	
	@Override
	public String getTypeName() {
		return "GC_JOIN_BOSS_WAR";
	}

	public com.hifun.soul.gameserver.boss.BossInfo getBossInfo(){
		return bossInfo;
	}
		
	public void setBossInfo(com.hifun.soul.gameserver.boss.BossInfo bossInfo){
		this.bossInfo = bossInfo;
	}

	public int getEncourageRate(){
		return encourageRate;
	}
		
	public void setEncourageRate(int encourageRate){
		this.encourageRate = encourageRate;
	}

	public int getChargedstrikeRate(){
		return chargedstrikeRate;
	}
		
	public void setChargedstrikeRate(int chargedstrikeRate){
		this.chargedstrikeRate = chargedstrikeRate;
	}

	public int getRemainTime(){
		return remainTime;
	}
		
	public void setRemainTime(int remainTime){
		this.remainTime = remainTime;
	}

	public String getDamageDesc(){
		return damageDesc;
	}
		
	public void setDamageDesc(String damageDesc){
		this.damageDesc = damageDesc;
	}

	public String[] getDamageRankings(){
		return damageRankings;
	}

	public void setDamageRankings(String[] damageRankings){
		this.damageRankings = damageRankings;
	}	

	public boolean getIfFull(){
		return ifFull;
	}
		
	public void setIfFull(boolean ifFull){
		this.ifFull = ifFull;
	}

	public int getCoin(){
		return coin;
	}
		
	public void setCoin(int coin){
		this.coin = coin;
	}

	public int getCrystal(){
		return crystal;
	}
		
	public void setCrystal(int crystal){
		this.crystal = crystal;
	}

	public int getEncourageForgeStoneCost(){
		return encourageForgeStoneCost;
	}
		
	public void setEncourageForgeStoneCost(int encourageForgeStoneCost){
		this.encourageForgeStoneCost = encourageForgeStoneCost;
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

	public int getMeditation(){
		return meditation;
	}
		
	public void setMeditation(int meditation){
		this.meditation = meditation;
	}
}