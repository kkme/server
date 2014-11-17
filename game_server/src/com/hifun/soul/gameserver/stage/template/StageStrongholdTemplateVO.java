package com.hifun.soul.gameserver.stage.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 关卡据点模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class StageStrongholdTemplateVO extends TemplateObject {

	/**  地图id */
	@ExcelCellBinding(offset = 1)
	protected int mapId;

	/**  剧情背景id(client去读) */
	@ExcelCellBinding(offset = 2)
	protected int bgId;

	/**  据点形象资源id(client去读) */
	@ExcelCellBinding(offset = 3)
	protected int ressourceId;

	/**  据点x坐标(client去读) */
	@ExcelCellBinding(offset = 4)
	protected int x;

	/**  据点y坐标(client去读) */
	@ExcelCellBinding(offset = 5)
	protected int y;

	/**  名称 */
	@ExcelCellBinding(offset = 6)
	protected String name;

	/**  剧情描述 */
	@ExcelCellBinding(offset = 7)
	protected String desc;


	public int getMapId() {
		return this.mapId;
	}

	public void setMapId(int mapId) {
		if (mapId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 地图id]mapId不可以为0");
		}
		if (mapId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 地图id]mapId的值不得小于1");
		}
		this.mapId = mapId;
	}
	
	public int getBgId() {
		return this.bgId;
	}

	public void setBgId(int bgId) {
		if (bgId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 剧情背景id(client去读)]bgId不可以为0");
		}
		if (bgId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 剧情背景id(client去读)]bgId的值不得小于0");
		}
		this.bgId = bgId;
	}
	
	public int getRessourceId() {
		return this.ressourceId;
	}

	public void setRessourceId(int ressourceId) {
		if (ressourceId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 据点形象资源id(client去读)]ressourceId不可以为0");
		}
		if (ressourceId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 据点形象资源id(client去读)]ressourceId的值不得小于0");
		}
		this.ressourceId = ressourceId;
	}
	
	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 名称]name不可以为空");
		}
		this.name = name;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 剧情描述]desc不可以为空");
		}
		this.desc = desc;
	}
	

	@Override
	public String toString() {
		return "StageStrongholdTemplateVO[mapId=" + mapId + ",bgId=" + bgId + ",ressourceId=" + ressourceId + ",x=" + x + ",y=" + y + ",name=" + name + ",desc=" + desc + ",]";

	}
}