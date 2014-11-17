package com.hifun.soul.gameserver.shop.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.shop.SpecialShopRefreshType;
import com.hifun.soul.gameserver.shop.manager.HumanSpecialShopManager;
import com.hifun.soul.gameserver.shop.msg.CGRefreshSpecialShop;
import com.hifun.soul.gameserver.shop.service.SpecialShopNotifyService;
import com.hifun.soul.gameserver.vip.template.RefreshSpecialShopTemplate;

@Component
public class CGRefreshSpecialShopHandler implements
		IMessageHandlerWithType<CGRefreshSpecialShop> {

	@Autowired
	private TemplateService templateService;
	@Autowired
	private GameFuncService gameFuncService;
	@Autowired
	private SpecialShopNotifyService specialShopService;

	
	@Override
	public short getMessageType() {
		return MessageType.CG_REFRESH_SPECIAL_SHOP;
	}

	@Override
	public void execute(CGRefreshSpecialShop message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(player.getHuman(), GameFuncType.SPECIAL_SHOP, true)){
			return;
		}
		HumanSpecialShopManager specialShopManager = human.getHumanSpecialShopManager();
		RefreshSpecialShopTemplate template = templateService.get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID, RefreshSpecialShopTemplate.class);
		if(template == null){
			return;
		}
		
		// 判断是否可以刷新神秘商店
		if(!specialShopManager.canRefreshSpecialShop()){
			human.sendGenericMessage(LangConstants.SPECIAL_SHOP_REFRESH_TIME_USE_OUT);
			return;
		}
		
		// 判断是否有足够的金钱
		if(!human.getWallet().isEnough(CurrencyType.CRYSTAL, template.getCrystal())){
			human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH,CurrencyType.CRYSTAL.getDesc());
			return;
		}
		
		// 消耗金钱执行刷新操作
		if(human.getWallet().costMoney(CurrencyType.CRYSTAL, template.getCrystal(), MoneyLogReason.REFRESH_SPECIAL_SHOP, "")){
			specialShopManager.refreshSpecialShop(SpecialShopRefreshType.CRYSTAL);
			specialShopManager.setSpecialShopRefreshTime(specialShopManager.getSpecialShopRefreshTime()+1);
		}
		
		// 更新面板
		specialShopManager.updateSpecialShopPanel(specialShopService);
	}

}
