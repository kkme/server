package com.hifun.soul.gameserver.elitestage.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 精英副本模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EliteStageTemplateVO extends TemplateObject {

	/** 副本类型 */
	@ExcelCellBinding(offset = 1)
	protected int type;

	/** 怪物id */
	@ExcelCellBinding(offset = 2)
	protected int monsterId;

	/** 开启等级 */
	@ExcelCellBinding(offset = 3)
	protected int openLevel;

	/** 后置副本id */
	@ExcelCellBinding(offset = 4)
	protected int nextStageId;

	/** 每日挑战次数 */
	@ExcelCellBinding(offset = 5)
	protected int dailyChallengeNum;

	/** 奖励金币数量 */
	@ExcelCellBinding(offset = 6)
	protected int coinNum;

	/** 奖励经验 */
	@ExcelCellBinding(offset = 7)
	protected int exp;

	/** 奖励科技点 */
	@ExcelCellBinding(offset = 8)
	protected int techPoint;

	/**  奖励物品概率 */
	@ExcelCellBinding(offset = 9)
	protected int rewardItemRate;

	/** 奖励物品（多个‘,’分隔，随机取一个） */
	@ExcelCellBinding(offset = 10)
	protected String itemIds;

	/** 奖励物品概率（多个‘,’分隔，随机取一个） */
	@ExcelCellBinding(offset = 11)
	protected String itemRates;

	/** 职业 */
	@ExcelCellBinding(offset = 12)
	protected int occupation;

	/** 加星条件1(剩余血量下限) */
	@ExcelCellBinding(offset = 13)
	protected int addStarOne;

	/** 加星条件2(剩余血量下限) */
	@ExcelCellBinding(offset = 14)
	protected int addStarTwo;

	/** 加星条件3(剩余血量下限) */
	@ExcelCellBinding(offset = 15)
	protected int addStarThree;

	/** 加星条件4(剩余血量下限) */
	@ExcelCellBinding(offset = 16)
	protected int addStarFour;


	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		if (type == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[副本类型]type不可以为0");
		}
		this.type = type;
	}
	
	public int getMonsterId() {
		return this.monsterId;
	}

	public void setMonsterId(int monsterId) {
		if (monsterId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[怪物id]monsterId不可以为0");
		}
		this.monsterId = monsterId;
	}
	
	public int getOpenLevel() {
		return this.openLevel;
	}

	public void setOpenLevel(int openLevel) {
		if (openLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[开启等级]openLevel的值不得小于0");
		}
		this.openLevel = openLevel;
	}
	
	public int getNextStageId() {
		return this.nextStageId;
	}

	public void setNextStageId(int nextStageId) {
		if (nextStageId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[后置副本id]nextStageId不可以为0");
		}
		this.nextStageId = nextStageId;
	}
	
	public int getDailyChallengeNum() {
		return this.dailyChallengeNum;
	}

	public void setDailyChallengeNum(int dailyChallengeNum) {
		if (dailyChallengeNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[每日挑战次数]dailyChallengeNum的值不得小于0");
		}
		this.dailyChallengeNum = dailyChallengeNum;
	}
	
	public int getCoinNum() {
		return this.coinNum;
	}

	public void setCoinNum(int coinNum) {
		if (coinNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[奖励金币数量]coinNum的值不得小于0");
		}
		this.coinNum = coinNum;
	}
	
	public int getExp() {
		return this.exp;
	}

	public void setExp(int exp) {
		if (exp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[奖励经验]exp的值不得小于0");
		}
		this.exp = exp;
	}
	
	public int getTechPoint() {
		return this.techPoint;
	}

	public void setTechPoint(int techPoint) {
		this.techPoint = techPoint;
	}
	
	public int getRewardItemRate() {
		return this.rewardItemRate;
	}

	public void setRewardItemRate(int rewardItemRate) {
		this.rewardItemRate = rewardItemRate;
	}
	
	public String getItemIds() {
		return this.itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	
	public String getItemRates() {
		return this.itemRates;
	}

	public void setItemRates(String itemRates) {
		this.itemRates = itemRates;
	}
	
	public int getOccupation() {
		return this.occupation;
	}

	public void setOccupation(int occupation) {
		if (occupation == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[职业]occupation不可以为0");
		}
		this.occupation = occupation;
	}
	
	public int getAddStarOne() {
		return this.addStarOne;
	}

	public void setAddStarOne(int addStarOne) {
		if (addStarOne == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[加星条件1(剩余血量下限)]addStarOne不可以为0");
		}
		this.addStarOne = addStarOne;
	}
	
	public int getAddStarTwo() {
		return this.addStarTwo;
	}

	public void setAddStarTwo(int addStarTwo) {
		if (addStarTwo == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[加星条件2(剩余血量下限)]addStarTwo不可以为0");
		}
		this.addStarTwo = addStarTwo;
	}
	
	public int getAddStarThree() {
		return this.addStarThree;
	}

	public void setAddStarThree(int addStarThree) {
		if (addStarThree == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[加星条件3(剩余血量下限)]addStarThree不可以为0");
		}
		this.addStarThree = addStarThree;
	}
	
	public int getAddStarFour() {
		return this.addStarFour;
	}

	public void setAddStarFour(int addStarFour) {
		if (addStarFour == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[加星条件4(剩余血量下限)]addStarFour不可以为0");
		}
		this.addStarFour = addStarFour;
	}
	

	@Override
	public String toString() {
		return "EliteStageTemplateVO[type=" + type + ",monsterId=" + monsterId + ",openLevel=" + openLevel + ",nextStageId=" + nextStageId + ",dailyChallengeNum=" + dailyChallengeNum + ",coinNum=" + coinNum + ",exp=" + exp + ",techPoint=" + techPoint + ",rewardItemRate=" + rewardItemRate + ",itemIds=" + itemIds + ",itemRates=" + itemRates + ",occupation=" + occupation + ",addStarOne=" + addStarOne + ",addStarTwo=" + addStarTwo + ",addStarThree=" + addStarThree + ",addStarFour=" + addStarFour + ",]";

	}
}