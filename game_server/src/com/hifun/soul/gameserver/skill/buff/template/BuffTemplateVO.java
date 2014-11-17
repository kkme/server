package com.hifun.soul.gameserver.skill.buff.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * buff模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class BuffTemplateVO extends TemplateObject {

	/**  buff类型 */
	@ExcelCellBinding(offset = 1)
	protected int buffType;

	/**  buff资源ID; */
	@ExcelCellBinding(offset = 2)
	protected int buffResourceId;

	/**  名称多语言id */
	@ExcelCellBinding(offset = 3)
	protected int nameLangId;

	/**  buff名称 */
	@ExcelCellBinding(offset = 4)
	protected String buffName;

	/**  描述多语言id */
	@ExcelCellBinding(offset = 5)
	protected int descLangId;

	/**  buff描述 */
	@ExcelCellBinding(offset = 6)
	protected String buffDesc;

	/**  buff自身类型(buff or debuff) */
	@ExcelCellBinding(offset = 7)
	protected int buffSelfType;

	/**  buff最大的叠加次数 */
	@ExcelCellBinding(offset = 8)
	protected int overlyingCount;


	public int getBuffType() {
		return this.buffType;
	}

	public void setBuffType(int buffType) {
		if (buffType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ buff类型]buffType不可以为0");
		}
		if (buffType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ buff类型]buffType的值不得小于0");
		}
		this.buffType = buffType;
	}
	
	public int getBuffResourceId() {
		return this.buffResourceId;
	}

	public void setBuffResourceId(int buffResourceId) {
		if (buffResourceId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ buff资源ID;]buffResourceId的值不得小于0");
		}
		this.buffResourceId = buffResourceId;
	}
	
	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 名称多语言id]nameLangId不可以为0");
		}
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 名称多语言id]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getBuffName() {
		return this.buffName;
	}

	public void setBuffName(String buffName) {
		if (StringUtils.isEmpty(buffName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ buff名称]buffName不可以为空");
		}
		this.buffName = buffName;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 描述多语言id]descLangId不可以为0");
		}
		if (descLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 描述多语言id]descLangId的值不得小于0");
		}
		this.descLangId = descLangId;
	}
	
	public String getBuffDesc() {
		return this.buffDesc;
	}

	public void setBuffDesc(String buffDesc) {
		if (StringUtils.isEmpty(buffDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ buff描述]buffDesc不可以为空");
		}
		this.buffDesc = buffDesc;
	}
	
	public int getBuffSelfType() {
		return this.buffSelfType;
	}

	public void setBuffSelfType(int buffSelfType) {
		if (buffSelfType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ buff自身类型(buff or debuff)]buffSelfType不可以为0");
		}
		if (buffSelfType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ buff自身类型(buff or debuff)]buffSelfType的值不得小于1");
		}
		this.buffSelfType = buffSelfType;
	}
	
	public int getOverlyingCount() {
		return this.overlyingCount;
	}

	public void setOverlyingCount(int overlyingCount) {
		if (overlyingCount == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ buff最大的叠加次数]overlyingCount不可以为0");
		}
		if (overlyingCount < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ buff最大的叠加次数]overlyingCount的值不得小于1");
		}
		this.overlyingCount = overlyingCount;
	}
	

	@Override
	public String toString() {
		return "BuffTemplateVO[buffType=" + buffType + ",buffResourceId=" + buffResourceId + ",nameLangId=" + nameLangId + ",buffName=" + buffName + ",descLangId=" + descLangId + ",buffDesc=" + buffDesc + ",buffSelfType=" + buffSelfType + ",overlyingCount=" + overlyingCount + ",]";

	}
}