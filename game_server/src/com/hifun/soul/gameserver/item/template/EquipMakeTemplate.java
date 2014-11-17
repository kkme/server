package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
/**
 * 装备打造模板
 * 
 * @author magicstone
 *
 */
@ExcelRowBinding
public class EquipMakeTemplate extends EquipMakeTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		String[] materialIds = getMaterialItemIds().split(",");
		String[] needNums = getMaterialItemNum().split(",");
		if(materialIds.length != needNums.length){
			throw new TemplateConfigException(this.getSheetName(), getId(), "需要的材料数组和数量数值长度不一致");
		}
		int materialCount = materialIds.length;
		materialIdArray = new int[materialCount];
		materialNumArray = new int[materialCount];
		for(int i=0;i<materialIds.length; i++){
			materialIdArray[i] = Integer.parseInt(materialIds[i]);
			materialNumArray[i] = Integer.parseInt(needNums[i]);
		}		
	}
	private int[] materialIdArray;
	private int[] materialNumArray;
	
	/**
	 * 获取所需材料Id数组
	 * @return
	 */
	public int[] getMaterialIdArray() {
		return materialIdArray;
	}
	
	/**
	 * 获取所需材料数量数组
	 * @return
	 */
	public int[] getMaterialNumArray() {
		return materialNumArray;
	}
	
}
