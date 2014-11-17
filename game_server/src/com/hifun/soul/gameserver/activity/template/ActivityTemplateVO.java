package com.hifun.soul.gameserver.activity.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 活动模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class ActivityTemplateVO extends TemplateObject {

	/** 活动名称 */
	@ExcelCellBinding(offset = 1)
	protected String activityName;

	/** 活动名称多语言id */
	@ExcelCellBinding(offset = 2)
	protected int activityNameLangId;

	/** 活动图标id */
	@ExcelCellBinding(offset = 3)
	protected int activityIconId;

	/** 活动简要说明 */
	@ExcelCellBinding(offset = 4)
	protected String simpleDesc;

	/** 活动简要说明多语言id */
	@ExcelCellBinding(offset = 5)
	protected int simpleDescLangId;

	/** 活动详细说明 */
	@ExcelCellBinding(offset = 6)
	protected String desc;

	/** 活动详细说明多语言id */
	@ExcelCellBinding(offset = 7)
	protected int descLangId;

	/** 活动类型 */
	@ExcelCellBinding(offset = 8)
	protected int activityShowType;

	/** 可视等级 */
	@ExcelCellBinding(offset = 9)
	protected int visibleLevel;

	/** 开启等级 */
	@ExcelCellBinding(offset = 10)
	protected int openLevel;

	/** 开始日期(yyyy-MM-dd) */
	@ExcelCellBinding(offset = 11)
	protected String startDate;

	/** 结束日期(yyyy-MM-dd) */
	@ExcelCellBinding(offset = 12)
	protected String endDate;

	/** 是否为定时开启活动，当为false时表示全天开启 */
	@ExcelCellBinding(offset = 13)
	protected boolean timing;

	/** 活动开启的时间段 */
	@ExcelCellBinding(offset = 14)
	protected String activeTimeSpan;


	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		if (StringUtils.isEmpty(activityName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[活动名称]activityName不可以为空");
		}
		this.activityName = activityName;
	}
	
	public int getActivityNameLangId() {
		return this.activityNameLangId;
	}

	public void setActivityNameLangId(int activityNameLangId) {
		if (activityNameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[活动名称多语言id]activityNameLangId不可以为0");
		}
		this.activityNameLangId = activityNameLangId;
	}
	
	public int getActivityIconId() {
		return this.activityIconId;
	}

	public void setActivityIconId(int activityIconId) {
		if (activityIconId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[活动图标id]activityIconId不可以为0");
		}
		this.activityIconId = activityIconId;
	}
	
	public String getSimpleDesc() {
		return this.simpleDesc;
	}

	public void setSimpleDesc(String simpleDesc) {
		if (StringUtils.isEmpty(simpleDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[活动简要说明]simpleDesc不可以为空");
		}
		this.simpleDesc = simpleDesc;
	}
	
	public int getSimpleDescLangId() {
		return this.simpleDescLangId;
	}

	public void setSimpleDescLangId(int simpleDescLangId) {
		if (simpleDescLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[活动简要说明多语言id]simpleDescLangId不可以为0");
		}
		this.simpleDescLangId = simpleDescLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[活动详细说明多语言id]descLangId不可以为0");
		}
		this.descLangId = descLangId;
	}
	
	public int getActivityShowType() {
		return this.activityShowType;
	}

	public void setActivityShowType(int activityShowType) {
		if (activityShowType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[活动类型]activityShowType不可以为0");
		}
		this.activityShowType = activityShowType;
	}
	
	public int getVisibleLevel() {
		return this.visibleLevel;
	}

	public void setVisibleLevel(int visibleLevel) {
		if (visibleLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[可视等级]visibleLevel不可以为0");
		}
		this.visibleLevel = visibleLevel;
	}
	
	public int getOpenLevel() {
		return this.openLevel;
	}

	public void setOpenLevel(int openLevel) {
		if (openLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[开启等级]openLevel不可以为0");
		}
		this.openLevel = openLevel;
	}
	
	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		if (StringUtils.isEmpty(startDate)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[开始日期(yyyy-MM-dd)]startDate不可以为空");
		}
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		if (StringUtils.isEmpty(endDate)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[结束日期(yyyy-MM-dd)]endDate不可以为空");
		}
		this.endDate = endDate;
	}
	
	public boolean isTiming() {
		return this.timing;
	}

	public void setTiming(boolean timing) {
		this.timing = timing;
	}
	
	public String getActiveTimeSpan() {
		return this.activeTimeSpan;
	}

	public void setActiveTimeSpan(String activeTimeSpan) {
		this.activeTimeSpan = activeTimeSpan;
	}
	

	@Override
	public String toString() {
		return "ActivityTemplateVO[activityName=" + activityName + ",activityNameLangId=" + activityNameLangId + ",activityIconId=" + activityIconId + ",simpleDesc=" + simpleDesc + ",simpleDescLangId=" + simpleDescLangId + ",desc=" + desc + ",descLangId=" + descLangId + ",activityShowType=" + activityShowType + ",visibleLevel=" + visibleLevel + ",openLevel=" + openLevel + ",startDate=" + startDate + ",endDate=" + endDate + ",timing=" + timing + ",activeTimeSpan=" + activeTimeSpan + ",]";

	}
}