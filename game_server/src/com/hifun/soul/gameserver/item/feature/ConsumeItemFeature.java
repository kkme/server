package com.hifun.soul.gameserver.item.feature;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.gameserver.item.DynamicPropertyType;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.template.ConsumeItemTemplate;

/**
 * 
 * 消耗品特有属性
 * 
 * @author magicstone
 * 
 */
public class ConsumeItemFeature implements ItemFeature {

	protected Item item;

	private ConsumeItemTemplate template;

	private float extraSuccessRate;
	/**
	 * 动态属性
	 */
	private Map<DynamicPropertyType,Integer> dynamicProps = new HashMap<DynamicPropertyType,Integer>();

	public float getExtraSuccessRate() {
		return extraSuccessRate;
	}

	public ConsumeItemFeature(Item item) {
		this.item = item;

		template = (ConsumeItemTemplate) item.getTemplate();
		if (item.getType() == ItemDetailType.FORTUNESTONE.getIndex()) {
			this.extraSuccessRate = template.getIntParam1();
		}

	}

	public ConsumeItemTemplate getTemplate() {
		return template;
	}
	
	public void setDynamicProperty(DynamicPropertyType dynamicPropertyType, int value){
		dynamicProps.put(dynamicPropertyType, value);
	}
	
	public int getDynamicPropertyValue(DynamicPropertyType dynamicPropertyType){
		if(dynamicProps.containsKey(dynamicPropertyType)){
			return dynamicProps.get(dynamicPropertyType);
		}
		return 0;
	}
	
	public Map<DynamicPropertyType,Integer> getDynamicProperties(){
		return this.dynamicProps;
	}
}
