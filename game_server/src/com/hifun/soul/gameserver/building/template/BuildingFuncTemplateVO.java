package com.hifun.soul.gameserver.building.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 建筑功能模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class BuildingFuncTemplateVO extends TemplateObject {

	/**  建筑id */
	@ExcelCellBinding(offset = 1)
	protected int buildingId;

	/**  显示优先级 */
	@ExcelCellBinding(offset = 2)
	protected int priority;

	/**  显示等级限制 */
	@ExcelCellBinding(offset = 3)
	protected int level;


	public int getBuildingId() {
		return this.buildingId;
	}

	public void setBuildingId(int buildingId) {
		if (buildingId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 建筑id]buildingId不可以为0");
		}
		if (buildingId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 建筑id]buildingId的值不得小于1");
		}
		this.buildingId = buildingId;
	}
	
	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		if (priority == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 显示优先级]priority不可以为0");
		}
		if (priority < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 显示优先级]priority的值不得小于1");
		}
		this.priority = priority;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 显示等级限制]level的值不得小于0");
		}
		this.level = level;
	}
	

	@Override
	public String toString() {
		return "BuildingFuncTemplateVO[buildingId=" + buildingId + ",priority=" + priority + ",level=" + level + ",]";

	}
}