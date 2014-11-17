package com.hifun.soul.gameserver.mars.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 战神之巅房间品质模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class MarsRoomTemplateVO extends TemplateObject {

	/** 房间名称 */
	@ExcelCellBinding(offset = 1)
	protected String roomName;

	/** 杀戮值 */
	@ExcelCellBinding(offset = 2)
	protected int killValue;

	/** 对手等级下限参数 */
	@ExcelCellBinding(offset = 3)
	protected int lowestLevelParam;

	/** 对手等级上限参数 */
	@ExcelCellBinding(offset = 4)
	protected int highestLevelParam;

	/** 军团贡献值 */
	@ExcelCellBinding(offset = 5)
	protected int legionContribution;


	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String roomName) {
		if (StringUtils.isEmpty(roomName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[房间名称]roomName不可以为空");
		}
		this.roomName = roomName;
	}
	
	public int getKillValue() {
		return this.killValue;
	}

	public void setKillValue(int killValue) {
		if (killValue == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[杀戮值]killValue不可以为0");
		}
		this.killValue = killValue;
	}
	
	public int getLowestLevelParam() {
		return this.lowestLevelParam;
	}

	public void setLowestLevelParam(int lowestLevelParam) {
		this.lowestLevelParam = lowestLevelParam;
	}
	
	public int getHighestLevelParam() {
		return this.highestLevelParam;
	}

	public void setHighestLevelParam(int highestLevelParam) {
		this.highestLevelParam = highestLevelParam;
	}
	
	public int getLegionContribution() {
		return this.legionContribution;
	}

	public void setLegionContribution(int legionContribution) {
		if (legionContribution < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[军团贡献值]legionContribution的值不得小于0");
		}
		this.legionContribution = legionContribution;
	}
	

	@Override
	public String toString() {
		return "MarsRoomTemplateVO[roomName=" + roomName + ",killValue=" + killValue + ",lowestLevelParam=" + lowestLevelParam + ",highestLevelParam=" + highestLevelParam + ",legionContribution=" + legionContribution + ",]";

	}
}