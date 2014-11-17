package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 灵石洗炼
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class ItemForgeTemplateVO extends TemplateObject {

	/**  装备品质 */
	@ExcelCellBinding(offset = 1)
	protected int itemQuality;

	/**  锁定数量 */
	@ExcelCellBinding(offset = 2)
	protected int lockNum;

	/**  消耗魔晶 */
	@ExcelCellBinding(offset = 3)
	protected int crystal;

	/**  消耗物品id */
	@ExcelCellBinding(offset = 4)
	protected int costItemId;

	/**  消耗物品数量 */
	@ExcelCellBinding(offset = 5)
	protected int costItemNum;


	public int getItemQuality() {
		return this.itemQuality;
	}

	public void setItemQuality(int itemQuality) {
		if (itemQuality < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 装备品质]itemQuality的值不得小于0");
		}
		this.itemQuality = itemQuality;
	}
	
	public int getLockNum() {
		return this.lockNum;
	}

	public void setLockNum(int lockNum) {
		if (lockNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 锁定数量]lockNum的值不得小于0");
		}
		this.lockNum = lockNum;
	}
	
	public int getCrystal() {
		return this.crystal;
	}

	public void setCrystal(int crystal) {
		if (crystal < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 消耗魔晶]crystal的值不得小于0");
		}
		this.crystal = crystal;
	}
	
	public int getCostItemId() {
		return this.costItemId;
	}

	public void setCostItemId(int costItemId) {
		if (costItemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 消耗物品id]costItemId不可以为0");
		}
		this.costItemId = costItemId;
	}
	
	public int getCostItemNum() {
		return this.costItemNum;
	}

	public void setCostItemNum(int costItemNum) {
		if (costItemNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 消耗物品数量]costItemNum的值不得小于0");
		}
		this.costItemNum = costItemNum;
	}
	

	@Override
	public String toString() {
		return "ItemForgeTemplateVO[itemQuality=" + itemQuality + ",lockNum=" + lockNum + ",crystal=" + crystal + ",costItemId=" + costItemId + ",costItemNum=" + costItemNum + ",]";

	}
}