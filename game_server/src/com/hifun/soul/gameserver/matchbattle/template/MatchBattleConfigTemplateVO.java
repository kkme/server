package com.hifun.soul.gameserver.matchbattle.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 匹配战配置模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MatchBattleConfigTemplateVO extends TemplateObject {

	/** 开始准备参战时长(s) */
	@ExcelCellBinding(offset = 1)
	protected int beginWaitTime;

	/** 匹配间隔时长(s) */
	@ExcelCellBinding(offset = 2)
	protected int matchTurnTime;

	/** 匹配等待队列长度 */
	@ExcelCellBinding(offset = 3)
	protected int waitMatchQueneSize;

	/** 匹配等级段大小 */
	@ExcelCellBinding(offset = 4)
	protected int matchLevelSpan;

	/** 排行榜排行长度 */
	@ExcelCellBinding(offset = 5)
	protected int rankSize;

	/**  鼓舞加成攻击力上限 */
	@ExcelCellBinding(offset = 6)
	protected int maxEncourageDamage;

	/**  每次鼓舞加成攻击力 */
	@ExcelCellBinding(offset = 7)
	protected int encourageDamage;

	/**  金币鼓舞消耗 */
	@ExcelCellBinding(offset = 8)
	protected int encourageCoinCost;

	/**  金币鼓舞概率(基数10000) */
	@ExcelCellBinding(offset = 9)
	protected int coinEncourageRate;

	/**  魔晶鼓舞消耗 */
	@ExcelCellBinding(offset = 10)
	protected int encourageCrystalCost;

	/**  魔晶鼓舞概率(基数10000) */
	@ExcelCellBinding(offset = 11)
	protected int crystalEncourageRate;

	/**  灵石鼓舞消耗 */
	@ExcelCellBinding(offset = 12)
	protected int forgeStoneEncourageCost;

	/**  灵石鼓舞概率(基数10000) */
	@ExcelCellBinding(offset = 13)
	protected int forgeStoneEncourageRate;


	public int getBeginWaitTime() {
		return this.beginWaitTime;
	}

	public void setBeginWaitTime(int beginWaitTime) {
		if (beginWaitTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[开始准备参战时长(s)]beginWaitTime不可以为0");
		}
		this.beginWaitTime = beginWaitTime;
	}
	
	public int getMatchTurnTime() {
		return this.matchTurnTime;
	}

	public void setMatchTurnTime(int matchTurnTime) {
		if (matchTurnTime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[匹配间隔时长(s)]matchTurnTime不可以为0");
		}
		this.matchTurnTime = matchTurnTime;
	}
	
	public int getWaitMatchQueneSize() {
		return this.waitMatchQueneSize;
	}

	public void setWaitMatchQueneSize(int waitMatchQueneSize) {
		if (waitMatchQueneSize == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[匹配等待队列长度]waitMatchQueneSize不可以为0");
		}
		this.waitMatchQueneSize = waitMatchQueneSize;
	}
	
	public int getMatchLevelSpan() {
		return this.matchLevelSpan;
	}

	public void setMatchLevelSpan(int matchLevelSpan) {
		if (matchLevelSpan == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[匹配等级段大小]matchLevelSpan不可以为0");
		}
		this.matchLevelSpan = matchLevelSpan;
	}
	
	public int getRankSize() {
		return this.rankSize;
	}

	public void setRankSize(int rankSize) {
		if (rankSize == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[排行榜排行长度]rankSize不可以为0");
		}
		this.rankSize = rankSize;
	}
	
	public int getMaxEncourageDamage() {
		return this.maxEncourageDamage;
	}

	public void setMaxEncourageDamage(int maxEncourageDamage) {
		if (maxEncourageDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 鼓舞加成攻击力上限]maxEncourageDamage的值不得小于0");
		}
		this.maxEncourageDamage = maxEncourageDamage;
	}
	
	public int getEncourageDamage() {
		return this.encourageDamage;
	}

	public void setEncourageDamage(int encourageDamage) {
		if (encourageDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 每次鼓舞加成攻击力]encourageDamage的值不得小于0");
		}
		this.encourageDamage = encourageDamage;
	}
	
	public int getEncourageCoinCost() {
		return this.encourageCoinCost;
	}

	public void setEncourageCoinCost(int encourageCoinCost) {
		if (encourageCoinCost < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 金币鼓舞消耗]encourageCoinCost的值不得小于0");
		}
		this.encourageCoinCost = encourageCoinCost;
	}
	
	public int getCoinEncourageRate() {
		return this.coinEncourageRate;
	}

	public void setCoinEncourageRate(int coinEncourageRate) {
		if (coinEncourageRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 金币鼓舞概率(基数10000)]coinEncourageRate的值不得小于0");
		}
		this.coinEncourageRate = coinEncourageRate;
	}
	
	public int getEncourageCrystalCost() {
		return this.encourageCrystalCost;
	}

	public void setEncourageCrystalCost(int encourageCrystalCost) {
		if (encourageCrystalCost < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 魔晶鼓舞消耗]encourageCrystalCost的值不得小于0");
		}
		this.encourageCrystalCost = encourageCrystalCost;
	}
	
	public int getCrystalEncourageRate() {
		return this.crystalEncourageRate;
	}

	public void setCrystalEncourageRate(int crystalEncourageRate) {
		if (crystalEncourageRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 魔晶鼓舞概率(基数10000)]crystalEncourageRate的值不得小于0");
		}
		this.crystalEncourageRate = crystalEncourageRate;
	}
	
	public int getForgeStoneEncourageCost() {
		return this.forgeStoneEncourageCost;
	}

	public void setForgeStoneEncourageCost(int forgeStoneEncourageCost) {
		if (forgeStoneEncourageCost < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 灵石鼓舞消耗]forgeStoneEncourageCost的值不得小于0");
		}
		this.forgeStoneEncourageCost = forgeStoneEncourageCost;
	}
	
	public int getForgeStoneEncourageRate() {
		return this.forgeStoneEncourageRate;
	}

	public void setForgeStoneEncourageRate(int forgeStoneEncourageRate) {
		if (forgeStoneEncourageRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 灵石鼓舞概率(基数10000)]forgeStoneEncourageRate的值不得小于0");
		}
		this.forgeStoneEncourageRate = forgeStoneEncourageRate;
	}
	

	@Override
	public String toString() {
		return "MatchBattleConfigTemplateVO[beginWaitTime=" + beginWaitTime + ",matchTurnTime=" + matchTurnTime + ",waitMatchQueneSize=" + waitMatchQueneSize + ",matchLevelSpan=" + matchLevelSpan + ",rankSize=" + rankSize + ",maxEncourageDamage=" + maxEncourageDamage + ",encourageDamage=" + encourageDamage + ",encourageCoinCost=" + encourageCoinCost + ",coinEncourageRate=" + coinEncourageRate + ",encourageCrystalCost=" + encourageCrystalCost + ",crystalEncourageRate=" + crystalEncourageRate + ",forgeStoneEncourageCost=" + forgeStoneEncourageCost + ",forgeStoneEncourageRate=" + forgeStoneEncourageRate + ",]";

	}
}