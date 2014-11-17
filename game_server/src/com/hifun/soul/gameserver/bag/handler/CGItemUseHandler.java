package com.hifun.soul.gameserver.bag.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.EnergyLogReason;
import com.hifun.soul.common.LogReasons.HonourLogReason;
import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.PrestigeLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.bag.msg.CGItemUse;
import com.hifun.soul.gameserver.bag.service.BagService;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.ConsumeItem;
import com.hifun.soul.gameserver.item.DynamicPropertyType;
import com.hifun.soul.gameserver.item.EquipItem;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemDetailType;
import com.hifun.soul.gameserver.item.feature.ConsumeItemFeature;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.msg.GCUseNostrum;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.skill.template.SkillScrollTemplate;
import com.hifun.soul.gameserver.skill.template.SkillTemplateManager;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

@Component
public class CGItemUseHandler implements IMessageHandlerWithType<CGItemUse> {

	@Autowired
	BagService bagService;
	@Autowired
	private SkillTemplateManager skillTemplateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_ITEM_USE;
	}

	@Override
	public void execute(CGItemUse message) {
		Player player = message.getSession().getPlayer();
		if (player == null) {
			return;
		}
		Human human = player.getHuman();
		HumanBagManager bagManager = human.getBagManager();
		BagType bagType = BagType.indexOf(message.getBagType());
		int bagIndex = message.getBagIndex();
		switch (bagType) {
		case MAIN_BAG:
			Item item = bagManager.getItem(bagType, bagIndex);
			if (item == null) {
				return;
			}
			if (item.isEquip()) {
				EquipItem equipment = (EquipItem) item;
				EquipItemFeature equipItemFeature = (EquipItemFeature) equipment.getFeature();
				if (equipItemFeature.isEquiped()) {
					bagManager.takeOffEquipItem(bagIndex);
				} else {
					bagManager.equipItem(bagIndex);
				}
			} else if (item instanceof ConsumeItem) {
				consumeItem(human, (ConsumeItem) item);
			} else {
				// 不能使用
				human.sendWarningMessage(LangConstants.CAN_NOT_USE_THIS_ITEM);
			}
			break;
		}
	}

	/**
	 * 消耗物品
	 * 
	 * @param player
	 * @param item
	 */
	private void consumeItem(Human human, ConsumeItem item) {
		if (item.getType() == ItemDetailType.SPREE.getIndex()) {
			useSpree(human, item);
		} else if (item.getType() == ItemDetailType.SKILLSCROLL.getIndex()) {
			useSkillScroll(human, item);
		} else if (item.getType() == ItemDetailType.PROPERTY_POINT_RESET_ITEM.getIndex()) {
			human.getBagManager().usePropertyPointResetItem(item.getBagType(), item.getBagIndex());
		} else if (item.getType() == ItemDetailType.DYNAMIC_PROPERTY_ITEM.getIndex()) {
			useDynamicPropertyItem(human, item);
		} else if (item.getType() == ItemDetailType.ENERGY_ITEM.getIndex()) {
			useEnergyItem(human, item);
		} else if (item.getType() == ItemDetailType.VIP_TEMP.getIndex()) {
			useVipItem(human, item);
		} else if (item.getType() == ItemDetailType.RESET_SKILL_POINT_TEMP.getIndex()) {
			useResetSkillPointItem(human, item);
		} else if (item.getType() == ItemDetailType.NOSTRUM.getIndex()) {
			useNostrum(human, item);
		} else {
			// 不能使用
			human.sendWarningMessage(LangConstants.CAN_NOT_USE_THIS_ITEM);
		}
	}

	/**
	 * 使用礼包
	 * 
	 * @param human
	 * @param item
	 */
	private void useSpree(Human human, ConsumeItem item) {
		// 逻辑跟使用虚拟礼包一样
		HumanBagManager bagManager = human.getBagManager();
		if (bagManager.useVirtualSpree(item, true)) {
			bagManager.removeItem(item.getBagType(), item.getBagIndex(), 1,
					ItemLogReason.SPREE_USE, "");
		}
	}

	/**
	 * 使用技能卷轴
	 * 
	 * @param human
	 * @param item
	 */
	private void useSkillScroll(Human human, ConsumeItem item) {
		// 根据卷轴找到对应的技能
		SkillScrollTemplate template = skillTemplateManager.getSkillScrollTemplateBySkillScrollId(item.getItemId());
		if (template != null) {
			// 技能卷轴使用
			human.getSkillManager().studySkill(template.getId(), item);
		}
	}

	/**
	 * 使用动态属性的物品
	 * 
	 * @param human
	 * @param item
	 */
	private void useDynamicPropertyItem(Human human, ConsumeItem item) {
		ConsumeItemFeature itemFeature = (ConsumeItemFeature) item.getFeature();
		int propValue = 0;
		for (DynamicPropertyType type : DynamicPropertyType.values()) {
			propValue = itemFeature.getDynamicPropertyValue(type);
			switch (type) {
			case COIN:
				if (propValue > 0) {
					human.getWallet().addMoney(CurrencyType.COIN, propValue, true, MoneyLogReason.SPREE_USE, "");
				}
				break;
			case HONOUR:
				if (propValue > 0) {
					human.addArenaHonor(propValue, true, HonourLogReason.ITEM_USE_ADD_HONOUR, "");
				}
				break;
			case PRESTIGE:
				if(propValue > 0) {
					human.addPrestige(propValue, true, PrestigeLogReason.ITEM_USE_ADD_PRESTIGE, "");
				}
			}
		}
		human.getBagManager().removeItem(item.getBagType(), item.getBagIndex(), 1, ItemLogReason.SPREE_USE, "");
	}

	/**
	 * 使用动态属性的物品
	 * 
	 * @param human
	 * @param item
	 */
	private void useEnergyItem(Human human, ConsumeItem item) {
		if (human.getEnergy() >= GameServerAssist.getGameConstants().getMaxEnergy()) {
			human.sendErrorMessage(LangConstants.NO_NEED_USE_ENERGY_ITEM);
			return;
		}
		int addEnergy = item.getTemplate().getIntParam1();
		human.getBagManager().removeItem(item.getBagType(), item.getBagIndex(), 1, ItemLogReason.COMSUME_USE, "");
		human.setEnergy(human.getEnergy() + addEnergy, EnergyLogReason.USE_ENERGY_ITEM, "");
	}

	/**
	 * 使用动态属性的物品
	 * 
	 * @param human
	 * @param item
	 */
	private void useVipItem(Human human, ConsumeItem item) {
		int vipLevel = item.getTemplate().getIntParam1();
		int currentVip = human.getVipLevel();
		if (currentVip >= vipLevel) {
			human.sendErrorMessage(LangConstants.CANT_CHANGE_VIP_NOTICE, currentVip);
			return;
		}
		human.getBagManager().removeItem(item.getBagType(), item.getBagIndex(), 1, ItemLogReason.COMSUME_USE, "");
		human.getHumanVipManager().setVipLevelTemporary(vipLevel);
		human.getHumanCdManager().removeCdFree(CdType.VIP_TEMP);
		human.getHumanCdManager().addCd(CdType.VIP_TEMP, item.getTemplate().getIntParam2() * TimeUtils.MIN);
		human.getHumanCdManager().snapCdQueueInfo(CdType.VIP_TEMP);
	}

	/**
	 * 使用重置技能点道具
	 * 
	 * @param human
	 * @param item
	 */
	private void useResetSkillPointItem(Human human, ConsumeItem item) {
		if (human.getBagManager().removeItem(item.getBagType(), item.getBagIndex(), 1, ItemLogReason.COMSUME_USE, "")) {
			human.getSkillManager().resetSkills();
		}
	}
	
	/**
	 * 使用秘药
	 * 
	 * @param human
	 * @param item
	 */
	private void useNostrum(Human human, ConsumeItem item) {
		// 是否达到秘药使用数量上限
		VipPrivilegeTemplate vipTemplate = GameServerAssist
				.getVipPrivilegeTemplateManager().getVipTemplate(
						human.getVipLevel());
		if (vipTemplate == null) {
			return;
		}
		int maxNostrumNum = vipTemplate.getMaxNostrumNum();
		if (human.getHumanNostrumManager().getNostrumList().size() >= maxNostrumNum) {
			human.sendErrorMessage(LangConstants.NOSTRUM_TO_MAX_NUM);
			return;
		}
		int minutes = item.getTemplate().getIntParam2();
		int propertyId = item.getTemplate().getNostrumPropertyId();
		int propertyAmendRate = item.getTemplate()
				.getNostrumPropertyAmendRate();
		int propertyAmendMethod = item.getTemplate()
				.getNostrumPropertyAmendMethod();
		human.getHumanNostrumManager().addNostrum(propertyId, minutes,
				propertyAmendRate, propertyAmendMethod);
		human.getBagManager().removeItem(item.getBagType(), item.getBagIndex(),
				1, ItemLogReason.COMSUME_USE, "");
		// 返回消息
		GCUseNostrum msg = new GCUseNostrum();
		human.sendMessage(msg);
	}
}
