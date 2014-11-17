package com.hifun.soul.gameserver.shop.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gameserver.shop.SpecialShopNotify;

@Scope("singleton")
@Component
public class SpecialShopNotifyService implements IInitializeRequired {

	private LinkedList<SpecialShopNotify> items = new LinkedList<SpecialShopNotify>();
	
	@Override
	public void init() {
		
	}
	
	public SpecialShopNotify[] getSpecialShopNotify() {
		return items.toArray(new SpecialShopNotify[0]);
	}
	
	public void addSimpleSpecialShopItem(SpecialShopNotify simpleSpecialShopItem) {
		if(items.size() >= SharedConstants.SPECIAL_SHOP_NOTIFY_NUM){
			items.removeLast();
		}
		
		items.addFirst(simpleSpecialShopItem);
	}
	
	public void start(IDBService dbService) {
		loadSimpleSpecialItems(dbService);
	}
	
	private void loadSimpleSpecialItems(IDBService dbService) {
		List<?> result = dbService.findByNamedQuery(DataQueryConstants.QUERY_SPECIAL_SHOP_BUY_INFO);
		if(!result.isEmpty()){
			@SuppressWarnings("unchecked")
			List<Object[]> rewardInfos = (List<Object[]>)result;
			int i=0;
			for(Object[] objects : rewardInfos){
				if(i>= SharedConstants.SPECIAL_SHOP_NOTIFY_NUM){
					break;
				}
				SpecialShopNotify simpleSpecialShopItem = new SpecialShopNotify();
				simpleSpecialShopItem.setId(Integer.valueOf(objects[0].toString()));
				simpleSpecialShopItem.setRoleName(objects[1].toString());
				simpleSpecialShopItem.setItemName(objects[2].toString());
				simpleSpecialShopItem.setSpecialShopItemId(Integer.valueOf(objects[3].toString()));
				simpleSpecialShopItem.setItemId(Integer.valueOf(objects[4].toString()));
				simpleSpecialShopItem.setItemNum(Integer.valueOf(objects[5].toString()));
				items.add(simpleSpecialShopItem);
				i++;
			}
		}
	}


}
