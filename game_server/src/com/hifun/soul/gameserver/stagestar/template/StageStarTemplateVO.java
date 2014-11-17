package com.hifun.soul.gameserver.stagestar.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 关卡评星模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class StageStarTemplateVO extends TemplateObject {

	/**  扫荡评级条件 */
	@ExcelCellBinding(offset = 1)
	protected int autoBattleMinStageStar;

	/**  五星经验金币百分比 */
	@ExcelCellBinding(offset = 2)
	protected int fiveStarRate;

	/**  四星经验金币百分比 */
	@ExcelCellBinding(offset = 3)
	protected int fourStarRate;

	/**  三星经验金币百分比 */
	@ExcelCellBinding(offset = 4)
	protected int threeStarRate;

	/**  二星经验金币百分比 */
	@ExcelCellBinding(offset = 5)
	protected int twoStarRate;

	/**  一星经验金币百分比 */
	@ExcelCellBinding(offset = 6)
	protected int oneStarRate;

	/**  失败经验金币百分比 */
	@ExcelCellBinding(offset = 7)
	protected int failRate;


	public int getAutoBattleMinStageStar() {
		return this.autoBattleMinStageStar;
	}

	public void setAutoBattleMinStageStar(int autoBattleMinStageStar) {
		if (autoBattleMinStageStar == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 扫荡评级条件]autoBattleMinStageStar不可以为0");
		}
		this.autoBattleMinStageStar = autoBattleMinStageStar;
	}
	
	public int getFiveStarRate() {
		return this.fiveStarRate;
	}

	public void setFiveStarRate(int fiveStarRate) {
		if (fiveStarRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 五星经验金币百分比]fiveStarRate不可以为0");
		}
		this.fiveStarRate = fiveStarRate;
	}
	
	public int getFourStarRate() {
		return this.fourStarRate;
	}

	public void setFourStarRate(int fourStarRate) {
		if (fourStarRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 四星经验金币百分比]fourStarRate不可以为0");
		}
		this.fourStarRate = fourStarRate;
	}
	
	public int getThreeStarRate() {
		return this.threeStarRate;
	}

	public void setThreeStarRate(int threeStarRate) {
		if (threeStarRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 三星经验金币百分比]threeStarRate不可以为0");
		}
		this.threeStarRate = threeStarRate;
	}
	
	public int getTwoStarRate() {
		return this.twoStarRate;
	}

	public void setTwoStarRate(int twoStarRate) {
		if (twoStarRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 二星经验金币百分比]twoStarRate不可以为0");
		}
		this.twoStarRate = twoStarRate;
	}
	
	public int getOneStarRate() {
		return this.oneStarRate;
	}

	public void setOneStarRate(int oneStarRate) {
		if (oneStarRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 一星经验金币百分比]oneStarRate不可以为0");
		}
		this.oneStarRate = oneStarRate;
	}
	
	public int getFailRate() {
		return this.failRate;
	}

	public void setFailRate(int failRate) {
		if (failRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 失败经验金币百分比]failRate不可以为0");
		}
		this.failRate = failRate;
	}
	

	@Override
	public String toString() {
		return "StageStarTemplateVO[autoBattleMinStageStar=" + autoBattleMinStageStar + ",fiveStarRate=" + fiveStarRate + ",fourStarRate=" + fourStarRate + ",threeStarRate=" + threeStarRate + ",twoStarRate=" + twoStarRate + ",oneStarRate=" + oneStarRate + ",failRate=" + failRate + ",]";

	}
}