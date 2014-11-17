package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 装备打造模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class EquipMakeTemplateVO extends TemplateObject {

	/** 图纸Id */
	@ExcelCellBinding(offset = 1)
	protected int paperId;

	/** 所需图纸数量 */
	@ExcelCellBinding(offset = 2)
	protected int needPaperNum;

	/** 所需材料Id(多种材料用逗号“,”分隔) */
	@ExcelCellBinding(offset = 3)
	protected String materialItemIds;

	/** 所需材料数量(与材料Id对应，多种材料用逗号“,”分隔) */
	@ExcelCellBinding(offset = 4)
	protected String materialItemNum;

	/** 所需货币类型 */
	@ExcelCellBinding(offset = 5)
	protected int currencyType;

	/** 所需货币数量 */
	@ExcelCellBinding(offset = 6)
	protected int costMomey;

	/** 成功率 */
	@ExcelCellBinding(offset = 7)
	protected float successRate;

	/** 打造成功后的装备Id */
	@ExcelCellBinding(offset = 8)
	protected int equipItemId;

	/** 所需装备id */
	@ExcelCellBinding(offset = 9)
	protected int needEquipId;


	public int getPaperId() {
		return this.paperId;
	}

	public void setPaperId(int paperId) {
		if (paperId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[图纸Id]paperId不可以为0");
		}
		this.paperId = paperId;
	}
	
	public int getNeedPaperNum() {
		return this.needPaperNum;
	}

	public void setNeedPaperNum(int needPaperNum) {
		if (needPaperNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[所需图纸数量]needPaperNum不可以为0");
		}
		this.needPaperNum = needPaperNum;
	}
	
	public String getMaterialItemIds() {
		return this.materialItemIds;
	}

	public void setMaterialItemIds(String materialItemIds) {
		this.materialItemIds = materialItemIds;
	}
	
	public String getMaterialItemNum() {
		return this.materialItemNum;
	}

	public void setMaterialItemNum(String materialItemNum) {
		this.materialItemNum = materialItemNum;
	}
	
	public int getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(int currencyType) {
		if (currencyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[所需货币类型]currencyType不可以为0");
		}
		this.currencyType = currencyType;
	}
	
	public int getCostMomey() {
		return this.costMomey;
	}

	public void setCostMomey(int costMomey) {
		if (costMomey < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[所需货币数量]costMomey的值不得小于0");
		}
		this.costMomey = costMomey;
	}
	
	public float getSuccessRate() {
		return this.successRate;
	}

	public void setSuccessRate(float successRate) {
		if (successRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[成功率]successRate不可以为0");
		}
		this.successRate = successRate;
	}
	
	public int getEquipItemId() {
		return this.equipItemId;
	}

	public void setEquipItemId(int equipItemId) {
		if (equipItemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[打造成功后的装备Id]equipItemId不可以为0");
		}
		this.equipItemId = equipItemId;
	}
	
	public int getNeedEquipId() {
		return this.needEquipId;
	}

	public void setNeedEquipId(int needEquipId) {
		if (needEquipId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[所需装备id]needEquipId不可以为0");
		}
		this.needEquipId = needEquipId;
	}
	

	@Override
	public String toString() {
		return "EquipMakeTemplateVO[paperId=" + paperId + ",needPaperNum=" + needPaperNum + ",materialItemIds=" + materialItemIds + ",materialItemNum=" + materialItemNum + ",currencyType=" + currencyType + ",costMomey=" + costMomey + ",successRate=" + successRate + ",equipItemId=" + equipItemId + ",needEquipId=" + needEquipId + ",]";

	}
}