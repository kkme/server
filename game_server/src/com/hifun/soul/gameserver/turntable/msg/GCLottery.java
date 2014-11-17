package com.hifun.soul.gameserver.turntable.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 转盘抽奖
 *
 * @author SevenSoul
 */
@Component
public class GCLottery extends GCMessage{
	
	/** 魔晶抽奖次数 */
	private int crystalLotteryRemainTimes;
	/** 抽奖消耗的魔晶数 */
	private int crystalCost;
	/** 选中的物品 */
	private int selectIndex;
	/** 抽取的奖品信息 */
	private com.hifun.soul.gameserver.turntable.msg.TurntableRewardInfo[] rewards;

	public GCLottery (){
	}
	
	public GCLottery (
			int crystalLotteryRemainTimes,
			int crystalCost,
			int selectIndex,
			com.hifun.soul.gameserver.turntable.msg.TurntableRewardInfo[] rewards ){
			this.crystalLotteryRemainTimes = crystalLotteryRemainTimes;
			this.crystalCost = crystalCost;
			this.selectIndex = selectIndex;
			this.rewards = rewards;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		crystalLotteryRemainTimes = readInteger();
		crystalCost = readInteger();
		selectIndex = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		rewards = new com.hifun.soul.gameserver.turntable.msg.TurntableRewardInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.turntable.msg.TurntableRewardInfo objrewards = new com.hifun.soul.gameserver.turntable.msg.TurntableRewardInfo();
			rewards[i] = objrewards;
					objrewards.setId(readInteger());
							objrewards.setRoleName(readString());
							objrewards.setRewardName(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(crystalLotteryRemainTimes);
		writeInteger(crystalCost);
		writeInteger(selectIndex);
	writeShort(rewards.length);
	for(int i=0; i<rewards.length; i++){
	com.hifun.soul.gameserver.turntable.msg.TurntableRewardInfo objrewards = rewards[i];
				writeInteger(objrewards.getId());
				writeString(objrewards.getRoleName());
				writeString(objrewards.getRewardName());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LOTTERY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LOTTERY";
	}

	public int getCrystalLotteryRemainTimes(){
		return crystalLotteryRemainTimes;
	}
		
	public void setCrystalLotteryRemainTimes(int crystalLotteryRemainTimes){
		this.crystalLotteryRemainTimes = crystalLotteryRemainTimes;
	}

	public int getCrystalCost(){
		return crystalCost;
	}
		
	public void setCrystalCost(int crystalCost){
		this.crystalCost = crystalCost;
	}

	public int getSelectIndex(){
		return selectIndex;
	}
		
	public void setSelectIndex(int selectIndex){
		this.selectIndex = selectIndex;
	}

	public com.hifun.soul.gameserver.turntable.msg.TurntableRewardInfo[] getRewards(){
		return rewards;
	}

	public void setRewards(com.hifun.soul.gameserver.turntable.msg.TurntableRewardInfo[] rewards){
		this.rewards = rewards;
	}	
}