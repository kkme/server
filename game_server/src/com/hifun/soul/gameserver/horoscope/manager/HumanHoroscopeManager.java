package com.hifun.soul.gameserver.horoscope.manager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.HoroscopeLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanHoroscopeEntity;
import com.hifun.soul.gamedb.entity.HumanStargazerEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.HoroscopeEvent;
import com.hifun.soul.gameserver.horoscope.HoroscopeBagType;
import com.hifun.soul.gameserver.horoscope.HoroscopeSoulType;
import com.hifun.soul.gameserver.horoscope.StargazerInfo;
import com.hifun.soul.gameserver.horoscope.StargazerType;
import com.hifun.soul.gameserver.horoscope.converter.HoroscopeInfoToEntityConverter;
import com.hifun.soul.gameserver.horoscope.converter.StargazerInfoToEntityConverter;
import com.hifun.soul.gameserver.horoscope.msg.GCEquipHoroscopeInfos;
import com.hifun.soul.gameserver.horoscope.msg.GCHoroscopeGamble;
import com.hifun.soul.gameserver.horoscope.msg.GCHoroscopeUpdateAndRemove;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.horoscope.service.HoroscopeTemplateManager;
import com.hifun.soul.gameserver.horoscope.sorter.HoroscopeInfoSorter;
import com.hifun.soul.gameserver.horoscope.template.HoroscopeGambleTemplate;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.IHumanPropertiesLoadForBattle;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;
import com.hifun.soul.proto.data.entity.Entity.HumanHoroscope;
import com.hifun.soul.proto.data.entity.Entity.HumanStargazer;

/**
 * 星运管理器
 * @author magicstone
 * TODO:星运的内存组织结构一开始想做的简单点，没想到有需求变更。代码冗余很多，没事的时候在重构吧
 */
public class HumanHoroscopeManager implements ICachableComponent,IHumanPersistenceManager,IHumanPropertiesLoadForBattle, ILoginManager{
	private Logger logger = Loggers.HOROSCOPE_LOGGER;
	private Human human;
	/** 当前占星师的状态 */
	private Map<StargazerType,Boolean> stargazerIdMap = new HashMap<StargazerType,Boolean>();
	/** 星运的主背包 */
	private HoroscopeInfo[] mainBagHoroscopeInfos = new HoroscopeInfo[SharedConstants.HOROSCOPE_MAINBAG_SIZE];
	/** 星运的装备背包 */
	private Map<HoroscopeSoulType,HoroscopeInfo> equipHoroscopeInfos = new HashMap<HoroscopeSoulType,HoroscopeInfo>();
	/** 星运的仓库 */
	private HoroscopeInfo[] storageBagHoroscopeInfos = new HoroscopeInfo[SharedConstants.HOROSCOPE_STORAGEBAG_SIZE];
	private HoroscopeInfoToEntityConverter horoscopeConverter;
	private StargazerInfoToEntityConverter stargazerConverter;
	private CacheEntry<StargazerType, StargazerInfo> stargazerCaches = new CacheEntry<StargazerType, StargazerInfo>();
	private CacheEntry<String, HoroscopeInfo> horoscopeCaches = new CacheEntry<String, HoroscopeInfo>();
	private HoroscopeInfoSorter sorter = new HoroscopeInfoSorter();
	
	public HumanHoroscopeManager(Human human) {
		this.human = human;
		horoscopeConverter = new HoroscopeInfoToEntityConverter(human);
		stargazerConverter = new StargazerInfoToEntityConverter(human);
		
		this.human.registerCachableManager(this);
		this.human.registerPersistenceManager(this);
		this.human.registerLoginManager(this);
	}
	
	public Human getHuman() {
		return human;
	}
	
	public int getHoroscopeGambleTime() {
		return human.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(HumanIntProperty.HOROSCOPE_GAMBLE_TIMES);
	}
	
