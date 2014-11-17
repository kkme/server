package com.hifun.soul.gameserver.human.quest.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import com.hifun.soul.core.util.StringUtils;
import java.util.List;

/**
 * 主线任务模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class QuestTemplateVO extends TemplateObject {

	/**  任務類型(1為日常, 2为其它) */
	@ExcelCellBinding(offset = 1)
	protected int questType;

	/**  任务获得积分 */
	@ExcelCellBinding(offset = 2)
	protected int getScore;

	/**  接受任务的最小等级 */
	@ExcelCellBinding(offset = 3)
	protected int minGetLevel;

	/**  接受任务的最大等级 */
	@ExcelCellBinding(offset = 4)
	protected int maxGetLevel;

	/**  任务图标ID */
	@ExcelCellBinding(offset = 5)
	protected int iconId;

	/**  NPC图标ID */
	@ExcelCellBinding(offset = 6)
	protected int npcIcon;

	/**  任务名称 */
	@ExcelCellBinding(offset = 7)
	protected String questName;

	/**  任务描述 */
	@ExcelCellBinding(offset = 8)
	protected String questDesc;

	/**  前置任务ID */
	@ExcelCellBinding(offset = 9)
	protected String preQuestIds;

	/**  任务完成奖励经验 */
	@ExcelCellBinding(offset = 10)
	protected long rewardExp;

	/**  任务完成奖励金币 */
	@ExcelCellBinding(offset = 11)
	protected long rewardMoney;

	/**  任务目标描述 */
	@ExcelCellBinding(offset = 12)
	protected String questAimDesc;

	/**  任务目标信息列表 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.human.quest.aim.AimInfo.class, collectionNumber = "13,14,15")
	protected List<com.hifun.soul.gameserver.human.quest.aim.AimInfo> aimInfoList;

	/**  任务关卡引导信息; */
	@ExcelCellBinding(offset = 16)
	protected String stageGuideInfo;

	/**  奖励物品; */
	@ExcelCellBinding(offset = 17)
	protected int itemId;

	/** 日常任务类型 */
	@ExcelCellBinding(offset = 18)
	protected int dailyQuestType;

	/** 任务品质 */
	@ExcelCellBinding(offset = 19)
	protected int questQuanlity;

	/** 系统刷新权重 */
	@ExcelCellBinding(offset = 20)
	protected int systemRefreshWeight;

	/** 魔晶刷新权重 */
	@ExcelCellBinding(offset = 21)
	protected int crystalRefreshWeigh;

	/** 委托时间/分钟 */
	@ExcelCellBinding(offset = 22)
	protected int autoCompleteTime;

	/** 功能ID */
	@ExcelCellBinding(offset = 23)
	protected int gameFuncId;


	public int getQuestType() {
		return this.questType;
	}

	public void setQuestType(int questType) {
		if (questType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 任務類型(1為日常, 2为其它)]questType不可以为0");
		}
		this.questType = questType;
	}
	
	public int getGetScore() {
		return this.getScore;
	}

	public void setGetScore(int getScore) {
		if (getScore < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 任务获得积分]getScore的值不得小于0");
		}
		this.getScore = getScore;
	}
	
	public int getMinGetLevel() {
		return this.minGetLevel;
	}

	public void setMinGetLevel(int minGetLevel) {
		if (minGetLevel < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 接受任务的最小等级]minGetLevel的值不得小于1");
		}
		this.minGetLevel = minGetLevel;
	}
	
	public int getMaxGetLevel() {
		return this.maxGetLevel;
	}

	public void setMaxGetLevel(int maxGetLevel) {
		if (maxGetLevel < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 接受任务的最大等级]maxGetLevel的值不得小于1");
		}
		this.maxGetLevel = maxGetLevel;
	}
	
	public int getIconId() {
		return this.iconId;
	}

	public void setIconId(int iconId) {
		if (iconId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 任务图标ID]iconId的值不得小于0");
		}
		this.iconId = iconId;
	}
	
	public int getNpcIcon() {
		return this.npcIcon;
	}

	public void setNpcIcon(int npcIcon) {
		if (npcIcon < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ NPC图标ID]npcIcon的值不得小于0");
		}
		this.npcIcon = npcIcon;
	}
	
	public String getQuestName() {
		return this.questName;
	}

	public void setQuestName(String questName) {
		if (StringUtils.isEmpty(questName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 任务名称]questName不可以为空");
		}
		this.questName = questName;
	}
	
	public String getQuestDesc() {
		return this.questDesc;
	}

	public void setQuestDesc(String questDesc) {
		if (StringUtils.isEmpty(questDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 任务描述]questDesc不可以为空");
		}
		this.questDesc = questDesc;
	}
	
	public String getPreQuestIds() {
		return this.preQuestIds;
	}

	public void setPreQuestIds(String preQuestIds) {
		this.preQuestIds = preQuestIds;
	}
	
	public long getRewardExp() {
		return this.rewardExp;
	}

	public void setRewardExp(long rewardExp) {
		if (rewardExp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 任务完成奖励经验]rewardExp的值不得小于0");
		}
		this.rewardExp = rewardExp;
	}
	
	public long getRewardMoney() {
		return this.rewardMoney;
	}

	public void setRewardMoney(long rewardMoney) {
		if (rewardMoney < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 任务完成奖励金币]rewardMoney的值不得小于0");
		}
		this.rewardMoney = rewardMoney;
	}
	
	public String getQuestAimDesc() {
		return this.questAimDesc;
	}

	public void setQuestAimDesc(String questAimDesc) {
		this.questAimDesc = questAimDesc;
	}
	
	public List<com.hifun.soul.gameserver.human.quest.aim.AimInfo> getAimInfoList() {
		return this.aimInfoList;
	}

	public void setAimInfoList(List<com.hifun.soul.gameserver.human.quest.aim.AimInfo> aimInfoList) {
		this.aimInfoList = aimInfoList;
	}
	
	public String getStageGuideInfo() {
		return this.stageGuideInfo;
	}

	public void setStageGuideInfo(String stageGuideInfo) {
		this.stageGuideInfo = stageGuideInfo;
	}
	
	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		if (itemId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[ 奖励物品;]itemId的值不得小于0");
		}
		this.itemId = itemId;
	}
	
	public int getDailyQuestType() {
		return this.dailyQuestType;
	}

	public void setDailyQuestType(int dailyQuestType) {
		if (dailyQuestType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[日常任务类型]dailyQuestType的值不得小于0");
		}
		this.dailyQuestType = dailyQuestType;
	}
	
	public int getQuestQuanlity() {
		return this.questQuanlity;
	}

	public void setQuestQuanlity(int questQuanlity) {
		if (questQuanlity < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[任务品质]questQuanlity的值不得小于0");
		}
		this.questQuanlity = questQuanlity;
	}
	
	public int getSystemRefreshWeight() {
		return this.systemRefreshWeight;
	}

	public void setSystemRefreshWeight(int systemRefreshWeight) {
		if (systemRefreshWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[系统刷新权重]systemRefreshWeight的值不得小于0");
		}
		this.systemRefreshWeight = systemRefreshWeight;
	}
	
	public int getCrystalRefreshWeigh() {
		return this.crystalRefreshWeigh;
	}

	public void setCrystalRefreshWeigh(int crystalRefreshWeigh) {
		if (crystalRefreshWeigh < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[魔晶刷新权重]crystalRefreshWeigh的值不得小于0");
		}
		this.crystalRefreshWeigh = crystalRefreshWeigh;
	}
	
	public int getAutoCompleteTime() {
		return this.autoCompleteTime;
	}

	public void setAutoCompleteTime(int autoCompleteTime) {
		if (autoCompleteTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[委托时间/分钟]autoCompleteTime的值不得小于0");
		}
		this.autoCompleteTime = autoCompleteTime;
	}
	
	public int getGameFuncId() {
		return this.gameFuncId;
	}

	public void setGameFuncId(int gameFuncId) {
		if (gameFuncId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[功能ID]gameFuncId的值不得小于0");
		}
		this.gameFuncId = gameFuncId;
	}
	

	@Override
	public String toString() {
		return "QuestTemplateVO[questType=" + questType + ",getScore=" + getScore + ",minGetLevel=" + minGetLevel + ",maxGetLevel=" + maxGetLevel + ",iconId=" + iconId + ",npcIcon=" + npcIcon + ",questName=" + questName + ",questDesc=" + questDesc + ",preQuestIds=" + preQuestIds + ",rewardExp=" + rewardExp + ",rewardMoney=" + rewardMoney + ",questAimDesc=" + questAimDesc + ",aimInfoList=" + aimInfoList + ",stageGuideInfo=" + stageGuideInfo + ",itemId=" + itemId + ",dailyQuestType=" + dailyQuestType + ",questQuanlity=" + questQuanlity + ",systemRefreshWeight=" + systemRefreshWeight + ",crystalRefreshWeigh=" + crystalRefreshWeigh + ",autoCompleteTime=" + autoCompleteTime + ",gameFuncId=" + gameFuncId + ",]";

	}
}