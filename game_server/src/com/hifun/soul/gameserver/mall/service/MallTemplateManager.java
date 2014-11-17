package com.hifun.soul.gameserver.mall.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.service.ItemTemplateManager;
import com.hifun.soul.gameserver.mall.MallItemSorter;
import com.hifun.soul.gameserver.mall.MallItemType;
import com.hifun.soul.gameserver.mall.msg.MallItemInfo;
import com.hifun.soul.gameserver.mall.template.MallTemplate;
import com.hifun.soul.gameserver.role.Occupation;


/**
 * 商城服务类
 * 
 * @author magicstone
 * 
 * 
 * 注意： 原则上MallService的类只是对模版的数据重新组织，如果以后有修改数据需求，需要考虑并发
 */
@Scope("singleton")
@Component
public class MallTemplateManager implements IInitializeRequired{
	@Autowired
	private TemplateService templateService;
	@Autowired
	private ItemTemplateManager itemTemplateService;

	/** 商城所有物品 */
	private List<MallItemInfo> allMallItems = new ArrayList<MallItemInfo>();
	/** 需要显示在商城装备强化页签的道具 */
	private List<MallItemInfo> equips = new ArrayList<MallItemInfo>();
	/** 需要显示在商城建筑材料页签的道具 */
	private List<MallItemInfo> materials = new ArrayList<MallItemInfo>();
	/** 需要显示在商城常用道具页签的道具 */
	private List<MallItemInfo> commons = new ArrayList<MallItemInfo>();
	/** 需要显示在商城新品页签的道具 */
	private List<MallItemInfo> news = new ArrayList<MallItemInfo>();
	/** 需要显示在商城打折页签的道具 */
	private List<MallItemInfo> discounts = new ArrayList<MallItemInfo>();
	/** 需要显示在商城限时页签的道具 */
	private List<MallItemInfo> limits = new ArrayList<MallItemInfo>();
	/** key:mallItemType  */
	private Map<Integer,List<MallItemInfo>> mallItemInfoListMap = new HashMap<Integer,List<MallItemInfo>>();
	/** key：itemId */
	private Map<Integer,MallItemInfo> mallItemInfoMap = new HashMap<Integer,MallItemInfo>();
	/** 排序 */
	private MallItemSorter mallItemSorter = new MallItemSorter();
	
	@Override
	public void init() {
		Map<Integer,MallTemplate> malls = templateService.getAll(MallTemplate.class);
		initMallItemInfoMap(malls, itemTemplateService);
		initMallItemInfoListMap();
	}
	
	private void initMallItemInfoListMap() {
		mallItemInfoListMap.put(MallItemType.EQUIP_ITEM.getIndex(), equips);
		mallItemInfoListMap.put(MallItemType.MATERIAL_ITEM.getIndex(), materials);
		mallItemInfoListMap.put(MallItemType.COMMON_ITEM.getIndex(), commons);
		mallItemInfoListMap.put(MallItemType.NEW_ITEM.getIndex(), news);
		mallItemInfoListMap.put(MallItemType.DISCOUNT_ITEM.getIndex(), discounts);
		mallItemInfoListMap.put(MallItemType.LIMIT_ITEM.getIndex(), limits);
	}
	
	private void initMallItems(Map<Integer,MallTemplate> malls) {
		for(MallTemplate mallTemplate : malls.values()){
			if(mallTemplate.isSell()) {
				String[] mallItemTypes = mallTemplate.getMallItemType().split(";");
				for(String mallItemType : mallItemTypes){
					MallItemInfo mallItemInfo = mallItemInfoMap.get(mallTemplate.getItemId());
					allMallItems.add(mallItemInfo);
					if(MallItemType.EQUIP_ITEM.getIndex() == Integer.valueOf(mallItemType)){
						equips.add(mallItemInfo);
					}
					else if(MallItemType.MATERIAL_ITEM.getIndex() == Integer.valueOf(mallItemType)){
						materials.add(mallItemInfo);
					}
					else if(MallItemType.COMMON_ITEM.getIndex() == Integer.valueOf(mallItemType)){
						commons.add(mallItemInfo);				
					}
					else if(MallItemType.NEW_ITEM.getIndex() == Integer.valueOf(mallItemType)){
						news.add(mallItemInfo);
					}
					else if(MallItemType.DISCOUNT_ITEM.getIndex() == Integer.valueOf(mallItemType)){
						discounts.add(mallItemInfo);
					}
					else if(MallItemType.LIMIT_ITEM.getIndex() == Integer.valueOf(mallItemType)){
						limits.add(mallItemInfo);
					}
				}
			}
		}
	}
	
	private void initMallItemInfoMap(Map<Integer,MallTemplate> malls,ItemTemplateManager itemTemplateService) {
		for(MallTemplate template : malls.values()){
			if(template.isSell()){
				MallItemInfo mallItemInfo = new MallItemInfo();
				mallItemInfo.setItemId(template.getItemId());
				mallItemInfo.setCurrencyType(template.getCurrencyType());
				mallItemInfo.setNum(template.getNum());
				mallItemInfo.setDiscount(template.isDiscount());
				mallItemInfo.setDiscountRate(template.getDiscountRate()/100.0f);
				CommonItem commonItem = CommonItemBuilder.genCommonItem(template.getItemId());
				if(commonItem==null){
					throw new NullPointerException("商城售卖物品配置错误,未找到itemId="+template.getItemId()+"的物品。");
				}
				commonItem.setCanSeeLevelLimit(template.getLevelLimit());
				commonItem.setCanSeeOccupationLimit(template.getOccupationLimit());
				mallItemInfo.setCommonItem(commonItem);			
				mallItemInfoMap.put(template.getItemId(), mallItemInfo);
			}
		}
		
		initMallItems(malls);
	}
	
	/**
	 * 得到所有商城需要显示的道具信息
	 * @param human
	 * @return
	 */
	public List<MallItemInfo> getMallItemInfos(int mallItemType, int level, int occupation)
	{
		List<MallItemInfo> canSeeItems = new ArrayList<MallItemInfo>();
		List<MallItemInfo> allItems = new ArrayList<MallItemInfo>();
		if(MallItemType.ALL_ITEM.getIndex() == mallItemType) {
			allItems = allMallItems;
		}
		else{
			allItems = mallItemInfoListMap.get(mallItemType);
		}
		for(MallItemInfo item : allItems){
			if(canSee(item.getCommonItem(), level, occupation)){
				canSeeItems.add(item);
			}
		}
		
		return canSeeItems;
	}
	
	/**
	 * 判断物品对玩家是否可见
	 * 
	 * @param commonItem
	 * @param level
	 * @param occupation
	 * @return
	 */
	public boolean canSee(CommonItem commonItem, int level, int occupation) {
		if(level >= commonItem.getCanSeeLevelLimit()
				&& (occupation == commonItem.getCanSeeOccupationLimit()
					|| Occupation.typeOf(commonItem.getCanSeeOccupationLimit()) == null)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 对目标list进行排序
	 * @param displayList
	 */
	public void sortMallItemList(List<MallItemInfo> mallItemList)
	{
		Collections.sort(mallItemList,getMallItemSorter());
	}
	
	public MallItemInfo getMallItemInfo(int itemId) {
		return mallItemInfoMap.get(itemId);
	}
	
	public MallItemSorter getMallItemSorter() {
		return mallItemSorter;
	}
}