	public void setHoroscopeGambleTime(int times) {
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.HOROSCOPE_GAMBLE_TIMES, times);
	}
	
	public int getHoroscopeStorageSize() {
		int openSize = human.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(HumanIntProperty.HOROSCOPE_STORAGE_SIZE);
		if(openSize < SharedConstants.HOROSCOPE_STORAGE_INIT_SIZE){
			openSize = SharedConstants.HOROSCOPE_STORAGE_INIT_SIZE;
			setHoroscopeStorageSize(SharedConstants.HOROSCOPE_STORAGE_INIT_SIZE);
		}
		return openSize;
	}
	
	public void setHoroscopeStorageSize(int size) {
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.HOROSCOPE_STORAGE_SIZE, size);
	}
	
	public void setLastResetGambleTime(long lastTime) {
		human.getHumanPropertiesManager().setLongPropertyValue(
				HumanLongProperty.LAST_GAMBLE_RESET_TIME, lastTime);
	}

	public long getLastResetGambleTime() {
		return human.getHumanPropertiesManager().getLongPropertyValue(
				HumanLongProperty.LAST_GAMBLE_RESET_TIME);
	}
	
	/**
	 * 判断占星师是否是开启状态
	 * 
	 * @param stargazerId
	 * @return
	 */
	public boolean stargazerIsOpen(int stargazerId) {
		StargazerType stargazerType = StargazerType.indexOf(stargazerId);
		if(stargazerType == null){
			return false;
		}
		if(stargazerId == StargazerType.STARGAZER_ONE.getIndex()){
			return true;
		}
		Boolean isOpen = stargazerIdMap.get(stargazerType);
		if(isOpen == null){
			return false;
		}
		return isOpen.booleanValue();
	}
	
	/**
	 * 开启下一级占星师
	 * @param stargazerType
	 */
	public int openNextStargazer(StargazerType stargazerType) {
		if(stargazerType == StargazerType.STARGAZER_ONE){
			stargazerIdMap.put(StargazerType.STARGAZER_TWO, true);
			StargazerInfo stargazerInfo = new StargazerInfo();
			stargazerInfo.setStargazerId(StargazerType.STARGAZER_TWO.getIndex());
			stargazerInfo.setOpen(true);
			stargazerCaches.addUpdate(StargazerType.STARGAZER_TWO, stargazerInfo);
			return StargazerType.STARGAZER_TWO.getIndex();
		}
		else if(stargazerType == StargazerType.STARGAZER_FIVE){
			stargazerIdMap.put(StargazerType.STARGAZER_FIVE, false);
			StargazerInfo stargazerInfo = new StargazerInfo();
			stargazerInfo.setStargazerId(StargazerType.STARGAZER_FIVE.getIndex());
			stargazerInfo.setOpen(false);
			stargazerCaches.addUpdate(StargazerType.STARGAZER_FIVE, stargazerInfo);
			// 最高等级没有下一级
			return 0;
		}
		else{
			stargazerIdMap.put(stargazerType, false);
			stargazerIdMap.put(StargazerType.indexOf(stargazerType.getIndex()+1), true);
			StargazerInfo stargazerInfo = new StargazerInfo();
			stargazerInfo.setStargazerId(stargazerType.getIndex());
			stargazerInfo.setOpen(false);
			stargazerCaches.addUpdate(stargazerType, stargazerInfo);
			StargazerInfo nextStargazerInfo = new StargazerInfo();
			nextStargazerInfo.setStargazerId(stargazerType.getIndex()+1);
			nextStargazerInfo.setOpen(true);
			stargazerCaches.addUpdate(StargazerType.indexOf(stargazerType.getIndex()+1), stargazerInfo);
			return stargazerType.getIndex()+1;
		}
	}
	
	/**
	 * 关闭当前占星
	 * @param stargazerType
	 */
	public void closeStargazer(StargazerType stargazerType) {
		if(stargazerType != StargazerType.STARGAZER_ONE){
			stargazerIdMap.put(stargazerType, false);
			StargazerInfo stargazerInfo = new StargazerInfo();
			stargazerInfo.setStargazerId(stargazerType.getIndex());
			stargazerInfo.setOpen(false);
			stargazerCaches.addUpdate(stargazerType, stargazerInfo);
		}
	}
	
	/**
	 * 获取当前占星师最高的等级
	 * @return
	 */
	public StargazerType getLastStargazer() {
		StargazerType[] stargazerTypes = StargazerType.values();
		Arrays.sort(stargazerTypes);
		for(int i=stargazerTypes.length-1; i>=StargazerType.STARGAZER_ONE.getIndex(); i--){
			if(stargazerIdMap.get(stargazerTypes[i]) != null
					&& stargazerIdMap.get(stargazerTypes[i]).booleanValue() == true){
				return stargazerTypes[i];
			}
		}
		return StargazerType.STARGAZER_ONE;
	}
	
	/**
	 * 判断是否有空的星运装备位
	 * @param bagType
	 * @return
	 */
	public boolean hasEmptyGrid(HoroscopeBagType bagType) {
		if(bagType == null){
			return false;
		}
		switch(bagType){
		case MAIN_BAG:
			return hasEmptyGridMainBag();
		case EQUIP_BAG:
			return hasEmptyGridEquipBag();
		case STORAGE_BAG:
			return hasEmptyGridStorageBag();
		}
		return false;
	}
	
	/**
	 * 判断是否已经装备同样效果的星运
	 * @param key
	 * @return
	 */
	public boolean hasSameHoroscope(int key) {
		for(HoroscopeInfo horoscopeInfo : equipHoroscopeInfos.values()){
			if(horoscopeInfo != null
					&& horoscopeInfo.getKey() == key){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断星运的主背包是否有空格子
	 * @return
	 */
	private boolean hasEmptyGridMainBag() {
		for(int i=0; i<mainBagHoroscopeInfos.length; i++){
			if(mainBagHoroscopeInfos[i] == null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断星运的仓库是否有空格子
	 * @return
	 */
	public boolean hasEmptyGridStorageBag() {
		for(int i=0; i<getHoroscopeStorageSize(); i++){
			if(storageBagHoroscopeInfos[i] == null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断星运装备栏是否有位置
	 * @return
	 */
	private boolean hasEmptyGridEquipBag() {
		HoroscopeSoulType[] horoscopeSoulTypes = GameServerAssist.getHoroscopeTemplateManager().getOpenHoroscopeTypes(human.getLevel());
		if(horoscopeSoulTypes == null){
			return false;
		}
		Arrays.sort(horoscopeSoulTypes);
		for(HoroscopeSoulType horoscopeSoulType : horoscopeSoulTypes) {
			if(getHoroscopeInfoFromEquipBag(horoscopeSoulType) == null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取星运信息
	 * @param bagType
	 * @param bagIndex
	 * @return
	 */
	public HoroscopeInfo getHoroscopeInfo(HoroscopeBagType bagType, int bagIndex) {
		if(bagType == null){
			return null;
		}
		switch(bagType){
		case MAIN_BAG:
			return getHoroscopeInfoFromMainBag(bagIndex);
		case EQUIP_BAG:
			return getHoroscopeInfoFromEquipBag(HoroscopeSoulType.indexOf(bagIndex));
		case STORAGE_BAG:
			return getHoroscopeInfoFromStorageBag(bagIndex);
		}
		return null;
	}
	
	/**
	 * 获取星运主背包中固定位置的星运
	 * @param index
	 * @return
	 */
	private HoroscopeInfo getHoroscopeInfoFromMainBag(int index) {
		if(index < 0
				|| index >= mainBagHoroscopeInfos.length){
			return null;
		}
		return mainBagHoroscopeInfos[index];
	}
	
	/**
	 * 获取星运仓库中固定位置的星运
	 * @param index
	 * @return
	 */
	private HoroscopeInfo getHoroscopeInfoFromStorageBag(int index) {
		if(index < 0
				|| index >= getHoroscopeStorageSize()){
			return null;
		}
		return storageBagHoroscopeInfos[index];
	}
	
	/**
	 * 获取星运装备位上星运
	 * @param horoscopeSoulType
	 * @return
	 */
	private HoroscopeInfo getHoroscopeInfoFromEquipBag(HoroscopeSoulType horoscopeSoulType) {
		return equipHoroscopeInfos.get(horoscopeSoulType);
	}
	
	/**
	 * 添加星运
	 * @param bagType
	 * @param horoscopeInfo
	 * @param reason
	 * @param param
	 * @return
	 */
	public HoroscopeInfo addHoroscopeInfo(HoroscopeBagType bagType, HoroscopeInfo horoscopeInfo, HoroscopeLogReason reason, String param) {
		if(bagType == null){
			return null;
		}
		switch(bagType){
		case MAIN_BAG:
			return addHoroscopeInfoToMainBag(horoscopeInfo,reason,param);
		case EQUIP_BAG:
			return addHoroscopeInfoToEquipBag(horoscopeInfo, reason, param);
		case STORAGE_BAG:
			return addHoroscopeInfoToStorageBag(horoscopeInfo, reason, param);
		}
		return null;
	}
	
	/**
	 * 添加星运到主背包
	 * @param horoscopeInfo
	 */
	private  HoroscopeInfo addHoroscopeInfoToMainBag(HoroscopeInfo horoscopeInfo, HoroscopeLogReason reason, String param) {
		if(horoscopeInfo == null){
			return null;
		}
		if(!hasEmptyGridMainBag()){
			logger.error("horoscopeMainBag has not empty Grid! humanGuid:" + human.getHumanGuid() + "; horoscopeId:" + horoscopeInfo.getHoroscopeId());
			return null;
		}
		for(int i=0; i<mainBagHoroscopeInfos.length; i++){
			if(mainBagHoroscopeInfos[i] == null){
				horoscopeInfo.setHoroscopeBag(HoroscopeBagType.MAIN_BAG.getIndex());
				horoscopeInfo.setIndex(i);
				mainBagHoroscopeInfos[i] = horoscopeInfo;
				// 同步缓存
				horoscopeCaches.addUpdate(mainBagHoroscopeInfos[i].getUuid(), mainBagHoroscopeInfos[i]);
				// 发送星运日志
				sendHoroscopeLogMsg(reason,param,mainBagHoroscopeInfos[i]);
				return mainBagHoroscopeInfos[i];
			}
		}
		return null;
	}
	
	/**
	 * 添加星运到仓库
	 * @param horoscopeInfo
	 */
	private  HoroscopeInfo addHoroscopeInfoToStorageBag(HoroscopeInfo horoscopeInfo, HoroscopeLogReason reason, String param) {
		if(horoscopeInfo == null){
			return null;
		}
		if(!hasEmptyGridStorageBag()){
			logger.error("horoscopeStorageBag has not empty Grid! humanGuid:" + human.getHumanGuid() + "; horoscopeId:" + horoscopeInfo.getHoroscopeId());
			return null;
		}
		for(int i=0; i<getHoroscopeStorageSize(); i++){
			if(storageBagHoroscopeInfos[i] == null){
				horoscopeInfo.setHoroscopeBag(HoroscopeBagType.STORAGE_BAG.getIndex());
				horoscopeInfo.setIndex(i);
				storageBagHoroscopeInfos[i] = horoscopeInfo;
				// 同步缓存
				horoscopeCaches.addUpdate(storageBagHoroscopeInfos[i].getUuid(), storageBagHoroscopeInfos[i]);
				// 发送星运日志
				sendHoroscopeLogMsg(reason,param,storageBagHoroscopeInfos[i]);
				
				return storageBagHoroscopeInfos[i];
			}
		}
		return null;
	}
	
	/**
	 * 更新某星运装备位上的星运
	 * @param horoscopeSoulType
	 * @param horoscopeInfo
	 */
	private HoroscopeInfo addHoroscopeInfoToEquipBag(HoroscopeInfo horoscopeInfo, HoroscopeLogReason reason, String param) {
		if(horoscopeInfo == null){
			return null;
		}
		if(!hasEmptyGridEquipBag()){
			logger.error("horoscopeEquipBag has not empty Grid! humanGuid:" + human.getHumanGuid() + "; horoscopeId:" + horoscopeInfo.getHoroscopeId());
			return null;
		}
		HoroscopeSoulType[] horoscopeSoulTypes = GameServerAssist.getHoroscopeTemplateManager().getOpenHoroscopeTypes(human.getLevel());
		if(horoscopeSoulTypes == null){
			return null;
		}
		Arrays.sort(horoscopeSoulTypes);
		HoroscopeInfo newHoroscopeInfo = null;
		for(HoroscopeSoulType horoscopeSoulType : horoscopeSoulTypes) {
			if(getHoroscopeInfoFromEquipBag(horoscopeSoulType) == null){
				horoscopeInfo.setHoroscopeBag(HoroscopeBagType.EQUIP_BAG.getIndex());
				horoscopeInfo.setIndex(horoscopeSoulType.getIndex());
				newHoroscopeInfo = horoscopeInfo;
				equipHoroscopeInfos.put(horoscopeSoulType, newHoroscopeInfo);
				// 同步缓存
				horoscopeCaches.addUpdate(newHoroscopeInfo.getUuid(), newHoroscopeInfo);
				// 更新角色属性
				human.getHumanPropertiesManager().amendProperty(human, getAmendMethod(newHoroscopeInfo.getPropertyAddType()), newHoroscopeInfo.getKey(), (int)newHoroscopeInfo.getValue(), PropertyLogReason.HOROSCOPE_ON, "");
				// 发送星运日志
				sendHoroscopeLogMsg(reason,param,newHoroscopeInfo);
				return newHoroscopeInfo;
			}
		}
		return null;
	}
	
	/**
	 * 删除星运
	 * @param bagType
	 * @param index
	 * @param reason
	 * @param param
	 */
	public void removeHoroscopeInfo(HoroscopeBagType bagType, int index, HoroscopeLogReason reason, String param) {
		if(bagType == null){
			return;
		}
		switch(bagType){
		case MAIN_BAG:
			removeHoroscopeInfoFromMainBag(index,reason,param);
			break;
		case EQUIP_BAG:
			removeHoroscopeInfoFromEquipBag(HoroscopeSoulType.indexOf(index), reason, param);
			break;
		case STORAGE_BAG:
			removeHoroscopeInfoFromStorageBag(index,reason,param);
			break;
		}
		return;
	}
	
	/**
	 * 删除主背包index指向的星运
	 * @param index
	 */
	private void removeHoroscopeInfoFromMainBag(int index, HoroscopeLogReason reason, String param) {
		if(index < 0
				|| index >= mainBagHoroscopeInfos.length){
			return;
		}
		// 发送星运日志
		sendHoroscopeLogMsg(reason,param,mainBagHoroscopeInfos[index]);
		mainBagHoroscopeInfos[index] = null;
	}
	
	/**
	 * 删除主背包index指向的星运
	 * @param index
	 */
	private void removeHoroscopeInfoFromStorageBag(int index, HoroscopeLogReason reason, String param) {
		if(index < 0
				|| index >= getHoroscopeStorageSize()){
			return;
		}
		// 发送星运日志
		sendHoroscopeLogMsg(reason,param,storageBagHoroscopeInfos[index]);
		storageBagHoroscopeInfos[index] = null;
	}
	
	/**
	 * 清除某星运装备位
	 * @param horoscopeSoulType
	 */
	 private void removeHoroscopeInfoFromEquipBag(HoroscopeSoulType horoscopeSoulType, HoroscopeLogReason reason, String param) {
		HoroscopeInfo horoscopeInfo = equipHoroscopeInfos.get(horoscopeSoulType);
		// 更新角色属性
		human.getHumanPropertiesManager().amendProperty(human, getAmendMethod(horoscopeInfo.getPropertyAddType()), horoscopeInfo.getKey(), (int)(-horoscopeInfo.getValue()), PropertyLogReason.HOROSCOPE_OFF, "");
		equipHoroscopeInfos.put(horoscopeSoulType, null);
		// 发送星运日志
		sendHoroscopeLogMsg(reason,param,horoscopeInfo);
	 }
	 
	 /**
	  * 只有星运合并的时候才会用的到，星运在几个背包挪动不涉及到删除
	  * @param horoscopeInfo
	  */
	 public void updateHoroscopeCache(HoroscopeInfo horoscopeInfo) {
		// 更新缓存
		horoscopeCaches.addDelete(horoscopeInfo.getUuid(), horoscopeInfo);
	 }
	
	 /**
	  * 更新星运
	  * @param bagType
	  * @param index
	  * @param horoscopeInfo
	  * @param reason
	  * @param param
	  * @return
	  */
	 public HoroscopeInfo updateHoroscopeInfo(HoroscopeBagType bagType, int index, HoroscopeInfo horoscopeInfo,HoroscopeLogReason reason,String param) {
		 if(bagType == null){
			 return null;
		 }
		 switch(bagType){
		 case MAIN_BAG:
			 return updateHoroscopeInfoToMainBag(index,horoscopeInfo,reason,param);
		 case EQUIP_BAG:
			 return updateHoroscopeInfoToEquipBag(HoroscopeSoulType.indexOf(index),horoscopeInfo,reason,param);
		 case STORAGE_BAG:
			 return updateHoroscopeInfoToStorageBag(index,horoscopeInfo,reason,param);
		 }
		 return null;
	 }
	 
	/**
	 * 更新星运主背包数据
	 * @param index
	 * @param horoscopeInfo
	 */
	private HoroscopeInfo updateHoroscopeInfoToMainBag(int index, HoroscopeInfo horoscopeInfo,HoroscopeLogReason reason,String param) {
		if(index < 0
				|| index >= mainBagHoroscopeInfos.length){
			return null;
		}		
		if(horoscopeInfo != null){
			horoscopeInfo.setHoroscopeBag(HoroscopeBagType.MAIN_BAG.getIndex());
			horoscopeInfo.setIndex(index);
		}		
		mainBagHoroscopeInfos[index] = horoscopeInfo;
		// 同步缓存
		horoscopeCaches.addUpdate(mainBagHoroscopeInfos[index].getUuid(), mainBagHoroscopeInfos[index]);		
		// 发送星运日志
		sendHoroscopeLogMsg(reason, param, horoscopeInfo);
		return mainBagHoroscopeInfos[index];
	}
	
	/**
	 * 更新星运仓库数据
	 * @param index
	 * @param horoscopeInfo
	 */
	private HoroscopeInfo updateHoroscopeInfoToStorageBag(int index, HoroscopeInfo horoscopeInfo,HoroscopeLogReason reason,String param) {
		// 这种情况下认为是拾取请求, 查询第一个存储的空位
		if (index < 0) {
			HoroscopeInfo pickUpOne = addHoroscopeInfoToStorageBag(horoscopeInfo, HoroscopeLogReason.PICK_UP, "");
			if (pickUpOne == null) {
				return null;
			}
			return pickUpOne;
		}
		if(index >= getHoroscopeStorageSize()){
			return null;
		}		
		if(horoscopeInfo != null){
			horoscopeInfo.setHoroscopeBag(HoroscopeBagType.STORAGE_BAG.getIndex());
			horoscopeInfo.setIndex(index);
		}		
		storageBagHoroscopeInfos[index] = horoscopeInfo;
		// 同步缓存
		horoscopeCaches.addUpdate(storageBagHoroscopeInfos[index].getUuid(), storageBagHoroscopeInfos[index]);		
		// 发送星运日志
		sendHoroscopeLogMsg(reason,param,horoscopeInfo);		
		return storageBagHoroscopeInfos[index];
	}
	
	/**
	 * 更新星运装备背包数据
	 * @param index
	 * @param horoscopeInfo
	 */
	private HoroscopeInfo updateHoroscopeInfoToEquipBag(HoroscopeSoulType index, HoroscopeInfo horoscopeInfo,HoroscopeLogReason reason,String param) {
		if(index == null){
			return null;
		}		
		HoroscopeInfo oldHoroscopeInfo = getHoroscopeInfoFromEquipBag(index);
		if(oldHoroscopeInfo != null){
			human.getHumanPropertiesManager().amendProperty(
					human, getAmendMethod(oldHoroscopeInfo.getPropertyAddType()), oldHoroscopeInfo.getKey(), (int)(-oldHoroscopeInfo.getValue()), PropertyLogReason.HOROSCOPE_ON, "");
		}		
		if(horoscopeInfo != null){
			horoscopeInfo.setHoroscopeBag(HoroscopeBagType.EQUIP_BAG.getIndex());
			horoscopeInfo.setIndex(index.getIndex());
		}		
		equipHoroscopeInfos.put(index, horoscopeInfo);		
		// 同步缓存
		horoscopeCaches.addUpdate(horoscopeInfo.getUuid(), horoscopeInfo);		
		// 更新角色属性
		human.getHumanPropertiesManager().amendProperty(
				human, getAmendMethod(horoscopeInfo.getPropertyAddType()), horoscopeInfo.getKey(), (int)horoscopeInfo.getValue(), PropertyLogReason.HOROSCOPE_ON, "");		
		// 发送星运日志
		sendHoroscopeLogMsg(reason,param,horoscopeInfo);
		return horoscopeInfo;
	}
	
	/**
	 * 从主背包中拿取两个星运
	 * @return
	 */
	public HoroscopeInfo[] getTwoHoroscopeInfosFromMainBag() {
		HoroscopeInfo[] tempHoroscopeInfos = new HoroscopeInfo[2];
		int i=0;
		for(int j=0; j<this.mainBagHoroscopeInfos.length&&i<2; j++){
			if(mainBagHoroscopeInfos[j] != null && mainBagHoroscopeInfos[j].getNextHoroscopeId() > 0){
				tempHoroscopeInfos[i] = mainBagHoroscopeInfos[j];
				i++;
			}
		}
		if(i <= 1){
			return null;
		}
		return tempHoroscopeInfos;
	}
	
	
	/**
	 * 将两个星运按规则排序
	 * @param horoscopeManager
	 * @param indexA
	 * @param indexB
	 * @return
	 */
	public HoroscopeInfo[] sortHoroscopeInfo(HoroscopeInfo horoscopeInfoA, HoroscopeInfo horoscopeInfoB, boolean needSort) {
		if(horoscopeInfoA == null
				|| horoscopeInfoB == null){
			return null;
		}
		HoroscopeInfo[] horoscopeInfos = new HoroscopeInfo[2];
		horoscopeInfos[0] = horoscopeInfoA;
		horoscopeInfos[1] = horoscopeInfoB;
		if(needSort || horoscopeInfoA.getColor() != horoscopeInfoB.getColor()){
			Arrays.sort(horoscopeInfos,sorter);
		}
		return horoscopeInfos;
	}
	
	/**
	 * 按占星师顺序返回开启状态
	 * @return
	 */
	public StargazerInfo[] getStargazers(HoroscopeTemplateManager templateManager) {
		StargazerType[] stargazerTypes = StargazerType.values();
		Arrays.sort(stargazerTypes);
		StargazerInfo[] stargazers = new StargazerInfo[stargazerTypes.length];
		for(int i=0; i<stargazerTypes.length; i++){
			int stargazerId = stargazerTypes[i].getIndex();
			HoroscopeGambleTemplate template = templateManager.getHoroscopeGambleTemplate(stargazerId);
			StargazerInfo stargazerInfo = new StargazerInfo();
			stargazerInfo.setCostCurrencyNum(template.getCostCurrencyNum());
			stargazerInfo.setCostCurrencyType(template.getCostCurrencyType());
			stargazerInfo.setDesc(template.getDesc());
			stargazerInfo.setIcon(template.getIcon());
			stargazerInfo.setName(template.getName());
			stargazerInfo.setOpen(stargazerIsOpen(stargazerId));
			stargazerInfo.setStargazerId(stargazerId);
			stargazers[i] = stargazerInfo;
		}
		return stargazers;
	}
	
	/**
	 * 获取星运主背包中星运
	 * @return
	 */
	public HoroscopeInfo[] getMainBagHoroscopeInfos() {
		List<HoroscopeInfo> horoscopeInfoList = new ArrayList<HoroscopeInfo>();
		for(int i=0; i<mainBagHoroscopeInfos.length; i++){
			if(mainBagHoroscopeInfos[i] != null){
				horoscopeInfoList.add(mainBagHoroscopeInfos[i]);
			}
		}
		return horoscopeInfoList.toArray(new HoroscopeInfo[horoscopeInfoList.size()]);
	}
	
	/**
	 * 获取星运仓库中星运
	 * @return
	 */
	public HoroscopeInfo[] getStorageBagHoroscopeInfos() {
		List<HoroscopeInfo> horoscopeInfoList = new ArrayList<HoroscopeInfo>();
		for(int i=0; i<getHoroscopeStorageSize(); i++){
			if(storageBagHoroscopeInfos[i] != null){
				horoscopeInfoList.add(storageBagHoroscopeInfos[i]);
			}
		}
		return horoscopeInfoList.toArray(new HoroscopeInfo[horoscopeInfoList.size()]);
	}
	
	/**
	 * 获取星运装备背包中星运
	 * @return
	 */
	public HoroscopeInfo[] getEquipBagHoroscopeInfos() {
		List<HoroscopeInfo> horoscopeInfoList = new ArrayList<HoroscopeInfo>();
		HoroscopeInfo[] horoscopeInfos= equipHoroscopeInfos.values().toArray(new HoroscopeInfo[0]);
		for(int i=0; i<horoscopeInfos.length; i++){
			if(horoscopeInfos[i] != null){
				horoscopeInfoList.add(horoscopeInfos[i]);
			}
		}
		return horoscopeInfoList.toArray(new HoroscopeInfo[horoscopeInfoList.size()]);
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		for(HumanHoroscope.Builder builder : humanEntity.getBuilder().getHoroscopeBuilderList()){
			HoroscopeInfo horoscopeInfo = GameServerAssist.getHoroscopeTemplateManager().genHoroscopeInfo(
					builder.getHoroscope().getHoroscopeId(), HoroscopeBagType.indexOf(builder.getHoroscope().getBagType()));
			horoscopeInfo.setUuid(builder.getHoroscope().getHoroscopeKey());
			horoscopeInfo.setIndex(builder.getHoroscope().getBagIndex());
			horoscopeInfo.setExperience(builder.getHoroscope().getExperience());
			if(builder.getHoroscope().getBagType() == HoroscopeBagType.MAIN_BAG.getIndex()){
				mainBagHoroscopeInfos[builder.getHoroscope().getBagIndex()] = horoscopeInfo;
			}
			else if(builder.getHoroscope().getBagType() == HoroscopeBagType.EQUIP_BAG.getIndex()){
				equipHoroscopeInfos.put(HoroscopeSoulType.indexOf(builder.getHoroscope().getBagIndex()), horoscopeInfo);
				// 登录时属性已经装备的星运效果要加到角色身上
				human.getPropertyManager().amendProperty(human, getAmendMethod(horoscopeInfo.getPropertyAddType()), horoscopeInfo.getKey(), (int)horoscopeInfo.getValue(), null, "");
			}
			else if(builder.getHoroscope().getBagType() == HoroscopeBagType.STORAGE_BAG.getIndex()){
				storageBagHoroscopeInfos[builder.getHoroscope().getBagIndex()] = horoscopeInfo;
			}
		}
		for(HumanStargazer.Builder builder : humanEntity.getBuilder().getStargazerBuilderList()){
			stargazerIdMap.put(StargazerType.indexOf(builder.getStargazer().getStargazerId()), 
					builder.getStargazer().getOpen());
		}
		
	}
	
	@Override
	public void onBattlePropertiesLoad(HumanEntity humanEntity, HumanPropertyManager propertyManager) {
		for(HumanHoroscope.Builder builder : humanEntity.getBuilder().getHoroscopeBuilderList()){
			HoroscopeInfo horoscopeInfo = GameServerAssist.getHoroscopeTemplateManager().genHoroscopeInfo(
					builder.getHoroscope().getHoroscopeId(), HoroscopeBagType.indexOf(builder.getHoroscope().getBagType()));
			if(builder.getHoroscope().getBagType() == HoroscopeBagType.EQUIP_BAG.getIndex()){
				// 登录时属性已经装备的星运效果要加到角色身上
				propertyManager.amendProperty(human, getAmendMethod(horoscopeInfo.getPropertyAddType()), horoscopeInfo.getKey(), (int)horoscopeInfo.getValue(), null, "");
			}
		}
	}
	
	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for(StargazerType stargazerType : stargazerIdMap.keySet()){
			StargazerInfo stargazerInfo = new StargazerInfo();
			stargazerInfo.setStargazerId(stargazerType.getIndex());
			stargazerInfo.setOpen(stargazerIsOpen(stargazerType.getIndex()));
			humanEntity.getBuilder().addStargazer(this.stargazerConverter.convert(stargazerInfo).getBuilder());
		}
		for(HoroscopeInfo horoscopeInfo : mainBagHoroscopeInfos){
			if(horoscopeInfo == null){
				continue;
			}
			humanEntity.getBuilder().addHoroscope(this.horoscopeConverter.convert(horoscopeInfo).getBuilder());
		}
		for(HoroscopeInfo horoscopeInfo : storageBagHoroscopeInfos){
			if(horoscopeInfo == null){
				continue;
			}
			humanEntity.getBuilder().addHoroscope(this.horoscopeConverter.convert(horoscopeInfo).getBuilder());
		}
		for(HoroscopeInfo horoscopeInfo : equipHoroscopeInfos.values()){
			if(horoscopeInfo == null){
				continue;
			}
			humanEntity.getBuilder().addHoroscope(this.horoscopeConverter.convert(horoscopeInfo).getBuilder());
		}
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		for(HoroscopeInfo horoscopeInfo : horoscopeCaches.getAllUpdateData()){
			HumanHoroscopeEntity entity = this.horoscopeConverter.convert(horoscopeInfo);
			updateList.add(entity);
		}
		for(StargazerInfo stargazerInfo : stargazerCaches.getAllUpdateData()){
			HumanStargazerEntity entity = this.stargazerConverter.convert(stargazerInfo);
			updateList.add(entity);
		}
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> deleteList = new ArrayList<IEntity>();
		for(HoroscopeInfo horoscopeInfo : horoscopeCaches.getAllDeleteData()){
			HumanHoroscopeEntity entity = this.horoscopeConverter.convert(horoscopeInfo);
			deleteList.add(entity);
		}
		for(StargazerInfo stargazerInfo : stargazerCaches.getAllDeleteData()){
			HumanStargazerEntity entity = this.stargazerConverter.convert(stargazerInfo);
			deleteList.add(entity);
		}
		return deleteList;
	}
	
	/**
	 * 发送星运日志
	 * @param reason
	 * @param param
	 * @param horoscopeInfo
	 */
	private void sendHoroscopeLogMsg(HoroscopeLogReason reason, String param, HoroscopeInfo horoscopeInfo) {
		if(horoscopeInfo != null && reason!=null){
			GameServerAssist.getLogService().sendHoroscopeLog(human, reason, param, 
					horoscopeInfo.getHoroscopeBag(), horoscopeInfo.getIndex(), horoscopeInfo.getHoroscopeId(), horoscopeInfo.getExperience());
		}
	}
	
	/**
	 * 占星
	 * @param templateManager
	 * @param stargazerId
	 * @param needNotify
	 * @return
	 */
	public boolean horoscopeGamble(HoroscopeTemplateManager templateManager, int stargazerId, boolean needNotify) {
		// 校验占星师类型是否合法
		if(!stargazerIsOpen(stargazerId)){
			human.sendErrorMessage(LangConstants.STARGAZER_OPEN);
			return false;
		}
		// 查找占星师对应的星运模版
		HoroscopeGambleTemplate template = templateManager.getHoroscopeGambleTemplate(stargazerId);
		if(template == null){
			logger.error("HoroscopeGambleTemplate  can not find! humanGuid=" + human.getHumanGuid() + "; stargazerId=" + stargazerId);
			return false;
		}
		// 判断玩家星运主背包是否有空位
		if(!hasEmptyGridMainBag()){
			human.sendWarningMessage(LangConstants.HOROSCOPE_MAINBAG_FULL);
			return false;
		}
		boolean isHasGamebleTime = false;
		if(getHoroscopeGambleTime() > 0){
			setHoroscopeGambleTime(getHoroscopeGambleTime() - 1);
			isHasGamebleTime = true;
		}
		CurrencyType currencyType = CurrencyType.indexOf(template.getCostCurrencyType());
		int currencyNum = template.getCostCurrencyNum();
		if(!isHasGamebleTime){
			// 判断玩家是否有足够的货币
			if(!human.getWallet().isEnough(currencyType, currencyNum)){
				human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, currencyType.getDesc());
				return false;
			}
		}
		// 花费货币并根据概率获取物品
		if(isHasGamebleTime || human.getWallet().costMoney(currencyType, currencyNum, MoneyLogReason.HOROSCOPE, "")){
			// 关闭当前占星师
			closeStargazer(StargazerType.indexOf(stargazerId));
			// 随机概率
			int rate = MathUtils.random(0, (int)SharedConstants.DEFAULT_FLOAT_BASE);
			// 判断是否失败
			if(rate < template.getFailRate()){
				HoroscopeInfo horoscopeInfo = templateManager.genHoroscopeInfo(template.getFailHoroscopeId(),HoroscopeBagType.MAIN_BAG);
				this.addHoroscopeInfo(HoroscopeBagType.MAIN_BAG, horoscopeInfo, HoroscopeLogReason.GAMBLE, "");
				human.sendGenericMessage(LangConstants.HOROSCOPE_GAMBLE_FAIL, horoscopeInfo.getName());
				if(needNotify){
					GCHoroscopeGamble gcMsg = new GCHoroscopeGamble();
					gcMsg.setStargazerInfos(getStargazers(templateManager));
					gcMsg.setHoroscopeInfo(horoscopeInfo);
					gcMsg.setFreeTimes(getHoroscopeGambleTime());
					human.sendMessage(gcMsg);
				}
				return true;
			}
			// 判断应该给予的物品
			int horoscopeId = templateManager.randHoroscopeId(template);
			if(horoscopeId == 0){
				logger.error("Can not find horoscopeId! humanGuid=" + human.getHumanGuid() + "; stargazerId:" + template.getId() + "; rate=" + rate);
				return false;
			}
			// 给玩家添加星运到玩家的星运包
			HoroscopeInfo horoscopeInfo = templateManager.genHoroscopeInfo(horoscopeId,HoroscopeBagType.MAIN_BAG);
			if(horoscopeInfo == null){
				logger.error("can not find horoscopeTemplate! humanGuid=" + human.getHumanGuid() + "; horoscopeId=" + horoscopeId);
				return false;
			}
			// 添加星运到背包
			horoscopeInfo = addHoroscopeInfoToMainBag(horoscopeInfo,HoroscopeLogReason.GAMBLE,"");
			// 获得高级星运进行公告
			bulletinGetGoodHoroscope(horoscopeInfo);			
			// 下一个占星师出现概率(下一级占星师的id是当前默认加1)
			int nextRate = MathUtils.random(0, (int)SharedConstants.DEFAULT_FLOAT_BASE);
			if(nextRate < template.getNextRate()){
				int newOpenStargazerId = openNextStargazer(StargazerType.indexOf(stargazerId));
				HoroscopeGambleTemplate horoscopeGambleTemplate = templateManager.getHoroscopeGambleTemplate(newOpenStargazerId);
				if(horoscopeGambleTemplate != null){
					human.sendImportantMessage(LangConstants.HOROSCOPE_GAMBLE_SUCCESS_AND_OPEN_NEXT, horoscopeInfo.getName(), horoscopeGambleTemplate.getName());
				}
			}
			else{
				human.sendImportantMessage(LangConstants.HOROSCOPE_GAMBLE_SUCCESS, horoscopeInfo.getName());
			}
			// 通知客户端
			if(needNotify
					&& horoscopeInfo != null){
				GCHoroscopeGamble gcMsg = new GCHoroscopeGamble();
				gcMsg.setStargazerInfos(getStargazers(templateManager));
				gcMsg.setHoroscopeInfo(horoscopeInfo);
				gcMsg.setFreeTimes(getHoroscopeGambleTime());
				human.sendMessage(gcMsg);
			}
			// 发送占星事件
			HoroscopeEvent horoscopeEvent = new HoroscopeEvent();
			human.getEventBus().fireEvent(horoscopeEvent);
			return true;
		}
		return false;
	}
	
	private void bulletinGetGoodHoroscope(HoroscopeInfo horoscopeInfo) {
		if(horoscopeInfo == null){
			return;
		}
		if(horoscopeInfo.getColor() >= 5){
			String horoscopeName = horoscopeInfo.getName();
			int lvIndex = horoscopeName.indexOf("lv");
			if(lvIndex!=-1){
				horoscopeName = horoscopeName.substring(0, lvIndex);
			}			
			String content = GameServerAssist.getSysLangService().read(LangConstants.GET_GOOD_HOROSCOPE, human.getName(),horoscopeName);
			GameServerAssist.getBulletinManager().sendSystemBulletin(content);
		}
	}

	/**
	 * 星运合成
	 * @param human
	 * @param indexA
	 * @param indexB
	 * @return
	 */
	public boolean horoscopeCompound(HoroscopeTemplateManager templateManager, HoroscopeInfo from, HoroscopeInfo to, boolean needNotify, boolean needSort) {
		if(from == to){
			return false;
		}
		int fromBagType = from.getHoroscopeBag();
		int fromIndex = from.getIndex();
		int toBagType = to.getHoroscopeBag();
		int toIndex = to.getIndex();
		// 分清要合成星运的主次
		HoroscopeInfo[] horoscopeInfos = sortHoroscopeInfo(from,to, needSort);
		if(horoscopeInfos == null){
			return false;
		}
		if(to.getHoroscopeBag() == HoroscopeBagType.EQUIP_BAG.getIndex()
				&& horoscopeInfos[0].getHoroscopeBag() != HoroscopeBagType.EQUIP_BAG.getIndex()
				&& horoscopeInfos[0].getKey() != horoscopeInfos[1].getKey()
				&& hasSameHoroscope(horoscopeInfos[0].getKey())){
			human.sendErrorMessage(LangConstants.HOROSCOPE_SAME);
			return false;
		}
		if(horoscopeInfos[0].getNextHoroscopeId() <= 0){
			if(needNotify){
				human.sendErrorMessage(LangConstants.HOROSCOPE_TOP, horoscopeInfos[0].getName());
				return false;
			}
			else{
				return true;
			}
		}
		// 删除原来两个星运
		updateHoroscopeCache(horoscopeInfos[0]);
		updateHoroscopeCache(horoscopeInfos[1]);
		// 合成之后的星运
		HoroscopeInfo horoscopeInfo = templateManager.getCompoundHoroscopeInfo(horoscopeInfos[0],horoscopeInfos[1]);
		// 更新合成之后的星运
		horoscopeInfo = updateHoroscopeInfo(
				HoroscopeBagType.indexOf(toBagType),
				toIndex,
				horoscopeInfo,null,"");
		sendHoroscopeCompoundLogMessage(horoscopeInfo,getHoroscopeInfo(HoroscopeBagType.indexOf(fromBagType),fromIndex));
		// 删除from的星运
		removeHoroscopeInfo(
				HoroscopeBagType.indexOf(fromBagType),
				fromIndex,null,"");
		// 判断是否需要下发通知
		if(needNotify
				&& horoscopeInfo != null){
			GCHoroscopeUpdateAndRemove gcMsg = new GCHoroscopeUpdateAndRemove();
			gcMsg.setHoroscopeInfo(horoscopeInfo);
			gcMsg.setRemoveBagType(fromBagType);
			gcMsg.setRemoveBagIndex(fromIndex);
			human.sendMessage(gcMsg);
		}
		return true;
	}

	@Override
	public void onLogin() {
		// 玩家登录下发已经装备的星运信息
		GCEquipHoroscopeInfos gcMsg = new GCEquipHoroscopeInfos();
		gcMsg.setEquipBagHoroscopeInfos(getEquipBagHoroscopeInfos());
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 星运合成日志
	 * @param remainedHoro
	 * @param subHoro
	 */
	private void sendHoroscopeCompoundLogMessage(HoroscopeInfo remainedHoro,HoroscopeInfo subHoro){
		if(remainedHoro != null && subHoro != null){
			String reasonText = HoroscopeLogReason.COMPOUND.getReasonText();
			String param = MessageFormat.format(reasonText,subHoro.getHoroscopeId(), subHoro.getLevel(),subHoro.getExperience());
			GameServerAssist.getLogService().sendHoroscopeLog(human, HoroscopeLogReason.COMPOUND, param, 
					remainedHoro.getHoroscopeBag(), remainedHoro.getIndex(), remainedHoro.getHoroscopeId(), remainedHoro.getExperience());
		}
	}
	
	/**
	 * 属性加成方式
	 * @param propertyAddType
	 * @return
	 */
	private AmendMethod getAmendMethod(int propertyAddType) {
		AmendMethod amendMethod = AmendMethod.valueOf(propertyAddType);
		if(amendMethod == null){
			return AmendMethod.ADD;
		}
		else{
			return amendMethod;
		}
	}
	
	/**
	 * 获取星运的主背包空格子数
	 * @return
	 */
	public int getEmptyGridSizeOfMainBag() {
		int result = 0;
		for(int i=0; i<mainBagHoroscopeInfos.length; i++){
			if(mainBagHoroscopeInfos[i] == null){
				result++;
			}
		}
		return result;
	}
	

}
