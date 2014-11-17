package com.hifun.soul.gameserver.human.quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;

/**
 * 任务信息;<BR>
 * 跟客户端通信用的bean;
 * 
 * @author crazyjohn
 * 
 */
public class QuestInfo {
	/** 任务ID */
	private int questId;
	/** 任务类型 */
	private int questType;
	/** 任务名称 */
	private String questName;
	/** npc头像 */
	private int npcIcon;
	/** 任务引导图标 */
	private int questIcon;
	/** 任务状态 */
	private int questState;
	/** 任务描述 */
	private String questDesc;
	/** 任务目标描述 */
	private String questAimDesc;
	/** 接受任务最小等级 */
	private int minLevel;
	/** 任务完成奖励经验 */
	private int rewardExp;
	/** 任务完成奖励金币 */
	private int rewardMoney;
	/** 任务完成奖励积分 */
	private int rewardStore;
	/** 任务目标 */
	private List<QuestAimInfo> questAims = new ArrayList<QuestAimInfo>();
	/** 任务引导信息 */
	private String stageGuideInfo;
	/** 任务奖励物品 */
	private List<SimpleCommonItem> questItems = new ArrayList<SimpleCommonItem>();
	/** 任务剩余时间 */
	private int remainTime;
	/** 任务品质 */
	private int quantity;
	/** 功能ID */
	private int gameFuncId;

	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public String getQuestName() {
		return questName;
	}

	public void setQuestName(String questName) {
		this.questName = questName;
	}

	public String getQuestDesc() {
		return questDesc;
	}

	public void setQuestDesc(String questDesc) {
		this.questDesc = questDesc;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	public int getRewardExp() {
		return rewardExp;
	}

	public void setRewardExp(int rewardExp) {
		this.rewardExp = rewardExp;
	}

	public int getRewardMoney() {
		return rewardMoney;
	}

	public void setRewardMoney(int rewardMoney) {
		this.rewardMoney = rewardMoney;
	}

	public int getRewardStore() {
		return rewardStore;
	}

	public void setRewardStore(int rewardStore) {
		this.rewardStore = rewardStore;
	}

	public int getQuestType() {
		return questType;
	}

	public void setQuestType(int questType) {
		this.questType = questType;
	}

	public int getQuestState() {
		return questState;
	}

	public void setQuestState(int questState) {
		this.questState = questState;
	}

	public QuestAimInfo[] getQuestAims() {
		return questAims.toArray(new QuestAimInfo[0]);
	}

	public void setQuestAims(QuestAimInfo[] questAims) {
		for (QuestAimInfo aim : questAims) {
			this.questAims.add(aim);
		}
	}

	public int getNpcIcon() {
		return npcIcon;
	}

	public void setNpcIcon(int npcIcon) {
		this.npcIcon = npcIcon;
	}

	public int getQuestIcon() {
		return questIcon;
	}

	public void setQuestIcon(int questIcon) {
		this.questIcon = questIcon;
	}

	public String getQuestAimDesc() {
		return questAimDesc;
	}

	public void setQuestAimDesc(String questAimDesc) {
		this.questAimDesc = questAimDesc;
	}

	public String getStageGuideInfo() {
		return stageGuideInfo;
	}

	public void setStageGuideInfo(String stageGuideInfo) {
		this.stageGuideInfo = stageGuideInfo;
	}

	public SimpleCommonItem[] getQuestItems() {
		return questItems.toArray(new SimpleCommonItem[0]);
	}

	public void setQuestItems(SimpleCommonItem[] questItems) {
		this.questItems = Arrays.asList(questItems);
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getGameFuncId() {
		return gameFuncId;
	}

	public void setGameFuncId(int gameFuncId) {
		this.gameFuncId = gameFuncId;
	}

}
