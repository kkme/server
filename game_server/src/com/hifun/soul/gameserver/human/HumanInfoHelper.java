package com.hifun.soul.gameserver.human;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.service.DataService;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.horoscope.HoroscopeBagType;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.human.msg.GCCharacterShowInfo;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItem;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.Level1Property;
import com.hifun.soul.gameserver.role.properties.Level2Property;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.gameserver.scene.SceneHumanManager;
import com.hifun.soul.proto.common.Character.CharProperty;
import com.hifun.soul.proto.common.Horoscopes.Horoscope;
import com.hifun.soul.proto.data.entity.Entity.HumanHoroscope;
import com.hifun.soul.proto.data.entity.Entity.HumanItem;
import com.hifun.soul.proto.data.entity.Entity.HumanOtherProperties;

public class HumanInfoHelper {

	private static Logger logger = Loggers.GAME_LOGGER;

	public static void sendHumanBaseInfoMessage(Human human, long showCharacterId) {

		SceneHumanManager humanManager = GameServerAssist.getGameWorld().getSceneHumanManager();
		Human showHuman = humanManager.getHumanByGuid(showCharacterId);
		if (showHuman != null) {
			sendHumanBaseInfoMessage(human, showHuman);
		} else {
			// 查库
			sendHumanBaseInfoMessageAfterDbQuery(human, showCharacterId);			
		}
	}

	/**
	 * 
	 * @param human
	 * @param showCharacterId
	 */
	private static void sendHumanBaseInfoMessageAfterDbQuery(final Human human, final long showCharacterId) {
		IDataService dataService = ApplicationContext.getApplicationContext().getBean(DataService.class);
		dataService.query(DataQueryConstants.QUERY_PORPERTY_AND_ITEM_BY_HUMAN_ID, new String[] { "humanGuid" },
				new Object[] { showCharacterId }, new IDBCallback<List<?>>() {
					@Override
					public void onSucceed(List<?> result) {
						if (result == null || result.size() == 0) {
							human.sendErrorMessage(LangConstants.NO_ROLE);
							return;
						}
						sendHumanBaseInfoMessage(human, (HumanEntity)result.get(0));
					}

					@Override
					public void onFailed(String errorMsg) {
						logger.error("qury property and item info failed ! errorMsg:", errorMsg);
					}
				});
	}

	/**
	 * 
	 * @param human
	 * @param showHuman
	 */
	private static void sendHumanBaseInfoMessage(Human human, HumanEntity humanEntity) {
		List<CommonItem> equipments = new ArrayList<CommonItem>();
		List<HumanItem> items = humanEntity.getBuilder().getItemList();
		for (HumanItem humanItem : items) {
			if (humanItem.getItem().getIsEquiped()) {
				Item item = ItemFactory.convertHumanItemToItem(null, humanItem);
				equipments.add(CommonItemBuilder.converToCommonItem(item));
			}
		}
		CommonItem[] equipedItems = equipments.toArray(new CommonItem[0]);
		HumanOtherProperties otherProps = humanEntity.getBuilder().getOtherProperties();
		List<CharProperty> charPropertyList = otherProps.getPropsList();
		KeyValuePair<Integer, Integer>[] properties = KeyValuePair.newKeyValuePairArray(charPropertyList.size());
		int i = 0;
		for (CharProperty charProperty : charPropertyList) {
			properties[i] = new KeyValuePair<Integer, Integer>();
			properties[i].setKey(charProperty.getKey());
			properties[i].setValue((int) charProperty.getValue());
			i++;
		}
		List<HumanHoroscope> equipedHoroscopes = humanEntity.getBuilder().getHoroscopeList();
		List<HoroscopeInfo> horoscopes = new ArrayList<HoroscopeInfo>();
		for (HumanHoroscope humanHoros : equipedHoroscopes) {
			Horoscope horos = humanHoros.getHoroscope();
			if (horos.getBagType() == HoroscopeBagType.EQUIP_BAG.getIndex()) {
				HoroscopeInfo horosInfo = GameServerAssist.getHoroscopeTemplateManager().genHoroscopeInfo(
						horos.getHoroscopeId(), HoroscopeBagType.indexOf(horos.getBagType()));
				horosInfo.setUuid(horos.getHoroscopeKey());
				horosInfo.setExperience(horos.getExperience());
				horosInfo.setIndex(horos.getBagIndex());
				horoscopes.add(horosInfo);
			}
		}
		GCCharacterShowInfo gcCharacterShowMsg = new GCCharacterShowInfo();
		gcCharacterShowMsg.setHumanId(humanEntity.getId());
		gcCharacterShowMsg.setName(humanEntity.getName());
		gcCharacterShowMsg.setEquipments(equipedItems);
		gcCharacterShowMsg.setEquipedHoroscopeInfos(horoscopes.toArray(new HoroscopeInfo[0]));
		gcCharacterShowMsg.setProperties(properties);
		human.sendMessage(gcCharacterShowMsg);
	}

