package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 装备强化模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EquipUpgradeTemplateVO extends TemplateObject {

	/**  装备id */
	@ExcelCellBinding(offset = 1)
	protected int equipId;

	/**  强化等级 */
	@ExcelCellBinding(offset = 2)
	protected int level;

	/**  强化材料&道具id */
	@ExcelCellBinding(offset = 3)
	protected int materialId;

	/**  强化材料&道具数量 */
	@ExcelCellBinding(offset = 4)
	protected int materialNum;

	/**  基础成功率 */
	@ExcelCellBinding(offset = 5)
	protected int upgradeRate;

	/**  成功属性加成值 */
	@ExcelCellBinding(offset = 6)
	protected String props;

	/**  失败后降级概率 */
	@ExcelCellBinding(offset = 7)
	protected int degradeRate;

	/**  失败降级后等级 */
	@ExcelCellBinding(offset = 8)
	protected int degradeLevel;

	/**  消耗货币类型 */
	@ExcelCellBinding(offset = 9)
	protected Short costCurrencyType;

	/**  消耗货币值 */
	@ExcelCellBinding(offset = 10)
	protected int costCurrencyNum;

	/**  售卖价格 */
	@ExcelCellBinding(offset = 11)
	protected int sellPrice;


	public int getEquipId() {
		return this.equipId;
	}

	public void setEquipId(int equipId) {
		if (equipId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 装备id]equipId不可以为0");
		}
		if (equipId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 装备id]equipId的值不得小于1");
		}
		this.equipId = equipId;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 强化等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public int getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(int materialId) {
		if (materialId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 强化材料&道具id]materialId不可以为0");
		}
		if (materialId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 强化材料&道具id]materialId的值不得小于1");
		}
		this.materialId = materialId;
	}
	
	public int getMaterialNum() {
		return this.materialNum;
	}

	public void setMaterialNum(int materialNum) {
		if (materialNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 强化材料&道具数量]materialNum不可以为0");
		}
		if (materialNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 强化材料&道具数量]materialNum的值不得小于1");
		}
		this.materialNum = materialNum;
	}
	
	public int getUpgradeRate() {
		return this.upgradeRate;
	}

	public void setUpgradeRate(int upgradeRate) {
		if (upgradeRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 基础成功率]upgradeRate的值不得小于0");
		}
		this.upgradeRate = upgradeRate;
	}
	
	public String getProps() {
		return this.props;
	}

	public void setProps(String props) {
		if (StringUtils.isEmpty(props)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 成功属性加成值]props不可以为空");
		}
		this.props = props;
	}
	
	public int getDegradeRate() {
		return this.degradeRate;
	}

	public void setDegradeRate(int degradeRate) {
		if (degradeRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 失败后降级概率]degradeRate的值不得小于0");
		}
		this.degradeRate = degradeRate;
	}
	
	public int getDegradeLevel() {
		return this.degradeLevel;
	}

	public void setDegradeLevel(int degradeLevel) {
		if (degradeLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 失败降级后等级]degradeLevel的值不得小于0");
		}
		this.degradeLevel = degradeLevel;
	}
	
	public Short getCostCurrencyType() {
		return this.costCurrencyType;
	}

	public void setCostCurrencyType(Short costCurrencyType) {
		if (costCurrencyType == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 消耗货币类型]costCurrencyType不可以为空");
		}	
		if (costCurrencyType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 消耗货币类型]costCurrencyType的值不得小于1");
		}
		this.costCurrencyType = costCurrencyType;
	}
	
	public int getCostCurrencyNum() {
		return this.costCurrencyNum;
	}

	public void setCostCurrencyNum(int costCurrencyNum) {
		if (costCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 消耗货币值]costCurrencyNum的值不得小于0");
		}
		this.costCurrencyNum = costCurrencyNum;
	}
	
	public int getSellPrice() {
		return this.sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		if (sellPrice < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 售卖价格]sellPrice的值不得小于0");
		}
		this.sellPrice = sellPrice;
	}
	

	@Override
	public String toString() {
		return "EquipUpgradeTemplateVO[equipId=" + equipId + ",level=" + level + ",materialId=" + materialId + ",materialNum=" + materialNum + ",upgradeRate=" + upgradeRate + ",props=" + props + ",degradeRate=" + degradeRate + ",degradeLevel=" + degradeLevel + ",costCurrencyType=" + costCurrencyType + ",costCurrencyNum=" + costCurrencyNum + ",sellPrice=" + sellPrice + ",]";

	}
}