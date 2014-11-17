package com.hifun.soul.gameserver.abattoir.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.template.BeanFieldNumber;

@ExcelRowBinding
public class AbattoirRoomTemp {
	/** 房间名称 */
	@BeanFieldNumber(number = 1)
	private String roomName;
	/** 单位时间(分钟)收益 */
	@BeanFieldNumber(number = 2)
	private int revenue;
	/** 推荐npc等级下限 */
	@BeanFieldNumber(number = 3)
	private int npcLevelLowest;
	/** 推荐npc等级上限 */
	@BeanFieldNumber(number = 4)
	private int npcLevelHighest;

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getRevenue() {
		return revenue;
	}

	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	public int getNpcLevelLowest() {
		return npcLevelLowest;
	}

	public void setNpcLevelLowest(int npcLevelLowest) {
		this.npcLevelLowest = npcLevelLowest;
	}

	public int getNpcLevelHighest() {
		return npcLevelHighest;
	}

	public void setNpcLevelHighest(int npcLevelHighest) {
		this.npcLevelHighest = npcLevelHighest;
	}

}
