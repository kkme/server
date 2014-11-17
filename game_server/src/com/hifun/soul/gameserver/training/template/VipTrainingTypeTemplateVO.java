package com.hifun.soul.gameserver.training.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * Vip训练模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class VipTrainingTypeTemplateVO extends TemplateObject {

	/** 训练模式名称 */
	@ExcelCellBinding(offset = 1)
	protected String VipTrainingTypeName;

	/** 多语言名称 */
	@ExcelCellBinding(offset = 2)
	protected int nameLangId;

	/** 消耗货币类型 */
	@ExcelCellBinding(offset = 3)
	protected int costCurrencyType;

	/** 消耗货币数量 */
	@ExcelCellBinding(offset = 4)
	protected int costCurrencyNum;

	/** 开启训练模式所需的VIP等级 */
	@ExcelCellBinding(offset = 5)
	protected int vipLevel;


	public String getVipTrainingTypeName() {
		return this.VipTrainingTypeName;
	}

	public void setVipTrainingTypeName(String VipTrainingTypeName) {
		if (StringUtils.isEmpty(VipTrainingTypeName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[训练模式名称]VipTrainingTypeName不可以为空");
		}
		this.VipTrainingTypeName = VipTrainingTypeName;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[多语言名称]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public int getCostCurrencyType() {
		return this.costCurrencyType;
	}

	public void setCostCurrencyType(int costCurrencyType) {
		if (costCurrencyType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[消耗货币类型]costCurrencyType不可以为0");
		}
		this.costCurrencyType = costCurrencyType;
	}
	
	public int getCostCurrencyNum() {
		return this.costCurrencyNum;
	}

	public void setCostCurrencyNum(int costCurrencyNum) {
		if (costCurrencyNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[消耗货币数量]costCurrencyNum不可以为0");
		}
		this.costCurrencyNum = costCurrencyNum;
	}
	
	public int getVipLevel() {
		return this.vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		if (vipLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[开启训练模式所需的VIP等级]vipLevel的值不得小于0");
		}
		this.vipLevel = vipLevel;
	}
	

	@Override
	public String toString() {
		return "VipTrainingTypeTemplateVO[VipTrainingTypeName=" + VipTrainingTypeName + ",nameLangId=" + nameLangId + ",costCurrencyType=" + costCurrencyType + ",costCurrencyNum=" + costCurrencyNum + ",vipLevel=" + vipLevel + ",]";

	}
}