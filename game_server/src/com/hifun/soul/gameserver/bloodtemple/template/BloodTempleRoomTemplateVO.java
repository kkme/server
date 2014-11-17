package com.hifun.soul.gameserver.bloodtemple.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 嗜血神殿房间模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class BloodTempleRoomTemplateVO extends TemplateObject {

	/** 多语言 */
	@ExcelCellBinding(offset = 1)
	protected int language;

	/** 房间分页ID */
	@ExcelCellBinding(offset = 2)
	protected int pageIndex;

	/** 房间名称 */
	@ExcelCellBinding(offset = 3)
	protected String roomName;

	/** 货币类型 */
	@ExcelCellBinding(offset = 4)
	protected String currentType;

	/** 单次时间收益(分钟) */
	@ExcelCellBinding(offset = 5)
	protected int revenue;

	/** 推荐npc等级 */
	@ExcelCellBinding(offset = 6)
	protected int npcLevel;

	/** 保护时间 */
	@ExcelCellBinding(offset = 7)
	protected int protectTime;


	public int getLanguage() {
		return this.language;
	}

	public void setLanguage(int language) {
		if (language == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[多语言]language不可以为0");
		}
		this.language = language;
	}
	
	public int getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[房间分页ID]pageIndex不可以为0");
		}
		this.pageIndex = pageIndex;
	}
	
	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String roomName) {
		if (StringUtils.isEmpty(roomName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[房间名称]roomName不可以为空");
		}
		this.roomName = roomName;
	}
	
	public String getCurrentType() {
		return this.currentType;
	}

	public void setCurrentType(String currentType) {
		if (StringUtils.isEmpty(currentType)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[货币类型]currentType不可以为空");
		}
		this.currentType = currentType;
	}
	
	public int getRevenue() {
		return this.revenue;
	}

	public void setRevenue(int revenue) {
		if (revenue == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[单次时间收益(分钟)]revenue不可以为0");
		}
		this.revenue = revenue;
	}
	
	public int getNpcLevel() {
		return this.npcLevel;
	}

	public void setNpcLevel(int npcLevel) {
		if (npcLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[推荐npc等级]npcLevel不可以为0");
		}
		this.npcLevel = npcLevel;
	}
	
	public int getProtectTime() {
		return this.protectTime;
	}

	public void setProtectTime(int protectTime) {
		this.protectTime = protectTime;
	}
	

	@Override
	public String toString() {
		return "BloodTempleRoomTemplateVO[language=" + language + ",pageIndex=" + pageIndex + ",roomName=" + roomName + ",currentType=" + currentType + ",revenue=" + revenue + ",npcLevel=" + npcLevel + ",protectTime=" + protectTime + ",]";

	}
}