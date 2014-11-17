package com.hifun.soul.gameserver.item.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.EquipRefineEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.EquipForgeInfo;
import com.hifun.soul.gameserver.item.ForgeType;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.msg.GCEquipForgeCancel;
import com.hifun.soul.gameserver.item.msg.GCEquipForgeReplace;
import com.hifun.soul.gameserver.item.msg.GCEquipForgeToLock;
import com.hifun.soul.gameserver.item.msg.GCSelectEquipToForge;
import com.hifun.soul.gameserver.item.msg.GCUpdateEquipForgePanel;
import com.hifun.soul.gameserver.item.template.EquipItemTemplate;
import com.hifun.soul.gameserver.item.template.GemAttribute;
import com.hifun.soul.gameserver.item.template.ItemForgeTemplate;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.HumanLongProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;

/**
 * 装备洗炼的功能
 * @author magicstone
 */
public class HumanForgeManager {
	private static Logger logger = Loggers.ITEM_LOGGER;
	private Human human;
	
	public HumanForgeManager(Human human) {
		this.human = human;
	}
	
	public Human getHuman() {
		return this.human;
	}
	
	public long getLastResetTime() {
		return human.getHumanPropertiesManager()
				.getLongPropertyValue(HumanLongProperty.LAST_RESET_FREE_EQUIP_FORGE);
	}

	public void setLastResetTime(long lastTime) {
		human.getHumanPropertiesManager()
			.setLongPropertyValue(HumanLongProperty.LAST_RESET_FREE_EQUIP_FORGE, lastTime);
	}
	
	/**
	 * 重置免费洗炼次数
	 */
	public void resetFreeResetTimes() {
		setFreeForgeTimes(GameServerAssist.getGameConstants().getEquipForgeFreeTimes());
	}
	
	/**
	 * 获取免费洗炼次数
	 * @return
	 */
	private int getFreeForgeTimes() {
		return human.getPropertyManager().getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(HumanIntProperty.FORGE_TIMES);
	}
	
	/**
	 * 设置免费洗炼次数
	 * @param times
	 */
	private void setFreeForgeTimes(int times) {
		human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
			.setPropertyValue(HumanIntProperty.FORGE_TIMES, times);
	}
	
	/**
	 * 获取当前可以洗炼的类型
	 * @return
	 */
	private ForgeType getForgeType() {
		if(getFreeForgeTimes()>0){
			return ForgeType.FREE;
		}
		else{
			return ForgeType.STONE;
		}
	}
	
