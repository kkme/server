package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回的单个任务数据更新的消息
 *
 * @author SevenSoul
 */
@Component
public class GCSingleQuestUpdate extends GCMessage{
	
	/** 更新任务信息 */
	private com.hifun.soul.gameserver.human.quest.QuestInfo quest;

	public GCSingleQuestUpdate (){
	}
	
	public GCSingleQuestUpdate (
			com.hifun.soul.gameserver.human.quest.QuestInfo quest ){
			this.quest = quest;
	}

	@Override
	protected boolean readImpl() {
		quest = new com.hifun.soul.gameserver.human.quest.QuestInfo();
						quest.setQuestId(readInteger());
						quest.setQuestType(readInteger());
						quest.setNpcIcon(readInteger());
						quest.setQuestIcon(readInteger());
						quest.setQuestName(readString());
						quest.setQuestState(readInteger());
						quest.setQuestDesc(readString());
						quest.setQuestAimDesc(readString());
						quest.setMinLevel(readInteger());
						quest.setRewardExp(readInteger());
						quest.setRewardMoney(readInteger());
						quest.setRewardStore(readInteger());
							{
	int subCountquestAims = readShort();
		com.hifun.soul.gameserver.human.quest.QuestAimInfo[] subListquestAims = new com.hifun.soul.gameserver.human.quest.QuestAimInfo[subCountquestAims];
		quest.setQuestAims(subListquestAims);
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
						quest.setStageGuideInfo(readString());
							{
	int subCountquestItems = readShort();
		com.hifun.soul.gameserver.item.assist.SimpleCommonItem[] subListquestItems = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem[subCountquestItems];
		quest.setQuestItems(subListquestItems);
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
						quest.setRemainTime(readInteger());
						quest.setQuantity(readInteger());
						quest.setGameFuncId(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(quest.getQuestId());
		writeInteger(quest.getQuestType());
		writeInteger(quest.getNpcIcon());
		writeInteger(quest.getQuestIcon());
		writeString(quest.getQuestName());
		writeInteger(quest.getQuestState());
		writeString(quest.getQuestDesc());
		writeString(quest.getQuestAimDesc());
		writeInteger(quest.getMinLevel());
		writeInteger(quest.getRewardExp());
		writeInteger(quest.getRewardMoney());
		writeInteger(quest.getRewardStore());
			com.hifun.soul.gameserver.human.quest.QuestAimInfo[] questAims_quest=quest.getQuestAims();
	writeShort(questAims_quest.length);
	for(int jquestAims=0; jquestAims<questAims_quest.length; jquestAims++){
					com.hifun.soul.gameserver.human.quest.QuestAimInfo questAims_quest_jquestAims = questAims_quest[jquestAims];
													writeInteger(questAims_quest_jquestAims.getAimType());
													writeInteger(questAims_quest_jquestAims.getAimIndex());
													writeInteger(questAims_quest_jquestAims.getAimValue());
													writeInteger(questAims_quest_jquestAims.getCurrentValue());
													writeString(questAims_quest_jquestAims.getDesc());
									}
		writeString(quest.getStageGuideInfo());
			com.hifun.soul.gameserver.item.assist.SimpleCommonItem[] questItems_quest=quest.getQuestItems();
	writeShort(questItems_quest.length);
	for(int jquestItems=0; jquestItems<questItems_quest.length; jquestItems++){
					com.hifun.soul.gameserver.item.assist.SimpleCommonItem questItems_quest_jquestItems = questItems_quest[jquestItems];
													writeString(questItems_quest_jquestItems.getUUID());
													writeInteger(questItems_quest_jquestItems.getItemId());
													writeString(questItems_quest_jquestItems.getName());
													writeString(questItems_quest_jquestItems.getDesc());
													writeInteger(questItems_quest_jquestItems.getIcon());
									}
		writeInteger(quest.getRemainTime());
		writeInteger(quest.getQuantity());
		writeInteger(quest.getGameFuncId());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SINGLE_QUEST_UPDATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SINGLE_QUEST_UPDATE";
	}

	public com.hifun.soul.gameserver.human.quest.QuestInfo getQuest(){
		return quest;
	}
		
	public void setQuest(com.hifun.soul.gameserver.human.quest.QuestInfo quest){
		this.quest = quest;
	}
}