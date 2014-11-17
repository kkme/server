package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开军团矿战面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenLegionMineWarPanel extends GCMessage{
	
	/** 剩余时间(单位秒) */
	private int remainTime;
	/** 冥想力鼓舞花费 */
	private int meditationEncourageCost;
	/** 魔晶鼓舞花费 */
	private int crystalEncourageCost;
	/** 鼓舞是否已经满 */
	private boolean encourageIsFull;
	/** 攻击加成百分比 */
	private int attackRate;
	/** 防御加成百分比 */
	private int defenseRate;
	/** 战功 */
	private int warExploit;
	/** 排名 */
	private int rank;
	/** 军团buf信息 */
	private com.hifun.soul.gameserver.legionmine.LegionBuf[] legionBufs;
	/** 个人buf信息 */
	private com.hifun.soul.gameserver.legionmine.SelfBuf[] selfBufs;
	/** 所在矿的人数 */
	private int occupyMemberNum;
	/** 所在矿最大人数 */
	private int totalMemberNum;
	/** 可收获占领值 */
	private int currentOccupyValue;
	/** 侦查魔晶消耗 */
	private int watchCrystalCost;
	/** 军团矿堆信息 */
	private com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo[] mineInfos;
	/** 所在矿堆索引 */
	private int holdMineIndex;
	/** 是否翻转 */
	private boolean isOverturn;
	/** 矿战军团信息 */
	private com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo[] joinLegionInfos;
	/** 侦查持续时间(s) */
	private int watchHoldTime;
	/** 排名需要最小占领值 */
	private int rankMinOccupyValue;
	/** 所在矿富裕度描述 */
	private String richRateDesc;
	/** 所在矿收益比例 */
	private int revenueRate;

	public GCOpenLegionMineWarPanel (){
	}
	
	public GCOpenLegionMineWarPanel (
			int remainTime,
			int meditationEncourageCost,
			int crystalEncourageCost,
			boolean encourageIsFull,
			int attackRate,
			int defenseRate,
			int warExploit,
			int rank,
			com.hifun.soul.gameserver.legionmine.LegionBuf[] legionBufs,
			com.hifun.soul.gameserver.legionmine.SelfBuf[] selfBufs,
			int occupyMemberNum,
			int totalMemberNum,
			int currentOccupyValue,
			int watchCrystalCost,
			com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo[] mineInfos,
			int holdMineIndex,
			boolean isOverturn,
			com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo[] joinLegionInfos,
			int watchHoldTime,
			int rankMinOccupyValue,
			String richRateDesc,
			int revenueRate ){
			this.remainTime = remainTime;
			this.meditationEncourageCost = meditationEncourageCost;
			this.crystalEncourageCost = crystalEncourageCost;
			this.encourageIsFull = encourageIsFull;
			this.attackRate = attackRate;
			this.defenseRate = defenseRate;
			this.warExploit = warExploit;
			this.rank = rank;
			this.legionBufs = legionBufs;
			this.selfBufs = selfBufs;
			this.occupyMemberNum = occupyMemberNum;
			this.totalMemberNum = totalMemberNum;
			this.currentOccupyValue = currentOccupyValue;
			this.watchCrystalCost = watchCrystalCost;
			this.mineInfos = mineInfos;
			this.holdMineIndex = holdMineIndex;
			this.isOverturn = isOverturn;
			this.joinLegionInfos = joinLegionInfos;
			this.watchHoldTime = watchHoldTime;
			this.rankMinOccupyValue = rankMinOccupyValue;
			this.richRateDesc = richRateDesc;
			this.revenueRate = revenueRate;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		remainTime = readInteger();
		meditationEncourageCost = readInteger();
		crystalEncourageCost = readInteger();
		encourageIsFull = readBoolean();
		attackRate = readInteger();
		defenseRate = readInteger();
		warExploit = readInteger();
		rank = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		legionBufs = new com.hifun.soul.gameserver.legionmine.LegionBuf[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legionmine.LegionBuf objlegionBufs = new com.hifun.soul.gameserver.legionmine.LegionBuf();
			legionBufs[i] = objlegionBufs;
					objlegionBufs.setLegionBufType(readInteger());
							objlegionBufs.setBufIcon(readInteger());
							objlegionBufs.setBufDesc(readString());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		selfBufs = new com.hifun.soul.gameserver.legionmine.SelfBuf[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legionmine.SelfBuf objselfBufs = new com.hifun.soul.gameserver.legionmine.SelfBuf();
			selfBufs[i] = objselfBufs;
					objselfBufs.setSelfBufType(readInteger());
							objselfBufs.setBufIcon(readInteger());
							objselfBufs.setBufDesc(readString());
							objselfBufs.setUsing(readBoolean());
				}
		occupyMemberNum = readInteger();
		totalMemberNum = readInteger();
		currentOccupyValue = readInteger();
		watchCrystalCost = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		mineInfos = new com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo objmineInfos = new com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo();
			mineInfos[i] = objmineInfos;
					objmineInfos.setMineIndex(readInteger());
							objmineInfos.setCanBattle(readBoolean());
							objmineInfos.setCanMove(readBoolean());
							objmineInfos.setCanDisturb(readBoolean());
							objmineInfos.setJoinLegionType(readInteger());
							objmineInfos.setSurroundState(readInteger());
							objmineInfos.setOccupyNum(readInteger());
							objmineInfos.setOccupyNumVisible(readBoolean());
							objmineInfos.setIsDouble(readBoolean());
							objmineInfos.setIsRedMine(readBoolean());
							objmineInfos.setIsSelf(readBoolean());
							objmineInfos.setMineType(readInteger());
				}
		holdMineIndex = readInteger();
		isOverturn = readBoolean();
		count = readShort();
		count = count < 0 ? 0 : count;
		joinLegionInfos = new com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo objjoinLegionInfos = new com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo();
			joinLegionInfos[i] = objjoinLegionInfos;
					objjoinLegionInfos.setLegionId(readLong());
							objjoinLegionInfos.setLegionName(readString());
							objjoinLegionInfos.setOccupyValue(readInteger());
							objjoinLegionInfos.setJoinLegionType(readInteger());
				}
		watchHoldTime = readInteger();
		rankMinOccupyValue = readInteger();
		richRateDesc = readString();
		revenueRate = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainTime);
		writeInteger(meditationEncourageCost);
		writeInteger(crystalEncourageCost);
		writeBoolean(encourageIsFull);
		writeInteger(attackRate);
		writeInteger(defenseRate);
		writeInteger(warExploit);
		writeInteger(rank);
	writeShort(legionBufs.length);
	for(int i=0; i<legionBufs.length; i++){
	com.hifun.soul.gameserver.legionmine.LegionBuf objlegionBufs = legionBufs[i];
				writeInteger(objlegionBufs.getLegionBufType());
				writeInteger(objlegionBufs.getBufIcon());
				writeString(objlegionBufs.getBufDesc());
	}
	writeShort(selfBufs.length);
	for(int i=0; i<selfBufs.length; i++){
	com.hifun.soul.gameserver.legionmine.SelfBuf objselfBufs = selfBufs[i];
				writeInteger(objselfBufs.getSelfBufType());
				writeInteger(objselfBufs.getBufIcon());
				writeString(objselfBufs.getBufDesc());
				writeBoolean(objselfBufs.getUsing());
	}
		writeInteger(occupyMemberNum);
		writeInteger(totalMemberNum);
		writeInteger(currentOccupyValue);
		writeInteger(watchCrystalCost);
	writeShort(mineInfos.length);
	for(int i=0; i<mineInfos.length; i++){
	com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo objmineInfos = mineInfos[i];
				writeInteger(objmineInfos.getMineIndex());
				writeBoolean(objmineInfos.getCanBattle());
				writeBoolean(objmineInfos.getCanMove());
				writeBoolean(objmineInfos.getCanDisturb());
				writeInteger(objmineInfos.getJoinLegionType());
				writeInteger(objmineInfos.getSurroundState());
				writeInteger(objmineInfos.getOccupyNum());
				writeBoolean(objmineInfos.getOccupyNumVisible());
				writeBoolean(objmineInfos.getIsDouble());
				writeBoolean(objmineInfos.getIsRedMine());
				writeBoolean(objmineInfos.getIsSelf());
				writeInteger(objmineInfos.getMineType());
	}
		writeInteger(holdMineIndex);
		writeBoolean(isOverturn);
	writeShort(joinLegionInfos.length);
	for(int i=0; i<joinLegionInfos.length; i++){
	com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo objjoinLegionInfos = joinLegionInfos[i];
				writeLong(objjoinLegionInfos.getLegionId());
				writeString(objjoinLegionInfos.getLegionName());
				writeInteger(objjoinLegionInfos.getOccupyValue());
				writeInteger(objjoinLegionInfos.getJoinLegionType());
	}
		writeInteger(watchHoldTime);
		writeInteger(rankMinOccupyValue);
		writeString(richRateDesc);
		writeInteger(revenueRate);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_LEGION_MINE_WAR_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_LEGION_MINE_WAR_PANEL";
	}

	public int getRemainTime(){
		return remainTime;
	}
		
	public void setRemainTime(int remainTime){
		this.remainTime = remainTime;
	}

	public int getMeditationEncourageCost(){
		return meditationEncourageCost;
	}
		
	public void setMeditationEncourageCost(int meditationEncourageCost){
		this.meditationEncourageCost = meditationEncourageCost;
	}

	public int getCrystalEncourageCost(){
		return crystalEncourageCost;
	}
		
	public void setCrystalEncourageCost(int crystalEncourageCost){
		this.crystalEncourageCost = crystalEncourageCost;
	}

	public boolean getEncourageIsFull(){
		return encourageIsFull;
	}
		
	public void setEncourageIsFull(boolean encourageIsFull){
		this.encourageIsFull = encourageIsFull;
	}

	public int getAttackRate(){
		return attackRate;
	}
		
	public void setAttackRate(int attackRate){
		this.attackRate = attackRate;
	}

	public int getDefenseRate(){
		return defenseRate;
	}
		
	public void setDefenseRate(int defenseRate){
		this.defenseRate = defenseRate;
	}

	public int getWarExploit(){
		return warExploit;
	}
		
	public void setWarExploit(int warExploit){
		this.warExploit = warExploit;
	}

	public int getRank(){
		return rank;
	}
		
	public void setRank(int rank){
		this.rank = rank;
	}

	public com.hifun.soul.gameserver.legionmine.LegionBuf[] getLegionBufs(){
		return legionBufs;
	}

	public void setLegionBufs(com.hifun.soul.gameserver.legionmine.LegionBuf[] legionBufs){
		this.legionBufs = legionBufs;
	}	

	public com.hifun.soul.gameserver.legionmine.SelfBuf[] getSelfBufs(){
		return selfBufs;
	}

	public void setSelfBufs(com.hifun.soul.gameserver.legionmine.SelfBuf[] selfBufs){
		this.selfBufs = selfBufs;
	}	

	public int getOccupyMemberNum(){
		return occupyMemberNum;
	}
		
	public void setOccupyMemberNum(int occupyMemberNum){
		this.occupyMemberNum = occupyMemberNum;
	}

	public int getTotalMemberNum(){
		return totalMemberNum;
	}
		
	public void setTotalMemberNum(int totalMemberNum){
		this.totalMemberNum = totalMemberNum;
	}

	public int getCurrentOccupyValue(){
		return currentOccupyValue;
	}
		
	public void setCurrentOccupyValue(int currentOccupyValue){
		this.currentOccupyValue = currentOccupyValue;
	}

	public int getWatchCrystalCost(){
		return watchCrystalCost;
	}
		
	public void setWatchCrystalCost(int watchCrystalCost){
		this.watchCrystalCost = watchCrystalCost;
	}

	public com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo[] getMineInfos(){
		return mineInfos;
	}

	public void setMineInfos(com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo[] mineInfos){
		this.mineInfos = mineInfos;
	}	

	public int getHoldMineIndex(){
		return holdMineIndex;
	}
		
	public void setHoldMineIndex(int holdMineIndex){
		this.holdMineIndex = holdMineIndex;
	}

	public boolean getIsOverturn(){
		return isOverturn;
	}
		
	public void setIsOverturn(boolean isOverturn){
		this.isOverturn = isOverturn;
	}

	public com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo[] getJoinLegionInfos(){
		return joinLegionInfos;
	}

	public void setJoinLegionInfos(com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo[] joinLegionInfos){
		this.joinLegionInfos = joinLegionInfos;
	}	

	public int getWatchHoldTime(){
		return watchHoldTime;
	}
		
	public void setWatchHoldTime(int watchHoldTime){
		this.watchHoldTime = watchHoldTime;
	}

	public int getRankMinOccupyValue(){
		return rankMinOccupyValue;
	}
		
	public void setRankMinOccupyValue(int rankMinOccupyValue){
		this.rankMinOccupyValue = rankMinOccupyValue;
	}

	public String getRichRateDesc(){
		return richRateDesc;
	}
		
	public void setRichRateDesc(String richRateDesc){
		this.richRateDesc = richRateDesc;
	}

	public int getRevenueRate(){
		return revenueRate;
	}
		
	public void setRevenueRate(int revenueRate){
		this.revenueRate = revenueRate;
	}
}