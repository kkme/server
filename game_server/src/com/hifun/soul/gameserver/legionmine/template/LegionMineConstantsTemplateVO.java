package com.hifun.soul.gameserver.legionmine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团矿战常量模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionMineConstantsTemplateVO extends TemplateObject {

	/** 鼓舞上限 */
	@ExcelCellBinding(offset = 1)
	protected int maxEncourageRate;

	/** 鼓舞加成 */
	@ExcelCellBinding(offset = 2)
	protected int encourageRate;

	/** 冥想力鼓舞消耗 */
	@ExcelCellBinding(offset = 3)
	protected int meditationEncourageCost;

	/** 冥想力鼓舞概率 */
	@ExcelCellBinding(offset = 4)
	protected int meditationEncourageRate;

	/** 魔晶鼓舞消耗 */
	@ExcelCellBinding(offset = 5)
	protected int crystalEncourageCost;

	/** 魔晶鼓舞概率 */
	@ExcelCellBinding(offset = 6)
	protected int crystalEncourageRate;

	/** 占领红矿收益增加 */
	@ExcelCellBinding(offset = 7)
	protected int redMineRevenue;

	/** 占领红矿buf图标 */
	@ExcelCellBinding(offset = 8)
	protected int redMineBufIcon;

	/** 矿位人数上限系数 */
	@ExcelCellBinding(offset = 9)
	protected int mineMaxNumRate;

	/** 侦查消耗魔晶 */
	@ExcelCellBinding(offset = 10)
	protected int watchCostCraystal;

	/** 侦查持续时间(s) */
	@ExcelCellBinding(offset = 11)
	protected int watchHoldTime;

	/** 获得指令概率 */
	@ExcelCellBinding(offset = 12)
	protected int getSelfBufRate;

	/** 指令扰增加CD时间 */
	@ExcelCellBinding(offset = 13)
	protected int disturbCdTime;

	/** 军团战败奖励系数 */
	@ExcelCellBinding(offset = 14)
	protected float legionFailRewardRate;

	/** 占领值转化军团个人贡献系数 */
	@ExcelCellBinding(offset = 15)
	protected int occupyValueToContributionRate;

	/** 胜方军团加贡献值 */
	@ExcelCellBinding(offset = 16)
	protected int legionWinRewardExperience;

	/** 失败方军团加贡献值 */
	@ExcelCellBinding(offset = 17)
	protected int legionFailRewardExperience;

	/** 收获基础CD时间(min) */
	@ExcelCellBinding(offset = 18)
	protected int harvestBaseCdTime;

	/** 胜利减少收获CD时间(min) */
	@ExcelCellBinding(offset = 19)
	protected int winReduceHarvestCdTime;

	/** 失败减少CD时间(min) */
	@ExcelCellBinding(offset = 20)
	protected float failReduceHarvestCdTime;

	/** 退出再进入CD时间(s) */
	@ExcelCellBinding(offset = 21)
	protected int quitEnterCdTime;

	/** 活动开始后多少分钟刷加倍矿 */
	@ExcelCellBinding(offset = 22)
	protected int doubleDelyTime;

	/** 刷加倍矿间隔时间(分钟) */
	@ExcelCellBinding(offset = 23)
	protected int doubleInternalTime;

	/** 战斗基础CD时间(s) */
	@ExcelCellBinding(offset = 24)
	protected int battleBaseCdTime;

	/** 移动基础CD时间(s) */
	@ExcelCellBinding(offset = 25)
	protected int moveBaseCdTime;

	/** 军团获胜需要占领值 */
	@ExcelCellBinding(offset = 26)
	protected int legionWinNeedOccupyValue;

	/** 排名需要最小占领值 */
	@ExcelCellBinding(offset = 27)
	protected int rankMinOccupyValue;


	public int getMaxEncourageRate() {
		return this.maxEncourageRate;
	}

	public void setMaxEncourageRate(int maxEncourageRate) {
		if (maxEncourageRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[鼓舞上限]maxEncourageRate不可以为0");
		}
		this.maxEncourageRate = maxEncourageRate;
	}
	
	public int getEncourageRate() {
		return this.encourageRate;
	}

	public void setEncourageRate(int encourageRate) {
		if (encourageRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[鼓舞加成]encourageRate不可以为0");
		}
		this.encourageRate = encourageRate;
	}
	
	public int getMeditationEncourageCost() {
		return this.meditationEncourageCost;
	}

	public void setMeditationEncourageCost(int meditationEncourageCost) {
		if (meditationEncourageCost == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[冥想力鼓舞消耗]meditationEncourageCost不可以为0");
		}
		this.meditationEncourageCost = meditationEncourageCost;
	}
	
	public int getMeditationEncourageRate() {
		return this.meditationEncourageRate;
	}

	public void setMeditationEncourageRate(int meditationEncourageRate) {
		if (meditationEncourageRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[冥想力鼓舞概率]meditationEncourageRate不可以为0");
		}
		this.meditationEncourageRate = meditationEncourageRate;
	}
	
	public int getCrystalEncourageCost() {
		return this.crystalEncourageCost;
	}

	public void setCrystalEncourageCost(int crystalEncourageCost) {
		if (crystalEncourageCost == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[魔晶鼓舞消耗]crystalEncourageCost不可以为0");
		}
		this.crystalEncourageCost = crystalEncourageCost;
	}
	
	public int getCrystalEncourageRate() {
		return this.crystalEncourageRate;
	}

	public void setCrystalEncourageRate(int crystalEncourageRate) {
		if (crystalEncourageRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[魔晶鼓舞概率]crystalEncourageRate不可以为0");
		}
		this.crystalEncourageRate = crystalEncourageRate;
	}
	
	public int getRedMineRevenue() {
		return this.redMineRevenue;
	}

	public void setRedMineRevenue(int redMineRevenue) {
		if (redMineRevenue == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[占领红矿收益增加]redMineRevenue不可以为0");
		}
		this.redMineRevenue = redMineRevenue;
	}
	
	public int getRedMineBufIcon() {
		return this.redMineBufIcon;
	}

	public void setRedMineBufIcon(int redMineBufIcon) {
		if (redMineBufIcon == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[占领红矿buf图标]redMineBufIcon不可以为0");
		}
		this.redMineBufIcon = redMineBufIcon;
	}
	
	public int getMineMaxNumRate() {
		return this.mineMaxNumRate;
	}

	public void setMineMaxNumRate(int mineMaxNumRate) {
		if (mineMaxNumRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[矿位人数上限系数]mineMaxNumRate不可以为0");
		}
		this.mineMaxNumRate = mineMaxNumRate;
	}
	
	public int getWatchCostCraystal() {
		return this.watchCostCraystal;
	}

	public void setWatchCostCraystal(int watchCostCraystal) {
		if (watchCostCraystal == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[侦查消耗魔晶]watchCostCraystal不可以为0");
		}
		this.watchCostCraystal = watchCostCraystal;
	}
	
	public int getWatchHoldTime() {
		return this.watchHoldTime;
	}

	public void setWatchHoldTime(int watchHoldTime) {
		if (watchHoldTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[侦查持续时间(s)]watchHoldTime不可以为0");
		}
		this.watchHoldTime = watchHoldTime;
	}
	
	public int getGetSelfBufRate() {
		return this.getSelfBufRate;
	}

	public void setGetSelfBufRate(int getSelfBufRate) {
		if (getSelfBufRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[获得指令概率]getSelfBufRate不可以为0");
		}
		this.getSelfBufRate = getSelfBufRate;
	}
	
	public int getDisturbCdTime() {
		return this.disturbCdTime;
	}

	public void setDisturbCdTime(int disturbCdTime) {
		if (disturbCdTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[指令扰增加CD时间]disturbCdTime不可以为0");
		}
		this.disturbCdTime = disturbCdTime;
	}
	
	public float getLegionFailRewardRate() {
		return this.legionFailRewardRate;
	}

	public void setLegionFailRewardRate(float legionFailRewardRate) {
		if (legionFailRewardRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[军团战败奖励系数]legionFailRewardRate不可以为0");
		}
		this.legionFailRewardRate = legionFailRewardRate;
	}
	
	public int getOccupyValueToContributionRate() {
		return this.occupyValueToContributionRate;
	}

	public void setOccupyValueToContributionRate(int occupyValueToContributionRate) {
		if (occupyValueToContributionRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[占领值转化军团个人贡献系数]occupyValueToContributionRate不可以为0");
		}
		this.occupyValueToContributionRate = occupyValueToContributionRate;
	}
	
	public int getLegionWinRewardExperience() {
		return this.legionWinRewardExperience;
	}

	public void setLegionWinRewardExperience(int legionWinRewardExperience) {
		if (legionWinRewardExperience == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[胜方军团加贡献值]legionWinRewardExperience不可以为0");
		}
		this.legionWinRewardExperience = legionWinRewardExperience;
	}
	
	public int getLegionFailRewardExperience() {
		return this.legionFailRewardExperience;
	}

	public void setLegionFailRewardExperience(int legionFailRewardExperience) {
		if (legionFailRewardExperience == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[失败方军团加贡献值]legionFailRewardExperience不可以为0");
		}
		this.legionFailRewardExperience = legionFailRewardExperience;
	}
	
	public int getHarvestBaseCdTime() {
		return this.harvestBaseCdTime;
	}

	public void setHarvestBaseCdTime(int harvestBaseCdTime) {
		if (harvestBaseCdTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[收获基础CD时间(min)]harvestBaseCdTime不可以为0");
		}
		this.harvestBaseCdTime = harvestBaseCdTime;
	}
	
	public int getWinReduceHarvestCdTime() {
		return this.winReduceHarvestCdTime;
	}

	public void setWinReduceHarvestCdTime(int winReduceHarvestCdTime) {
		if (winReduceHarvestCdTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[胜利减少收获CD时间(min)]winReduceHarvestCdTime不可以为0");
		}
		this.winReduceHarvestCdTime = winReduceHarvestCdTime;
	}
	
	public float getFailReduceHarvestCdTime() {
		return this.failReduceHarvestCdTime;
	}

	public void setFailReduceHarvestCdTime(float failReduceHarvestCdTime) {
		if (failReduceHarvestCdTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[失败减少CD时间(min)]failReduceHarvestCdTime不可以为0");
		}
		this.failReduceHarvestCdTime = failReduceHarvestCdTime;
	}
	
	public int getQuitEnterCdTime() {
		return this.quitEnterCdTime;
	}

	public void setQuitEnterCdTime(int quitEnterCdTime) {
		if (quitEnterCdTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[退出再进入CD时间(s)]quitEnterCdTime不可以为0");
		}
		this.quitEnterCdTime = quitEnterCdTime;
	}
	
	public int getDoubleDelyTime() {
		return this.doubleDelyTime;
	}

	public void setDoubleDelyTime(int doubleDelyTime) {
		if (doubleDelyTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[活动开始后多少分钟刷加倍矿]doubleDelyTime不可以为0");
		}
		this.doubleDelyTime = doubleDelyTime;
	}
	
	public int getDoubleInternalTime() {
		return this.doubleInternalTime;
	}

	public void setDoubleInternalTime(int doubleInternalTime) {
		if (doubleInternalTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[刷加倍矿间隔时间(分钟)]doubleInternalTime不可以为0");
		}
		this.doubleInternalTime = doubleInternalTime;
	}
	
	public int getBattleBaseCdTime() {
		return this.battleBaseCdTime;
	}

	public void setBattleBaseCdTime(int battleBaseCdTime) {
		if (battleBaseCdTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					25, "[战斗基础CD时间(s)]battleBaseCdTime不可以为0");
		}
		this.battleBaseCdTime = battleBaseCdTime;
	}
	
	public int getMoveBaseCdTime() {
		return this.moveBaseCdTime;
	}

	public void setMoveBaseCdTime(int moveBaseCdTime) {
		if (moveBaseCdTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					26, "[移动基础CD时间(s)]moveBaseCdTime不可以为0");
		}
		this.moveBaseCdTime = moveBaseCdTime;
	}
	
	public int getLegionWinNeedOccupyValue() {
		return this.legionWinNeedOccupyValue;
	}

	public void setLegionWinNeedOccupyValue(int legionWinNeedOccupyValue) {
		if (legionWinNeedOccupyValue == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					27, "[军团获胜需要占领值]legionWinNeedOccupyValue不可以为0");
		}
		this.legionWinNeedOccupyValue = legionWinNeedOccupyValue;
	}
	
	public int getRankMinOccupyValue() {
		return this.rankMinOccupyValue;
	}

	public void setRankMinOccupyValue(int rankMinOccupyValue) {
		if (rankMinOccupyValue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					28, "[排名需要最小占领值]rankMinOccupyValue的值不得小于0");
		}
		this.rankMinOccupyValue = rankMinOccupyValue;
	}
	

	@Override
	public String toString() {
		return "LegionMineConstantsTemplateVO[maxEncourageRate=" + maxEncourageRate + ",encourageRate=" + encourageRate + ",meditationEncourageCost=" + meditationEncourageCost + ",meditationEncourageRate=" + meditationEncourageRate + ",crystalEncourageCost=" + crystalEncourageCost + ",crystalEncourageRate=" + crystalEncourageRate + ",redMineRevenue=" + redMineRevenue + ",redMineBufIcon=" + redMineBufIcon + ",mineMaxNumRate=" + mineMaxNumRate + ",watchCostCraystal=" + watchCostCraystal + ",watchHoldTime=" + watchHoldTime + ",getSelfBufRate=" + getSelfBufRate + ",disturbCdTime=" + disturbCdTime + ",legionFailRewardRate=" + legionFailRewardRate + ",occupyValueToContributionRate=" + occupyValueToContributionRate + ",legionWinRewardExperience=" + legionWinRewardExperience + ",legionFailRewardExperience=" + legionFailRewardExperience + ",harvestBaseCdTime=" + harvestBaseCdTime + ",winReduceHarvestCdTime=" + winReduceHarvestCdTime + ",failReduceHarvestCdTime=" + failReduceHarvestCdTime + ",quitEnterCdTime=" + quitEnterCdTime + ",doubleDelyTime=" + doubleDelyTime + ",doubleInternalTime=" + doubleInternalTime + ",battleBaseCdTime=" + battleBaseCdTime + ",moveBaseCdTime=" + moveBaseCdTime + ",legionWinNeedOccupyValue=" + legionWinNeedOccupyValue + ",rankMinOccupyValue=" + rankMinOccupyValue + ",]";

	}
}