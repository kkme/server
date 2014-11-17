package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务端通知追加新的任务
 *
 * @author SevenSoul
 */
@Component
public class GCAppendNewQuests extends GCMessage{
	
	/** 追加的任务列表 */
	private com.hifun.soul.gameserver.human.quest.QuestInfo[] questList;

	public GCAppendNewQuests (){
	}
	
	public GCAppendNewQuests (
			com.hifun.soul.gameserver.human.quest.QuestInfo[] questList ){
			this.questList = questList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		questList = new com.hifun.soul.gameserver.human.quest.QuestInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.human.quest.QuestInfo objquestList = new com.hifun.soul.gameserver.human.quest.QuestInfo();
			questList[i] = objquestList;
					objquestList.setQuestId(readInteger());
							objquestList.setQuestType(readInteger());
							objquestList.setNpcIcon(readInteger());
							objquestList.setQuestIcon(readInteger());
							objquestList.setQuestName(readString());
							objquestList.setQuestState(readInteger());
							objquestList.setQuestDesc(readString());
							objquestList.setQuestAimDesc(readString());
							objquestList.setMinLevel(readInteger());
							objquestList.setRewardExp(readInteger());
							objquestList.setRewardMoney(readInteger());
							objquestList.setRewardStore(readInteger());
								{
	int subCountquestAims = readShort();
		com.hifun.soul.gameserver.human.quest.QuestAimInfo[] subListquestAims = new com.hifun.soul.gameserver.human.quest.QuestAimInfo[subCountquestAims];
		objquestList.setQuestAims(subListquestAims);
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
							objquestList.setStageGuideInfo(readString());
								{
	int subCountquestItems = readShort();
		com.hifun.soul.gameserver.item.assist.SimpleCommonItem[] subListquestItems = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem[subCountquestItems];
		objquestList.setQuestItems(subListquestItems);
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
							objquestList.setRemainTime(readInteger());
							objquestList.setQuantity(readInteger());
							objquestList.setGameFuncId(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(questList.length);
	for(int i=0; i<questList.length; i++){
	com.hifun.soul.gameserver.human.quest.QuestInfo objquestList = questList[i];
				writeInteger(objquestList.getQuestId());
				writeInteger(objquestList.getQuestType());
				writeInteger(objquestList.getNpcIcon());
				writeInteger(objquestList.getQuestIcon());
				writeString(objquestList.getQuestName());
				writeInteger(objquestList.getQuestState());
				writeString(objquestList.getQuestDesc());
				writeString(objquestList.getQuestAimDesc());
				writeInteger(objquestList.getMinLevel());
				writeInteger(objquestList.getRewardExp());
				writeInteger(objquestList.getRewardMoney());
				writeInteger(objquestList.getRewardStore());
					com.hifun.soul.gameserver.human.quest.QuestAimInfo[] questAims_objquestList=objquestList.getQuestAims();
	writeShort(questAims_objquestList.length);
	for(int jquestAims=0; jquestAims<questAims_objquestList.length; jquestAims++){
					com.hifun.soul.gameserver.human.quest.QuestAimInfo questAims_objquestList_jquestAims = questAims_objquestList[jquestAims];
													writeInteger(questAims_objquestList_jquestAims.getAimType());
													writeInteger(questAims_objquestList_jquestAims.getAimIndex());
													writeInteger(questAims_objquestList_jquestAims.getAimValue());
													writeInteger(questAims_objquestList_jquestAims.getCurrentValue());
													writeString(questAims_objquestList_jquestAims.getDesc());
									}
				writeString(objquestList.getStageGuideInfo());
					com.hifun.soul.gameserver.item.assist.SimpleCommonItem[] questItems_objquestList=objquestList.getQuestItems();
	writeShort(questItems_objquestList.length);
	for(int jquestItems=0; jquestItems<questItems_objquestList.length; jquestItems++){
					com.hifun.soul.gameserver.item.assist.SimpleCommonItem questItems_objquestList_jquestItems = questItems_objquestList[jquestItems];
													writeString(questItems_objquestList_jquestItems.getUUID());
													writeInteger(questItems_objquestList_jquestItems.getItemId());
													writeString(questItems_objquestList_jquestItems.getName());
													writeString(questItems_objquestList_jquestItems.getDesc());
													writeInteger(questItems_objquestList_jquestItems.getIcon());
									}
				writeInteger(objquestList.getRemainTime());
				writeInteger(objquestList.getQuantity());
				writeInteger(objquestList.getGameFuncId());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_APPEND_NEW_QUESTS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_APPEND_NEW_QUESTS";
	}

	public com.hifun.soul.gameserver.human.quest.QuestInfo[] getQuestList(){
		return questList;
	}

	public void setQuestList(com.hifun.soul.gameserver.human.quest.QuestInfo[] questList){
		this.questList = questList;
	}	
}