package com.hifun.soul.gameserver.building.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 建筑升级系数模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class BuildingUpgradeRatioTemplateVO extends TemplateObject {

	/**  金钱系数 */
	@ExcelCellBinding(offset = 1)
	protected float costRatio;

	/**  木材系数 */
	@ExcelCellBinding(offset = 2)
	protected float woodRatio;

	/**  矿石系数 */
	@ExcelCellBinding(offset = 3)
	protected float mineRatio;

	/**  宝石系数 */
	@ExcelCellBinding(offset = 4)
	protected float gemRatio;

	/**  cd系数 */
	@ExcelCellBinding(offset = 5)
	protected float cdRatio;


	public float getCostRatio() {
		return this.costRatio;
	}

	public void setCostRatio(float costRatio) {
		if (costRatio == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 金钱系数]costRatio不可以为0");
		}
		this.costRatio = costRatio;
	}
	
	public float getWoodRatio() {
		return this.woodRatio;
	}

	public void setWoodRatio(float woodRatio) {
		if (woodRatio == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 木材系数]woodRatio不可以为0");
		}
		this.woodRatio = woodRatio;
	}
	
	public float getMineRatio() {
		return this.mineRatio;
	}

	public void setMineRatio(float mineRatio) {
		if (mineRatio == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 矿石系数]mineRatio不可以为0");
		}
		this.mineRatio = mineRatio;
	}
	
	public float getGemRatio() {
		return this.gemRatio;
	}

	public void setGemRatio(float gemRatio) {
		if (gemRatio == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 宝石系数]gemRatio不可以为0");
		}
		this.gemRatio = gemRatio;
	}
	
	public float getCdRatio() {
		return this.cdRatio;
	}

	public void setCdRatio(float cdRatio) {
		if (cdRatio == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ cd系数]cdRatio不可以为0");
		}
		this.cdRatio = cdRatio;
	}
	

	@Override
	public String toString() {
		return "BuildingUpgradeRatioTemplateVO[costRatio=" + costRatio + ",woodRatio=" + woodRatio + ",mineRatio=" + mineRatio + ",gemRatio=" + gemRatio + ",cdRatio=" + cdRatio + ",]";

	}
}