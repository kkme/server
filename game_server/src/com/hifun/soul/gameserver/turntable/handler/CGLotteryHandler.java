package com.hifun.soul.gameserver.turntable.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.entity.TurntableEntity;
import com.hifun.soul.gamedb.service.IDataService;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.event.LotteryEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.turntable.manager.HumanTurntableManager;
import com.hifun.soul.gameserver.turntable.msg.CGLottery;
import com.hifun.soul.gameserver.turntable.msg.GCLottery;
import com.hifun.soul.gameserver.turntable.msg.TurntableRewardInfo;
import com.hifun.soul.gameserver.turntable.service.TurntableDataService;
import com.hifun.soul.gameserver.turntable.template.TurntableTemplate;

@Component
public class CGLotteryHandler implements IMessageHandlerWithType<CGLottery> {
	private Logger logger = Loggers.TURNTABLE_LOGGER;

	@Autowired
	private IDataService dataService;
	@Autowired
	private TurntableDataService turntableDataService;

	
	@Override
	public short getMessageType() {
		return MessageType.CG_LOTTERY;
	}

	@Override
	public void execute(CGLottery message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
//		
//		//检查是否可用
//		if(!marketActSetting.isEnable(MarketActType.TURNTABLE, human.getLevel(), human.getVipLevel())){
//			return;
//		}
		
		TurntableTemplate turntableTemplate = GameServerAssist.getTurntableTemplateManager().getTurntableTemplate();
		if(turntableTemplate == null){
			return;
		}		
		HumanTurntableManager turntableManager = human.getHumanTurntableManager();	
		int crystalTurntableTimes = turntableManager.getTurntableUseCrystalTime();
		int remainCrystalTurntableTimes = human.getHumanVipManager().getCurrenctVipTemplate().getCrystalTurntableTimes()-crystalTurntableTimes;
		int warriorHeartNum = human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(HumanIntProperty.WARRIOR_HEART_NUM);
		if(warriorHeartNum<GameServerAssist.getGameConstants().getTurntableCost()){	
			if(remainCrystalTurntableTimes<=0){
				human.sendErrorMessage(LangConstants.NO_TURNTABLE_TIME);
				return;
			}
			int crystalCost = turntableManager.getTurntableCrystalCost();
			if(!human.getWallet().isEnough(CurrencyType.CRYSTAL, crystalCost)){
				human.sendWarningMessage(LangConstants.COMMON_NOT_ENOUGH, CurrencyType.CRYSTAL.getDesc());
				return;
			}			
		}
		
		// 判断是否有空的背包
		if(human.getBagManager().isFull(BagType.MAIN_BAG)){
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		
		// 随机获取物品
		int[] rewardInfo = turntableManager.getRandomRewardInfo(turntableTemplate);
		if(rewardInfo == null){
			return;
		}
		
		Item item = ItemFactory.creatNewItem(human, rewardInfo[1]);
		if(item == null){
			logger.error("turntableTemplate error! consumeItemId can not find.");
			return;
		}
		item.setOverlapNum(1);
		
		// 扣除勇者之心或者魔晶
		if(warriorHeartNum<GameServerAssist.getGameConstants().getTurntableCost()){
			int crystalCost = turntableManager.getTurntableCrystalCost();
			if(!human.getWallet().costMoney(CurrencyType.CRYSTAL, crystalCost,MoneyLogReason.TURNTABLE_USE_CRYSTAL,"")){				
				return;
			}
			crystalTurntableTimes = turntableManager.getTurntableUseCrystalTime();
			crystalTurntableTimes++;
			remainCrystalTurntableTimes = remainCrystalTurntableTimes>1 ? remainCrystalTurntableTimes-1 : 0;
			turntableManager.setTurntableUseCrystalTime(crystalTurntableTimes);
		}else{
			warriorHeartNum=warriorHeartNum-GameServerAssist.getGameConstants().getTurntableCost();
			human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY).setPropertyValue(HumanIntProperty.WARRIOR_HEART_NUM,warriorHeartNum);
		}		
		// 给玩家奖励
		human.getBagManager().putItem(BagType.MAIN_BAG,item, false, ItemLogReason.TURNTABLE_GET, "");
		
		// 发送抽奖事件
		LotteryEvent lotteryEvent = new LotteryEvent();
		human.getEventBus().fireEvent(lotteryEvent);
		
		// 更新奖励缓存
		TurntableRewardInfo turntableRewardInfo = new TurntableRewardInfo();
		turntableRewardInfo.setRoleName(human.getName());
		turntableRewardInfo.setRewardName(item.getName());
		turntableDataService.addTurntableRewardInfo(turntableRewardInfo);
		
		// 更新数据库
		TurntableEntity entity = new TurntableEntity();
		entity.setRoleName(human.getName());
		entity.setRewardName(item.getName());
		dataService.insert(entity, null);
		
		// 发送响应消息
		GCLottery gcMsg = new GCLottery();
		gcMsg.setCrystalCost(turntableManager.getTurntableCrystalCost());		
		gcMsg.setCrystalLotteryRemainTimes(remainCrystalTurntableTimes);
		gcMsg.setSelectIndex(rewardInfo[0]);
		gcMsg.setRewards(turntableDataService.getRewardInfos());
		human.sendMessage(gcMsg);
	}

}
