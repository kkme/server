package com.hifun.soul.gameserver.item.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.EquipUpgradeEvent;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.ItemConstantId;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.feature.ConsumeItemFeature;
import com.hifun.soul.gameserver.item.feature.EquipItemFeature;
import com.hifun.soul.gameserver.item.msg.CGEquipUpgrade;
import com.hifun.soul.gameserver.item.msg.GCEquipUpgrade;
import com.hifun.soul.gameserver.item.msg.GCSelectEquip;
import com.hifun.soul.gameserver.item.service.EquipUpgradeTemplateManager;
import com.hifun.soul.gameserver.item.template.EquipUpgradeTemplate;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGEquipUpgradeHandler implements
		IMessageHandlerWithType<CGEquipUpgrade> {
	@Autowired
	private EquipUpgradeTemplateManager equipUpgradeTemplateManager;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_EQUIP_UPGRADE;
	}

	@Override
	public void execute(CGEquipUpgrade message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.EQUIP_UPGRADE, true)){
			return;
		}
		HumanBagManager bagManager = human.getBagManager();
		// 判断是否是装备
		BagType equipBagType = BagType.indexOf(message.getEquipBagType());
		int equipBagIndex = message.getEquipBagIndex();
		Item equip = bagManager.getItem(equipBagType, equipBagIndex);
		if(equip == null
				|| !equip.isEquip()){
			return;
		}
		EquipItemFeature equipItemFeature = (EquipItemFeature)equip.getFeature();
		int level = equipItemFeature.getLevel();
		// 判断是否有下一级强化模版
		EquipUpgradeTemplate nextEquipUpgradeTemplate 
			= equipUpgradeTemplateManager.getEquipUpgradeTemplate(equip.getItemId(), level+1);
		if(nextEquipUpgradeTemplate == null){
			human.sendGenericMessage(LangConstants.EQUIP_UPGRADE_FULL);
			return;
		}
		// 获取到强化模版
		EquipUpgradeTemplate equipUpgradeTemplate 
			= equipUpgradeTemplateManager.getEquipUpgradeTemplate(equip.getItemId(), level);
		if(equipUpgradeTemplate == null){
			return;
		}
		// 判断强化石是否足够
		if(bagManager.getItemCount(equipUpgradeTemplate.getMaterialId()) < equipUpgradeTemplate.getMaterialNum()){
			human.sendErrorMessage(LangConstants.EQUIP_UPGRADE_MATERIAL_NOT_ENOUGH);
			return;
		}
		int guardStoneId = message.getGuardStoneId();
		int fortuneStoneId = message.getFortuneStoneId();
		// 是否使用守护石
		boolean isUseGuardStone = true;
		if(guardStoneId <= 0){
			isUseGuardStone = false;
		}
		int guardStoneCount = bagManager.getItemCount(guardStoneId);
		// 是否使用幸运石
		boolean isUseFortuneStone = true;
		if(fortuneStoneId <= 0){
			isUseFortuneStone = false;
		}
		int fortuneStoneCount = bagManager.getItemCount(fortuneStoneId);
		// 判断道具类型
		if(isUseGuardStone){
			// 判断道具类型
			if(guardStoneCount < 1){
				human.sendErrorMessage(LangConstants.NO_ENOUGH_GUARD_STONE);
				return;
			}
		}
		if(isUseFortuneStone){
			// 判断道具类型
			if((fortuneStoneId != ItemConstantId.SIMPLE_FORTUNE_STONE_ID 
					&& fortuneStoneId != ItemConstantId.SUPER_FORTUNE_STONE_ID)
					|| fortuneStoneCount < 1){
				human.sendErrorMessage(LangConstants.NO_ENOUGH_FORTUNE_STONE);
				return;
			}
		}
		// 判断货币是否足够
		CurrencyType costCurrencyType = CurrencyType.indexOf(equipUpgradeTemplate.getCostCurrencyType());
		int costCurrencyNum = equipUpgradeTemplate.getCostCurrencyNum();
		if(!human.getWallet().isEnough(costCurrencyType, costCurrencyNum)){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, costCurrencyType.getDesc());
			return;
		}
		if(human.getWallet().costMoney(costCurrencyType, costCurrencyNum, MoneyLogReason.EQUIP_UPGRADE, "")){
			// 判断强化是否成功
			float successRate = equipUpgradeTemplate.getUpgradeRate();
			if(isUseFortuneStone){
				Item fortuneStone = ItemFactory.creatNewItem(human, fortuneStoneId);
				ConsumeItemFeature consumeItemFeature = (ConsumeItemFeature)fortuneStone.getFeature();
				successRate += consumeItemFeature.getExtraSuccessRate();
			}
			if(MathUtils.shake(successRate/SharedConstants.DEFAULT_FLOAT_BASE)){
				// 强化成功
				equipItemFeature.setLevel(level+1,PropertyLogReason.EQUIP_UPGRADE,"");
				// 发送成功提示信息
				human.sendSuccessMessage(LangConstants.EQUIP_UPGRADE_SUCESS, level+1);
			}
			else{
				// 强化失败
				float degradeRate = equipUpgradeTemplate.getDegradeRate()/SharedConstants.DEFAULT_FLOAT_BASE;
				if(MathUtils.shake(degradeRate)
						&& !isUseGuardStone){
					equipItemFeature.setLevel(equipUpgradeTemplate.getDegradeLevel(),PropertyLogReason.EQUIP_UPGRADE,"");
					// 强化失败并降级
					human.sendGenericMessage(LangConstants.EQUIP_UPGRADE_FAIL, equipUpgradeTemplate.getDegradeLevel());
				}
				else{
					// 强化失败不降级
					human.sendGenericMessage(LangConstants.EQUIP_UPGRADE_FAIL_USE_GUARDSTONE);
				}
			}
			// 删除材料、守护石、幸运石
			human.getBagManager().removeItemByItemId(equipUpgradeTemplate.getMaterialId(),equipUpgradeTemplate.getMaterialNum(),ItemLogReason.EQUIP_UPGRADE,"");
			if(isUseGuardStone){
				human.getBagManager().removeItemByItemId(guardStoneId,1,ItemLogReason.EQUIP_UPGRADE,"");
			}
			if(isUseFortuneStone){
				human.getBagManager().removeItemByItemId(fortuneStoneId,1,ItemLogReason.EQUIP_UPGRADE,"");
			}
			// 背包更新
			human.getBagManager().updateItem(equipBagType, equipBagIndex);
			GCEquipUpgrade gcEquipUpgrade = new GCEquipUpgrade();
			human.sendMessage(gcEquipUpgrade);
			// 更新强化面板的信息
			equipUpgradeTemplate = equipUpgradeTemplateManager.getEquipUpgradeTemplate(equip.getItemId(), equipItemFeature.getLevel());
			GCSelectEquip gcMsg = new GCSelectEquip();
			gcMsg.setRate(equipUpgradeTemplate.getUpgradeRate());
			gcMsg.setCurrencyType(equipUpgradeTemplate.getCostCurrencyType());
			gcMsg.setCurrencyNum(equipUpgradeTemplate.getCostCurrencyNum());
			gcMsg.setUpgradeAttributes(
					equipUpgradeTemplateManager.getEquipUpgradeAttributes(equip.getItemId(), equipItemFeature.getLevel()+1));
			gcMsg.setUpgradeStone(CommonItemBuilder.genCommonItem(equipUpgradeTemplate.getMaterialId()));
			gcMsg.setNeedUpgradeStoneNum(equipUpgradeTemplate.getMaterialNum());
			human.sendMessage(gcMsg);
			// 发送装备强化事件
			EquipUpgradeEvent upgradeEvent = new EquipUpgradeEvent();
			human.getEventBus().fireEvent(upgradeEvent);
		}

	}

}
