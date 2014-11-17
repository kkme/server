package com.hifun.soul.gameserver.levy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 收集魔法石任务完成
 *
 * @author SevenSoul
 */
@Component
public class GCCollectMagicStoneComplete extends GCMessage{
	
	/** 收集目标数组 */
	private com.hifun.soul.gameserver.levy.MagicStoneInfo[] collectTarget;
	/** 当前税收收益 */
	private int levyRevenue;
	/** 税收加成 */
	private int levyExtraRate;
	/** 奖励货币类型 */
	private int rewardCurrencyType;
	/** 奖励货币数量 */
	private int rewardCurrencyNum;

	public GCCollectMagicStoneComplete (){
	}
	
	public GCCollectMagicStoneComplete (
			com.hifun.soul.gameserver.levy.MagicStoneInfo[] collectTarget,
			int levyRevenue,
			int levyExtraRate,
			int rewardCurrencyType,
			int rewardCurrencyNum ){
			this.collectTarget = collectTarget;
			this.levyRevenue = levyRevenue;
			this.levyExtraRate = levyExtraRate;
			this.rewardCurrencyType = rewardCurrencyType;
			this.rewardCurrencyNum = rewardCurrencyNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
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
		levyRevenue = readInteger();
		levyExtraRate = readInteger();
		rewardCurrencyType = readInteger();
		rewardCurrencyNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(collectTarget.length);
	for(int i=0; i<collectTarget.length; i++){
	com.hifun.soul.gameserver.levy.MagicStoneInfo objcollectTarget = collectTarget[i];
				writeInteger(objcollectTarget.getId());
				writeInteger(objcollectTarget.getIcon());
				writeBoolean(objcollectTarget.getCollected());
				writeInteger(objcollectTarget.getTargetIndex());
	}
		writeInteger(levyRevenue);
		writeInteger(levyExtraRate);
		writeInteger(rewardCurrencyType);
		writeInteger(rewardCurrencyNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_COLLECT_MAGIC_STONE_COMPLETE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_COLLECT_MAGIC_STONE_COMPLETE";
	}

	public com.hifun.soul.gameserver.levy.MagicStoneInfo[] getCollectTarget(){
		return collectTarget;
	}

	public void setCollectTarget(com.hifun.soul.gameserver.levy.MagicStoneInfo[] collectTarget){
		this.collectTarget = collectTarget;
	}	

	public int getLevyRevenue(){
		return levyRevenue;
	}
		
	public void setLevyRevenue(int levyRevenue){
		this.levyRevenue = levyRevenue;
	}

	public int getLevyExtraRate(){
		return levyExtraRate;
	}
		
	public void setLevyExtraRate(int levyExtraRate){
		this.levyExtraRate = levyExtraRate;
	}

	public int getRewardCurrencyType(){
		return rewardCurrencyType;
	}
		
	public void setRewardCurrencyType(int rewardCurrencyType){
		this.rewardCurrencyType = rewardCurrencyType;
	}

	public int getRewardCurrencyNum(){
		return rewardCurrencyNum;
	}
		
	public void setRewardCurrencyNum(int rewardCurrencyNum){
		this.rewardCurrencyNum = rewardCurrencyNum;
	}
}