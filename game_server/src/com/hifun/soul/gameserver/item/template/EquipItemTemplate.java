package com.hifun.soul.gameserver.item.template;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.core.util.KeyValuePair;


/**
 * 
 * 装备模版
 * 
 * @author Administrator
 *
 */
@ExcelRowBinding
public class EquipItemTemplate extends EquipItemTemplateVO implements
		ItemTemplate {
	
	private List<KeyValuePair<Integer,Integer>> equipAttributes
				= new ArrayList<KeyValuePair<Integer,Integer>>();
	
	@Override
	public void check() throws TemplateConfigException {

	}
	
	@Override
	public void patchUp() {
		try{
			super.patchUp();
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
		
		if (attributes == null || attributes.isEmpty()) {
			return;
		}
		else
		{
			for(EquipItemAttribute attribute : attributes) {
				KeyValuePair<Integer,Integer> _attribute = new KeyValuePair<Integer,Integer>();
				// TODO: cfh 为了最小的改动原来的excel结构暂时不改变EquipItemAttribute中key的类型。
				// 装备升级整体定下来之后再变动
				String key = attribute.getPropKey();
				if(key != null
						&& !"".equals(key.trim())){
					_attribute.setKey(Integer.valueOf(key));
					_attribute.setValue(attribute.getPropValue());
				}
				
				equipAttributes.add(_attribute);
			}
		}		
	}
	@Override
	public boolean isOverlapable() {
		return this.maxOverlap > 1;
	}
	
	public List<KeyValuePair<Integer,Integer>> getEquipAttributes() {
		return equipAttributes;
	}
	
	@Override
	public boolean canSell() {
		return this.sell;
	}
	
	@Override
	public int getLevelLimit() {
		return super.getLimitLevel();
	}
	
	@Override
	public int getOccupationLimit() {
		return super.getLimitOccupation();
	}

}
