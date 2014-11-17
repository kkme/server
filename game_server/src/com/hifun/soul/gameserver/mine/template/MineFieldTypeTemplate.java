package com.hifun.soul.gameserver.mine.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 矿坑类型模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public class MineFieldTypeTemplate extends MineFieldTypeTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		String[] itemIds = getItemIds().split(",");
		String[] itemNums = getItemNums().split(",");
		String[] weights = getItemWeights().split(",");
		String[] icons = getItemIcons().split(",");	
		if(itemIds.length != itemNums.length || itemIds.length != weights.length || itemIds.length != icons.length){
			throw new TemplateConfigException(this.getSheetName(), getId(), "产出物品数组配置不正确，各数组长度不一");
		}
		int itemCount = itemIds.length;
		itemIdArray = new int[itemCount];
		itemNumArray = new int[itemCount];
		itemWeightArray = new int[itemCount];
		itemIconArray = new int[itemCount];
		for (int i = 0; i < itemIds.length; i++) {
			itemIdArray[i] = Integer.parseInt(itemIds[i]);
			itemNumArray[i] = Integer.parseInt(itemNums[i]);
			itemWeightArray[i] = Integer.parseInt(weights[i]);
			itemIconArray[i] = Integer.parseInt(icons[i]);
		}
	}
	
	private int[] itemIdArray;
	private int[] itemNumArray;	
	private int[] itemWeightArray;
	private int[] itemIconArray;
	
	/**
	 * 获取产出物品id数组
	 * @return
	 */
	public int[] getItemIdArray() {
		return itemIdArray;
	}
	/**
	 * 获取产出物品数量数组
	 * @return
	 */
	public int[] getItemNumArray() {
		return itemNumArray;
	}
	/**
	 * 获取物品产出权重数组
	 * @return
	 */
	public int[] getItemWeightArray() {
		return itemWeightArray;
	}
	/**
	 * 获取产出物品图标数组
	 * @return
	 */
	public int[] getItemIconArray() {
		return itemIconArray;
	}
	
	

}
