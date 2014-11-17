package com.hifun.soul.gameserver.arena.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开竞技场面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenArenaPanel extends GCMessage{
	
	/** 排名 */
	private int rank;
	/** 当前荣誉 */
	private int currentHonor;
	/** 剩余战斗次数 */
	private int battleTimes;
	/** 可挑战玩家列表 */
	private com.hifun.soul.gameserver.arena.ArenaMember[] arenaMembers;
	/** 竞技场提示信息 */
	private com.hifun.soul.gameserver.arena.ArenaNotice[] arenaNotices;
	/** 排名奖励领取状态 */
	private int rewardState;
	/** 增加挑战次数需要的花费 */
	private int crystal;
	/** 金币奖励 */
	private int giftCoin;
	/** 荣誉奖励 */
	private int giftHonor;
	/** 礼包领取cd */
	private int giftCd;
	/** 荣誉是否已经满了 */
	private boolean honorIsFull;
	/** 荣誉上限 */
	private int maxHonor;

	public GCOpenArenaPanel (){
	}
	
	public GCOpenArenaPanel (
			int rank,
			int currentHonor,
			int battleTimes,
			com.hifun.soul.gameserver.arena.ArenaMember[] arenaMembers,
			com.hifun.soul.gameserver.arena.ArenaNotice[] arenaNotices,
			int rewardState,
			int crystal,
			int giftCoin,
			int giftHonor,
			int giftCd,
			boolean honorIsFull,
			int maxHonor ){
			this.rank = rank;
			this.currentHonor = currentHonor;
			this.battleTimes = battleTimes;
			this.arenaMembers = arenaMembers;
			this.arenaNotices = arenaNotices;
			this.rewardState = rewardState;
			this.crystal = crystal;
			this.giftCoin = giftCoin;
			this.giftHonor = giftHonor;
			this.giftCd = giftCd;
			this.honorIsFull = honorIsFull;
			this.maxHonor = maxHonor;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		rank = readInteger();
		currentHonor = readInteger();
		battleTimes = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		arenaMembers = new com.hifun.soul.gameserver.arena.ArenaMember[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.arena.ArenaMember objarenaMembers = new com.hifun.soul.gameserver.arena.ArenaMember();
			arenaMembers[i] = objarenaMembers;
					objarenaMembers.setRank(readInteger());
							objarenaMembers.setRoleId(readLong());
							objarenaMembers.setIcon(readInteger());
							objarenaMembers.setName(readString());
							objarenaMembers.setLevel(readInteger());
							objarenaMembers.setLegionId(readLong());
							objarenaMembers.setLegionName(readString());
							objarenaMembers.setRankRewardId(readInteger());
							objarenaMembers.setRankRewardState(readInteger());
							objarenaMembers.setOccupation(readInteger());
							objarenaMembers.setYellowVipLevel(readInteger());
							objarenaMembers.setIsYearYellowVip(readBoolean());
							objarenaMembers.setIsYellowHighVip(readBoolean());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		arenaNotices = new com.hifun.soul.gameserver.arena.ArenaNotice[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.arena.ArenaNotice objarenaNotices = new com.hifun.soul.gameserver.arena.ArenaNotice();
			arenaNotices[i] = objarenaNotices;
					objarenaNotices.setRoleId(readLong());
							objarenaNotices.setRoleName(readString());
							objarenaNotices.setOtherRoleId(readLong());
							objarenaNotices.setOtherRoleName(readString());
							objarenaNotices.setWin(readBoolean());
							objarenaNotices.setTimeInterval(readInteger());
							objarenaNotices.setIsChallenger(readBoolean());
							objarenaNotices.setRank(readInteger());
							objarenaNotices.setIsUpFiveAndHigherVsLower(readBoolean());
				}
		rewardState = readInteger();
		crystal = readInteger();
		giftCoin = readInteger();
		giftHonor = readInteger();
		giftCd = readInteger();
		honorIsFull = readBoolean();
		maxHonor = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rank);
		writeInteger(currentHonor);
		writeInteger(battleTimes);
	writeShort(arenaMembers.length);
	for(int i=0; i<arenaMembers.length; i++){
	com.hifun.soul.gameserver.arena.ArenaMember objarenaMembers = arenaMembers[i];
				writeInteger(objarenaMembers.getRank());
				writeLong(objarenaMembers.getRoleId());
				writeInteger(objarenaMembers.getIcon());
				writeString(objarenaMembers.getName());
				writeInteger(objarenaMembers.getLevel());
				writeLong(objarenaMembers.getLegionId());
				writeString(objarenaMembers.getLegionName());
				writeInteger(objarenaMembers.getRankRewardId());
				writeInteger(objarenaMembers.getRankRewardState());
				writeInteger(objarenaMembers.getOccupation());
				writeInteger(objarenaMembers.getYellowVipLevel());
				writeBoolean(objarenaMembers.getIsYearYellowVip());
				writeBoolean(objarenaMembers.getIsYellowHighVip());
	}
	writeShort(arenaNotices.length);
	for(int i=0; i<arenaNotices.length; i++){
	com.hifun.soul.gameserver.arena.ArenaNotice objarenaNotices = arenaNotices[i];
				writeLong(objarenaNotices.getRoleId());
				writeString(objarenaNotices.getRoleName());
				writeLong(objarenaNotices.getOtherRoleId());
				writeString(objarenaNotices.getOtherRoleName());
				writeBoolean(objarenaNotices.getWin());
				writeInteger(objarenaNotices.getTimeInterval());
				writeBoolean(objarenaNotices.getIsChallenger());
				writeInteger(objarenaNotices.getRank());
				writeBoolean(objarenaNotices.getIsUpFiveAndHigherVsLower());
	}
		writeInteger(rewardState);
		writeInteger(crystal);
		writeInteger(giftCoin);
		writeInteger(giftHonor);
		writeInteger(giftCd);
		writeBoolean(honorIsFull);
		writeInteger(maxHonor);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_ARENA_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_ARENA_PANEL";
	}

	public int getRank(){
		return rank;
	}
		
	public void setRank(int rank){
		this.rank = rank;
	}

	public int getCurrentHonor(){
		return currentHonor;
	}
		
	public void setCurrentHonor(int currentHonor){
		this.currentHonor = currentHonor;
	}

	public int getBattleTimes(){
		return battleTimes;
	}
		
	public void setBattleTimes(int battleTimes){
		this.battleTimes = battleTimes;
	}

	public com.hifun.soul.gameserver.arena.ArenaMember[] getArenaMembers(){
		return arenaMembers;
	}

	public void setArenaMembers(com.hifun.soul.gameserver.arena.ArenaMember[] arenaMembers){
		this.arenaMembers = arenaMembers;
	}	

	public com.hifun.soul.gameserver.arena.ArenaNotice[] getArenaNotices(){
		return arenaNotices;
	}

	public void setArenaNotices(com.hifun.soul.gameserver.arena.ArenaNotice[] arenaNotices){
		this.arenaNotices = arenaNotices;
	}	

	public int getRewardState(){
		return rewardState;
	}
		
	public void setRewardState(int rewardState){
		this.rewardState = rewardState;
	}

	public int getCrystal(){
		return crystal;
	}
		
	public void setCrystal(int crystal){
		this.crystal = crystal;
	}

	public int getGiftCoin(){
		return giftCoin;
	}
		
	public void setGiftCoin(int giftCoin){
		this.giftCoin = giftCoin;
	}

	public int getGiftHonor(){
		return giftHonor;
	}
		
	public void setGiftHonor(int giftHonor){
		this.giftHonor = giftHonor;
	}

	public int getGiftCd(){
		return giftCd;
	}
		
	public void setGiftCd(int giftCd){
		this.giftCd = giftCd;
	}

	public boolean getHonorIsFull(){
		return honorIsFull;
	}
		
	public void setHonorIsFull(boolean honorIsFull){
		this.honorIsFull = honorIsFull;
	}

	public int getMaxHonor(){
		return maxHonor;
	}
		
	public void setMaxHonor(int maxHonor){
		this.maxHonor = maxHonor;
	}
}