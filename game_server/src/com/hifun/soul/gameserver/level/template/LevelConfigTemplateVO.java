package com.hifun.soul.gameserver.level.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 等级配置
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LevelConfigTemplateVO extends TemplateObject {

	/** 日常任务奖励等级配置 */
	@ExcelCellBinding(offset = 1)
	protected int dailyQuestRewardConfig;

	/** 竞技场单次战斗金币系数 */
	@ExcelCellBinding(offset = 2)
	protected int arenaPerBattleCoinRewardConfig;

	/** 竞技场排名金币系数 */
	@ExcelCellBinding(offset = 3)
	protected int arenaRankCoinRewardConfig;

	/** 竞技场单次战斗荣誉系数 */
	@ExcelCellBinding(offset = 4)
	protected int arenaPerBattleHonourRewardConfig;

	/** 竞技场排名荣誉系数 */
	@ExcelCellBinding(offset = 5)
	protected int arenaRankHonourRewardConfig;


	public int getDailyQuestRewardConfig() {
		return this.dailyQuestRewardConfig;
	}

	public void setDailyQuestRewardConfig(int dailyQuestRewardConfig) {
		if (dailyQuestRewardConfig < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[日常任务奖励等级配置]dailyQuestRewardConfig的值不得小于0");
		}
		this.dailyQuestRewardConfig = dailyQuestRewardConfig;
	}
	
	public int getArenaPerBattleCoinRewardConfig() {
		return this.arenaPerBattleCoinRewardConfig;
	}

	public void setArenaPerBattleCoinRewardConfig(int arenaPerBattleCoinRewardConfig) {
		if (arenaPerBattleCoinRewardConfig < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[竞技场单次战斗金币系数]arenaPerBattleCoinRewardConfig的值不得小于0");
		}
		this.arenaPerBattleCoinRewardConfig = arenaPerBattleCoinRewardConfig;
	}
	
	public int getArenaRankCoinRewardConfig() {
		return this.arenaRankCoinRewardConfig;
	}

	public void setArenaRankCoinRewardConfig(int arenaRankCoinRewardConfig) {
		if (arenaRankCoinRewardConfig < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[竞技场排名金币系数]arenaRankCoinRewardConfig的值不得小于0");
		}
		this.arenaRankCoinRewardConfig = arenaRankCoinRewardConfig;
	}
	
	public int getArenaPerBattleHonourRewardConfig() {
		return this.arenaPerBattleHonourRewardConfig;
	}

	public void setArenaPerBattleHonourRewardConfig(int arenaPerBattleHonourRewardConfig) {
		if (arenaPerBattleHonourRewardConfig < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[竞技场单次战斗荣誉系数]arenaPerBattleHonourRewardConfig的值不得小于0");
		}
		this.arenaPerBattleHonourRewardConfig = arenaPerBattleHonourRewardConfig;
	}
	
	public int getArenaRankHonourRewardConfig() {
		return this.arenaRankHonourRewardConfig;
	}

	public void setArenaRankHonourRewardConfig(int arenaRankHonourRewardConfig) {
		if (arenaRankHonourRewardConfig < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[竞技场排名荣誉系数]arenaRankHonourRewardConfig的值不得小于0");
		}
		this.arenaRankHonourRewardConfig = arenaRankHonourRewardConfig;
	}
	

	@Override
	public String toString() {
		return "LevelConfigTemplateVO[dailyQuestRewardConfig=" + dailyQuestRewardConfig + ",arenaPerBattleCoinRewardConfig=" + arenaPerBattleCoinRewardConfig + ",arenaRankCoinRewardConfig=" + arenaRankCoinRewardConfig + ",arenaPerBattleHonourRewardConfig=" + arenaPerBattleHonourRewardConfig + ",arenaRankHonourRewardConfig=" + arenaRankHonourRewardConfig + ",]";

	}
}