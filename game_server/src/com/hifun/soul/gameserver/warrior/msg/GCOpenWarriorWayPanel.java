package com.hifun.soul.gameserver.warrior.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新勇士之路面板信息
 *
 * @author SevenSoul
 */
@Component
public class GCOpenWarriorWayPanel extends GCMessage{
	
	/** 任务列表 */
	private com.hifun.soul.gameserver.warrior.WarriorQuest[] warriorQuests;
	/** 奖励 */
	private com.hifun.soul.gameserver.warrior.WarriorQuestReward[] rewards;
	/** 对手Id */
	private long opponentId;
	/** 对手名称 */
	private String opponentName;
	/** 对手职业 */
	private int opponentOccupation;
	/** 对手等级 */
	private int opponentLevel;
	/** 对手类型 */
	private int opponentType;
	/** 战胜获得勇者之心数 */
	private int battleWinRewardNum;
	/** 所有的任务奖励 */
	private com.hifun.soul.gameserver.warrior.WarriorQuestReward[] allRewards;

	public GCOpenWarriorWayPanel (){
	}
	
	public GCOpenWarriorWayPanel (
			com.hifun.soul.gameserver.warrior.WarriorQuest[] warriorQuests,
			com.hifun.soul.gameserver.warrior.WarriorQuestReward[] rewards,
			long opponentId,
			String opponentName,
			int opponentOccupation,
			int opponentLevel,
			int opponentType,
			int battleWinRewardNum,
			com.hifun.soul.gameserver.warrior.WarriorQuestReward[] allRewards ){
			this.warriorQuests = warriorQuests;
			this.rewards = rewards;
			this.opponentId = opponentId;
			this.opponentName = opponentName;
			this.opponentOccupation = opponentOccupation;
			this.opponentLevel = opponentLevel;
			this.opponentType = opponentType;
			this.battleWinRewardNum = battleWinRewardNum;
			this.allRewards = allRewards;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		warriorQuests = new com.hifun.soul.gameserver.warrior.WarriorQuest[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.warrior.WarriorQuest objwarriorQuests = new com.hifun.soul.gameserver.warrior.WarriorQuest();
			warriorQuests[i] = objwarriorQuests;
					objwarriorQuests.setId(readInteger());
							objwarriorQuests.setTotalCount(readInteger());
							objwarriorQuests.setCompleteCount(readInteger());
							objwarriorQuests.setDamageHpPercent(readInteger());
							objwarriorQuests.setQuestState(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		rewards = new com.hifun.soul.gameserver.warrior.WarriorQuestReward[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.warrior.WarriorQuestReward objrewards = new com.hifun.soul.gameserver.warrior.WarriorQuestReward();
			rewards[i] = objrewards;
					objrewards.setQuestId(readInteger());
							objrewards.setCoin(readInteger());
							objrewards.setExp(readInteger());
							objrewards.setTechPoint(readInteger());
							objrewards.setState(readInteger());
				}
		opponentId = readLong();
		opponentName = readString();
		opponentOccupation = readInteger();
		opponentLevel = readInteger();
		opponentType = readInteger();
		battleWinRewardNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		allRewards = new com.hifun.soul.gameserver.warrior.WarriorQuestReward[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.warrior.WarriorQuestReward objallRewards = new com.hifun.soul.gameserver.warrior.WarriorQuestReward();
			allRewards[i] = objallRewards;
					objallRewards.setQuestId(readInteger());
							objallRewards.setCoin(readInteger());
							objallRewards.setExp(readInteger());
							objallRewards.setTechPoint(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(warriorQuests.length);
	for(int i=0; i<warriorQuests.length; i++){
	com.hifun.soul.gameserver.warrior.WarriorQuest objwarriorQuests = warriorQuests[i];
				writeInteger(objwarriorQuests.getId());
				writeInteger(objwarriorQuests.getTotalCount());
				writeInteger(objwarriorQuests.getCompleteCount());
				writeInteger(objwarriorQuests.getDamageHpPercent());
				writeInteger(objwarriorQuests.getQuestState());
	}
	writeShort(rewards.length);
	for(int i=0; i<rewards.length; i++){
	com.hifun.soul.gameserver.warrior.WarriorQuestReward objrewards = rewards[i];
				writeInteger(objrewards.getQuestId());
				writeInteger(objrewards.getCoin());
				writeInteger(objrewards.getExp());
				writeInteger(objrewards.getTechPoint());
				writeInteger(objrewards.getState());
	}
		writeLong(opponentId);
		writeString(opponentName);
		writeInteger(opponentOccupation);
		writeInteger(opponentLevel);
		writeInteger(opponentType);
		writeInteger(battleWinRewardNum);
	writeShort(allRewards.length);
	for(int i=0; i<allRewards.length; i++){
	com.hifun.soul.gameserver.warrior.WarriorQuestReward objallRewards = allRewards[i];
				writeInteger(objallRewards.getQuestId());
				writeInteger(objallRewards.getCoin());
				writeInteger(objallRewards.getExp());
				writeInteger(objallRewards.getTechPoint());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_WARRIOR_WAY_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_WARRIOR_WAY_PANEL";
	}

	public com.hifun.soul.gameserver.warrior.WarriorQuest[] getWarriorQuests(){
		return warriorQuests;
	}

	public void setWarriorQuests(com.hifun.soul.gameserver.warrior.WarriorQuest[] warriorQuests){
		this.warriorQuests = warriorQuests;
	}	

	public com.hifun.soul.gameserver.warrior.WarriorQuestReward[] getRewards(){
		return rewards;
	}

	public void setRewards(com.hifun.soul.gameserver.warrior.WarriorQuestReward[] rewards){
		this.rewards = rewards;
	}	

	public long getOpponentId(){
		return opponentId;
	}
		
	public void setOpponentId(long opponentId){
		this.opponentId = opponentId;
	}

	public String getOpponentName(){
		return opponentName;
	}
		
	public void setOpponentName(String opponentName){
		this.opponentName = opponentName;
	}

	public int getOpponentOccupation(){
		return opponentOccupation;
	}
		
	public void setOpponentOccupation(int opponentOccupation){
		this.opponentOccupation = opponentOccupation;
	}

	public int getOpponentLevel(){
		return opponentLevel;
	}
		
	public void setOpponentLevel(int opponentLevel){
		this.opponentLevel = opponentLevel;
	}

	public int getOpponentType(){
		return opponentType;
	}
		
	public void setOpponentType(int opponentType){
		this.opponentType = opponentType;
	}

	public int getBattleWinRewardNum(){
		return battleWinRewardNum;
	}
		
	public void setBattleWinRewardNum(int battleWinRewardNum){
		this.battleWinRewardNum = battleWinRewardNum;
	}

	public com.hifun.soul.gameserver.warrior.WarriorQuestReward[] getAllRewards(){
		return allRewards;
	}

	public void setAllRewards(com.hifun.soul.gameserver.warrior.WarriorQuestReward[] allRewards){
		this.allRewards = allRewards;
	}	
}