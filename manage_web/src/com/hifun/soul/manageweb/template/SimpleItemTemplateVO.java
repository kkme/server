package com.hifun.soul.manageweb.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 消耗物品模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SimpleItemTemplateVO extends TemplateObject {

	/**  物品的名字 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/**  物品描述 */
	@ExcelCellBinding(offset = 2)
	protected String desc;

	/**  道具种类（道具的详细种类，可能排序的时候用到） */
	@ExcelCellBinding(offset = 3)
	protected String category;

	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {		
		this.name = name;
	}
	
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {		
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "ConsumeItemTemplateVO[name=" + name + ",desc=" + desc + ",category=" + category + "]";

	}
}