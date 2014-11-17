package com.hifun.soul.gameserver.levy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 刷新收集信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateMainCityPanel extends GCMessage{
	
	/** 剩余征收次数 */
	private int remainLevyNum;
	/** 最大可征收次数 */
	private int maxLevyNum;
	/** 税收加成 */
	private int levyExtraRate;
	/** 税收加成上限 */
	private int maxLevyExtraRate;
	/** 当前税收收益 */
	private int levyRevenue;
	/** 剩余押注次数 */
	private int remainBetNum;
	/** 最大押注次数 */
	private int maxtBetNum;
	/** 骰子点数 */
	private int[] betPoints;
	/** 剩余必胜次数 */
	private int remainCertainWinNum;
	/** 必胜消费魔晶 */
	private int certainWinCost;
	/** 收集目标数组 */
	private com.hifun.soul.gameserver.levy.MagicStoneInfo[] collectTarget;
	/** 当前已收集数组 */
	private com.hifun.soul.gameserver.levy.MagicStoneInfo[] currentCollected;
	/** 免费收集次数 */
	private int freeCollectNum;
	/** 收集需要消耗的魔晶 */
	private int costCrystalNum;
	/** 魔晶拣选剩余次数 */
	private int crystalCollectRemainNum;
	/** 每次恢复体力点数 */
	private int recoverEnergyNum;
	/** 体力恢复剩余次数 */
	private int remainRecoverNum;
	/** 每日体力恢复最大次数 */
	private int maxRecoverNum;

	public GCUpdateMainCityPanel (){
	}
	
	public GCUpdateMainCityPanel (
			int remainLevyNum,
			int maxLevyNum,
			int levyExtraRate,
			int maxLevyExtraRate,
			int levyRevenue,
			int remainBetNum,
			int maxtBetNum,
			int[] betPoints,
			int remainCertainWinNum,
			int certainWinCost,
			com.hifun.soul.gameserver.levy.MagicStoneInfo[] collectTarget,
			com.hifun.soul.gameserver.levy.MagicStoneInfo[] currentCollected,
			int freeCollectNum,
			int costCrystalNum,
			int crystalCollectRemainNum,
			int recoverEnergyNum,
			int remainRecoverNum,
			int maxRecoverNum ){
			this.remainLevyNum = remainLevyNum;
			this.maxLevyNum = maxLevyNum;
			this.levyExtraRate = levyExtraRate;
			this.maxLevyExtraRate = maxLevyExtraRate;
			this.levyRevenue = levyRevenue;
			this.remainBetNum = remainBetNum;
			this.maxtBetNum = maxtBetNum;
			this.betPoints = betPoints;
			this.remainCertainWinNum = remainCertainWinNum;
			this.certainWinCost = certainWinCost;
			this.collectTarget = collectTarget;
			this.currentCollected = currentCollected;
			this.freeCollectNum = freeCollectNum;
			this.costCrystalNum = costCrystalNum;
			this.crystalCollectRemainNum = crystalCollectRemainNum;
			this.recoverEnergyNum = recoverEnergyNum;
			this.remainRecoverNum = remainRecoverNum;
			this.maxRecoverNum = maxRecoverNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		remainLevyNum = readInteger();
		maxLevyNum = readInteger();
		levyExtraRate = readInteger();
		maxLevyExtraRate = readInteger();
		levyRevenue = readInteger();
		remainBetNum = readInteger();
		maxtBetNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		betPoints = new int[count];
		for(int i=0; i<count; i++){
			betPoints[i] = readInteger();
		}
		remainCertainWinNum = readInteger();
		certainWinCost = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		collectTarget = new com.hifun.soul.gameserver.levy.MagicStoneInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.levy.MagicStoneInfo objcollectTarget = new com.hifun.soul.gameserver.levy.MagicStoneInfo();
			collectTarget[i] = objcollectTarget;
					objcollectTarget.setId(readInteger());
							objcollectTarget.setIcon(readInteger());
							objcollectTarget.setCollected(readBoolean());
							objcollectTarget.setTargetIndex(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		currentCollected = new com.hifun.soul.gameserver.levy.MagicStoneInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.levy.MagicStoneInfo objcurrentCollected = new com.hifun.soul.gameserver.levy.MagicStoneInfo();
			currentCollected[i] = objcurrentCollected;
					objcurrentCollected.setId(readInteger());
							objcurrentCollected.setIcon(readInteger());
							objcurrentCollected.setCollected(readBoolean());
							objcurrentCollected.setTargetIndex(readInteger());
				}
		freeCollectNum = readInteger();
		costCrystalNum = readInteger();
		crystalCollectRemainNum = readInteger();
		recoverEnergyNum = readInteger();
		remainRecoverNum = readInteger();
		maxRecoverNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainLevyNum);
		writeInteger(maxLevyNum);
		writeInteger(levyExtraRate);
		writeInteger(maxLevyExtraRate);
		writeInteger(levyRevenue);
		writeInteger(remainBetNum);
		writeInteger(maxtBetNum);
	writeShort(betPoints.length);
	for(int i=0; i<betPoints.length; i++){
	Integer objbetPoints = betPoints[i];
			writeInteger(objbetPoints);
}
		writeInteger(remainCertainWinNum);
		writeInteger(certainWinCost);
	writeShort(collectTarget.length);
	for(int i=0; i<collectTarget.length; i++){
	com.hifun.soul.gameserver.levy.MagicStoneInfo objcollectTarget = collectTarget[i];
				writeInteger(objcollectTarget.getId());
				writeInteger(objcollectTarget.getIcon());
				writeBoolean(objcollectTarget.getCollected());
				writeInteger(objcollectTarget.getTargetIndex());
	}
	writeShort(currentCollected.length);
	for(int i=0; i<currentCollected.length; i++){
	com.hifun.soul.gameserver.levy.MagicStoneInfo objcurrentCollected = currentCollected[i];
				writeInteger(objcurrentCollected.getId());
				writeInteger(objcurrentCollected.getIcon());
				writeBoolean(objcurrentCollected.getCollected());
				writeInteger(objcurrentCollected.getTargetIndex());
	}
		writeInteger(freeCollectNum);
		writeInteger(costCrystalNum);
		writeInteger(crystalCollectRemainNum);
		writeInteger(recoverEnergyNum);
		writeInteger(remainRecoverNum);
		writeInteger(maxRecoverNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_MAIN_CITY_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_MAIN_CITY_PANEL";
	}

	public int getRemainLevyNum(){
		return remainLevyNum;
	}
		
	public void setRemainLevyNum(int remainLevyNum){
		this.remainLevyNum = remainLevyNum;
	}

	public int getMaxLevyNum(){
		return maxLevyNum;
	}
		
	public void setMaxLevyNum(int maxLevyNum){
		this.maxLevyNum = maxLevyNum;
	}

	public int getLevyExtraRate(){
		return levyExtraRate;
	}
		
	public void setLevyExtraRate(int levyExtraRate){
		this.levyExtraRate = levyExtraRate;
	}

	public int getMaxLevyExtraRate(){
		return maxLevyExtraRate;
	}
		
	public void setMaxLevyExtraRate(int maxLevyExtraRate){
		this.maxLevyExtraRate = maxLevyExtraRate;
	}

	public int getLevyRevenue(){
		return levyRevenue;
	}
		
	public void setLevyRevenue(int levyRevenue){
		this.levyRevenue = levyRevenue;
	}

	public int getRemainBetNum(){
		return remainBetNum;
	}
		
	public void setRemainBetNum(int remainBetNum){
		this.remainBetNum = remainBetNum;
	}

	public int getMaxtBetNum(){
		return maxtBetNum;
	}
		
	public void setMaxtBetNum(int maxtBetNum){
		this.maxtBetNum = maxtBetNum;
	}

	public int[] getBetPoints(){
		return betPoints;
	}

	public void setBetPoints(int[] betPoints){
		this.betPoints = betPoints;
	}	

	public int getRemainCertainWinNum(){
		return remainCertainWinNum;
	}
		
	public void setRemainCertainWinNum(int remainCertainWinNum){
		this.remainCertainWinNum = remainCertainWinNum;
	}

	public int getCertainWinCost(){
		return certainWinCost;
	}
		
	public void setCertainWinCost(int certainWinCost){
		this.certainWinCost = certainWinCost;
	}

	public com.hifun.soul.gameserver.levy.MagicStoneInfo[] getCollectTarget(){
		return collectTarget;
	}

	public void setCollectTarget(com.hifun.soul.gameserver.levy.MagicStoneInfo[] collectTarget){
		this.collectTarget = collectTarget;
	}	

	public com.hifun.soul.gameserver.levy.MagicStoneInfo[] getCurrentCollected(){
		return currentCollected;
	}

	public void setCurrentCollected(com.hifun.soul.gameserver.levy.MagicStoneInfo[] currentCollected){
		this.currentCollected = currentCollected;
	}	

	public int getFreeCollectNum(){
		return freeCollectNum;
	}
		
	public void setFreeCollectNum(int freeCollectNum){
		this.freeCollectNum = freeCollectNum;
	}

	public int getCostCrystalNum(){
		return costCrystalNum;
	}
		
	public void setCostCrystalNum(int costCrystalNum){
		this.costCrystalNum = costCrystalNum;
	}

	public int getCrystalCollectRemainNum(){
		return crystalCollectRemainNum;
	}
		
	public void setCrystalCollectRemainNum(int crystalCollectRemainNum){
		this.crystalCollectRemainNum = crystalCollectRemainNum;
	}

	public int getRecoverEnergyNum(){
		return recoverEnergyNum;
	}
		
	public void setRecoverEnergyNum(int recoverEnergyNum){
		this.recoverEnergyNum = recoverEnergyNum;
	}

	public int getRemainRecoverNum(){
		return remainRecoverNum;
	}
		
	public void setRemainRecoverNum(int remainRecoverNum){
		this.remainRecoverNum = remainRecoverNum;
	}

	public int getMaxRecoverNum(){
		return maxRecoverNum;
	}
		
	public void setMaxRecoverNum(int maxRecoverNum){
		this.maxRecoverNum = maxRecoverNum;
	}
}