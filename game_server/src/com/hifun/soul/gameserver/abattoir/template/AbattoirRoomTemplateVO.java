package com.hifun.soul.gameserver.abattoir.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import com.hifun.soul.core.util.StringUtils;

/**
 * 角斗场房间模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class AbattoirRoomTemplateVO extends TemplateObject {

	/** 玩家等级下限 */
	@ExcelCellBinding(offset = 1)
	protected int levelLowest;

	/** 玩家等级上限 */
	@ExcelCellBinding(offset = 2)
	protected int levelHighest;

	/** 货币类型 */
	@ExcelCellBinding(offset = 3)
	protected String currentType;

	/** 角斗场房间 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.abattoir.template.AbattoirRoomTemp[].class, collectionNumber = "4,5,6,7;8,9,10,11;12,13,14,15")
	protected com.hifun.soul.gameserver.abattoir.template.AbattoirRoomTemp[] abattoirRooms;

	/** 单次收益时间上限(分钟) */
	@ExcelCellBinding(offset = 16)
	protected int timeLimit;

	/** 保护时间 */
	@ExcelCellBinding(offset = 17)
	protected int protectTime;


	public int getLevelLowest() {
		return this.levelLowest;
	}

	public void setLevelLowest(int levelLowest) {
		if (levelLowest == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[玩家等级下限]levelLowest不可以为0");
		}
		this.levelLowest = levelLowest;
	}
	
	public int getLevelHighest() {
		return this.levelHighest;
	}

	public void setLevelHighest(int levelHighest) {
		if (levelHighest == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[玩家等级上限]levelHighest不可以为0");
		}
		this.levelHighest = levelHighest;
	}
	
	public String getCurrentType() {
		return this.currentType;
	}

	public void setCurrentType(String currentType) {
		if (StringUtils.isEmpty(currentType)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[货币类型]currentType不可以为空");
		}
		this.currentType = currentType;
	}
	
	public com.hifun.soul.gameserver.abattoir.template.AbattoirRoomTemp[] getAbattoirRooms() {
		return this.abattoirRooms;
	}

	public void setAbattoirRooms(com.hifun.soul.gameserver.abattoir.template.AbattoirRoomTemp[] abattoirRooms) {
		if (abattoirRooms == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[角斗场房间]abattoirRooms不可以为空");
		}	
		this.abattoirRooms = abattoirRooms;
	}
	
	public int getTimeLimit() {
		return this.timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		if (timeLimit == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[单次收益时间上限(分钟)]timeLimit不可以为0");
		}
		this.timeLimit = timeLimit;
	}
	
	public int getProtectTime() {
		return this.protectTime;
	}

	public void setProtectTime(int protectTime) {
		this.protectTime = protectTime;
	}
	

	@Override
	public String toString() {
		return "AbattoirRoomTemplateVO[levelLowest=" + levelLowest + ",levelHighest=" + levelHighest + ",currentType=" + currentType + ",abattoirRooms=" + abattoirRooms + ",timeLimit=" + timeLimit + ",protectTime=" + protectTime + ",]";

	}
}