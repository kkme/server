package com.hifun.soul.gameserver.loginreward.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.bag.manager.HumanBagManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.Item;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;
import com.hifun.soul.gameserver.loginreward.LoginRewardInfo;
import com.hifun.soul.gameserver.loginreward.manager.HumanLoginRewardManager;
import com.hifun.soul.gameserver.loginreward.msg.CGGetReward;
import com.hifun.soul.gameserver.loginreward.msg.GCGetReward;
import com.hifun.soul.gameserver.loginreward.service.LoginRewardTemplateManager;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGGetRewardHandler implements IMessageHandlerWithType<CGGetReward> {
	@Autowired
	private LoginRewardTemplateManager templateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_GET_REWARD;
	}

	@Override
	public void execute(CGGetReward message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		HumanLoginRewardManager loginRewardManager = human.getHumanLoginRewardManager();
		// 校验合法性
		int index = message.getIndex();
		if(!loginRewardManager.checkIndex(index)){
			return;
		}
		// 校验是否还有剩余次数
		if(loginRewardManager.getTimes() <= 0){
			return;
		}
		// 判断背包是否已满
		HumanBagManager bagManager = human.getBagManager();
		if(bagManager.isFull(BagType.MAIN_BAG)){
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		// 随机获取奖励物品
		int rewardId = templateManager.getRewardId(loginRewardManager.getRewardIds());
		if(rewardId <= 0){
			return;
		}
		// 扣除次数
		loginRewardManager.setTimes(loginRewardManager.getTimes() - 1);
		// 添加物品
		Item item = ItemFactory.creatNewItem(human, rewardId);
		bagManager.putItem(BagType.MAIN_BAG, item, ItemLogReason.LOGIN_REWARD, "");
		LoginRewardInfo loginRewardInfo = new LoginRewardInfo();
		loginRewardInfo.setIndex(index);
		SimpleCommonItem commonItem = CommonItemBuilder.genSimpleCommonItem(rewardId);
		if(commonItem == null){
			return;
		}
		else{
			loginRewardInfo.setCommonItem(commonItem);
		}
		loginRewardManager.addLoginRewardInfo(loginRewardInfo);
		// 通知客户端
		GCGetReward gcMsg = new GCGetReward();
		gcMsg.setTimes(loginRewardManager.getTimes());
		gcMsg.setSelectItem(loginRewardInfo);
		human.sendMessage(gcMsg);
		// 同步登陆奖励状态
		loginRewardManager.updateLoginRewardState();
	}

}
