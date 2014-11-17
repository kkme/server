package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 军团荣誉建筑模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionHonorTemplateVO extends TemplateObject {

	/** 头衔名称 */
	@ExcelCellBinding(offset = 1)
	protected String titleName;

	/** 图标ID */
	@ExcelCellBinding(offset = 2)
	protected int iconId;

	/** 品质颜色ID */
	@ExcelCellBinding(offset = 3)
	protected int colorId;

	/** 需要建筑等级 */
	@ExcelCellBinding(offset = 4)
	protected int needBuildingLevle;

	/** 可兑换最低职位 */
	@ExcelCellBinding(offset = 5)
	protected int needPosition;

	/** 可兑换最低职位名称 */
	@ExcelCellBinding(offset = 6)
	protected String needPositionName;

	/** 人数上限 */
	@ExcelCellBinding(offset = 7)
	protected int maxNum;

	/** 消耗勋章 */
	@ExcelCellBinding(offset = 8)
	protected int costMedal;

	/** 是否是全属性加成 */
	@ExcelCellBinding(offset = 9)
	protected boolean allProperty;

	/** 加成属性ID */
	@ExcelCellBinding(offset = 10)
	protected int propertyId;

	/** 加成效果 */
	@ExcelCellBinding(offset = 11)
	protected int amendEffect;

	/** 加成方式 */
	@ExcelCellBinding(offset = 12)
	protected int amendMethod;

	/** 有效天数 */
	@ExcelCellBinding(offset = 13)
	protected int validDays;


	public String getTitleName() {
		return this.titleName;
	}

	public void setTitleName(String titleName) {
		if (StringUtils.isEmpty(titleName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[头衔名称]titleName不可以为空");
		}
		this.titleName = titleName;
	}
	
	public int getIconId() {
		return this.iconId;
	}

	public void setIconId(int iconId) {
		if (iconId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[图标ID]iconId不可以为0");
		}
		this.iconId = iconId;
	}
	
	public int getColorId() {
		return this.colorId;
	}

	public void setColorId(int colorId) {
		if (colorId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[品质颜色ID]colorId不可以为0");
		}
		this.colorId = colorId;
	}
	
	public int getNeedBuildingLevle() {
		return this.needBuildingLevle;
	}

	public void setNeedBuildingLevle(int needBuildingLevle) {
		if (needBuildingLevle == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[需要建筑等级]needBuildingLevle不可以为0");
		}
		this.needBuildingLevle = needBuildingLevle;
	}
	
	public int getNeedPosition() {
		return this.needPosition;
	}

	public void setNeedPosition(int needPosition) {
		if (needPosition == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[可兑换最低职位]needPosition不可以为0");
		}
		this.needPosition = needPosition;
	}
	
	public String getNeedPositionName() {
		return this.needPositionName;
	}

	public void setNeedPositionName(String needPositionName) {
		if (StringUtils.isEmpty(needPositionName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[可兑换最低职位名称]needPositionName不可以为空");
		}
		this.needPositionName = needPositionName;
	}
	
	public int getMaxNum() {
		return this.maxNum;
	}

	public void setMaxNum(int maxNum) {
		if (maxNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[人数上限]maxNum不可以为0");
		}
		this.maxNum = maxNum;
	}
	
	public int getCostMedal() {
		return this.costMedal;
	}

	public void setCostMedal(int costMedal) {
		if (costMedal == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[消耗勋章]costMedal不可以为0");
		}
		this.costMedal = costMedal;
	}
	
	public boolean isAllProperty() {
		return this.allProperty;
	}

	public void setAllProperty(boolean allProperty) {
		this.allProperty = allProperty;
	}
	
	public int getPropertyId() {
		return this.propertyId;
	}

	public void setPropertyId(int propertyId) {
		if (propertyId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[加成属性ID]propertyId不可以为0");
		}
		this.propertyId = propertyId;
	}
	
	public int getAmendEffect() {
		return this.amendEffect;
	}

	public void setAmendEffect(int amendEffect) {
		if (amendEffect == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[加成效果]amendEffect不可以为0");
		}
		this.amendEffect = amendEffect;
	}
	
	public int getAmendMethod() {
		return this.amendMethod;
	}

	public void setAmendMethod(int amendMethod) {
		if (amendMethod == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[加成方式]amendMethod不可以为0");
		}
		this.amendMethod = amendMethod;
	}
	
	public int getValidDays() {
		return this.validDays;
	}

	public void setValidDays(int validDays) {
		if (validDays == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[有效天数]validDays不可以为0");
		}
		this.validDays = validDays;
	}
	

	@Override
	public String toString() {
		return "LegionHonorTemplateVO[titleName=" + titleName + ",iconId=" + iconId + ",colorId=" + colorId + ",needBuildingLevle=" + needBuildingLevle + ",needPosition=" + needPosition + ",needPositionName=" + needPositionName + ",maxNum=" + maxNum + ",costMedal=" + costMedal + ",allProperty=" + allProperty + ",propertyId=" + propertyId + ",amendEffect=" + amendEffect + ",amendMethod=" + amendMethod + ",validDays=" + validDays + ",]";

	}
}