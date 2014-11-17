package com.hifun.soul.gameserver.quest.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回日常任务列表
 *
 * @author SevenSoul
 */
@Component
public class GCDailyQuestList extends GCMessage{
	
	/** 当前积分 */
	private int currentStore;
	/** 日常任务列表 */
	private com.hifun.soul.gameserver.human.quest.QuestInfo[] questList;
	/** 日常任务奖励 */
	private com.hifun.soul.gameserver.human.quest.daily.DailyScoreRewardInfo[] dailyRewardList;
	/** 剩余免费任务数量 */
	private int remainFreeQuestNum;
	/** 总任务数量 */
	private int totalQuestNum;
	/** 接受任务消费 */
	private int receiveQuestCost;
	/** 刷新消费 */
	private int refreshQuestCost;
	/** 剩余魔晶任务数量 */
	private int remainCrystalQuestNum;

	public GCDailyQuestList (){
	}
	
	public GCDailyQuestList (
			int currentStore,
			com.hifun.soul.gameserver.human.quest.QuestInfo[] questList,
			com.hifun.soul.gameserver.human.quest.daily.DailyScoreRewardInfo[] dailyRewardList,
			int remainFreeQuestNum,
			int totalQuestNum,
			int receiveQuestCost,
			int refreshQuestCost,
			int remainCrystalQuestNum ){
			this.currentStore = currentStore;
			this.questList = questList;
			this.dailyRewardList = dailyRewardList;
			this.remainFreeQuestNum = remainFreeQuestNum;
			this.totalQuestNum = totalQuestNum;
			this.receiveQuestCost = receiveQuestCost;
			this.refreshQuestCost = refreshQuestCost;
			this.remainCrystalQuestNum = remainCrystalQuestNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		currentStore = readInteger();
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
		count = readShort();
		count = count < 0 ? 0 : count;
		dailyRewardList = new com.hifun.soul.gameserver.human.quest.daily.DailyScoreRewardInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.human.quest.daily.DailyScoreRewardInfo objdailyRewardList = new com.hifun.soul.gameserver.human.quest.daily.DailyScoreRewardInfo();
			dailyRewardList[i] = objdailyRewardList;
					objdailyRewardList.setBoxId(readInteger());
							objdailyRewardList.setState(readInteger());
							objdailyRewardList.setScoreLimit(readInteger());
							objdailyRewardList.setRewardMoney(readInteger());
							objdailyRewardList.setTip(readString());
								{
	int subCountdailyRewardItems = readShort();
		com.hifun.soul.gameserver.human.quest.daily.DailyItemReward[] subListdailyRewardItems = new com.hifun.soul.gameserver.human.quest.daily.DailyItemReward[subCountdailyRewardItems];
		objdailyRewardList.setDailyRewardItems(subListdailyRewardItems);
	for(int jdailyRewardItems = 0; jdailyRewardItems < subCountdailyRewardItems; jdailyRewardItems++){
						com.hifun.soul.gameserver.human.quest.daily.DailyItemReward objdailyRewardItems = new com.hifun.soul.gameserver.human.quest.daily.DailyItemReward();
		subListdailyRewardItems[jdailyRewardItems] = objdailyRewardItems;
						objdailyRewardItems.setItemId(readInteger());
								objdailyRewardItems.setItemCount(readInteger());
							}
	}
				}
		remainFreeQuestNum = readInteger();
		totalQuestNum = readInteger();
		receiveQuestCost = readInteger();
		refreshQuestCost = readInteger();
		remainCrystalQuestNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(currentStore);
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
	writeShort(dailyRewardList.length);
	for(int i=0; i<dailyRewardList.length; i++){
	com.hifun.soul.gameserver.human.quest.daily.DailyScoreRewardInfo objdailyRewardList = dailyRewardList[i];
				writeInteger(objdailyRewardList.getBoxId());
				writeInteger(objdailyRewardList.getState());
				writeInteger(objdailyRewardList.getScoreLimit());
				writeInteger(objdailyRewardList.getRewardMoney());
				writeString(objdailyRewardList.getTip());
					com.hifun.soul.gameserver.human.quest.daily.DailyItemReward[] dailyRewardItems_objdailyRewardList=objdailyRewardList.getDailyRewardItems();
	writeShort(dailyRewardItems_objdailyRewardList.length);
	for(int jdailyRewardItems=0; jdailyRewardItems<dailyRewardItems_objdailyRewardList.length; jdailyRewardItems++){
					com.hifun.soul.gameserver.human.quest.daily.DailyItemReward dailyRewardItems_objdailyRewardList_jdailyRewardItems = dailyRewardItems_objdailyRewardList[jdailyRewardItems];
													writeInteger(dailyRewardItems_objdailyRewardList_jdailyRewardItems.getItemId());
													writeInteger(dailyRewardItems_objdailyRewardList_jdailyRewardItems.getItemCount());
									}
	}
		writeInteger(remainFreeQuestNum);
		writeInteger(totalQuestNum);
		writeInteger(receiveQuestCost);
		writeInteger(refreshQuestCost);
		writeInteger(remainCrystalQuestNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DAILY_QUEST_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DAILY_QUEST_LIST";
	}

	public int getCurrentStore(){
		return currentStore;
	}
		
	public void setCurrentStore(int currentStore){
		this.currentStore = currentStore;
	}

	public com.hifun.soul.gameserver.human.quest.QuestInfo[] getQuestList(){
		return questList;
	}

	public void setQuestList(com.hifun.soul.gameserver.human.quest.QuestInfo[] questList){
		this.questList = questList;
	}	

	public com.hifun.soul.gameserver.human.quest.daily.DailyScoreRewardInfo[] getDailyRewardList(){
		return dailyRewardList;
	}

	public void setDailyRewardList(com.hifun.soul.gameserver.human.quest.daily.DailyScoreRewardInfo[] dailyRewardList){
		this.dailyRewardList = dailyRewardList;
	}	

	public int getRemainFreeQuestNum(){
		return remainFreeQuestNum;
	}
		
	public void setRemainFreeQuestNum(int remainFreeQuestNum){
		this.remainFreeQuestNum = remainFreeQuestNum;
	}

	public int getTotalQuestNum(){
		return totalQuestNum;
	}
		
	public void setTotalQuestNum(int totalQuestNum){
		this.totalQuestNum = totalQuestNum;
	}

	public int getReceiveQuestCost(){
		return receiveQuestCost;
	}
		
	public void setReceiveQuestCost(int receiveQuestCost){
		this.receiveQuestCost = receiveQuestCost;
	}

	public int getRefreshQuestCost(){
		return refreshQuestCost;
	}
		
	public void setRefreshQuestCost(int refreshQuestCost){
		this.refreshQuestCost = refreshQuestCost;
	}

	public int getRemainCrystalQuestNum(){
		return remainCrystalQuestNum;
	}
		
	public void setRemainCrystalQuestNum(int remainCrystalQuestNum){
		this.remainCrystalQuestNum = remainCrystalQuestNum;
	}
}