	/**
	 * 
	 * @param human
	 * @param showHuman
	 */
	private static void sendHumanBaseInfoMessage(Human human, Human showHuman) {

		List<Item> equipments = showHuman.getBagManager().getEquipedItems();
		CommonItem[] commonItems = CommonItemBuilder.converToCommonItemArray(equipments);

		HumanPropertyManager propertyManager = showHuman.getHumanPropertiesManager();
		List<KeyValuePair<Integer, Integer>> properties = new ArrayList<KeyValuePair<Integer, Integer>>();
		// 所有一级属性
		for (int i = Level1Property._BEGIN; i <= Level1Property._END; i++) {
			KeyValuePair<Integer, Integer> prop = new KeyValuePair<Integer, Integer>();
			prop.setKey(PropertyType.genPropertyKey(i, PropertyType.LEVEL1_PROPERTY));
			prop.setValue(propertyManager.getIntPropertySet(PropertyType.LEVEL1_PROPERTY).getPropertyValue(i));
			properties.add(prop);
		}
		// 所有2级属性
		for (int i = Level2Property._BEGIN; i <= Level2Property._END; i++) {
			KeyValuePair<Integer, Integer> prop = new KeyValuePair<Integer, Integer>();
			prop.setKey(PropertyType.genPropertyKey(i, PropertyType.LEVEL2_PROPERTY));
			prop.setValue(propertyManager.getIntPropertySet(PropertyType.LEVEL2_PROPERTY).getPropertyValue(i));
			properties.add(prop);
		}
		// 等级
		properties.add(getHumanProperty(propertyManager, PropertyType.HUMAN_INT_PROPERTY, HumanIntProperty.LEVEL));
		// 职业
		properties.add(getHumanProperty(propertyManager, PropertyType.HUMAN_INT_PROPERTY, HumanIntProperty.OCCUPATION));
		KeyValuePair<Integer, Integer>[] array = KeyValuePair.newKeyValuePairArray(0);
		GCCharacterShowInfo gcCharacterShowMsg = new GCCharacterShowInfo();
		gcCharacterShowMsg.setHumanId(showHuman.getHumanGuid());
		gcCharacterShowMsg.setName(showHuman.getName());
		gcCharacterShowMsg.setProperties(properties.toArray(array));
		gcCharacterShowMsg.setEquipments(commonItems);
		// 装备的星运
		gcCharacterShowMsg.setEquipedHoroscopeInfos(showHuman.getHumanHoroscopeManager().getEquipBagHoroscopeInfos());
		human.sendMessage(gcCharacterShowMsg);
	}

	private static KeyValuePair<Integer, Integer> getHumanProperty(HumanPropertyManager propertyManager,
			int propertyType, int index) {
		KeyValuePair<Integer, Integer> property = new KeyValuePair<Integer, Integer>();
		property.setKey(PropertyType.genPropertyKey(index, propertyType));
		property.setValue(propertyManager.getIntPropertySet(propertyType).getPropertyValue(index));
		return property;
	}

}
