package com.hifun.soul.gameserver.building.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 建筑升级模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class BuildingUpgradeTemplateVO extends TemplateObject {

	/**  货币类型 */
	@ExcelCellBinding(offset = 1)
	protected short currencyType;

	/**  货币数量 */
	@ExcelCellBinding(offset = 2)
	protected int num;

	/**  木材数量 */
	@ExcelCellBinding(offset = 3)
	protected int wood;

	/**  矿石数量 */
	@ExcelCellBinding(offset = 4)
	protected int mine;

	/**  宝石数量 */
	@ExcelCellBinding(offset = 5)
	protected int gem;

	/**  cd时间 */
	@ExcelCellBinding(offset = 6)
	protected int cd;


	public short getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(short currencyType) {
		if (currencyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 货币类型]currencyType不可以为0");
		}
		this.currencyType = currencyType;
	}
	
	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
		if (num == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 货币数量]num不可以为0");
		}
		if (num < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 货币数量]num的值不得小于0");
		}
		this.num = num;
	}
	
	public int getWood() {
		return this.wood;
	}

	public void setWood(int wood) {
		if (wood == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 木材数量]wood不可以为0");
		}
		if (wood < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 木材数量]wood的值不得小于0");
		}
		this.wood = wood;
	}
	
	public int getMine() {
		return this.mine;
	}

	public void setMine(int mine) {
		if (mine == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 矿石数量]mine不可以为0");
		}
		if (mine < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 矿石数量]mine的值不得小于0");
		}
		this.mine = mine;
	}
	
	public int getGem() {
		return this.gem;
	}

	public void setGem(int gem) {
		if (gem == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 宝石数量]gem不可以为0");
		}
		if (gem < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 宝石数量]gem的值不得小于0");
		}
		this.gem = gem;
	}
	
	public int getCd() {
		return this.cd;
	}

	public void setCd(int cd) {
		if (cd == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ cd时间]cd不可以为0");
		}
		if (cd < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ cd时间]cd的值不得小于0");
		}
		this.cd = cd;
	}
	

	@Override
	public String toString() {
		return "BuildingUpgradeTemplateVO[currencyType=" + currencyType + ",num=" + num + ",wood=" + wood + ",mine=" + mine + ",gem=" + gem + ",cd=" + cd + ",]";

	}
}