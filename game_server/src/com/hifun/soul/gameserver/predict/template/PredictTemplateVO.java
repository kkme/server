package com.hifun.soul.gameserver.predict.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import java.util.List;

/**
 * 预见模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class PredictTemplateVO extends TemplateObject {

	/** 激活需要等级 */
	@ExcelCellBinding(offset = 1)
	protected int needLevel;

	/** 页码 */
	@ExcelCellBinding(offset = 2)
	protected int pageIndex;

	/** 加成属性 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.predict.template.PredictAttribute.class, collectionNumber = "3,4;5,6;7,8;9,10;11,12")
	protected List<com.hifun.soul.gameserver.predict.template.PredictAttribute> attributes;

	/** 加成方式 */
	@ExcelCellBinding(offset = 13)
	protected int amendType;

	/** x坐标 */
	@ExcelCellBinding(offset = 14)
	protected int x;

	/** y坐标 */
	@ExcelCellBinding(offset = 15)
	protected int y;


	public int getNeedLevel() {
		return this.needLevel;
	}

	public void setNeedLevel(int needLevel) {
		if (needLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[激活需要等级]needLevel不可以为0");
		}
		this.needLevel = needLevel;
	}
	
	public int getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[页码]pageIndex不可以为0");
		}
		this.pageIndex = pageIndex;
	}
	
	public List<com.hifun.soul.gameserver.predict.template.PredictAttribute> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(List<com.hifun.soul.gameserver.predict.template.PredictAttribute> attributes) {
		if (attributes == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[加成属性]attributes不可以为空");
		}	
		this.attributes = attributes;
	}
	
	public int getAmendType() {
		return this.amendType;
	}

	public void setAmendType(int amendType) {
		if (amendType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[加成方式]amendType不可以为0");
		}
		this.amendType = amendType;
	}
	
	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		if (x == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[x坐标]x不可以为0");
		}
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		if (y == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[y坐标]y不可以为0");
		}
		this.y = y;
	}
	

	@Override
	public String toString() {
		return "PredictTemplateVO[needLevel=" + needLevel + ",pageIndex=" + pageIndex + ",attributes=" + attributes + ",amendType=" + amendType + ",x=" + x + ",y=" + y + ",]";

	}
}