package com.hifun.soul.gameserver.legionmine.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 军团矿战个人buf模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionMineSelfBufTemplateVO extends TemplateObject {

	/** 指令名称 */
	@ExcelCellBinding(offset = 1)
	protected String bufName;

	/** 使用方法 */
	@ExcelCellBinding(offset = 2)
	protected String useGuide;

	/** 取消方法 */
	@ExcelCellBinding(offset = 3)
	protected String cancelMethod;

	/** 功能描述 */
	@ExcelCellBinding(offset = 4)
	protected String functionDesc;

	/** 图标 */
	@ExcelCellBinding(offset = 5)
	protected int bufIcon;


	public String getBufName() {
		return this.bufName;
	}

	public void setBufName(String bufName) {
		if (StringUtils.isEmpty(bufName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[指令名称]bufName不可以为空");
		}
		this.bufName = bufName;
	}
	
	public String getUseGuide() {
		return this.useGuide;
	}

	public void setUseGuide(String useGuide) {
		if (StringUtils.isEmpty(useGuide)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[使用方法]useGuide不可以为空");
		}
		this.useGuide = useGuide;
	}
	
	public String getCancelMethod() {
		return this.cancelMethod;
	}

	public void setCancelMethod(String cancelMethod) {
		this.cancelMethod = cancelMethod;
	}
	
	public String getFunctionDesc() {
		return this.functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		if (StringUtils.isEmpty(functionDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[功能描述]functionDesc不可以为空");
		}
		this.functionDesc = functionDesc;
	}
	
	public int getBufIcon() {
		return this.bufIcon;
	}

	public void setBufIcon(int bufIcon) {
		if (bufIcon == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[图标]bufIcon不可以为0");
		}
		this.bufIcon = bufIcon;
	}
	

	@Override
	public String toString() {
		return "LegionMineSelfBufTemplateVO[bufName=" + bufName + ",useGuide=" + useGuide + ",cancelMethod=" + cancelMethod + ",functionDesc=" + functionDesc + ",bufIcon=" + bufIcon + ",]";

	}
}