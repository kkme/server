package com.hifun.soul.gameserver.item.assist;

public class EmbedGemInfo {

	/** 宝石镶嵌的位置 */
	private int index;
	/** 宝石的具体信息 */
	private CommonItem commonItem;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public CommonItem getCommonItem() {
		return commonItem;
	}

	public void setCommonItem(CommonItem commonItem) {
		this.commonItem = commonItem;
	}
	
}
