package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 军团职位模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionPositionTemplateVO extends TemplateObject {

	/** 职位名称多语言ID */
	@ExcelCellBinding(offset = 1)
	protected int nameLangId;

	/** 职位名称 */
	@ExcelCellBinding(offset = 2)
	protected String positionName;

	/** 品质ID */
	@ExcelCellBinding(offset = 3)
	protected int qualityId;

	/** 人数限制 */
	@ExcelCellBinding(offset = 4)
	protected int numLimit;

	/** 升职需要贡献值 */
	@ExcelCellBinding(offset = 5)
	protected int needContribution;

	/** 权限ID */
	@ExcelCellBinding(offset = 6)
	protected int rightId;


	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[职位名称多语言ID]nameLangId不可以为0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		if (StringUtils.isEmpty(positionName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[职位名称]positionName不可以为空");
		}
		this.positionName = positionName;
	}
	
	public int getQualityId() {
		return this.qualityId;
	}

	public void setQualityId(int qualityId) {
		if (qualityId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[品质ID]qualityId不可以为0");
		}
		this.qualityId = qualityId;
	}
	
	public int getNumLimit() {
		return this.numLimit;
	}

	public void setNumLimit(int numLimit) {
		if (numLimit == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[人数限制]numLimit不可以为0");
		}
		this.numLimit = numLimit;
	}
	
	public int getNeedContribution() {
		return this.needContribution;
	}

	public void setNeedContribution(int needContribution) {
		this.needContribution = needContribution;
	}
	
	public int getRightId() {
		return this.rightId;
	}

	public void setRightId(int rightId) {
		if (rightId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[权限ID]rightId不可以为0");
		}
		this.rightId = rightId;
	}
	

	@Override
	public String toString() {
		return "LegionPositionTemplateVO[nameLangId=" + nameLangId + ",positionName=" + positionName + ",qualityId=" + qualityId + ",numLimit=" + numLimit + ",needContribution=" + needContribution + ",rightId=" + rightId + ",]";

	}
}