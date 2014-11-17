package com.hifun.soul.gameserver.stage.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 关卡地图模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class StageMapTemplateVO extends TemplateObject {

	/**  多语言地图的名字id */
	@ExcelCellBinding(offset = 1)
	protected int mapNameLangId;

	/**  地图名称 */
	@ExcelCellBinding(offset = 2)
	protected String mapName;

	/**  多语言地图的描述id */
	@ExcelCellBinding(offset = 3)
	protected int mapDescLangId;

	/**  地图描述 */
	@ExcelCellBinding(offset = 4)
	protected String mapDesc;

	/**  下张地图id */
	@ExcelCellBinding(offset = 5)
	protected int nextMapId;

	/**  地图icon */
	@ExcelCellBinding(offset = 6)
	protected int icon;

	/**  多语言章节id */
	@ExcelCellBinding(offset = 7)
	protected int chapterLangId;

	/**  章节 */
	@ExcelCellBinding(offset = 8)
	protected String chapter;

	/**  地图小icon */
	@ExcelCellBinding(offset = 9)
	protected int simpleIcon;


	public int getMapNameLangId() {
		return this.mapNameLangId;
	}

	public void setMapNameLangId(int mapNameLangId) {
		if (mapNameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 多语言地图的名字id]mapNameLangId的值不得小于0");
		}
		this.mapNameLangId = mapNameLangId;
	}
	
	public String getMapName() {
		return this.mapName;
	}

	public void setMapName(String mapName) {
		if (StringUtils.isEmpty(mapName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 地图名称]mapName不可以为空");
		}
		this.mapName = mapName;
	}
	
	public int getMapDescLangId() {
		return this.mapDescLangId;
	}

	public void setMapDescLangId(int mapDescLangId) {
		if (mapDescLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 多语言地图的描述id]mapDescLangId的值不得小于0");
		}
		this.mapDescLangId = mapDescLangId;
	}
	
	public String getMapDesc() {
		return this.mapDesc;
	}

	public void setMapDesc(String mapDesc) {
		if (StringUtils.isEmpty(mapDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 地图描述]mapDesc不可以为空");
		}
		this.mapDesc = mapDesc;
	}
	
	public int getNextMapId() {
		return this.nextMapId;
	}

	public void setNextMapId(int nextMapId) {
		if (nextMapId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 下张地图id]nextMapId的值不得小于0");
		}
		this.nextMapId = nextMapId;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 地图icon]icon的值不得小于0");
		}
		this.icon = icon;
	}
	
	public int getChapterLangId() {
		return this.chapterLangId;
	}

	public void setChapterLangId(int chapterLangId) {
		if (chapterLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 多语言章节id]chapterLangId的值不得小于0");
		}
		this.chapterLangId = chapterLangId;
	}
	
	public String getChapter() {
		return this.chapter;
	}

	public void setChapter(String chapter) {
		if (StringUtils.isEmpty(chapter)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 章节]chapter不可以为空");
		}
		this.chapter = chapter;
	}
	
	public int getSimpleIcon() {
		return this.simpleIcon;
	}

	public void setSimpleIcon(int simpleIcon) {
		if (simpleIcon < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 地图小icon]simpleIcon的值不得小于0");
		}
		this.simpleIcon = simpleIcon;
	}
	

	@Override
	public String toString() {
		return "StageMapTemplateVO[mapNameLangId=" + mapNameLangId + ",mapName=" + mapName + ",mapDescLangId=" + mapDescLangId + ",mapDesc=" + mapDesc + ",nextMapId=" + nextMapId + ",icon=" + icon + ",chapterLangId=" + chapterLangId + ",chapter=" + chapter + ",simpleIcon=" + simpleIcon + ",]";

	}
}