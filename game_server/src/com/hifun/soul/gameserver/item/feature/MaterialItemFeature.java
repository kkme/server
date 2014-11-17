package com.hifun.soul.gameserver.item.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.template.GemAttribute;
import com.hifun.soul.gameserver.item.template.MaterialItemTemplate;


/**
 * 
 * 材料特有属性
 * 
 * @author magicstone
 *
 */
public class MaterialItemFeature implements ItemFeature {

	protected Item item;
	
	private MaterialItemTemplate template;
	
	/** 宝石属性 */	
	private List<KeyValuePair<Integer,Integer>> gemAttributes = null;
	/** 宝石合成属性成长上限 */	
	private Map<Integer,Integer> gemUpgradeLimits = null;
	
	public MaterialItemFeature(Item item) {
		this.item = item;
		
		template = (MaterialItemTemplate)item.getTemplate();
		
		initGemAttributes();
	}
	
	public MaterialItemTemplate getTemplate() {
		return template;
	}
	
	private void initGemAttributes() {
		if(gemAttributes != null){
			return;
		}
		
		if(item.getType() != ItemDetailType.GEM.getIndex()){
			return;			
		}
		List<GemAttribute> gemTempAttributes = template.getAttributes();
		if(gemTempAttributes==null || gemTempAttributes.size()==0){
			return;
		}
		gemAttributes = new ArrayList<KeyValuePair<Integer,Integer>>();
		gemUpgradeLimits = new HashMap<Integer,Integer>();
		for (GemAttribute gemAttr : gemTempAttributes) {
			if(gemAttr==null || gemAttr.getPropKey()<=0 ||gemAttr.getPropValue()<=0){
				continue;
			}
			KeyValuePair<Integer,Integer> attr = new KeyValuePair<Integer, Integer>();
			attr.setKey(gemAttr.getPropKey());
			attr.setValue(gemAttr.getPropValue());
			gemAttributes.add(attr);
			gemUpgradeLimits.put(gemAttr.getPropKey(), gemAttr.getPropMaxValue());
		}
	}
	
	/**
	 * 获取宝石的属性
	 * 
	 * @return
	 */
	public List<KeyValuePair<Integer,Integer>> getGemAttributes(){
		return this.gemAttributes;
	}
	
	/**
	 * 设置宝石的属性
	 * 
	 * @param gemAttributes
	 */
	public void setGemAttributes(List<KeyValuePair<Integer,Integer>> attributes){
		this.gemAttributes = new ArrayList<KeyValuePair<Integer,Integer>>();
		for(KeyValuePair<Integer,Integer> attr : attributes){
			if(attr==null || attr.getKey()<=0){
				continue;
			}
			gemAttributes.add(attr);
		}
	}	
	
	/**
	 * 获取宝石属性成长上限
	 * @return
	 */
	public Map<Integer, Integer> getGemUpgradeLimits() {
		return gemUpgradeLimits;
	}

	/**
	 * 获取宝石镶嵌需要消耗的货币类型
	 * 
	 * @return
	 */
	public CurrencyType getGemEmbedCurrencyType() {
		return CurrencyType.indexOf(
				template.getGemEmbedCurrencyType()
				);
	}
	
	/**
	 * 获取宝石镶嵌需要消耗的货币数量
	 * 
	 * @return
	 */
	public int getGemEnbedCurrencyNum() {
		return template.getGemEmbedCurrencyNum();
	}
	
	/**
	 * 获取卸下宝石需要消耗的货币类型
	 * 
	 * @return
	 */
	public CurrencyType getGemExtractCurrencyType() {
		return CurrencyType.indexOf(
				template.getGemExtractCurrencyType()
				);
	}
	
	/**
	 * 获取卸下宝石需要消耗的货币数量
	 * 
	 * @return
	 */
	public int getGemExtractCurrencyNum() {
		return template.getGemExtractCurrencyNum();
	}
}
