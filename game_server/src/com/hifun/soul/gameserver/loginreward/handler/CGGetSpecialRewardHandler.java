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
import com.hifun.soul.gameserver.item.assist.ItemFactory;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;
import com.hifun.soul.gameserver.loginreward.LoginRewardStateType;
import com.hifun.soul.gameserver.loginreward.manager.HumanLoginRewardManager;
import com.hifun.soul.gameserver.loginreward.msg.CGGetSpecialReward;
import com.hifun.soul.gameserver.loginreward.msg.GCGetSpecialReward;
import com.hifun.soul.gameserver.loginreward.service.LoginRewardTemplateManager;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGGetSpecialRewardHandler implements
		IMessageHandlerWithType<CGGetSpecialReward> {
	@Autowired
	private LoginRewardTemplateManager loginRewardTemplateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_SPECIAL_REWARD;
	}

	@Override
	public void execute(CGGetSpecialReward message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		HumanLoginRewardManager loginRewardManager = human.getHumanLoginRewardManager();
		// 是否可以领取奖励
		if(loginRewardManager.getLoginRewardState(message.getSpecialRewardType()) != LoginRewardStateType.CAN_GET.getIndex()){
			return;
		}
		// 判断背包是否已满
		HumanBagManager bagManager = human.getBagManager();
		if(bagManager.isFull(BagType.MAIN_BAG)){
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		// 给玩家物品
		SimpleCommonItem simpleCommonItem = loginRewardTemplateManager.getReward(message.getSpecialRewardType());
		if(simpleCommonItem == null){
			return;
		}
		Item item = ItemFactory.creatNewItem(human, simpleCommonItem.getItemId());
		bagManager.putItem(BagType.MAIN_BAG, item, ItemLogReason.LOGIN_REWARD, "");
		// 修改奖励领取状态
		loginRewardManager.setLoginRewardState(message.getSpecialRewardType(), LoginRewardStateType.GETED, true);
		// 通知客户端
		GCGetSpecialReward gcMsg = new GCGetSpecialReward();
		gcMsg.setSpecialRewardType(message.getSpecialRewardType());
		gcMsg.setState(LoginRewardStateType.GETED.getIndex());
		human.sendMessage(gcMsg);
		// 是否还有可领取奖励
		loginRewardManager.updateLoginRewardState();
	}

}
