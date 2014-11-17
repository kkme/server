package com.hifun.soul.gameserver.stage.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import java.util.List;

/**
 * 通关奖励模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class StagePassRewardTemplateVO extends TemplateObject {

	/**  奖励物品1id */
	@ExcelCellBinding(offset = 1)
	protected int item1Id;

	/**  奖励物品1数量 */
	@ExcelCellBinding(offset = 2)
	protected int item1Num;

	/**  奖励物品2id */
	@ExcelCellBinding(offset = 3)
	protected int item2Id;

	/**  奖励物品2数量 */
	@ExcelCellBinding(offset = 4)
	protected int item2Num;

	/**  金币数量 */
	@ExcelCellBinding(offset = 5)
	protected int coin;

	/**  完美通关奖励物品列表 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.item.template.RewardItemData.class, collectionNumber = "6,7;8,9;10,11")
	protected List<com.hifun.soul.gameserver.item.template.RewardItemData> perfectRewardItemList;


	public int getItem1Id() {
		return this.item1Id;
	}

	public void setItem1Id(int item1Id) {
		if (item1Id == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 奖励物品1id]item1Id不可以为0");
		}
		this.item1Id = item1Id;
	}
	
	public int getItem1Num() {
		return this.item1Num;
	}

	public void setItem1Num(int item1Num) {
		if (item1Num == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 奖励物品1数量]item1Num不可以为0");
		}
		this.item1Num = item1Num;
	}
	
	public int getItem2Id() {
		return this.item2Id;
	}

	public void setItem2Id(int item2Id) {
		if (item2Id == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 奖励物品2id]item2Id不可以为0");
		}
		this.item2Id = item2Id;
	}
	
	public int getItem2Num() {
		return this.item2Num;
	}

	public void setItem2Num(int item2Num) {
		if (item2Num == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 奖励物品2数量]item2Num不可以为0");
		}
		this.item2Num = item2Num;
	}
	
	public int getCoin() {
		return this.coin;
	}

	public void setCoin(int coin) {
		if (coin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 金币数量]coin不可以为0");
		}
		this.coin = coin;
	}
	
	public List<com.hifun.soul.gameserver.item.template.RewardItemData> getPerfectRewardItemList() {
		return this.perfectRewardItemList;
	}

	public void setPerfectRewardItemList(List<com.hifun.soul.gameserver.item.template.RewardItemData> perfectRewardItemList) {
		this.perfectRewardItemList = perfectRewardItemList;
	}
	

	@Override
	public String toString() {
		return "StagePassRewardTemplateVO[item1Id=" + item1Id + ",item1Num=" + item1Num + ",item2Id=" + item2Id + ",item2Num=" + item2Num + ",coin=" + coin + ",perfectRewardItemList=" + perfectRewardItemList + ",]";

	}
}