	/**
	 * 选中装备准备洗炼
	 * @param equipItem
	 * @param lockNum
	 */
	public void selectEquipToForge(Item equipItem, int lockNum) {
		EquipForgeInfo equipForgeInfo = getEquipForgeInfo(equipItem,lockNum);
		if(equipForgeInfo == null){
			return;
		}
		if(equipForgeInfo.getEquipSpecialAttributes() == null
				|| equipForgeInfo.getEquipSpecialAttributes().length <= 0){
			human.sendWarningMessage(LangConstants.NO_SPECIAL_ATTRIBUTES);
			return;
		}
		// 通知客户端
		GCSelectEquipToForge gcMsg = new GCSelectEquipToForge();
		gcMsg.setAttributes(equipForgeInfo.getAttributes());
		gcMsg.setEquipSpecialAttributes(equipForgeInfo.getEquipSpecialAttributes());
		gcMsg.setNewEquipSpecialAttributes(equipForgeInfo.getNewEquipSpecialAttributes());
		gcMsg.setForgeType(equipForgeInfo.getForgeType());
		gcMsg.setFreeTimes(equipForgeInfo.getFreeTimes());
		gcMsg.setStoneIcon(equipForgeInfo.getStoneIcon());
		gcMsg.setStoneName(equipForgeInfo.getStoneName());
		gcMsg.setStoneNum(equipForgeInfo.getStoneNum());
		gcMsg.setStoneId(equipForgeInfo.getStoneId());
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 更新洗练面板
	 */
	public void updateForgePanel(Item equipItem, int lockNum) {
		EquipForgeInfo equipForgeInfo = getEquipForgeInfo(equipItem,lockNum);
		if(equipForgeInfo == null){
			return;
		}
		// 通知客户端
		GCUpdateEquipForgePanel gcMsg = new GCUpdateEquipForgePanel();
		gcMsg.setEquipSpecialAttributes(equipForgeInfo.getEquipSpecialAttributes());
		gcMsg.setNewEquipSpecialAttributes(equipForgeInfo.getNewEquipSpecialAttributes());
		gcMsg.setForgeType(equipForgeInfo.getForgeType());
		gcMsg.setFreeTimes(equipForgeInfo.getFreeTimes());
		gcMsg.setCrystalNum(equipForgeInfo.getCrystalNum());
		gcMsg.setStoneIcon(equipForgeInfo.getStoneIcon());
		gcMsg.setStoneName(equipForgeInfo.getStoneName());
		gcMsg.setStoneNum(equipForgeInfo.getStoneNum());
		gcMsg.setStoneId(equipForgeInfo.getStoneId());
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 装备洗练面板的显示信息
	 * @param equipItem
	 * @param lockNum
	 * @return
	 */
	private EquipForgeInfo getEquipForgeInfo(Item equipItem, int lockNum) {
		if(equipItem == null
				|| !equipItem.isEquip()){
			return null;
		}
		EquipForgeInfo equipForgeInfo = new EquipForgeInfo();
		EquipItemFeature equipItemFeature = (EquipItemFeature)equipItem.getFeature();
		EquipItemTemplate equipItemTemplate = (EquipItemTemplate)equipItem.getTemplate();
		// 装备随机属性具体值
		List<KeyValuePair<Integer, Integer>> _equipSpecialAttributes = equipItemFeature.getEquipSpecialAttributes();
		if(_equipSpecialAttributes == null){
			KeyValuePair<Integer, Integer>[] emptyAttributes = KeyValuePair
					.newKeyValuePairArray(0);
			equipForgeInfo.setEquipSpecialAttributes(emptyAttributes);
		}
		else{
			KeyValuePair<Integer, Integer>[] attributes = KeyValuePair.newKeyValuePairArray(_equipSpecialAttributes.size());
			for(int i=0; i<attributes.length; i++) {
				attributes[i] = _equipSpecialAttributes.get(i);
			}
			equipForgeInfo.setEquipSpecialAttributes(attributes);
		}
		// 装备洗练尚未保存的随机属性
		List<KeyValuePair<Integer,Integer>> _newEquipSpecialAttributes = equipItemFeature.getNewEquipSpecialAttributes();
		if(_newEquipSpecialAttributes == null){
			KeyValuePair<Integer, Integer>[] emptyAttributes = KeyValuePair
					.newKeyValuePairArray(0);
			equipForgeInfo.setNewEquipSpecialAttributes(emptyAttributes);
		}
		else{
			KeyValuePair<Integer, Integer>[] attributes = KeyValuePair.newKeyValuePairArray(_newEquipSpecialAttributes.size());
			for(int i=0; i<attributes.length; i++) {
				attributes[i] = _newEquipSpecialAttributes.get(i);
			}
			equipForgeInfo.setNewEquipSpecialAttributes(attributes);
		}
		// 装备随机属性值区间
		equipForgeInfo.setAttributes(equipItemTemplate.getSpecialAttributes().toArray(new GemAttribute[0]));
		// 判断洗炼类型(有免费洗练用免费洗练，没有的话判断是否可以有足够的灵石，没有足够的灵石，vip等级够的话可以用魔晶洗炼)
		ForgeType forgeType = getForgeType();
		equipForgeInfo.setForgeType(forgeType.getIndex());
		switch(forgeType){
			case FREE:
				equipForgeInfo.setFreeTimes(getFreeForgeTimes());
			case STONE:
				ItemForgeTemplate itemForgeTemplate = GameServerAssist
						.getForgeTemplateManager().getItemForgeTemplate(equipItemTemplate.getRarity(), lockNum);
				if(itemForgeTemplate == null){
					logger.warn(String.format("can not find itemForgeTemplate! quality:%d,lockNum:%d", equipItemTemplate.getRarity(), lockNum));
					return null;
				}
				// 物品的名称
				ItemTemplate itemTemplate = GameServerAssist
						.getItemTemplateManager().getItemTemplate(itemForgeTemplate.getCostItemId());
				equipForgeInfo.setStoneName(itemTemplate.getName());
				equipForgeInfo.setStoneNum(itemForgeTemplate.getCostItemNum());
				equipForgeInfo.setCrystalNum(itemForgeTemplate.getCrystal());
				equipForgeInfo.setStoneIcon(itemTemplate.getIcon());
				equipForgeInfo.setStoneId(itemForgeTemplate.getCostItemId());
				break;
			default:
				return null;
		}
		return equipForgeInfo;
	}
	
	/**
	 * 更新洗炼消耗
	 * @param equipItem
	 * @param lockNum
	 */
	public void updateForgeCost(Item equipItem, int lockNum) {
		if(equipItem == null
				|| !equipItem.isEquip()){
			return;
		}
		EquipItemTemplate equipItemTemplate = (EquipItemTemplate)equipItem.getTemplate();
		GCEquipForgeToLock gcMsg = new GCEquipForgeToLock();
		// 判断洗炼类型
		ForgeType forgeType = getForgeType();
		switch(forgeType){
			case FREE:
			case STONE:
				ItemForgeTemplate itemForgeTemplate = GameServerAssist
						.getForgeTemplateManager().getItemForgeTemplate(equipItemTemplate.getRarity(), lockNum);
				if(itemForgeTemplate == null){
					logger.warn(String.format("can not find itemForgeTemplate! quality:%d,lockNum:%d", equipItemTemplate.getRarity(), lockNum));
					return;
				}
				// 物品的名称
				ItemTemplate itemTemplate = GameServerAssist
						.getItemTemplateManager().getItemTemplate(itemForgeTemplate.getCostItemId());
				gcMsg.setStoneName(itemTemplate.getName());
				gcMsg.setStoneNum(itemForgeTemplate.getCostItemNum());
				gcMsg.setCrystalNum(itemForgeTemplate.getCrystal());
				gcMsg.setStoneIcon(itemTemplate.getIcon());
				gcMsg.setStoneId(itemForgeTemplate.getCostItemId());
				gcMsg.setForgeType(forgeType.getIndex());
				break;
			default:
				break;
		}
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 装备洗炼的具体操作
	 * @param equipItem
	 * @param locks
	 */
	public void equipForge(Item equipItem, int[] locks) {
		if(equipItem == null
				|| !equipItem.isEquip()){
			return;
		}
		EquipItemFeature equipItemFeature = (EquipItemFeature)equipItem.getFeature();
		EquipItemTemplate equipItemTemplate = (EquipItemTemplate)equipItem.getTemplate();
		// 校验一下锁定属性的合法性
		if(!checkLockIndexs(locks, equipItemFeature.getEquipSpecialAttributes().size())){
			return;
		}
		// 获取当前的洗炼类型 ,计算消耗
		boolean costState = false;
		ForgeType forgeType = getForgeType();
		switch(forgeType){
			case FREE:
				costState = equipForgeForFree(equipItemTemplate,locks);
				break;
			case STONE:
				costState = equipForgeForStone(equipItemTemplate,locks);
				break;
			default:
				break;
		}
		// 消耗成功之后，计算新的随机属性并更新客户端
		if(costState){
			equipItemFeature.equipForge(locks);
			// 更新面板
			updateForgePanel(equipItem, locks.length);
			// 发送装备洗练事件
			EquipRefineEvent equipRefineEvent = new EquipRefineEvent();
			human.getEventBus().fireEvent(equipRefineEvent);
		}
	}
	
	/**
	 * 替换洗练的属性
	 * @param equipItem
	 */
	public void replaceForge(Item equipItem) {
		if(equipItem == null
				|| !equipItem.isEquip()){
			return;
		}
		EquipItemFeature equipItemFeature = (EquipItemFeature)equipItem.getFeature();
		equipItemFeature.equipForgeReplace();
		human.getBagManager().updateItem(equipItem.getBagType(), equipItem.getBagIndex());
		GCEquipForgeReplace gcMsg = new GCEquipForgeReplace();
		List<KeyValuePair<Integer, Integer>> _equipSpecialAttributes = equipItemFeature.getEquipSpecialAttributes();
		if(_equipSpecialAttributes == null){
			KeyValuePair<Integer, Integer>[] emptyAttributes = KeyValuePair
					.newKeyValuePairArray(0);
			gcMsg.setEquipSpecialAttributes(emptyAttributes);
		}
		else{
			KeyValuePair<Integer, Integer>[] attributes = KeyValuePair.newKeyValuePairArray(_equipSpecialAttributes.size());
			for(int i=0; i<attributes.length; i++) {
				attributes[i] = _equipSpecialAttributes.get(i);
			}
			gcMsg.setEquipSpecialAttributes(attributes);
		}
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 取消保存洗练属性
	 * @param equipItem
	 */
	public void cancelForge(Item equipItem) {
		if(equipItem == null
				|| !equipItem.isEquip()){
			return;
		}
		EquipItemFeature equipItemFeature = (EquipItemFeature)equipItem.getFeature();
		equipItemFeature.equipForgeCancel();
		GCEquipForgeCancel gcMsg = new GCEquipForgeCancel();
		human.sendMessage(gcMsg);
	}
	
	/**
	 * 免费洗炼
	 */
	private boolean equipForgeForFree(EquipItemTemplate equipItemTemplate, int[] locks) {
		// 判断当前是否有免费次数
		if(getFreeForgeTimes() <= 0){
			return false;
		}
		ItemForgeTemplate itemForgeTemplate = GameServerAssist
				.getForgeTemplateManager().getItemForgeTemplate(equipItemTemplate.getRarity(), locks.length);
		if(itemForgeTemplate == null){
			logger.warn(String.format("can not find itemForgeTemplate! quality:%d,lockNum:%d", equipItemTemplate.getRarity(), locks.length));
			return false;
		}
		// 判断是否有足够的魔晶
		if(!human.getWallet().isEnough(CurrencyType.CRYSTAL, itemForgeTemplate.getCrystal())){
			human.sendErrorMessage(LangConstants.COMMON_NOT_ENOUGH, CurrencyType.CRYSTAL.getDesc());
			return false;
		}
		// 消耗魔晶
		if(human.getWallet().costMoney(CurrencyType.CRYSTAL, itemForgeTemplate.getCrystal(), MoneyLogReason.EQUIP_FORGE, "")){
			// 减少免费次数
			setFreeForgeTimes(getFreeForgeTimes()-1);
			return true;
		}
		return false;
	}
	
	/**
	 * 灵石洗炼
	 * @return
	 */
	private boolean equipForgeForStone(EquipItemTemplate equipItemTemplate, int[] locks) {
		ItemForgeTemplate itemForgeTemplate = GameServerAssist
				.getForgeTemplateManager().getItemForgeTemplate(equipItemTemplate.getRarity(), locks.length);
		if(itemForgeTemplate == null){
			logger.warn(String.format("can not find itemForgeTemplate! quality:%d,lockNum:%d", equipItemTemplate.getRarity(), locks.length));
			return false;
		}
		// 判断是否有足够的灵石
		if(human.getBagManager().getItemCount(itemForgeTemplate.getCostItemId()) < itemForgeTemplate.getCostItemNum()){
			ItemTemplate itemTemplate = GameServerAssist
					.getItemTemplateManager().getItemTemplate(itemForgeTemplate.getCostItemId());
			if(itemTemplate != null){
				human.sendErrorMessage(LangConstants.COMMON_NOT_ENOUGH, itemTemplate.getName());
			}
			return false;
		}
		// 判断是否有足够的魔晶
		if(!human.getWallet().isEnough(CurrencyType.CRYSTAL, itemForgeTemplate.getCrystal())){
			human.sendErrorMessage(LangConstants.COMMON_NOT_ENOUGH, CurrencyType.CRYSTAL.getDesc());
			return false;
		}
		// 消耗灵石和魔晶
		if(human.getBagManager().removeItemByItemId(itemForgeTemplate.getCostItemId(), itemForgeTemplate.getCostItemNum(), ItemLogReason.EQUIP_FORGE, "")
				&& human.getWallet().costMoney(CurrencyType.CRYSTAL, itemForgeTemplate.getCrystal(), MoneyLogReason.EQUIP_FORGE, "")){
			return true;
		}
		return false;
	}
	
	/**
	 * 校验属性的合法性
	 * @param locks
	 * @param specialPropSize
	 * @return
	 */
	private boolean checkLockIndexs(int[] locks, int specialPropSize) {
		// 不可能所有位置全部锁定
		if(locks.length >= specialPropSize){
			return false;
		}
		// 锁定的位置不能出现重复，还是校验一下吧
		List<Integer> lockSet = new ArrayList<Integer>();
		for(Integer lockIndex : locks){
			if(lockSet.contains(lockIndex)){
				return false;
			}
			else{
				lockSet.add(lockIndex);
			}
		}
		// 锁定位置应该>=0并且<=specialPropSize-1
		for(Integer lockIndex : locks){
			if(lockIndex < 0
					|| lockIndex >= specialPropSize){
				return false;
			}
		}
		return true;
	}
}
