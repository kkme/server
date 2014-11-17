package com.hifun.soul.gameserver.functionhelper.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 功能助手分类映射模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class FuncHelperMapTemplateVO extends TemplateObject {

	/** 分类id */
	@ExcelCellBinding(offset = 1)
	protected int categoryId;

	/** 功能id */
	@ExcelCellBinding(offset = 2)
	protected int funcHelperId;

	/** 推荐星星数 */
	@ExcelCellBinding(offset = 3)
	protected int star;


	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		if (categoryId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[分类id]categoryId不可以为0");
		}
		this.categoryId = categoryId;
	}
	
	public int getFuncHelperId() {
		return this.funcHelperId;
	}

	public void setFuncHelperId(int funcHelperId) {
		if (funcHelperId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[功能id]funcHelperId不可以为0");
		}
		this.funcHelperId = funcHelperId;
	}
	
	public int getStar() {
		return this.star;
	}

	public void setStar(int star) {
		if (star == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[推荐星星数]star不可以为0");
		}
		this.star = star;
	}
	

	@Override
	public String toString() {
		return "FuncHelperMapTemplateVO[categoryId=" + categoryId + ",funcHelperId=" + funcHelperId + ",star=" + star + ",]";

	}
}