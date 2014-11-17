package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应领取日常任务奖励
 *
 * @author SevenSoul
 */
@Component
public class GCGetDailyQuestReward extends GCMessage{
	
	/** 任务ID */
	private int oldQuestId;
	/** 日常任务 */
	private com.hifun.soul.gameserver.human.quest.QuestInfo newQuestInfo;
	/** 剩余免费任务数量 */
	private int remainFreeQuestNum;
	/** 接受任务消费 */
	private int receiveQuestCost;
	/** 剩余魔晶任务数量 */
	private int remainCrystalQuestNum;

	public GCGetDailyQuestReward (){
	}
	
	public GCGetDailyQuestReward (
			int oldQuestId,
			com.hifun.soul.gameserver.human.quest.QuestInfo newQuestInfo,
			int remainFreeQuestNum,
			int receiveQuestCost,
			int remainCrystalQuestNum ){
			this.oldQuestId = oldQuestId;
			this.newQuestInfo = newQuestInfo;
			this.remainFreeQuestNum = remainFreeQuestNum;
			this.receiveQuestCost = receiveQuestCost;
			this.remainCrystalQuestNum = remainCrystalQuestNum;
	}

	@Override
	protected boolean readImpl() {
		oldQuestId = readInteger();
		newQuestInfo = new com.hifun.soul.gameserver.human.quest.QuestInfo();
						newQuestInfo.setQuestId(readInteger());
						newQuestInfo.setQuestType(readInteger());
						newQuestInfo.setNpcIcon(readInteger());
						newQuestInfo.setQuestIcon(readInteger());
						newQuestInfo.setQuestName(readString());
						newQuestInfo.setQuestState(readInteger());
						newQuestInfo.setQuestDesc(readString());
						newQuestInfo.setQuestAimDesc(readString());
						newQuestInfo.setMinLevel(readInteger());
						newQuestInfo.setRewardExp(readInteger());
						newQuestInfo.setRewardMoney(readInteger());
						newQuestInfo.setRewardStore(readInteger());
							{
	int subCountquestAims = readShort();
		com.hifun.soul.gameserver.human.quest.QuestAimInfo[] subListquestAims = new com.hifun.soul.gameserver.human.quest.QuestAimInfo[subCountquestAims];
		newQuestInfo.setQuestAims(subListquestAims);
	for(int jquestAims = 0; jquestAims < subCountquestAims; jquestAims++){
						com.hifun.soul.gameserver.human.quest.QuestAimInfo objquestAims = new com.hifun.soul.gameserver.human.quest.QuestAimInfo();
		subListquestAims[jquestAims] = objquestAims;
						objquestAims.setAimType(readInteger());
								objquestAims.setAimIndex(readInteger());
								objquestAims.setAimValue(readInteger());
								objquestAims.setCurrentValue(readInteger());
								objquestAims.setDesc(readString());
							}
	}
						newQuestInfo.setStageGuideInfo(readString());
							{
	int subCountquestItems = readShort();
		com.hifun.soul.gameserver.item.assist.SimpleCommonItem[] subListquestItems = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem[subCountquestItems];
		newQuestInfo.setQuestItems(subListquestItems);
	for(int jquestItems = 0; jquestItems < subCountquestItems; jquestItems++){
						com.hifun.soul.gameserver.item.assist.SimpleCommonItem objquestItems = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem();
		subListquestItems[jquestItems] = objquestItems;
						objquestItems.setUUID(readString());
								objquestItems.setItemId(readInteger());
								objquestItems.setName(readString());
								objquestItems.setDesc(readString());
								objquestItems.setIcon(readInteger());
							}
	}
						newQuestInfo.setRemainTime(readInteger());
						newQuestInfo.setQuantity(readInteger());
						newQuestInfo.setGameFuncId(readInteger());
				remainFreeQuestNum = readInteger();
		receiveQuestCost = readInteger();
		remainCrystalQuestNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(oldQuestId);
		writeInteger(newQuestInfo.getQuestId());
		writeInteger(newQuestInfo.getQuestType());
		writeInteger(newQuestInfo.getNpcIcon());
		writeInteger(newQuestInfo.getQuestIcon());
		writeString(newQuestInfo.getQuestName());
		writeInteger(newQuestInfo.getQuestState());
		writeString(newQuestInfo.getQuestDesc());
		writeString(newQuestInfo.getQuestAimDesc());
		writeInteger(newQuestInfo.getMinLevel());
		writeInteger(newQuestInfo.getRewardExp());
		writeInteger(newQuestInfo.getRewardMoney());
		writeInteger(newQuestInfo.getRewardStore());
			com.hifun.soul.gameserver.human.quest.QuestAimInfo[] questAims_newQuestInfo=newQuestInfo.getQuestAims();
	writeShort(questAims_newQuestInfo.length);
	for(int jquestAims=0; jquestAims<questAims_newQuestInfo.length; jquestAims++){
					com.hifun.soul.gameserver.human.quest.QuestAimInfo questAims_newQuestInfo_jquestAims = questAims_newQuestInfo[jquestAims];
													writeInteger(questAims_newQuestInfo_jquestAims.getAimType());
													writeInteger(questAims_newQuestInfo_jquestAims.getAimIndex());
													writeInteger(questAims_newQuestInfo_jquestAims.getAimValue());
													writeInteger(questAims_newQuestInfo_jquestAims.getCurrentValue());
													writeString(questAims_newQuestInfo_jquestAims.getDesc());
									}
		writeString(newQuestInfo.getStageGuideInfo());
			com.hifun.soul.gameserver.item.assist.SimpleCommonItem[] questItems_newQuestInfo=newQuestInfo.getQuestItems();
	writeShort(questItems_newQuestInfo.length);
	for(int jquestItems=0; jquestItems<questItems_newQuestInfo.length; jquestItems++){
					com.hifun.soul.gameserver.item.assist.SimpleCommonItem questItems_newQuestInfo_jquestItems = questItems_newQuestInfo[jquestItems];
													writeString(questItems_newQuestInfo_jquestItems.getUUID());
													writeInteger(questItems_newQuestInfo_jquestItems.getItemId());
													writeString(questItems_newQuestInfo_jquestItems.getName());
													writeString(questItems_newQuestInfo_jquestItems.getDesc());
													writeInteger(questItems_newQuestInfo_jquestItems.getIcon());
									}
		writeInteger(newQuestInfo.getRemainTime());
		writeInteger(newQuestInfo.getQuantity());
		writeInteger(newQuestInfo.getGameFuncId());
		writeInteger(remainFreeQuestNum);
		writeInteger(receiveQuestCost);
		writeInteger(remainCrystalQuestNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_DAILY_QUEST_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_DAILY_QUEST_REWARD";
	}

	public int getOldQuestId(){
		return oldQuestId;
	}
		
	public void setOldQuestId(int oldQuestId){
		this.oldQuestId = oldQuestId;
	}

	public com.hifun.soul.gameserver.human.quest.QuestInfo getNewQuestInfo(){
		return newQuestInfo;
	}
		
	public void setNewQuestInfo(com.hifun.soul.gameserver.human.quest.QuestInfo newQuestInfo){
		this.newQuestInfo = newQuestInfo;
	}

	public int getRemainFreeQuestNum(){
		return remainFreeQuestNum;
	}
		
	public void setRemainFreeQuestNum(int remainFreeQuestNum){
		this.remainFreeQuestNum = remainFreeQuestNum;
	}

	public int getReceiveQuestCost(){
		return receiveQuestCost;
	}
		
	public void setReceiveQuestCost(int receiveQuestCost){
		this.receiveQuestCost = receiveQuestCost;
	}

	public int getRemainCrystalQuestNum(){
		return remainCrystalQuestNum;
	}
		
	public void setRemainCrystalQuestNum(int remainCrystalQuestNum){
		this.remainCrystalQuestNum = remainCrystalQuestNum;
	}
}