package com.hifun.soul.gameserver.godsoul.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 神魄灵图等级模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MagicPaperLevelTemplateVO extends TemplateObject {

	/** 灵图ID */
	@ExcelCellBinding(offset = 1)
	protected int paperId;

	/** 灵图等级 */
	@ExcelCellBinding(offset = 2)
	protected int paperLevel;

	/** 升至下级消耗道具 */
	@ExcelCellBinding(offset = 3)
	protected int costItemId;

	/** 升至下级消耗道具数量 */
	@ExcelCellBinding(offset = 4)
	protected int costItemNum;

	/** 属性ID */
	@ExcelCellBinding(offset = 5)
	protected int propertyId;

	/** 加成效果 */
	@ExcelCellBinding(offset = 6)
	protected int amendEffect;

	/** 加成方式 */
	@ExcelCellBinding(offset = 7)
	protected int amendMethod;

	/** 装备位强化等级上限 */
	@ExcelCellBinding(offset = 8)
	protected int maxEquipBitLevel;


	public int getPaperId() {
		return this.paperId;
	}

	public void setPaperId(int paperId) {
		if (paperId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[灵图ID]paperId不可以为0");
		}
		this.paperId = paperId;
	}
	
	public int getPaperLevel() {
		return this.paperLevel;
	}

	public void setPaperLevel(int paperLevel) {
		this.paperLevel = paperLevel;
	}
	
	public int getCostItemId() {
		return this.costItemId;
	}

	public void setCostItemId(int costItemId) {
		this.costItemId = costItemId;
	}
	
	public int getCostItemNum() {
		return this.costItemNum;
	}

	public void setCostItemNum(int costItemNum) {
		this.costItemNum = costItemNum;
	}
	
	public int getPropertyId() {
		return this.propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}
	
	public int getAmendEffect() {
		return this.amendEffect;
	}

	public void setAmendEffect(int amendEffect) {
		this.amendEffect = amendEffect;
	}
	
	public int getAmendMethod() {
		return this.amendMethod;
	}

	public void setAmendMethod(int amendMethod) {
		this.amendMethod = amendMethod;
	}
	
	public int getMaxEquipBitLevel() {
		return this.maxEquipBitLevel;
	}

	public void setMaxEquipBitLevel(int maxEquipBitLevel) {
		this.maxEquipBitLevel = maxEquipBitLevel;
	}
	

	@Override
	public String toString() {
		return "MagicPaperLevelTemplateVO[paperId=" + paperId + ",paperLevel=" + paperLevel + ",costItemId=" + costItemId + ",costItemNum=" + costItemNum + ",propertyId=" + propertyId + ",amendEffect=" + amendEffect + ",amendMethod=" + amendMethod + ",maxEquipBitLevel=" + maxEquipBitLevel + ",]";

	}
}