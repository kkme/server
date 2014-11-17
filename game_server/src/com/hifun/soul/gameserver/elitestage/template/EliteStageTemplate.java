package com.hifun.soul.gameserver.elitestage.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;

/**
 * 精英副本模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public class EliteStageTemplate extends EliteStageTemplateVO {
	/** 掉落物品id数组 */
	private int[] itemIdArr;
	/** 掉落物品概率数组 */
	private int[] itemRateArr;
	
	
	
	public int[] getItemIdArr() {
		return itemIdArr;
	}

	public int[] getItemRateArr() {
		return itemRateArr;
	}

	@Override
	public void check() throws TemplateConfigException {
		if(this.getItemIds().length()<0 || this.getItemRates().length()<0){
			throw new TemplateConfigException(this.sheetName, id, "掉落物品和掉落概率不能为空");
		}
		String[] temArr = this.getItemIds().split(",");
		itemIdArr = new int[temArr.length];
		for(int i=0;i<temArr.length ; i++){
			itemIdArr[i] = Integer.parseInt(temArr[i]);
		}
		temArr = this.getItemRates().split(",");
		itemRateArr = new int[temArr.length];
		for(int i=0;i<temArr.length ; i++){
			itemRateArr[i] = Integer.parseInt(temArr[i]);
		}
		if(itemIdArr.length != itemRateArr.length){
			throw new TemplateConfigException(this.sheetName, id, "掉落物品个数与掉落概率个数不相等");
		}
	}

}
