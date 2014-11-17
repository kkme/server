package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 军团赏金任务模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionTaskTemplateVO extends TemplateObject {

	/** 主题类型 */
	@ExcelCellBinding(offset = 1)
	protected int shemeType;

	/** 主题名称 */
	@ExcelCellBinding(offset = 2)
	protected String shemeName;

	/** 图标ID */
	@ExcelCellBinding(offset = 3)
	protected int iconId;

	/** 品质ID */
	@ExcelCellBinding(offset = 4)
	protected int quantityId;

	/** 奖励贡献 */
	@ExcelCellBinding(offset = 5)
	protected int rewardContribution;

	/** 奖励勋章 */
	@ExcelCellBinding(offset = 6)
	protected int rewardMedal;

	/** 奖励积分 */
	@ExcelCellBinding(offset = 7)
	protected int rewardTaskScore;

	/** 奖励军团经验 */
	@ExcelCellBinding(offset = 8)
	protected int rewardLegionExperience;

	/** 奖励军团资金 */
	@ExcelCellBinding(offset = 9)
	protected int rewardLegionCoin;

	/** 任务时间 */
	@ExcelCellBinding(offset = 10)
	protected int taskTime;

	/** 系统刷新权重 */
	@ExcelCellBinding(offset = 11)
	protected int systemRefreshWeight;

	/** 魔晶刷新权重 */
	@ExcelCellBinding(offset = 12)
	protected int crystalRefreshWeight;


	public int getShemeType() {
		return this.shemeType;
	}

	public void setShemeType(int shemeType) {
		if (shemeType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[主题类型]shemeType不可以为0");
		}
		this.shemeType = shemeType;
	}
	
	public String getShemeName() {
		return this.shemeName;
	}

	public void setShemeName(String shemeName) {
		if (StringUtils.isEmpty(shemeName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[主题名称]shemeName不可以为空");
		}
		this.shemeName = shemeName;
	}
	
	public int getIconId() {
		return this.iconId;
	}

	public void setIconId(int iconId) {
		if (iconId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[图标ID]iconId不可以为0");
		}
		this.iconId = iconId;
	}
	
	public int getQuantityId() {
		return this.quantityId;
	}

	public void setQuantityId(int quantityId) {
		if (quantityId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[品质ID]quantityId不可以为0");
		}
		this.quantityId = quantityId;
	}
	
	public int getRewardContribution() {
		return this.rewardContribution;
	}

	public void setRewardContribution(int rewardContribution) {
		if (rewardContribution == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[奖励贡献]rewardContribution不可以为0");
		}
		this.rewardContribution = rewardContribution;
	}
	
	public int getRewardMedal() {
		return this.rewardMedal;
	}

	public void setRewardMedal(int rewardMedal) {
		if (rewardMedal == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[奖励勋章]rewardMedal不可以为0");
		}
		this.rewardMedal = rewardMedal;
	}
	
	public int getRewardTaskScore() {
		return this.rewardTaskScore;
	}

	public void setRewardTaskScore(int rewardTaskScore) {
		if (rewardTaskScore == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[奖励积分]rewardTaskScore不可以为0");
		}
		this.rewardTaskScore = rewardTaskScore;
	}
	
	public int getRewardLegionExperience() {
		return this.rewardLegionExperience;
	}

	public void setRewardLegionExperience(int rewardLegionExperience) {
		if (rewardLegionExperience == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[奖励军团经验]rewardLegionExperience不可以为0");
		}
		this.rewardLegionExperience = rewardLegionExperience;
	}
	
	public int getRewardLegionCoin() {
		return this.rewardLegionCoin;
	}

	public void setRewardLegionCoin(int rewardLegionCoin) {
		if (rewardLegionCoin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[奖励军团资金]rewardLegionCoin不可以为0");
		}
		this.rewardLegionCoin = rewardLegionCoin;
	}
	
	public int getTaskTime() {
		return this.taskTime;
	}

	public void setTaskTime(int taskTime) {
		if (taskTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[任务时间]taskTime不可以为0");
		}
		this.taskTime = taskTime;
	}
	
	public int getSystemRefreshWeight() {
		return this.systemRefreshWeight;
	}

	public void setSystemRefreshWeight(int systemRefreshWeight) {
		if (systemRefreshWeight == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[系统刷新权重]systemRefreshWeight不可以为0");
		}
		this.systemRefreshWeight = systemRefreshWeight;
	}
	
	public int getCrystalRefreshWeight() {
		return this.crystalRefreshWeight;
	}

	public void setCrystalRefreshWeight(int crystalRefreshWeight) {
		if (crystalRefreshWeight == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[魔晶刷新权重]crystalRefreshWeight不可以为0");
		}
		this.crystalRefreshWeight = crystalRefreshWeight;
	}
	

	@Override
	public String toString() {
		return "LegionTaskTemplateVO[shemeType=" + shemeType + ",shemeName=" + shemeName + ",iconId=" + iconId + ",quantityId=" + quantityId + ",rewardContribution=" + rewardContribution + ",rewardMedal=" + rewardMedal + ",rewardTaskScore=" + rewardTaskScore + ",rewardLegionExperience=" + rewardLegionExperience + ",rewardLegionCoin=" + rewardLegionCoin + ",taskTime=" + taskTime + ",systemRefreshWeight=" + systemRefreshWeight + ",crystalRefreshWeight=" + crystalRefreshWeight + ",]";

	}
}