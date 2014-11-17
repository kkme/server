package com.hifun.soul.gameserver.recharge.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 充值活动时间模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class RechargeActivityTimeTemplateVO extends TemplateObject {

	/** 充值金额 */
	@ExcelCellBinding(offset = 1)
	protected int rechargeActivityType;

	/** 开始日期 */
	@ExcelCellBinding(offset = 2)
	protected String startDate;

	/** 结束日期 */
	@ExcelCellBinding(offset = 3)
	protected String endDate;

	/** 活动描述 */
	@ExcelCellBinding(offset = 4)
	protected String activityDesc;


	public int getRechargeActivityType() {
		return this.rechargeActivityType;
	}

	public void setRechargeActivityType(int rechargeActivityType) {
		if (rechargeActivityType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[充值金额]rechargeActivityType不可以为0");
		}
		this.rechargeActivityType = rechargeActivityType;
	}
	
	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		if (StringUtils.isEmpty(startDate)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[开始日期]startDate不可以为空");
		}
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		if (StringUtils.isEmpty(endDate)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[结束日期]endDate不可以为空");
		}
		this.endDate = endDate;
	}
	
	public String getActivityDesc() {
		return this.activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		if (StringUtils.isEmpty(activityDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[活动描述]activityDesc不可以为空");
		}
		this.activityDesc = activityDesc;
	}
	

	@Override
	public String toString() {
		return "RechargeActivityTimeTemplateVO[rechargeActivityType=" + rechargeActivityType + ",startDate=" + startDate + ",endDate=" + endDate + ",activityDesc=" + activityDesc + ",]";

	}
}